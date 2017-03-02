package com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.unfinishedorder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.myorder.MyOrderDetailAcitivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mOrder;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.List;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主 运输中 货单
 */

public class InTransitAdapter extends BaseAdapter{

    private List<mOrder> mList;
    private Context context;


    public InTransitAdapter(Context context,List<mOrder> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.unfinishedorder_item,null);
        }
        TextView consignee_tv = ViewHolder.get(convertView,R.id.consignee_tv);//货单号
        TextView begin_tv = ViewHolder.get(convertView,R.id.begin_tv);//始
        TextView receive_tv = ViewHolder.get(convertView,R.id.receive_tv);//终
        TextView count_tv = ViewHolder.get(convertView,R.id.count_tv);//运费
        TextView pinming_tv = ViewHolder.get(convertView,R.id.pinming_tv);//品名
        TextView num_tv = ViewHolder.get(convertView,R.id.num_tv);//件
        TextView weight_tv = ViewHolder.get(convertView,R.id.weight_tv);//kg
        TextView space_tv = ViewHolder.get(convertView,R.id.space_tv);//m
        TextView reconmend_tv = ViewHolder.get(convertView,R.id.reconmend_tv);//发货时间
        LinearLayout root_ll  = ViewHolder.get(convertView,R.id.root_ll);

        final mOrder bean = mList.get(position);
        if (bean!=null){
            root_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MyOrderDetailAcitivity.class);
                    intent.putExtra(MyOrderDetailAcitivity.ORDER_ID, bean);
                    context.startActivity(intent);
                }
            });

//            ObjectAnimationUtils.showScaleXScalexY(root_ll);
            SetViewValueUtil.setTextViewValue(consignee_tv,bean.Ticket_No);
            SetViewValueUtil.setTextViewValue(begin_tv,bean.SendStation);
            SetViewValueUtil.setTextViewValue(receive_tv,bean.ArrStation);
            SetViewValueUtil.setTextViewValue(count_tv,bean.Total_Fee);
            SetViewValueUtil.setTextViewValue(pinming_tv,bean.Goods_Breed);
            SetViewValueUtil.setTextViewValue(num_tv, ContextUtil.getDouble(bean.Goods_Num), "件");
            SetViewValueUtil.setTextViewValue(weight_tv, ContextUtil.getDouble(bean.Goods_Weight), "kg");
            SetViewValueUtil.setTextViewValue(space_tv, ContextUtil.getDouble(bean.Bulk_Weight), "m³");
            SetViewValueUtil.setTextViewValue(reconmend_tv,bean.SendGoods_DateTime);

//            consignee_tv.setText(bean.Ticket_No);
//            begin_tv.setText(bean.SendStation);
//            receive_tv.setText(bean.ArrStation);
//            count_tv.setText(bean.Ticket_Fee);
//            pinming_tv.setText(bean.Goods_Breed);
//            num_tv.setText(String.format(Utils.getResourcesString(R.string.my_order_jian), bean.Goods_Num));
//            weight_tv.setText(String.format(Utils.getResourcesString(R.string.my_order_kg), bean.Goods_Weight));
//            space_tv.setText(String.format(Utils.getResourcesString(R.string.my_order_m), bean.Bulk_Weight));
//            reconmend_tv.setText(bean.SendGoods_DateTime);
        }

        return convertView;
    }
}
