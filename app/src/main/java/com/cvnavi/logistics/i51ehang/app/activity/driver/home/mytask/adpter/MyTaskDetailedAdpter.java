package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskDetailedOrderListBean;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskDetailedAdpter extends BaseAdapter {
    private List<TaskDetailedOrderListBean> list;
    private LayoutInflater mInflater;
    private MyTaskDetailedListener listener;

    public MyTaskDetailedAdpter(List<TaskDetailedOrderListBean> dataList, Context context, MyTaskDetailedListener listener) {
        super();
        this.list = dataList;
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
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
        final TaskDetailedOrderListBean bean = list.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_my_task_detailed_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (!TextUtils.isEmpty(bean.ticketType) && bean.ticketType.equals("提货")) {
            viewHolder.tihuo_tv.setVisibility(View.VISIBLE);
            viewHolder.stateTv.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(bean.complete_Status) && bean.complete_Status.equals("1")) {
                viewHolder.operationBtn.setOnClickListener(null);
                viewHolder.operationBtn.setText("已提货");
                viewHolder.operationBtn.setBackgroundResource(R.drawable.shape_rounded_btn_5b5b5b);
            } else {
                viewHolder.operationBtn.setText("确认提货");
                viewHolder.operationBtn.setBackgroundResource(R.drawable.shape_rounded_btn_2fc561);
                viewHolder.operationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.itemClick(bean);
                    }
                });
            }
        } else {
            viewHolder.tihuo_tv.setVisibility(View.GONE);
            viewHolder.stateTv.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(bean.complete_Status) && bean.complete_Status.equals("1")) {
                viewHolder.operationBtn.setOnClickListener(null);
                viewHolder.operationBtn.setText("已签收");
                viewHolder.operationBtn.setBackgroundResource(R.drawable.shape_rounded_btn_5b5b5b);
            } else {

                viewHolder.operationBtn.setText("确认签收");
                viewHolder.operationBtn.setBackgroundResource(R.drawable.shape_rounded_btn_2fc561);
                viewHolder.operationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.itemClick(bean);
                    }
                });
            }
        }

        if (Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CONFIRM_SIGN+"") || Utils.checkOperate(Constants.EMPLOYEE_SERVICE_ID_CONFIRM_TIHUO+"")) {
            viewHolder.operationBtn.setVisibility(View.VISIBLE);
        } else {
            viewHolder.operationBtn.setVisibility(View.GONE);
        }
        viewHolder.itemRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.toOrderdetai(bean);
            }
        });
        SetViewValueUtil.setTextViewValue(viewHolder.AllTicketNolTv, bean.Ticket_No);
        SetViewValueUtil.setTextViewValue(viewHolder.ReceiveManTelTv, bean.ReceiveMan_Tel);
        SetViewValueUtil.setTextViewValue(viewHolder.GoodsBreedTv, bean.Goods_Breed);
        SetViewValueUtil.setTextViewValue(viewHolder.GoodsNumTv, bean.Goods_Num, "件");
        SetViewValueUtil.setTextViewValue(viewHolder.GoodsWeightTv,  ContextUtil.getDouble(bean.Goods_Weight), "kg");
        SetViewValueUtil.setTextViewValue(viewHolder.BulkWeightTv,  ContextUtil.getDouble(bean.Bulk_Weight), "m³");
        SetViewValueUtil.setTextViewValue(viewHolder.ReceiveManAddressTv, bean.ReceiveMan_Address);


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.All_Ticket_Nol_tv)
        TextView AllTicketNolTv;
        @BindView(R.id.state_tv)
        TextView stateTv;
        @BindView(R.id.tihuo_tv)
        TextView tihuo_tv;
        @BindView(R.id.ReceiveMan_Tel_tv)
        TextView ReceiveManTelTv;
        @BindView(R.id.Goods_Breed_tv)
        TextView GoodsBreedTv;
        @BindView(R.id.Goods_Num_tv)
        TextView GoodsNumTv;
        @BindView(R.id.Goods_Weight_tv)
        TextView GoodsWeightTv;
        @BindView(R.id.Bulk_Weight_tv)
        TextView BulkWeightTv;
        @BindView(R.id.ReceiveMan_Address_tv)
        TextView ReceiveManAddressTv;
        @BindView(R.id.operation_btn)
        Button operationBtn;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface MyTaskDetailedListener {
        void itemClick(TaskDetailedOrderListBean bean);

        void toOrderdetai(TaskDetailedOrderListBean bean);
    }
}
