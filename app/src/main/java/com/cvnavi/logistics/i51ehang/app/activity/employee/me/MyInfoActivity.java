package com.cvnavi.logistics.i51ehang.app.activity.employee.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:10
*描述：我的个人信息
************************************************************************************/



public class MyInfoActivity extends BaseActivity {
    @BindView(R.id.login_name_tv)
    TextView loginNameTv;
    @BindView(R.id.company_tv)
    TextView companyTv;
    @BindView(R.id.org_tv)
    TextView orgTv;
    @BindView(R.id.tel_tv)
    TextView telTv;
    @BindView(R.id.titlt_tv)
    TextView titltTv;

    public static void startActivity(Context activity) {
        Intent intent = new Intent(activity, MyInfoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        titltTv.setText("我的信息");
        GetAppLoginResponse loginInfo = MyApplication.getInstance().getLoginInfo();
        if (loginInfo != null && loginInfo.DataValue != null) {
            loginNameTv.setText(loginInfo.DataValue.User_Name);
            companyTv.setText(loginInfo.DataValue.Company_Name);
            orgTv.setText(loginInfo.DataValue.Org_Name);
            telTv.setText(loginInfo.DataValue.User_Tel);
        } else {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyInfoActivity.this, "个人信息丢失。。。", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
        }
    }


    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }
}
