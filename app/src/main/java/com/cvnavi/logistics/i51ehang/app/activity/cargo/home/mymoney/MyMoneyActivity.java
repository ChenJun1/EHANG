package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.mymoney;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

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
 * Depict: 货主 我的钱包 页面
 */

public class MyMoneyActivity extends BaseActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        ButterKnife.bind(this);
        DialogUtils.showMessageDialog(this, "提示", "此功能暂未开放", "确定", "取消", new CustomDialogListener() {
            @Override
            public void onDialogClosed(int closeType) {
                if (CustomDialogListener.BUTTON_NO == closeType) {
                    finish();
                } else if (CustomDialogListener.BUTTON_OK == closeType) {
                    finish();
                }
            }
        });
        initview();
    }

    private void initview() {
        titleTv.setText("我的钱包");
    }

    @OnClick(R.id.back_llayout)
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_llayout:
                finish();
                break;
        }
    }
}
