package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.ArrayList;

/**
 * Created by ${ChenJ} on 2016/7/25.
 */
public class DriverChildAdapter extends BaseExpandableListAdapter {

    private Context mContext;// 上下文

    private ArrayList<mCarInfo> mChilds;// 数据源

    public DriverChildAdapter(Context context, ArrayList<mCarInfo> childs) {
        this.mContext = context;
        this.mChilds = childs;
    }

    @Override
    public int getGroupCount() {
        return mChilds != null ? mChilds.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).CarList != null ? mChilds
                .get(groupPosition).CarList.size() : 0;
    }

    @Override
    public mCarInfo getGroup(int groupPosition) {
        if (mChilds != null && mChilds.size() > 0)
            return mChilds.get(groupPosition);
        return null;
    }

    @Override
    public mCarInfo getChild(int groupPosition, int childPosition) {
        if (mChilds.get(groupPosition).CarList != null
                && mChilds.get(groupPosition).CarList.size() > 0)
            return mChilds.get(groupPosition).CarList
                    .get(childPosition);
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        TextView childChildTV;
        TextView IsOnline_Status_tv;
        ImageView TemperatureCount_iv;
        mCarInfo carInfo = getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_driver_car_list_child_child, null);
        }
        childChildTV = ViewHolder.get(convertView, R.id.childChildTV);
        TemperatureCount_iv = ViewHolder.get(convertView, R.id.TemperatureCount_iv);
        IsOnline_Status_tv=ViewHolder.get(convertView,R.id.IsOnline_Status_tv);

        SetViewValueUtil.setTextViewValue(childChildTV, carInfo.CarCode);
        SetViewValueUtil.setTextViewValue(IsOnline_Status_tv,carInfo.IsOnline_Status);
        if(!TextUtils.isEmpty(carInfo.IsOnline_Status)&&carInfo.IsOnline_Status.equals("离线")){
            IsOnline_Status_tv.setTextColor(Utils.getResourcesColor(R.color.text_red));
        }else{
            IsOnline_Status_tv.setTextColor(Utils.getResourcesColor(R.color.text_black));
        }
        if (!TextUtils.isEmpty(carInfo.TemperatureCount) && carInfo.TemperatureCount.equals("1")) {
            TemperatureCount_iv.setImageResource(R.drawable.thermometer_red);
        } else {
            TemperatureCount_iv.setImageResource(R.drawable.thermometer_gray);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        ChildHolder holder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(
//                    R.layout.item_driver_car_list_child_child, null);
//            holder = new ChildHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ChildHolder) convertView.getTag();
//        }
//        holder.update(getChild(groupPosition, childPosition).CarCode);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

//    /**
//     * @author Apathy、恒
//     *         <p>
//     *         Holder优化
//     */
//    class GroupHolder {
//
//        private TextView childGroupTV;
//
//        public GroupHolder(View v) {
//            childGroupTV = (TextView) v.findViewById(R.id.childGroupTV);
//        }
//
//        public void update(mCarInfo model) {
//            childGroupTV.setText(model.CarCode);
//        }
//    }

}
