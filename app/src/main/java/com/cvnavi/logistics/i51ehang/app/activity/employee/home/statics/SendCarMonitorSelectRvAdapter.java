package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarSelectCarResponse;
import com.cvnavi.logistics.i51ehang.app.callback.recycleview.OnItemClickLitener;

import java.util.List;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午4:02
 * 描述：发车监控选择车辆
 ************************************************************************************/


public class SendCarMonitorSelectRvAdapter extends RecyclerView.Adapter<SendCarMonitorSelectRvAdapter.MyViewHolder> {

    private List<SendCarSelectCarResponse.DataValueBean> list;
    private Context context;


    public SendCarMonitorSelectRvAdapter(Context context, List<SendCarSelectCarResponse.DataValueBean> list) {
        this.context = context;
        this.list = list;
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new SendCarMonitorSelectRvAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_send_car_select_rv, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        SendCarSelectCarResponse.DataValueBean info = list.get(position);

        //手动设置是否是选中状态
        if (info.isSelect()) {
            holder.textView.setBackgroundResource(R.drawable.shape_send_car_select_all);
            holder.textView.setTextColor(0xff788fb5);
        } else {
            holder.textView.setBackgroundResource(R.drawable.shape_send_car_select_line);
            holder.textView.setTextColor(0xffffffff);
        }

        holder.textView.setText(info.getCarCode());
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.carCode);
        }


    }

}
