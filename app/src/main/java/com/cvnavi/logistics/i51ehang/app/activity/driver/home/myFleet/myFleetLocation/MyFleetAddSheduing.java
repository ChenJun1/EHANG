package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.myFleetLocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.location.DriverCarTreeListActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.ChoiceDriverActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.ChoiceLineActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarSchedulingDriver;
import com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.AddCarShiftRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
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
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.utils.VerifyPhoneNumUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.DateTimePickDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.DateTimeTwoPickDialog;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/9.
 */
public class MyFleetAddSheduing extends BaseActivity implements LocationChooseCarCallback, DriverChoiceCallback, LineChoiceCallback {
    private static String TAG = MyFleetAddSheduing.class.getName();

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
    @BindView(R.id.sl)
    ScrollView sl;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout operationLlayout;
    @BindView(R.id.car_tv)
    TextView carTv;
    @BindView(R.id.gua_car_tv)
    TextView guaCarTv;
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
    private com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo mLineInfo;
    private mCarSchedulingDriver mainDriverInfo;
    private mCarSchedulingDriver assistantDriverInfo;

    private boolean isChoiceMainDriver = false;
    private boolean isChoiceAssistantDriver = false;

    private DateTimePickDialog scheduleDia = null;
    private DateTimeTwoPickDialog preDia = null;

    public static final String INTENT_DATA_CAR_CODE = "INTENT_DATA_CAR_CODE";
    public static final String INTENT_DATA_CAR_CODE_KEY = "INTENT_DATA_CAR_CODE_KEY";
    private String carCode;
    private String carCodeKey;

    private String CarCodeGua;
    private String CarCodeKeyGua;


    public static void startActivity(Activity activity, String carCode, String carCodeKey, int requestCode) {
        Intent intent = new Intent(activity, MyFleetAddSheduing.class);
        intent.putExtra(INTENT_DATA_CAR_CODE, carCode);
        intent.putExtra(INTENT_DATA_CAR_CODE_KEY, carCodeKey);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_scheduling);
        ButterKnife.bind(this);
        carCode = getIntent().getStringExtra(INTENT_DATA_CAR_CODE);
        carCodeKey = getIntent().getStringExtra(INTENT_DATA_CAR_CODE_KEY);
        if (TextUtils.isEmpty(carCode) && TextUtils.isEmpty(carCodeKey)) {
            sl.setVisibility(View.GONE);
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetAddSheduing.this, "无法添加排班!", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
        }
        titleTv.setText(carCode);
        choiceCarEt.setText(carCode);
        choiceCarEt.setOnClickListener(null);
        sl.setVisibility(View.VISIBLE);

        mContext = this;

        scheduleDateEt.setText(DateUtil.strOldFormat2NewFormat(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN), DateUtil.FORMAT_YMD_SN, DateUtil.FORMAT_MD));
        preTimeEt.setText(DateUtil.strOldFormat2NewFormat(DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));

        LocationChooseCarCallBackManager.newStance().add(this);
        DriverChoiceCallBackManager.newInstance().add(this);
        LineChoiceCallBackManager.newInstance().add(this);
    }

    @OnClick({R.id.back_llayout, R.id.choice_car_et, R.id.main_drive_et, R.id.assistant_drive_et, R.id.line_et, R.id.schedule_date_et, R.id.pre_time_et, R.id.confirm_btn, R.id.choice_gua_car_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                MyFleetAddSheduing.this.finish();
                break;
//            case R.id.choice_car_et:
//                showActivity(MyFleetAddSheduing.this, DriverCarTreeListActivity.class);
//                break;
            case R.id.choice_car_et:
//                showActivity(DriverAddCarSchedulingActivity.this, DriverCarTreeListActivity.class);
                DriverCarTreeListActivity.startActivity(MyFleetAddSheduing.this, 0x12, DriverCarTreeListActivity.TYPE_CAR);
                break;
            case R.id.choice_gua_car_et:
                DriverCarTreeListActivity.startActivity(MyFleetAddSheduing.this, 0x12, DriverCarTreeListActivity.TYPE_GUA);
                break;
            case R.id.main_drive_et:
                isChoiceMainDriver = true;
                isChoiceAssistantDriver = false;
                Intent intent = new Intent(MyFleetAddSheduing.this, ChoiceDriverActivity.class);
                intent.putExtra(Constants.DriverList, Utils.getResourcesString(R.string.driver_manager));
                startActivity(intent);
                break;
            case R.id.assistant_drive_et:
                isChoiceMainDriver = false;
                isChoiceAssistantDriver = true;
                Intent intent1 = new Intent(MyFleetAddSheduing.this, ChoiceDriverActivity.class);
                intent1.putExtra(Constants.DriverList, Utils.getResourcesString(R.string.driver_manager));
                startActivity(intent1);
                break;
            case R.id.line_et:
                showActivity(MyFleetAddSheduing.this, ChoiceLineActivity.class);
                break;
            case R.id.schedule_date_et:
                scheduleDia = new DateTimePickDialog();
                scheduleDia.dateWithOutYYDialog(mContext, scheduleDateEt, null);
                break;
            case R.id.pre_time_et:
//                TimePopupWindow timePopupWindow = new TimePopupWindow(MyFleetAddSheduing.this, TimePopupWindow.Type.HOURS_MINS);
//                timePopupWindow.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {
//                    @Override
//                    public void onTimeSelect(Date date) {
//                        preTimeEt.setText(DateUtil.date2Str(date, DateUtil.FORMAT_HM));
//                    }
//                });
//                timePopupWindow.setCyclic(true);
//                timePopupWindow.showAtLocation(preTimeEt, Gravity.BOTTOM, 0, 0, new Date());

                preDia = new DateTimeTwoPickDialog(DateUtil.getBeforeOneDayTime());
                preDia.dateWithOutYYDialog(MyFleetAddSheduing.this, preTimeEt, null);
                break;
            case R.id.confirm_btn:
                if (verifyData() == false) {
                    return;
                }

                AddCarShiftRequest addCarShiftRequest = new AddCarShiftRequest();

                addCarShiftRequest.CarCodeSerial_Oid = carCodeKey;
                if (CarCodeGua != null && CarCodeGua.contains("挂")) {
                    addCarShiftRequest.BoxCarCode_Key = CarCodeKeyGua;
                }
                addCarShiftRequest.MainDriverSerial_Oid = mainDriverInfo.Serial_Oid;
                addCarShiftRequest.Driver_Tel = phoneNumEt.getEditableText().toString();
                addCarShiftRequest.SecondDriverSerial_Oid = assistantDriverInfo.Serial_Oid;
                addCarShiftRequest.Line_Oid = mLineInfo.Line_Oid;
//                addCarShiftRequest.CarCode_Date = DateUtil.str2YMD_SNStr(scheduleDateEt.getEditableText().toString());
                if (scheduleDia == null) {
                    addCarShiftRequest.CarCode_Date = DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN);
                } else {
                    addCarShiftRequest.CarCode_Date = scheduleDia.getResultData();
                }

//                addCarShiftRequest.Forecast_Leave_DateTime = preTimeEt.getEditableText().toString();
                if (preDia == null) {
                    addCarShiftRequest.Forecast_Leave_DateTime = DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM);

                } else {
                    addCarShiftRequest.Forecast_Leave_DateTime = preDia.getResultData();
                }
