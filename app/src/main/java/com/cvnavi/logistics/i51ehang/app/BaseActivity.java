package com.cvnavi.logistics.i51ehang.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.activity.login.LoginActivity;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.callback.manager.HttpDelWithListener;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.ui.ISkipActivity;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import org.json.JSONObject;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;
import okhttp3.MediaType;
import volley.VolleyManager;

public abstract class BaseActivity extends FragmentActivity implements ISkipActivity {
    DataReceiver dataReceiver;//BroadcastReceiver对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 头部沉侵
        }
    }

    private SweetAlertDialog sweetAlertDialog = null;

    public void showLoading() {
        if (sweetAlertDialog == null) {
            sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }
        sweetAlertDialog.show();
    }

    public void dissLoading() {
        if (sweetAlertDialog != null) {
            sweetAlertDialog.dismiss();
        }
    }

    /**
     * 基类自带的网络请求
     * @param url
     * @param dataRequestBase
     * @param listener
     */
    public void httpPostString(String url, Object dataRequestBase, final HttpDelWithListener listener) {
        showLoading();
        OkHttpUtils
                .postString()
                .url(url)
                .content(new Gson().toJson(dataRequestBase))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dissLoading();
                        if (listener != null) {
                            listener.httpError(call, e, id);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dissLoading();
                        if (listener != null) {
                            listener.httpSucc(response, id);
                        }
                    }
                });
    }


    @Override
    protected void onStart() {
        dataReceiver = new DataReceiver();
        IntentFilter filter = new IntentFilter();//创建IntentFilter对象
        filter.addAction("com.cvnavi.logistics.i51eyun.app.MyCheckLoginService");
        registerReceiver(dataReceiver, filter);//注册Broadcast Receiver
        super.onStart();
        LogUtil.i("-->>onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("-->>onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("-->>onPause");
    }

    @Override
    protected void onStop() {
        unregisterReceiver(dataReceiver);//取消注册Broadcast Receiver
        super.onStop();
        LogUtil.i("-->>onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sweetAlertDialog != null) {
            sweetAlertDialog = null;
        }
        LogUtil.i("-->>onDestroy");
    }

    private class DataReceiver extends BroadcastReceiver {//继承自BroadcastReceiver的子类

        @Override
        public void onReceive(Context context, Intent intent) {//重写onReceive方法
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(BaseActivity.this, "该账号已经在其他设备上面登录!", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    exitLogin();
                }
            });
        }
    }

    /**
     * 退出登录
     * 清除本地Token
     */
    private void exitLogin() {
        JPushInterface.stopPush(this);//停止推送
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_User_Key, null);

        VolleyManager.newInstance().PostJsonRequest(LoginService.ExitLogin_TAG, LoginService.ExitLogin_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Message message = Message.obtain();
                DataResponseBase dataResponseBase = GsonUtil.newInstance().fromJson(response, DataResponseBase.class);
                if (dataResponseBase.Success) {
                    message.what = Constants.REQUEST_SUCC;
                } else {
                    message.what = Constants.REQUEST_FAIL;
                }

                handler.sendMessage(message);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_FAIL;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Token, null);
                    skipActivity(BaseActivity.this, LoginActivity.class);
                    break;
                case Constants.REQUEST_FAIL:
//                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.exit_login_fail));
                    break;
            }
        }
    };


    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls) {
        showActivity(aty, cls);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Intent it) {
        showActivity(aty, it);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        showActivity(aty, cls, extras);
        aty.finish();
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }
}
