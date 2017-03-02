package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.MyTaskDetailedActivity;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.request.SetMessageReadRequest;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.LogisticsFollowBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.LogisticsFollowNoteBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetLogisticsFollowRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetLogisicsFollowResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;

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
*创建时间：2017/1/17 下午1:07
*描述：查看物流
************************************************************************************/

public class DriverMyOrderLogisticsFollowActivity extends BaseActivity {

    private final String TAG = MyTaskDetailedActivity.class.getName();
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.Letter_Status_tv)
    TextView LetterStatusTv;
    @BindView(R.id.Letter_Oid_tv)
    TextView LetterOidTv;
    @BindView(R.id.logistics_follow_lv)
    ListView logisticsFollowLv;
    @BindView(R.id.CY_text)
    TextView CYText;
    @BindView(R.id.good_num_text)
    TextView goodNumText;
    @BindView(R.id.KF_tel_text)
    TextView KFTelText;

    private List<LogisticsFollowNoteBean> list;

    private LogisticsFollowBean bean = null;

    private SweetAlertDialog lodingDialog;
    private DataRequestBase dataRequestBase;

    private DriverMyOrderLogisticsFollowAdapter adapter;
    public static final String ALL_TICKET = "ALL_TICKET";
    public static final String GOODS_NUM = "GOODS_NUM";
    public static final String SEAR_OID = "SEAR_OID";
    private String allTicket;
    private String goodsNum;
    private String searOid;

    public static void startActivity(Activity activity, String allTcketNo,String goodsNum) {
        Intent intent = new Intent(activity, DriverMyOrderLogisticsFollowActivity.class);
        intent.putExtra(ALL_TICKET, allTcketNo);
        intent.putExtra(GOODS_NUM, goodsNum);
        activity.startActivity(intent);
    }
    public static void startActivity(Activity activity, String allTcketNo,String goodsNum,String searOid) {
        Intent intent = new Intent(activity, DriverMyOrderLogisticsFollowActivity.class);
        intent.putExtra(ALL_TICKET, allTcketNo);
        intent.putExtra(GOODS_NUM, goodsNum);
        intent.putExtra(SEAR_OID, searOid);
        activity.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics_follow);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        titleTv.setText("查看物流");
        goodsNum = getIntent().getStringExtra(GOODS_NUM);
        searOid = getIntent().getStringExtra(SEAR_OID);
        if (!TextUtils.isEmpty(searOid)){
            requestMsgCount();
        }

        list = new ArrayList<>();
        adapter = new DriverMyOrderLogisticsFollowAdapter(list, this);
        logisticsFollowLv.setAdapter(adapter);
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        lodingDialog.show();
        loadDataRequest(TMSService.GetSelectTicket_Url);

    }

    private void loadDataRequest(final String Url) {
        dataRequestBase = new DataRequestBase();
        allTicket = getIntent().getStringExtra(ALL_TICKET);

        GetLogisticsFollowRequest reqest = new GetLogisticsFollowRequest();
        reqest.All_Ticket_No = allTicket;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;

        dataRequestBase.DataValue = reqest; //JsonUtils.toJsonData(getDriverListRequest);
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
                LoggerUtil.json(response.toString());
                GetLogisicsFollowResponse response1 = JsonUtils.parseData(response.toString(), GetLogisicsFollowResponse.class);
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
                        bean = (LogisticsFollowBean) msg.obj;
                        setDateValue();
                        if (bean.TrackTicket != null) {
                            list.clear();
                            list.addAll(bean.TrackTicket);
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverMyOrderLogisticsFollowActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    private void setDateValue() {
        if (bean != null) {
            SetViewValueUtil.setTextViewValue(LetterStatusTv, bean.Transport_Status);//状态
            SetViewValueUtil.setTextViewValue(LetterOidTv, bean.Ticket_No);//货单编号
            SetViewValueUtil.setTextViewValue(CYText, bean.Org_Name);//承运来源
            SetViewValueUtil.setTextViewValue(KFTelText,  bean.Custom_Tel);//电话
            SetViewValueUtil.setTextViewValue(goodNumText, bean.Goods_Num+"件");//件数
        }
    }

    @OnClick(R.id.back_llayout)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
        }
    }


    //角标数据
    private void requestMsgCount() {
        dataRequestBase = new DataRequestBase();
        final SetMessageReadRequest request = new SetMessageReadRequest();
        request.Serial_Oid =searOid;
        dataRequestBase.DataValue = request;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;

        final JSONObject jsonObject = GsonUtil.newInstance().toJson(dataRequestBase);
        VolleyManager.newInstance().PostJsonRequest("Tag", TMSService.SetMessageRead_Url, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DataResponseBase bean = JsonUtils.parseData(response.toString(), DataResponseBase.class);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        myHandler = null;
    }
}
