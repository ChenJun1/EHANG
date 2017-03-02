package com.cvnavi.logistics.i51ehang.app.activity.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.CargoHomeFragment;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickupthedoor.CargoPickUpTheDoorActivity;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.me.CargoMeFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.DriverMainViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.widget.viewpage.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;


/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主端入口
 */
public class CargoMainActivity extends BaseActivity {

    @BindView(R.id.vp)
    NoScrollViewPager vp;
    @BindView(R.id.cargo_home_text)
    TextView cargoHomeText;
    @BindView(R.id.cargo_home_layout)
    LinearLayout cargoHomeLayout;
    @BindView(R.id.cargo_sm_text)
    TextView cargoSmText;
    @BindView(R.id.cargo_sm_layout)
    LinearLayout cargoSmLayout;
    @BindView(R.id.cargo_me_text)
    TextView cargoMeText;
    @BindView(R.id.cargo_me_layout)
    LinearLayout cargoMeLayout;


    private DriverMainViewPagerAdapter adapter;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();
    private long exitTime;
    private Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cargo);
        ButterKnife.bind(this);
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.resumePush(this);
        }
        init();
//        myIntent = new Intent(CargoMainActivity.this, MyCheckLoginService.class);
//        CargoMainActivity.this.startService(myIntent);//发送Intent启动Service
    }

    /**
     * 初始化控件
     */
    private void init() {

        if (list == null) {
            list = new ArrayList<>();
        }
        cargoHomeText.setTextColor(0xFF3a95e7);
        cargoMeText.setTextColor(0xFF717171);
        list.add(CargoHomeFragment.instantiation());
        list.add(CargoMeFragment.instantiation());
        if (adapter == null) {
            adapter = new DriverMainViewPagerAdapter(getSupportFragmentManager(), list);
        }

        vp.setAdapter(adapter);
        vp.setNoScroll(true);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp.setCurrentItem(0);
    }

    private void changeState(int position) {
        if (position == 0) {
            cargoHomeText.setTextColor(0xFF3a95e7);
            cargoMeText.setTextColor(0xFF717171);

        } else if (position == 1) {
            cargoHomeText.setTextColor(0xFF717171);
            cargoMeText.setTextColor(0xFF3a95e7);
        }
        vp.setCurrentItem(position);
    }

    @OnClick({R.id.cargo_home_layout, R.id.cargo_sm_layout, R.id.cargo_me_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cargo_home_layout:
                cargoHomeText.setTextColor(0xFF3a95e7);
                cargoMeText.setTextColor(0xFF717171);
                vp.setCurrentItem(0,false);
                break;
            case R.id.cargo_sm_layout:

                startActivity(new Intent(CargoMainActivity.this, CargoPickUpTheDoorActivity.class));
                break;
            case R.id.cargo_me_layout:

                cargoHomeText.setTextColor(0xFF717171);
                cargoMeText.setTextColor(0xFF3a95e7);
                vp.setCurrentItem(1,false);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplication(), "再按一次退出!", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
//
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (LogUtil.debug) {
//            ArrayList<ActionSheetItemInfo> list = new ArrayList<>();
//            list.add(new ActionSheetItemInfo("测试", new ActionSheetDialog.OnSheetItemClickListener() {
//                @Override
//                public void onClick(int which) {
//                    startActivity(new Intent(CargoMainActivity.this, MainActivity.class));
//
//                }
//            }));
//
//            DialogUtils.showActionSheetDialog(this, "测试", list);
//
//        }
//        return true;
//
//    }
//
//

}
