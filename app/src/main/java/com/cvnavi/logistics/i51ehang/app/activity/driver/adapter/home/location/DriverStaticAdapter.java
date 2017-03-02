package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;

import java.util.List;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/25.
 */
public class DriverStaticAdapter extends BaseAdapter {
    private List<mMainService> mList;
    private Context mContext;
    private ItemView itemView;


    public DriverStaticAdapter(List<mMainService> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;

        }
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
            itemView = new ItemView();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_statics_car, null);
            itemView.content_tv = (TextView) convertView.findViewById(R.id.content_tv);
            itemView.icon = (ImageView) convertView.findViewById(R.id.icon);
            itemView.line = (TextView) convertView.findViewById(R.id.line);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        itemView.content_tv.setText(mList.get(position).ServiceName);
        switch (Integer.valueOf(mList.get(position).ServiceID)) {
            case 5:
                itemView.icon.setImageResource(R.drawable.static_order);
                break;
            case 6:
                itemView.icon.setImageResource(R.drawable.static_peizai);
                break;
            case 8:
                itemView.icon.setImageResource(R.drawable.static_put);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_MILES_TONGJI:
                itemView.icon.setImageResource(R.drawable.mile_icon);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_STATICS:
                itemView.icon.setImageResource(R.drawable.icon_static_paiche);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_WEI_TUO_STATICS:
                itemView.icon.setImageResource(R.drawable.icon_static_weituo);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_CAI_WU_STATICS:
                itemView.icon.setImageResource(R.drawable.icon_static_caiwu);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_PLAN:
                itemView.icon.setImageResource(R.drawable.icon_trans_plan);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_MONITOR:
                itemView.icon.setImageResource(R.drawable.icon_trans_send);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_ARRIVE_MONITOR:
                itemView.icon.setImageResource(R.drawable.icon_trans_arrive);
                break;
            case Constants.EMPLOYEE_SERVICE_ID_KU_CUN:
                itemView.icon.setImageResource(R.drawable.icon_store_fang);
                break;


            default:
                itemView.icon.setVisibility(View.GONE);
                break;
        }

//        if (position == 1 || position == 4) {
//            itemView.line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 2)));
//            itemView.line.setBackgroundColor(Utils.getResourcesColor(R.color.send_car_detail_text_color));
//        } else {
//            itemView.line.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(mContext, 1)));
//            itemView.line.setBackgroundColor(Utils.getResourcesColor(R.color.send_car_detail_text_color));
//        }


        return convertView;
    }

    private class ItemView {
        public TextView content_tv;
        public ImageView icon;
        public TextView line;

    }
}
