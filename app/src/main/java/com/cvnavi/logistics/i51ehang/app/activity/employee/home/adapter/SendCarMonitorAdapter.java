package com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarMonitorResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.widget.view.RoundProgressBar;

import java.util.List;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午3:09
 * 描述：发车监控的适配器
 ************************************************************************************/


public class SendCarMonitorAdapter extends BaseAdapter {

    private List<SendCarMonitorResponse.DataValueBean> dataList;//数据
    private Context context;
    private ItemView itemView;
    private MyOnClickItemListener listener;

    public SendCarMonitorAdapter(List<SendCarMonitorResponse.DataValueBean> dataList, Context context, MyOnClickItemListener listener) {
        this.dataList = dataList;
        this.context = context;
        this.listener = listener;
    }


    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        } else {
            return dataList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemView = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_send_car_monitor_detail, null);
            itemView.roundProgressBar = (RoundProgressBar) convertView.findViewById(R.id.roundProgressBar);
            itemView.car_code = (TextView) convertView.findViewById(R.id.car_code);
            itemView.from = (TextView) convertView.findViewById(R.id.from);
            itemView.to = (TextView) convertView.findViewById(R.id.to);
            itemView.pre_time_tv = (TextView) convertView.findViewById(R.id.pre_time_tv);
            itemView.pre_time_des = (TextView) convertView.findViewById(R.id.pre_time_des);
            itemView.line_tv = (TextView) convertView.findViewById(R.id.line_tv);
            itemView.curr_address = (TextView) convertView.findViewById(R.id.curr_address);
            itemView.send_time = (TextView) convertView.findViewById(R.id.send_time);
            itemView.num = (TextView) convertView.findViewById(R.id.num);
            itemView.root_ll = (LinearLayout) convertView.findViewById(R.id.root);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        final SendCarMonitorResponse.DataValueBean info = dataList.get(position);
        if (info == null) {
            return null;
        }

        //设置序号
        if (!TextUtils.isEmpty(info.getGPS_Status()) && info.getGPS_Status().equals("0")) {
            itemView.num.setBackgroundResource(R.drawable.shape_send_car_num);
            itemView.num.setText(position + 1 + "");
        } else {
            itemView.num.setBackgroundResource(R.drawable.shape_send_car_num_error);
            itemView.num.setText(position + 1 + "");
        }


        if (!TextUtils.isEmpty(info.getCarCode())) {
            //设置车牌号
            itemView.car_code.setText(info.getCarCode());
        } else {
            itemView.car_code.setText("--");
        }

        //设置出发地
        if (!TextUtils.isEmpty(info.getCurrentNodeName())) {
            itemView.from.setText(info.getCurrentNodeName());
        } else {
            itemView.from.setText("--");
        }

        //设置目的地
        if (!TextUtils.isEmpty(info.getNextNodeName())) {
            itemView.to.setText(info.getNextNodeName());
        } else {
            itemView.to.setText("--");
        }

        //预计到达时间
        if (!TextUtils.isEmpty(info.getShould_Arr_DateTime())) {
            itemView.pre_time_tv.setText(info.getShould_Arr_DateTime());
            itemView.pre_time_des.setText("预计到达时间");
        } else {
            if (!TextUtils.isEmpty(info.getNextNode_Arr_DateTime())) {
                itemView.pre_time_tv.setText(info.getNextNode_Arr_DateTime());
            } else {
                itemView.pre_time_tv.setText("--");
            }
            itemView.pre_time_des.setText("到达时间");
        }

        //设置线路名称
        if (!TextUtils.isEmpty(info.getLine_Name())) {

            itemView.line_tv.setText(info.getLine_Name());
        } else {
            itemView.line_tv.setText("--");
        }

        //当前的详细地址
        if (!TextUtils.isEmpty(info.getCHS_Address())) {
            itemView.curr_address.setText(info.getCHS_Address());
        } else {
            itemView.curr_address.setText("--");
        }

        if (!TextUtils.isEmpty(info.getStartTime())) {
            //设置实际发车时间
            itemView.send_time.setText(info.getStartTime());
        } else {
            itemView.send_time.setText("--");
        }


        itemView.roundProgressBar.setMax(100);
        if (!TextUtils.isEmpty(info.getRun_Ratio())) {
            Double ratio = Double.parseDouble(info.getRun_Ratio());
            //设置随机数
            int pos = (int) (ratio * 100);

            //设置颜色
            if (pos < 30) {
                itemView.roundProgressBar.setCricleProgressColor(0xffae68fc);
            } else if (pos < 60 && pos >= 30) {
                itemView.roundProgressBar.setCricleProgressColor(0xfff49b29);
            } else if (pos < 100 && pos >= 60) {
                itemView.roundProgressBar.setCricleProgressColor(0xff2efcfe);
            } else if (pos == 100) {
                itemView.roundProgressBar.setCricleProgressColor(0xff2efcfe);
            }

            //没有预计到达时间，说明已经到达 ，设置100
            if (!TextUtils.isEmpty(info.getShould_Arr_DateTime())) {
                itemView.roundProgressBar.startAnimation(pos);
            } else {
                itemView.roundProgressBar.startAnimation(100);
                itemView.roundProgressBar.setCricleProgressColor(0xff2efcfe);
            }

        } else {
            //没有进度直接显示100%
            itemView.roundProgressBar.startAnimation(100);
            itemView.roundProgressBar.setCricleProgressColor(0xff2efcfe);
        }


        itemView.root_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.myOnClickItem(position, info);
                }
            }
        });


        return convertView;
    }

    class ItemView {
        public TextView car_code;
        public TextView from;
        public TextView to;
        public TextView pre_time_tv;
        public TextView pre_time_des;
        public TextView line_tv;
        public TextView curr_address;
        public TextView send_time;
        public TextView num;
        public LinearLayout root_ll;
        public RoundProgressBar roundProgressBar;
    }


    /**
     * 设置进度
     *
     * @param pos
     * @param roundProgressBar
     */
    private void setProgress(int pos, RoundProgressBar roundProgressBar) {


    }


}
