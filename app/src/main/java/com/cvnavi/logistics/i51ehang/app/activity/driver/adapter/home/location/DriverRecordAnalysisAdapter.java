package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarHistoryLocusAnalysis;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.ArrayList;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/1.
 */
public class DriverRecordAnalysisAdapter extends BaseAdapter {
    private ArrayList<mCarHistoryLocusAnalysis> list;
    private Context context;


    public DriverRecordAnalysisAdapter(ArrayList<mCarHistoryLocusAnalysis> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @Override
    public int getCount() {
        if (list == null) {
            return 0;

        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mCarHistoryLocusAnalysis bean = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_driver_record_analysis_lv_item, null);
        }
        TextView drive_tv = ViewHolder.get(convertView, R.id.drive_tv);
        TextView min_tv = ViewHolder.get(convertView, R.id.min_tv);
        TextView startTime_tv = ViewHolder.get(convertView, R.id.startTime_tv);
        TextView endTime_tv = ViewHolder.get(convertView, R.id.endTime_tv);

        if (bean.Status_Oid.equals("1")) {
            drive_tv.setText("停车");
        } else if (bean.Status_Oid.equals("0")) {
            drive_tv.setText("行驶");
        }

        SetViewValueUtil.setTextViewValue(min_tv, bean.Time_Description);
        SetViewValueUtil.setTextViewValue(startTime_tv, bean.Begin_DateTime);
        SetViewValueUtil.setTextViewValue(endTime_tv, bean.End_DateTime);

        return convertView;
    }


}
