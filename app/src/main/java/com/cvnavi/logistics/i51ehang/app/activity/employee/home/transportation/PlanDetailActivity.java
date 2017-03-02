package com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:07
*描述：计划详情
************************************************************************************/


public class PlanDetailActivity extends BaseActivity {
    public static String DATA_INFO = "DATA_INFO";
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
    @BindView(R.id.type_btn)
    TextView typeBtn;
    @BindView(R.id.car_code)
    EditText carCode;
    @BindView(R.id.state_tv)
    TextView stateTv;
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
    @BindView(R.id.place_tv)
    TextView placeTv;
    @BindView(R.id.place_et)
    EditText placeEt;
    @BindView(R.id.main_drive_tv)
    TextView mainDriveTv;
    @BindView(R.id.main_drive_et)
    EditText mainDriveEt;
    @BindView(R.id.phone_num_et)
    EditText phoneNumEt;
    @BindView(R.id.assistant_drive_tv)
    TextView assistantDriveTv;
    @BindView(R.id.assistant_drive_et)
    EditText assistantDriveEt;
    @BindView(R.id.un_main_phone_num_et)
    EditText unMainPhoneNumEt;
    @BindView(R.id.sl)
    ScrollView sl;
    @BindView(R.id.main_tel)
    TextView mainTel;
    @BindView(R.id.second_tel)
    TextView secondTel;

    private mCarShift info;

    public static void startActivity(Context activity, mCarShift info) {
        Intent intent = new Intent(activity, PlanDetailActivity.class);
        intent.putExtra(DATA_INFO, info);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_detail);
        ButterKnife.bind(this);
        info = (mCarShift) getIntent().getSerializableExtra(DATA_INFO);
        initView();
    }

    private void initView() {
        titleTv.setText("计划详情");
        if (info == null) {
            return;
        }

        if (!TextUtils.isEmpty(info.BoxCarCode)) {
            //挂车号
            choiceGuaCarEt.setText(info.BoxCarCode);
        } else {
            choiceGuaCarEt.setText("无");
        }

        if (!TextUtils.isEmpty(info.Line_Name)) {
            //线路
            lineEt.setText(info.Line_Name);
        } else {
            lineEt.setText("无");
        }

        if (!TextUtils.isEmpty(info.Shuttle_No)) {
            //班次
            banEt.setText(info.Shuttle_No);
        } else {
            banEt.setText("无");
        }

        if (!TextUtils.isEmpty(info.MainDriver)) {
            //主司机
            mainDriveEt.setText(info.MainDriver);
        } else {
            mainDriveEt.setText("无");
        }

        if (!TextUtils.isEmpty(info.MainDriver_Tel)) {
            //主司机手机号
            mainTel.setText("手机号: " + info.MainDriver_Tel);
            mainTel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContextUtil.callAlertDialog(info.MainDriver_Tel, PlanDetailActivity.this);
                }
            });
        } else {
            mainTel.setText("手机号：无");
        }

        if (!TextUtils.isEmpty(info.SecondDriver)) {
            //副司机
            assistantDriveEt.setText(info.SecondDriver);
        } else {
            assistantDriveEt.setText("无");
        }

        if (!TextUtils.isEmpty(info.SecondDriver_Tel)) {
            //副司机手机号
            secondTel.setText("手机号：" + info.SecondDriver_Tel);
            secondTel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContextUtil.callAlertDialog(info.SecondDriver_Tel, PlanDetailActivity.this);
                }
            });
        } else {
            secondTel.setText("手机号：无");
        }

        if (!TextUtils.isEmpty(info.Destination)) {
            //目的地
            placeEt.setText(info.Destination);
        } else {
            placeEt.setText("无");
        }

        if (!TextUtils.isEmpty(info.CarCode)) {
            //车牌号
            carCode.setText(info.CarCode);
        } else {
            carCode.setText("无");
        }


        if (!TextUtils.isEmpty(info.SendWay)) {
            if (info.SendWay.equals("0")) {
                typeBtn.setBackgroundResource(R.drawable.shape_zheng);
                typeBtn.setText("整");

            } else {
                typeBtn.setText("配");
                typeBtn.setBackgroundResource(R.drawable.shape_pei);
            }
        } else {
            typeBtn.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(info.Schedule_Status)) {
            switch (Integer.parseInt(info.Schedule_Status)) {
                case 0:
                    stateTv.setText("待发车");
                    break;
                case 1:
                    stateTv.setText("已发车");
                    break;
                case 2:
                    stateTv.setText("已完成");
                    break;
                case 4:
                    stateTv.setText("全部");
                    break;

            }
        } else {
            stateTv.setText("无");
        }

    }


    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }
}
