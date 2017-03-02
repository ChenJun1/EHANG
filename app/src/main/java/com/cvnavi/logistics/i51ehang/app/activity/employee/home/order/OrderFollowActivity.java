package com.cvnavi.logistics.i51ehang.app.activity.employee.home.order;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter.OrderFollowAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.LogisticsFollowBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.LogisticsFollowNoteBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetLogisticsFollowRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetLogisicsFollowResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
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
*创建时间：2017/1/16 下午3:45
*描述： 查看物流界面
************************************************************************************/


public class OrderFollowActivity extends BaseActivity {
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

    private DataRequestBase dataRequestBase;

    private OrderFollowAdapter adapter;
    private static final String AllTicket = "ALL_TICKET";
    private static final String GoodsNum = "GOODS_NUM";
    private static final String OrgName = "OrgName";
    private String allTicket;
    private String goodsNum;
    private String orgName;


    public static void startActivity(Activity activity, String allTcketNo,String goodsNum,String orgName) {
        Intent intent = new Intent(activity, OrderFollowActivity.class);
        intent.putExtra(AllTicket, allTcketNo);
        intent.putExtra(GoodsNum, goodsNum);
        intent.putExtra(OrgName, orgName);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_order_follow);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        titleTv.setText("查看物流");
        goodsNum = getIntent().getStringExtra(GoodsNum);
        orgName = getIntent().getStringExtra(OrgName);
        list = new ArrayList<>();
        adapter = new OrderFollowAdapter(list, this);
        logisticsFollowLv.setAdapter(adapter);
        loadDataRequest(TMSService.GetSelectTicket_Url);
    }

    private void loadDataRequest(final String Url) {
        showLoading();
        dataRequestBase = new DataRequestBase();
        allTicket = getIntent().getStringExtra(AllTicket);

        GetLogisticsFollowRequest reqest = new GetLogisticsFollowRequest();
        reqest.All_Ticket_No = allTicket;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;

        dataRequestBase.DataValue = reqest; //JsonUtils.toJsonData(getDriverListRequest);
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
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
            dissLoading();
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(OrderFollowActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    private void setDateValue() {
        if (bean != null) {
            SetViewValueUtil.setTextViewValue(LetterStatusTv, bean.Transport_Status);//状态
            SetViewValueUtil.setTextViewValue(LetterOidTv, bean.Ticket_No);//货单编号
            SetViewValueUtil.setTextViewValue(CYText, orgName);//承运来源
            SetViewValueUtil.setTextViewValue(KFTelText, "--");//电话
            SetViewValueUtil.setTextViewValue(goodNumText, goodsNum+"件");//件数

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

}
