package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation.PlanDetailActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MySendCar;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarShift;
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
 * Depict: 未完成发车
 */
public class DrNoStartAdpter extends BaseAdapter {
    private List<MySendCar> list;
    private LayoutInflater mInflater;
    private Context mContext;

    public DrNoStartAdpter(List<MySendCar> dataList, Context context) {
        super();
        this.list = dataList;
        this.mInflater = LayoutInflater.from(context);
        this.mContext=context;
    }


    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public void setData(List<MySendCar> dataList){
        this.list=dataList;
    }

    @Override
    public MySendCar getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MySendCar car=getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dr_send_car_item1, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(car!=null){
            ObjectAnimationUtils.showScaleXScalexY(viewHolder.mItemRl);
            SetViewValueUtil.setTextViewValue(viewHolder.mAllTicketNolTv,car.getCarCode());
            SetViewValueUtil.setTextViewStr(viewHolder.mGuaCarTv,car.getBoxCarCode());
//            SetViewValueUtil.setTextViewValue(viewHolder.mLineTypeTv,car.getLine_Type());
            SetViewValueUtil.setTextViewValue(viewHolder.mLineNameTv,car.getLine_Name());
            SetViewValueUtil.setTextViewValue(viewHolder.mReceiveManTelTv,car.getCarCode_Date());

            if(!TextUtils.isEmpty(car.getTraffic_Mode())&&car.getTraffic_Mode().equals("整")){
                viewHolder.mZhengTv.setVisibility(View.VISIBLE);
                viewHolder.mPeiTv.setVisibility(View.GONE);
            }else{
                viewHolder.mZhengTv.setVisibility(View.GONE);
                viewHolder.mPeiTv.setVisibility(View.VISIBLE);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlanDetailActivity.startActivity(mContext,new mCarShift(car.getCarCode(),car.getLine_Name(),
                            car.getBoxCarCode(),car.getCarCode_Date(),car.getMainDriver(),
                            car.getMainDriver_Tel(),car.getSecondDriver(),car.getSecondDriver_Tel(),
                            car.getShuttle_Oid(),car.getShuttle_No(),"0"));
                }
            });
        }



        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.All_Ticket_Nol_tv)
        TextView mAllTicketNolTv;
        @BindView(R.id.gua_car_tv)
        TextView mGuaCarTv;
        @BindView(R.id.Line_Type_tv)
        TextView mLineTypeTv;
        @BindView(R.id.Line_Name_tv)
        TextView mLineNameTv;
        @BindView(R.id.ReceiveMan_Tel_tv)
        TextView mReceiveManTelTv;
        @BindView(R.id.item_rl)
        RelativeLayout mItemRl;
        @BindView(R.id.zheng_tv)
        TextView mZhengTv;
        @BindView(R.id.pei_tv)
        TextView mPeiTv;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
