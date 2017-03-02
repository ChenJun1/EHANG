package com.cvnavi.logistics.i51ehang.app.activity.driver.home.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics.DriverStowageStatisticsListAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.SendCarDetailSelectActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.EventSelect;
import com.cvnavi.logistics.i51ehang.app.bean.model.mGetStowageStatisticsList;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetStowageStatisticsListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetStowageStatisticsListResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.popupmenu.DriverStowageStatisticsListPopuMenu;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午2:08
*描述：发车明细
************************************************************************************/

public class DriverStowageStatisticsListActivity extends BaseActivity {

    @BindView(R.id.back_ll)
    LinearLayout backLl;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.top_bar_line)
    ImageView topBarLine;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    @BindView(R.id.noData_view)
    FrameLayout noDataView;
    @BindView(R.id.lv)
    PullToRefreshListView lv;

    private ArrayList<mGetStowageStatisticsList> mList;
    private DriverStowageStatisticsListAdapter mAdapter;
    private GetStowageStatisticsListRequest request = null;

    DataRequestBase requestBase;
    private boolean isRefresh = false;//是否刷新
    private String mStarTime;
    private String mEendTime;
    private Intent intent;


    private int signId = 0;
    private int useId = 0;
    private int pageInt = 1;//分页num
    private SweetAlertDialog sweetAlertDialog = null;
    private DriverStowageStatisticsListPopuMenu popuMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stowage_statistics_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        popuMenu = new DriverStowageStatisticsListPopuMenu(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init() {
        title.setText("发车明细");
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("筛选");
        topBarLine.setVisibility(View.GONE);
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        intent = getIntent();
        mStarTime = intent.getStringExtra("mStarTime");
        mEendTime = intent.getStringExtra("mEendTime");
        mList = new ArrayList<>();

        requestBase = new DataRequestBase();
        request = new GetStowageStatisticsListRequest();
        lv.setAdapter(mAdapter);

        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(DriverStowageStatisticsListActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isRefresh = true;
                pageInt = 1;
                //执行刷新
                requestHttp();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageInt++;
                //执行刷新
                requestHttp();
            }
        });
        requestHttp();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestHttp() {
        sweetAlertDialog.show();
        request.BeginDate = mStarTime;
        request.EndDate = mEendTime;

        requestBase.DataValue = request;
        requestBase.Page = pageInt;
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        MLog.json(GsonUtil.newInstance().toJson(requestBase).toString());
        VolleyManager.newInstance().PostJsonRequest("Tag", TMSService.GetStowageStatisticsList_Request_Url, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MLog.json(response.toString());
                        GetStowageStatisticsListResponse analysisRequest = JsonUtils.parseData(response.toString(), GetStowageStatisticsListResponse.class);

                        Message msg = Message.obtain();
                        if (analysisRequest.Success) {
                            msg.what = Constants.REQUEST_SUCC;
                            msg.obj = analysisRequest;
                            mHandler.sendMessage(msg);
                        } else {
                            msg.what = Constants.REQUEST_FAIL;
                            mHandler.sendMessage(msg);

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Message msg = Message.obtain();
                        msg.what = Constants.REQUEST_ERROR;
                        mHandler.sendMessage(msg);
                    }
                });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            sweetAlertDialog.dismiss();
            lv.onRefreshComplete();
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    GetStowageStatisticsListResponse analysisRequest = (GetStowageStatisticsListResponse) msg.obj;

                    if (analysisRequest != null && analysisRequest.Success && analysisRequest.DataValue != null) {
                        if (isRefresh) {
                            isRefresh = false;
                            mList.clear();
                        }

                        if (mList != null && mList.size() == 0 && analysisRequest.DataValue != null && analysisRequest.DataValue.size() == 0) {
                            noDataView.setVisibility(View.VISIBLE);
                            lv.setVisibility(View.GONE);
                        } else {
                            noDataView.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);
                            mList.addAll(analysisRequest.DataValue);
                            if (mAdapter == null) {
                                mAdapter = new DriverStowageStatisticsListAdapter(mList, DriverStowageStatisticsListActivity.this);
                                lv.setAdapter(mAdapter);
                            } else {
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    } else {
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverStowageStatisticsListActivity.this, "获取数据失败", new CustomDialogListener() {
                            @Override
                            public void onDialogClosed(int closeType) {
                                finish();
                            }
                        });
                    }


                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverStowageStatisticsListActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    @OnClick({R.id.back_ll, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.right_tv:
                SendCarDetailSelectActivity.startActivity(this, signId, useId);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkReresh(EventSelect select) {
        isRefresh = true;
        if (select != null) {
            request.DeliverStatus = select.getUseState();
            request.Traffic_Mode = select.getSignState();
            signId = select.getSignId();
            useId = select.getUseId();
        } else {
            request.Traffic_Mode = "";
            request.DeliverStatus = "0";
            signId = 0;
            useId = 0;
        }

        mList.clear();
        mAdapter.notifyDataSetChanged();
        requestHttp();
    }


}
