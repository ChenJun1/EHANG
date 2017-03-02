package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.trans;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarSchedulingDriver;

import java.util.ArrayList;
import java.util.List;


/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:59
*描述：司机管理界面
************************************************************************************/

public class DriverManagerListViewAdapter extends BaseAdapter {

    private Context context;
    private List<mCarSchedulingDriver> driverList = new ArrayList<>();

    public DriverManagerListViewAdapter(List<mCarSchedulingDriver> dataList, Context context) {
        this.context = context;
        this.driverList = dataList;
    }

    @Override
    public int getCount() {
        return driverList.size();
    }

    @Override
    public Object getItem(int position) {
        return driverList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_driver_manager_item, null);
            holder.driveNameTv = (TextView) convertView.findViewById(R.id.driver_name_tv);
            holder.phoneNumTv = (TextView) convertView.findViewById(R.id.driver_phone_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final mCarSchedulingDriver carSchedulingDriver = driverList.get(position);
        holder.driveNameTv.setText(carSchedulingDriver.Driver);
        holder.phoneNumTv.setText(carSchedulingDriver.Driver_Tel);

        return convertView;
    }

    class ViewHolder {
        TextView driveNameTv;
        TextView phoneNumTv;
    }
}
