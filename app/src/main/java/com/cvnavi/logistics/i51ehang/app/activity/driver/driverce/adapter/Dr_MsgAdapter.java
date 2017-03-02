package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.DrSendCarDetailActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MessageContentBean;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 司机消息中心
 */

public class Dr_MsgAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MessageContentBean> mList;

    public Dr_MsgAdapter(Context context, List<MessageContentBean> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public MessageContentBean getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        final MessageContentBean bean = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.dr_msg_item, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mLookSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrSendCarDetailActivity.start(mContext, bean.getLetter_Oid(), bean.getSerial_Oid());
            }
        });

        if (bean != null) {
            if (!TextUtils.isEmpty(bean.getOperate_Code())) {
                String str = "";
                switch (bean.getOperate_Code()) {
                    case "IB":
                        str = "车辆发车";
                        break;
                    case "BV":
                        str = "车辆到车";
                        break;
                    default:
                        break;
                }
                SetViewValueUtil.setTextViewValue(mViewHolder.mOperateCodeTv, str);
            }
            SetViewValueUtil.setTextViewValue(mViewHolder.mContentTv, bean.getContent());
            SetViewValueUtil.setTextViewValue(mViewHolder.mOperateDateTime, bean.getOperate_DateTime());
            if (!TextUtils.isEmpty(bean.getIsRead()) && bean.getIsRead().equals("1")) {
                mViewHolder.mOperateCodeTv.setTextColor(Utils.getResourcesColor(R.color.color_878787));
                mViewHolder.mContentTv.setTextColor(Utils.getResourcesColor(R.color.color_878787));
                mViewHolder.mOperateDateTime.setTextColor(Utils.getResourcesColor(R.color.color_878787));
            } else {
                mViewHolder.mOperateDateTime.setTextColor(Utils.getResourcesColor(R.color.color_5E5E5E));
                mViewHolder.mOperateCodeTv.setTextColor(Utils.getResourcesColor(R.color.color_1E81D1));
                mViewHolder.mContentTv.setTextColor(Utils.getResourcesColor(R.color.color_5E5E5E));
            }
        }
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.Operate_Code_tv)
        TextView mOperateCodeTv;
        @BindView(R.id.Operate_DateTime)
        TextView mOperateDateTime;
        @BindView(R.id.Content_tv)
        TextView mContentTv;
        @BindView(R.id.look_search)
        LinearLayout mLookSearch;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
