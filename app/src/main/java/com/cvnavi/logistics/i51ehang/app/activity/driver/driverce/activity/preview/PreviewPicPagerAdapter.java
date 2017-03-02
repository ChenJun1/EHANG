package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.preview;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 查看照片
 */
public class PreviewPicPagerAdapter extends PagerAdapter {

    private List<View> views;

    public PreviewPicPagerAdapter(List<View> views) {
        this.views = views;
    }

    /**
     * 移除position的位置的界面时调用
     */
    @Override
    public void destroyItem(View view, int position, Object object) {
        ((ViewPager) view).removeView(views.get(position));
    }

    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    // 初始化position位置的界面
    @Override
    public Object instantiateItem(View view, int position) {

        ((ViewPager) view).addView(views.get(position), 0);
        return views.get(position);

    }

    //判断是否由对象生成界面
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

}
