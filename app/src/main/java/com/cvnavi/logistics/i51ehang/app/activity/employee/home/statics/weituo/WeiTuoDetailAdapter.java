package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.weituo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarDetaiItemModel;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;

import java.util.List;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午3:56
 * 描述：委托详情适配器
 ************************************************************************************/


public class WeiTuoDetailAdapter extends BaseAdapter {


    public static final int FROM_SEND_CAR = 1;
    public static final int FROM_WEI_TUO = 2;
    private Context context;
    private List<SendCarDetaiItemModel.DataValueBean.MainTicketBean> list;
    private MyOnClickItemListener listener;
    private ItemView itemView;
    private int type = FROM_SEND_CAR;

    public WeiTuoDetailAdapter(Context context, List<SendCarDetaiItemModel.DataValueBean.MainTicketBean> list, int fromType) {
        this.context = context;
        this.list = list;
        this.type = fromType;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemView = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_weituo_detail_item, null);
            itemView.order_num = (TextView) convertView.findViewById(R.id.order_num);
            itemView.begin = (TextView) convertView.findViewById(R.id.begin);
            itemView.end = (TextView) convertView.findViewById(R.id.end);
            itemView.name = (TextView) convertView.findViewById(R.id.name);
            itemView.name_detail = (TextView) convertView.findViewById(R.id.name_detail);
            itemView.carState = (TextView) convertView.findViewById(R.id.carState);
            itemView.timeType = (TextView) convertView.findViewById(R.id.timeType);
            itemView.time = (TextView) convertView.findViewById(R.id.time);
            itemView.time_ll = (LinearLayout) convertView.findViewById(R.id.time_ll);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        SendCarDetaiItemModel.DataValueBean.MainTicketBean info = list.get(position);


        if (info != null) {


            //货单号
            if (!TextUtils.isEmpty(info.getTicket_No())) {
                itemView.order_num.setText(info.getTicket_No());
            } else {
                itemView.order_num.setText("--");
            }

            //起始

            if (!TextUtils.isEmpty(info.getSendStation())) {
                itemView.begin.setText(info.getSendStation());
            } else {
                itemView.begin.setText("--");
            }

            //终点
            if (!TextUtils.isEmpty(info.getArrStation())) {
                itemView.end.setText(info.getArrStation());
            } else {
                itemView.end.setText("--");
            }

            //物品名

            if (!TextUtils.isEmpty(info.getGoods_Breed())) {
                itemView.name.setText(info.getGoods_Breed());
            } else {
                itemView.name.setText("--");
            }


            if (!TextUtils.isEmpty(info.getGoods_Num())
                    && !TextUtils.isEmpty(info.getGoods_Casing())
                    && !TextUtils.isEmpty(info.getGoods_Weight())
                    && !TextUtils.isEmpty(info.getBulk_Weight())) {
                itemView.name_detail.setText(info.getGoods_Casing() + "  " + info.getGoods_Num() + "件/" + info.getGoods_Weight() + "kg/" + info.getBulk_Weight() + "m³");
            } else {
                itemView.name_detail.setText("--");
            }


            if (type == FROM_SEND_CAR) {
                //如果是派车单详情
                itemView.timeType.setText("签收时间");
                if (!TextUtils.isEmpty(info.getDeliver_DateTime())) {
                    itemView.time.setText(info.getDeliver_DateTime());
                    itemView.time_ll.setVisibility(View.VISIBLE);
                } else {
                    itemView.time_ll.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(info.getCarStates())) {
                    itemView.carState.setText(info.getCarStates());
                } else {
                    itemView.carState.setText("");
                }

            } else {
                //如果是委托详情
                itemView.timeType.setText("制单时间:");
                if (!TextUtils.isEmpty(info.getOperate_DateTime())) {
                    itemView.time.setText(info.getOperate_DateTime());
                } else {
                    itemView.time.setText("--");
                }
                itemView.carState.setText("");
            }
        }
        return convertView;
    }

    class ItemView {
        TextView order_num;
        TextView begin;
        TextView end;
        TextView name;
        TextView name_detail;
        TextView carState;
        TextView time;
        TextView timeType;
        LinearLayout time_ll;

    }
}