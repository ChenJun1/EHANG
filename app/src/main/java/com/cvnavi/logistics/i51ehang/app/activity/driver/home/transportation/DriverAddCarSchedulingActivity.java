package com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickuprcord.CargoLocationMapActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.location.DriverCarTreeListActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.AddSendCarPlanSelectOrgActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation.ChoiceShuttleActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.CommonAddressActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.DestinationBean;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.carfleet.CarFleetMonitorBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.employee.me.GetDestinationResponse;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarSchedulingDriver;
import com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.AddCarShiftRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.DestinationCallback;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.biz.IDestinationBiz;
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
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.utils.VerifyPhoneNumUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.DateTimePickDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/6/28.
 * 添加车辆排班
 */
public class DriverAddCarSchedulingActivity extends BaseActivity implements LocationChooseCarCallback, DriverChoiceCallback, LineChoiceCallback, IDestinationBiz {
    private static String TAG = DriverAddCarSchedulingActivity.class.getName();
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
    @BindView(R.id.go_tv)
    TextView goTv;
    @BindView(R.id.schedule_date_et)
    EditText scheduleDateEt;
    @BindView(R.id.car_tv)
    TextView carTv;
    @BindView(R.id.choice_car_et)
    EditText choiceCarEt;
    @BindView(R.id.gua_car_tv)
    TextView guaCarTv;
    @BindView(R.id.choice_gua_car_et)
    EditText choiceGuaCarEt;
    @BindView(R.id.line_tv)
    TextView lineTv;
    @BindView(R.id.line_et)
    EditText lineEt;
    @BindView(R.id.ban_tv)
    TextView banTv;
    @BindView(R.id.ban_et)
    EditText banEt;
    @BindView(R.id.main_drive_tv)
    TextView mainDriveTv;
    @BindView(R.id.main_drive_et)
    EditText mainDriveEt;
    @BindView(R.id.main_choice)
    TextView mainChoice;
    @BindView(R.id.ReceiveMan_Tel_tv)
    TextView ReceiveManTelTv;
    @BindView(R.id.phone_num_et)
    EditText phoneNumEt;
    @BindView(R.id.assistant_drive_tv)
    TextView assistantDriveTv;
    @BindView(R.id.assistant_drive_et)
    EditText assistantDriveEt;
    @BindView(R.id.un_main_choice)
    TextView unMainChoice;
    @BindView(R.id.un_main_tv)
    TextView unMainTv;
    @BindView(R.id.un_main_phone_num_et)
    EditText unMainPhoneNumEt;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.sl)
    ScrollView sl;
    @BindView(R.id.zheng_tv)
    TextView zhengTv;
    @BindView(R.id.zheng_tb)
    ToggleButton zhengTb;
    @BindView(R.id.des_tv)
    TextView desTv;
    @BindView(R.id.des_tb)
    ToggleButton desTb;
    @BindView(R.id.place_tv)
    TextView placeTv;
    @BindView(R.id.desc_tv)
    TextView descTv;
    @BindView(R.id.choice_desc_iv)
    ImageView choiceDescIv;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    @BindView(R.id.route_ll)
    LinearLayout routeLl;
    @BindView(R.id.place_rl)
    RelativeLayout placeRl;
    @BindView(R.id.bottom_driver)
    LinearLayout bottomDriver;
    @BindView(R.id.diy_place)
    RelativeLayout diyPlace;
    @BindView(R.id.org_tv)
    TextView orgTv;
    @BindView(R.id.titp_org)
    TextView titpOrg;

    private Context mContext;
    private mCarInfo mCarInfo;
    private mLineInfo mLineInfo;
    private mCarSchedulingDriver mainDriverInfo;
    private mCarSchedulingDriver assistantDriverInfo;

    private boolean isChoiceMainDriver = false;
    private boolean isChoiceAssistantDriver = false;
    private DateTimePickDialog scheduleDia = null;

    private String CarCodeGua;
    private String CarCodeKeyGua;
    private String Shuttle_Oid;

    public static final String INTENT_DATA_CAR_CODE = "INTENT_DATA_CAR_CODE";
    public static final String INTENT_DATA_CAR_CODE_KEY = "INTENT_DATA_CAR_CODE_KEY";
    public static final String INTENT_DATA_ADD_TYPE = "INTENT_DATA_ADD_TYPE";
    public static final int INTENT_DATA_ADD_FROM_LOCATION = 1;//从定位信息进入
    public static final int INTENT_DATA_ADD_FROM_PLAN = 0;//从发车详情进入
    private String carCode = "";
    private String carCodeKey = "";
    private int type;
    private static final int Animation_Time = 500;
    private String SendWay = "1";//配载的
    private String BLat = "";
    private String BLng = "";
    private boolean bottmCheck = false;
    private String Org_key;


    public static void startActivity(Activity activity, String carCode, String carCodeKey, int requestCode, int type) {
        Intent intent = new Intent(activity, DriverAddCarSchedulingActivity.class);
        intent.putExtra(INTENT_DATA_CAR_CODE, carCode);
        intent.putExtra(INTENT_DATA_CAR_CODE_KEY, carCodeKey);
        intent.putExtra(INTENT_DATA_ADD_TYPE, type);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statr_car);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        scheduleDateEt.setText(DateUtil.getLastNDaysForm(0, DateUtil.FORMAT_YMD));
        carCode = getIntent().getStringExtra(INTENT_DATA_CAR_CODE);
        carCodeKey = getIntent().getStringExtra(INTENT_DATA_CAR_CODE_KEY);
        type = getIntent().getIntExtra(INTENT_DATA_ADD_TYPE, INTENT_DATA_ADD_FROM_PLAN);

        if (MyApplication.getInstance().getLoginInfo() != null && MyApplication.getInstance().getLoginInfo().DataValue != null) {
            orgTv.setText(MyApplication.getInstance().getLoginInfo().DataValue.Org_Name);
            this.Org_key = MyApplication.getInstance().getLoginInfo().DataValue.Org_Key;
        }

        if (type == INTENT_DATA_ADD_FROM_LOCATION) {
            if (TextUtils.isEmpty(carCode) && TextUtils.isEmpty(carCodeKey)) {
                titleTv.setText(R.string.add_car_scheduling);
                sl.setVisibility(View.GONE);
                DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverAddCarSchedulingActivity.this, "无法新建计划!", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        finish();
                    }
                });
            } else {
                titleTv.setText(carCode);
                choiceCarEt.setText(carCode);
                choiceCarEt.setOnClickListener(null);
            }
        } else {
            titleTv.setText(R.string.add_car_scheduling);
        }

        DriverChoiceCallBackManager.newInstance().add(this);
        LocationChooseCarCallBackManager.newStance().add(this);
        LineChoiceCallBackManager.newInstance().add(this);
        DestinationCallback.getCallback().setCallback(this);


        zhengTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AnimatorSet aSet = new AnimatorSet();
                    aSet.playTogether(
                            ObjectAnimator.ofFloat(diyPlace, "translationX", routeLl.getWidth(), 0)
                                    .setDuration(Animation_Time)

                    );
                    aSet.start();
                    diyPlace.setVisibility(View.VISIBLE);
                    SendWay = "0";
                } else {
                    AnimatorSet aSet = new AnimatorSet();
                    aSet.playTogether(
                            ObjectAnimator.ofFloat(diyPlace, "translationX", 0, routeLl.getWidth())
                                    .setDuration(Animation_Time),
                            ObjectAnimator.ofFloat(routeLl, "translationY", diyPlace.getHeight(), 0)
                                    .setDuration(Animation_Time),

                            ObjectAnimator.ofFloat(bottomLl, "translationY", diyPlace.getHeight(), 0)
                                    .setDuration(Animation_Time));
                    aSet.start();
                    diyPlace.setVisibility(View.GONE);
                    SendWay = "1";
                    if (bottmCheck) {
                        desTb.performClick();
                    }
                }
            }
        });

        desTb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AnimatorSet aSet = new AnimatorSet();
                    aSet.playTogether(ObjectAnimator.ofFloat(routeLl, "alpha", 1.0f, 0)
                                    .setDuration(Animation_Time),
                            ObjectAnimator.ofFloat(routeLl, "translationX", 0, routeLl.getWidth())
                                    .setDuration(Animation_Time),

                            ObjectAnimator.ofFloat(placeRl, "translationX", routeLl.getWidth(), 0)
                                    .setDuration(Animation_Time),
                            ObjectAnimator.ofFloat(bottomDriver, "translationY", -placeRl.getHeight(), 0)
                                    .setDuration(Animation_Time),

                            ObjectAnimator.ofFloat(bottomLl, "translationY", 0f, -routeLl.getHeight())
                                    .setDuration(Animation_Time));
                    aSet.start();
                    placeRl.setVisibility(View.VISIBLE);
                    bottmCheck = true;
                    /**
                     * 打开自定义目的地
                     * 重置view数据
                     */
                    mLineInfo = null;
                    lineEt.setText("");
                    MyApplication.getInstance().setShuttleList(null);
                } else {
                    AnimatorSet aSet = new AnimatorSet();
                    aSet.playTogether(ObjectAnimator.ofFloat(routeLl, "alpha", 0f, 1.0f)
                                    .setDuration(Animation_Time),
                            ObjectAnimator.ofFloat(routeLl, "translationX", routeLl.getWidth(), 0)
                                    .setDuration(Animation_Time),
                            ObjectAnimator.ofFloat(placeRl, "translationX", 0, routeLl.getWidth())
                                    .setDuration(Animation_Time),
                            ObjectAnimator.ofFloat(bottomDriver, "translationY", 0, -placeRl.getHeight())
                                    .setDuration(Animation_Time),
                            ObjectAnimator.ofFloat(bottomLl, "translationY", -routeLl.getHeight(), 0)
                                    .setDuration(Animation_Time));
                    aSet.start();
                    placeRl.setVisibility(View.INVISIBLE);

                    /**
                     * 关闭自定义目的地
                     * 重置view数据
                     */
                    descTv.setText("");
                    bottmCheck = false;
                    BLng = "";
                    BLat = "";
                }
            }
        });

    }


    @OnClick({R.id.back_llayout, R.id.choice_car_et, R.id.main_choice, R.id.desc_tv, R.id.choice_desc_iv, R.id.un_main_choice, R.id.line_et, R.id.schedule_date_et, R.id.confirm_btn, R.id.choice_gua_car_et, R.id.ban_et, R.id.org_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                DriverAddCarSchedulingActivity.this.finish();
                break;
            case R.id.choice_car_et:
                DriverCarTreeListActivity.startActivity(DriverAddCarSchedulingActivity.this, 0x12, DriverCarTreeListActivity.TYPE_CAR);
                break;
            case R.id.choice_gua_car_et:
                DriverCarTreeListActivity.startActivity(DriverAddCarSchedulingActivity.this, 0x12, DriverCarTreeListActivity.TYPE_GUA);
                break;
            case R.id.main_choice:
                isChoiceMainDriver = true;
                isChoiceAssistantDriver = false;
                Intent intent = new Intent(DriverAddCarSchedulingActivity.this, ChoiceDriverActivity.class);
                intent.putExtra(Constants.DriverList, Utils.getResourcesString(R.string.driver_manager));
                startActivity(intent);
                break;
            case R.id.un_main_choice:
                isChoiceMainDriver = false;
                isChoiceAssistantDriver = true;
                Intent intent1 = new Intent(DriverAddCarSchedulingActivity.this, ChoiceDriverActivity.class);
                intent1.putExtra(Constants.DriverList, Utils.getResourcesString(R.string.driver_manager));
                startActivity(intent1);
                break;
            case R.id.line_et:
                showActivity(DriverAddCarSchedulingActivity.this, ChoiceLineActivity.class);
                break;
            case R.id.ban_et:
                ChoiceShuttleActivity.startActivity(this, 0x20);
                break;
            case R.id.schedule_date_et:
                scheduleDia = new DateTimePickDialog();
                scheduleDia.dateWithOutYYDialog(mContext, scheduleDateEt, null);
                break;
            case R.id.choice_desc_iv:
                startActivity(new Intent(DriverAddCarSchedulingActivity.this, CargoLocationMapActivity.class));
                break;
            case R.id.desc_tv:
                CommonAddressActivity.startActivity(DriverAddCarSchedulingActivity.this, CommonAddressActivity.TYPE_FROM_PLAN);
                break;
            case R.id.org_tv:
                //选择机构号
                AddSendCarPlanSelectOrgActivity.start(this);
                break;
            case R.id.confirm_btn:
                if (verifyData() == false) {
                    return;
                }

                AddCarShiftRequest addCarShiftRequest = new AddCarShiftRequest();

                //发车日期
                if (scheduleDia == null) {
                    addCarShiftRequest.CarCode_Date = DateUtil.getCurDateStr(DateUtil.FORMAT_YMD);
                } else {
                    addCarShiftRequest.CarCode_Date = scheduleDia.getResultData();
                }

                if (type == INTENT_DATA_ADD_FROM_PLAN && mCarInfo != null) {
                    //车牌号
                    addCarShiftRequest.CarCode = mCarInfo.CarCode;
                    //车辆key
                    addCarShiftRequest.CarCode_Key = mCarInfo.CarCode_Key;

                } else {
                    //车牌号
                    addCarShiftRequest.CarCode = carCode;
                    //车辆key
                    addCarShiftRequest.CarCode_Key = carCodeKey;
                }


                if (mLineInfo != null) {
                    //线路Id
                    addCarShiftRequest.Line_Oid = mLineInfo.Line_Oid;
                }


                //主驾驶
                addCarShiftRequest.MainDriver = mainDriveEt.getEditableText().toString();
                addCarShiftRequest.MainDriver_Tel = phoneNumEt.getEditableText().toString();

                //副驾驶
                addCarShiftRequest.SecondDriver = assistantDriveEt.getEditableText().toString();
                addCarShiftRequest.SecondDriver_Tel = unMainPhoneNumEt.getEditableText().toString();

                //挂车
                if (CarCodeGua != null && CarCodeGua.contains("挂")) {
                    addCarShiftRequest.BoxCarCode_Key = CarCodeKeyGua;
                }

                //班次
                addCarShiftRequest.Shuttle_No = banEt.getEditableText().toString();

                //班次号
                addCarShiftRequest.Shuttle_Oid = Shuttle_Oid;

                //发车方式
                addCarShiftRequest.SendWay = SendWay;

                //目的地
                if (!TextUtils.isEmpty(descTv.getText().toString())) {
                    addCarShiftRequest.Destination = descTv.getText().toString();
                }

                //上传经纬度
                addCarShiftRequest.BLat = BLat;
                addCarShiftRequest.BLng = BLng;


                //添加状态 0-系统添加、1-TMS添加、2-ehang 添加、3-VMS添加
                addCarShiftRequest.Add_Status = "2";

                //添加发车机构
                addCarShiftRequest.Org_key = Org_key;

                DataRequestBase dataRequestBase = new DataRequestBase();
                dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
                dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
                dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
                dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
                dataRequestBase.DataValue = addCarShiftRequest;

                MLog.json(GsonUtil.newInstance().toJsonStr(dataRequestBase));
                VolleyManager.newInstance().PostJsonRequest(TAG, TMSService.AddCarShift_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MLog.print(response.toString());
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

        if (VerifyPhoneNumUtil.isMobileNO(phoneNumEt.getText().toString()) == false) {
            DialogUtils.showNormalToast("请输入正确的电话！");
            return false;
        }

//        if (SendWay.equals("1")) {
//            if (TextUtils.isEmpty(lineEt.getEditableText().toString())) {
//                DialogUtils.showNormalToast(getString(R.string.add_car_scheduling_choose_route));
//                return false;
//            }
//        }

        if (TextUtils.isEmpty(orgTv.getEditableText().toString()) || TextUtils.isEmpty(Org_key)) {
            DialogUtils.showNormalToast("请选择机构号！");
            return false;
        }


        if (DateUtil.compareDate(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getEditableText().toString())) {
            DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
            return false;
        }

        if (scheduleDateEt.getText().toString().contains("-")) {
            if (Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD)) < 0) {
                DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
                return false;
            }
        } else {
            if (Integer.valueOf(DateUtil.calcDiffDays2(DateUtil.getCurDateStr(DateUtil.FORMAT_YMD_SN), scheduleDateEt.getText().toString(), DateUtil.FORMAT_YMD_SN)) < 0) {
                DialogUtils.showNormalToast(getString(R.string.schedule_date_before_cur_date));
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
                    DialogUtils.showSuccToast(getResources().getString(R.string.add_succ));
                    EventBus.getDefault().post("Succ");
                    DriverAddCarSchedulingActivity.this.finish();
                    break;
                case Constants.REQUEST_FAIL:
                    if (msg.obj != null) {
                        DialogUtils.showFailToast((String) msg.obj);
                    } else {
                        DialogUtils.showFailToast(Utils.getResourcesString(R.string.add_fail));
                    }
                    break;
            }
        }
    };


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setDescinfo(GetDestinationResponse.DataValueBean info) {
        if (info != null) {
            descTv.setText(info.getDestination());
            this.BLat = info.getBLat() + "";
            this.BLng = info.getBLng() + "";
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setOrg(CarFleetMonitorBean.DataValueBean info) {
        if (info != null) {
            orgTv.setText(info.getOrg_Name());
            this.Org_key = info.getOrg_Key();
        }
    }


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
                phoneNumEt.setText(mCarSchedulingDriver.Driver_Tel);
            }

            if (isChoiceAssistantDriver) {
                assistantDriverInfo = mCarSchedulingDriver;
                assistantDriveEt.setText(mCarSchedulingDriver.Driver);
                unMainPhoneNumEt.setText(mCarSchedulingDriver.Driver_Tel);
            }
        }
    }

    @Override
    public void onLineChoice(mLineInfo lineInfo) {
        if (lineInfo != null) {
            mLineInfo = lineInfo;
            lineEt.setText(lineInfo.Line_Name);
            MyApplication.getInstance().setShuttleList(lineInfo.getShuttleList());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x12:
                    CarCodeGua = data.getStringExtra("CarCode");
                    CarCodeKeyGua = data.getStringExtra("CarCodeKey");
                    if (CarCodeGua != null && CarCodeGua.contains("挂")) {
                        choiceGuaCarEt.setText(CarCodeGua);
                    } else {
                        choiceGuaCarEt.setText("无");
                    }
                    break;
                case 0x20:
                    banEt.setText(data.getStringExtra("Shuttle_No"));
                    Shuttle_Oid = data.getStringExtra("Shuttle_Oid");
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().setShuttleList(null);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setDestination(DestinationBean bean) {
        //从选择地图界面得到选中的界面的经纬度，目的地名称
        if (bean != null) {
            descTv.setText(bean.ArriveAddress);
            this.BLat = bean.ArriveAddressBLat;
            this.BLng = bean.ArriveAddressBLng;
        }
    }
}
