package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods;

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
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.adapter.DrTakeGoodsViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.fragment.DrAlreadTakeGoodsFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.fragment.DrStayTakeGoodsFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.transformer.CubeOutTransformer;
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
 * Depict: 我的提货
 */
public class DrMyTakeGoods extends BaseActivity {

    private final int REQUESTCODE=10011; //请求码
    private static final int OFF_THE_STOCKS = 0; //0未提货
    public static final int TOTAL = 1;//1已提货
    private static final String PAGE="PAGE";

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

    private String beginTime="";
    private String endTime="";
    private int requestTime=-7;//请求的时间天数
    private int vl_page = 0;//页面位置

    private DrTakeGoodsViewPagerAdapter pagerAdapter = null;
    private ArrayList<Fragment> fgList = null;


    public static void start(Context context) {
        Intent starter = new Intent(context, DrMyTakeGoods.class);
        context.startActivity(starter);
    }

    public static void start(Context context,int  page) {
        Intent starter = new Intent(context, DrMyTakeGoods.class);
        starter.putExtra(PAGE,page);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_take_goods_activity);
        ButterKnife.bind(this);
        mTitltTv.setText("提货");
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
        fgList.add(DrStayTakeGoodsFragment.getInstance());
        fgList.add(DrAlreadTakeGoodsFragment.getInstance());
        if (pagerAdapter == null) {
            pagerAdapter = new DrTakeGoodsViewPagerAdapter(getSupportFragmentManager(), fgList);
        }
        mVp.setAdapter(pagerAdapter);
        //设置viewpage翻页动画
        try {
            mVp.setPageTransformer(true, CubeOutTransformer.class.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
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

        } else if (position == TOTAL) {
            mOffTheStocksTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            mOffTheStocksView.setVisibility(View.INVISIBLE);

            mTotalTv.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            mTotalView.setVisibility(View.VISIBLE);

        }
        mVp.setCurrentItem(position);
    }

    @OnClick(value = {R.id.back_llayout, R.id.off_the_stocks_rl, R.id.total_rl, R.id.search_ll})
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
            case R.id.search_ll:
                DriverDataChooseActivity.startActivity(this,REQUESTCODE,beginTime,endTime);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case REQUESTCODE:
                    beginTime=data.getStringExtra(DriverCarSchedulingSearchActivity.BEGIN_DATA);
                    endTime=data.getStringExtra(DriverCarSchedulingSearchActivity.END_DATA);
                    break;
            }
        }
    }

    public String getBeginTime(){
        if(TextUtils.isEmpty(beginTime)){
            return beginTime=DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(requestTime), DateUtil
                    .FORMAT_YMD, DateUtil.FORMAT_YMD);
        }else{
            return beginTime;
        }

    }
    public String getEndTime(){
        if(TextUtils.isEmpty(endTime)){
            return endTime=DateUtil.strOldFormat2NewFormat(DateUtil.getLastNDays(0), DateUtil.FORMAT_YMD,
                    DateUtil.FORMAT_YMD);
        }else{
            return endTime;
        }

    }
}
