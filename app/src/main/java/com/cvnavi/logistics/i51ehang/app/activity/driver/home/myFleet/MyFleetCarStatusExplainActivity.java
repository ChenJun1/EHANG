package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午10:17
*描述：车辆状态说明
************************************************************************************/

public class MyFleetCarStatusExplainActivity extends BaseActivity {
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
    @BindView(R.id.contact_us)
    TextView contactUs;
    @BindView(R.id.i_know_btn)
    Button iKnowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_explain);
        ButterKnife.bind(this);
        titleTv.setText("车辆状态说明");
    }

    @OnClick({R.id.back_llayout, R.id.contact_us, R.id.i_know_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.contact_us:
                ContextUtil.callAlertDialog("", MyFleetCarStatusExplainActivity.this);
                break;
            case R.id.i_know_btn:
                finish();
                break;
        }
    }
}
