package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.queryorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrder;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.trans.OnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.List;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/17 上午9:21
 * 描述：查询订单数据库获取的listview
 ************************************************************************************/

public class DriverHomeSearchListViewAdapter extends BaseAdapter {

    private Context context;
    private ItemView itemView;
    private List<mOrder> list;
    private OnClickItemListener listener;

    public DriverHomeSearchListViewAdapter(Context context, List<mOrder> list, OnClickItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemView = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.diver_query_order_item, null);
            itemView.consignee_tv = (TextView) convertView.findViewById(R.id.consignee_tv);
            itemView.begin_tv = (TextView) convertView.findViewById(R.id.begin_tv);
            itemView.receive_tv = (TextView) convertView.findViewById(R.id.receive_tv);
            itemView.num_tv = (TextView) convertView.findViewById(R.id.num_tv);
            itemView.pinming_tv = (TextView) convertView.findViewById(R.id.pinming_tv);
            itemView.weight_tv = (TextView) convertView.findViewById(R.id.weight_tv);
            itemView.space_tv = (TextView) convertView.findViewById(R.id.space_tv);
            itemView.reconmend_tv = (TextView) convertView.findViewById(R.id.reconmend_tv);
            itemView.sign_state_tv = (TextView) convertView.findViewById(R.id.sign_state_tv);
            itemView.count_tv = (TextView) convertView.findViewById(R.id.count_tv);
            itemView.root_ll = (LinearLayout) convertView.findViewById(R.id.root_ll);
            itemView.sign_state_tv = (TextView) convertView.findViewById(R.id.sign_state_tv);
            itemView.total_fee_ll = (LinearLayout) convertView.findViewById(R.id.total_fee_ll);
            convertView.setTag(itemView);

        } else {
            itemView = (ItemView) convertView.getTag();
        }


        mOrder info = list.get(position);

        itemView.sign_state_tv.setVisibility(View.INVISIBLE);
        if (info != null) {
            itemView.root_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }

                }
            });
            itemView.total_fee_ll.setVisibility(View.INVISIBLE);
            itemView.consignee_tv.setText(info.Ticket_No);
            itemView.count_tv.setText(info.Ticket_Fee);
            itemView.begin_tv.setText(info.SendStation);
            itemView.receive_tv.setText(info.ArrStation);
            itemView.pinming_tv.setText(info.Goods_Breed);
            itemView.num_tv.setText(info.Goods_Num + "件");
            itemView.weight_tv.setText(String.format(Utils.getResourcesString(R.string.my_order_kg), info.Goods_Weight));
            itemView.space_tv.setText(String.format(Utils.getResourcesString(R.string.my_order_m), info.Bulk_Weight));
            itemView.reconmend_tv.setText(info.Operate_DateTime);
            itemView.sign_state_tv.setText(info.Deliver_Status);
        }


        return convertView;
    }

    private class ItemView {
        TextView consignee_tv;
        TextView begin_tv;
        TextView receive_tv;
        TextView num_tv;
        TextView pinming_tv;
        TextView weight_tv;
        TextView space_tv;
        TextView reconmend_tv;
        TextView sign_state_tv;
        TextView count_tv;
        LinearLayout root_ll;
        LinearLayout total_fee_ll;
    }


}
