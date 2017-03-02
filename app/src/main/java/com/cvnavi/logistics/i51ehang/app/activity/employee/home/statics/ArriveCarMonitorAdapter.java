package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.ArriveCarMonitorModel;

import java.util.List;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午4:00
 * 描述：到车监控适配器
 ************************************************************************************/


public class ArriveCarMonitorAdapter extends BaseAdapter {
    private Context context;
    private List<ArriveCarMonitorModel.DataValueBean> list;

    private ItemView itemView;

    public ArriveCarMonitorAdapter(Context context, List<ArriveCarMonitorModel.DataValueBean> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_arrive_car_monitor_item, null);
            itemView.carCode = (TextView) convertView.findViewById(R.id.carCode);
            itemView.gua = (TextView) convertView.findViewById(R.id.gua);
            itemView.no = (TextView) convertView.findViewById(R.id.no);
            itemView.line = (TextView) convertView.findViewById(R.id.line);
            itemView.curr_address = (TextView) convertView.findViewById(R.id.curr_address);
            itemView.last_mile = (TextView) convertView.findViewById(R.id.last_mile);
            itemView.arrive_time = (TextView) convertView.findViewById(R.id.arrive_time);
            itemView.send_time = (TextView) convertView.findViewById(R.id.send_time);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }
        ArriveCarMonitorModel.DataValueBean info = list.get(position);
        if (info != null) {

            //车牌号
            if (!TextUtils.isEmpty(info.getCarCode())) {
                itemView.carCode.setText("车牌号：" + info.getCarCode());
            } else {
                itemView.carCode.setText("车牌号：" + "--");
            }

//


            itemView.gua.setText("挂车号：" + "--");


            //线路
            if (!TextUtils.isEmpty(info.getLine_Name())) {
                itemView.line.setText("线路：" + info.getLine_Name());
            } else {
                itemView.line.setText("线路：" + "--");
            }

            //车牌号
            if (!TextUtils.isEmpty(info.getCHS_Address())) {
                itemView.curr_address.setText("当前位置：" + info.getCHS_Address());
            } else {
                itemView.curr_address.setText("当前位置：" + "--");
            }

            //剩余里程
            if (!TextUtils.isEmpty(info.getNextNodeMil())) {
                itemView.last_mile.setText("剩余里程：" + info.getNextNodeMil());
            } else {
                itemView.last_mile.setText("剩余里程：" + "--");
            }

            //预计到达时间
            if (!TextUtils.isEmpty(info.getShould_Arr_DateTime())) {
                itemView.arrive_time.setText(info.getShould_Arr_DateTime());
            } else {
                itemView.arrive_time.setText("预计到达时间：" + "--");
            }

            //发车时间
            if (!TextUtils.isEmpty(info.getStartTime())) {
                itemView.send_time.setText("发车时间：" + info.getStartTime());
            } else {
                itemView.send_time.setText("发车时间：" + "--");
            }

        }


        return convertView;
    }

    class ItemView {
        TextView carCode;
        TextView gua;
        TextView no;
        TextView line;
        TextView curr_address;
        TextView last_mile;
        TextView arrive_time;
        TextView send_time;
    }


}
