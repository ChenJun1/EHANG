package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarHistoryLocusAnalysis;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${ChenJ} on 2016/8/9.
 */
public class MyFleetRecordAdapter extends BaseAdapter {
    private List<mCarHistoryLocusAnalysis> list;
    private Context context;


    public MyFleetRecordAdapter(List<mCarHistoryLocusAnalysis> list, Context context) {
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
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_fleet_record_analysis_lv_item, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (bean.Status_Oid.equals("1")) {
            viewHolder.driveTv.setText("停车");
        } else if (bean.Status_Oid.equals("0")) {
            viewHolder.driveTv.setText("行驶");
        }

        SetViewValueUtil.setTextViewValue(viewHolder.minTv, bean.Time_Description);
        SetViewValueUtil.setTextViewValue(viewHolder.startTimeTv, bean.Begin_DateTime);
        SetViewValueUtil.setTextViewValue(viewHolder.endTimeTv, bean.End_DateTime);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.drive_tv)
        TextView driveTv;
        @BindView(R.id.min_tv)
        TextView minTv;
        @BindView(R.id.startTime_tv)
        TextView startTimeTv;
        @BindView(R.id.endTime_tv)
        TextView endTimeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
