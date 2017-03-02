package com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarSchedulingDriver;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.EditCarShiftRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.location.LocationChooseCarCallback;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.transportation.DriverChoiceCallback;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.transportation.LineChoiceCallback;
import com.cvnavi.logistics.i51ehang.app.callback.manager.DriverChoiceCallBackManager;
import com.cvnavi.logistics.i51ehang.app.callback.manager.LineChoiceCallBackManager;
import com.cvnavi.logistics.i51ehang.app.callback.manager.LocationChooseCarCallBackManager;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.VerifyPhoneNumUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.DateTimeTwoPickDialog;

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
*创建时间：2017/1/17 下午2:11
*描述：编辑车辆排班
************************************************************************************/

public class DriverEditCarSchedulingActivity extends BaseActivity implements LocationChooseCarCallback, DriverChoiceCallback, LineChoiceCallback {
    private static String TAG = DriverEditCarSchedulingActivity.class.getName();

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.choice_car_et)
    EditText choiceCarEt;
    @BindView(R.id.main_drive_et)
    EditText mainDriveEt;
    @BindView(R.id.phone_num_et)
    EditText phoneNumEt;
    @BindView(R.id.assistant_drive_et)
    EditText assistantDriveEt;
    @BindView(R.id.line_et)
    EditText lineEt;
    @BindView(R.id.schedule_date_et)
    EditText scheduleDateEt;
    @BindView(R.id.pre_time_et)
    EditText preTimeEt;
    @BindView(R.id.remark_et)
    EditText remarkEt;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout operationLlayout;
    @BindView(R.id.car_tv)
    TextView carTv;
    @BindView(R.id.car_gua_tv)
    TextView carGuaTv;
    @BindView(R.id.choice_gua_car_et)
    EditText choiceGuaCarEt;
    @BindView(R.id.main_drive_tv)
    TextView mainDriveTv;
    @BindView(R.id.ReceiveMan_Tel_tv)
    TextView ReceiveManTelTv;
    @BindView(R.id.assistant_drive_tv)
    TextView assistantDriveTv;
    @BindView(R.id.line_tv)
    TextView lineTv;
    @BindView(R.id.schedule_date_tv)
    TextView scheduleDateTv;
    @BindView(R.id.pre_time_tv)
    TextView preTimeTv;

    private Context mContext;
    private com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo mCarInfo;
    private com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo mLineInfo;
    private mCarSchedulingDriver mainDriverInfo;
    private mCarSchedulingDriver assistantDriverInfo;

    private boolean isChoiceMainDriver = false;
    private boolean isChoiceAssistantDriver = false;

    private mCarShift carShift;

    private DateTimeTwoPickDialog preDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_scheduling);
        ButterKnife.bind(this);

        mContext = this;
        carShift = (mCarShift) getIntent().getSerializableExtra(Constants.EDIT_CAR_SCHEDULING);
        titleTv.setText(R.string.edit_car_scheduling);
        confirmBtn.setText(R.string.edit_car_scheduling_sure);

        LocationChooseCarCallBackManager.newStance().add(this);
        DriverChoiceCallBackManager.newInstance().add(this);
        LineChoiceCallBackManager.newInstance().add(this);

        initData();
    }

    private void initData() {
        choiceCarEt.setText(carShift.CarCode);
        mainDriveEt.setText(carShift.MainDriver);
        phoneNumEt.setText(carShift.Driver_Tel);
        assistantDriveEt.setText(carShift.SecondDriver);
        choiceGuaCarEt.setText(carShift.BoxCarCode);
        lineEt.setText(carShift.Line_Name);
//        scheduleDateEt.setText(carShift.CarCode_Date);

        scheduleDateEt.setText(DateUtil.strOldFormat2NewFormat(carShift.CarCode_Date, DateUtil.FORMAT_YMD_SN, DateUtil.FORMAT_MD));

        if (TextUtils.isEmpty(carShift.Forecast_Leave_DateTime) == false) {
            try {
                LogUtil.d("-->>dataStr = " + carShift.Forecast_Leave_DateTime);
                preTimeEt.setText(DateUtil.strOldFormat2NewFormat(carShift.Forecast_Leave_DateTime, DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));
            } catch (Exception ex) {
                preTimeEt.setText(DateUtil.strOldFormat2NewFormat(carShift.Forecast_Leave_DateTime, DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));
            }
        }
        remarkEt.setText(carShift.CarSchedule_Note);
    }

    @OnClick({R.id.back_llayout, R.id.choice_car_et, R.id.main_drive_et, R.id.assistant_drive_et, R.id.line_et, R.id.schedule_date_et, R.id.pre_time_et, R.id.confirm_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                DriverEditCarSchedulingActivity.this.finish();
                break;
            case R.id.choice_car_et:
                //2016/7/14先不加
//                showActivity(DriverEditCarSchedulingActivity.this, DriverCarTreeListActivity.class);
                break;
            case R.id.main_drive_et:
                isChoiceMainDriver = true;
                isChoiceAssistantDriver = false;
                showActivity(DriverEditCarSchedulingActivity.this, ChoiceDriverActivity.class);
                break;
            case R.id.assistant_drive_et:
                isChoiceMainDriver = false;
                isChoiceAssistantDriver = true;
                showActivity(DriverEditCarSchedulingActivity.this, ChoiceDriverActivity.class);
                break;
            case R.id.line_et:
                //2016/7/14先不加
//                showActivity(DriverEditCarSchedulingActivity.this, ChoiceLineActivity.class);
                break;
            case R.id.schedule_date_et:
                //2016/7/14先不加
//                DateTimePickDialog dateTimePicKDialog = new DateTimePickDialog();
//                dateTimePicKDialog.dateTimePicKDialog(mContext, scheduleDateEt, null);
                break;
            case R.id.pre_time_et:
                preDialog = new DateTimeTwoPickDialog(DateUtil.getBeforeOneDayTime());
                preDialog.dateWithOutYYDialog(DriverEditCarSchedulingActivity.this, preTimeEt, null);
                break;
            case R.id.confirm_btn:
                if (verifyData() == false) {
                    return;
                }

                EditCarShiftRequest editCarShiftRequest = new EditCarShiftRequest();

                editCarShiftRequest.BoxCarCode_Key = carShift.BoxCarCode_Key;
                editCarShiftRequest.CarCodeSerial_Oid = mCarInfo == null ? carShift.CarCodeSerial_Oid : mCarInfo.CarCode_Key;
//                editCarShiftRequest.CarCodeSerial_Oid = mCarInfo == null ? carShift.LPSCarCode_Key : mCarInfo.CarCode_Key;
                editCarShiftRequest.MainDriverSerial_Oid = mainDriverInfo == null ? carShift.MainDriverSerial_Oid : mainDriverInfo.Serial_Oid;
                editCarShiftRequest.Driver_Tel = phoneNumEt.getText().toString();
                editCarShiftRequest.SecondDriverSerial_Oid = assistantDriverInfo == null ? carShift.SecondDriverSerial_Oid : assistantDriverInfo.Serial_Oid;
                editCarShiftRequest.Line_Oid = mLineInfo == null ? carShift.Line_Oid : mLineInfo.Line_Oid;

//                if (scheduleDateEt.getText().toString().contains("-")) {
//                    editCarShiftRequest.CarCode_Date = DateUtil.str2YMD_SNStr(scheduleDateEt.getText().toString());
//                } else {
//                    editCarShiftRequest.CarCode_Date = scheduleDateEt.getText().toString();
//                }

                //   2016/7/14因为不能点击排班日期所以给赋值为传进来的值
                editCarShiftRequest.CarCode_Date = carShift.CarCode_Date;


                if (preDialog == null) {
                    editCarShiftRequest.Forecast_Leave_DateTime = carShift.Forecast_Leave_DateTime;
                } else {
                    if (preDialog.getResultData() == null) {
                        editCarShiftRequest.Forecast_Leave_DateTime = carShift.Forecast_Leave_DateTime;
                    } else {
                        editCarShiftRequest.Forecast_Leave_DateTime = preDialog.getResultData();
                    }
                }


//                addCarShiftRequest.CarCode_NO = "1";
                editCarShiftRequest.CarSchedule_Note = remarkEt.getText().toString();
                editCarShiftRequest.Serial_Oid = carShift.Serial_Oid;
                DataRequestBase dataRequestBase = new DataRequestBase();
                dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
                dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
                dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
                dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
                dataRequestBase.DataValue = editCarShiftRequest;

                VolleyManager.newInstance().PostJsonRequest(TAG, TMSService.EditCarShift_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DataResponseBase responseBase = JsonUtils.parseData(response.toString(), DataResponseBase.class);
                        Message msg = Message.obtain();
                        if (responseBase.Success) {
                            myHandler.sendEmptyMessage(Constants.REQUEST_SUCC);
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
                        msg.what = Constants.REQUEST_FAIL;
                        myHandler.sendMessage(msg);
                    }
                });
                break;
        }
    }

    private boolean verifyData() {
        if (TextUtils.isEmpty(choiceCarEt.getText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_car));
            return false;
        }
        if (TextUtils.isEmpty(mainDriveEt.getText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_car_main));
            return false;
        }
        if (TextUtils.isEmpty(lineEt.getText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_route));
            return false;
        }
        if (VerifyPhoneNumUtil.isMobileNO(phoneNumEt.getText().toString()) == false) {
            DialogUtils.showNormalToast("请输入正确的电话！");
            return false;
        }

