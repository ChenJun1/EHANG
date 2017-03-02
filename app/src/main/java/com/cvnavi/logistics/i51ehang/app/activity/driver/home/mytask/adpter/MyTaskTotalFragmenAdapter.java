package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 版权所有势航网络
 * Created by ${ChenJ} on 2016/7/25.
 */
public class MyTaskTotalFragmenAdapter extends BaseAdapter {
    private ArrayList<TaskBean> list;
    private LayoutInflater mInflater;
    private MyTaskTotalListener listener;

    public MyTaskTotalFragmenAdapter(ArrayList<TaskBean> dataList, Context context,MyTaskTotalListener listener){
            super();
        this.list=dataList;
        this.mInflater=LayoutInflater.from(context);
        this.listener=listener;
    }

    @Override
    public int getCount() {
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

        final TaskBean taskBean=list.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_my_task_lv_item, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.taskLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(taskBean);
            }
        });


        if (taskBean != null) {
            if (!TextUtils.isEmpty(taskBean.Letter_Type_Oid) && taskBean.Letter_Type_Oid.equals("A")) {
                viewHolder.zhengcheBtn.setVisibility(View.GONE);
                viewHolder.peizaiBtn.setVisibility(View.VISIBLE);
            } else {
                viewHolder.zhengcheBtn.setVisibility(View.VISIBLE);
                viewHolder.peizaiBtn.setVisibility(View.GONE);
            }
//            SetViewValueUtil.setTextViewValue(Letter_Oid_tv,taskBean.Letter_Oid);
            SetViewValueUtil.setTextViewValue(viewHolder.AllReceivableAccountTv, taskBean.AllReceivableAccount);
            SetViewValueUtil.setTextViewValue(viewHolder.TicketCountTv, taskBean.Ticket_Count, "票");
            SetViewValueUtil.setTextViewValue(viewHolder.GoodsNumTv, taskBean.Goods_Num, "件");
//            SetViewValueUtil.setTextViewValue(Goods_Weight_tv,taskBean.Goods_Weight,"kg");
//            SetViewValueUtil.setTextViewValue(Bulk_Weight_tv,taskBean.Bulk_Weight,"m³");
            SetViewValueUtil.setTextViewValue(viewHolder.TicketStatusTv, taskBean.TicketStatus);
            SetViewValueUtil.setTextViewValue(viewHolder.LetterDateTv, taskBean.Leave_DateTime);
//            SetViewValueUtil.setTextViewValue(viewHolder.LetterDateTv, taskBean.Letter_Date);
            SetViewValueUtil.setTextViewValue(viewHolder.CarCodeTv,taskBean.CarCode);
            SetViewValueUtil.setTextViewValue(viewHolder.ShuttleFeeTv,taskBean.Shuttle_Fee);
            if(TextUtils.isEmpty(taskBean.BoxCarCode)){
                SetViewValueUtil.setTextViewValue(viewHolder.BoxCarCodeTv,"无");
            }else{

                SetViewValueUtil.setTextViewValue(viewHolder.BoxCarCodeTv,taskBean.BoxCarCode);
            }
        }
        return convertView;
    }

    public interface MyTaskTotalListener{
        void itemClick(TaskBean bean);
    }

    static class ViewHolder {
        @BindView(R.id.CarCode_tv)
        TextView CarCodeTv;
        @BindView(R.id.zhengche_btn)
        Button zhengcheBtn;
        @BindView(R.id.peizai_btn)
        Button peizaiBtn;
        @BindView(R.id.BoxCarCode_tv)
        TextView BoxCarCodeTv;
        @BindView(R.id.Ticket_Count_tv)
        TextView TicketCountTv;
        @BindView(R.id.Goods_Num_tv)
        TextView GoodsNumTv;
        @BindView(R.id.Shuttle_Fee_tv)
        TextView ShuttleFeeTv;
        @BindView(R.id.Letter_Date_tv)
        TextView LetterDateTv;
        @BindView(R.id.TicketStatus_tv)
        TextView TicketStatusTv;
        @BindView(R.id.AllReceivableAccount_tv)
        TextView AllReceivableAccountTv;
        @BindView(R.id.task_ll)
        LinearLayout taskLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
