package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.DriverExceptionInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.DriverMyOrderLogisticsFollowActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrder;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrderDetail;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.OrederDetailedRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.OrederDetailedResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.OrderDetailPopWindow;
import com.google.gson.Gson;

import org.json.JSONObject;

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
 * Depict:货单明细(货主跳到货单明细)
 */
public class MyOrderDetailAcitivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener{
    public static final String ORDER_ID = "ORDER_ID";

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
    @BindView(R.id.order_num_tv)
    TextView orderNumTv;
    @BindView(R.id.fazhan_tv)
    TextView fazhanTv;
    @BindView(R.id.daozhan_tv)
    TextView daozhanTv;
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
    @BindView(R.id.consignee_tv)
    TextView consigneeTv;
    @BindView(R.id.consignee_num_tv)
    TextView consigneeNumTv;
    @BindView(R.id.receiving_tv)
    TextView receivingTv;
    @BindView(R.id.receiving_unit_tv)
    TextView receivingUnitTv;
    @BindView(R.id.consignor_tv)
    TextView consignorTv;
    @BindView(R.id.consignor_num_tv)
    TextView consignorNumTv;
    @BindView(R.id.consignor_unit_tv)
    TextView consignorUnitTv;
    @BindView(R.id.consignor_place_tv)
    TextView consignorPlaceTv;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.layout_sl)
    ScrollView layoutSl;
    @BindView(R.id.sign_text)
    TextView signText;
    @BindView(R.id.sign_time_text)
    TextView signTimeText;
    @BindView(R.id.sign_layout)
    LinearLayout signLayout;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.feed_bin_num_tv)
    TextView feedBinNumTv;
    @BindView(R.id.invoice_tv)
    TextView invoiceTv;
    private SweetAlertDialog loadingDialog;
    @BindView(R.id.sign_state_btn)
    Button signStateBtn;
    private String allTicketNo;
    private String goodsNum;
    @BindView(R.id.ysk_tv)
    TextView ysk_tv;
    private GetAppLoginResponse loginInfo;

    private mOrder bean;
    private HandlerUtils.HandlerHolder mHandlerHolder;
    public static void start(Context context, String TikeNo) {
        Intent starter = new Intent(context, MyOrderDetailAcitivity.class);
        starter.putExtra(ORDER_ID, TikeNo);
        context.startActivity(starter);
    }


    public static void startActivity(Activity activity, int requestCode, String orderId) {
        Intent intent = new Intent(activity, MyOrderDetailAcitivity.class);
        intent.putExtra(ORDER_ID, orderId);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_order_detail);
        ButterKnife.bind(this);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        bean = (mOrder) getIntent().getSerializableExtra(ORDER_ID);
        allTicketNo = bean.All_Ticket_No;
        loginInfo = MyApplication.getInstance().getLoginInfo();
        titltTv.setText("货单明细");
        contentLl.setVisibility(View.VISIBLE);

        if (loginInfo.DataValue.UserType_Oid.equals("E")) {
            rightTv.setText("物流跟踪");
            rightTv.setVisibility(View.GONE);
        } else {
            rightTv.setText("更多");
        }
        getInfo();
    }

    private void getInfo() {
        showDialog();
        OrederDetailedRequest dataValue = new OrederDetailedRequest();
        if (TextUtils.isEmpty(allTicketNo)) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyOrderDetailAcitivity.this, "无效查询码!", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
            return;
        } else {
            dataValue.All_Ticket_No = allTicketNo;
        }

        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = dataValue;
        dataRequestBase.User_Key = loginInfo.DataValue.User_Key;
        dataRequestBase.UserType_Oid = loginInfo.DataValue.UserType_Oid;
        dataRequestBase.Token = loginInfo.DataValue.Token;
        dataRequestBase.Company_Oid = loginInfo.DataValue.Company_Oid;

        LogUtil.d("-->> respon = " + new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetOrederDetailed_TAG, TMSService.GetOrederDetailed_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.d("-->> respon = " + jsonObject.toString());
                LoggerUtil.json(jsonObject.toString());
                OrederDetailedResponse info = GsonUtil.newInstance().fromJson(jsonObject, OrederDetailedResponse.class);
                Message msg = Message.obtain();
                if (info.Success) {
                    msg.obj = info;
                    msg.what = Constants.REQUEST_SUCC;
                } else {
                    msg.obj = info.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
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

    private void setInfo(mOrderDetail info) {
        goodsNum = info.Goods_Num;

        SetViewValueUtil.setTextViewValue(orderNumTv, info.Ticket_No);
        SetViewValueUtil.setTextViewValue(fazhanTv, info.SendStation);
        SetViewValueUtil.setTextViewValue(daozhanTv, info.ArrStation);
        SetViewValueUtil.setTextViewValue(pinmingTv, info.Goods_Breed);
        SetViewValueUtil.setTextViewValue(baozhuangTv, info.Goods_Casing);
        SetViewValueUtil.setTextViewValue(jianshuTv, ContextUtil.getDouble(info.Goods_Num),"件");
        SetViewValueUtil.setTextViewValue(zhongliangTv, ContextUtil.getDouble(info.Goods_Weight),"kg");
        SetViewValueUtil.setTextViewValue(consignorTv, info.SendMan_Name);
        SetViewValueUtil.setTextViewValue(consignorNumTv, info.SendMan_Tel);
        SetViewValueUtil.setTextViewValue(consignorUnitTv, info.SendMan_Org);
        SetViewValueUtil.setTextViewValue(consignorPlaceTv, info.SendMan_Address);
        SetViewValueUtil.setTextViewValue(consigneeTv, info.ReceiveMan_Name);
        SetViewValueUtil.setTextViewValue(consigneeNumTv, info.ReceiveMan_Tel);
        SetViewValueUtil.setTextViewValue(receivingTv, info.ReceiveMan_Org);
        SetViewValueUtil.setTextViewValue(receivingUnitTv, info.ReceiveMan_Address);
        SetViewValueUtil.setTextViewValue(signStateBtn, info.Distribution_Status);
        SetViewValueUtil.setTextViewValue(tijiTv,ContextUtil.getDouble(info.Bulk_Weight),"m³");
        SetViewValueUtil.setTextViewValue(ysk_tv, info.Total_Fee);
        SetViewValueUtil.setTextViewValue(signText, info.Distribution_Status);
        SetViewValueUtil.setTextViewValue(signTimeText, info.Recent_Goods_DateTime);
        SetViewValueUtil.setTextViewValue(feedBinNumTv,info.Ticket_Note);

    }

    @OnClick({R.id.back_llayout, R.id.content_ll, R.id.sign_layout,R.id.consignee_num_tv,R.id.consignor_num_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.consignee_num_tv:
                ContextUtil.callAlertDialog(consigneeNumTv.getText().toString(), MyOrderDetailAcitivity.this);
                break;
            case R.id.consignor_num_tv:
                ContextUtil.callAlertDialog(consignorNumTv.getText().toString(), MyOrderDetailAcitivity.this);
                break;
            case R.id.sign_layout:
                DriverMyOrderLogisticsFollowActivity.startActivity(MyOrderDetailAcitivity.this, allTicketNo, goodsNum);
                break;
            case R.id.content_ll:

                if (loginInfo.DataValue.UserType_Oid.equals("E")) {
                    DriverMyOrderLogisticsFollowActivity.startActivity(MyOrderDetailAcitivity.this, allTicketNo, goodsNum);
                } else {
                    //跳转到物流跟踪
                    OrderDetailPopWindow popWindow = new OrderDetailPopWindow(MyOrderDetailAcitivity.this);
                    popWindow.showLocation(R.id.content_ll);
                    popWindow.setOnItemClickListener(new OrderDetailPopWindow.OnItemClickListener() {
                        @Override
                        public void onClick(OrderDetailPopWindow.MENUITEM item, String str) {

                            if (str.equals("1")) {
                                DriverMyOrderLogisticsFollowActivity.startActivity(MyOrderDetailAcitivity.this, allTicketNo, goodsNum);
                            } else {
                                DriverExceptionInfoActivity.startActivity(MyOrderDetailAcitivity.this, allTicketNo);
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void handlerMessage(Message msg) {
        dissDialog();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                OrederDetailedResponse info = (OrederDetailedResponse) msg.obj;
                if (info != null && info.DataValue != null && info.DataValue.size() > 0) {
                    setInfo(info.DataValue.get(0));
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
                DialogUtils.showMessageDialogOfDefaultSingleBtn(MyOrderDetailAcitivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
