package com.cvnavi.logistics.i51ehang.app.widget.markview;

import android.content.Context;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.Utils;

/**
 * Created by fan on 2016/7/28.
 */
public class MyMarkView extends MarkerView {

    private TextView tvContent;
    private String clickValue;
    private TextView title;

    public MyMarkView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    public MyMarkView(Context context, int layoutResource, TextView title) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.tvContent);
        this.title = title;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
            setClickValue(ce.getHigh() + "");
            if (title != null) {
                title.setText(ce.getHigh() + "");
            }
        } else {
            tvContent.setText("" + Utils.formatNumber(e.getVal(), 0, true));
            setClickValue(e.getVal() + "");
            if (title != null) {
                title.setText(e.getVal() + "");
                tvContent.setText(e.getVal()+"");
            }
        }
    }

    @Override
    public int getXOffset(float v) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float v) {
        return -getHeight();
    }

    public String getClickValue() {
        return clickValue;
    }

    public void setClickValue(String clickValue) {
        this.clickValue = clickValue;
    }
}
