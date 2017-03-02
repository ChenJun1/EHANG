package com.cvnavi.logistics.i51ehang.app.activity.login;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivityForNoTitle;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.HelpActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.CargoMainActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.DriverMainActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.EmployeeMainActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.CompanyBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.mVerifyCode;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetAppLoginRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.VerifyCodeRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.VerifyCodeResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.extra.MyCountDownTimer;
import com.cvnavi.logistics.i51ehang.app.jpush.ExampleUtil;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.ObjectAnimationUtils;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.utils.VerifyPhoneNumUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ScreenSupport;
import com.cvnavi.logistics.i51ehang.app.widget.edittext.EditTextWithDel;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import volley.VolleyManager;


/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午2:26
 * 描述：登录界面
 ************************************************************************************/

public class LoginActivity extends BaseActivityForNoTitle implements View.OnClickListener {
    private static final int TEL_PHONE_NUM_LENGTH = 11;
    MyCountDownTimer myCountDownTimer;
    long exitTime;
    @BindView(R.id.help_iv)
    TextView helpIv;
    @BindView(R.id.help_rl)
    RelativeLayout helpRl;
    @BindView(R.id.phone_num_et)
    EditTextWithDel phoneNumEt;
    @BindView(R.id.pwd_et)
    EditTextWithDel pwdEt;
    @BindView(R.id.verification_btn)
    TextView verificationBtn;
    @BindView(R.id.pwd_llayout)
    LinearLayout pwdLlayout;
    @BindView(R.id.confirm_btn)
    TextView confirmBtn;
    @BindView(R.id.bg_ll)
    RelativeLayout bgLl;
    private List<CompanyBean> companyBeanList;
    private String lastTel;//点击验证码之前的手机号；
    private SweetAlertDialog loadingDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        myCountDownTimer = new MyCountDownTimer(60000, 1000, verificationBtn);
        ScreenSupport.displayMetrics(this);
        EventBus.getDefault().register(this);
        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        phoneNumEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //触摸获取焦点颜色
                    phoneNumEt.setBackgroundResource(R.drawable.shape_edite_select);
                    pwdEt.setBackgroundResource(R.drawable.shape_rounded_et_ffffff_6);
                }
                return false;
            }
        });
        pwdEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //触摸获取焦点颜色
                    phoneNumEt.setBackgroundResource(R.drawable.shape_rounded_et_ffffff_6);
                    pwdEt.setBackgroundResource(R.drawable.shape_edite_select);
                }
                return false;
            }
        });


        phoneNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < TEL_PHONE_NUM_LENGTH) {
                    //如果没有达到手机号的长度，验证码按钮不会获取焦点色

                    ((EditTextWithDel) (findViewById(R.id.pwd_et))).setText("");
                    cleanToken();
                    verificationBtn.setBackground(getResources().getDrawable(R.drawable.shape_rounded_btn_e0e0e6_3));
                    verificationBtn.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                    verificationBtn.setText("获取验证码");
                } else if (s.length() == TEL_PHONE_NUM_LENGTH) {
                    if (verifyPhoneNumData() == false) {
                        return;
                    }
                    verificationBtn.setBackground(getResources().getDrawable(R.drawable.login_btn_selector));
                    verificationBtn.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));

                    if (!TextUtils.isEmpty(lastTel) && !phoneNumEt.getText().toString().equals(lastTel)) {
                        //判断当前的号码和上一次存取的手机号是否相等
                        myCountDownTimer.cancel();
                        verificationBtn.setText("获取验证码");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initLoginData();
    }

    private Handler msgHangler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String codeMsg = msg.getData().getString("messagecode");
                    ((EditTextWithDel) (findViewById(R.id.pwd_et))).setText(codeMsg);
                    break;
                default:
                    break;
            }
        }
    };


    @OnClick({R.id.verification_btn, R.id.confirm_btn, R.id.bg_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verification_btn:
                ObjectAnimationUtils.showClickAnimation(verificationBtn, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (verifyPhoneNumData() == false) {
                            return;
                        }

                        //开始计时
                        myCountDownTimer.start();
                        //获取上输入的手机号 作比较 如果两个手机号不同是可以重新获取验证码
                        lastTel = phoneNumEt.getText().toString();
                        //获取验证的请求
                        verification();

                        //密码框获取焦点颜色，账号框失去焦点颜色
                        phoneNumEt.setBackgroundResource(R.drawable.shape_rounded_et_ffffff_6);
                        pwdEt.setBackgroundResource(R.drawable.shape_edite_select);

                        //密码框获取焦点
                        pwdEt.setFocusable(true);
                        pwdEt.setFocusableInTouchMode(true);
                        pwdEt.requestFocus();
                        //强制启动软键盘
                        InputMethodManager imm = (InputMethodManager) LoginActivity.this
                                .getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(pwdEt, InputMethodManager.RESULT_SHOWN);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                                InputMethodManager.HIDE_IMPLICIT_ONLY);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                break;
            case R.id.confirm_btn:

                ObjectAnimationUtils.showClickAnimation(confirmBtn, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (verifyData() == false) {
                            return;
                        }

                        if (companyBeanList != null && companyBeanList.size() > 0) {
                            //有机构公司列表，调到公司列表界面选择公司号
                            startActivity(new Intent(LoginActivity.this, SelectCompanyActivity.class));
                        } else {
                            login();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


                break;
            case R.id.bg_ll:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                break;
        }
    }

    private boolean verifyData() {
        if (verifyPhoneNumData() == false) {
            return false;
        }

        if (TextUtils.isEmpty(((EditTextWithDel) (findViewById(R.id.pwd_et))).getText().toString())) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(LoginActivity.this, Utils.getResourcesString(R.string.input_pwd_or_vefiry_code_hint), null);
            return false;
        }

        return true;
    }

    private boolean verifyPhoneNumData() {
        if (TextUtils.isEmpty(phoneNumEt.getText().toString())) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(LoginActivity.this, Utils.getResourcesString(R.string.input_phone_num_hint), null);
            return false;
        }

        if (VerifyPhoneNumUtil.isMobileNO(phoneNumEt.getText().toString()) == false) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(LoginActivity.this, Utils.getResourcesString(R.string.input_right_phone_num_hint), null);
            return false;
        }

        return true;
    }

    private void initLoginData() {
        phoneNumEt.setText(SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UER_TEL, null));
        if (SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_Token, null) != null) {
            //存在token值，进行登录操作
            pwdEt.setText(SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UER_VERIFY_CODE, null));
            login();
        } else {
            //没有token，既第一次登录，清空账号框和密码框
            pwdEt.setText("");
        }
    }


    private void verification() {
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest();
        verifyCodeRequest.User_Tel = phoneNumEt.getText().toString();
        final DataRequestBase requestBase = new DataRequestBase();
        requestBase.DataValue = verifyCodeRequest.User_Tel;

        LogUtil.d("-->> request = " + new Gson().toJson(requestBase));
        VolleyManager.newInstance().PostJsonRequest(LoginService.VerifyCode_TAG, LoginService.VerifyCode_Request_Url, GsonUtil.newInstance().toJson(requestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                MLog.json(jsonObject.toString());
                Message message = Message.obtain();
                VerifyCodeResponse responseData = GsonUtil.newInstance().fromJson(jsonObject, VerifyCodeResponse.class);
                if (responseData != null && responseData.Success) {

                    if (responseData.CompanyList != null && responseData.CompanyList.size() > 0) {
                        if (companyBeanList == null) {
                            companyBeanList = new ArrayList<>();
                            companyBeanList.addAll(responseData.CompanyList);
                        } else {
                            companyBeanList.clear();
                            companyBeanList.addAll(responseData.CompanyList);
                        }
                        //如果有公司列表返回，保存
                        MyApplication.getInstance().setCompanyBeanList(responseData.CompanyList);
                    }

                    mVerifyCode myVerifyCode = GsonUtil.newInstance().fromJson(responseData.DataValue, mVerifyCode.class);
                    message.what = Constants.REQUEST_SUCC;
                    message.obj = myVerifyCode;
                } else {
                    message.what = Constants.REQUEST_FAIL;
                    message.obj = responseData.ErrorText;
                }

                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_FAIL;
                handler.sendEmptyMessage(message.what);
            }
        });


    }

    /**
     * 15216747933 司机账号
     * 15216747938 货主账号
     * 15021705443 APP员工
     * <p/>
     * 15216747938 货主账号E
     * 15021705443  APP员工F
     * 15216747933 司机账号G
     * <p/>
     * 18301969307 APP员工
     */
    private void login() {
        loadingDialog.show();
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_User_Key, null);
        dataRequestBase.UserType_Oid = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UserType_Oid, null);
        dataRequestBase.Token = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_Token, null);
        dataRequestBase.Company_Oid = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_Company_Oid, null);
        dataRequestBase.AndroidVersion = ContextUtil.getVerName(this);
        String tag = null;
        String url = null;

        if (TextUtils.isEmpty(dataRequestBase.Token) || (!phoneNumEt.getText().toString().equals(SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UER_TEL, null)))) {
            //首次登錄,或者登陆的手机号和保存的手机号不一样
            tag = LoginService.VerifyCode_TAG;
            url = LoginService.GetAppLogin_Request_Url;
            GetAppLoginRequest DataValue = new GetAppLoginRequest();
            DataValue.User_Tel = phoneNumEt.getText().toString();
            DataValue.VerifyCode = pwdEt.getText().toString();
            DataValue.UUID = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UER_UUID, null);//测试环境中要传
            dataRequestBase.DataValue = GsonUtil.newInstance().toJsonStr(DataValue);
        } else {
            //七日免登录
            tag = LoginService.GetAppAutoLogin_TAG;
            url = LoginService.GetAppAutoLogin_Request_Url;
            dataRequestBase.DataValue = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UER_TEL, null);
        }

        MLog.json(new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(tag, url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                MLog.json(jsonObject.toString());
                Message message = Message.obtain();
                GetAppLoginResponse info = GsonUtil.newInstance().fromJson(jsonObject, GetAppLoginResponse.class);
                if (info != null) {
                    if (info.Success) {
                        message.what = Constants.REQUEST_SUCC;
                        message.obj = info;
                    } else {
                        message.what = Constants.REQUEST_FAIL;
                        message.obj = info.ErrorText;
                    }
                } else {
                    message.what = Constants.REQUEST_FAIL;
                }

                loginHandler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                loginHandler.sendMessage(msg);
            }
        });
    }

    private void saveToken(GetAppLoginResponse info) {
        if (info.DataValue != null) {
            SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_User_Key, info.DataValue.User_Key);
            SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_UserType_Oid, info.DataValue.UserType_Oid);
            SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Token, info.DataValue.Token);
            SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Company_Oid, info.DataValue.Company_Oid);
        }

        if (verifyPhoneNumData() == false) {
            return;
        }
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_UER_TEL, phoneNumEt.getText().toString());
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_UER_VERIFY_CODE, pwdEt.getText().toString());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            loadingDialog.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    pwdEt.setText("");
                    mVerifyCode code = (mVerifyCode) msg.obj;
                    if (code != null && code.VerifyCode != null) {
                        // 写入缓存
                        MyApplication.getInstance().setVerifyCode(code);
                        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_UER_UUID, code.UUID);
                    }

                    DialogUtils.showSuccToast(Utils.getResourcesString(R.string.get_verifycode_succ));

                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.get_verifycode_faild));
                    break;
            }
        }
    };

    private Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            loadingDialog.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        GetAppLoginResponse loginInfo = (GetAppLoginResponse) msg.obj;
                        if (loginInfo != null && loginInfo.DataValue != null) {
                            //保存数据
                            saveToken(loginInfo);
                            //写入缓存
                            MyApplication.getInstance().setLoginInfo(loginInfo);
                            //设置极光推送
                            setAlias();
                            //判断角色返回的类型
                            String userType = loginInfo.DataValue.UserType_Oid;
                            if (!TextUtils.isEmpty(userType) && (userType.equals("G") || userType.equals("E") || userType.equals("F"))) {
                                if (userType.equals("G")) {
                                    skipActivity(LoginActivity.this, DriverMainActivity.class);
                                } else if (userType.equals("E")) {
                                    skipActivity(LoginActivity.this, CargoMainActivity.class);
                                } else if (userType.equals("F")) {
                                    skipActivity(LoginActivity.this, EmployeeMainActivity.class);
                                }
                            } else {
                                DialogUtils.showFailToast("无权限登录!");
                                reLogin();
                            }
                        } else {
                            DialogUtils.showFailToast("请求登录失败!");
                            reLogin();
                        }
                    } else {
                        DialogUtils.showFailToast(Utils.getResourcesString(R.string.login_fail));
                        reLogin();
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    if (msg != null && msg.obj != null) {
                        //判断返回错误的类型
                        String infoError = (String) msg.obj;
                        if (infoError != null && infoError.equals("Displace")) {
                            DialogUtils.showFailToast("账号已在别的设备登录!");
                        } else {
                            DialogUtils.showFailToast("无法登录!");
                        }
                    } else {
                        DialogUtils.showFailToast("登录异常!");
                    }

                    reLogin();
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(LoginActivity.this, Utils.getResourcesString(R.string.request_error));
                    reLogin();
                    break;
            }
        }
    };

    //重新登陆
    private void reLogin() {
        pwdEt.setText("");
        cleanToken();
    }

    private void cleanToken() {
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_User_Key, null);
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_UserType_Oid, null);
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Token, null);
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Company_Oid, null);
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_UER_TEL, null);
        SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_UER_VERIFY_CODE, null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplication(), "再按一次退出!", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick(R.id.help_rl)
    public void onClick() {
        startActivity(new Intent(LoginActivity.this, HelpActivity.class));
    }

    //设置别名
    private void setAlias() {

        String token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        if (token.contains("-")) {
            token = token.replace("-", "");
        }
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(LoginActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!ExampleUtil.isValidTagAndAlias(token)) {
            Toast.makeText(LoginActivity.this, "格式不对", Toast.LENGTH_SHORT).show();
            return;
        }
        //调用JPush API设置Alias
        JPushInterface.setAliasAndTags(getApplicationContext(), token, null, mAliasCallback);
    }


    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    LoggerUtil.i("", logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    LoggerUtil.i("", logs);
                    if (ExampleUtil.isConnected(getApplicationContext())) {
                        JPushInterface.setAliasAndTags(getApplicationContext(), alias, null, mAliasCallback);
                    } else {
                        LoggerUtil.i("", "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("", logs);
            }

        }

    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectCompany(CompanyBean bean) {
        if (bean != null) {
            login();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (msgHangler != null) {
            msgHangler.removeCallbacksAndMessages(null);
        }
        if (loginHandler != null) {
            loginHandler.removeCallbacksAndMessages(null);
        }
        msgHangler = null;
        loginHandler = null;
        EventBus.getDefault().unregister(this);
    }
}
