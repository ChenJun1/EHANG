package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.exception.MyTaskExceptionInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.preview.PreviewPicPagerActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.CarExceptionBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.PictureBean;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${ChenJ} on 2016/8/22.
 */
public class MyTaskExceptionInfoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<CarExceptionBean> list;
    private MyTaskExceptionInfoActivity mContext;

    public MyTaskExceptionInfoAdapter(List<CarExceptionBean> list, Context context) {
        super();
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.mContext= (MyTaskExceptionInfoActivity) context;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public CarExceptionBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CarExceptionBean bean = getItem(position);
        ViewHolder viewHolder=null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_task_exception_info, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(bean!=null){
            SetViewValueUtil.setTextViewValue(viewHolder.ExceptionDateTime,bean.Exception_DateTime);
            SetViewValueUtil.setTextViewValue(viewHolder.ExceptionType,bean.Exception_Type);
            if(bean.IMGList!=null&&bean.IMGList.size()>0){
                viewHolder.picTv.setText("查看"+bean.IMGList.size());
                viewHolder.picLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mContext, PreviewPicPagerActivity.class);
                        List<PictureBean> dataList= bean.IMGList;
                        intent.putExtra(Constants.MyTaskExceptionInfoActivity, (Serializable) dataList);
                        mContext.startActivity(intent);
                    }
                });
            }
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.progress_state_iv)
        ImageView progressStateIv;
        @BindView(R.id.progress_v)
        View progressV;
        @BindView(R.id.Exception_DateTime)
        TextView ExceptionDateTime;
        @BindView(R.id.Exception_Type)
        TextView ExceptionType;
        @BindView(R.id.pic_tv)
        TextView picTv;
        @BindView(R.id.pic_ll)
        LinearLayout picLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
