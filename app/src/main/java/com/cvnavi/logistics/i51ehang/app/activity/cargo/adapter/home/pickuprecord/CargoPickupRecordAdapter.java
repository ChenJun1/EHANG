package com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.pickuprecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.GetTakeManifestsDataValue;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.trans.OnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.List;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主 取货记录
 */

public class CargoPickupRecordAdapter extends BaseAdapter{

    private Context context;
    private List<GetTakeManifestsDataValue> mList;
    private OnClickItemListener listener;

    public CargoPickupRecordAdapter(Context context,List<GetTakeManifestsDataValue> mList,OnClickItemListener listener) {
        this.context = context;
        this.mList = mList;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GetTakeManifestsDataValue bean = mList.get(position);
       if (convertView == null){
           convertView = LayoutInflater.from(context).inflate(R.layout.activity_cargo_pickup_record_item,null);
       }

        LinearLayout LLlayout = ViewHolder.get(convertView,R.id.cargo_pickup_item_LLlayout);
        TextView Ticket_No_text = ViewHolder.get(convertView,R.id.Ticket_No_text);
        TextView Goods_Breed_text = ViewHolder.get(convertView,R.id.Goods_Breed_text);
        TextView Goods_Casing_text = ViewHolder.get(convertView,R.id.Goods_Casing_text);
        TextView Goods_Num_text = ViewHolder.get(convertView,R.id.Goods_Num_text);
        TextView SendMan_Address_txet = ViewHolder.get(convertView,R.id.SendMan_Address_txet);
        TextView Operate_DateTime_text = ViewHolder.get(convertView,R.id.Operate_DateTime_text);

        LLlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
//        ObjectAnimationUtils.showScaleXScalexY(LLlayout);
        SetViewValueUtil.setTextViewValue(Ticket_No_text,bean.Ticket_No);
        SetViewValueUtil.setTextViewValue(Goods_Breed_text,bean.Goods_Breed);
        SetViewValueUtil.setTextViewValue(Goods_Casing_text,bean.Goods_Casing);
        SetViewValueUtil.setTextViewValue(Goods_Num_text,String.format(Utils.getResourcesString(R.string.my_order_jian),bean.Goods_Num)
                +"/"+String.format(Utils.getResourcesString(R.string.my_order_kg),bean.Goods_Weight)
                +"/"+String.format(Utils.getResourcesString(R.string.my_order_m),bean.Bulk_Weight)
        );
        SetViewValueUtil.setTextViewValue(SendMan_Address_txet,bean.SendMan_Address);
        SetViewValueUtil.setTextViewValue(Operate_DateTime_text,bean.Operate_DateTime);

        return convertView;
    }
}
