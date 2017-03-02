package com.cvnavi.logistics.i51ehang.app.activity.employee.home.storehouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.StowageStatisticsSummary.SummaryItem;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:04
*描述：库存统计下方的模块适配器
************************************************************************************/


public class StoreGridViewAdapter extends BaseAdapter {

    private Context mContext;
    public List<SummaryItem> SummaryList;

    public StoreGridViewAdapter(Context mContext, List<SummaryItem> SummaryList) {
        super();
        this.mContext = mContext;
        this.SummaryList = SummaryList;
    }

    @Override
    public int getCount() {
        if (SummaryList == null) {
            return 0;
        } else {
            return SummaryList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return SummaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_statistics_gv_item, parent, false);
        }
        TextView tv = ViewHolder.get(convertView, R.id.tv_item);
        TextView iv = ViewHolder.get(convertView, R.id.iv_item);
        LinearLayout ll = ViewHolder.get(convertView, R.id.root_ll);

        //设置背景的颜色 ，类型的颜色，数量的 颜色
        ll.setBackgroundColor(Utils.getResourcesColor(R.color.store_gridview_item_bg));

        iv.setText(SummaryList.get(position).Name);
        tv.setText(SummaryList.get(position).Numval);
        iv.setTextColor(Utils.getResourcesColor(R.color.store_gridview_text_color));
        tv.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));


        return convertView;
    }


}
