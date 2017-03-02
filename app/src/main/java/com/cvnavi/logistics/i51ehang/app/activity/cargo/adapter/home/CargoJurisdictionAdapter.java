package com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home;

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

import java.util.ArrayList;


/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict:　货主权限
 */

public class CargoJurisdictionAdapter extends BaseAdapter{
    private ArrayList<mMainService> list;
    private Context context;
    private CargoJurisdictionAdapter.ViewHolder viewHolder;

    public CargoJurisdictionAdapter(ArrayList<mMainService> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
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
        if (convertView == null) {
            viewHolder = new CargoJurisdictionAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_driver_home_yewu_gv_item, null);

            viewHolder.menuItemImageView = (ImageView) convertView.findViewById(R.id.menuitem_iv);
            viewHolder.menuItemNameTextView = (TextView) convertView.findViewById(R.id.menuitem_tv);
//            viewHolder.badgeView = new BadgeView(context,viewHolder.menuItemImageView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (CargoJurisdictionAdapter.ViewHolder) convertView.getTag();
        }

//        if (position % 3 == 0){
//
//            viewHolder.badgeView.setText(String.valueOf(position));
//            viewHolder.badgeView.show();
//        }else {
//            viewHolder.badgeView.hide();
//        }
        mMainService info = list.get(position);
        if (info != null) {

            switch (info.ServiceID){
                case Constants.HOME_BASE_WWC:
                    viewHolder.menuItemImageView.setImageResource(R.drawable.unfinished);
                    break;
                case Constants.HOME_BASE_LS:
                    viewHolder.menuItemImageView.setImageResource(R.drawable.historyorder);
                    break;
                case Constants.HOME_BASE_QH:
                    viewHolder.menuItemImageView.setImageResource(R.drawable.record);
                    break;
                case Constants.HOME_BASE_WO:
                    viewHolder.menuItemImageView.setImageResource(R.drawable.money);
                    break;
            }
            viewHolder.menuItemNameTextView.setText(info.ServiceName);
        }


        return convertView;
    }


    private class ViewHolder {
        public ImageView menuItemImageView;
        public TextView menuItemNameTextView;
//        BadgeView badgeView;
    }
}
