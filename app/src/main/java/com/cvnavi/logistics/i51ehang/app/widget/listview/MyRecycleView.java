package com.cvnavi.logistics.i51ehang.app.widget.listview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/************************************************************************************
 * 唯一标识：
 * 创建人：  george
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/6
 * 版本：
 * 描述：
 * <p>
 * =====================================================================
 * 修改标记
 * 修改时间：
 * 修改人：
 * 电子邮箱：
 * 手机号：
 * 版本：
 * 描述：
 ************************************************************************************/

public class MyRecycleView extends RecyclerView {
    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        if(e.getAction() == MotionEvent.ACTION_SCROLL){
            return true;
        }
        return super.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_SCROLL){
            return true;
        }
        return super.onInterceptTouchEvent(e);
    }
}
