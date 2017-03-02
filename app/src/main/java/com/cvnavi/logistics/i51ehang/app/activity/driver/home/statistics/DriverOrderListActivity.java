package com.cvnavi.logistics.i51ehang.app.activity.driver.home.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics.DriverOrderListAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverHomeOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.OrderDetailSelectActivty;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.EventSelect;
import com.cvnavi.logistics.i51ehang.app.bean.model.mGetOrederList;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetOrederStatisticsListRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetOrderStatisticsListResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午2:06
*描述：货单明细
************************************************************************************/

public class DriverOrderListActivity extends BaseActivity {


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
    private ArrayList<mGetOrederList> mList;
    private DriverOrderListAdapter mAdapter;

    private ListView actualListView;
    DataRequestBase requestBase;
    private boolean isRefresh = false;//是否刷新
    private int PageNum = 1;

    private SweetAlertDialog sweetAlertDialog = null;
    private String mStarTime;
    private String mEendTime;

    private Intent intent;

    GetOrederStatisticsListRequest request;
    private int sigId = 0;
    private int useId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ist);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init() {
        title.setText("货单明细");
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("筛选");
        noDataTv.setTextColor(0xffffffff);
        topBarLine.setVisibility(View.GONE);
        intent = getIntent();
        mStarTime = intent.getStringExtra("mStarTime");
        mEendTime = intent.getStringExtra("mEendTime");
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        mList = new ArrayList<>();
        requestBase = new DataRequestBase();
        request = new GetOrederStatisticsListRequest();
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(DriverOrderListActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                onRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                PageNum++;
                requestBase.Page = PageNum;
                requestHttp();
            }
        });
        mAdapter = new DriverOrderListAdapter(mList, this);
        actualListView = lv.getRefreshableView();
        actualListView.setAdapter(mAdapter);
        lv.setOnItemClickListener(itemClickListener);
        onRefresh();
    }


    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mList != null) {
                mGetOrederList Bean = mList.get(position - 1);
                Intent intent = new Intent(DriverOrderListActivity.this, DriverHomeOrderDeatilActivity.class);
                intent.putExtra(DriverHomeOrderDeatilActivity.ORDER_ID, Bean.All_Ticket_No);
                intent.putExtra(DriverHomeOrderDeatilActivity.FROM_TAG, DriverHomeOrderDeatilActivity.FROM_TAG_STATIC);
                startActivity(intent);
            }
        }
    };

    /**
     * 刷新获取新的数据
     */
    private void onRefresh() {
        isRefresh = true;
        PageNum = 1;
        requestBase.Page = PageNum;
        sweetAlertDialog.show();
        requestHttp();
    }

    /**
     * 请求数据
     * 获取网络数据
     */
    private void requestHttp() {
        request.BeginDate = mStarTime;
        request.EndDate = mEendTime;
        requestBase.DataValue = request;
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);

        MLog.json(GsonUtil.newInstance().toJsonStr(requestBase));
        VolleyManager.newInstance().PostJsonRequest("Tag", TMSService.GetReadNoTicket_Url, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        LogUtil.d("-->>onResponse============>>" + response.toString());
                        List<mGetOrederList> carLocusAnaly = null;

                        GetOrderStatisticsListResponse analysisRequest = JsonUtils.parseData(response.toString(), GetOrderStatisticsListResponse.class);

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
                    GetOrderStatisticsListResponse analysisRequest = (GetOrderStatisticsListResponse) msg.obj;
                    if (analysisRequest != null && analysisRequest.Success && analysisRequest.DataValue != null) {
                        List<mGetOrederList> mGather = analysisRequest.DataValue;
                        if (isRefresh) {
                            mList.clear();
                            isRefresh = false;
                        }

                        if (mList != null && mList.size() == 0 && mGather != null && mGather.size() == 0) {
                            noDataView.setVisibility(View.VISIBLE);
                            lv.setVisibility(View.GONE);
                        } else {
                            noDataView.setVisibility(View.GONE);
                            lv.setVisibility(View.VISIBLE);
                            mList.addAll(mGather);
                            if (mAdapter == null) {
                                mAdapter = new DriverOrderListAdapter(mList, DriverOrderListActivity.this);
                                lv.setAdapter(mAdapter);
                            } else {
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                    } else {
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverOrderListActivity.this, "获取数据失败", new CustomDialogListener() {
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverOrderListActivity.this, Utils.getResourcesString(R.string.request_error));
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
                OrderDetailSelectActivty.startActivty(this, sigId, useId);
                break;

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkReresh(EventSelect select) {
        if (select != null) {
            request.Ticket_Status = select.getUseState();
            request.DeliverStatus = select.getSignState();
        } else {
            request.Ticket_Status = "";
            request.DeliverStatus = "";
        }
        sigId = select.getSignId();
        useId = select.getUseId();
        mList.clear();
        mAdapter.notifyDataSetChanged();
        onRefresh();

    }


}
