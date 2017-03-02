package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MySendCarDetailBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.Model_LetterTrace_Node;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil.setTextViewValue;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 发车详情
 */
public class DrSendCarDetailLineFollowAdapter extends BaseAdapter {
    private List<MySendCarDetailBean.LineNodesBean> list;
    private LayoutInflater inflater;
    private TaskLineLookPicListener lineLookPicListener;

    public DrSendCarDetailLineFollowAdapter(List<MySendCarDetailBean.LineNodesBean> list, Context context, TaskLineLookPicListener lineLookPicListener) {
        super();
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.lineLookPicListener=lineLookPicListener;

    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public MySendCarDetailBean.LineNodesBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MySendCarDetailBean.LineNodesBean node = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dr_send_car_detail_line_follow_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (node != null) {
            setTextViewValue(viewHolder.arriveAddressTv, node.getOrg_Name());
            setTextViewValue(viewHolder.date1ValueTv, node.getArrive_DateTime());
            setTextViewValue(viewHolder.date2ValueTv, node.getLeave_DateTime());
            //第一个
            if (position == 0) {
                viewHolder.date2NameTv.setText(R.string.send_car_time);
                viewHolder.date1NameTv.setVisibility(View.GONE);
                setTextViewValue(viewHolder.date2ValueTv, node.getLeave_DateTime());
                viewHolder.date1ValueTv.setVisibility(View.GONE);
            }
//            if (position == getCount() - 1) {
//                viewHolder.date1NameTv.setText(R.string.reach_car_time);
//                viewHolder.date2NameTv.setVisibility(View.GONE);
//            }
            //判断是否有照片
            if(TextUtils.isEmpty(node.getArriveImgNo())||node.getArriveImgNo().equals("0")){
                viewHolder.picLl.setVisibility(View.GONE);
            }else{
                viewHolder.picLl.setVisibility(View.VISIBLE);
                viewHolder.picTv.setText("查看 "+node.getArrive_DateTime());
                viewHolder.picLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        lineLookPicListener.onLookPic(node);
                    }
                });
            }
            //判断到达时间是否为空
            if (!TextUtils.isEmpty(node.getArrive_DateTime())) {
                viewHolder.date1NameTv.setText("到达  " + node.getCity());
                viewHolder.arriveAddressTv.setTextColor(Utils.getResourcesColor(R.color.color_71B2EE));
                viewHolder.date1ValueTv.setTextColor(Utils.getResourcesColor(R.color.color_71B2EE));
                viewHolder.date1NameTv.setTextColor(Utils.getResourcesColor(R.color.color_71B2EE));
                viewHolder.progressStateIv.setImageResource(R.drawable.arrived);
                if (position == 0) {
                    viewHolder.progressV.setBackgroundColor(Utils.getResourcesColor(R.color.color_71B2EE));
                }

                if(position==list.size()-1){ //最后一个到达点
                    viewHolder.progressStateIv.setImageResource(R.drawable.circle);
                    viewHolder.progressV.setBackgroundColor(Utils.getResourcesColor(R.color.color_71B2EE));
                }
                //判断是当前节点
                if(position<list.size()-1){
                    MySendCarDetailBean.LineNodesBean nextNode = list.get(position + 1);
                    if(!TextUtils.isEmpty(nextNode.getArrive_DateTime())){
                        viewHolder.progressStateIv.setImageResource(R.drawable.circle);
                    }
                }
            } else {
                viewHolder.date1NameTv.setVisibility(View.GONE);
                viewHolder.date1ValueTv.setVisibility(View.GONE);
            }
            //判断发车时间是否为空
            if (!TextUtils.isEmpty(node.getLeave_DateTime())) {
                viewHolder.arriveAddressTv.setTextColor(Utils.getResourcesColor(R.color.color_71B2EE));
                viewHolder.date2ValueTv.setTextColor(Utils.getResourcesColor(R.color.color_71B2EE));
                viewHolder.date2NameTv.setTextColor(Utils.getResourcesColor(R.color.color_71B2EE));
                viewHolder.progressV.setBackgroundColor(Utils.getResourcesColor(R.color.color_71B2EE));
                viewHolder.progressStateIv.setImageResource(R.drawable.arrived);
            } else {
                viewHolder.date2NameTv.setVisibility(View.GONE);
                viewHolder.date2ValueTv.setVisibility(View.GONE);
            }

        }


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.progress_state_iv)
        ImageView progressStateIv;
        @BindView(R.id.progress_v)
        View progressV;
        @BindView(R.id.arrive_address_tv)
        TextView arriveAddressTv;
        @BindView(R.id.date1_name_tv)
        TextView date1NameTv;
        @BindView(R.id.date1_value_tv)
        TextView date1ValueTv;
        @BindView(R.id.date2_name_tv)
        TextView date2NameTv;
        @BindView(R.id.date2_value_tv)
        TextView date2ValueTv;
        @BindView(R.id.pic_tv)
        TextView picTv;
        @BindView(R.id.pic_ll)
        LinearLayout picLl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface TaskLineLookPicListener{
        void onLookPic(Model_LetterTrace_Node node);
    }
}
