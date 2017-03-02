package com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPopupWindow;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.DriverInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.cache.BasicDataBuffer;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.utils.VerifyPhoneNumUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.LoadingDialog;

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
*创建时间：2017/1/17 下午2:09
*描述：编辑车辆排班
************************************************************************************/

public class DriverAddActivity extends BaseActivity {
    private static String TAG = DriverAddActivity.class.getName();

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titlt_tv;
    @BindView(R.id.drive_et)
    EditText driveEt;
    @BindView(R.id.driver_tel_et)
    EditText driverTelEt;
    @BindView(R.id.Driver_License_Type_et)
    EditText DriverLicenseTypeEt;
    @BindView(R.id.driver_Card_Oid_et)
    EditText driverCardOidEt;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private Context mContext;
    private mCarShift carShift;
    private DriverInfo driverInfo;

    OptionsPopupWindow optionPopWindow;

    private LoadingDialog lodingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_driver);
        ButterKnife.bind(this);
        mContext = this;
        driverInfo = new DriverInfo();
        titlt_tv.setText(Utils.getResourcesString(R.string.driver_add));
        lodingDialog=LoadingDialog.getInstance(this);
        optionPopWindow = new OptionsPopupWindow(this);
    }

    @OnClick({R.id.Driver_License_Type_et, R.id.btnSubmit, R.id.back_llayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Driver_License_Type_et:
                optionPopWindow.setPicker(BasicDataBuffer.newInstance().getDrivingLicenseValueList());
                optionPopWindow.setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3) {
                        DriverLicenseTypeEt.setText(BasicDataBuffer.newInstance().getDrivingLicenseValueList().get(options1));
                    }
                });
                optionPopWindow.showAtLocation(DriverLicenseTypeEt, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.btnSubmit:
                if (checkContext()) {
                    lodingDialog.show();
                    addDriverRequest(TMSService.AddDriver_Request_Url);
                }
                break;
            case R.id.back_llayout:
                finish();
                break;
            default:
                break;
        }
    }

    private Boolean checkContext() {

        if (TextUtils.isEmpty(driveEt.getText())) {
            DialogUtils.showNormalToast("请输入司机姓名！");
            return false;
        }
        if (TextUtils.isEmpty(driverTelEt.getText())) {
            DialogUtils.showNormalToast("请输入司机电话！");
            return false;
        } else if (VerifyPhoneNumUtil.isMobileNO(driverTelEt.getText().toString()) == false) {
            DialogUtils.showNormalToast("请输入正确的电话！");
            return false;
        }

        if (TextUtils.isEmpty(DriverLicenseTypeEt.getText())) {
            DialogUtils.showNormalToast("请选中驾照类型！");
            return false;
        }

        if (TextUtils.isEmpty(driverCardOidEt.getText())) {
            DialogUtils.showNormalToast("请输入身份证号！");
            return false;
        }
        if (VerifyPhoneNumUtil.isIDCard(driverCardOidEt.getText().toString()) == false) {
            DialogUtils.showNormalToast("请输入正确的身份证号！");
            return false;
        }

        driverInfo.Driver = driveEt.getText().toString();
        driverInfo.Driver_Tel = driverTelEt.getText().toString();
        driverInfo.Driver_License_Type = DriverLicenseTypeEt.getText().toString();
        driverInfo.Driver_License_Type_Oid = BasicDataBuffer.newInstance().getDriverLicenseCode(driverInfo.Driver_License_Type);
        driverInfo.Card_Oid = driverCardOidEt.getText().toString();

        return true;
    }

    private void addDriverRequest(final String Url) {

        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = driverInfo; //JsonUtils.toJsonData(getDriverListRequest);

        LogUtil.d("-->>" + GsonUtil.newInstance().toJson(dataRequestBase).toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url,
                GsonUtil.newInstance().toJson(dataRequestBase),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DataResponseBase responseBase = JsonUtils.parseData(response.toString(), DataResponseBase.class);

                        Message msg = Message.obtain();
                        if (responseBase.Success) {
                            msg.what = Constants.REQUEST_SUCC;
                            myHandler.sendMessage(msg);
                        } else {
                            msg.obj = responseBase.ErrorText;
                            msg.what = Constants.REQUEST_FAIL;
                            myHandler.sendMessage(msg);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
            if(lodingDialog!=null){
                lodingDialog.dismiss();
            }
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    DialogUtils.showNormalToast("司机添加成功！");
                    DriverAddActivity.this.finish();
                    break;
                case Constants.REQUEST_FAIL:
                    if (String.valueOf(msg.obj).equals("null") || TextUtils.isEmpty(String.valueOf(msg.obj))) {
                        DialogUtils.showFailToast(getResources().getString(R.string.add_fail));
                    } else {
                        DialogUtils.showFailToast(String.valueOf(msg.obj));
                    }
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverAddActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;


            }
        }
    };


}
