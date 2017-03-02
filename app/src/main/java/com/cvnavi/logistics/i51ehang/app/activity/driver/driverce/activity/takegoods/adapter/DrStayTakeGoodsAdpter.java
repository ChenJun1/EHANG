package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.Dr_ConfirmPickUpGoodsActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.DriverOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.queryorder.DriverHomeOrderDeatilActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetTakeGoodsResponseBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskDetailedOrderListBean;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.ObjectAnimationUtils;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 未完成 提货
 */
public class DrStayTakeGoodsAdpter extends BaseAdapter {
    private List<GetTakeGoodsResponseBean.TakeBean> list;
    private LayoutInflater mInflater;
    private Context mContext;

    public DrStayTakeGoodsAdpter(List<GetTakeGoodsResponseBean.TakeBean> dataList, Context context) {
        super();
        this.list = dataList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public GetTakeGoodsResponseBean.TakeBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final GetTakeGoodsResponseBean.TakeBean bean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dr_take_goods_item1, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (bean != null) {
            ObjectAnimationUtils.showScaleXScalexY(viewHolder.mItemRl);

            SetViewValueUtil.setTextViewValue(viewHolder.mAllTicketNolTv, bean.getTicket_No());
            SetViewValueUtil.setTextViewValue(viewHolder.mGoodsBreedTv, bean.getGoods_Breed());
            SetViewValueUtil.setTextViewValue(viewHolder.mGoodsNumTv, bean.getGoods_Num(), "件");
            SetViewValueUtil.setTextViewValue(viewHolder.mGoodsWeightTv, ContextUtil.getDouble(bean.getGoods_Weight()), "kg");
            SetViewValueUtil.setTextViewValue(viewHolder.mBulkWeightTv, ContextUtil.getDouble(bean.getBulk_Weight()), "m³");
            SetViewValueUtil.setTextViewValue(viewHolder.mReceiveManTelTv, bean.getOperate_DateTime());
            SetViewValueUtil.setTextViewValue(viewHolder.mPackTv, bean.getGoods_Casing());
            SetViewValueUtil.setTextViewValue(viewHolder.mSendCityTv, bean.getSendStation());
            SetViewValueUtil.setTextViewValue(viewHolder.mArriveCityTv, bean.getArrStation());

            viewHolder.mOperationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDetailedOrderListBean bean1 = new TaskDetailedOrderListBean();
                    bean1.All_Ticket_No = bean.getAll_Ticket_No();
                    bean1.Bulk_Weight = bean.getBulk_Weight();
                    bean1.Goods_Num = bean.getGoods_Num();
                    bean1.Goods_Weight = bean.getGoods_Weight();
                    bean1.Org_Code = bean.getOrg_Code();
                    Dr_ConfirmPickUpGoodsActivity.start(mContext, bean1,
                            new TaskBean(bean.getLetter_Oid()));
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskDetailedOrderListBean bean2 = new TaskDetailedOrderListBean();
                    bean2.All_Ticket_No = bean.getAll_Ticket_No();
                    bean2.Ticket_No = bean.getAll_Ticket_No();
                    bean2.Bulk_Weight = bean.getBulk_Weight();
                    bean2.Goods_Num = bean.getGoods_Num();
                    bean2.Goods_Weight = bean.getGoods_Weight();
                    bean2.Org_Code = bean.getOrg_Code();
                    DriverOrderDeatilActivity.startActivity2(mContext,
                            bean2,
                            bean.getAll_Ticket_No(),
                            new TaskBean(bean.getLetter_Oid(), bean.getGoods_Num(), bean.getGoods_Weight(), bean.getBulk_Weight()),
                            DriverHomeOrderDeatilActivity.INTENT_DATA_SIGN_ORDER);
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.All_Ticket_Nol_tv)
        TextView mAllTicketNolTv;
        @BindView(R.id.sendCity_tv)
        TextView mSendCityTv;
        @BindView(R.id.arriveCity_tv)
        TextView mArriveCityTv;
        @BindView(R.id.Goods_Breed_tv)
        TextView mGoodsBreedTv;
        @BindView(R.id.pack_tv)
        TextView mPackTv;
        @BindView(R.id.Goods_Num_tv)
        TextView mGoodsNumTv;
        @BindView(R.id.Goods_Weight_tv)
        TextView mGoodsWeightTv;
        @BindView(R.id.Bulk_Weight_tv)
        TextView mBulkWeightTv;
        @BindView(R.id.ReceiveMan_Tel_tv)
        TextView mReceiveManTelTv;
        @BindView(R.id.operation_btn)
        Button mOperationBtn;
        @BindView(R.id.item_rl)
        LinearLayout mItemRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
