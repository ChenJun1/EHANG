package com.cvnavi.logistics.i51ehang.app.activity.employee.myutils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by george on 2016/12/9.
 */

public class FloatingView {
    private final int MESSAGE_HANDLER = 0X3;
    private View view = null;
    private int type;
    private View goneView;

    public FloatingView(View view) {
        this.view = view;
        this.goneView = view;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setView(View view, View goneView) {
        this.view = view;
        this.goneView = goneView;

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MESSAGE_HANDLER:
                    makeAnimation();
                    break;
            }
        }

        ;
    };

    public void remove() {
        mHandler.removeMessages(MESSAGE_HANDLER);
    }

//    public void floatingView() {
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(
////                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.2f, 1.1f, 1)
////                        .setDuration(1500),
////                ObjectAnimator.ofFloat(view, "scaleY", 1, 0.9f, 0.95f, 1f)
////                        .setDuration(1500)
//                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.2f, 1.1f, 1)
//                        .setDuration(7000),
//                ObjectAnimator.ofFloat(view, "scaleX", 1, 0.8f, 0.7f, 1f)
//                        .setDuration(7000)
//
//        );
//        set.start();
//        set.addListener(new Animator.AnimatorListener() {
//
//            @Override
//            public void onAnimationStart(Animator arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator arg0) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator arg0) {
//                // TODO Auto-generated method stub
//                mHandler.sendEmptyMessage(MESSAGE_HANDLER);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//    }


    public final static int ANIMATION_TYPE_FADE_INOUT = 1;// 透明度渐变
    public final static int ANIMATION_TYPE_FADE_INOUT2 = 2;// 从上面出现，从下面消失
    public final static int ANIMATION_TYPE_FLIP_IN_X_ADD_OUT_X = 3;// 绕着x轴出现，绕x轴消失
    public final static int ANIMATION_TYPE_ZOOM_IN_AND_ZOOM_OUT = 4;// 逐渐变大出现，逐渐变小消失
    public final static int ANIMATION_TYPE_ROTATE_IN_AND_ROTATE_OUT = 5;// 旋转进入，旋转消失

    public final static int ANIMATION_TYPE_SLIDE_IN_LEFT_OUT_RIGHT = 6;// 左边渐变滑动进入，右边渐变滑动消失
    public final static int ANIMATION_TYPE_SLIDE_IN_RIGHT_OUT_LEFT = 7;// 右边渐变滑动进入，左边渐变滑动消失
    public final static int ANIMATION_TYPE_SLIDE_IN_UP_OUT_DOWN = 8;// 从上边渐变滑动进入，从下边渐变滑动消失
    public final static int ANIMATION_TYPE_SLIDE_IN_DOWN_OUT_UP = 9;// 从下边渐变滑动进入，从上边渐变滑动消失

    public final static int ANIMATION_TYPE_SLIDE_OUT_RIGHT = 10;// 渐变出现，从右边渐变滑动消失
    public final static int ANIMATION_TYPE_SCALE_AND_ALPHA = 11;// 渐变并缩小到原图出现，然后直接消失
    public final static int ANIMATION_TYPE_SCALE_X_AND_Y = 12;// 围绕左上角进行缩放进入，然后渐变消失
    public final static int ANIMATION_TYPE_LANDING_IN_AND_TAKINGOFF_OUT = 10;

    public final static int ANIMATION_TYPE_STANDING_UP = 13;// 站立的动画
    public final static int ANIMATION_TYPE_ROTATEINUPLEFT = 14;// 左下角旋转进入

    public void makeAnimation() {
        AnimatorSet aSet = new AnimatorSet();

        ViewGroup parent = (ViewGroup) view.getParent();
        int distance = parent.getWidth() - view.getLeft();

        switch (type) {
            case ANIMATION_TYPE_FADE_INOUT:
                // 透明度渐变
                aSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                                .setDuration(1000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1f, 0f)
                                .setDuration(1000));
                aSet.setInterpolator(new AccelerateInterpolator());
                break;
            case ANIMATION_TYPE_FADE_INOUT2:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "translationY",
                                -view.getHeight() / 4, 0).setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationY", 0,
                                goneView.getHeight() / 4).setDuration(1200));

                break;
            case ANIMATION_TYPE_FLIP_IN_X_ADD_OUT_X:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "rotationX", 90, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "rotationX", 0, -45, -90)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0, 0)
                                .setDuration(1200));
                break;

            case ANIMATION_TYPE_ZOOM_IN_AND_ZOOM_OUT:
                aSet.playTogether(ObjectAnimator.ofFloat(view, "scaleX", 0, 1)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(view, "scaleY", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
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
                        ObjectAnimator.ofFloat(view, "rotation", -200, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "scaleX", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "scaleY", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "rotation", 0, 200)
                                .setDuration(1200)

                );
                break;

            case ANIMATION_TYPE_SLIDE_IN_LEFT_OUT_RIGHT:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "translationX", -distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationX", 0,
                                distance).setDuration(1200)

                );
                break;
            case ANIMATION_TYPE_SLIDE_IN_RIGHT_OUT_LEFT:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "translationX", distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationX", 0,
                                -distance).setDuration(1200)

                );
                break;
            case ANIMATION_TYPE_SLIDE_IN_UP_OUT_DOWN:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "translationY", -distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationY", 0,
                                distance / 4).setDuration(1200));

                break;
            case ANIMATION_TYPE_SLIDE_IN_DOWN_OUT_UP:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),

                        ObjectAnimator.ofFloat(view, "translationY", distance,
                                0).setDuration(2000),

                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(1200),
                        ObjectAnimator.ofFloat(goneView, "translationY", 0,
                                -distance).setDuration(1200));

                break;

            case ANIMATION_TYPE_SLIDE_OUT_RIGHT:

                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 1, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "translationX", 0,
                                distance).setDuration(1200)

                );
                break;
            case ANIMATION_TYPE_SCALE_AND_ALPHA:
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "scaleX", 4f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "scaleY", 4f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(goneView, "alpha", 0).setDuration(
                                1200)

                );
                break;

            case ANIMATION_TYPE_SCALE_X_AND_Y:

                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "pivotX",
                                view.getLeft()).setDuration(2000),
                        ObjectAnimator.ofFloat(view, "pivotY",
                                view.getTop()).setDuration(2000)
                        // ObjectAnimator.ofFloat(view, "alpha", 1,0).setDuration(4000)
                        // ObjectAnimator.ofFloat(view, "alpha", 1, 0)
                        // .setDuration(1200)

                );
                break;

            case ANIMATION_TYPE_STANDING_UP:

                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "scaleX", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "scaleY", 0, 1),
                        ObjectAnimator.ofFloat(view, "translationX", -distance,
                                0).setDuration(2000),
                        ObjectAnimator.ofFloat(view, "rotation", -120, 0)
                                .setDuration(2000));
                break;
            case ANIMATION_TYPE_ROTATEINUPLEFT:
                float y = view.getHeight();
                aSet.playTogether(
                        ObjectAnimator.ofFloat(view, "rotation", 90, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "alpha", 0, 1)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "pivotX", 0, 0)
                                .setDuration(2000),
                        ObjectAnimator.ofFloat(view, "pivotY", y, y)
                                .setDuration(2000));
                break;

        }

        aSet.start();
        aSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator arg0) {
                // TODO Auto-generated method stub
                mHandler.sendEmptyMessage(MESSAGE_HANDLER);
            }

            @Override
            public void onAnimationCancel(Animator arg0) {
                // TODO Auto-generated method stub

            }
        });


    }
}
