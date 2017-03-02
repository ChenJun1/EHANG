package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.DriverMainViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;

import java.util.ArrayList;

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
 * Depict: 货主 我的货单
 */
public class MyOrderActivity extends BaseActivity {

    private static final int LAST_DAY = 0;
    private static final int LAST_THREE_DAY = 1;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    private DriverMainViewPagerAdapter adapter;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();
    public static final int REQUEST_CODE_SEARCH = 0x12;


    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.last_day_tv)
    TextView lastDayTv;
    @BindView(R.id.last_day_view)
    View lastDayView;
    @BindView(R.id.last_day_rl)
    RelativeLayout lastDayRl;
    @BindView(R.id.last_three_day_tv)
    TextView lastThreeDayTv;
    @BindView(R.id.last_three_day_view)
    View lastThreeDayView;
    @BindView(R.id.last_three_day_rl)
    RelativeLayout lastThreeDayRl;
    @BindView(R.id.vp)
    ViewPager vp;

    private int curPosition;
    public String beginDate = null;
    public String endDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        titltTv.setText("我的货单");
        rightLl.setVisibility(View.VISIBLE);
        addLl.setVisibility(View.GONE);
        lastDayTv.setTextColor(0xff1f89d4);
        if (list == null) {
            list = new ArrayList<Fragment>();
        }

        list.add(MyOrderHasSignFragment.getInstance());
        list.add(MyOrderAllFragment.getInstance());
        if (adapter == null) {
            adapter = new DriverMainViewPagerAdapter(getSupportFragmentManager(), list);
        }

        vp.setAdapter(adapter);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                changeState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        vp.setCurrentItem(0);
    }

    public void setCurPosition(int position) {
        this.curPosition = position;
    }

    public int getCurPosition() {
        return curPosition;
    }

    private void changeState(int position) {
        if (position == LAST_DAY) {
            lastDayTv.setTextColor(0xff1f89d4);
            lastDayView.setVisibility(View.VISIBLE);
            lastThreeDayTv.setTextColor(0xff5b5b5b);
            lastThreeDayView.setVisibility(View.INVISIBLE);
        } else {
            lastDayTv.setTextColor(0xff5b5b5b);
            lastDayView.setVisibility(View.INVISIBLE);
            lastThreeDayTv.setTextColor(0xff1f89d4);
            lastThreeDayView.setVisibility(View.VISIBLE);
        }
        setCurPosition(position);
        vp.setCurrentItem(position);
    }

    @OnClick({R.id.last_day_rl, R.id.last_three_day_rl, R.id.back_llayout, R.id.search_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.last_day_rl:
                changeState(LAST_DAY);
                break;
            case R.id.last_three_day_rl:
                changeState(LAST_THREE_DAY);
                break;
            case R.id.search_ll:
                DriverCarSchedulingSearchActivity.startActivity(this, REQUEST_CODE_SEARCH);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SEARCH) {
            beginDate = data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
            endDate = data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);
        }
    }

    @OnClick(R.id.right_ll)
    public void onClick() {}
}
