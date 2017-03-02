package com.cvnavi.logistics.i51ehang.app.activity.driver.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.DateTimeTwoPickDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午2:12
*描述：带日期选择器的搜索界面
************************************************************************************/

public class SearchActivity extends BaseActivity {


    public static final String BEGIN_DATA = "BEGIN_DATA";
    public static final String END_DATA = "END_DATA";
    public static final String DATA_TYPE_ALL = "DATA_TYPE_ALL";
    public static final String DATA_DAY_NUM = "DATA_DAY_NUM";
    public static final int timeType = 10;
    public static final int REQUEST_CODE = 0X13;//该界面的请求码
    private String dateFormat;
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
    private int dayNum = 30;

    public static void startActivity(Activity activity, int requestCode, String dataFormmat, int dayNum) {
        Intent intent = new Intent(activity, SearchActivity.class);
        intent.putExtra(DATA_TYPE_ALL, dataFormmat);
        intent.putExtra(DATA_DAY_NUM, dayNum);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_schedulig_search);
        ButterKnife.bind(this);

        dateFormat = getIntent().getStringExtra(DATA_TYPE_ALL);
        dayNum = getIntent().getIntExtra(DATA_DAY_NUM, 30);
        titltTv.setText("搜索");
        beginEt.setInputType(InputType.TYPE_NULL);
        endEt.setInputType(InputType.TYPE_NULL);
    }


    @OnClick({R.id.begin_et, R.id.end_et, R.id.back_llayout, R.id.search_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.begin_et:
                showDia(beginEt);
                break;
            case R.id.end_et:
                showDia(endEt);
                break;
            case R.id.back_llayout:
                finish();
                break;
            case R.id.search_btn:
                if (TextUtils.isEmpty(beginEt.getText().toString()) || TextUtils.isEmpty(endEt.getText().toString())) {
                    DialogUtils.showWarningToast(Utils.getResourcesString(R.string.search_data_not_allow_null));
                } else {
                    if ((!DateUtil.compareData(beginEt.getText().toString(), endEt.getText().toString(), DateUtil.FORMAT_YMD)) && Integer.parseInt(DateUtil.calcDiffDays(beginEt.getText().toString(), endEt.getText().toString())) <= dayNum) {
                        //只有开始日期小于结束日期并且在dayNum范围内可以查询
                        Intent intent = new Intent();
                        intent.putExtra(BEGIN_DATA, beginEt.getText().toString());
                        intent.putExtra(END_DATA, endEt.getText().toString());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        if (Integer.parseInt(DateUtil.calcDiffDays(beginEt.getText().toString(), endEt.getText().toString())) > dayNum) {
                            DialogUtils.showMessageDialogOfDefaultSingleBtn(this, String.format("查询日期不能大于%1$d天",dayNum));
                        } else {
                            DialogUtils.showMessageDialogOfDefaultSingleBtn(this, Utils.getResourcesString(R.string.car_scheduling_search_star_more_than_end));
                        }
                    }
                }
                break;
        }
    }

    private void showDia(TextView editText) {
        if ((DateUtil.FORMAT_YMDHM).equals(dateFormat)){
            DateTimeTwoPickDialog preDialog = new DateTimeTwoPickDialog(DateUtil.getNowTime());
            preDialog.dateTimePicKDialog(SearchActivity.this, editText, null);
        } else {
            DateTimePickDialog dateTimePicKDialog = new DateTimePickDialog();
            dateTimePicKDialog.dateTimePicKDialog(this, editText, null);
        }
    }

}
