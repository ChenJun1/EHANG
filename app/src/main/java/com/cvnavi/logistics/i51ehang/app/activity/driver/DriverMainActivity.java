package com.cvnavi.logistics.i51ehang.app.activity.driver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MainActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.addressbook.DriverAddressBookFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.DrHomeFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.me.DriverMeFragement;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ActionSheetItemInfo;
import com.king.photo.util.PublicWay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict:司机首页
 */
public class DriverMainActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.container_flayout)
    FrameLayout containerFlayout;
    @BindView(R.id.homepage_rb)
    RadioButton homepageRb;
    @BindView(R.id.addressbooks_rb)
    RadioButton addressbooksRb;
    @BindView(R.id.me_rb)
    RadioButton meRb;
    private long mTime;
    private Intent myIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("-->>onCreate");
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.resumePush(this);
        }
        setContentView(R.layout.activity_main_driver);
        ButterKnife.bind(this);
//        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        int memorySize = activityManager.getMemoryClass();//获取app分配的内存
        initview();

        //监控账号多登陆服务
//        myIntent = new Intent(DriverMainActivity.this, MyCheckLoginService.class);
//        DriverMainActivity.this.startService(myIntent);//发送Intent启动Service
    }

    private void initview() {
        homepageRb.setOnCheckedChangeListener(this);
        addressbooksRb.setOnCheckedChangeListener(this);
        meRb.setOnCheckedChangeListener(this);
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
                    return DrHomeFragment.getInstance();
                case R.id.addressbooks_rb:
                    return DriverAddressBookFragment.getInstance();
                case R.id.me_rb:
                    return DriverMeFragement.getInstance();
                default:
                    return DrHomeFragment.getInstance();
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
                for (Activity activity : PublicWay.activityList) {
                    if(activity.isFinishing()==false) {
                        activity.finish();
                    }
                }
                PublicWay.activityList.clear();
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
                    startActivity(new Intent(DriverMainActivity.this, MainActivity.class));

                }
            }));
            DialogUtils.showActionSheetDialog(this, "测试", list);
        }
        return true;
    }
}
