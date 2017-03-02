package com.cvnavi.logistics.i51ehang.app.widget.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cvnavi.logistics.i51ehang.app.R;


/**
 * @类说明:loading图旋转的布局
 */
public class LoadingRotateLinearLayout extends LinearLayout {
    private Context mContext;

    public LoadingRotateLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        addAnimationImageView();
    }

    public LoadingRotateLinearLayout(Context context) {
        super(context);
        mContext = context;
        addAnimationImageView();
    }

    public void addAnimationImageView() {
        Animation rotateAni = AnimationUtils.loadAnimation(mContext,
                R.anim.loading_rotate);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAni.setInterpolator(lin);

        ImageView image = new ImageView(mContext);
        image.setImageResource(R.drawable.loading_image);
        image.clearAnimation();
        image.startAnimation(rotateAni);
        this.removeAllViews();
        this.addView(image);
    }
}
