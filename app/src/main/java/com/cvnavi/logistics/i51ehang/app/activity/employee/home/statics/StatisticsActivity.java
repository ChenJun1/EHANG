package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location.DriverStaticAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.location.DriverCarTreeListActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetMileStatisticActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.statistics.DriverOrderStatisticsActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.statistics.DriverStowageStatisticsSummaryActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.caiwu.CaiWuActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.sendcars.SendCarsStatics;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics.weituo.WeiTuoActivity;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.mMainService;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.location.LocationChooseCarCallback;
import com.cvnavi.logistics.i51ehang.app.callback.manager.LocationChooseCarCallBackManager;
import com.cvnavi.logistics.i51ehang.app.widget.activity.BaseSwipeBackActivity;
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
 * 创建时间：2017/1/16 下午4:03
 * 描述：包含货单统计，发车统计，里程统计
 ************************************************************************************/

public class StatisticsActivity extends BaseSwipeBackActivity implements LocationChooseCarCallback {
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.back_ll)
    LinearLayout backLl;
    @BindView(R.id.title)
    TextView title;
    private GetAppLoginResponse data;
    private List<mMainService> list;
    private List<String> mList;
    private DriverStaticAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_transportation_list);
        ButterKnife.bind(this);
        data = MyApplication.getInstance().getLoginInfo();
        LocationChooseCarCallBackManager.newStance().add(this);
        list = getBusinessList();
        title.setText("统计");
        mAdapter = new DriverStaticAdapter(list, this);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mMainService info = list.get(position);
                if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_HUODAN_TONGJI + "")) {
                    Intent intent = new Intent(StatisticsActivity.this, DriverOrderStatisticsActivity.class);
                    startActivity(intent);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_PEIZAI_TONGJI + "")) {
                    Intent intent = new Intent(StatisticsActivity.this, DriverStowageStatisticsSummaryActivity.class);
                    startActivity(intent);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_SEND_CAR_STATICS + "")) {
                    SendCarsStatics.start(StatisticsActivity.this);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_WEI_TUO_STATICS + "")) {
                    WeiTuoActivity.start(StatisticsActivity.this);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_CAI_WU_STATICS + "")) {
                    //财务统计
                    CaiWuActivity.start(StatisticsActivity.this);
                } else if (info.ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_MILES_TONGJI + "")) {
                    Intent intent = new Intent(StatisticsActivity.this, DriverCarTreeListActivity.class);
                    startActivity(intent);
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
            for (int i = 0; i < alllist.size(); i++) {
                //筛选出统计的界面，里程 统计单独拿出来
                if (alllist.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_TONGJI + "")) {
                    dataList.addAll(alllist.get(i).ServiceList);
                }

                if (alllist.get(i).ServiceID.equals(Constants.EMPLOYEE_SERVICE_ID_MILES_TONGJI + "")) {
                    dataList.add(alllist.get(i));
                }
            }
        }

        return dataList;
    }


    @OnClick(R.id.back_ll)
    public void onClick() {
        finish();
    }


    @Override
    public void OnMonitorLoadCarCode(mCarInfo mCarInfo) {
        if (mCarInfo != null) {
            MyFleetMileStatisticActivity.startActivity(StatisticsActivity.this, mCarInfo.CarCode_Key, mCarInfo.CarCode, mCarInfo.TSP_CarCode_Key);
        }
    }

    @Override
    public void OnHistorLoadCarCode(mCarInfo mCarInfo) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationChooseCarCallBackManager.newStance().remove(this);
    }
}
