package com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.SignOrderActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.order.OrderFollowActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrderDetail;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.OrederDetailedRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.OrederDetailedResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
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

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午2:02
*描述：货单详情
************************************************************************************/

public class DriverHomeOrderDeatilActivity extends BaseActivity {
    public static final String ORDER_ID = "ORDER_ID";
    public static final String INTENT_ORDER_ID = "INTENT_ORDER_ID";
    public static final String INTENT_BENGINING = "INTENT_BENGINING";
    public static final String INTENT_ARRIVING = "INTENT_ARRIVING";
    public static final String INTENT_DATA = "INTENT_DATA";
    public static final String INTENT_ALL_TICKET_NO = "INTENT_ALL_TICKET_NO";
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

    public static final int FROM_TAG_STATIC = 12;//从货单列表中跳转的
    public static final String FROM_TAG = "FROM_TAG";

    private SweetAlertDialog loadingDialog;
    private String All_Ticket_No = null;
    private mOrderDetail orderInfo;
    private String goodsNum;

    private void setInfo(mOrderDetail info) {
        orderNumTv.setText(info.Ticket_No);//货单号
        fazhanTv.setText(info.SendStation);
        daozhanTv.setText(info.ArrStation);
        pinmingTv.setText(info.Goods_Breed);
        jianshuTv.setText(String.format(Utils.getResourcesString(R.string.my_order_jian), info.Goods_Num));
        goodsNum = info.Goods_Num;
        zhongliangTv.setText(String.format(Utils.getResourcesString(R.string.my_order_kg), info.Goods_Weight));
        baozhuangTv.setText(info.Goods_Casing);


        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CHECK_SEND_MAN_INFO + "")) {
            /**
             * 发货人信息
             */
            consignorTv.setText(info.SendMan_Name);
            consignorNumTv.setText(info.SendMan_Tel);
            consignorNumTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContextUtil.callAlertDialog(consignorNumTv.getText().toString(), DriverHomeOrderDeatilActivity.this);
                }
            });

            consignorUnitTv.setText(info.SendMan_Org);
            consignorPlaceTv.setText(info.SendMan_Address);
        } else {
            consignorTv.setText("*");
            consignorNumTv.setText("*");
            consignorUnitTv.setText("*");
            consignorPlaceTv.setText("*");
        }


        reconmendTv.setText(info.SendGoods_Ask);
        consigneeTv.setText(info.ReceiveMan_Name);
        consigneeNumTv.setText(info.ReceiveMan_Tel);
        consigneeNumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextUtil.callAlertDialog(consigneeNumTv.getText().toString(), DriverHomeOrderDeatilActivity.this);

            }
        });
        receivingTv.setText(info.ReceiveMan_Org);
        receivingUnitTv.setText(info.ReceiveMan_Address);

        feedBinNumTv.setText(info.Ticket_Note);
        signText.setText(info.Distribution_Status);
        signTimeText.setText(info.Recent_Goods_DateTime);
        if (info.Bulk_Weight != null) {
            tijiTv.setText(String.format(Utils.getResourcesString(R.string.my_order_m), info.Bulk_Weight));
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


//        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CAR_EXCEPTION_UPLOAD + "") || Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_ORDER_EXCEPTION_UPLOAD + "")) {
//            //有异常上报权限显示
//            excptionBtn.setVisibility(View.VISIBLE);
//        } else {
//            excptionBtn.setVisibility(View.GONE);
//        }
//
//        if (!TextUtils.isEmpty(info.Deliver_Status_Oid)) {
//            if (info.Deliver_Status_Oid.equals("2")) {
//                signOk.setText("已签收");
//                excptionBtn.setVisibility(View.GONE);
//                signOk.setOnClickListener(null);
//            } else {
//                signOk.setText("确认签收");
//            }
//        } else {
//            signOk.setText("确认签收");
//        }
//
//        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CONFIRM_SIGN + "") || Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CONFIRM_TIHUO + "")) {
//            //有确认签收或者有确认提货的权限显示
//            signOk.setVisibility(View.VISIBLE);
//        } else {
//            signOk.setVisibility(View.GONE);
//        }


        excptionBtn.setVisibility(View.GONE);
        signOk.setVisibility(View.GONE);
    }

    public static void startActivity(Activity activity, int requestCode, String orderId) {
        Intent intent = new Intent(activity, DriverHomeOrderDeatilActivity.class);
        intent.putExtra(ORDER_ID, orderId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home_order_detail);
        ButterKnife.bind(this);
        All_Ticket_No = getIntent().getStringExtra(ORDER_ID);
        if (getIntent().getIntExtra(FROM_TAG, 0) == FROM_TAG_STATIC) {
            bottomLl.setVisibility(View.GONE);
        } else {
            bottomLl.setVisibility(View.VISIBLE);
        }

        titltTv.setText("货单详情");
        contentLl.setVisibility(View.VISIBLE);
        rightTv.setText("更多");
    }

    /**
     * 获取货单信息
     * 请求数据
     */
    private void getInfo() {
        showDialog();
        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        OrederDetailedRequest dataValue = new OrederDetailedRequest();
        if (TextUtils.isEmpty(All_Ticket_No)) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverHomeOrderDeatilActivity.this, "无效查询码", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
            return;
        } else {
            dataValue.All_Ticket_No = All_Ticket_No;
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
        dataRequestBase.ActionSystem = "APP";
        VolleyManager.newInstance().PostJsonRequest(TMSService.OrederDetailed_Request_Url, TMSService.OrederDetailed_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.d("-->> respon = " + jsonObject.toString());
                OrederDetailedResponse info = GsonUtil.newInstance().fromJson(jsonObject, OrederDetailedResponse.class);
                Message msg = Message.obtain();
                if (info.Success) {
                    msg.obj = info;
                    msg.what = Constants.REQUEST_SUCC;
                } else {
                    msg.obj = info.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                }
                myHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(message);
            }
        });
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

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dissDialog();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    OrederDetailedResponse info = (OrederDetailedResponse) msg.obj;
                    if (info.DataValue != null && info.DataValue.size() > 0) {
                        orderInfo = info.DataValue.get(0);
                        setInfo(orderInfo);
                        layoutSl.setVisibility(View.VISIBLE);
                    } else {
                        DialogUtils.showFailToast(Utils.getResourcesString(R.string.check_data_empty));
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    String infoError = (String) msg.obj;
                    if (infoError != null) {
                        DialogUtils.showFailToast(infoError);
                    } else {
                        DialogUtils.showFailToast(Utils.getResourcesString(R.string.get_data_fail));
                    }
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverHomeOrderDeatilActivity
                            .this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };


    @OnClick({R.id.sign_ok, R.id.excption_btn, R.id.content_ll, R.id.back_llayout, R.id.sign_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_ok:
                //正常签收
                SignOrderActivity.startActivty(this, orderInfo, SignOrderActivity.REQUEST_CODE, SignOrderActivity.FROM_ORDER_DETAIL);
                break;
            case R.id.excption_btn:
                DriverUploadPhotoActivity.startActivity(this, All_Ticket_No);
                break;
            case R.id.content_ll:
                ArrayList<String> item = new ArrayList<>();
                item.add("异常信息");
                MyPopWindow popWindow = new MyPopWindow(DriverHomeOrderDeatilActivity.this, item);
                popWindow.showLocation(R.id.content_ll);
                popWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        switch (pos) {
                            case 0:
//                                OrderExceptionActivity.startActivity(DriverHomeOrderDeatilActivity.this, All_Ticket_No);
//                                DriverExceptionInfoActivity.startActivity(DriverHomeOrderDeatilActivity.this, All_Ticket_No);
                                Dr_OrderExceptionInfoActivity.startActivity(DriverHomeOrderDeatilActivity.this, All_Ticket_No);
                                break;
                        }

                    }
                });

                break;
            case R.id.back_llayout:
                finish();
                break;
            case R.id.sign_layout:
                OrderFollowActivity.startActivity(DriverHomeOrderDeatilActivity.this, All_Ticket_No, goodsNum, orderInfo.Org_Name);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getInfo();
    }
}
