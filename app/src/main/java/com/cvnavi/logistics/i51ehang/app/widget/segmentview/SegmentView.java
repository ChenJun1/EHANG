package com.cvnavi.logistics.i51ehang.app.widget.segmentview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by fan on 2016/7/18.
 */
public class SegmentView extends LinearLayout{
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private onSegmentViewClickListener listener;

    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SegmentView(Context context) {
        super(context);
        init();
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void init() {
        textView1 = new TextView(getContext());
        textView2 = new TextView(getContext());
        textView3 = new TextView(getContext());
        textView1.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView2.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView3.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView1.setText("最近三天");
        textView2.setText("最近一周");
        textView3.setText("最近一月");
        //noinspection ResourceType
        XmlPullParser xrp = getResources().getXml(R.drawable.seg_text_color_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor(csl);
            textView2.setTextColor(csl);
            textView3.setTextColor(csl);
        } catch (Exception e) {
        }
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView3.setGravity(Gravity.CENTER);
        textView1.setPadding(3, 6, 3, 6);
        textView2.setPadding(3, 6, 3, 6);
        textView3.setPadding(3, 6, 3, 6);
        setSegmentTextSize(16);
        textView1.setBackgroundResource(R.drawable.seg_left);
        textView2.setBackgroundResource(R.drawable.seg_center);
        textView3.setBackgroundResource(R.drawable.seg_right);
        textView1.setSelected(true);
        this.removeAllViews();
        this.addView(textView1);
        this.addView(textView2);
        this.addView(textView3);
        this.invalidate();

        textView1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView1.isSelected()) {
                    listener.onSegmentViewClick(textView1, 0);
                    return;
                }
                textView1.setSelected(true);
                textView2.setSelected(false);
                textView3.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView1, 0);
                }
            }
        });
        textView2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView2.isSelected()) {
                    listener.onSegmentViewClick(textView2, 1);
                    return;
                }
                textView2.setSelected(true);
                textView1.setSelected(false);
                textView3.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView2, 1);
                }
            }
        });

        textView3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView3.isSelected()) {
                    listener.onSegmentViewClick(textView3, 2);
                    return;
                }
                textView3.setSelected(true);
                textView1.setSelected(false);
                textView2.setSelected(false);
                if (listener != null) {
                    listener.onSegmentViewClick(textView3, 2);
                }
            }
        });
    }

    /**
     * 设置字体大小 单位dip
     * <p>
     * 2014年7月18日
     * </p>
     *
     * @param dp
     * @author RANDY.ZHANG
     */
    public void setSegmentTextSize(int dp) {
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    public void setOnSegmentViewClickListener(
            onSegmentViewClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置文字
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            textView1.setText(text);
        }
        if (position == 1) {
            textView2.setText(text);
        }
        if (position == 2) {
            textView3.setText(text);
        }
    }

    /**
     * 全部选为false
     */
    public void setSegmentTextCooler() {
        textView1.setSelected(false);
        textView2.setSelected(false);
        textView3.setSelected(false);
    }

    public interface onSegmentViewClickListener {

        void onSegmentViewClick(TextView v, int position);
    }
}
