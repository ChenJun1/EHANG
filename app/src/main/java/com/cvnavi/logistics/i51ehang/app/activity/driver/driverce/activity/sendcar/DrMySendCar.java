package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar;

import android.content.Context;
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
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.fragment.DrCompleteFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.fragment.DrNoStartFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.fragment.DrRunTimeFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.transformer.CubeOutTransformer;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskMainViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.utils.DriverDataChooseActivity;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
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
 * Depict: 我的发车 页面
 */

public class DrMySendCar extends BaseActivity {

    private final int REQUESTCODE = 10011;//请求码
    private static final int OFF_THE_STOCKS = 0;
    private static final int TOTAL = 1;
    public static final int WAN = 2;

    private static final String PAGE="PAGE";//确定进入viewPage的位置

    @BindView(R.id.back_llayout)
    LinearLayout mBackLlayout;
    @BindView(R.id.titlt_tv)
    TextView mTitltTv;
    @BindView(R.id.add_ll)
    LinearLayout mAddLl;
    @BindView(R.id.search_iv)
    ImageView mSearchIv;
    @BindView(R.id.search_ll)
    LinearLayout mSearchLl;
    @BindView(R.id.right_ll)
    LinearLayout mRightLl;
    @BindView(R.id.right_tv)
    TextView mRightTv;
    @BindView(R.id.content_ll)
    LinearLayout mContentLl;
    @BindView(R.id.check_tv)
    TextView mCheckTv;
    @BindView(R.id.add)
    LinearLayout mAdd;
    @BindView(R.id.custom_ll)
    LinearLayout mCustomLl;
    @BindView(R.id.off_the_stocks_tv)
    TextView mOffTheStocksTv;
    @BindView(R.id.off_the_stocks_view)
    View mOffTheStocksView;
    @BindView(R.id.off_the_stocks_rl)
    RelativeLayout mOffTheStocksRl;
    @BindView(R.id.total_tv)
    TextView mTotalTv;
    @BindView(R.id.total_view)
    View mTotalView;
    @BindView(R.id.total_rl)
    RelativeLayout mTotalRl;
    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.wan_tv)
    TextView mWanTv;
    @BindView(R.id.wan_view)
    View mWanView;
    @BindView(R.id.wan_rl)
    RelativeLayout mWanRl;

    private String beginTime = "";
    private String endTime = "";

    private int requestTime=-7;//请求的时间天数
    private int vl_page = 1;//页面位置

    private MyTaskMainViewPagerAdapter pagerAdapter = null;
    private ArrayList<Fragment> fgList = null;

    public static void start(Context context) {
        Intent starter = new Intent(context, DrMySendCar.class);
        context.startActivity(starter);
    }

    /**
     *
     * @param context
     * @param page 定位到的页面数 0,1,2
     *
     */
    public static void start(Context context,int  page) {
        Intent starter = new Intent(context, DrMySendCar.class);
        starter.putExtra(PAGE,page);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_send_car_activity);
        ButterKnife.bind(this);
        mTitltTv.setText("我的发车");
        init();

    }

    private void init() {
        int page;//计算出请求开始日期
        if((page=getIntent().getIntExtra(PAGE,-1))!=-1){
            String day= DateUtil.getCurDateStr(DateUtil.FORMAT_D);
            requestTime=Integer.valueOf(day);
            requestTime=requestTime-requestTime*2+1;
            vl_page=page;
        }

        mRightLl.setVisibility(View.VISIBLE);
        mAddLl.setVisibility(View.GONE);
        if (fgList == null) {
            fgList = new ArrayList<>();
        }
        fgList.add(DrNoStartFragment.getInstance());
        fgList.add(DrRunTimeFragment.getInstance());
        fgList.add(DrCompleteFragment.getInstance());
        if (pagerAdapter == null) {
            pagerAdapter = new MyTaskMainViewPagerAdapter(getSupportFragmentManager(), fgList);
        }
        try {
            mVp.setPageTransformer(true, CubeOutTransformer.class.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mVp.setAdapter(pagerAdapter);
        mVp.setOffscreenPageLimit(2);
        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        mVp.setCurrentItem(vl_page);
    }

    private void changeState(int position) {
        if (position == OFF_THE_STOCKS) {
            mOffTheStocksTv.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            mOffTheStocksView.setVisibility(View.VISIBLE);

            mTotalTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            mTotalView.setVisibility(View.INVISIBLE);
            mWanTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            mWanView.setVisibility(View.INVISIBLE);

        } else if (position == TOTAL) {
            mOffTheStocksTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            mOffTheStocksView.setVisibility(View.INVISIBLE);
            mWanTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            mWanView.setVisibility(View.INVISIBLE);

            mTotalTv.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            mTotalView.setVisibility(View.VISIBLE);

        }else if(position==WAN){
            mOffTheStocksTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            mOffTheStocksView.setVisibility(View.INVISIBLE);
            mTotalTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            mTotalView.setVisibility(View.INVISIBLE);

            mWanTv.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            mWanView.setVisibility(View.VISIBLE);
        }
        mVp.setCurrentItem(position);
    }

    @OnClick(value = {R.id.back_llayout, R.id.off_the_stocks_rl, R.id.total_rl, R.id.search_ll,R.id.wan_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.off_the_stocks_rl:
                changeState(0);
                break;
            case R.id.total_rl:
                changeState(1);
                break;
            case R.id.wan_rl:
                changeState(2);
                break;
            case R.id.search_ll:
                DriverDataChooseActivity.startActivity(this,REQUESTCODE,beginTime,endTime);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE:
                    beginTime = data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
                    endTime = data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);
                    break;
            }
        }
    }

    public String getBeginTime() {
        if (TextUtils.isEmpty(beginTime)) {
            return beginTime=DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(requestTime), DateUtil
                    .FORMAT_YMD, DateUtil.FORMAT_YMD);
        } else {
            return beginTime;
        }

    }

    public String getEndTime() {
        if (TextUtils.isEmpty(endTime)) {
            return endTime=DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(0), DateUtil.FORMAT_YMD,
                    DateUtil.FORMAT_YMD);
        } else {
            return endTime;
        }
    }
}
