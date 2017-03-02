package com.cvnavi.logistics.i51ehang.app.activity.guid;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.vpannimation.CubeOutTransformer;
import com.cvnavi.logistics.i51ehang.app.activity.login.LoginActivity;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ScreenSupport;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * 第一次安装的引导页
 * 每次引导页
 *
 */
public class GuidViewPagerActivity extends BaseActivity implements OnClickListener, OnPageChangeListener {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.button)
    Button mButton;

    private GuidViewPagerAdapter mGuidViewPagerAdapter;
    private List<View> mViews;

    /**
     * 底部小点图片
     */
    private ImageView[] mImageViews;
    /**
     * 记录当前选中位置
     **/
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        ButterKnife.bind(this);

        ScreenSupport.setFullScreen(this, true);
        initview();
    }

    private void initview() {
        mViews = new ArrayList<View>();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //初始化引导图片列表
        for (int i = 0; i < Constants.GuidResImgs.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(Constants.GuidResImgs[i]);
            mViews.add(imageView);
        }

        //初始化adapter
        mGuidViewPagerAdapter = new GuidViewPagerAdapter(mViews);
        mViewPager.setAdapter(mGuidViewPagerAdapter);
        //绑定回调
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setPageTransformer(false,new CubeOutTransformer());

        //初始化底部小圆点
        initDotImgs();

        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(GuidViewPagerActivity.this, LoginActivity.class);
                GuidViewPagerActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 初始化底部小圆点
     */
    private void initDotImgs() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
        mImageViews = new ImageView[Constants.GuidResImgs.length];

        //循环取得小圆点图片
        for (int i = 0; i < Constants.GuidResImgs.length; i++) {
            mImageViews[i] = (ImageView) linearLayout.getChildAt(i);
            mImageViews[i].setEnabled(true);//都设为灰色
            mImageViews[i].setOnClickListener(this);
            mImageViews[i].setTag(i);//设置位置tag,方便取出与当前位置对应
        }
        currentIndex = 0;
        mImageViews[currentIndex].setEnabled(false);//设置为白色,即选中状态
    }

    /**
     * 设置当前的引导页
     *
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= Constants.GuidResImgs.length) {
            return;
        }
        mViewPager.setCurrentItem(position);
    }

    /**
     * 设置当前引导小点的位置
     *
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position >= Constants.GuidResImgs.length || currentIndex == position) {
            return;
        }
        mImageViews[position].setEnabled(false);
        mImageViews[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    //当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        setCurDot(arg0);
        if (arg0 == mImageViews.length - 1) {//根据引导页的数量-1对比
            mButton.setVisibility(View.VISIBLE);
        } else {
            mButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }
}
