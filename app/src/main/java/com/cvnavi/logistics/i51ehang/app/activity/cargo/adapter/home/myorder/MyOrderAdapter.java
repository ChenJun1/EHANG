package com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.myorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrder;
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
 * Depict: 货主 我的货单
 */
public class MyOrderAdapter extends BaseAdapter {

    private Context context;
//    private ItemView itemView;

    private List<mOrder> list;
    private OnClickItemListener listener;

    public MyOrderAdapter(Context context, List<mOrder> list, OnClickItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        } else {
            return list.size();
        }
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_my_order_list_view_item, null);
        }
        TextView consignee_tv = ViewHolder.get(convertView,R.id.consignee_tv);
        TextView begin_tv = ViewHolder.get(convertView,R.id.begin_tv);
        TextView receive_tv = ViewHolder.get(convertView,R.id.receive_tv);
        TextView num_tv = ViewHolder.get(convertView,R.id.num_tv);
        TextView pinming_tv = ViewHolder.get(convertView,R.id.pinming_tv);
        TextView weight_tv = ViewHolder.get(convertView,R.id.weight_tv);
        TextView space_tv = ViewHolder.get(convertView,R.id.space_tv);
        TextView reconmend_tv = ViewHolder.get(convertView,R.id.reconmend_tv);
        TextView sign_state_tv = ViewHolder.get(convertView,R.id.sign_state_tv);
        TextView count_tv = ViewHolder.get(convertView,R.id.count_tv);
        LinearLayout root_ll = ViewHolder.get(convertView,R.id.root_ll);

        mOrder info = list.get(position);

        if (info != null) {
            root_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(position);
                    }

                }
            });
//            ObjectAnimationUtils.showScaleXScalexY(root_ll);
            SetViewValueUtil.setTextViewValue(consignee_tv,info.Ticket_No);
            SetViewValueUtil.setTextViewValue(count_tv,info.Total_Fee);
            SetViewValueUtil.setTextViewValue(begin_tv,info.SendStation);
            SetViewValueUtil.setTextViewValue(receive_tv,info.ArrStation);
            SetViewValueUtil.setTextViewValue(pinming_tv,info.Goods_Breed);
            SetViewValueUtil.setTextViewValue(num_tv,String.format(Utils.getResourcesString(R.string.my_order_jian), info.Goods_Num));
            SetViewValueUtil.setTextViewValue(weight_tv,String.format(Utils.getResourcesString(R.string.my_order_kg), info.Goods_Weight));
            SetViewValueUtil.setTextViewValue(space_tv,String.format(Utils.getResourcesString(R.string.my_order_m), info.Bulk_Weight));
            SetViewValueUtil.setTextViewValue(reconmend_tv,info.Deliver_DateTime);
            SetViewValueUtil.setTextViewValue(sign_state_tv,info.Deliver_Status);

        }

        return convertView;
    }




}
