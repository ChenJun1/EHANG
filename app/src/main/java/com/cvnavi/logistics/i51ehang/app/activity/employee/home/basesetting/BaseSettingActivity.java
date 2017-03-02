package com.cvnavi.logistics.i51ehang.app.activity.employee.home.basesetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location.DriverStaticAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.location.DriverCarTreeListActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverManagerActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.widget.activity.BaseSwipeBackActivity;

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
*创建时间：2017/1/16 下午3:09
*描述：基础设置 包含车辆管理和司机管理
************************************************************************************/

public class BaseSettingActivity extends BaseSwipeBackActivity {

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
        data = MyApplication.getInstance().getLoginInfo();
        list = getBusinessList();
        title.setText("基础设置");
        mAdapter = new DriverStaticAdapter(list, this);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainService info = list.get(position);
                if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_CAR_MANAGE + "")) {
                    //车辆管理
                    Intent intent = new Intent(BaseSettingActivity.this, DriverCarTreeListActivity.class);
                    intent.putExtra(Constants.HOME, Constants.HOME);
                    startActivity(intent);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_DRIVER_MANAGE + "")) {
                    //司机管理
                    showActivity(BaseSettingActivity.this, DriverManagerActivity.class);
                }
            }
        });
    }

    //获取业务list
    private ArrayList<mMainService> getBusinessList() {
        if (data == null) {
            return null;
        }

        List<mMainService> alllist = data.DataValue.MenuList;
        ArrayList<mMainService> dataList = new ArrayList<mMainService>();
        if (alllist != null) {
            //筛选
            for (int i = 0; i < alllist.size(); i++) {
                if (alllist.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_SETTING + "")) {
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
