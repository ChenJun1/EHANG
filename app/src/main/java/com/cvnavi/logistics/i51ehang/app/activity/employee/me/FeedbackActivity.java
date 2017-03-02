package com.cvnavi.logistics.i51ehang.app.activity.employee.me;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:09
*描述：意见反馈界面
************************************************************************************/


public class FeedbackActivity extends BaseActivity {
    private static final int EDIT_MAX_LENGTH = 300;
    @BindView(R.id.feedback_et)
    EditText feedbackEt;
    @BindView(R.id.commit_tv)
    TextView commitTv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    private SweetAlertDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        titltTv.setText("意见反馈");
        feedbackEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= EDIT_MAX_LENGTH) {
                    //监控输入内容长度,大于30个字弹出提示
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(FeedbackActivity.this, "您输入的内容过长!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

//    /**
//     * 显示加载loading
//     */
//    private void showLoading() {
//        if (loading == null) {
//            loading = new SweetAlertDialog(FeedbackActivity.this, SweetAlertDialog.PROGRESS_TYPE);
//        }
//        loading.show();
//    }

    /**
     * 提交反馈的入口
     */
    private void commitFeedback() {
        showLoading();
        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        final DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = feedbackEt.getText().toString();
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.User_Name = info.DataValue.User_Name;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;

        VolleyManager.newInstance().PostJsonRequest(LoginService.SetFeedBack_TAG, LoginService.SetFeedBack_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                dissLoading();
                DataResponseBase dataResponseBase = GsonUtil.newInstance().fromJson(response, DataResponseBase.class);
                LogUtil.d("=====>>response:" + response);
                String msg = "succ";
                if (dataResponseBase != null && dataResponseBase.Success) {
                    msg = "感谢您的建议以及反馈!";
                } else {
                    msg = "提交失败";
                }
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(FeedbackActivity.this, msg, new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        FeedbackActivity.this.finish();
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dissLoading();
                DialogUtils.showMessageDialogOfDefaultSingleBtn(FeedbackActivity.this, "网络异常!");
            }
        });

    }


    @OnClick({R.id.back_llayout, R.id.commit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.commit_tv:
                if (!TextUtils.isEmpty(feedbackEt.getText().toString().trim())) {
                    commitFeedback();
                } else {
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(FeedbackActivity.this, "请输入您的建议以及反馈!");
                }
                break;
        }
    }
}
