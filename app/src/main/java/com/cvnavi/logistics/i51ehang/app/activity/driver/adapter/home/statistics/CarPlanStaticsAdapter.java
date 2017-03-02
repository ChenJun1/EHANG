package com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics;

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
*创建时间：2017/1/17 上午9:22
*描述：发车统计界面的适配器
************************************************************************************/


public class CarPlanStaticsAdapter extends BaseAdapter {

    private Context mContext;
    public List<SummaryItem> SummaryList;

    public CarPlanStaticsAdapter(Context mContext, List<SummaryItem> SummaryList) {
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
        return position;
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

        ll.setBackgroundColor(Utils.getResourcesColor(R.color.store_gridview_item_bg));
        iv.setTextColor(Utils.getResourcesColor(R.color.store_gridview_text_color));
        tv.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
        tv.setText(SummaryList.get(position).Name);
        iv.setText(SummaryList.get(position).Numval);
        return convertView;
    }


}
