package com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.preview.PreviewPicPagerActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.mytask.MyTaskLookPicActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.PictureBean;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import volley.VolleyManager;

/**
 * Created by ${ChenJ} on 2016/7/26.
 */
public class MyTaskLookPicGriderViewAdapter extends BaseAdapter {
    private List<PictureBean> list;
    private LayoutInflater inflater;
    private Context mContext;


    public MyTaskLookPicGriderViewAdapter(List<PictureBean> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PictureBean bean= (PictureBean) getItem(position);
        final MyTaskLookPicActivity activity= (MyTaskLookPicActivity) mContext;
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_look_pic_image, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if(bean!=null) {
            VolleyManager.newInstance().ImageLoaderRequest(viewHolder.imag,bean.FilePath,R.drawable.cry,R.drawable.cry );
        }
        viewHolder.imag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PreviewPicPagerActivity.class);
                intent.putExtra(Constants.PictureBeanList, (Serializable) list);
                intent.putExtra(Constants.POSITION,position);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imag)
        ImageView imag;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