//                addCarShiftRequest.CarCode_NO = "1";
                addCarShiftRequest.CarSchedule_Note = remarkEt.getEditableText().toString();

                DataRequestBase dataRequestBase = new DataRequestBase();
                dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
                dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
                dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
                dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
                dataRequestBase.DataValue = addCarShiftRequest;

                LogUtil.d("-->>" + GsonUtil.newInstance().toJson(dataRequestBase).toString());
                VolleyManager.newInstance().PostJsonRequest(TAG, TMSService.AddCarShift_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
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
        if (TextUtils.isEmpty(choiceCarEt.getEditableText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_car));
            return false;
        }
        if (TextUtils.isEmpty(mainDriveEt.getEditableText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_car_main));
            return false;
        }
        if (TextUtils.isEmpty(assistantDriveEt.getEditableText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_car_not_main));
            return false;
        }

        if (VerifyPhoneNumUtil.isMobileNO(phoneNumEt.getText().toString()) == false) {
            DialogUtils.showNormalToast("请输入正确的电话！");
            return false;
        }
        if (TextUtils.isEmpty(lineEt.getEditableText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_route));
            return false;
        }



        if (DateUtil.compareDate(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getEditableText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
            return false;
        }

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

        if (DateUtil.compareData(DateUtil.getCurDateStr(DateUtil.FORMAT_MDHM), preTimeEt.getEditableText().toString(), DateUtil.FORMAT_MDHM)) {
            DialogUtils.showNormalToast(getString(R.string.pre_time_after_today));
            return false;
        }

        return true;
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    DialogUtils.showSuccToast(getResources().getString(R.string.add_succ));
                    MyFleetAddSheduing.this.finish();
                    break;
                case Constants.REQUEST_FAIL:
                    if (String.valueOf(msg.obj).equals("null") || TextUtils.isEmpty(String.valueOf(msg.obj))) {
                        DialogUtils.showFailToast(getResources().getString(R.string.add_fail));
                    } else {
                        DialogUtils.showFailToast(String.valueOf(msg.obj));
                    }
                    break;
            }
        }
    };


    @Override
    public void OnMonitorLoadCarCode(mCarInfo mCarInfo) {
//        if (mCarInfo != null) {
//            this.mCarInfo = mCarInfo;
//            choiceCarEt.setText(mCarInfo.CarCode);
//        }
    }

    @Override
    public void OnHistorLoadCarCode(mCarInfo mCarInfo) {
//        if (mCarInfo != null) {
//            this.mCarInfo = mCarInfo;
//            choiceCarEt.setText(mCarInfo.CarCode);
//        }
    }

    @Override
    public void onDriverChoice(mCarSchedulingDriver mCarSchedulingDriver) {
        if (mCarSchedulingDriver != null) {
            if (isChoiceMainDriver) {
                mainDriverInfo = mCarSchedulingDriver;
                mainDriveEt.setText(mCarSchedulingDriver.Driver);
                phoneNumEt.setText(mCarSchedulingDriver.Driver_Tel);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0x12) {
            CarCodeGua = data.getStringExtra("CarCode");
            CarCodeKeyGua = data.getStringExtra("CarCodeKey");
            LogUtil.d("-->> code= " + CarCodeGua);
            LogUtil.d("-->>key= " + CarCodeKeyGua);
            if (CarCodeGua != null && CarCodeGua.contains("挂")) {
                choiceGuaCarEt.setText(CarCodeGua);
            } else {
                choiceGuaCarEt.setText("无");
            }
        }
    }
}
