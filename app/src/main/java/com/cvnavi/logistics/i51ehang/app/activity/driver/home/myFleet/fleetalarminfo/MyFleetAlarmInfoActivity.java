package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetalarminfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.SearchActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.MyTaskDetailedActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.myFleet.mAlarmBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetAlarmInfoRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAlarmInfoRsponse;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/17 上午10:10
 * 描述：报警信息界面
 ************************************************************************************/

public class MyFleetAlarmInfoActivity extends BaseActivity {
    private final String TAG = MyTaskDetailedActivity.class.getName();
    private static final String KEY_CARKEY = "CARKEY";
    private static final String KEY_CARCODE = "CARCODE";
    private final String STARTIME = " 00:00:00";
    private final String ENDTIME = " 23:59:59";

    private final int Reques_Code = 88;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.lv)
    PullToRefreshListView lv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.noData_view)
    FrameLayout noDataView;

    private DataRequestBase dataRequestBase;
    private GetAlarmInfoRequest requestBean;
    private MyFleetAlarmAdapter adapter;
    private List<mAlarmBean> list;
    private int pageInt = 1;

    private boolean isFresh;
    private String startTime;
    private String endTime;
    private String carCodeKey;
    private String carCode;

    public static void start(Context context, String Carkey, String CarCode) {
        Intent starter = new Intent(context, MyFleetAlarmInfoActivity.class);
        starter.putExtra(KEY_CARKEY, Carkey);
        starter.putExtra(KEY_CARCODE, CarCode);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_give_an_alarm_info);
        ButterKnife.bind(this);
        if (getIntent().getStringExtra(KEY_CARKEY) != null && getIntent().getStringExtra(KEY_CARCODE) != null) {
            carCodeKey = getIntent().getStringExtra(KEY_CARKEY);
            carCode = getIntent().getStringExtra(KEY_CARCODE);
        } else {
            DialogUtils.showNormalToast(Utils.getResourcesString(R.string.request_Fill));
        }
        initView();
    }

    private void initView() {
        titltTv.setText(carCode);
        rightLl.setVisibility(View.VISIBLE);
        addLl.setVisibility(View.GONE);

        startTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + STARTIME;
        endTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + ENDTIME;
        dataRequestBase = new DataRequestBase();
        requestBean = new GetAlarmInfoRequest();
        list = new ArrayList<>();
        adapter = new MyFleetAlarmAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isFresh = true;
                onRefreshs();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                onLoad();
            }
        });

        onRefreshs();

    }

    private void onRefreshs() {
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        requestData(LPSService.GetAlarmInfo_Request_Url);
    }

    private void onLoad() {
        pageInt++;
        dataRequestBase.Page = pageInt;
        requestData(LPSService.GetAlarmInfo_Request_Url);
    }

    private void requestData(String Url) {

        showLoading();
        requestBean.StartTime = startTime;
        requestBean.EndTime = endTime;
        requestBean.CarCode_Key = carCodeKey;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = requestBean; //JsonUtils.toJsonData(getDriverListRequest);
        MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                MLog.json(response.toString());
                GetAlarmInfoRsponse response1 = JsonUtils.parseData(response.toString(), GetAlarmInfoRsponse.class);
                Message msg = Message.obtain();
                if (response1.Success) {
                    msg.obj = response1.DataValue;
                    msg.what = Constants.REQUEST_SUCC;
                    myHandler.sendMessage(msg);
                } else {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                    myHandler.sendMessage(msg);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(msg);
            }
        });
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dissLoading();
            lv.onRefreshComplete();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    List<mAlarmBean> dataList = (List<mAlarmBean>) msg.obj;
                    if (dataList != null) {
                        if (isFresh) {
                            isFresh = false;
                            list.clear();
                        }

                        if (list != null && list.size() <= 0 && dataList != null && dataList.size() <= 0) {
                            //第一次加载的时候如果没有数据显示暂无数据
                            noDataView.setVisibility(View.VISIBLE);
                            lv.setVisibility(View.GONE);
                        } else {
                            noDataView.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);
                            list.addAll(dataList);
                            adapter.notifyDataSetChanged();
                        }

                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.DELETE_SUCC:
                    DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyFleetAlarmInfoActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    @OnClick({R.id.back_llayout, R.id.search_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.search_ll:
                SearchActivity.startActivity(this, Reques_Code, DateUtil.FORMAT_YMDHM, 30);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Reques_Code:
                    Bundle bundle = data.getExtras();
                    startTime = bundle.getString(SearchActivity.BEGIN_DATA);
                    endTime = bundle.getString(SearchActivity.END_DATA);
                    startTime = startTime + ":00";
                    endTime = endTime + ":00";
                    onRefreshs();
                    break;
            }
        }
    }
}
