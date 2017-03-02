package com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.callback.recycleview.OnItemClickLitener;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.ScreenSupport;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:05
*描述：员工首页recycleview的适配器
************************************************************************************/


public class MyEmployeeRecycleAdapter extends RecyclerView.Adapter<MyEmployeeRecycleAdapter.MyViewHolder> {
    private static final int WITH_PADDING = 20;//单位dp,控件距离屏幕左右的间距
    private static final int ITEM_PADDING = 4;//单位dp,item与item距离
    private Context context;
    private List<mMainService> mDatas;


    public MyEmployeeRecycleAdapter(Context context, List<mMainService> data) {
        this.context = context;
        this.mDatas = data;
    }


    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


    @Override
    public MyEmployeeRecycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyEmployeeRecycleAdapter.MyViewHolder myViewHolder = new MyEmployeeRecycleAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test, parent, false));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyEmployeeRecycleAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mDatas.get(position).ServiceName);
        //根据返回数据的数量来确定布局的样式
        switch (mDatas.size()) {
            case 1:
                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ScreenSupport.SCREEN_WIDTH / 2, ScreenSupport.SCREEN_WIDTH / 2);
                params1.gravity = Gravity.CENTER;
                holder.out_rl.setLayoutParams(params1);
                break;
            case 2:
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(((ScreenSupport.SCREEN_WIDTH - Utils.dip2px(context, WITH_PADDING) - 2 * Utils.dip2px(context, ITEM_PADDING)) / 2), LinearLayout.LayoutParams.MATCH_PARENT);
                params2.gravity = Gravity.CENTER;
                holder.out_rl.setLayoutParams(params2);
                break;
            case 3:
                LinearLayout.LayoutParams params3;
                if (position == 0) {
                    //第一行布局 宽度是全屏显示，
                    params3 = new LinearLayout.LayoutParams((ScreenSupport.SCREEN_WIDTH - 2 * Utils.dip2px(context, WITH_PADDING)), LinearLayout.LayoutParams.MATCH_PARENT);
                } else {
                    //第二行布局是一半一半
                    params3 = new LinearLayout.LayoutParams(((ScreenSupport.SCREEN_WIDTH - 2 * Utils.dip2px(context, WITH_PADDING)) - Utils.dip2px(context, ITEM_PADDING)) / 2, LinearLayout.LayoutParams.MATCH_PARENT);
                }

                params3.gravity = Gravity.CENTER;
                holder.out_rl.setLayoutParams(params3);
                break;
            case 4:
                LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(((ScreenSupport.SCREEN_WIDTH - 2 * Utils.dip2px(context, WITH_PADDING) - 2 * Utils.dip2px(context, ITEM_PADDING)) / 2), LinearLayout.LayoutParams.MATCH_PARENT);
                params4.gravity = Gravity.CENTER;
                holder.out_rl.setLayoutParams(params4);
                break;
            case 5:
                //第一行三条数据 第二行是两条数据
                LinearLayout.LayoutParams params5;
                if (position == 3) {
                    params5 = new LinearLayout.LayoutParams(((ScreenSupport.SCREEN_WIDTH - 2 * Utils.dip2px(context, WITH_PADDING) - 2 * Utils.dip2px(context, ITEM_PADDING)) / 3) * 2 + Utils.dip2px(context, ITEM_PADDING), LinearLayout.LayoutParams.MATCH_PARENT);
                } else {
                    params5 = new LinearLayout.LayoutParams(((ScreenSupport.SCREEN_WIDTH - 2 * Utils.dip2px(context, WITH_PADDING) - 2 * Utils.dip2px(context, ITEM_PADDING)) / 3), LinearLayout.LayoutParams.MATCH_PARENT);
                }
                params5.gravity = Gravity.CENTER;
                holder.out_rl.setLayoutParams(params5);
                break;
            case 6:
                LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(((ScreenSupport.SCREEN_WIDTH - 2 * Utils.dip2px(context, WITH_PADDING) - 2 * Utils.dip2px(context, ITEM_PADDING)) / 3), LinearLayout.LayoutParams.MATCH_PARENT);
                params6.gravity = Gravity.CENTER;
                holder.out_rl.setLayoutParams(params6);
                break;
        }
        mMainService info = mDatas.get(position);
        if (info != null) {
            switch (Integer.parseInt(info.ServiceID)) {
                case Constants.EMPLOYEE_SERVICE_ID_GPS:
                    holder.imageView.setImageResource(R.drawable.home_look);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_GPS_TRACK:
                    holder.imageView.setImageResource(R.drawable.home_gps);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_TRANSTION:
                    holder.imageView.setImageResource(R.drawable.home_trans);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_TONGJI:
                    holder.imageView.setImageResource(R.drawable.home_tj);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_CAR_FLEET:
                    holder.imageView.setImageResource(R.drawable.home_cars);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_SETTING:
                    holder.imageView.setImageResource(R.drawable.home_setting);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_MONITOR:
                    holder.imageView.setImageResource(R.drawable.car_monitor);
                    break;
                case Constants.EMPLOYEE_SERVICE_ID_KU_FANG:
                    holder.imageView.setImageResource(R.drawable.icon_kufang);
                    break;
                default:
                    holder.imageView.setImageResource(R.drawable.home_look);
                    break;
            }
        }


        switch (position) {
            case 0:
                holder.out_rl.setBackgroundColor(Utils.getResourcesColor(R.color.colr_home_menu_2));
                break;
            case 1:
                holder.out_rl.setBackgroundColor(Utils.getResourcesColor(R.color.colr_home_menu_1));
                break;
            case 2:
                holder.out_rl.setBackgroundColor(Utils.getResourcesColor(R.color.colr_home_menu_1));
                break;
            case 3:
                holder.out_rl.setBackgroundColor(Utils.getResourcesColor(R.color.colr_home_menu_2));
                break;
            case 4:
                holder.out_rl.setBackgroundColor(Utils.getResourcesColor(R.color.colr_home_menu_2));
                break;
            case 5:
                holder.out_rl.setBackgroundColor(Utils.getResourcesColor(R.color.colr_home_menu_1));
                break;
        }


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
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        LinearLayout root;
        RelativeLayout out_rl;


        public MyViewHolder(View view) {
            super(view);

            textView = (TextView) view.findViewById(R.id.id_num);
            imageView = (ImageView) view.findViewById(R.id.iv_icon);
            root = (LinearLayout) view.findViewById(R.id.item);
            out_rl = (RelativeLayout) view.findViewById(R.id.out_rl);
        }
    }


}
