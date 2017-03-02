package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarSelectLineResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.List;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午4:02
 * 描述：发车监控选择线路
 ************************************************************************************/


public class SendCarMonitorSelectLineAdapter extends BaseAdapter {

    private List<SendCarSelectLineResponse.DataValueBean> list;
    private Context context;
    private ItemView itemView;
    private MyOnClickItemListener listener;

    public SendCarMonitorSelectLineAdapter(Context context, List<SendCarSelectLineResponse.DataValueBean> list, MyOnClickItemListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemView = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_send_car_select_line, null);
            itemView.line = (TextView) convertView.findViewById(R.id.line);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        final SendCarSelectLineResponse.DataValueBean info = list.get(position);

        //第一条数据是假数据，设置显示的样式，间隔
        if (position == 0) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            itemView.line.setLayoutParams(params);
            itemView.line.setPadding(Utils.getResourcesDimension(R.dimen.dimen_34), Utils.getResourcesDimension(R.dimen.dimen_8), Utils.getResourcesDimension(R.dimen.dimen_34), Utils.getResourcesDimension(R.dimen.dimen_8));
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            itemView.line.setLayoutParams(params);
            itemView.line.setPadding(Utils.getResourcesDimension(R.dimen.dimen_10), Utils.getResourcesDimension(R.dimen.dimen_8), Utils.getResourcesDimension(R.dimen.dimen_10), Utils.getResourcesDimension(R.dimen.dimen_8));
        }

        //手动设置是否是选中状态
        if (info.isSelect()) {
            itemView.line.setBackgroundResource(R.drawable.shape_send_car_select_all);
            itemView.line.setTextColor(0xff788fb5);
        } else {
            itemView.line.setBackgroundResource(R.drawable.shape_send_car_select_line);
            itemView.line.setTextColor(0xffffffff);
        }

        itemView.line.setText(info.getLine_Name());
        itemView.line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.myOnClickItem(position, info);
                }

            }
        });


        return convertView;
    }


    class ItemView {
        TextView line;
    }


}
