package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.exception;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.DriverCarExceptionUpLoadActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskExceptionInfoAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.CarExceptionBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetCarExceptionRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetCarExceptionRespson;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
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
 * Created by ${ChenJ} on 2016/8/22.
 */
public class MyTaskExceptionInfoActivity extends BaseActivity {

    private static final String TAG = "MyTaskExceptionInfoActivity";
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.empty_tv)
    TextView emptyTv;

    private SweetAlertDialog lodingDialog;
    private DataRequestBase dataRequestBase;
    private TaskBean taskBean;
    private List<CarExceptionBean> list;
    private MyTaskExceptionInfoAdapter adapter;

    public static void start(Context context,TaskBean taskBean) {
        Intent starter = new Intent(context, MyTaskExceptionInfoActivity.class);
        starter.putExtra(Constants.TASKINFO, taskBean);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_exception_info);
        ButterKnife.bind(this);
        list = new ArrayList<>();
        adapter = new MyTaskExceptionInfoAdapter(list, this);
        lv.setAdapter(adapter);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (taskBean != null) {

            loadDataRequest(TMSService.GetCarExceptImgInfo_Request_Url);
        }
    }

    private void init() {
        titleTv.setText(Utils.getResourcesString(R.string.exception_info));
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        if (getIntent().getSerializableExtra(Constants.TASKINFO) != null) {
            taskBean = (TaskBean) getIntent().getSerializableExtra(Constants.TASKINFO);

            if (!TextUtils.isEmpty(taskBean.TicketStatus) && taskBean.TicketStatus.equals("已完成")) {
                operationBtn.setVisibility(View.GONE);
            } else {
                operationBtn.setVisibility(View.VISIBLE);
                operationBtn.setText(Utils.getResourcesString(R.string.add_exception_info));
            }
        }




        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CAR_EXCEPTION_UPLOAD+"")) {
            operationBtn.setVisibility(View.VISIBLE);
        } else {
            operationBtn.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.back_llayout, R.id.operation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:
                Intent intent = new Intent(this, DriverCarExceptionUpLoadActivity.class);
                LogUtil.d("-->> taskben1");
                if (taskBean != null) {
                    LogUtil.d("-->> taskben2");
                    intent.putExtra(Constants.TASKINFO, taskBean);
                }
                startActivity(intent);
                break;
        }
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
        dataRequestBase.DataValue = new GetCarExceptionRequest(taskBean.Letter_Oid); //JsonUtils.toJsonData
        // (getDriverListRequest);
        LogUtil.d("-->>" + dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>response" + response.toString());
                GetCarExceptionRespson response1 = JsonUtils.parseData(response.toString(), GetCarExceptionRespson.class);
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
                LogUtil.d("-->>" + error.toString());
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
                    list.clear();
                    if (msg.obj != null) {
                        List<CarExceptionBean> dataList = (List<CarExceptionBean>) msg.obj;
                        list.addAll(dataList);
                    }
                    if (list.size() > 0) {
                        emptyTv.setVisibility(View.GONE);
                    } else {
                        emptyTv.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.DELETE_SUCC:
                    DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyTaskExceptionInfoActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;


            }
        }
    };
}
