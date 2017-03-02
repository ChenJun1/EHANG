package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.sendcars;

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
*创建时间：2017/1/16 下午3:53
*描述：派车单详情适配器
************************************************************************************/


public class SendCarDetailAdapter extends BaseAdapter {


    private Context context;
    private List<SendCarDetailModel.DataValueBean> list;
    private MyOnClickItemListener listener;
    private ItemView itemView;

    public SendCarDetailAdapter(Context context, List<SendCarDetailModel.DataValueBean> list, MyOnClickItemListener listener) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_send_car_detail_item, null);

            itemView.time = (TextView) convertView.findViewById(R.id.time);
            itemView.carCode = (TextView) convertView.findViewById(R.id.carCode);
            itemView.piao = (TextView) convertView.findViewById(R.id.piao);
            itemView.jian = (TextView) convertView.findViewById(R.id.jian);
            itemView.zhong = (TextView) convertView.findViewById(R.id.zhong);
            itemView.tiji = (TextView) convertView.findViewById(R.id.tiji);
            itemView.foreView = (TextView) convertView.findViewById(R.id.foreView);
            itemView.back_view = (TextView) convertView.findViewById(R.id.back_view);
            itemView.root = (LinearLayout) convertView.findViewById(R.id.root);
            convertView.setTag(itemView);
        } else {
            itemView = (ItemView) convertView.getTag();
        }

        final SendCarDetailModel.DataValueBean info = list.get(position);


        if (info != null) {

            //设置时间
            if (!TextUtils.isEmpty(info.getLetter_DateStr())) {
                itemView.time.setText(DateUtil.strOldFormat2NewFormat(info.getLetter_DateStr(), DateUtil.FORMAT_YMD, DateUtil.FORMAT_MD));
            } else {
                itemView.time.setText("--");
            }


            //设置车牌号
            if (!TextUtils.isEmpty(info.getCarCode())) {
                itemView.carCode.setText(info.getCarCode());
            } else {
                itemView.carCode.setText("车牌号：" + "--");
            }

            //设置票数

            if (!TextUtils.isEmpty(info.getTicket_Count())) {
                itemView.piao.setText("票数：" + info.getTicket_Count() + "票");
            } else {
                itemView.piao.setText("票数：" + "--");
            }

            //设置件数
            if (!TextUtils.isEmpty(info.getTotal_Goods_Num())) {
                itemView.jian.setText("件数：" + info.getTotal_Goods_Num() + "件");
            } else {
                itemView.jian.setText("件数：" + "--");
            }
            //设置重量

            if (!TextUtils.isEmpty(info.getTotal_Goods_Weight())) {
                itemView.zhong.setText("重量：" + info.getTotal_Goods_Weight() + "kg");
            } else {
                itemView.zhong.setText("重量：" + "--");
            }

            //设置体积

            if (!TextUtils.isEmpty(info.getTotal_Bulk_Weight())) {
                itemView.tiji.setText("体积：" + info.getTotal_Bulk_Weight() + "m³");
            } else {
                itemView.tiji.setText("体积：" + "--");
            }


            /**
             * 判断是否显示线
             */
            if (position != list.size() - 1) {
                SendCarDetailModel.DataValueBean start = list.get(position);
                SendCarDetailModel.DataValueBean next = list.get(position + 1);
                if (showLineView(start.getLetter_DateStr(), next.getLetter_DateStr())) {
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
                if (!showLineView(start.getLetter_DateStr(), next.getLetter_DateStr())) {
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
        TextView carCode;
        TextView piao;
        TextView jian;
        TextView zhong;
        TextView tiji;
        TextView foreView;
        TextView back_view;
        LinearLayout root;
    }

}
