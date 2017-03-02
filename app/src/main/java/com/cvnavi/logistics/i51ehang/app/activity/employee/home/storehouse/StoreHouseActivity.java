package com.cvnavi.logistics.i51ehang.app.activity.employee.home.storehouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location.DriverStaticAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 下午2:58
*描述： 从首页界面
************************************************************************************/

public class StoreHouseActivity extends BaseActivity {

    @BindView(R.id.back_ll)
    LinearLayout backLl;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.lv)
    ListView lv;
    private GetAppLoginResponse data;
    private List<mMainService> list;
    private DriverStaticAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_transportation_list);
        ButterKnife.bind(this);
        title.setText("库房");
        list = getBusinessList();
        mAdapter = new DriverStaticAdapter(list, this);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mMainService info = list.get(position);
                if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_KU_CUN + "")) {

                    startActivity(new Intent(StoreHouseActivity.this, StockActivity.class));
                }
            }
        });

    }

    //获取业务list
    private ArrayList<mMainService> getBusinessList() {
        if (data == null) {
            data = MyApplication.getInstance().getLoginInfo();
        }

        List<mMainService> alllist = data.DataValue.MenuList;
        ArrayList<mMainService> dataList = new ArrayList<mMainService>();
        if (alllist != null) {
            for (int i = 0; i < alllist.size(); i++) {
                //筛选库中的list
                if (alllist.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_KU_FANG + "")) {
                    dataList.addAll(alllist.get(i).ServiceList);
                }
            }
        }

        return dataList;
    }


    @OnClick(R.id.back_ll)
    public void onClick() {
        finish();
    }
}
