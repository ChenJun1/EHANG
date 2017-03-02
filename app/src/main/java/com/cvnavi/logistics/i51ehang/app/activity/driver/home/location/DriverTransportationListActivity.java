package com.cvnavi.logistics.i51ehang.app.activity.driver.home.location;

import android.content.Context;
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
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.ArriveCarMonitorActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.SendCarMonitorActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.transportation.CarPlanActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.widget.activity.BaseSwipeBackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 调度运输
 */
public class DriverTransportationListActivity extends BaseSwipeBackActivity {

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

    public static void start(Context context) {
        Intent starter = new Intent(context, DriverTransportationListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_transportation_list);
        ButterKnife.bind(this);
        title.setText("运输");
        list = getBusinessList();
        mAdapter = new DriverStaticAdapter(list, this);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainService info = list.get(position);
                if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_MONITOR + "")) {
                    SendCarMonitorActivity.startActivity(DriverTransportationListActivity.this);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_PLAN + "")) {
                    Intent intent = new Intent(DriverTransportationListActivity.this, CarPlanActivity.class);
                    startActivity(intent);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_ARRIVE_MONITOR + "")) {
                    ArriveCarMonitorActivity.start(DriverTransportationListActivity.this);
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
                //筛选调度运输中的list
                if (alllist.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_TRANSTION + "")) {
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
