package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.weituo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarDetailModel;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;

import java.util.List;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:55
*描述：委托详情适配器
************************************************************************************/

public class WeiTuoAdapter extends BaseAdapter {

    private Context context;
    private List<SendCarDetailModel.DataValueBean> list;
    private MyOnClickItemListener listener;
    private ItemView itemView;

    public WeiTuoAdapter(Context context, List<SendCarDetailModel.DataValueBean> list, MyOnClickItemListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_weituo_item, null);
            itemView.time = (TextView) convertView.findViewById(R.id.time);
            itemView.name = (TextView) convertView.findViewById(R.id.name);
            itemView.piao = (TextView) convertView.findViewById(R.id.piao);
            itemView.jian = (TextView) convertView.findViewById(R.id.jian);
            itemView.send_time = (TextView) convertView.findViewById(R.id.send_time);
            itemView.foreView = (TextView) convertView.findViewById(R.id.foreView);
            itemView.back_view = (TextView) convertView.findViewById(R.id.back_view);
            itemView.root = (LinearLayout) convertView.findViewById(R.id.root);

            convertView.setTag(itemView);

        } else {
            itemView = (ItemView) convertView.getTag();

        }

        final SendCarDetailModel.DataValueBean info = list.get(position);

        if (info != null) {
            if (!TextUtils.isEmpty(info.getLetter_Date())) {
                itemView.time.setText(DateUtil.strOldFormat2NewFormat(info.getLetter_Date(), DateUtil.FORMAT_YMD, DateUtil.FORMAT_MD));
            } else {
                itemView.time.setText("--");
            }

            if (!TextUtils.isEmpty(info.getThreeCompany())) {
                itemView.name.setText(info.getThreeCompany());
            } else {
                itemView.name.setText("--");
            }

            if (!TextUtils.isEmpty(info.getTicket_Count())) {

                itemView.piao.setText("票数：" + info.getTicket_Count() + "票");
            } else {
                itemView.piao.setText("票数：" + "--");
            }

            if (!TextUtils.isEmpty(info.getGoods_Num())) {
                itemView.jian.setText("件数：" + info.getGoods_Num() + "件");

            } else {
                itemView.jian.setText("件数：" + "--");
            }

            if (!TextUtils.isEmpty(info.getLeave_DateTime())) {
                itemView.send_time.setText(info.getLeave_DateTime());

            } else {
                itemView.send_time.setText("--");
            }


            /**
             * 判断是否显示线
             */
            if (position != list.size() - 1) {
                SendCarDetailModel.DataValueBean start = list.get(position);
                SendCarDetailModel.DataValueBean next = list.get(position + 1);
                if (showLineView(start.getLetter_Date(), next.getLetter_Date())) {
                    itemView.foreView.setVisibility(View.VISIBLE);
                } else {
                    itemView.foreView.setVisibility(View.INVISIBLE);
                }
                itemView.back_view.setVisibility(View.VISIBLE);
            } else {
                itemView.foreView.setVisibility(View.INVISIBLE);
                itemView.back_view.setVisibility(View.INVISIBLE);
            }

            /**
             * 判断是会否显示时间
             */
            if (position != 0) {
                SendCarDetailModel.DataValueBean start = list.get(position - 1);
                SendCarDetailModel.DataValueBean next = list.get(position);
                if (!showLineView(start.getLetter_Date(), next.getLetter_Date())) {
                    itemView.time.setVisibility(View.INVISIBLE);
                } else {
                    itemView.time.setVisibility(View.VISIBLE);
                }
            } else {
                itemView.time.setVisibility(View.VISIBLE);
            }

            itemView.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.myOnClickItem(position, info);
                    }
                }
            });
        }


        return convertView;
    }

    /**
     * 比较两个view的时间
     *
     * @param forTime
     * @param backTime
     * @return
     */
    public static boolean showLineView(String forTime, String backTime) {
        if (TextUtils.isEmpty(forTime) || TextUtils.isEmpty(backTime)) {
            return true;
        }

        if (DateUtil.strOldFormat2NewFormat(forTime, DateUtil.FORMAT_YMD, DateUtil.FORMAT_MD).equals(DateUtil.strOldFormat2NewFormat(backTime, DateUtil.FORMAT_YMD, DateUtil.FORMAT_MD))) {
            return false;
        }
        return true;
    }

    class ItemView {

        TextView time;
        TextView name;
        TextView piao;
        TextView jian;
        TextView send_time;
        TextView foreView;
        TextView back_view;
        LinearLayout root;
    }

}