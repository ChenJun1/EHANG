package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.Dr_OrderExceptionInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.preview.PreviewPicPagerActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder.ExceptionInfoBean;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

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
 * Depict: 货单异常
 */
public class Dr_OrderExceptionInfoAdapter extends BaseAdapter {
    private List<ExceptionInfoBean> list;
    private LayoutInflater inflater;
    private Context mContext;

    public Dr_OrderExceptionInfoAdapter(List<ExceptionInfoBean> list, Context context) {
        super();
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.mContext=context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final ExceptionInfoBean bean= (ExceptionInfoBean) getItem(position);
        final Dr_OrderExceptionInfoActivity activity= (Dr_OrderExceptionInfoActivity) mContext;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exception_info, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(bean!=null) {
            SetViewValueUtil.setTextViewValue(viewHolder.dateTimeTv, bean.Operate_DateTime);
            SetViewValueUtil.setTextViewValue(viewHolder.exceptionTypeTv, bean.Exception_Mode);
            if(!TextUtils.isEmpty(bean.IMGCopies)&&!bean.IMGCopies.equals("0")){
                viewHolder.picLl.setVisibility(View.VISIBLE);
                viewHolder.picTv.setText("查看 "+bean.IMGCopies);
            }else{
                viewHolder.picLl.setVisibility(View.GONE);
            }
        }

        viewHolder.picLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, PreviewPicPagerActivity.class);
                intent.putExtra(Constants.ExceptionInfoBean,bean);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.dateTime_tv)
        TextView dateTimeTv;
        @BindView(R.id.exception_type_tv)
        TextView exceptionTypeTv;
        @BindView(R.id.pic_tv)
        TextView picTv;
        @BindView(R.id.pic_ll)
        LinearLayout picLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
