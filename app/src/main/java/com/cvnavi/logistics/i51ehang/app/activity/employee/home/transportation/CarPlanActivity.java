package com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.DriverMainViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverAddCarSchedulingActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.popupwindow.MyPopWindow;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:06
*描述： 发车计划界面
************************************************************************************/


public class CarPlanActivity extends BaseActivity {

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
    @BindView(R.id.last_day_tv)
    TextView lastDayTv;
    @BindView(R.id.last_day_view)
    View lastDayView;
    @BindView(R.id.last_day_rl)
    RelativeLayout lastDayRl;
    @BindView(R.id.todaytv)
    TextView todaytv;
    @BindView(R.id.today_view)
    View todayView;
    @BindView(R.id.today_rl)
    RelativeLayout todayRl;
    @BindView(R.id.tomo_day_tv)
    TextView tomoDayTv;
    @BindView(R.id.tomorrow_view)
    View tomorrowView;
    @BindView(R.id.tomorrow_rl)
    RelativeLayout tomorrowRl;
    @BindView(R.id.all_tv)
    TextView allTv;
    @BindView(R.id.all_view)
    View allView;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;
    @BindView(R.id.vp)
    ViewPager vp;

    private static final int LAST_DAY = 0;//待发车
    private static final int TO_DAY = 1;//已发车
    private static final int TOMORROW = 2;//已完成
    private static final int ALL = 3;//全部
    public static final int REQUEST_CODE_SEARCH = 0x12;
    private DriverMainViewPagerAdapter adapter;
    private ArrayList<Fragment> list = new ArrayList<Fragment>();

    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, CarPlanActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver_transportation);
        ButterKnife.bind(this);
        init();
    }


    private void init() {
        if (list == null) {
            list = new ArrayList<Fragment>();
        }

        list.add(ToStartPlanFragment.getInstance());
        list.add(HasStartedPlanFragment.getInstance());
        list.add(CompletedPlanFragment.getInstance());
        list.add(AllPlanFragment.getInstance());
        if (adapter == null) {
            adapter = new DriverMainViewPagerAdapter(getSupportFragmentManager(), list);
        }

        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(4);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //默认是待发车
        vp.setCurrentItem(0);
        titltTv.setText(Utils.getResourcesString(R.string.scheduling_list));
        rightLl.setVisibility(View.VISIBLE);
        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_ADD_PLAN + "")) {
            addLl.setVisibility(View.VISIBLE);
        } else {
            addLl.setVisibility(View.INVISIBLE);
        }
        searchLl.setVisibility(View.VISIBLE);
    }

    private void changeState(int position) {
        if (position == LAST_DAY) {
            lastDayTv.setTextColor(0xff1f89d4);
            lastDayView.setVisibility(View.VISIBLE);
            todaytv.setTextColor(0xff5b5b5b);
            todayView.setVisibility(View.INVISIBLE);
            tomoDayTv.setTextColor(0xff5b5b5b);
            tomorrowView.setVisibility(View.INVISIBLE);
            allTv.setTextColor(0xff5b5b5b);
            allView.setVisibility(View.INVISIBLE);
        } else if (position == TO_DAY) {
            lastDayTv.setTextColor(0xff5b5b5b);
            lastDayView.setVisibility(View.INVISIBLE);
            todaytv.setTextColor(0xff1f89d4);
            todayView.setVisibility(View.VISIBLE);
            tomoDayTv.setTextColor(0xff5b5b5b);
            tomorrowView.setVisibility(View.INVISIBLE);
            allTv.setTextColor(0xff5b5b5b);
            allView.setVisibility(View.INVISIBLE);
        } else if (position == TOMORROW) {
            lastDayTv.setTextColor(0xff5b5b5b);
            lastDayView.setVisibility(View.INVISIBLE);
            todaytv.setTextColor(0xff5b5b5b);
            todayView.setVisibility(View.INVISIBLE);
            tomoDayTv.setTextColor(0xff1f89d4);
            tomorrowView.setVisibility(View.VISIBLE);
            allTv.setTextColor(0xff5b5b5b);
            allView.setVisibility(View.INVISIBLE);
        } else {
            lastDayTv.setTextColor(0xff5b5b5b);
            lastDayView.setVisibility(View.INVISIBLE);
            todaytv.setTextColor(0xff5b5b5b);
            todayView.setVisibility(View.INVISIBLE);
            tomoDayTv.setTextColor(0xff5b5b5b);
            tomorrowView.setVisibility(View.INVISIBLE);
            allTv.setTextColor(0xff1f89d4);
            allView.setVisibility(View.VISIBLE);
        }
        vp.setCurrentItem(position);
    }


    @OnClick({R.id.last_day_rl, R.id.today_rl, R.id.tomorrow_rl, R.id.add_ll, R.id.search_ll, R.id.right_ll, R.id.all_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.last_day_rl:
                changeState(LAST_DAY);
                break;
            case R.id.today_rl:
                changeState(TO_DAY);
                break;
            case R.id.tomorrow_rl:
                changeState(TOMORROW);
                break;
            case R.id.all_rl:
                changeState(ALL);
                break;
            case R.id.add_ll:
                if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_ADD_PLAN + "")) {
                    //检查是否有该权限，跳转到添加发车计划
                    DriverAddCarSchedulingActivity.startActivity(CarPlanActivity.this, null, null, 0x12, DriverAddCarSchedulingActivity.INTENT_DATA_ADD_FROM_PLAN);
                } else {
                    //没有弹出提示
                    DialogUtils.showWarningToast("您没有该权限！");
                }
                break;
            case R.id.search_ll:
                MyPopWindow popWindow = new MyPopWindow(CarPlanActivity.this, new ArrayList<String>() {{
                    add("昨日计划");
                    add("今日计划");
                    add("明日计划");
                    add("自定义");
                }});

                popWindow.showLocation(R.id.search_ll);
                popWindow.setOnItemClickListener(new MyPopWindow.OnItemClickListener() {
                    @Override
                    public void onClick(int pos) {
                        switch (pos) {
                            case 0:
                                EventBus.getDefault().post(new TimeEvent(DateUtil.getLastNDays(-1), DateUtil.getLastNDays(-1)));
                                break;
                            case 1:
                                EventBus.getDefault().post(new TimeEvent(DateUtil.getLastNDays(0), DateUtil.getLastNDays(0)));
                                break;
                            case 2:
                                EventBus.getDefault().post(new TimeEvent(DateUtil.getLastNDays(1), DateUtil.getLastNDays(1)));
                                break;
                            case 3:
                                DriverCarSchedulingSearchActivity.startActivity(CarPlanActivity.this, REQUEST_CODE_SEARCH);
                                break;
                        }
                    }
                });
                break;
            case R.id.right_ll:
                break;
        }
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SEARCH) {
            String beginDate = data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
            String endDate = data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);

            if (!TextUtils.isEmpty(beginDate) && !TextUtils.isEmpty(endDate)) {
                EventBus.getDefault().post(new TimeEvent(beginDate, endDate));
            }
        }
    }


}
