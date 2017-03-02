package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.myFleet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetMyCarFleetResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.order.MyOrderListener;
import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午9:16
*描述：我的车队适配器
************************************************************************************/


public class MyFleetAdapter extends BaseAdapter {
    private Context context;
    private List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> list;
    private ViewHolder holder;
    private MyOnClickItemListener listener;
    private MyOrderListener orderListener;

    public MyFleetAdapter(Context context, List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> list, MyOnClickItemListener listener, MyOrderListener orderListener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        this.orderListener = orderListener;
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_my_fleet_item, null);
            holder.carCodeTv = (TextView) convertView.findViewById(R.id.carCode_tv);
            holder.locationTv = (TextView) convertView.findViewById(R.id.location_tv);
            holder.orgNameTv = (TextView) convertView.findViewById(R.id.org_name_tv);
            holder.arm_iv = (ImageView) convertView.findViewById(R.id.arm_iv);
            holder.state_iv = (TextView) convertView.findViewById(R.id.state_iv);
            holder.locatin_iv = (ImageView) convertView.findViewById(R.id.locatin_iv);
            holder.root = (LinearLayout) convertView.findViewById(R.id.root_ll);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        ObjectAnimationUtils.showScaleXScalexY(holder.root);

        final GetMyCarFleetResponse.DataValueBean.MCarInfoListBean info = list.get(position);

        if (info != null) {
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.myOnClickItem(position, info);

                    }
                }
            });
            holder.carCodeTv.setText(info.getCarCode());
            holder.orgNameTv.setText(info.getOrg_Name());
            holder.locationTv.setText(info.getCHS_Address());
            if (info.getCarStatus_Oid() != null) {
                holder.arm_iv.setVisibility(View.INVISIBLE);
                switch (Integer.parseInt(info.getCarStatus_Oid())) {
                    case 1:
                        holder.state_iv.setText("空闲中");
                        holder.locatin_iv.setImageResource(R.drawable.location_red);
                        holder.state_iv.setBackgroundResource(R.drawable.shape_fleet_ting);
                        break;
                    case 2:
                        holder.state_iv.setText("已计划");
                        holder.state_iv.setBackgroundResource(R.drawable.shape_fleet_jing);
                        holder.locatin_iv.setImageResource(R.drawable.location_blue);
                        break;
                    case 3:
                    case 0:
                        holder.state_iv.setText("在途中");
                        holder.state_iv.setBackgroundResource(R.drawable.shape_fleet_sudu);
                        holder.locatin_iv.setImageResource(R.drawable.location_green1);
                        break;
                    case 4:
                        holder.state_iv.setText("全部");
                        holder.locatin_iv.setImageResource(R.drawable.location_grey);
                        holder.state_iv.setBackgroundResource(R.drawable.shape_fleet_ting);
                        break;
                    default:
                        holder.state_iv.setText("空闲中");
                        holder.locatin_iv.setImageResource(R.drawable.location_red);
                        holder.state_iv.setBackgroundResource(R.drawable.shape_fleet_ting);
                        break;
                }

            }

        }
        return convertView;
    }


    private class ViewHolder {

        TextView carCodeTv;
        TextView locationTv;
        LinearLayout locationLl;
        TextView orgNameTv;
        LinearLayout jigouLl;
        LinearLayout root;
        ImageView arm_iv;
        TextView state_iv;
        ImageView locatin_iv;
    }
}
