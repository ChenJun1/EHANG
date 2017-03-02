package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskDetailedOrderListBean;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskCarryDetailedAdpter extends BaseAdapter {
    private List<TaskDetailedOrderListBean> list;
    private LayoutInflater mInflater;

    public MyTaskCarryDetailedAdpter(List<TaskDetailedOrderListBean> dataList, Context context) {
        super();
        this.list = dataList;
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public TaskDetailedOrderListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskDetailedOrderListBean bean = list.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_carry_detailed_list_item, null);
            viewHolder=  new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SetViewValueUtil.setTextViewValue(viewHolder.waybillTv,bean.Ticket_No);
        SetViewValueUtil.setTextViewValue(viewHolder.sendCityTv,bean.sendCity);
        SetViewValueUtil.setTextViewValue(viewHolder.arriveCityTv,bean.arriveCity);
        SetViewValueUtil.setTextViewValue(viewHolder.GoodsBreedTv,bean.Goods_Breed);
        SetViewValueUtil.setTextViewValue(viewHolder.GoodsNumTv,bean.Goods_Num,"件");
        SetViewValueUtil.setTextViewValue(viewHolder.GoodsWeightTv, ContextUtil.getDouble(bean.Goods_Weight),"kg");
        SetViewValueUtil.setTextViewValue(viewHolder.BulkWeightTv, ContextUtil.getDouble(bean.Bulk_Weight),"m³");
        SetViewValueUtil.setTextViewValue(viewHolder.DelegateDateTimeTv,bean.Delegate_DateTime);
        if(!TextUtils.isEmpty(bean.fullStatus)&&bean.fullStatus.equals("是")){
            SetViewValueUtil.setTextViewValue(viewHolder.fullStatus_tv,"拆");
        }else{
            viewHolder.fullStatus_tv.setVisibility(View.GONE);
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.waybill_tv)
        TextView waybillTv;
        @BindView(R.id.sendCity_tv)
        TextView sendCityTv;
        @BindView(R.id.arriveCity_tv)
        TextView arriveCityTv;
        @BindView(R.id.Goods_Breed_tv)
        TextView GoodsBreedTv;
        @BindView(R.id.Goods_Num_tv)
        TextView GoodsNumTv;
        @BindView(R.id.Goods_Weight_tv)
        TextView GoodsWeightTv;
        @BindView(R.id.Bulk_Weight_tv)
        TextView BulkWeightTv;
        @BindView(R.id.Delegate_DateTime_tv)
        TextView DelegateDateTimeTv;
        @BindView(R.id.fullStatus_tv)
        TextView fullStatus_tv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
