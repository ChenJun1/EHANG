package com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.order.MyOrderListener;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:07
*描述：选择班次界面
************************************************************************************/


public class ChoiceShuttleActivity extends BaseActivity implements MyOrderListener {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout operationLlayout;
    @BindView(R.id.choice_line_lv)
    PullToRefreshListView choiceLineLv;
    private List<mLineInfo.ShuttleListBean> ShuttleList;
    private ChoiceShuttleListViewAdapter adapter;


    public static void startActivity(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ChoiceShuttleActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_line);
        ButterKnife.bind(this);
        titleTv.setText("选择班次");
        ShuttleList = MyApplication.getInstance().getShuttleList();
        if (ShuttleList != null && ShuttleList.size() > 0) {
            //有班次数据设置数据显示
            adapter = new ChoiceShuttleListViewAdapter(ShuttleList, this, this);
            choiceLineLv.setAdapter(adapter);
        } else {
            //没有班次弹出消息，等待确认取消离开界面
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(this, "暂无班次！", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
        }
    }

    @Override
    public void onClickOrder(int position) {
        //返货线路oid，班次序号，班次oid
        mLineInfo.ShuttleListBean bean = ShuttleList.get(position);
        Intent intent = new Intent();
        intent.putExtra("Line_Oid", bean.getLine_Oid());
        intent.putExtra("Shuttle_No", bean.getShuttle_No());
        intent.putExtra("Shuttle_Oid", bean.getShuttle_Oid());
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }


    class ChoiceShuttleListViewAdapter extends BaseAdapter {

        private Context context;
        private List<mLineInfo.ShuttleListBean> ShuttleList;
        private ViewHolder holder;
        private MyOrderListener listener;


        public ChoiceShuttleListViewAdapter(List<mLineInfo.ShuttleListBean> ShuttleList, Context context, MyOrderListener listener) {
            this.context = context;
            this.ShuttleList = ShuttleList;
            this.listener = listener;
        }

        @Override
        public int getCount() {
            if (ShuttleList == null) {
                return 0;
            }
            return ShuttleList.size();
        }

        @Override
        public Object getItem(int position) {
            return ShuttleList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_choice_line_item, null);
                holder.lineTypeTv = (TextView) convertView.findViewById(R.id.line_type_tv);
                holder.lineNameTv = (TextView) convertView.findViewById(R.id.line_name_tv);
                holder.root_ll = (LinearLayout) convertView.findViewById(R.id.root_ll);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final mLineInfo.ShuttleListBean lineInfo = ShuttleList.get(position);
            //设置线路类型
            if (TextUtils.isEmpty(lineInfo.getShuttle_No())) {
                holder.lineTypeTv.setText("无");
            } else {
                holder.lineTypeTv.setText(lineInfo.getShuttle_No());

            }

            //设置线路名称
            holder.lineNameTv.setText(lineInfo.getShuttle_Oid());
            holder.lineNameTv.setVisibility(View.GONE);
            holder.root_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClickOrder(position);
                    }
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView lineTypeTv;
            TextView lineNameTv;
            LinearLayout root_ll;
        }
    }

}
