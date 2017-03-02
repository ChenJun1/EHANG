package com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.employee.me.GetDestinationResponse;
import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:03
*描述：常用目的地适配器
************************************************************************************/

public class CommonAddressAdapter extends BaseAdapter {
    private Context context;
    private List<GetDestinationResponse.DataValueBean> list;
    private ItemView itemView;
    private SelectDesc listener;

    public CommonAddressAdapter(Context context, List<GetDestinationResponse.DataValueBean> list, SelectDesc listener) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            itemView = new ItemView();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_common_address, null);
            itemView.textView = (TextView) convertView.findViewById(R.id.address_tv);
            itemView.root = (LinearLayout) convertView.findViewById(R.id.root);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }
        final GetDestinationResponse.DataValueBean bean = list.get(position);
        if (bean != null) {
            //设置重常用目的地的名称
            itemView.textView.setText(bean.getDestination());
            itemView.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.select(bean);
                    }
                }
            });
        }

        return convertView;
    }

    class ItemView {
        public TextView textView;
        public LinearLayout root;
    }


    public interface SelectDesc {
        public void select(GetDestinationResponse.DataValueBean info);
    }

}
