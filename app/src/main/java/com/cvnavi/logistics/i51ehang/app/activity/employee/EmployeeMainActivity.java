package com.cvnavi.logistics.i51ehang.app.activity.employee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.addressbook.EmployeeAddressBookFragment;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.MyEmployeeHomeFragement;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.weituo.WeituoDetailActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.EmployeeMeFragment;
import com.cvnavi.logistics.i51ehang.app.activity.employee.test.MyTestActivity;
import com.cvnavi.logistics.i51ehang.app.jpush.TestActivity;
import com.cvnavi.logistics.i51ehang.app.service.MyCheckLoginService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetItemInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 员工主activity
 */

public class EmployeeMainActivity extends BaseActivity implements OnCheckedChangeListener {

    @BindView(R.id.container_flayout)
    FrameLayout containerFlayout;
    @BindView(R.id.homepage_rb)
    RadioButton homepageRb;
    @BindView(R.id.addressbook_rb)
    RadioButton addressbooksRb;
    @BindView(R.id.me_rb)
    RadioButton meRb;

    private Intent myIntent;
    private long mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("-->>onCreate");
//        ActivityStackManager.getInstance().addActivity(this);
        setContentView(R.layout.activity_main_employee);
        ButterKnife.bind(this);
        homepageRb = (RadioButton) findViewById(R.id.homepage_rb);
        addressbooksRb = (RadioButton) findViewById(R.id.addressbook_rb);
        meRb = (RadioButton) findViewById(R.id.me_rb);
        containerFlayout = (FrameLayout) findViewById(R.id.container_flayout);
        initview();
        //启动定时检查异地登录的一个服务
//        myIntent = new Intent(EmployeeMainActivity.this, MyCheckLoginService.class);
//        EmployeeMainActivity.this.startService(myIntent);//发送Intent启动Service
    }

    /**
     * 初始化view控件
     */
    private void initview() {
        homepageRb.setOnCheckedChangeListener(this);
        addressbooksRb.setOnCheckedChangeListener(this);
        meRb.setOnCheckedChangeListener(this);
        //获取首页查询码的长度
        Utils.getQueryLength();
        //代码获取点击效果
        homepageRb.performClick();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            Fragment fragment = (Fragment) mFragmentPagerAdapter.instantiateItem(containerFlayout, buttonView.getId());
            mFragmentPagerAdapter.setPrimaryItem(containerFlayout, 0, fragment);
            mFragmentPagerAdapter.finishUpdate(containerFlayout);
        }
    }

    private FragmentPagerAdapter mFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case R.id.homepage_rb:
                    return MyEmployeeHomeFragement.instantiation();
                case R.id.addressbook_rb:
                    return EmployeeAddressBookFragment.instantiation();
                case R.id.me_rb:
                    return EmployeeMeFragment.instantiation();
                default:
                    return MyEmployeeHomeFragement.instantiation();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mTime > 2000) {
                Toast.makeText(getApplication(), "再按一次退出!", Toast.LENGTH_SHORT).show();
                mTime = System.currentTimeMillis();
            } else {
                if (myIntent != null) {
                    stopService(myIntent);
                }
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (Constants.IS_DEBUG) {
            ArrayList<ActionSheetItemInfo> list = new ArrayList<>();
            list.add(new ActionSheetItemInfo("测试", new ActionSheetDialog.OnSheetItemClickListener() {
                @Override
                public void onClick(int which) {
                    showActivity(EmployeeMainActivity.this, MyTestActivity.class);
                }
            }));
            DialogUtils.showActionSheetDialog(this, "测试", list);
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myIntent != null) {
            stopService(myIntent);
        }
    }
}
