package com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.litepal.CarInfo;
import com.cvnavi.logistics.i51ehang.app.callback.recycleview.OnItemClickLitener;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:04
*描述：Gps界面中Carinfo
************************************************************************************/


public class GpsCarHistoryRecycleAdapter extends RecyclerView.Adapter<GpsCarHistoryRecycleAdapter.MyViewHolder> {


    private List<CarInfo> list;
    private Context context;

    public GpsCarHistoryRecycleAdapter(Context context, List<CarInfo> list) {
        this.context = context;
        this.list = list;

    }

    public void setList(List<CarInfo> list){
        this.list = list;
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public GpsCarHistoryRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GpsCarHistoryRecycleAdapter.MyViewHolder viewHolder = new GpsCarHistoryRecycleAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recycle_gps, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GpsCarHistoryRecycleAdapter.MyViewHolder holder, int position) {

        //设置车牌号
        holder.textView.setText(list.get(position).getCarCode());
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
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public MyViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.carCode);
        }
    }


}
