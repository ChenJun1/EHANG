package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask;

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
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskLineFollowAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.preview.PreviewPicPagerActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.LineFollowBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.Model_LetterTrace_Node;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetCarGPSAggregateResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetTaskLineFollowResponse;
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

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskLineFollowActivity extends BaseActivity implements MyTaskLineFollowAdapter.TaskLineLookPicListener {

    private final String TAG = MyTaskDetailedActivity.class.getName();
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.Letter_Status_tv)
    TextView LetterStatusTv;
    @BindView(R.id.Letter_Oid_tv)
    TextView LetterOidTv;
    @BindView(R.id.line_follow_lv)
    ListView lineFollowLv;

    private SweetAlertDialog lodingDialog;

    private DataRequestBase dataRequestBase;

    private GetCarGPSAggregateResponse.DataValueBean.LetterListBean taskBean = null;

    private LineFollowBean lineFollowBean;

    private List<Model_LetterTrace_Node> list;

    private MyTaskLineFollowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_follow);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        titleTv.setText(Utils.getResourcesString(R.string.line_follow));
        lodingDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        dataRequestBase = new DataRequestBase();
        list = new ArrayList<>();
        adapter = new MyTaskLineFollowAdapter(list, this, this);
        lineFollowLv.setAdapter(adapter);
        if (getIntent().getSerializableExtra(Constants.TASKINFO) != null) {
            taskBean = (GetCarGPSAggregateResponse.DataValueBean.LetterListBean) getIntent().getSerializableExtra(Constants.TASKINFO);
            if (taskBean != null) {
                loadDataRequest(TMSService.GetCarLineNode_Request_Url);
            }
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
        dataRequestBase.DataValue = taskBean; //JsonUtils.toJsonData(getDriverListRequest);
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> line Response = "+response.toString());
                GetTaskLineFollowResponse response1 = JsonUtils.parseData(response.toString(), GetTaskLineFollowResponse.class);
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
                        lineFollowBean = (LineFollowBean) msg.obj;
                        setValue();
                        if (lineFollowBean.Line_Nodes != null) {
                            list.clear();
                            list.addAll(lineFollowBean.Line_Nodes);
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
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(MyTaskLineFollowActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    public void setValue() {
        SetViewValueUtil.setTextViewValue(LetterStatusTv, lineFollowBean.Letter_Status);
        SetViewValueUtil.setTextViewValue(LetterOidTv, lineFollowBean.Letter_Oid);

    }

    @Override
    public void onLookPic(Model_LetterTrace_Node node) {
        Intent intent = new Intent(this, PreviewPicPagerActivity.class);
        intent.putExtra(Constants.DRIVER_LINE_LOOK_PIC, node);
        startActivity(intent);
    }

    @OnClick(R.id.back_llayout)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_llayout:
                finish();
                break;
        }
    }
}
