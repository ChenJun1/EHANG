package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.caiwu;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.CaiWuDetailModel;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:51
*描述：财务统计适配器
************************************************************************************/


public class CaiWuDetailAdapter extends BaseAdapter {


    private Context context;
    private List<CaiWuDetailModel.DataValueBean> list;
    private ItemView itemView;

    public CaiWuDetailAdapter(Context context, List<CaiWuDetailModel.DataValueBean> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_caiwu_item, null);
            itemView.time = (TextView) convertView.findViewById(R.id.time);
            itemView.order = (TextView) convertView.findViewById(R.id.order);
            itemView.get = (TextView) convertView.findViewById(R.id.get);
            itemView.ben = (TextView) convertView.findViewById(R.id.ben);
            itemView.dai = (TextView) convertView.findViewById(R.id.dai);
            itemView.run = (TextView) convertView.findViewById(R.id.run);
            itemView.daishou = (TextView) convertView.findViewById(R.id.daishou);
            itemView.foreView = (TextView) convertView.findViewById(R.id.foreView);
            itemView.back_view = (TextView) convertView.findViewById(R.id.back_view);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        CaiWuDetailModel.DataValueBean info = list.get(position);
        if (info != null) {
            if (!TextUtils.isEmpty(info.getOperate_DateTime())) {
                itemView.time.setText(DateUtil.strOldFormat2NewFormat(info.getOperate_DateTime(), DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MD));
            } else {
                itemView.time.setText("--");
            }

            if (!TextUtils.isEmpty(info.getTicket_No())) {
                itemView.order.setText("货单号：" + info.getTicket_No());
            } else {
                itemView.order.setText("货单号：" + "--");
            }


            if (!TextUtils.isEmpty(info.getTotal_Fee())) {
                itemView.get.setText("收入：" + info.getTotal_Fee() + "元");
            } else {
                itemView.get.setText("收入：" + "--");
            }


            if (!TextUtils.isEmpty(info.getTotalPay_Fee())) {
                itemView.ben.setText("成本：" + info.getTotalPay_Fee() + "元");
            } else {
                itemView.ben.setText("成本：" + "--");
            }


            if (!TextUtils.isEmpty(info.getTotal_DD_Fee())) {
                itemView.dai.setText("代垫费：" + info.getTotal_DD_Fee() + "元");
            } else {
                itemView.dai.setText("代垫费：" + "--");
            }

            if (!TextUtils.isEmpty(info.getTicket_Profit())) {
                itemView.run.setText("利润：" + info.getTicket_Profit() + "元");
            } else {
                itemView.run.setText("利润：" + "--");
            }

            if (!TextUtils.isEmpty(info.getGoods_Fee())) {
                itemView.daishou.setText("代收贷款：" + info.getGoods_Fee() + "元");
            } else {
                itemView.daishou.setText("代收贷款：" + "--");
            }

            /**
             * 判断是否显示线
             */
            if (position != list.size() - 1) {
                CaiWuDetailModel.DataValueBean start = list.get(position);
                CaiWuDetailModel.DataValueBean next = list.get(position + 1);
                if (showLineView(start.getOperate_DateTime(), next.getOperate_DateTime())) {
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
                CaiWuDetailModel.DataValueBean start = list.get(position - 1);
                CaiWuDetailModel.DataValueBean next = list.get(position);
                if (!showLineView(start.getOperate_DateTime(), next.getOperate_DateTime())) {
                    itemView.time.setVisibility(View.INVISIBLE);
                } else {
                    itemView.time.setVisibility(View.VISIBLE);
                }
            } else {
                itemView.time.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }

    /**
     * 比较两个view的时间
     *
     * @param forTime
     * @param backTime
     * @return
     */
    public static boolean showLineView(String forTime, String backTime) {
        if (TextUtils.isEmpty(forTime) || TextUtils.isEmpty(backTime)) {
            return true;
        }

        if (DateUtil.strOldFormat2NewFormat(forTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MD).equals(DateUtil.strOldFormat2NewFormat(backTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MD))) {
            return false;
        }
        return true;
    }


    class ItemView {

        TextView time;
        TextView order;
        TextView get;
        TextView ben;
        TextView dai;
        TextView run;
        TextView daishou;
        TextView foreView;
        TextView back_view;

    }

}