//package com.cvnavi.logistics.i51ehang.app.ui;
//
//import android.app.Activity;
//import android.content.Context;
//
//import java.util.Stack;
//
///**
// *
// * 版权所有 上海势航
// *
// * @author chenJun and johnnyYuan
// * @description
// * @date 2016-5-17 下午1:19:57
// * @version 1.0.0
// * @email yuanlunjie@163.com
// */
//final public class ActivityStackManager {
//	private static Stack<Activity> activityStack;
//	private static final ActivityStackManager instance = new ActivityStackManager();
//
//	private ActivityStackManager() {
//		activityStack = new Stack<Activity>();
//	}
//
//	public static synchronized ActivityStackManager getInstance() {
//		return instance;
//	}
//
//	/**
//	 * 获取当前Activity栈中元素个数
//	 */
//	public int getCount() {
//		return activityStack.size();
//	}
//
//	/**
//	 * 添加Activity到栈
//	 */
//	public void addActivity(Activity activity) {
//		activityStack.add(activity);
//	}
//
//	/**
//	 * 获取当前Activity（栈顶Activity）
//	 */
//	public Activity topActivity() {
//		if (activityStack == null) {
//			throw new NullPointerException("Activity stack is Null,your Activity must extend YeeActivity");
//		}
//		if (activityStack.isEmpty()) {
//			return null;
//		}
//		Activity activity = activityStack.lastElement();
//		return (Activity) activity;
//	}
//
//	/**
//	 * 获取当前Activity（栈顶Activity） 没有找到则返回null
//	 */
//	public Activity findActivity(Class<?> cls) {
//		Activity activity = null;
//		for (int i = 0; i < activityStack.size(); i++) {
//			if (activityStack.get(i).getClass().equals(cls)) {
//				activity = activityStack.get(i);
//				break;
//			}
//		}
//		return (Activity) activity;
//	}
//
//	/**
//	 * 结束当前Activity（栈顶Activity）
//	 */
//	public void finishActivity() {
//		Activity activity = activityStack.lastElement();
//		finishActivity((Activity) activity);
//	}
//
//	/**
//	 * 结束指定的Activity(重载)
//	 */
//	public void finishActivity(Activity activity) {
//		if (activity != null) {
//			activity.finish();
//			activity = null;
//		}
//	}
//
//	/**
//	 * 结束指定的Activity(重载)
//	 */
//	public void finishActivity(Class<?> cls) {
//		for (int i = activityStack.size() - 1; i >= 0; i--) {
//			if (activityStack.get(i).getClass().equals(cls)) {
//				finishActivity((Activity) activityStack.get(i));
//				activityStack.remove(i);
//			}
//		}
//	}
//
//	/**
//	 * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
//	 *
//	 * @param cls
//	 */
//	public void finishOthersActivity(Class<?> cls) {
//		for (int i = activityStack.size() - 1; i >= 0; i--) {
//			if (!(activityStack.get(i).getClass().equals(cls))) {
//				finishActivity((Activity) activityStack.get(i));
//				activityStack.remove(i);
//			}
//		}
//	}
//
//	/**
//	 * 结束所有Activity
//	 */
//	public void finishAllActivity() {
//		for (int i = activityStack.size() - 1; i >= 0; i--) {
//			if (null != activityStack.get(i)) {
//				((Activity) activityStack.get(i)).finish();
//			}
//		}
//		activityStack.clear();
//	}
//
//	/**
//	 * 应用程序退出
//	 *
//	 */
//	public void AppExit(Context context) {
//		try {
//			finishAllActivity();
//			Runtime.getRuntime().exit(0);
//		} catch (Exception e) {
//			Runtime.getRuntime().exit(-1);
//		}
//	}
//}
