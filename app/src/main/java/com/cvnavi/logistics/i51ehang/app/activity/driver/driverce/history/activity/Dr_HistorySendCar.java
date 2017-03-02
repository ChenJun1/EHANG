package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.history.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.history.adapter.HistorySendCarAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.utils.DriverDataChooseActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MySendCar;
import com.cvnavi.logistics.i51ehang.app.bean.driver.request.SendCarRquestBean;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetMySendCarResponseBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/2/20 10:02
 * version: 2.3.2
 * Depict:
 */

public class Dr_HistorySendCar extends BaseActivity implements HandlerUtils.OnReceiveMessageListener {
    private static final String TAG = "Dr_HistorySendCar";
    private final int REQUESTCODE = 10022;//请求码

    @BindView(R.id.back_llayout)
    LinearLayout mBackLlayout;
    @BindView(R.id.titlt_tv)
    TextView mTitltTv;
    @BindView(R.id.search_iv)
    ImageView mSearchIv;
    @BindView(R.id.search_ll)
    LinearLayout mSearchLl;
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;
    @BindView(R.id.history_send_car_pull_lv)
    PullToRefreshListView mHistorySendCarPullLv;
    @BindView(R.id.add_iv)
    ImageView mAddIv;
    @BindView(R.id.add_ll)
    LinearLayout mAddLl;
    @BindView(R.id.right_ll)
    LinearLayout mRightLl;
    @BindView(R.id.right_tv)
    TextView mRightTv;
    @BindView(R.id.content_ll)
    LinearLayout mContentLl;
    @BindView(R.id.check_tv)
    TextView mCheckTv;
    @BindView(R.id.add)
    LinearLayout mAdd;
    @BindView(R.id.custom_ll)
    LinearLayout mCustomLl;

    private ListView myListView;
    private List<MySendCar> list;
    private HistorySendCarAdapter adapter;
    private SweetAlertDialog lodingDialog;
    private DataRequestBase dataRequestBase;
    private SendCarRquestBean mRquestBean;
    private boolean isRefresh = false;//是否刷新
    private int pageInt = 1;//加载页面数
    private HandlerUtils.HandlerHolder mHandlerHolder;

    private String beginTime = "";
    private String endTime = "";
    private int requestTime=-7;//请求的时间天数

    public static void start(Context context) {
        Intent starter = new Intent(context, Dr_HistorySendCar.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_send_car);
        ButterKnife.bind(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        mTitltTv.setText("历史发车");

        //头部右布局只显示查询图标
        mRightLl.setVisibility(View.VISIBLE);
        mAddLl.setVisibility(View.GONE);

        init();
        onRefreshs();
    }

    private void init() {
        mRquestBean = new SendCarRquestBean();
        dataRequestBase = new DataRequestBase();
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        //设置可以刷新加载属性
        mHistorySendCarPullLv.setMode(PullToRefreshBase.Mode.BOTH);

        mHistorySendCarPullLv.setOnRefreshListener(new PullToRefreshBase
                .OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefreshs();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                onLoad();
            }
        });
        list = new ArrayList<>();
        if (adapter == null) {
            adapter = new HistorySendCarAdapter(list, this);
        }
        myListView = mHistorySendCarPullLv.getRefreshableView();
        myListView.setAdapter(adapter);
    }

    private void onRefreshs() {
        isRefresh = true;
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        loadDataRequest(DriverService.GetMyLeaveCarCode_URL);
    }

    private void onLoad() {
        isRefresh = false;
        pageInt++;
        dataRequestBase.Page = pageInt;
        loadDataRequest(DriverService.GetMyLeaveCarCode_URL);
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        mRquestBean.DeliverStatus = "2";//2表示 已完成发车
        mRquestBean.Driver_Tel = MyApplication.getInstance().getLoginInfo().DataValue.User_Tel;
        mRquestBean.BeginDate = getBeginTime();
        mRquestBean.EndDate = getEndTime();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = mRquestBean; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson
                (dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>" + response.toString());

                GetMySendCarResponseBean response1 = GsonUtil.newInstance().fromJson(response.toString(),
                        GetMySendCarResponseBean.class);
                Message msg = Message.obtain();
                if (response1 == null) {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                } else {
                    if (response1.Success) {
                        msg.obj = response1.DataValue;
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = response1.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                    }
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });

    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        mHistorySendCarPullLv.onRefreshComplete();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                List<MySendCar> dataList = (List<MySendCar>) msg.obj;
                if (isRefresh) {
                    list.clear();
                }
                if (dataList != null) {
                    list.addAll(dataList);
                }
                adapter.setData(list);
                if (list.size() > 0) {
                    mNoDataLl.setVisibility(View.GONE);
                } else {
                    mNoDataLl.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R
                        .string.request_Fill) : msg.obj.toString());
                break;
            case Constants.DELETE_SUCC:
                DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(this, Utils
                        .getResourcesString(R.string.request_error));
                break;
        }
    }

    @OnClick({R.id.back_llayout, R.id.search_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.search_ll:
                DriverDataChooseActivity.startActivity(this,REQUESTCODE,beginTime,endTime);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE:
                    beginTime = data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
                    endTime = data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);
                    onRefreshs();
                    break;
            }
        }
    }

    public String getBeginTime() {
        if (TextUtils.isEmpty(beginTime)) {
            return beginTime= DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(requestTime), DateUtil
                    .FORMAT_YMD, DateUtil.FORMAT_YMD);
        } else {
            return beginTime;
        }

    }

    public String getEndTime() {
        if (TextUtils.isEmpty(endTime)) {
            return endTime=DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(0), DateUtil.FORMAT_YMD,
                    DateUtil.FORMAT_YMD);
        } else {
            return endTime;
        }
    }
}
