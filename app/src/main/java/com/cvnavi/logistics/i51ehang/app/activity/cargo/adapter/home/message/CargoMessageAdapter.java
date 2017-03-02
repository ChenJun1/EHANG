package com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.message;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myorder.DriverMyOrderLogisticsFollowActivity;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.GetMessageContentDataValue;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.viewholder.ViewHolder;

import java.util.List;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主 消息中心
 */

public class CargoMessageAdapter extends BaseAdapter {
    private Context context;
    private List<GetMessageContentDataValue> mList;


    public CargoMessageAdapter(Context context, List<GetMessageContentDataValue> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final GetMessageContentDataValue bean = mList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_cargo_msg_item, null);
        }
        RelativeLayout look_detail_layout = ViewHolder.get(convertView, R.id.look_detail_layout);
        final TextView cargo_msg_content_text = ViewHolder.get(convertView, R.id.cargo_msg_content_text);
        final TextView cargo_msg_type = ViewHolder.get(convertView, R.id.cargo_msg_type);
        final TextView cargo_msg_time = ViewHolder.get(convertView, R.id.cargo_msg_time);
        final TextView detail_text = ViewHolder.get(convertView, R.id.detail_text);


        look_detail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allTicketNo = bean.All_Ticket_No;
                String searOid = bean.Serial_Oid;
                DriverMyOrderLogisticsFollowActivity.startActivity((Activity) context, allTicketNo, "", searOid);


            }
        });

        SetViewValueUtil.setTextViewValue(cargo_msg_content_text, bean.Content);
        SetViewValueUtil.setTextViewValue(cargo_msg_time, DateUtil.strOldFormat2NewFormat(bean.Operate_DateTime, DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));

        if (!TextUtils.isEmpty(bean.Operate_Code)) {
            switch (bean.Operate_Code) {
                case "EC":
                    SetViewValueUtil.setTextViewValue(cargo_msg_type, "货单装车");
                    break;
                case "IB":
                    SetViewValueUtil.setTextViewValue(cargo_msg_type, "货单发车");
                    break;
                case "BV":
                    SetViewValueUtil.setTextViewValue(cargo_msg_type, "到达确认");
                    break;
                case "IE":
                    SetViewValueUtil.setTextViewValue(cargo_msg_type, "配送出库");
                    break;
                case "AH":
                    SetViewValueUtil.setTextViewValue(cargo_msg_type, "签收");
                    break;
            }
        }

        if (bean.IsRead.equals("1")) {
            cargo_msg_content_text.setTextColor(Utils.getResourcesColor(R.color.color_878787));
            cargo_msg_type.setTextColor(Utils.getResourcesColor(R.color.color_878787));
            cargo_msg_time.setTextColor(Utils.getResourcesColor(R.color.color_878787));
            detail_text.setTextColor(Utils.getResourcesColor(R.color.color_878787));
        }

        return convertView;
    }
}
