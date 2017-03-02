package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter.SendCarMonitorAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetSendCarSelectRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarMonitorResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
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
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午4:01
 * 描述：发车监控界面（包含到下一个节点的进度圈）
 ************************************************************************************/


public class SendCarMonitorActivity extends BaseActivity implements MyOnClickItemListener {
    @BindView(R.id.noData_view)
    FrameLayout noDataView;
    @BindView(R.id.listview)
    PullToRefreshListView listview;
    @BindView(R.id.back_ll)
    LinearLayout backLl;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.top_bar_line)
    ImageView topBarLine;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.no_data_tv)
    TextView noDataTv;
    private SendCarMonitorAdapter adapter;
    private List<Object> list;
    private int pageNum = 1;
    private boolean isPullDown = false;
    private List<SendCarMonitorResponse.DataValueBean> dataList;

    private ArrayList<Integer> mileList;
    private String carcodeKey = "";
    private String lineoid = "";

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, SendCarMonitorActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car_monitor);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        topBarLine.setVisibility(View.GONE);
        title.setText("发车监控");
        dataList = new ArrayList<>();
        adapter = new SendCarMonitorAdapter(dataList, this, this);

        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("筛选");
        mileList = new ArrayList<>();
        listview.setAdapter(adapter);

        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(SendCarMonitorActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                isPullDown = true;
                pageNum = 1;
                getMyCarListInfo();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageNum++;
                getMyCarListInfo();
            }
        });

        getMyCarListInfo();
    }

    /**
     * 获取车辆信息
     * @param
     */
    private void getMyCarListInfo() {
        showLoading();
        GetSendCarSelectRequest request = new GetSendCarSelectRequest();
        request.CarCode_Key = carcodeKey;
        request.Line_Oid = lineoid;
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = request;

        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Page = pageNum;

        MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.CarTimeMonitorList_Tag, EmployeeService.CarTimeMonitorList_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                MLog.json(response.toString());
                SendCarMonitorResponse dataInfo = GsonUtil.newInstance().fromJson(response, SendCarMonitorResponse.class);
                Message msg = Message.obtain();
                if (dataInfo != null && dataInfo.isSuccess()) {
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = dataInfo;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                    msg.obj = dataInfo;
                }
                myHangler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                myHangler.sendMessage(msg);
            }
        });
    }

    private Handler myHangler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listview.onRefreshComplete();
            dissLoading();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        if (isPullDown) {
                            dataList.clear();
                            isPullDown = false;
                        }

                        SendCarMonitorResponse info = (SendCarMonitorResponse) msg.obj;
                        //判断是否是第一次加载进行初始化操作
                        if (dataList != null && dataList.size() == 0 && info.getDataValue() != null && info.getDataValue().size() == 0) {
                            noDataView.setVisibility(View.VISIBLE);
                            listview.setVisibility(View.GONE);
                            if (pageNum == 1 && isPullDown) {
                                rightTv.setTextColor(Utils.getResourcesColor(R.color.color_949494));
                                rightTv.setOnClickListener(null);
                            }
                        } else {
                            noDataView.setVisibility(View.GONE);
                            listview.setVisibility(View.VISIBLE);
                            dataList.addAll(info.getDataValue());
                            adapter.notifyDataSetChanged();
                            //有数据显示总车辆，取第一条数据
                            if (dataList != null && dataList.size() > 0) {
                                sum.setText("总车辆：" + dataList.get(0).getSum() + "辆");
                            } else {
                                sum.setText("总车辆：" + "--");
                            }
                            rightTv.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
                            rightTv.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SendCarMonitorSelectActivity.start(SendCarMonitorActivity.this);
                                }
                            });
                        }
                    } else {
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SendCarMonitorActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                            @Override
                            public void onDialogClosed(int closeType) {
                                finish();
                            }
                        });
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SendCarMonitorActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SendCarMonitorActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void myOnClickItem(int position, Object data) {
        SendCarMonitorResponse.DataValueBean info = (SendCarMonitorResponse.DataValueBean) data;
        if (info != null) {
            SendCarMonitorDetailActivity.start(this, info);
        }
    }

    @OnClick({R.id.back_ll, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void select(TimeEvent timeEvent) {
        if (timeEvent != null) {
            carcodeKey = timeEvent.getStartTime();
            lineoid = timeEvent.getEndTime();
            isPullDown = true;
            getMyCarListInfo();
        }

    }
}
