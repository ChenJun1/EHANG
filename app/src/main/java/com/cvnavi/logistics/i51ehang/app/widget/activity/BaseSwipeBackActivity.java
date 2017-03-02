package com.cvnavi.logistics.i51ehang.app.widget.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.widget.layout.SwipeBackLayout;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/30.
 * 带侧滑功能的activity
 */
public class BaseSwipeBackActivity extends BaseActivity implements SwipeBackLayout.ActivityFinishStateListener {
    public SwipeBackLayout swipeBackLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().getDecorView().setBackgroundDrawable(null);
        swipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.base, null);
        swipeBackLayout.attachToActivity(this);
        swipeBackLayout.setActivityFinishListener(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // overridePendingTransition(R.anim.base_slide_right_in,
        // R.anim.base_slide_remain);
    }

    public void setSwipeBackEnable(boolean enable) {
        swipeBackLayout.setEnableGesture(enable);
    }

    @Override
    public boolean isCanFinishActivity() {
        // TODO Auto-generated method stub
        return true;
    }
}
