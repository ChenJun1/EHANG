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
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
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
 * 描述：统计发车统计 发车明细筛选（透明主题）
 ************************************************************************************/


public class SendCarDetailSelectActivity extends BaseActivity {

    @BindView(R.id.peizai_all)
    RadioButton peizaiAll;
    @BindView(R.id.sign_state_rg)
    RadioGroup signStateRg;
    @BindView(R.id.peizai_ll)
    LinearLayout peizaiLl;
    @BindView(R.id.daoche_all)
    RadioButton daocheAll;
    @BindView(R.id.daoche_state_rg)
    RadioGroup daocheStateRg;
    @BindView(R.id.clear)
    TextView clear;
    @BindView(R.id.sign_ok)
    TextView signOk;
    @BindView(R.id.zheng_tv)
    RadioButton zhengTv;
    @BindView(R.id.pei_tv)
    RadioButton peiTv;
    @BindView(R.id.has_arrive)
    RadioButton hasArrive;
    @BindView(R.id.no_arrive)
    RadioButton noArrive;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    private String signState;//配载方式
    private String unUseState;//到车方式


    private ArrayList<RadioButton> signList;
    private ArrayList<RadioButton> useList;

    private int signId;//签收状态id
    private int useId;//使用状态


    /**
     * 启动界面
     *
     * @param activity
     */
    public static void startActivity(Activity activity, int signId, int useId) {
        Intent intent = new Intent(activity, SendCarDetailSelectActivity.class);
        intent.putExtra("sigId", signId);
        intent.putExtra("useId", useId);
        activity.startActivity(intent);
    }


    private void createList() {
        if (signList == null) {
            signList = new ArrayList<>();
        }

        //将控件加入待选的list
        signList.add(peizaiAll);
        signList.add(zhengTv);
        signList.add(peiTv);

        if (useList == null) {
            useList = new ArrayList<>();
        }

        //将控件加入待选的list
        useList.add(daocheAll);
        useList.add(hasArrive);
        useList.add(noArrive);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_send_car_detail_select);
        ButterKnife.bind(this);
        titltTv.setText("筛选");

        signId = getIntent().getIntExtra("sigId", 0);
        useId = getIntent().getIntExtra("useId", 0);

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

        daocheStateRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                finish();
                break;
            case R.id.sign_ok:
                MLog.print("");
                EventSelect select = new EventSelect();
                if (signState.equals("整车")) {
                    select.setSignState("0");
                    signId = 1;
                } else if (signState.equals("配货")) {
                    select.setSignState("1");
                    signId = 2;
                } else {
                    select.setSignState("");
                    signId = 0;
                }
                select.setSignId(signId);

                if (unUseState.equals("全部")) {
                    select.setUseState("0");
                    useId = 0;
                } else if (unUseState.equals("已到车")) {
                    select.setUseState("2");
                    useId = 1;
                } else if (unUseState.equals("未到车")) {
                    select.setUseState("1");
                    useId = 2;
                }
                select.setUseId(useId);
                EventBus.getDefault().post(select);
                finish();
                break;
        }
    }


}
