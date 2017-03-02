package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetalarminfo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.myFleet.mAlarmBean;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午10:10
*描述：报警信息适配器
************************************************************************************/

public class MyFleetAlarmAdapter extends BaseAdapter {
    private List<mAlarmBean> list;
    private LayoutInflater inflater;

    public MyFleetAlarmAdapter(List<mAlarmBean> list, Context context) {
        super();
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public mAlarmBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mAlarmBean bean = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_give_an_alarm_info, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        /**
         * AD、AE、AF、AG、AH、AM、BN、EJ
         *超速报警、停车报警、到达报警、离开报警、线路偏离报警、温度报警
         *冷链传感器离线、离线报警
         *
         */
        if (!TextUtils.isEmpty(bean.Alarm_Type_Oid)) {
            switch (bean.Alarm_Type_Oid) {
                case "AD":
                    viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.warning_chao);
                    break;
                case "AE":
                    viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.warning_ting);
                    break;
                case "AF":
                    viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.warning_dao);
                    break;
                case "AG":
                case "AH":
                case "EJ":
                    viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.warning_li);
                    break;
                case "AM":
                    viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.warning_wen);
                    break;
                case "BN":
                    viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.warning_leng);
                    break;
                default:
                    viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.li);
                    break;
            }
        } else {
            viewHolder.AlarmTypeOidTv.setImageResource(R.drawable.li);
        }

        if (bean != null) {
            SetViewValueUtil.setTextViewValue(viewHolder.CarCodeTv, bean.CarCode);
            SetViewValueUtil.setTextViewValue(viewHolder.AlarmDateTimeTv, DateUtil.strOldFormat2NewFormat(bean.Alarm_DateTime, DateUtil.FORMAT_YMDHMS, DateUtil.FORMAT_MDHM));
            SetViewValueUtil.setTextViewValue(viewHolder.AlarmDescriptionTv, bean.CarCode + ":" + bean.Alarm_Description);
        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.CarCode_tv)
        TextView CarCodeTv;
        @BindView(R.id.Alarm_DateTime_tv)
        TextView AlarmDateTimeTv;
        @BindView(R.id.Alarm_Description_tv)
        TextView AlarmDescriptionTv;
        @BindView(R.id.Alarm_Type_Oid_iv)
        ImageView AlarmTypeOidTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
