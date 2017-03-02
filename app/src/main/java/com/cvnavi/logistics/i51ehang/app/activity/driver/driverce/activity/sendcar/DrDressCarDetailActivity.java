package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.adapter.DrDressCarDetailedAdpter;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.DressCarBean;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetDressResponseBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
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
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 装车清单
 */

public class DrDressCarDetailActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener{
    private static final String TAG = "DrDressCarDetailActivit";
    private static final String LETTEROID = "LETTEROID";//letteroid

    @BindView(R.id.back_llayout)
    LinearLayout mBackLlayout;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.empty_tv)
    TextView mEmptyTv;
    @BindView(R.id.my_task_off_the_stocks_pull_lv)
    PullToRefreshListView mMyTaskOffTheStocksPullLv;

    private DrDressCarDetailedAdpter adpter = null;

    private List<DressCarBean> detailedList;

    private ListView myListView;

    private SweetAlertDialog lodingDialog;

    private DataRequestBase dataRequestBase;

    private String LetterOid;

    private boolean isRefresh = true;//是否刷新

    private int pageInt = 1;//加载页面数

    private HandlerUtils.HandlerHolder mHandlerHolder;

    public static void start(Context context, String LetterOid) {
        Intent starter = new Intent(context, DrDressCarDetailActivity.class);
        starter.putExtra(LETTEROID, LetterOid);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_dress_car_detail_activity);
        ButterKnife.bind(this);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        initView();
    }

    private void initView() {
        mTitleTv.setText("装车清单");
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        if (TextUtils.isEmpty(getIntent().getStringExtra(LETTEROID)) == false) {
            LetterOid = getIntent().getStringExtra(LETTEROID);
        }
        dataRequestBase = new DataRequestBase();
        myListView = mMyTaskOffTheStocksPullLv.getRefreshableView();
        detailedList = new ArrayList<>();

        adpter = new DrDressCarDetailedAdpter(detailedList, this);
        myListView.setAdapter(adpter);
        loadDataRequest(DriverService.GetLoadingLetter_URL);

        //设置可以刷新加载属性
        mMyTaskOffTheStocksPullLv.setMode(PullToRefreshBase.Mode.BOTH);
        mMyTaskOffTheStocksPullLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefreshs();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = false;
                pageInt++;
                dataRequestBase.Page = pageInt;
                loadDataRequest(DriverService.GetLoadingLetter_URL);
            }
        });
    }

    public void onRefreshs() {
        isRefresh = true;
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        loadDataRequest(DriverService.GetLoadingLetter_URL);
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = LetterOid;
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
                GetDressResponseBean response1 = JsonUtils.parseData(response.toString(), GetDressResponseBean.class);
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
                Log.d(TAG, error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });

    }

    @OnClick(R.id.back_llayout)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                if (msg.obj != null) {
                    List<DressCarBean> dataList = (List<DressCarBean>) msg.obj;
                    if (dataList != null) {
                        if (isRefresh) {
                            detailedList.clear();
                            detailedList.addAll(dataList);
                        }
                    }
                    adpter.notifyDataSetChanged();
                    mMyTaskOffTheStocksPullLv.onRefreshComplete();
                    if (detailedList.size() > 0) {
                        mEmptyTv.setVisibility(View.GONE);
                    } else {
                        mEmptyTv.setVisibility(View.VISIBLE);
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
                DialogUtils.showMessageDialogOfDefaultSingleBtn(DrDressCarDetailActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
