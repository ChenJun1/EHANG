package com.cvnavi.logistics.i51ehang.app.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by george on 2016/10/14.
 * 属性动画的一些集合
 */

public class ObjectAnimationUtils {


    /**
     * 缩放x与y
     */
    public static void showScaleXScalexY(Object object) {
        AnimatorSet aSet = new AnimatorSet();
        aSet.playTogether(ObjectAnimator.ofFloat(object, "scaleX", 1.0f, 0.5f, 1.f)
                        .setDuration(800),
                ObjectAnimator.ofFloat(object, "scaleY", 1, 0.5f, 1.0f)
                        .setDuration(800));
        aSet.start();
    }

    /**
     * 缩放x与y
     */
    public static void rotationX(Object object) {
        AnimatorSet aSet = new AnimatorSet();
        aSet.playTogether(ObjectAnimator.ofFloat(object, "rotationX", 90, 0)
                .setDuration(2000));
        aSet.start();
    }


    /**
     * 上移动
     */

    public static void upToPos(View object) {
        AnimatorSet aSet = new AnimatorSet();
        aSet.playTogether(
                ObjectAnimator.ofFloat(object, "translationY", 0, 300)
                        .setDuration(800));
        aSet.start();
    }


    /**
     * 上移动
     */

    public static void Scle(View object) {
        AnimatorSet aSet = new AnimatorSet();
        aSet.playTogether(
                ObjectAnimator.ofFloat(object, "scaleY", 1, 0.5f)
                        .setDuration(800));
        aSet.start();
    }

    public static void ScleDown(View object) {
        AnimatorSet aSet = new AnimatorSet();
        aSet.playTogether(
                ObjectAnimator.ofFloat(object, "scaleY", 0.5f, 1f)
                        .setDuration(500));
        aSet.start();
    }


    private final static int ANIMATION_TYPE_FADE_INOUT = 1;// 透明度渐变
    private final static int ANIMATION_TYPE_FADE_INOUT2 = 2;// 从上面出现，从下面消失
    private final static int ANIMATION_TYPE_FLIP_IN_X_ADD_OUT_X = 3;// 绕着x轴出现，绕x轴消失
    private final static int ANIMATION_TYPE_ZOOM_IN_AND_ZOOM_OUT = 4;// 逐渐变大出现，逐渐变小消失
    private final static int ANIMATION_TYPE_ROTATE_IN_AND_ROTATE_OUT = 5;// 旋转进入，旋转消失

    private final static int ANIMATION_TYPE_SLIDE_IN_LEFT_OUT_RIGHT = 6;// 左边渐变滑动进入，右边渐变滑动消失
    private final static int ANIMATION_TYPE_SLIDE_IN_RIGHT_OUT_LEFT = 7;// 右边渐变滑动进入，左边渐变滑动消失
    private final static int ANIMATION_TYPE_SLIDE_IN_UP_OUT_DOWN = 8;// 从上边渐变滑动进入，从下边渐变滑动消失
    private final static int ANIMATION_TYPE_SLIDE_IN_DOWN_OUT_UP = 9;// 从下边渐变滑动进入，从上边渐变滑动消失

    private final static int ANIMATION_TYPE_SLIDE_OUT_RIGHT = 10;// 渐变出现，从右边渐变滑动消失
    private final static int ANIMATION_TYPE_SCALE_AND_ALPHA = 11;// 渐变并缩小到原图出现，然后直接消失
    private final static int ANIMATION_TYPE_SCALE_X_AND_Y = 12;// 围绕左上角进行缩放进入，然后渐变消失
    private final static int ANIMATION_TYPE_LANDING_IN_AND_TAKINGOFF_OUT = 10;

    private final static int ANIMATION_TYPE_STANDING_UP = 13;// 站立的动画
    private final static int ANIMATION_TYPE_ROTATEINUPLEFT = 14;// 左下角旋转进入

    /*
         * AccelerateInterpolator　　　　　                  加速，开始时慢中间加速
         * DecelerateInterpolator　　　 　　                 减速，开始时快然后减速
         * AccelerateDecelerateInterolator　                     先加速后减速，开始结束时慢，中间加速
         * AnticipateInterpolator　　　　　　                 反向 ，先向相反方向改变一段再加速播放
         * AnticipateOvershootInterpolator　                 反向加超越，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值
         * BounceInterpolator　　　　　　　                        跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100
         * CycleIinterpolator　　　　　　　　                   循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 *
         * mCycles * Math.PI * input) LinearInterpolator　　　 线性，线性均匀改变
         * OvershottInterpolator　　　　　　                  超越，最后超出目的值然后缓慢改变到目的值
         * TimeInterpolator　　　　　　　　　                        一个接口，允许你自定义interpolator，以上几个都是实现了这个接口
         */

