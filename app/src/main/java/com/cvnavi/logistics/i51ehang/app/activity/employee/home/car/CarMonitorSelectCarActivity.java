package com.cvnavi.logistics.i51ehang.app.activity.employee.home.car;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetMyCarFleetResponse;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:10
*描述：我的车队车队监控 中的筛选车辆
************************************************************************************/

public class CarMonitorSelectCarActivity extends BaseActivity {

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.listview)
    ListView listview;
    private List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> list;

    public static void start(Context context, List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> list) {
        Intent starter = new Intent(context, CarMonitorSelectCarActivity.class);
        starter.putExtra("data", (Serializable) list);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_monitor_select_car);
        ButterKnife.bind(this);
        titltTv.setText("选择车辆");
        list = (List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean>) getIntent().getSerializableExtra("data");
        if(list == null){
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(this, "暂无车辆", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
            return;
        }

        listview.setAdapter(new DataAdapter());
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.get(position) != null) {
                    EventBus.getDefault().post(list.get(position));
                    finish();
                }
            }
        });
    }


    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }

    class DataAdapter extends BaseAdapter {

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
            ItemView item;
            if (convertView == null) {
                item = new ItemView();
                convertView = LayoutInflater.from(CarMonitorSelectCarActivity.this).inflate(R.layout.layout_choice_driver_item, null);
                item.content_tv = (TextView) convertView.findViewById(R.id.driver_name_tv);
                convertView.setTag(item);
            } else {
                item = (ItemView) convertView.getTag();
            }

            GetMyCarFleetResponse.DataValueBean.MCarInfoListBean info = list.get(position);
            item.content_tv.setTextColor(0xff000000);
            if (info != null) {
                item.content_tv.setText(info.getCarCode());
            }else {
                item.content_tv.setText("无");
            }
            return convertView;
        }

        class ItemView {
            ImageView icon;

            TextView content_tv;

        }


    }


}
