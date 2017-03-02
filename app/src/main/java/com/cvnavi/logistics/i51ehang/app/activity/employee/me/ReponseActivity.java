package com.cvnavi.logistics.i51ehang.app.activity.employee.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.launch.LaunchActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.AppUpdateBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.AppVsionBean;
import com.cvnavi.logistics.i51ehang.app.bean.response.AppVsionBeanResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppUpdateBeanResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.service.DownAPKService;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:10
*描述：关于e航界面
************************************************************************************/


public class ReponseActivity extends BaseActivity {
    @BindView(R.id.version_tv)
    TextView versionTv;
    @BindView(R.id.upDate)
    LinearLayout upDate;
    @BindView(R.id.about_us)
    LinearLayout aboutUs;
    @BindView(R.id.contact_us)
    LinearLayout contactUs;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.new_tag)
    ImageView newTag;
    private final int GetAppUriOk = 123;
    private final int GetAppVsionOk = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reponse);
        ButterKnife.bind(this);
        titltTv.setText("关于e航");
        versionTv.setText("V" + ContextUtil.getVerName(this));
        getVison(LoginService.GetVersion_Request_Url);
    }

    /**
     * 获取版本号
     *
     * @param Url
     */
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
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "-->>" + error.getMessage(), error);
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        });
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GetAppVsionOk:
                    AppVsionBean vison = (AppVsionBean) msg.obj;
                    if (vison == null) {
                        DialogUtils.showNormalToast("获取版本失败！");
                        break;
                    }
                    String visonStr = vison.AndroidVersion;
                    String curVerName = ContextUtil.getVerName(ReponseActivity.this);
                    if (LaunchActivity.isEqually(curVerName, visonStr)) {
                        newTag.setVisibility(View.VISIBLE);
                    } else {
                        newTag.setVisibility(View.GONE);

                    }
                    break;
                case Constants.REQUEST_FAIL:
                case Constants.REQUEST_ERROR:
                    upDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DialogUtils.showMessageDialogOfDefaultSingleBtn(ReponseActivity.this, "没有更好用的版本了!");
                        }
                    });
                    break;
                case GetAppUriOk:
                    AppUpdateBean bean = (AppUpdateBean) msg.obj;
                    File file = null;
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
    };


    @OnClick({R.id.upDate, R.id.about_us, R.id.contact_us})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upDate:
                if (newTag.getVisibility() == View.VISIBLE) {
                    DialogUtils.showMessageDialog(ReponseActivity.this, "更新提示", "前方发现更好用的版本,现在下载?", "确定", "取消", new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            if (CustomDialogListener.BUTTON_NO == closeType) {
                                finish();
                            } else if (CustomDialogListener.BUTTON_OK == closeType) {
                                getAppDownUrl(LoginService.GetAPP_Request_Url);
                            }
                        }
                    });

                } else {
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(ReponseActivity.this, "没有更好用的版本了!");
                }
                break;
            case R.id.about_us:
                startActivity(new Intent(ReponseActivity.this, AboutUsActivity.class));
                break;
            case R.id.contact_us:
                ContextUtil.callAlertDialog(Utils.getResourcesString(R.string.customer_service_tel_num), ReponseActivity.this);
                break;
        }
    }


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
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        });
    }


    /**
     * 我
     *
     * @param appBean
     */

    public void successGetDwonAppUrl(final AppUpdateBean appBean) {

        if (appBean != null) {
            DialogUtils.showMessageDialog(ReponseActivity.this, "下载提示", "开始后台下载，完成后自动安装！", "确定", "取消", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    if (CustomDialogListener.BUTTON_NO == closeType) {
                        finish();
                    } else if (CustomDialogListener.BUTTON_OK == closeType) {
                        DownAPKService.startService(ReponseActivity.this, "e航", appBean.AppUrl, Integer.parseInt(appBean.AppSize));
                    }
                }
            });
        }
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }
}