//        if (scheduleDateEt.getText().toString().contains("-")) {
//            if (DateUtil.compareDate2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getText().toString())) {
//                DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
//                return false;
//            }
//        } else{
//            if (DateUtil.compareDate2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN), scheduleDateEt.getText().toString())) {
//                DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
//                return false;
//            }
//        }

        if (scheduleDateEt.getText().toString().contains("-")) {
            LogUtil.d("-->>" + Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD)));
            if (Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD)) < 0) {
                DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
                return false;
            }
        } else {
            LogUtil.d("-->>" + Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD_SN)));
            if (Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD_SN)) < 0) {
                DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
                return false;
            }
        }

        if (scheduleDateEt.getText().toString().contains("-")) {
            LogUtil.d("-->>" + Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD)));
            if (Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD)) > 1) {
                DialogUtils.showNormalToast(getString(R.string.schedule_date_after_cur_date_for_2));
                return false;
            }
        } else {
            LogUtil.d("-->>" + Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD_SN)));
            if (Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD_SN)) > 1) {
                DialogUtils.showNormalToast(getString(R.string.schedule_date_after_cur_date_for_2));
                return false;
            }
        }
        return true;
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    DialogUtils.showSuccToast(getResources().getString(R.string.edit_succ));
                    DriverEditCarSchedulingActivity.this.finish();
                    break;
                case Constants.REQUEST_FAIL:
                    if (String.valueOf(msg.obj).equals("null") || TextUtils.isEmpty(String.valueOf(msg.obj))) {
                        DialogUtils.showFailToast(getResources().getString(R.string.edit_fail));
                    } else {
                        DialogUtils.showFailToast(String.valueOf(msg.obj));
                    }
                    break;
            }
        }
    };


    @Override
    public void OnMonitorLoadCarCode(mCarInfo mCarInfo) {
        if (mCarInfo != null) {
            this.mCarInfo = mCarInfo;
            choiceCarEt.setText(mCarInfo.CarCode);
        }
    }

    @Override
    public void OnHistorLoadCarCode(mCarInfo mCarInfo) {
        if (mCarInfo != null) {
            this.mCarInfo = mCarInfo;
            choiceCarEt.setText(mCarInfo.CarCode);
        }
    }

    @Override
    public void onDriverChoice(mCarSchedulingDriver mCarSchedulingDriver) {
        if (mCarSchedulingDriver != null) {
            if (isChoiceMainDriver) {
                mainDriverInfo = mCarSchedulingDriver;
                mainDriveEt.setText(mCarSchedulingDriver.Driver);
            }

            if (isChoiceAssistantDriver) {
                assistantDriverInfo = mCarSchedulingDriver;
                assistantDriveEt.setText(mCarSchedulingDriver.Driver);
            }
        }
    }

    @Override
    public void onLineChoice(mLineInfo lineInfo) {
        if (lineInfo != null) {
            mLineInfo = lineInfo;
            lineEt.setText(lineInfo.Line_Name);
        }
    }


}
