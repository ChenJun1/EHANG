package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.EventSelect;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午4:00
 * 描述：统计—货单列表筛选
 ************************************************************************************/


public class OrderDetailSelectActivty extends BaseActivity {
    @BindView(R.id.sign_state_rg)
    RadioGroup signStateRg;
    @BindView(R.id.sign_ll)
    LinearLayout signLl;
    @BindView(R.id.unuse_state_rg)
    RadioGroup unuseStateRg;
    @BindView(R.id.clear)
    TextView clear;
    @BindView(R.id.sign_ok)
    TextView signOk;
    @BindView(R.id.sign_all)
    RadioButton signAll;
    @BindView(R.id.un_sign)
    RadioButton unSign;
    @BindView(R.id.part_sign)
    RadioButton partSign;
    @BindView(R.id.has_sign)
    RadioButton hasSign;
    @BindView(R.id.unuse_all)
    RadioButton unuseAll;
    @BindView(R.id.unuse)
    RadioButton unuse;
    @BindView(R.id.has_unuse)
    RadioButton hasUnuse;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    private String signState;
    private String unUseState;

    private ArrayList<RadioButton> signList;
    private ArrayList<RadioButton> useList;

    private int signId;
    private int useId;

    public static void startActivty(Activity activity, int signId, int useId) {
        Intent intent = new Intent(activity, OrderDetailSelectActivty.class);
        intent.putExtra("sigId", signId);
        intent.putExtra("useId", useId);
        activity.startActivity(intent);
    }

    /**
     * 创建可以使用的list
     */
    private void createList() {
        if (signList == null) {
            signList = new ArrayList<>();
        }
        signList.add(signAll);
        signList.add(unSign);
        signList.add(partSign);
        signList.add(hasSign);

        if (useList == null) {
            useList = new ArrayList<>();
        }

        useList.add(unuseAll);
        useList.add(unuse);
        useList.add(hasUnuse);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_order_detail_select);
        ButterKnife.bind(this);
        signId = getIntent().getIntExtra("sigId", 0);
        useId = getIntent().getIntExtra("useId", 0);
        titltTv.setText("筛选");

        createList();

        signList.get(signId).performClick();
        useList.get(useId).performClick();
        signState = signList.get(signId).getText().toString();
        unUseState = useList.get(useId).getText().toString();

        signStateRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = (RadioButton) findViewById(checkedId);
                signState = btn.getText().toString();
            }
        });

        unuseStateRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = (RadioButton) findViewById(checkedId);
                unUseState = btn.getText().toString();
            }
        });
    }


    @OnClick({R.id.clear, R.id.sign_ok, R.id.back_llayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.clear:
                //清楚上次的选中的结果
                finish();
                break;
            case R.id.sign_ok:
                EventSelect select = new EventSelect();
                if (signState.equals("已签收")) {
                    select.setSignState("3");
                    signId = 3;
                } else if (signState.equals("未签收")) {
                    select.setSignState("1");
                    signId = 1;
                } else if (signState.equals("部分")) {
                    select.setSignState("2");
                    signId = 2;
                } else {
                    select.setSignState("");
                    signId = 0;
                }

                select.setSignId(signId);

                if (unUseState.equals("全部")) {
                    select.setUseState("");
                    useId = 0;
                } else if (unUseState.equals("未作废")) {
                    select.setUseState("正常");
                    useId = 1;
                } else if (unUseState.equals("已作废")) {
                    select.setUseState("已作废");
                    useId = 2;
                }
                select.setUseId(useId);
                EventBus.getDefault().post(select);
                finish();
                break;
        }
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }
}
