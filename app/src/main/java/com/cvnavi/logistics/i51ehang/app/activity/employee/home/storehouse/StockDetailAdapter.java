package com.cvnavi.logistics.i51ehang.app.activity.employee.home.storehouse;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.caiwu.CaiWuDetailAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.response.KuCunDetailModel;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:04
*描述：库存明细界面
************************************************************************************/


public class StockDetailAdapter extends BaseAdapter {

    private Context context;
    private List<KuCunDetailModel.DataValueBean> list;
    private ItemView itemView;

    public StockDetailAdapter(Context context, List<KuCunDetailModel.DataValueBean> list) {
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemView = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_stock_detail_item, null);
            itemView.Operate_DateTime = (TextView) convertView.findViewById(R.id.Operate_DateTime);
            itemView.order_num = (TextView) convertView.findViewById(R.id.order_num);
            itemView.begin = (TextView) convertView.findViewById(R.id.begin);
            itemView.end = (TextView) convertView.findViewById(R.id.end);
            itemView.name = (TextView) convertView.findViewById(R.id.name);
            itemView.detail = (TextView) convertView.findViewById(R.id.detail);
            itemView.get = (TextView) convertView.findViewById(R.id.get);
            itemView.kucun = (TextView) convertView.findViewById(R.id.kucun);
            itemView.time = (TextView) convertView.findViewById(R.id.time);
            itemView.foreView = (TextView) convertView.findViewById(R.id.foreView);
            itemView.back_view = (TextView) convertView.findViewById(R.id.back_view);
            itemView.select_top_line = (ImageView) convertView.findViewById(R.id.select_top_line);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        KuCunDetailModel.DataValueBean info = list.get(position);

        if (info != null) {
            if (!TextUtils.isEmpty(info.getRK_DateTime())) {
                itemView.Operate_DateTime.setText(DateUtil.strOldFormat2NewFormat(info.getRK_DateTime(), DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MD));
            } else {
                itemView.Operate_DateTime.setText("--");
            }

            if (!TextUtils.isEmpty(info.getTicket_No())) {
                itemView.order_num.setText(info.getTicket_No());
            } else {
                itemView.order_num.setText("--");
            }


            if (!TextUtils.isEmpty(info.getSendStation())) {
                itemView.begin.setText(info.getSendStation());
            } else {
                itemView.begin.setText("--");
            }

            if (!TextUtils.isEmpty(info.getArrStation())) {
                itemView.end.setText(info.getArrStation());
            } else {
                itemView.end.setText("--");
            }

            if (!TextUtils.isEmpty(info.getGoods_Breed())) {
                itemView.name.setText(info.getGoods_Breed());
            } else {
                itemView.name.setText("--");
            }

            if (!TextUtils.isEmpty(info.getGoods_Num())) {
                itemView.detail.setText(info.getGoods_Num() + "件/" + info.getFastGoods_Weight() + "kg/" + info.getBulk_Weight() + "m³");
            } else {
                itemView.detail.setText("--");
            }

            if (!TextUtils.isEmpty(info.getTotal_Fee())) {
                itemView.get.setText(info.getTotal_Fee());
            } else {
                itemView.get.setText("--");
            }

            if (!TextUtils.isEmpty(info.getRoomTimeOutHour())) {
                itemView.kucun.setText(info.getRoomTimeOutHour()+"小时");
            } else {
                itemView.kucun.setText("--");
            }

            if (!TextUtils.isEmpty(info.getRK_DateTime())) {
                itemView.time.setText(info.getRK_DateTime());
            } else {
                itemView.time.setText("--");
            }


            /**
             * 判断是否显示线
             */
            if (position != list.size() - 1) {
                KuCunDetailModel.DataValueBean start = list.get(position);
                KuCunDetailModel.DataValueBean next = list.get(position + 1);
                if (CaiWuDetailAdapter.showLineView(start.getRK_DateTime(), next.getRK_DateTime())) {
                    itemView.foreView.setVisibility(View.VISIBLE);
                } else {
                    itemView.foreView.setVisibility(View.INVISIBLE);
                }
                itemView.back_view.setVisibility(View.VISIBLE);

            } else {
                itemView.foreView.setVisibility(View.INVISIBLE);
                itemView.back_view.setVisibility(View.INVISIBLE);
            }


            /**
             * 判断是会否显示时间
             */
            if (position != 0) {
                KuCunDetailModel.DataValueBean start = list.get(position - 1);
                KuCunDetailModel.DataValueBean next = list.get(position);
                if (!CaiWuDetailAdapter.showLineView(start.getRK_DateTime(), next.getRK_DateTime())) {
                    itemView.Operate_DateTime.setVisibility(View.INVISIBLE);
                } else {
                    itemView.Operate_DateTime.setVisibility(View.VISIBLE);
                }
            } else {
                itemView.Operate_DateTime.setVisibility(View.VISIBLE);
            }


        }


        return convertView;
    }


    class ItemView {
        TextView Operate_DateTime;
        TextView order_num;
        TextView begin;
        TextView end;
        TextView name;//品名
        TextView detail;
        TextView get;
        TextView kucun;
        TextView time;
        TextView foreView;
        TextView back_view;
        ImageView select_top_line;


    }


}
