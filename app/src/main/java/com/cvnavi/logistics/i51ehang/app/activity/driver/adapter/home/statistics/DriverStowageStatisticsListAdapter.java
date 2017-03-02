package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.DrSendCarDetailActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.caiwu.CaiWuDetailAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.mGetStowageStatisticsList;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.ArrayList;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:56
*描述：配载清单列表适配器
************************************************************************************/

public class DriverStowageStatisticsListAdapter extends BaseAdapter {

    private ArrayList<mGetStowageStatisticsList> list;
    private Context context;

    public DriverStowageStatisticsListAdapter(ArrayList<mGetStowageStatisticsList> list, Context context) {
        this.list = list;
        this.context = context;
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
        mGetStowageStatisticsList bean = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_stowage_statistics_list_item, parent, false);
        }

        TextView Operate_DateTime = ViewHolder.get(convertView, R.id.Operate_DateTime);
        TextView Traffic_Mode_btn = ViewHolder.get(convertView, R.id.Traffic_Mode_btn);
        TextView CarCode_text = ViewHolder.get(convertView, R.id.CarCode_text);
        TextView Line_Oid_btn = ViewHolder.get(convertView, R.id.Line_Oid_btn);
        TextView Line_Name_text = ViewHolder.get(convertView, R.id.Line_Name_text);
        TextView Ticket_Count_text = ViewHolder.get(convertView, R.id.Ticket_Count_text);
        TextView Profit_Fee_text = ViewHolder.get(convertView, R.id.Profit_Fee_text);
        TextView BoxCarCode_text = ViewHolder.get(convertView, R.id.BoxCarCode_text);
        TextView sendCity_tv = ViewHolder.get(convertView, R.id.sendCity_tv);
        TextView weiqianshou = ViewHolder.get(convertView, R.id.weiqianshou);
        TextView arriveCity_tv = ViewHolder.get(convertView, R.id.arriveCity_tv);
        TextView foreView = ViewHolder.get(convertView, R.id.foreView);
        TextView back_view = ViewHolder.get(convertView, R.id.back_view);

        LinearLayout stowage_layout = ViewHolder.get(convertView, R.id.stowage_layout);
        LinearLayout end_time_text_layout = ViewHolder.get(convertView, R.id.end_time_text_layout);


        /**
         * 判断是否显示线
         */
        if (position != list.size() - 1) {
            mGetStowageStatisticsList start = list.get(position);
            mGetStowageStatisticsList next = list.get(position + 1);
            if (CaiWuDetailAdapter.showLineView(start.Letter_Date, next.Letter_Date)) {
                foreView.setVisibility(View.VISIBLE);
            } else {
                foreView.setVisibility(View.INVISIBLE);
            }
            back_view.setVisibility(View.VISIBLE);
        } else {
            foreView.setVisibility(View.INVISIBLE);
            back_view.setVisibility(View.INVISIBLE);
        }


        Operate_DateTime.setText(DateUtil.strOldFormat2NewFormat(bean.Letter_Date, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MD));
        /**
         * 判断是会否显示时间
         */
        if (position != 0) {
            mGetStowageStatisticsList start = list.get(position - 1);
            mGetStowageStatisticsList next = list.get(position);
            if (!CaiWuDetailAdapter.showLineView(start.Letter_Date, next.Letter_Date)) {
                Operate_DateTime.setVisibility(View.INVISIBLE);
            } else {
                Operate_DateTime.setVisibility(View.VISIBLE);
            }
        } else {
            Operate_DateTime.setVisibility(View.VISIBLE);
        }


        if (!TextUtils.isEmpty(bean.Traffic_Mode)) {
            if (bean.Traffic_Mode.equals("配")) {
                SetViewValueUtil.setTextViewValue(Traffic_Mode_btn, "配");
                Traffic_Mode_btn.setBackgroundResource(R.drawable.shape_pei);
            } else {
                SetViewValueUtil.setTextViewValue(Traffic_Mode_btn, "整");
                Traffic_Mode_btn.setBackgroundResource(R.drawable.shape_zheng);
            }
        } else {
            SetViewValueUtil.setTextViewValue(Traffic_Mode_btn, "　");
        }

        if (!TextUtils.isEmpty(bean.Line_Type)) {
            if (bean.Line_Type.equals("干线")) {
                SetViewValueUtil.setTextViewValue(Line_Oid_btn, "线路");
            } else if (bean.Line_Type.equals("支线")) {
                SetViewValueUtil.setTextViewValue(Line_Oid_btn, "线路");
            }

        } else {
            SetViewValueUtil.setTextViewValue(Line_Oid_btn, "　");
        }


        SetViewValueUtil.setTextViewValue(CarCode_text, bean.CarCode);

        if (!TextUtils.isEmpty(bean.Line_Name)) {
            SetViewValueUtil.setTextViewValue(Line_Name_text, bean.Line_Name);
        } else {
            SetViewValueUtil.setTextViewValue(Line_Name_text, "线路：无");
        }

        SetViewValueUtil.setTextViewValue(Ticket_Count_text, bean.Ticket_Count + "票");
        SetViewValueUtil.setTextViewValue(Profit_Fee_text, bean.Profit_Fee + "元");
        SetViewValueUtil.setTextViewValue(BoxCarCode_text, bean.BoxCarCode);
        SetViewValueUtil.setTextViewValue(sendCity_tv, DateUtil.strOldFormat2NewFormat(bean.Leave_DateTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MDHM));

        if ((!TextUtils.isEmpty(bean.Letter_Status) && bean.Letter_Status.equals("未完成"))) {
            end_time_text_layout.setVisibility(View.GONE);
            weiqianshou.setVisibility(View.VISIBLE);
        } else {
            end_time_text_layout.setVisibility(View.VISIBLE);
            weiqianshou.setVisibility(View.GONE);
        }

        SetViewValueUtil.setTextViewValue(arriveCity_tv, DateUtil.strOldFormat2NewFormat(bean.Arrive_DateTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MDHM));

        if (TextUtils.isEmpty(bean.BoxCarCode)) {
            SetViewValueUtil.setTextViewValue(BoxCarCode_text, "无");
        }

        stowage_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list != null) {
                    mGetStowageStatisticsList bean = list.get(position);
                    DrSendCarDetailActivity.start(context, bean.Letter_Oid, null);
                }
            }
        });

        return convertView;
    }
}
