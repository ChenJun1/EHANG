package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.unfinlshedorder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.DriverMainViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.mBadgeView;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

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
 * Depict: 货主 未完成货单 页面
 */

public class UnfinishedOrdersActivity extends BaseActivity {
    private static final int LAST_DAY = 0;//ViewPage 位置
    private static final int LAST_THREE_DAYS = 1;//ViewPage 位置
    private static final int LAST_MONTHS = 2;//ViewPage 位置
    private static final String MBADGEVIEW = "MBADGEVIEW";

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.last_day_rl)
    RelativeLayout lastDayRl;
    @BindView(R.id.last_three_days_rl)
    RelativeLayout lastThreeDaysRl;
    @BindView(R.id.last_month_rl)
    RelativeLayout lastMonthRl;
    @BindView(R.id.id_tab_line_iv)
    View idTabLineIv;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.no_start_car_text)
    TextView noStartCarText;
    @BindView(R.id.in_transit_text)
    TextView inTransitText;
    @BindView(R.id.delivery_text)
    TextView deliveryText;
    @BindView(R.id.no_start_car_line)
    View noStartCarLine;
    @BindView(R.id.in_transit_line)
    View inTransitLine;
    @BindView(R.id.delivery_line)
    View deliveryLine;
    @BindView(R.id.no_tstart_number)
    TextView mNoTstartNumber;
    @BindView(R.id.transport_number)
    TextView mTransportNumber;
    @BindView(R.id.delivery_number)
    TextView mDeliveryNumber;

    private DriverMainViewPagerAdapter adapter;
    private ArrayList<Fragment> list;

    private int currentIndex;
    private int screenWidth;

    public static void start(Context context, mBadgeView mBadgeView) {
        Intent starter = new Intent(context, UnfinishedOrdersActivity.class);
        starter.putExtra(MBADGEVIEW, mBadgeView);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfinished_order);
        ButterKnife.bind(this);
        init();
        initTabLineWidth();
    }

    private void init() {
        titltTv.setText("未完成货单");
        mBadgeView badge = getIntent().getParcelableExtra(MBADGEVIEW);
        if (badge != null) {
            setBadgeView(badge);
        }
        if (list == null) {
            list = new ArrayList<>();
        }

        noStartCarText.setTextColor(0xff1f89d4);
        mNoTstartNumber.setTextColor(0xff1f89d4);
        inTransitText.setTextColor(0xff5b5b5b);
        deliveryText.setTextColor(0xff5b5b5b);

        list.add(NoStartCarFragment.getInstance());
        list.add(InTransitFragment.getInstance());
        list.add(DeliveryFragment.getInstance());
        if (adapter == null) {
            adapter = new DriverMainViewPagerAdapter(getSupportFragmentManager(), list);
        }

        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(2);


        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float offset, int positionOffsetPixels) {

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv.getLayoutParams();

                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
                 * 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1; 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 3) + currentIndex * (screenWidth / 3));
                }
                idTabLineIv.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                changeState(position);
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        vp.setCurrentItem(0);
    }

    private void setBadgeView(mBadgeView badView) {
        if (!TextUtils.isEmpty(badView.NotStartNumber))
          mNoTstartNumber.setText(badView.NotStartNumber);

        if (!TextUtils.isEmpty(badView.TransportNumber))
          mTransportNumber.setText(badView.TransportNumber);

        if (!TextUtils.isEmpty(badView.DeliveryNumber))
          mDeliveryNumber.setText(badView.DeliveryNumber);


    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) idTabLineIv.getLayoutParams();
        lp.width = screenWidth / 3;
        idTabLineIv.setLayoutParams(lp);
    }

    private void changeState(int position) {
        if (position == LAST_DAY) {

            mNoTstartNumber.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            mTransportNumber.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            mDeliveryNumber.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));

            noStartCarText.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            inTransitText.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            deliveryText.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            noStartCarLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_218ad5));
            inTransitLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_F5F4F7));
            deliveryLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_F5F4F7));


        } else if (position == LAST_THREE_DAYS) {

            mNoTstartNumber.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            mTransportNumber.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            mDeliveryNumber.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));

            noStartCarText.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            inTransitText.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            deliveryText.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));

            noStartCarLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_F5F4F7));
            inTransitLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_218ad5));
            deliveryLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_F5F4F7));

        } else if (position == LAST_MONTHS) {

            mNoTstartNumber.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            mTransportNumber.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            mDeliveryNumber.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));

            noStartCarText.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            inTransitText.setTextColor(Utils.getResourcesColor(R.color.color_5B5B5B));
            deliveryText.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));

            noStartCarLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_F5F4F7));
            inTransitLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_F5F4F7));
            deliveryLine.setBackgroundColor(Utils.getResourcesColor(R.color.color_218ad5));

        }
        vp.setCurrentItem(position);
    }

    @OnClick({R.id.last_day_rl, R.id.last_three_days_rl, R.id.last_month_rl, R.id.back_llayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.last_day_rl:
                vp.setCurrentItem(LAST_DAY, false);

                break;
            case R.id.last_three_days_rl:
                vp.setCurrentItem(LAST_THREE_DAYS, false);
                break;
            case R.id.last_month_rl:
                vp.setCurrentItem(LAST_MONTHS, false);
                break;
            case R.id.back_llayout:
                finish();
                break;
        }
    }
}
