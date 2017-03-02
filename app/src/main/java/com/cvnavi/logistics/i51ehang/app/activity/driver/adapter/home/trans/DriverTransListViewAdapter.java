package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.trans;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.trans.OnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:59
*描述：司机调度运输界面listview 适配器
************************************************************************************/

public class DriverTransListViewAdapter extends BaseAdapter {

    private Context context;
    private List<mCarShift> list;
    private ViewHolder holder;
    private OnClickItemListener listener;

    public DriverTransListViewAdapter(List<mCarShift> list, Context context, OnClickItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    /**
     * How many items are in the data set represented by this Adapter.
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     * @param position Position of the item whose data we want within the adapter's data set.
     * @return The data at the specified position.
     */
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
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_driver_home_trans_listview_item, null);
            holder.typeBtn = (TextView) convertView.findViewById(R.id.type_btn);
            holder.licenseTv = (TextView) convertView.findViewById(R.id.license_tv);
            holder.stateTv = (TextView) convertView.findViewById(R.id.state_tv);
            holder.orderTv = (TextView) convertView.findViewById(R.id.order_tv);
            holder.lineBtn = (Button) convertView.findViewById(R.id.line_btn);
            holder.routeTv = (TextView) convertView.findViewById(R.id.route_tv);
            holder.scheduleDateTv = (TextView) convertView.findViewById(R.id.scheduleDate_tv);
            holder.preDepartureTv = (TextView) convertView.findViewById(R.id.preDeparture_tv);
            holder.layout_ll = (LinearLayout) convertView.findViewById(R.id.layout_ll);
            holder.BoxCarCode_tv = (TextView) convertView.findViewById(R.id.BoxCarCode_tv);
            holder.send_real = (TextView) convertView.findViewById(R.id.send_real);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setInfo(position, holder);
        return convertView;
    }


    private void setInfo(final int position, ViewHolder holder) {
        final mCarShift info = list.get(position);
        if (info != null) {
            if (!TextUtils.isEmpty(info.Traffic_Mode)) {
                if (info.Traffic_Mode.equals("整")) {
                    holder.typeBtn.setBackgroundResource(R.drawable.shape_zheng);
                    holder.typeBtn.setText("整");
                } else {
                    holder.typeBtn.setText("配");
                    holder.typeBtn.setBackgroundResource(R.drawable.shape_pei);
                }
                holder.typeBtn.setVisibility(View.VISIBLE);
            } else {
                holder.typeBtn.setVisibility(View.GONE);
            }

            String BoxCarCode = "";
            if (TextUtils.isEmpty(info.BoxCarCode)) {
                BoxCarCode = "无";
            } else {
                BoxCarCode = info.BoxCarCode;
            }

            SetViewValueUtil.setTextViewValue(holder.BoxCarCode_tv, BoxCarCode);

            holder.layout_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }
                }
            });

            holder.layout_ll.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null) {
                        listener.onLongClick(info, position);
                    }
                    return false;
                }
            });

            holder.licenseTv.setText(info.CarCode);
            if (TextUtils.isEmpty(info.Line_Name)) {
                holder.routeTv.setText("线路：无");
            } else {
                holder.routeTv.setText("线路："+info.Line_Name);
            }

            //发车日期CarCode_Date
            if (!TextUtils.isEmpty(info.CarCode_Date)) {
                holder.scheduleDateTv.setText(info.CarCode_Date);
            } else {
                holder.scheduleDateTv.setText("未知");
            }


            if (!TextUtils.isEmpty(info.Leave_DateTime)) {
                //实际发车日期
                holder.preDepartureTv.setText(DateUtil.strOldFormat2NewFormat(info.Leave_DateTime, DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));
                holder.send_real.setVisibility(View.VISIBLE);
            } else {
                holder.send_real.setVisibility(View.INVISIBLE);
            }


            if (!TextUtils.isEmpty(info.Schedule_Status)) {
                switch (Integer.parseInt(info.Schedule_Status)) {
                    case 0:
                        holder.stateTv.setText("待发车");
                        break;
                    case 1:
                        holder.stateTv.setText("已发车");
                        break;
                    case 2:
                        holder.stateTv.setText("已完成");
                        break;
                    case 4:
                        holder.stateTv.setText("全部");
                        break;
                }
            } else {
                holder.stateTv.setText("全部");
            }


            if (!TextUtils.isEmpty(info.CarCode_No)) {
                switch (Integer.parseInt(info.CarCode_No)) {
                    case 1:
                        holder.orderTv.setBackgroundResource(R.drawable.placeholder1);
                        break;
                    case 2:
                        holder.orderTv.setBackgroundResource(R.drawable.placeholder2);
                        break;
                    case 3:
                        holder.orderTv.setBackgroundResource(R.drawable.placeholder3);
                        break;
                    case 4:
                        holder.orderTv.setBackgroundResource(R.drawable.placeholder4);
                        break;
                    default:
                        holder.orderTv.setBackgroundResource(R.drawable.placeholder4);
                        break;
                }
            }

            holder.orderTv.setText(info.CarCode_No);

        }

    }


    class ViewHolder {
        TextView typeBtn;
        TextView licenseTv;
        TextView stateTv;
        TextView orderTv;
        Button lineBtn;
        TextView routeTv;
        TextView scheduleDateTv;
        TextView preDepartureTv;
        LinearLayout layout_ll;
        TextView BoxCarCode_tv;
        TextView send_real;
    }
}
