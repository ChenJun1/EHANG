package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask;

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
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter.MyTaskMainViewPagerAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.fragment.MyTaskOffTheStocksFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.fragment.MyTaskTotalFragment;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverCarSchedulingSearchActivity;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskActivity extends BaseActivity {

    private static final int OFF_THE_STOCKS = 0;
    private static final int TOTAL = 1;

    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.off_the_stocks_tv)
    TextView offTheStocksTv;
    @BindView(R.id.off_the_stocks_view)
    View offTheStocksView;
    @BindView(R.id.off_the_stocks_rl)
    RelativeLayout offTheStocksRl;
    @BindView(R.id.total_tv)
    TextView totalTv;
    @BindView(R.id.total_view)
    View totalView;
    @BindView(R.id.total_rl)
    RelativeLayout totalRl;
    @BindView(R.id.vp)
    ViewPager vp;

    private MyTaskMainViewPagerAdapter pagerAdapter = null;
    private ArrayList<Fragment> fgList = null;

    private Boolean isPage=false;//页面位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        ButterKnife.bind(this);
        titltTv.setText(Utils.getResourcesString(R.string.my_task));
        init();

    }

    private void init() {
        rightLl.setVisibility(View.VISIBLE);
        addLl.setVisibility(View.GONE);
        if (fgList == null) {
            fgList = new ArrayList<>();
        }
        fgList.add(MyTaskOffTheStocksFragment.getInstance());
        fgList.add(MyTaskTotalFragment.getInstance());
        if (pagerAdapter == null) {
            pagerAdapter = new MyTaskMainViewPagerAdapter(getSupportFragmentManager(), fgList);
        }
        vp.setAdapter(pagerAdapter);

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
        vp.setCurrentItem(0);
    }

    private void changeState(int position) {
        if (position == OFF_THE_STOCKS) {
            isPage=false;
            offTheStocksTv.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            offTheStocksView.setVisibility(View.VISIBLE);
            totalTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            totalView.setVisibility(View.INVISIBLE);

        } else if (position == TOTAL) {
            isPage=true;
            offTheStocksTv.setTextColor(Utils.getResourcesColor(R.color.color_232323));
            offTheStocksView.setVisibility(View.INVISIBLE);

            totalTv.setTextColor(Utils.getResourcesColor(R.color.color_218ad5));
            totalView.setVisibility(View.VISIBLE);

        }
        vp.setCurrentItem(position);
    }

    @OnClick(value = {R.id.back_llayout, R.id.off_the_stocks_rl, R.id.total_rl,R.id.search_ll})
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
                Intent intent=new Intent(this, DriverCarSchedulingSearchActivity.class);
                if(isPage) {
                    intent.putExtra(Constants.MyTASK_CHOICE_TIME,TOTAL );
                }else{
                    intent.putExtra(Constants.MyTASK_CHOICE_TIME,OFF_THE_STOCKS );
                }
                startActivity(intent);
                break;
        }
    }
}
