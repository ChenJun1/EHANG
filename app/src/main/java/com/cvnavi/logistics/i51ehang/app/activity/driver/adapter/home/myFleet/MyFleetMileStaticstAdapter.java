package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.myFleet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.statics.MileStaticsResponse;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:18
*描述：里程统计适配器
************************************************************************************/

public class MyFleetMileStaticstAdapter extends BaseAdapter {

    private List<MileStaticsResponse.DataValueBean.DetailBean> listMileage;
    private Context context;
    private ItemView itemView;


    public MyFleetMileStaticstAdapter(List<MileStaticsResponse.DataValueBean.DetailBean> listMileage, Context context) {
        this.listMileage = listMileage;
        this.context = context;
    }


    @Override
    public int getCount() {
        if (listMileage == null) {
            return 0;
        }
        return listMileage.size();
    }

    @Override
    public Object getItem(int position) {
        return listMileage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemView = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_my_fleet_mile_staitc_item, null);
            itemView.rl = (RelativeLayout) convertView.findViewById(R.id.rl);
            itemView.data_tv = (TextView) convertView.findViewById(R.id.data_tv);
            itemView.mileage_tv = (TextView) convertView.findViewById(R.id.mileage_tv);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        MileStaticsResponse.DataValueBean.DetailBean info = listMileage.get(position);
        if (info != null) {
            itemView.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //设置开始时间
            itemView.data_tv.setText(info.getStartTime());
            itemView.mileage_tv.setText(String.format(Utils.getResourcesString(R.string.total_mile_km), info.getDistanceGPS()));
        }

        return convertView;
    }


    private class ItemView {
        public RelativeLayout rl;
        public TextView data_tv;
        public TextView mileage_tv;

    }
}
