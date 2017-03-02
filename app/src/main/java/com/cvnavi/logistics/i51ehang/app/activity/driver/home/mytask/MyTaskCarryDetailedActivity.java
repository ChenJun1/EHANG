package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskCarryDetailedAdpter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.exception.MyTaskExceptionInfoActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskDetailedOrderListBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.TaskDetaieldOrderListBeanResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.google.gson.Gson;
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
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 * 配载任务详情
 */
public class MyTaskCarryDetailedActivity extends BaseActivity {

    private final String TAG = MyTaskCarryDetailedActivity.class.getName();
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.CarCode_tv)
    TextView CarCodeTv;
    @BindView(R.id.Letter_Date_tv)
    TextView LetterDateTv;
    //    @BindView(R.id.CarCode_No_tv)
//    TextView CarCodeNoTv;
    @BindView(R.id.Line_Name_tv)
    TextView LineNameTv;
    @BindView(R.id.ticket_count_tv)
    TextView ticketCountTv;
    @BindView(R.id.Goods_Num_tv)
    TextView GoodsNumTv;
    @BindView(R.id.Bulk_Weight_tv)
    TextView BulkWeightTv;
    @BindView(R.id.Goods_Weight_tv)
    TextView GoodsWeightTv;
    @BindView(R.id.income_tv)
    TextView incomeTv;
    @BindView(R.id.Shuttle_Fee_tv)
    TextView ShuttleFeeTv;
    @BindView(R.id.profit_tv)
    TextView profitTv;
    @BindView(R.id.carry_operation_btn)
    Button carry_operation_btn;
    @BindView(R.id.my_task_detailed_list_lv)
    PullToRefreshListView myTaskDetailedListLv;
    @BindView(R.id.Traffic_Mode_tv)
    TextView TrafficModeTv;
    @BindView(R.id.Line_Type_tv)
    TextView LineTypeTv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.cur_task_tv)
    TextView curTaskTv;
    @BindView(R.id.operation_tow_btn)
    Button operationTowBtn;
    @BindView(R.id.empty_tv)
    TextView emptyTv;
    @BindView(R.id.TicketStatus_tv)
    TextView TicketStatusTv;
    @BindView(R.id.BoxCarCode_tv)
    TextView BoxCarCodeTv;
    @BindView(R.id.driver_tv)
    TextView driverTv;
    @BindView(R.id.driver_tel_tv)
    TextView driverTelTv;

    private TaskBean taskBean;

    private MyTaskCarryDetailedAdpter adpter = null;

    private List<TaskDetailedOrderListBean> detailedList;

    private ListView myListView;

    private SweetAlertDialog lodingDialog;

    private DataRequestBase dataRequestBase;

    private boolean isRefresh = true;
    private int pageInt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carry_detailed_list);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        titleTv.setText(Utils.getResourcesString(R.string.carry_detailed));
        operationBtn.setVisibility(View.VISIBLE);
        operationTowBtn.setVisibility(View.VISIBLE);
        operationTowBtn.setText(Utils.getResourcesString(R.string.exception));
        operationBtn.setText(Utils.getResourcesString(R.string.line_follow));
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dataRequestBase = new DataRequestBase();

        if (getIntent().getSerializableExtra(Constants.TASKINFO) != null) {
            taskBean = (TaskBean) getIntent().getSerializableExtra(Constants.TASKINFO);
        }

        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CONFIRM_ARRIVE+"")) {
            carry_operation_btn.setVisibility(View.VISIBLE);
        } else {
            carry_operation_btn.setVisibility(View.GONE);
        }

        if (getIntent().getStringExtra(Constants.DriverStowageStatisticsListActivity) != null) {
            operationTowBtn.setVisibility(View.GONE);
            carry_operation_btn.setVisibility(View.GONE);
        }


        if (taskBean != null) {
            setTitleValue();
            loadDataRequest(TMSService.DetailedListDetail_Request_Url);
        }

        myListView = myTaskDetailedListLv.getRefreshableView();
        detailedList = new ArrayList<>();
        //    detailedList.add(new TaskDetailedOrderListBean());
        adpter = new MyTaskCarryDetailedAdpter(detailedList, this);
        myListView.setAdapter(adpter);
        myTaskDetailedListLv.setMode(PullToRefreshBase.Mode.BOTH);
        myTaskDetailedListLv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                onRefreshs();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isRefresh = false;
                pageInt++;
                dataRequestBase.Page = pageInt;
                loadDataRequest(TMSService.DetailedListDetail_Request_Url);
            }
        });
    }

    public void onRefreshs() {
        isRefresh = true;
        pageInt = 1;
        dataRequestBase.Page = pageInt;
        loadDataRequest(TMSService.DetailedListDetail_Request_Url);
    }

    private void setTitleValue() {
//        if(!TextUtils.isEmpty(taskBean.TicketStatus)&&taskBean.TicketStatus.equals("已完成")){
//            operationTowBtn.setVisibility(View.GONE);
//        }
        SetViewValueUtil.setTextViewValue(CarCodeTv, taskBean.CarCode);
        SetViewValueUtil.setTextViewValue(LetterDateTv, taskBean.Leave_DateTime);
//        SetViewValueUtil.setTextViewValue(LetterDateTv, taskBean.Letter_Date);
//        SetViewValueUtil.setTextViewValue(CarCodeNoTv, taskBean.CarCode_No);
        SetViewValueUtil.setTextViewValue(GoodsNumTv, taskBean.Goods_Num, "件");
        SetViewValueUtil.setTextViewValue(BulkWeightTv,  ContextUtil.getDouble(taskBean.Bulk_Weight), "m³");
        SetViewValueUtil.setTextViewValue(GoodsWeightTv,  ContextUtil.getDouble(taskBean.Goods_Weight), "kg");

        SetViewValueUtil.setTextViewValue(incomeTv, taskBean.Ticket_Fee);
        SetViewValueUtil.setTextViewValue(ShuttleFeeTv, taskBean.Shuttle_Fee);
        SetViewValueUtil.setTextViewValue(profitTv, taskBean.Profit_Fee);
        SetViewValueUtil.setTextViewValue(LineNameTv, taskBean.Line_Name);
        LogUtil.d("-->> TicketStatusTv = " + taskBean.TicketStatus);
        SetViewValueUtil.setTextViewValue(TicketStatusTv, taskBean.TicketStatus);
        SetViewValueUtil.setTextViewValue(driverTv, taskBean.Driver);
        SetViewValueUtil.setTextViewValue(driverTelTv, taskBean.Driver_Tel);
        if (TextUtils.isEmpty(taskBean.BoxCarCode)) {
            SetViewValueUtil.setTextViewValue(BoxCarCodeTv, "无");
        } else {
            SetViewValueUtil.setTextViewValue(BoxCarCodeTv, taskBean.BoxCarCode);
        }
        SetViewValueUtil.setTextViewValue(ticketCountTv, taskBean.Ticket_Count, "票");
        SetViewValueUtil.setTextViewValue(TrafficModeTv, taskBean.Traffic_Mode.equals("整车")? "整" : "配");
        SetViewValueUtil.setTextViewValue(LineTypeTv, taskBean.Line_Type.equals("干线")  ? "干" : "支");
        if (!TextUtils.isEmpty(taskBean.TicketStatus) && taskBean.TicketStatus.equals("已完成")) {
            carry_operation_btn.setVisibility(View.GONE);
        }
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = taskBean.Letter_Oid; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
                TaskDetaieldOrderListBeanResponse response1 = JsonUtils.parseData(response.toString(), TaskDetaieldOrderListBeanResponse.class);
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
            if (lodingDialog != null) {
                lodingDialog.dismiss();
            }
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        List<TaskDetailedOrderListBean> dataList = (List<TaskDetailedOrderListBean>) msg.obj;
                        if (dataList != null) {
                            //判断是否存在未完成的货单
//                            carry_operation_btn.setVisibility(View.GONE);
//                            for (TaskDetailedOrderListBean taskDetailedOrderListBean : dataList) {
//                                LogUtil.d("-->>taskDetailedOrderListBean.complete_Status  = " + taskDetailedOrderListBean.complete_Status);
//                                if (taskDetailedOrderListBean.complete_Status.equals("0")) {
//                                    //   (1完成(隐藏)，0未完成（显示）)
//                                    carry_operation_btn.setVisibility(View.VISIBLE);
//                                    break;
//                                }
//                            }
                            if (isRefresh) {
                                detailedList.clear();
                                detailedList.addAll(dataList);
                            }
                        }
                        adpter.notifyDataSetChanged();
                        myTaskDetailedListLv.onRefreshComplete();
                        if (detailedList.size() > 0) {
                            emptyTv.setVisibility(View.GONE);
                        } else {
                            emptyTv.setVisibility(View.VISIBLE);
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyTaskCarryDetailedActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;


            }
        }
    };

    @OnClick({R.id.back_llayout, R.id.carry_operation_btn, R.id.operation_btn, R.id.operation_tow_btn})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.carry_operation_btn:
                intent = new Intent(this, MyTaskConfirmCarActivity.class);
                if (taskBean != null) {
                    intent.putExtra(Constants.TASKINFO, taskBean);
                }
                startActivity(intent);
                break;
            case R.id.operation_tow_btn:
                intent = new Intent(this, MyTaskExceptionInfoActivity.class);
                LogUtil.d("-->> taskben1");
                if (taskBean != null) {
                    LogUtil.d("-->> taskben2");
                    intent.putExtra(Constants.TASKINFO, taskBean);
                }
                startActivity(intent);
                break;
            case R.id.operation_btn:
                intent = new Intent(this, MyTaskLineFollowActivity.class);
                if (taskBean != null) {
                    intent.putExtra(Constants.TASKINFO, taskBean);
                }
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefreshs();
    }
}
