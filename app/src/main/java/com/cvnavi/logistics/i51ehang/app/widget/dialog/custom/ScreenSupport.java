package com.cvnavi.logistics.i51ehang.app.widget.dialog.custom;


import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取当前屏幕的宽高
 */
public class ScreenSupport {
    public static int SCREEN_WIDTH = 0;// 当前屏幕的宽度
    public static int SCREEN_HEIGHT = 0;// 当前屏幕的高度
    public static float SCREEN_DESTITY = 0.0f;// 当前屏幕的密度

    public static void displayMetrics(Activity activity) {
        if (activity == null) {
            return;
        }
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

        ScreenSupport.SCREEN_WIDTH = dm.widthPixels;
        ScreenSupport.SCREEN_HEIGHT = dm.heightPixels;
        ScreenSupport.SCREEN_DESTITY = dm.density;
        dm = null;
    }


    //设置全屏
    public static void setFullScreen(Activity activity, boolean isFullScreen) {
        if (isFullScreen) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }


}