    private void makeAnimation(ImageView showView, ImageView goneView,
                               int aniType) {
        AnimatorSet aSet = new AnimatorSet();
        ViewGroup parent = (ViewGroup) showView.getParent();
        int distance = parent.getWidth() - showView.getLeft();
        switch (aniType) {
            case ANIMATION_TYPE_FADE_INOUT:
                // 透明度渐变
                aSet.playTogether(ObjectAnimator.ofFloat(showView, "alpha", 0f, 1f)
                                .setDuration(1000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1f, 0f)
                                .setDuration(1000));
                aSet.setInterpolator(new AccelerateInterpolator());
                break;
            case ANIMATION_TYPE_FADE_INOUT2:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "translationY",
                                -showView.getHeight() / 4, 0).setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationY", 0,
                                goneView.getHeight() / 4).setDuration(1200));

                break;
            case ANIMATION_TYPE_FLIP_IN_X_ADD_OUT_X:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "rotationX", 90, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "rotationX", 0, -45, -90)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0, 0)
                                .setDuration(1200));
                break;

            case ANIMATION_TYPE_ZOOM_IN_AND_ZOOM_OUT:
                aSet.playTogether(ObjectAnimator.ofFloat(showView, "scaleX", 0, 1)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(showView, "scaleY", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "scaleX", 1, 0.3f, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "scaleY", 1, 0.3f, 0)
                                .setDuration(1200));
                break;

            case ANIMATION_TYPE_ROTATE_IN_AND_ROTATE_OUT:

                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "rotation", -200, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "scaleX", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "scaleY", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "rotation", 0, 200)
                                .setDuration(1200)

                );
                break;

            case ANIMATION_TYPE_SLIDE_IN_LEFT_OUT_RIGHT:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "translationX", -distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationX", 0,
                                distance).setDuration(1200)

                );
                break;
            case ANIMATION_TYPE_SLIDE_IN_RIGHT_OUT_LEFT:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "translationX", distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationX", 0,
                                -distance).setDuration(1200)

                );
                break;
            case ANIMATION_TYPE_SLIDE_IN_UP_OUT_DOWN:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "translationY", -distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationY", 0,
                                distance / 4).setDuration(1200));

                break;
            case ANIMATION_TYPE_SLIDE_IN_DOWN_OUT_UP:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),

                        ObjectAnimator.ofFloat(showView, "translationY", distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationY", 0,
                                -distance).setDuration(1200));

                break;

            case ANIMATION_TYPE_SLIDE_OUT_RIGHT:

                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "translationX", 0,
                                distance).setDuration(1200)

                );
                break;
            case ANIMATION_TYPE_SCALE_AND_ALPHA:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "scaleX", 4f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "scaleY", 4f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 0).setDuration(
                                1200)

                );
                break;

            case ANIMATION_TYPE_SCALE_X_AND_Y:

                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "scaleX", 0f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "scaleY", 0f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "pivotX",
                                showView.getLeft()).setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "pivotY",
                                showView.getTop()).setDuration(2000)
                        // ObjectAnimator.ofFloat(showView, "alpha", 1,0).setDuration(4000)
                        // ObjectAnimator.ofFloat(showView, "alpha", 1, 0)
                        // .setDuration(1200)
                );
                break;

            case ANIMATION_TYPE_STANDING_UP:

                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "scaleX", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "scaleY", 0, 1),
                        ObjectAnimator.ofFloat(showView, "translationX", -distance,
                                0).setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "rotation", -120, 0)
                                .setDuration(2000));
                break;
            case ANIMATION_TYPE_ROTATEINUPLEFT:
                float y = showView.getHeight();
                aSet.playTogether(
                        ObjectAnimator.ofFloat(showView, "rotation", 90, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "pivotX", 0, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(showView, "pivotY", y, y)
                                .setDuration(2000));

        }

        aSet.start();
    }


    public static void showClickAnimation(View view, Animator.AnimatorListener listener) {
        AnimatorSet aSet = new AnimatorSet();
        aSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.05f, 1.0f)
                        .setDuration(500),
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1)
                        .setDuration(500));
        if (listener != null) {
            aSet.addListener(listener);
        }
        aSet.start();
    }

}
