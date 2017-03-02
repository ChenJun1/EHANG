package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.DrSendCarDetailActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.DriverCarExceptionUpLoadActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MySendCar;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;
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
 * Depict: 进行中发车
 */
public class DrRunTimeAdpter extends BaseAdapter {
    private List<MySendCar> list;
    private LayoutInflater mInflater;
    private Context mContext;

    public DrRunTimeAdpter(List<MySendCar> dataList, Context context) {
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
    public MySendCar getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setData(List<MySendCar> dataList) {
        this.list = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MySendCar car = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dr_send_car_item2, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (car != null) {
            ObjectAnimationUtils.showScaleXScalexY(viewHolder.mItemRl);
            SetViewValueUtil.setTextViewValue(viewHolder.mAllTicketNolTv, car.getCarCode());
            SetViewValueUtil.setTextViewStr(viewHolder.mGoodsBreedTv, car.getLine_Name());
            SetViewValueUtil.setTextViewStr(viewHolder.mGuaCarTv, car.getBoxCarCode());
            SetViewValueUtil.setTextViewValue(viewHolder.mGoodsNumTv, car.getGoods_Num(), "件");
            SetViewValueUtil.setTextViewValue(viewHolder.mTicketCountTv, car.getTicket_Count(), "票");
            SetViewValueUtil.setTextViewValue(viewHolder.mReceiveManTelTv, car.getLeave_DateTime());
            SetViewValueUtil.setTextViewValue(viewHolder.mShuttleFeeTv,car.getShuttle_Fee() + "元");
            if(!TextUtils.isEmpty(car.getTraffic_Mode())&&car.getTraffic_Mode().equals("整")){
                viewHolder.mZhengTv.setVisibility(View.VISIBLE);
                viewHolder.mPeiTv.setVisibility(View.GONE);
            }else{
                viewHolder.mZhengTv.setVisibility(View.GONE);
                viewHolder.mPeiTv.setVisibility(View.VISIBLE);
            }
            viewHolder.mOperationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DriverCarExceptionUpLoadActivity.start(mContext, new TaskBean(car.getCarCode(), car.getCarCode_Key(), car.getDriver_Key(), car.getLPSCarCode_Key(), car.getDriver_Key(), car.getLetter_Oid()));
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrSendCarDetailActivity.start(mContext, car.getLetter_Oid(),null);
                }
            });
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.zheng_tv)
        TextView mZhengTv;
        @BindView(R.id.pei_tv)
        TextView mPeiTv;
        @BindView(R.id.All_Ticket_Nol_tv)
        TextView mAllTicketNolTv;
        @BindView(R.id.gua_car_tv)
        TextView mGuaCarTv;
        @BindView(R.id.Goods_Breed_tv)
        TextView mGoodsBreedTv;
        @BindView(R.id.Ticket_Count_tv)
        TextView mTicketCountTv;
        @BindView(R.id.Goods_Num_tv)
        TextView mGoodsNumTv;
        @BindView(R.id.ReceiveMan_Tel_tv)
        TextView mReceiveManTelTv;
        @BindView(R.id.operation_btn)
        Button mOperationBtn;
        @BindView(R.id.item_rl)
        RelativeLayout mItemRl;
        @BindView(R.id.Shuttle_Fee_tv)
        TextView mShuttleFeeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
