package com.cvnavi.logistics.i51ehang.app.activity.launch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.guid.GuidViewPagerActivity;
import com.cvnavi.logistics.i51ehang.app.activity.login.LoginActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.AppUpdateBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.AppVsionBean;
import com.cvnavi.logistics.i51ehang.app.bean.response.AppVsionBeanResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppUpdateBeanResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.service.DownAPKService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ScreenSupport;

import org.json.JSONObject;

import java.io.File;

import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 启动页
 */
public class LaunchActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener {
    private final String TAG = "LaunchActivity";
    private final int GetAppUriOk = 123;//标识码
    private final int GetAppVsionOk = 124;//标识码
    private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟2秒
    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ScreenSupport.displayMetrics(this);
//        EventBus.getDefault().register(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        setFullScreen(true);
        getVison(LoginService.GetVersion_Request_Url);//获取版本号

    }

    //下载
    private void getAppDownUrl(final String Url) {
        VolleyManager.newInstance().PostJsonRequest(null, Url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                GetAppUpdateBeanResponse Vsion = JsonUtils.parseData(response.toString(), GetAppUpdateBeanResponse.class);
                Message msg = Message.obtain();
                if (Vsion.Success) {
                    msg.what = GetAppUriOk;
                    msg.obj = Vsion.DataValue;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });
    }

    //获取版本请求
    private void getVison(final String Url) {
        VolleyManager.newInstance().PostJsonRequest(null, Url, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                AppVsionBeanResponse Vsion = JsonUtils.parseData(response.toString(), AppVsionBeanResponse.class);
                Message msg = Message.obtain();
                if (Vsion.Success) {
                    msg.what = GetAppVsionOk;
                    msg.obj = Vsion.DataValue;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "-->>" + error.getMessage(), error);
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });
    }

    private Handler mRunnableHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (SharedPreferencesTool.getBoolean(SharedPreferencesTool.FRIST_LOGIN, true)) {
                //将登录标志位设置为false，下次登录时不在显示首次登录界面
                SharedPreferencesTool.putBoolean(SharedPreferencesTool.FRIST_LOGIN, false);
                skipActivity(LaunchActivity.this, GuidViewPagerActivity.class);
//                    skipActivity(LaunchActivity.this, LoginActivity.class);
            } else {
                skipActivity(LaunchActivity.this, LoginActivity.class);
            }
        }
    };

    //延时跳转登录
    private void toLogin() {
        mRunnableHandler.postDelayed(mRunnable, SPLASH_DISPLAY_LENGHT);
    }

    //下载提示
    public void successGetDwonAppUrl(final AppUpdateBean appBean) {
        if (appBean != null) {
            DialogUtils.showMessageDialog(LaunchActivity.this, "下载提示", "开始后台下载，完成后自动安装！", "确定", "取消", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    if (CustomDialogListener.BUTTON_NO == closeType) {
                        finish();
                    } else if (CustomDialogListener.BUTTON_OK == closeType) {
                        DownAPKService.startService(LaunchActivity.this, "e航", appBean.AppUrl, Integer.parseInt(appBean.AppSize));
                        toLogin();
                    }
                }
            });
        }
    }

    public void setFullScreen(boolean isFullScreen) {
        if (isFullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    @Override
    protected void onDestroy() {
        mRunnableHandler.removeCallbacks(mRunnable);
        mRunnableHandler.removeCallbacksAndMessages(null);
//        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     *  版本号对比
     * @param oldCode
     * @param newCode
     * @return
     */
    public static Boolean isEqually(String oldCode, String newCode) {
        if (TextUtils.isEmpty(oldCode) || TextUtils.isEmpty(newCode)) {
            return false;
        }
        if (oldCode.equals(newCode)) {
            return false;
        }
        int oldCodes = 0;
        int newCodes = 0;

        if (oldCode.contains(".")) {
            oldCode = oldCode.replace(".", "");
            try {
                oldCodes = Integer.parseInt(oldCode);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (newCode.contains(".")) {
            newCode = newCode.replace(".", "");
            try {
                newCodes = Integer.parseInt(newCode);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (newCodes > oldCodes) {
            return true;
        }
        return false;
    }

    @Override
    public void handlerMessage(Message msg) {
        switch (msg.what) {
            case GetAppVsionOk:
                AppVsionBean vison = (AppVsionBean) msg.obj;
                if (vison == null) {
                    DialogUtils.showNormalToast("获取版本失败！");
                    break;
                }
                String visonStr = vison.AndroidVersion;
                String curVerName = ContextUtil.getVerName(getApplicationContext());
                if (isEqually(curVerName, visonStr)) {
                    DialogUtils.showMessageDialog(LaunchActivity.this, "更新提示", "当前版本非最新版本，请下载最新版本！", "确定", "取消", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            if (CustomDialogListener.BUTTON_NO == closeType) {
                                toLogin();
                            } else if (CustomDialogListener.BUTTON_OK == closeType) {
                                getAppDownUrl(LoginService.GetAPP_Request_Url);
                            }
                        }
                    });
                } else {
                    toLogin();
                }
                break;
            case Constants.REQUEST_FAIL:
                toLogin();
                break;
            case Constants.REQUEST_ERROR:
                toLogin();
                break;
            case GetAppUriOk:
                AppUpdateBean bean = (AppUpdateBean) msg.obj;
                File file;
                if (bean != null) {//判断是否存在安装包
                    String filePath = DownAPKService.getStoragePath() + bean.AppName;
                    file = new File(filePath);
                    if (file.exists()) {
                        boolean delete = file.delete();
                        if (delete) {
                            successGetDwonAppUrl(bean);
                        }
                    } else {
                        successGetDwonAppUrl(bean);
                    }
                }
                break;
            default:
                break;
        }
    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void upDate(ItemEvent itemEvent) {
//        MLog.print("-->>> pos = " + itemEvent.getOffset());
//    }


}
