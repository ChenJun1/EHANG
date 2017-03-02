package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarHistoryLocus;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.ArrayList;

/**
 * Created by fan on 2016/7/7.
 */
public class DriverRecordDetailAdapter extends BaseAdapter {
    private ArrayList<mCarHistoryLocus> list;
    private Context context;

    public DriverRecordDetailAdapter(ArrayList<mCarHistoryLocus> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
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
        mCarHistoryLocus bean = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_driver_record_detail_lv_item, null);
        }

        TextView CHS_Address = ViewHolder.get(convertView, R.id.CHS_Address_tv);
        TextView Position_DateTime_tv = ViewHolder.get(convertView, R.id.Position_DateTime_tv);
        TextView min_tv = ViewHolder.get(convertView, R.id.min_tv);


        SetViewValueUtil.setTextViewValue(CHS_Address, bean.CHS_Address);
        SetViewValueUtil.setTextViewValue(Position_DateTime_tv, bean.Position_DateTime);
        SetViewValueUtil.setTextViewValue(min_tv, bean.Speed, "km/h");

        return convertView;
    }


}
