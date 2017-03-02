package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.queryorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.litepal.Inquiries;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.order.MyOrderListener;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:20
*描述：货单查询适配器
************************************************************************************/

public class DriverHomeQueryOrderAdapter extends BaseAdapter {

    private Context context;

    private List<Inquiries> list;
    private ViewHolder holder;
    private MyOrderListener linstener;

    public DriverHomeQueryOrderAdapter(List<Inquiries> list, Context context, MyOrderListener linstener) {
        this.context = context;
        this.list = list;
        this.linstener = linstener;

    }

    public void setList(List<Inquiries> list) {
        this.list = list;
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_driver_home_query_order_item, null);
            holder.placeTv = (TextView) convertView.findViewById(R.id.place_tv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.time_tv);
            holder.orderNumTv = (TextView) convertView.findViewById(R.id.order_num_tv);
            holder.destinationTv = (TextView) convertView.findViewById(R.id.destination_tv);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Inquiries inquiries = list.get(position);
        if (inquiries != null) {
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (linstener != null) {
                        linstener.onClickOrder(position);
                    }
                }
            });
            //设置目的地
            holder.destinationTv.setText(inquiries.getArriving());
            //订单号
            holder.orderNumTv.setText(inquiries.getOrderId());
            //始发地
            holder.placeTv.setText(inquiries.getBengining());
            holder.timeTv.setText(DateUtil.strOldFormat2NewFormat(inquiries.getData(), DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MD));

        }


        return convertView;
    }


    class ViewHolder {
        TextView placeTv;
        TextView timeTv;
        TextView orderNumTv;
        TextView destinationTv;
        LinearLayout ll;

    }
}
