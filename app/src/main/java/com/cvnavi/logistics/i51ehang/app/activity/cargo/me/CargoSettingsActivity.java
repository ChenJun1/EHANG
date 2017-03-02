package com.cvnavi.logistics.i51ehang.app.activity.cargo.me;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.GetAPPNotifySetDataValue;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.response.GetAPPNotifySetResponse;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.togglebutton.ToggleButton;
import com.google.gson.Gson;

import org.json.JSONObject;

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
 * Depict: 物流跟踪设置 页面
 */

public class CargoSettingsActivity extends BaseActivity implements HandlerUtils.OnReceiveMessageListener {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout operationLlayout;
    @BindView(R.id.set_save_button)
    Button setSaveButton;
    @BindView(R.id.zc_msg_btn)
    ToggleButton zcMsgBtn;
    @BindView(R.id.fc_msg_btn)
    ToggleButton fcMsgBtn;
    @BindView(R.id.dc_msg_btn)
    ToggleButton dcMsgBtn;
    @BindView(R.id.sh_msg_btn)
    ToggleButton shMsgBtn;
    @BindView(R.id.sign_msg_btn)
    ToggleButton signMsgBtn;


    private SweetAlertDialog loadingDialog = null;

    private DataRequestBase dataRequestBase;

    private String ZCtf = "0";//	装车通知 0/1
    private String FCtf = "0";//	发车通知 0/1
    private String DCtf = "0";//	到车通知 0/1
    private String SHtf = "0";//	送货通知 0/1
    private String QStf = "0";//	签收通知 0/1
    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_set);
        ButterKnife.bind(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        initView();

        loadRequest();
    }

    private void initView() {
        titleTv.setText("物流跟踪设置");

        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dataRequestBase = new DataRequestBase();

        set();

    }

    @OnClick({R.id.back_llayout, R.id.set_save_button})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.set_save_button:
                httpRequestSet();
                break;

        }
    }


    private void loadRequest() {

        loadingDialog.show();

        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();

        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.User_Tel = info.DataValue.User_Tel;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;

        LogUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        LoggerUtil.json(new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.GetAppNotifySets_TAG, TMSService.GetAppNotifySets_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(response.toString());
                GetAPPNotifySetResponse getShiftListResponse = GsonUtil.newInstance().fromJson(response, GetAPPNotifySetResponse.class);
                Message msg = Message.obtain();
                if (getShiftListResponse != null) {
                    if (getShiftListResponse.Success && getShiftListResponse.DataValue != null) {
                        msg.what = Constants.REQUEST_SUCC;
                        msg.obj = getShiftListResponse.DataValue;
                    } else {
                        if (getShiftListResponse.DataValue == null) {
                            msg.what = Constants.REQUEST_FAIL;
                            msg.obj = Utils.getResourcesString(R.string.list_null);
                        } else {
                            msg.what = Constants.REQUEST_FAIL;
                            msg.obj = getShiftListResponse.ErrorText;
                        }
                    }
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_ERROR;
                message.obj = error.toString();
                mHandlerHolder.sendMessage(message);
            }
        });
    }

    private void httpRequestSet() {

        loadingDialog.show();

        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();

        dataRequestBase.DataValue = ZCtf + FCtf + DCtf + SHtf + QStf;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.User_Tel = info.DataValue.User_Tel;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;

        LogUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        LoggerUtil.json(new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.SetAppNotifySets_TAG, TMSService.SetAppNotifySets_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> respon=" + response.toString());
                LoggerUtil.json(response.toString());
                DataResponseBase getShiftListResponse = GsonUtil.newInstance().fromJson(response, DataResponseBase.class);
                Message msg = Message.obtain();
                if (getShiftListResponse != null) {
                    if (getShiftListResponse.Success) {
                        msg.what = Constants.SETMSG_SUCC;
                    } else {
                        msg.what = Constants.REQUEST_FAIL;
                        msg.obj = getShiftListResponse.ErrorText;
                    }
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_ERROR;
                message.obj = error.toString();
                mHandlerHolder.sendMessage(message);
            }
        });

    }

    private void search(GetAPPNotifySetDataValue bean) {
        ZCtf = bean.ZCtf;
        FCtf = bean.FCtf;
        DCtf = bean.DCtf;
        SHtf = bean.SHtf;
        QStf = bean.QStf;
        if (bean.ZCtf.equals("0")) {
            zcMsgBtn.setToggleOn(true);
        } else {
            zcMsgBtn.setToggleOff(true);
        }

        if (bean.FCtf.equals("0")) {

            fcMsgBtn.setToggleOn(true);
        } else {
            fcMsgBtn.setToggleOff(true);
        }

        if (bean.DCtf.equals("0")) {
            dcMsgBtn.setToggleOn(true);
        } else {
            dcMsgBtn.setToggleOff(true);
        }

        if (bean.SHtf.equals("0")) {
            shMsgBtn.setToggleOn(true);
        } else {
            shMsgBtn.setToggleOff(true);
        }

        if (bean.QStf.equals("0")) {
            signMsgBtn.setToggleOn(true);
        } else {
            signMsgBtn.setToggleOff(true);
        }
    }

    private void set() {
        zcMsgBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(View v, boolean on) {
                if (on) {

                    ZCtf = "0";
                } else {
                    ZCtf = "1";
                }
            }
        });

        fcMsgBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(View v, boolean on) {
                if (on) {
                    FCtf = "0";
                } else {
                    FCtf = "1";
                }
            }
        });

        dcMsgBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(View v, boolean on) {
                if (on) {
                    DCtf = "0";
                } else {
                    DCtf = "1";
                }
            }
        });

        shMsgBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(View v, boolean on) {
                if (on) {
                    SHtf = "0";
                } else {
                    SHtf = "1";
                }
            }
        });


        signMsgBtn.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(View v, boolean on) {
                if (on) {
                    QStf = "0";
                } else {
                    QStf = "1";
                }
            }
        });
    }

    @Override
    public void handlerMessage(Message msg) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        switch (msg.what) {
            case Constants.REQUEST_SUCC:

                if (msg.obj != null) {
                    GetAPPNotifySetDataValue bean = (GetAPPNotifySetDataValue) msg.obj;
                    search(bean);
                }

                break;
            case Constants.SETMSG_SUCC:
                DialogUtils.showMessageDialog(CargoSettingsActivity.this, "提示", "设置成功", "确定", "", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        if (CustomDialogListener.BUTTON_NO == closeType) {

                        } else if (CustomDialogListener.BUTTON_OK == closeType) {
                            finish();

                        }

                    }
                });
//                    DialogUtils.showNormalToast(Utils.getResourcesString(R.string.cargo_set_Save_settings_succ));
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(CargoSettingsActivity.this, Utils.getResourcesString(R.string.request_error));
                break;
        }
    }
}
