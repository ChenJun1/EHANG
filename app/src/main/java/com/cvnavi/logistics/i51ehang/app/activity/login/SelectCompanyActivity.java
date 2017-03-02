package com.cvnavi.logistics.i51ehang.app.activity.login;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.CompanyBean;
import com.cvnavi.logistics.i51ehang.app.callback.recycleview.OnItemClickLitener;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午3:01
 * 描述：登录选择公司界面
 ************************************************************************************/

public class SelectCompanyActivity extends BaseActivity implements OnItemClickLitener {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout operationLlayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;

    private SelectCompanyAdater adater;
    private List<CompanyBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_company);
        ButterKnife.bind(this);
        titleTv.setText("选择公司");
        data = new ArrayList<>();
        data = MyApplication.getInstance().getCompanyBeanList();
        if (data == null || data != null && data.size() <= 0) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SelectCompanyActivity.this, "无分公司!", null);
            return;
        }

        /**
         * 初始化适配器
         */
        adater = new SelectCompanyAdater();
        /**
         * 设置适配器的形式
         */

        rv.setLayoutManager(new LinearLayoutManager(this));

        /**
         * 设置点击事件
         */

        adater.setOnItemClickLitener(this);

        /**
         * 设置适配器
         */
        rv.setAdapter(adater);

    }

    @Override
    public void onItemClick(View view, int position) {
        if (data != null && data.size() > 0 && !TextUtils.isEmpty(data.get(position).getCompany_Oid())) {
            SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Company_Oid, data.get(position).getCompany_Oid());
            EventBus.getDefault().post(data.get(position));
            finish();
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }


    class SelectCompanyAdater extends RecyclerView.Adapter<SelectCompanyAdater.MyHolder> {

        private OnItemClickLitener mOnItemClickLitener;

        public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
            this.mOnItemClickLitener = mOnItemClickLitener;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(SelectCompanyActivity.this).inflate(R.layout.item_select_company, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyHolder holder, int position) {
            CompanyBean bean = data.get(position);
            holder.companyName.setText(bean.getCompany_Name());
            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                        return false;
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if (data == null) {
                return 0;
            }
            return data.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            TextView companyName;

            public MyHolder(View view) {
                super(view);
                companyName = (TextView) view.findViewById(R.id.company);
            }

        }
    }
}
