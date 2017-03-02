package com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:05
*描述：员工首页设置菜单的适配器
************************************************************************************/

public class MyViewPagerFragmentAdapter extends PagerAdapter implements
		ViewPager.OnPageChangeListener {
	private int currentPageIndex = 0; // 当前page索引（切换之前）
	private OnExtraPageChangeListener listener = null; // ViewPager切换页面时的额外功能添加接口
	private List<Fragment> fragments; // 每个Fragment对应一个Page
	private FragmentManager fragmentManager;
	private ViewPager viewPager; // viewPager对象

	public MyViewPagerFragmentAdapter(FragmentManager fragmentManager,ViewPager viewPager, List<Fragment> fragments) {
		this.fragments = fragments;
		this.fragmentManager = fragmentManager;
		this.viewPager = viewPager;
		this.viewPager.setAdapter(this);
		this.viewPager.setOnPageChangeListener(this);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return view == o;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// container.removeView(fragments.get(position).getView()); //
		// 移出viewpager两边之外的page布局
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment fragment = fragments.get(position);
		if (!fragment.isAdded()) { // 如果fragment还没有added
			FragmentTransaction ft = fragmentManager.beginTransaction();
			ft.add(fragment, fragment.getClass().getSimpleName());
			// ft.commit();//警告:
			// 你只能在activity保存它的状态(当用户离开activity)之前使用commit()提交事务.如果你试图在那个点之后提交,
			// 会抛出一个异常.这是因为如果activity需要被恢复, 提交之后的状态可能会丢失.对于你觉得可以丢失提交的状况, 使用
			// commitAllowingStateLoss().
			ft.commitAllowingStateLoss();
			/**
			 * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
			 * 会在进程的主线程中，用异步的方式来执行。 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
			 * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
			 */
			fragmentManager.executePendingTransactions();
		}

		if (fragment.getView().getParent() == null) {
			container.addView(fragment.getView()); // 为viewpager增加布局
		}

		return fragment.getView();
	}

	/**
	 * 当前page索引（切换之前）
	 * 
	 * @return
	 */
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public OnExtraPageChangeListener getOnExtraPageChangeListener() {
		return listener;
	}

	@Override
	public void onPageScrolled(int i, float v, int i2) {
		if (null != listener) { // 如果设置了额外功能接口
			listener.onExtraPageScrolled(i, v, i2);
		}
	}

	@Override
	public void onPageSelected(int i) {
		fragments.get(currentPageIndex).onPause(); // 调用切换前Fargment的onPause()
		// fragments.get(currentPageIndex).onStop(); // 调用切换前Fargment的onStop()
		if (fragments.get(i).isAdded()) {
			// fragments.get(i).onStart(); // 调用切换后Fargment的onStart()
			fragments.get(i).onResume(); // 调用切换后Fargment的onResume()
		}
		currentPageIndex = i;

		if (null != listener) { // 如果设置了额外功能接口
			listener.onExtraPageSelected(i);
		}
	}

	@Override
	public void onPageScrollStateChanged(int i) {
		if (null != listener) { // 如果设置了额外功能接口
			listener.onExtraPageScrollStateChanged(i);
		}
	}

	/**
	 * page切换额外功能接口
	 */
	public interface OnExtraPageChangeListener {
		public void onExtraPageScrolled(int pos, float v, int i2);
		public void onExtraPageSelected(int pos);
		public void onExtraPageScrollStateChanged(int pos);
	}

	/**
	 * 设置页面切换额外功能监听器
	 */
	public void setOnExtraPageChangeListener(OnExtraPageChangeListener listener) {
		this.listener = listener;
	}
}
