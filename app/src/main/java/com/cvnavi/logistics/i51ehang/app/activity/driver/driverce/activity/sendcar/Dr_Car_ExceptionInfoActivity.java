package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.adapter.Dr_Car_ExceptionInfoAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.ExceptionInfoBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetLogisticsFollowRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetExceptionResponse;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
 * Depict: 车辆异常信息 页面
 */
public class Dr_Car_ExceptionInfoActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener{

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.exception_follow_lv)
    ListView exceptionFollowLv;
    @BindView(R.id.no_data_ll)
    LinearLayout mNoDataLl;

    private SweetAlertDialog lodingDialog;

    private String LetterOid;

    private Dr_Car_ExceptionInfoAdapter adapter;

    private List<ExceptionInfoBean> list;

    private HandlerUtils.HandlerHolder mHandlerHolder;

    public static void startActivity(Activity activity, String AllTicktNo) {
        Intent intent = new Intent(activity, Dr_Car_ExceptionInfoActivity.class);
        intent.putExtra(Constants.All_Ticket_No, AllTicktNo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_info);
        ButterKnife.bind(this);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        init();
    }

    private void init() {
        titleTv.setText(Utils.getResourcesString(R.string.driver_home_order_detail_exception_info));
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        list = new ArrayList<>();
        adapter = new Dr_Car_ExceptionInfoAdapter(list, this);
        exceptionFollowLv.setAdapter(adapter);
        if (getIntent().getStringExtra(Constants.All_Ticket_No) != null) {
            LetterOid = getIntent().getStringExtra(Constants.All_Ticket_No);
        }
        loadDataRequest(DriverService.GetCarExceptInfo_Request_Url);//数据请求
    }

    @OnClick(R.id.back_llayout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    private void loadDataRequest(final String Url) {
        DataRequestBase dataRequestBase = new DataRequestBase();
        GetLogisticsFollowRequest reqest = new GetLogisticsFollowRequest();
        reqest.Letter_Oid = LetterOid;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;

        dataRequestBase.DataValue = reqest; //JsonUtils.toJsonData(getDriverListRequest);
        VolleyManager.newInstance().PostJsonRequest(null, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
                GetExceptionResponse response1 = JsonUtils.parseData(response.toString(), GetExceptionResponse.class);
                Message msg = Message.obtain();
                if(response1==null){
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                }else {
                    if (response1.Success) {
                        msg.obj = response1.DataValue;
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = response1.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                    }
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });

    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                if (msg.obj != null) {
                    List<ExceptionInfoBean> dataList = (List<ExceptionInfoBean>) msg.obj;
                    if (dataList != null) {
                        list.clear();
                        list.addAll(dataList);
                    }
                    if (list.size() > 0) {
                        mNoDataLl.setVisibility(View.GONE);
                    } else {
                        mNoDataLl.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                }
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.DELETE_SUCC:
                DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(Dr_Car_ExceptionInfoActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
