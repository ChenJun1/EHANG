package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.Dr_OrderExceptionInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.DriverMyOrderLogisticsFollowActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverUploadPhotoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.OrderDetailBean;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.Dr_OrederDetailedResponse;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskDetailedOrderListBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrderDetail;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.OrederDetailedRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;

import org.json.JSONObject;

import java.util.ArrayList;

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
 * Depict: 货单详情 页面
 */
public class DriverOrderDeatilActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener {
    public static final String ORDER_ID = "ORDER_ID";
    public static final String INTENT_DATA_INFO = "INTENT_DATA_INFO";
    public static final String INTENT_DATA_SIGN_DATA = "INTENT_DATA_SIGN";
    public static final int INTENT_DATA_SIGN_ORDER = 0;//提货
    public static final int INTENT_DATA_SIGN = 1;//（配载）签收
    public static final int INTENT_DATA_DEATAIL = 3;//(正常签收)
    @BindView(R.id.sign_text)
    TextView signText;
    @BindView(R.id.sign_time_text)
    TextView signTimeText;
    @BindView(R.id.sign_layout)
    LinearLayout signLayout;
    @BindView(R.id.tel_ll)
    LinearLayout telLl;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.order_num_tv)
    TextView orderNumTv;
    @BindView(R.id.fazhan_tv)
    TextView fazhanTv;
    @BindView(R.id.daozhan_tv)
    TextView daozhanTv;
    @BindView(R.id.consignee_tv)
    TextView consigneeTv;
    @BindView(R.id.consignee_num_tv)
    TextView consigneeNumTv;
    @BindView(R.id.receiving_tv)
    TextView receivingTv;
    @BindView(R.id.receiving_unit_tv)
    TextView receivingUnitTv;
    @BindView(R.id.reconmend_tv)
    TextView reconmendTv;
    @BindView(R.id.consignor_tv)
    TextView consignorTv;
    @BindView(R.id.consignor_num_tv)
    TextView consignorNumTv;
    @BindView(R.id.consignor_unit_tv)
    TextView consignorUnitTv;
    @BindView(R.id.consignor_place_tv)
    TextView consignorPlaceTv;
    @BindView(R.id.pinming_tv)
    TextView pinmingTv;
    @BindView(R.id.baozhuang_tv)
    TextView baozhuangTv;
    @BindView(R.id.jianshu_tv)
    TextView jianshuTv;
    @BindView(R.id.zhongliang_tv)
    TextView zhongliangTv;
    @BindView(R.id.tiji_tv)
    TextView tijiTv;
    @BindView(R.id.lack_tv)
    TextView lackTv;
    @BindView(R.id.feed_bin_num_tv)
    TextView feedBinNumTv;
    @BindView(R.id.invoice_tv)
    TextView invoiceTv;
    @BindView(R.id.sign_ok)
    Button signOk;
    @BindView(R.id.excption_btn)
    Button excptionBtn;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    @BindView(R.id.layout_sl)
    ScrollView layoutSl;
    private int signType = INTENT_DATA_SIGN;
    private HandlerUtils.HandlerHolder mHandlerHolder;

    private SweetAlertDialog loadingDialog;
    private String All_Ticket_No = null;
    private OrderDetailBean orderInfo;
    private TaskDetailedOrderListBean detaiBeance;

    private TaskBean taskBeance;

    private String goodsNum;

    private MyPopWindow mMyPopWindow;

    public static void startActivity(Activity activity, int requestCode, String orderId, mOrderDetail info) {
        Intent intent = new Intent(activity, DriverOrderDeatilActivity.class);
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(INTENT_DATA_INFO, info);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void startActivity2(Context activity, TaskDetailedOrderListBean bean, String orderId, TaskBean taskBean, int type) {
        Intent intent = new Intent(activity, DriverOrderDeatilActivity.class);
        intent.putExtra(ORDER_ID, orderId);
        intent.putExtra(INTENT_DATA_SIGN_DATA, type);
        intent.putExtra(Constants.TaskDetailedOrder, bean);
        intent.putExtra(Constants.TaskBean, taskBean);
        activity.startActivity(intent);
    }

    private ArrayList<String> mStringList = new ArrayList<String>() {{
        add("异常信息");
    }};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_order_detail_layout);
        ButterKnife.bind(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        init();

        All_Ticket_No = getIntent().getStringExtra(ORDER_ID);

        titltTv.setText("货单详情");
        contentLl.setVisibility(View.VISIBLE);
        rightTv.setText("更多");
    }

    public void init() {
        mMyPopWindow = new MyPopWindow(this, mStringList);
        mMyPopWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                switch (pos) {
                    case 0:
                        Dr_OrderExceptionInfoActivity.startActivity(DriverOrderDeatilActivity.this, All_Ticket_No);
                        break;
                    default:
                        Dr_OrderExceptionInfoActivity.startActivity(DriverOrderDeatilActivity.this, All_Ticket_No);
                        break;
                }
            }
        });
    }

    private void getInfo() {
        showDialog();
        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        OrederDetailedRequest dataValue = new OrederDetailedRequest();
        if (TextUtils.isEmpty(All_Ticket_No)) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverOrderDeatilActivity.this, "无效查询码", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
            return;
        } else {
            dataValue.All_Ticket_No = All_Ticket_No;
            dataValue.Driver_Tel = info.DataValue.User_Tel;
        }

        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = dataValue;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Org_Code = info.DataValue.Org_Code;
        dataRequestBase.Org_Name = info.DataValue.Org_Name;
        dataRequestBase.Limit = 1;
        dataRequestBase.Page = 2;
        MLog.print("-->> All_Ticket_No=" + All_Ticket_No);
        VolleyManager.newInstance().PostJsonRequest(TMSService.OrederDetailed_Request_Url, TMSService.OrederDetailed_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.d("-->> respon = " + jsonObject.toString());
                Dr_OrederDetailedResponse info = GsonUtil.newInstance().fromJson(jsonObject, Dr_OrederDetailedResponse.class);
                Message msg = Message.obtain();
                if (info == null) {
                    msg.obj = info.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                } else {
                    if (info.Success) {
                        msg.obj = info;
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = info.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                    }
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(message);
            }
        });
    }

    private void setInfo(OrderDetailBean info) {

        goodsNum = info.Goods_Num;//件数

        SetViewValueUtil.setTextViewValue(baozhuangTv, info.Goods_Casing);
        SetViewValueUtil.setTextViewValue(pinmingTv, info.Goods_Breed);
        SetViewValueUtil.setTextViewValue(fazhanTv, info.SendStation);
        SetViewValueUtil.setTextViewValue(daozhanTv, info.ArrStation);
        SetViewValueUtil.setTextViewValue(orderNumTv, info.Ticket_No);//货单号

        SetViewValueUtil.setTextViewValue(jianshuTv, ContextUtil.getDouble(info.Goods_Num), "件");
        SetViewValueUtil.setTextViewValue(zhongliangTv, ContextUtil.getDouble(info.Goods_Weight), "kg");

        /**
         * 新增以恒需求，司机不可查看发货人信息
         */
//        SetViewValueUtil.setTextViewValue(consignorTv, info.SendMan_Name);
//        SetViewValueUtil.setTextViewValue(consignorNumTv, info.SendMan_Tel);
//        SetViewValueUtil.setTextViewValue(consignorUnitTv, info.SendMan_Org);
//        SetViewValueUtil.setTextViewValue(consignorPlaceTv, info.SendMan_Address);
        SetViewValueUtil.setTextViewValue(reconmendTv, info.SendGoods_Ask);
        SetViewValueUtil.setTextViewValue(receivingUnitTv, info.ReceiveMan_Address);
        SetViewValueUtil.setTextViewValue(receivingTv, info.ReceiveMan_Org);
        SetViewValueUtil.setTextViewValue(consigneeNumTv, info.ReceiveMan_Tel);
        SetViewValueUtil.setTextViewValue(consigneeTv, info.ReceiveMan_Name);

        feedBinNumTv.setText(info.Ticket_Note);
        signText.setText(info.Distribution_Status);

        if (!TextUtils.isEmpty(info.Complete_Status) && info.Complete_Status.equals("0")) {
            excptionBtn.setVisibility(View.VISIBLE);
            signOk.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(info.Operate_Code) && info.Operate_Code.equals("EB")) {
                signType = INTENT_DATA_DEATAIL;
                signOk.setText("确认签收");
            } else {
                signType = INTENT_DATA_SIGN_ORDER;
                signOk.setText("确认提货");
            }
        } else {
            excptionBtn.setVisibility(View.GONE);
            signOk.setVisibility(View.GONE);
        }
        signTimeText.setText(info.Recent_Goods_DateTime);
        if (info.Bulk_Weight != null) {
            SetViewValueUtil.setTextViewValue(tijiTv, ContextUtil.getDouble(info.Bulk_Weight), "m³");
        } else {
            tijiTv.setText(String.format(Utils.getResourcesString(R.string.my_order_m), 0));
        }

        if (info.Lack_Num != null) {
            if (!TextUtils.isEmpty(info.Lack_Num) && info.Lack_Num.equals("0")) {
                lackTv.setText("否");
            } else {
                lackTv.setText(String.format("缺少%1$s件", info.Lack_Num));
            }
        } else {
            lackTv.setText("否");
        }
    }

    private void initData() {
        detaiBeance = new TaskDetailedOrderListBean();
        detaiBeance.All_Ticket_No = orderInfo.All_Ticket_No;
        detaiBeance.Ticket_No = orderInfo.Ticket_No;
        detaiBeance.Org_Code = orderInfo.Org_Code;
        detaiBeance.Goods_Weight = orderInfo.Goods_Weight;
        detaiBeance.Goods_Num = orderInfo.Goods_Num;
        detaiBeance.Goods_Breed = orderInfo.Goods_Breed;
        detaiBeance.Bulk_Weight = orderInfo.Bulk_Weight;
        detaiBeance.YSK_Fee = orderInfo.YSK_Fee;
        detaiBeance.ReceiveMan_Name = orderInfo.ReceiveMan_Name;
        taskBeance = new TaskBean();
        taskBeance.Letter_Oid = orderInfo.Letter_Oid;
        taskBeance.YSK_Fee = orderInfo.YSK_Fee;
        taskBeance.Operate_Org = orderInfo.Org_Code;
        taskBeance.Goods_Weight = orderInfo.Goods_Weight;
        taskBeance.Goods_Num = orderInfo.Goods_Num;
        taskBeance.Bulk_Weight = orderInfo.Bulk_Weight;

    }

    @OnClick({R.id.sign_ok, R.id.excption_btn, R.id.content_ll, R.id.back_llayout, R.id.sign_layout, R.id.consignee_num_tv, R.id.consignor_num_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_ok:
                Intent intent;
                if (signType == INTENT_DATA_DEATAIL) {
                    intent = new Intent(this, Dr_SignOrderActivity.class);
                    intent.putExtra(Constants.TaskDetailedOrder, detaiBeance);
                    intent.putExtra(Constants.TaskBean, taskBeance);

                    if (MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid.equals("G")) {
                        //如果是是司机，进入该界面要隐藏任务编号
                        intent.putExtra(Dr_SignOrderActivity.INTENT_FROM_TAG, Dr_SignOrderActivity.FROM_TASK_DETAIL);
                    } else {
                        intent.putExtra(Dr_SignOrderActivity.INTENT_FROM_TAG, Dr_SignOrderActivity.FROM_ORDER_DETAIL);
                    }
                    startActivity(intent);
                } else if (signType == INTENT_DATA_SIGN_ORDER) {
                    //确认提货
//
                    Dr_ConfirmPickUpGoodsActivity.start(this, detaiBeance, taskBeance);
                }

                break;
            case R.id.excption_btn:
                DriverUploadPhotoActivity.startActivity(this, All_Ticket_No);
                break;
            case R.id.content_ll:
                mMyPopWindow.showLocation(R.id.content_ll);

                break;
            case R.id.back_llayout:
                finish();
                break;
            case R.id.sign_layout:
                DriverMyOrderLogisticsFollowActivity.startActivity(DriverOrderDeatilActivity.this, All_Ticket_No, goodsNum);
                break;
            case R.id.consignee_num_tv:
                if (!TextUtils.isEmpty(consigneeNumTv.getText())) {
                    ContextUtil.callAlertDialog(consigneeNumTv.getText().toString(), this);
                }
                break;
            case R.id.consignor_num_tv:
                if (!TextUtils.isEmpty(consignorNumTv.getText())) {
                    ContextUtil.callAlertDialog(consignorNumTv.getText().toString(), this);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == SignOrderActivity.REQUEST_CODE) {
//            signOk.setText("已签收");
//            signOk.setOnClickListener(null);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
    }

    private void showDialog() {
        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }
        loadingDialog.show();
    }

    private void dissDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        dissDialog();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                Dr_OrederDetailedResponse info = (Dr_OrederDetailedResponse) msg.obj;
                if (info.DataValue != null && info.DataValue.size() > 0) {
                    orderInfo = info.DataValue.get(0);
                    if (orderInfo != null) {
                        setInfo(orderInfo);
                        initData();
                    }

                    layoutSl.setVisibility(View.VISIBLE);
                } else {
                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.check_data_empty));
                }
                break;
            case Constants.REQUEST_FAIL:
                String infoError = (String) msg.obj;
                if (infoError != null) {
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverOrderDeatilActivity.this, infoError, new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                } else {
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverOrderDeatilActivity.this, "无效查询码", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            finish();
                        }
                    });
                }
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverOrderDeatilActivity
                        .this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
