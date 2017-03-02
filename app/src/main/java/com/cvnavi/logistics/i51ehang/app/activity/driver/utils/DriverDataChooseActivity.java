package com.cvnavi.logistics.i51ehang.app.activity.driver.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.DateTimePickDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict:司机 时间选择器页面
 */
public class DriverDataChooseActivity extends BaseActivity {

    public static final String BEGIN_DATA = "BEGIN_DATA";//开始时间标识
    public static final String END_DATA = "END_DATA";//结束时间标识

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.begin_et)
    TextView beginEt;
    @BindView(R.id.end_et)
    TextView endEt;
    @BindView(R.id.search_btn)
    Button searchBtn;

    private String beginTime = "";
    private String endTime = "";

    private DateTimePickDialog dateTimePicKDialog;
    private DateTimePickDialog dateTimePicKDialog2;

    public static void startActivity(Activity activity, int requestCode, String beginTime, String endTime) {
        Intent intent = new Intent(activity, DriverDataChooseActivity.class);
        intent.putExtra(BEGIN_DATA, beginTime);
        intent.putExtra(END_DATA, endTime);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_schedulig_search);
        ButterKnife.bind(this);

        titltTv.setText("搜索");
        if (!TextUtils.isEmpty(getIntent().getStringExtra(BEGIN_DATA))
                &&!TextUtils.isEmpty(getIntent().getStringExtra(END_DATA)) ) {
            beginTime = getIntent().getStringExtra(BEGIN_DATA);
            endTime = getIntent().getStringExtra(END_DATA);
            beginEt.setText(beginTime);
            endEt.setText(endTime);
        } else {
            beginEt.setText(DateUtil.getBeforeDayNsTime(6, DateUtil.FORMAT_YMD));
            endEt.setText(DateUtil.getNowTime(DateUtil.FORMAT_YMD));
        }
    }

    @OnClick({R.id.begin_et, R.id.end_et, R.id.back_llayout, R.id.search_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.begin_et:
                showDia(beginEt);
                break;
            case R.id.end_et:
                showDiaEnd(endEt);
                break;
            case R.id.back_llayout:
                finish();
                break;
            case R.id.search_btn:

                if (TextUtils.isEmpty(beginEt.getText().toString()) || TextUtils.isEmpty(endEt.getText().toString())) {
                    DialogUtils.showWarningToast(Utils.getResourcesString(R.string.search_data_not_allow_null));
                } else {
                    if ((!DateUtil.compareData(beginEt.getText().toString(), endEt.getText().toString(), DateUtil.FORMAT_YMD)) && Integer.parseInt(DateUtil.calcDiffDays(beginEt.getText().toString(), endEt.getText().toString())) <= 93) {
                        //只有开始日期小于结束日期并且在93范围内可以查询
                        Intent intent = new Intent();
                        intent.putExtra(BEGIN_DATA, beginEt.getText().toString());
                        intent.putExtra(END_DATA, endEt.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        if (Integer.parseInt(DateUtil.calcDiffDays(beginEt.getText().toString(), endEt.getText().toString())) > 31) {
                            DialogUtils.showMessageDialogOfDefaultSingleBtn(this, Utils.getResourcesString(R.string.car_scheduling_search_more_than_90));
                        } else {
                            DialogUtils.showMessageDialogOfDefaultSingleBtn(this, Utils.getResourcesString(R.string.car_scheduling_search_star_more_than_end));
                        }
                    }
                }

                break;
        }
    }

    private void showDia(TextView editText) {
        if (dateTimePicKDialog == null) {
            dateTimePicKDialog = new DateTimePickDialog();
        }
        dateTimePicKDialog.dateTimePicKDialog(this, editText, null);
    }


    private void showDiaEnd(TextView editText) {
        if (dateTimePicKDialog2 == null) {
            dateTimePicKDialog2 = new DateTimePickDialog();
        }
        dateTimePicKDialog2.dateTimePicKDialog(this, editText, null);
    }

}
