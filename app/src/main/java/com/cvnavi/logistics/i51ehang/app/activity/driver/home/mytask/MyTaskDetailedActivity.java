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
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.SignOrderActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskDetailedAdpter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.exception.MyTaskExceptionInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverHomeOrderDeatilActivity;
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
 * 派车任务详情
 */
public class MyTaskDetailedActivity extends BaseActivity implements MyTaskDetailedAdpter.MyTaskDetailedListener {

    private final String TAG = MyTaskDetailedActivity.class.getName();
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    //    @BindView(R.id.Letter_Oid_tv)
//    TextView LetterOidTv;
    @BindView(R.id.CarCode_tv)
    TextView CarCodeTv;
    @BindView(R.id.Driver_tv)
    TextView DriverTv;
    @BindView(R.id.Driver_Tel_tv)
    TextView DriverTelTv;
    @BindView(R.id.AllReceivableAccount_tv)
    TextView AllReceivableAccountTv;
    @BindView(R.id.Ticket_Count_tv)
    TextView TicketCountTv;
    @BindView(R.id.Goods_Num_tv)
    TextView GoodsNumTv;
    @BindView(R.id.Goods_Weight_tv)
    TextView GoodsWeightTv;
    @BindView(R.id.Bulk_Weight_tv)
    TextView BulkWeightTv;
    @BindView(R.id.Shuttle_Fee)
    TextView ShuttleFee;

    @BindView(R.id.my_task_detailed_list_lv)
    PullToRefreshListView myTaskDetailedListLv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.cur_task_tv)
    TextView curTaskTv;
    @BindView(R.id.empty_tv)
    TextView emptyTv;
    @BindView(R.id.Letter_Date_tv)
    TextView LetterDateTv;

    private TaskBean taskBean;

    private MyTaskDetailedAdpter adpter = null;

    private List<TaskDetailedOrderListBean> detailedList;

    private ListView myListView;

    private SweetAlertDialog lodingDialog;

    private DataRequestBase dataRequestBase;

    private boolean isRefresh = true;
    private int pageInt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task_detailed_list);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        operationBtn.setVisibility(View.VISIBLE);
        operationBtn.setText(Utils.getResourcesString(R.string.exception));
        titleTv.setText(Utils.getResourcesString(R.string.order_detailed));
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        myListView = myTaskDetailedListLv.getRefreshableView();
        detailedList = new ArrayList<>();
        // detailedList.add(new TaskDetailedOrderListBean());
        adpter = new MyTaskDetailedAdpter(detailedList, this, this);
        myListView.setAdapter(adpter);

        if (getIntent().getSerializableExtra(Constants.TASKINFO) != null) {
            taskBean = (TaskBean) getIntent().getSerializableExtra(Constants.TASKINFO);
            if (taskBean != null) {
                setTitleValue();
            }
            loadDataRequest(TMSService.DetailedListDetail_Request_Url);
        }
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
//            operationBtn.setVisibility(View.GONE);
//        }
        DriverTelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextUtil.callAlertDialog(DriverTelTv.getText().toString(), MyTaskDetailedActivity.this);
            }
        });
//        SetViewValueUtil.setTextViewValue(LetterOidTv, taskBean.Letter_Oid);
        SetViewValueUtil.setTextViewValue(CarCodeTv, taskBean.CarCode);
        SetViewValueUtil.setTextViewValue(DriverTv, taskBean.Driver);
        SetViewValueUtil.setTextViewValue(DriverTelTv, taskBean.Driver_Tel);
        SetViewValueUtil.setTextViewValue(AllReceivableAccountTv, taskBean.AllReceivableAccount);
        SetViewValueUtil.setTextViewValue(TicketCountTv,  taskBean.Ticket_Count, "票");
        SetViewValueUtil.setTextViewValue(GoodsNumTv,  taskBean.Goods_Num, "件");
        SetViewValueUtil.setTextViewValue(GoodsWeightTv,  ContextUtil.getDouble(taskBean.Goods_Weight), "kg");
        SetViewValueUtil.setTextViewValue(BulkWeightTv,  ContextUtil.getDouble(taskBean.Bulk_Weight), "m³");
        SetViewValueUtil.setTextViewValue(ShuttleFee,  ContextUtil.getDouble(taskBean.Shuttle_Fee), " 元");
        SetViewValueUtil.setTextViewValue(LetterDateTv, taskBean.Leave_DateTime);
//        SetViewValueUtil.setTextViewValue(LetterDateTv, taskBean.Letter_Date);
    }

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = taskBean.Letter_Oid; //JsonUtils.toJsonData
        // (getDriverListRequest);
        LogUtil.d("-->>" + dataRequestBase.toString());
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyTaskDetailedActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;


            }
        }
    };

    @Override
    public void itemClick(TaskDetailedOrderListBean bean) {
        if (bean != null) {
            Intent intent;
            if (!TextUtils.isEmpty(bean.ticketType) && bean.ticketType.equals("提货")) {
                MyTaskConfirmPickUpGoodsActivity.start(this,bean,taskBean);
//                intent = new Intent(this, MyTaskConfirmPickUpGoodsActivity.class);
//                intent.putExtra(Constants.TaskDetailedOrder, bean);
//                intent.putExtra(Constants.Letter_Oid, taskBean.Letter_Oid);
            } else {
                intent = new Intent(this, SignOrderActivity.class);
                intent.putExtra(Constants.TaskDetailedOrder, bean);
                intent.putExtra(Constants.TaskBean, taskBean);

                if (MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid.equals("G")) {
                    //如果是是司机，进入该界面要隐藏任务编号
                    intent.putExtra(SignOrderActivity.INTENT_FROM_TAG, SignOrderActivity.FROM_TASK_DETAIL);
                } else {
                    intent.putExtra(SignOrderActivity.INTENT_FROM_TAG, SignOrderActivity.FROM_ORDER_DETAIL);
                }
                startActivity(intent);
            }
        }
    }

    @Override
    public void toOrderdetai(TaskDetailedOrderListBean bean) {
        if (bean != null) {
//            MyOrderDetailAcitivity.startActivity(this, 1, bean.All_Ticket_No);
//            if (!TextUtils.isEmpty(bean.ticketType) && bean.ticketType.equals("提货")) {
//                //提货
//                DriverHomeOrderDeatilActivity.startActivity2(this, bean, bean.All_Ticket_No, taskBean, DriverHomeOrderDeatilActivity.INTENT_DATA_SIGN_ORDER);
//
//            } else {
////签收
//                DriverHomeOrderDeatilActivity.startActivity2(this, bean, bean.All_Ticket_No, taskBean, DriverHomeOrderDeatilActivity.INTENT_DATA_SIGN);
//
//            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefreshs();
    }

    @OnClick({R.id.back_llayout, R.id.operation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:
                MyTaskExceptionInfoActivity.start(this,taskBean);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
