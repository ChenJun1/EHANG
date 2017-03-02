package com.cvnavi.logistics.i51ehang.app.widget.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.cvnavi.logistics.i51ehang.app.R;

/**
 * Created by george on 2016/10/25.
 * 圆形进度条
 * 有进度点表示
 */

public class RoundProgressBar extends View {

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public boolean isShowPoint = false;


    private float Progress = 0;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
        mTypedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画笔对象的引用
         */
        Paint paint = new Paint();
        /**
         * 画最外层的大圆环
         */
        int centre = getWidth() / 2; //获取圆心的x坐标
        int radius = (int) (centre - 2 * roundWidth); //圆环的半径
        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

        /**
         *
         * 画进度百分比
         *
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.MONOSPACE); //设置字体
        int percent = (int) (((float) progress / (float) max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(percent + "");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间


        if (textIsDisplayable && style == STROKE) {
            canvas.drawText(percent + "", centre - textWidth / 2, centre + textSize / 2, paint); //画出进度百分比

            paint.setColor(textColor);
            paint.setTextSize(textSize / 2);
            paint.setTypeface(Typeface.DEFAULT);
            canvas.drawText("%", centre + textWidth / 2, centre + textSize / 8, paint); //画出进度百分比

        }

        /**
         * 画圆弧 ，画圆环的进度
         *
         */

        //设置进度是实心还是空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(roundProgressColor);  //设置进度的颜色

        /**
         * 渐变
         */
//        int[] color = new int[]{Utils.getResourcesColor(R.color.progress_1), Utils.getResourcesColor(R.color.progress_2), Utils.getResourcesColor(R.color.progress_3)};
//        LinearGradient linearGradient = new LinearGradient(centre, centre - radius, centre, centre + radius, color, null, Shader.TileMode.CLAMP);
//        paint.setShader(linearGradient);

        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限

        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, 270, 360 * Progress / max, false, paint);  //根据进度画圆弧
                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawArc(oval, 270, 360 * Progress / max, true, paint);  //根据进度画圆弧
                break;
            }
        }

/**
 * 画进度点
 * 30° 角度 的弧度 = 2*PI/360*30
 */
        if (isShowPoint) {
            int rangle = 360 * progress / max;
            //弧度
            double a = 0.0;
            int pointX = 0;
            int pointY = 0;

            if (rangle > 0 && rangle <= 90) {
                a = 2 * Math.PI / 360 * (90 - rangle);
                pointX = centre + (int) (radius * Math.cos(a));
                pointY = centre - (int) (radius * Math.sin(a));
            } else if (rangle > 90 && rangle <= 180) {
                a = 2 * Math.PI / 360 * (rangle - 90);
                pointX = centre + (int) (radius * Math.cos(a));
                pointY = centre + (int) (radius * Math.sin(a));
            } else if (rangle > 180 && rangle <= 270) {
                a = 2 * Math.PI / 360 * (rangle - 180);
                pointX = centre - (int) (radius * Math.sin(a));
                pointY = centre + (int) (radius * Math.cos(a));
            } else if (rangle > 270 && rangle <= 360) {
                a = 2 * Math.PI / 360 * (rangle - 270);
                pointX = centre - (int) (radius * Math.cos(a));
                pointY = centre - (int) (radius * Math.sin(a));
            }

            paint.setColor(roundProgressColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);  //消除锯齿
            canvas.drawCircle(pointX, pointY, radius / 5, paint);
        }


    }

    public synchronized void isShowPoint(boolean isShow) {
        this.isShowPoint = isShow;
    }


    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }


    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }

    }


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }


    public void startAnimation(int progress) {
        this.progress = progress;

//        /*
//         * AccelerateInterpolator　　　　　                  加速，开始时慢中间加速
//         * DecelerateInterpolator　　　 　　                 减速，开始时快然后减速
//         * AccelerateDecelerateInterolator　                     先加速后减速，开始结束时慢，中间加速
//         * AnticipateInterpolator　　　　　　                 反向 ，先向相反方向改变一段再加速播放
//         * AnticipateOvershootInterpolator　                 反向加超越，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值
//         * BounceInterpolator　　　　　　　                        跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100
//         * CycleIinterpolator　　　　　　　　                   循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 *
//         * mCycles * Math.PI * input) LinearInterpolator　　　 线性，线性均匀改变
//         * OvershottInterpolator　　　　　　                  超越，最后超出目的值然后缓慢改变到目的值
//         * TimeInterpolator　　　　　　　　　                        一个接口，允许你自定义interpolator，以上几个都是实现了这个接口
//         */


        final ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Progress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();


    }


//    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:android_custom="http://schemas.android.com/apk/res/com.example.circlepregress"
//    xmlns:tools="http://schemas.android.com/tools"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent" >
//
//
//    <com.example.roundprogressbar.RoundProgressBar
//    android:id="@+id/roundProgressBar2"
//    android:layout_width="80dip"
//    android:layout_height="80dip"
//    android:layout_alignLeft="@+id/roundProgressBar1"
//    android:layout_alignParentBottom="true"
//    android:layout_marginBottom="78dp"
//    android_custom:roundColor="#D1D1D1"
//    android_custom:roundProgressColor="@android:color/black"
//    android_custom:textColor="#9A32CD"
//    android_custom:textIsDisplayable="false"
//    android_custom:roundWidth="10dip"
//    android_custom:textSize="18sp"/>
//    </RelativeLayout>


//    <com.cvnavi.logistics.i51ehang.app.widget.view.RoundProgressBar
//    android:id="@+id/roundProgressBar2"
//    android:layout_width="80dip"
//    android:layout_height="80dip"
//    android:layout_alignParentBottom="true"
//    android:layout_marginBottom="78dp"
//    android_custom:roundColor="#15212a"
//    android_custom:roundWidth="10dip"
//    android_custom:textColor="#82bfa5"
//    android_custom:textIsDisplayable="true"
//    style="0"
//    android_custom:textSize="18sp" />
//
//
//    <!--android_custom:roundProgressColor="@color/colr_home_menu_1"-->

}
