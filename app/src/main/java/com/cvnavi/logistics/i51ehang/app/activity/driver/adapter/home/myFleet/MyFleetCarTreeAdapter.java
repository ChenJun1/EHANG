package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.myFleet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:17
*描述：我的车队筛选车辆适配器
************************************************************************************/

public class MyFleetCarTreeAdapter extends BaseAdapter {

    private List<mCarInfo> list;
    private Context context;
    private ItemView viewHolder;
    private MyOnClickItemListener listener;

    public MyFleetCarTreeAdapter(List<mCarInfo> list, Context context, MyOnClickItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_driver_car_list_parent_group, null);
            viewHolder.carCode = (TextView) convertView.findViewById(R.id.car_code);
            viewHolder.parentGroupTV = (TextView) convertView.findViewById(R.id.parentGroupTV);
            viewHolder.rootLl = (RelativeLayout) convertView.findViewById(R.id.root_ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ItemView) convertView.getTag();
        }


        final mCarInfo info = list.get(position);
        if (info != null ) {
            //展示机构号
            viewHolder.parentGroupTV.setText(info.Org_Name);
            viewHolder.carCode.setText(info.CarCode);
            viewHolder.rootLl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.myOnClickItem(position, info);
                    }
                }
            });
        }
        return convertView;
    }

    class ItemView {
        TextView parentGroupTV;
        RelativeLayout rootLl;
        TextView carCode;
    }


}
