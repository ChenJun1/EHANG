package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.caiwu.CaiWuDetailAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.mGetOrederList;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.ArrayList;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:55
*描述：货单明细列表
************************************************************************************/

public class DriverOrderListAdapter extends BaseAdapter {

    private ArrayList<mGetOrederList> list;
    private Context context;

    public DriverOrderListAdapter(ArrayList<mGetOrederList> list, Context context) {
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
        mGetOrederList bean = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_order_list_item, parent, false);

        }

        TextView Operate_DateTime = ViewHolder.get(convertView, R.id.Operate_DateTime);
        TextView Ticket_No_text = ViewHolder.get(convertView, R.id.Ticket_No_text);
        TextView SendStation_text = ViewHolder.get(convertView, R.id.SendStation_text);
        TextView ArrStation_text = ViewHolder.get(convertView, R.id.ArrStation_text);
        TextView Ticket_Fee_text = ViewHolder.get(convertView, R.id.Ticket_Fee_text);
        TextView weiqianshou = ViewHolder.get(convertView, R.id.weiqianshou);
        TextView zuofei = ViewHolder.get(convertView, R.id.zuofei);
        TextView foreView = ViewHolder.get(convertView, R.id.foreView);
        TextView back_view = ViewHolder.get(convertView, R.id.back_view);


        LinearLayout qianshou_layout = ViewHolder.get(convertView, R.id.qianshou_layout);

        Operate_DateTime.setText(DateUtil.strOldFormat2NewFormat(bean.Operate_DateTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MD));


        /**
         * 判断是否显示线
         */
        if (position != list.size() - 1) {
            mGetOrederList start = list.get(position);
            mGetOrederList next = list.get(position + 1);
            if (CaiWuDetailAdapter.showLineView(start.Operate_DateTime, next.Operate_DateTime)) {
                foreView.setVisibility(View.VISIBLE);
            } else {
                foreView.setVisibility(View.INVISIBLE);
            }
            back_view.setVisibility(View.VISIBLE);

        } else {
            foreView.setVisibility(View.INVISIBLE);
            back_view.setVisibility(View.INVISIBLE);
        }


        /**
         * 判断是会否显示时间
         */
        if (position != 0) {
            mGetOrederList start = list.get(position - 1);
            mGetOrederList next = list.get(position);
            if (!CaiWuDetailAdapter.showLineView(start.Operate_DateTime, next.Operate_DateTime)) {
                Operate_DateTime.setVisibility(View.INVISIBLE);
            } else {
                Operate_DateTime.setVisibility(View.VISIBLE);
            }
        } else {
            Operate_DateTime.setVisibility(View.VISIBLE);
        }


        SetViewValueUtil.setTextViewValue(Ticket_No_text, bean.Ticket_No);
        SetViewValueUtil.setTextViewValue(SendStation_text, DateUtil.strOldFormat2NewFormat(bean.Operate_DateTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MDHM));
        SetViewValueUtil.setTextViewValue(Ticket_Fee_text, bean.Total_Fee + "元");

        if (bean.Deliver_Status.equals("未签收")) {
            weiqianshou.setVisibility(View.VISIBLE);
            qianshou_layout.setVisibility(View.GONE);
        } else {
            weiqianshou.setVisibility(View.GONE);
            qianshou_layout.setVisibility(View.VISIBLE);
            SetViewValueUtil.setTextViewValue(ArrStation_text, DateUtil.strOldFormat2NewFormat(bean.Deliver_DateTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MDHM));
        }
        if (bean.Ticket_Status.equals("正常")) {
            zuofei.setVisibility(View.GONE);
        } else if (bean.Ticket_Status.equals("作废")) {
            zuofei.setVisibility(View.VISIBLE);
            weiqianshou.setVisibility(View.GONE);
        }


        return convertView;
    }
}
