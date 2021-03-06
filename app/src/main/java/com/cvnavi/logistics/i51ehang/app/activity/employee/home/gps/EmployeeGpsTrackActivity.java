package com.cvnavi.logistics.i51ehang.app.activity.employee.home.gps;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location.DriverCarLineAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.location.DriverCarTreeListActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetRecordActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.adapter.GpsCarHistoryTrackAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.litepal.GpsTrackCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.Model_LetterTrace_Node;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.location.LocationChooseCarCallback;
import com.cvnavi.logistics.i51ehang.app.callback.manager.LocationChooseCarCallBackManager;
import com.cvnavi.logistics.i51ehang.app.callback.recycleview.OnItemClickLitener;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.activity.BaseSwipeBackActivity;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.cvnavi.logistics.i51ehang.app.widget.myscrollview.MyScrollView;
import com.github.mikephil.charting.charts.LineChart;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:29
*描述：GPS 轨迹
************************************************************************************/


public class EmployeeGpsTrackActivity extends BaseSwipeBackActivity implements OnItemClickLitener, LocationChooseCarCallback, DriverCarLineAdapter.TaskLineLookPicListener {

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.scanning_et)
    TextView scanningEt;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.bg_ll)
    LinearLayout bgLl;
    @BindView(R.id.history_num_tv)
    TextView historyNumTv;
    @BindView(R.id.clear_tv)
    TextView clearTv;
    @BindView(R.id.query_rl)
    RelativeLayout queryRl;
    @BindView(R.id.history_rv)
    RecyclerView historyRv;
    @BindView(R.id.history_ll)
    LinearLayout historyLl;
    @BindView(R.id.mile_tv)
    TextView mileTv;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.location_iv)
    ImageView locationIv;
    @BindView(R.id.location_info_tv)
    TextView locationInfoTv;
    @BindView(R.id.location_rl)
    RelativeLayout locationRl;
    @BindView(R.id.doc_iv)
    ImageView docIv;
    @BindView(R.id.doc_info_tv)
    TextView docInfoTv;
    @BindView(R.id.letter_oid_rl)
    RelativeLayout letterOidRl;
    @BindView(R.id.flag_iv)
    ImageView flagIv;
    @BindView(R.id.flag_tv)
    TextView flagTv;
    @BindView(R.id.line_rl)
    RelativeLayout lineRl;
    @BindView(R.id.main_iv)
    ImageView mainIv;
    @BindView(R.id.main_tv)
    TextView mainTv;
    @BindView(R.id.main_tel_tv)
    TextView mainTelTv;
    @BindView(R.id.main_rl)
    RelativeLayout mainRl;
    @BindView(R.id.other_iv)
    ImageView otherIv;
    @BindView(R.id.other_tv)
    TextView otherTv;
    @BindView(R.id.other_tel_tv)
    TextView otherTelTv;
    @BindView(R.id.other_rl)
    RelativeLayout otherRl;
    @BindView(R.id.empty_ll)
    LinearLayout emptyLl;
    @BindView(R.id.schedu_time_tv)
    TextView scheduTimeTv;
    @BindView(R.id.BoxCarCode_tv)
    TextView BoxCarCodeTv;
    @BindView(R.id.place_tv)
    TextView placeTv;
    @BindView(R.id.schedu_ll)
    LinearLayout scheduLl;
    @BindView(R.id.all_sl)
    MyScrollView allSl;
    @BindView(R.id.bj_ll)
    LinearLayout bjLl;
    @BindView(R.id.lc_ll)
    LinearLayout lcLl;
    @BindView(R.id.gj_ll)
    LinearLayout gjLl;
    @BindView(R.id.gps_location_rl)
    RelativeLayout gpsLocationRl;

    /**********
     * 变量初始化
     *************/

    private List<GpsTrackCarInfo> dataList = new ArrayList<>();
    private GetAppLoginResponse loginInfo;
    private String userTel;
    private String Company_Oid;
    private GpsCarHistoryTrackAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_gps);
        ButterKnife.bind(this);
        LocationChooseCarCallBackManager.newStance().add(this);
        loginInfo = MyApplication.getInstance().getLoginInfo();
        rightTv.setVisibility(View.GONE);
        dataList = checkInfoListFromDb();
        if (dataList != null && dataList.size() >= 0) {
            historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), dataList.size()));
        } else {
            historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), 0));
        }
        historyRv.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new GpsCarHistoryTrackAdapter((Context) this, dataList);
        mAdapter.setOnItemClickLitener(this);
        historyRv.setAdapter(mAdapter);

    }


    /**
     * 从数据库获取前十条数据(根据手机号)
     */
    private List<GpsTrackCarInfo> checkInfoListFromDb() {
        if (loginInfo != null && loginInfo.DataValue != null && loginInfo.DataValue.User_Tel != null) {
            userTel = loginInfo.DataValue.User_Tel;
            Company_Oid = loginInfo.DataValue.Company_Oid;
            List<GpsTrackCarInfo> list = DataSupport.where("Extend_One = ?", userTel + Company_Oid).limit(10).find(GpsTrackCarInfo.class);
            if (list != null) {
                Collections.reverse(list);
            }
            return list;
        } else {
            return null;
        }
    }

    private void cleanDb() {
        if (userTel != null && dataList != null && dataList.size() > 0) {
            DialogUtils.showMessageDialog(this, "温馨提示", "确认删除历史记录?", "确定", "取消", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    if (closeType == CustomDialogListener.BUTTON_OK) {
                        DataSupport.deleteAll(GpsTrackCarInfo.class, "Extend_One = ?", userTel + Company_Oid);
                        historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), 0));
                        dataList.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        } else {
            DialogUtils.showWarningToast("已经为空！");
        }
    }

    /**
     * 将查到的数据保存到数据库
     * @param CarCode
     * @param CarCode_Key
     * @param Driver
     * @param Driver_Tel
     * @param Line_Oid
     */
    private void saveInfoToDb(String CarCode, String CarCode_Key, String Driver, String Driver_Tel, String Line_Oid) {
        if (userTel != null) {
            GpsTrackCarInfo carInfo = new GpsTrackCarInfo();
            carInfo.setCarCode(CarCode);
            carInfo.setCarCode_Key(CarCode_Key);
            carInfo.setDriver(Driver);
            carInfo.setDriver_Tel(Driver_Tel);
            carInfo.setLine_Oid(Line_Oid);
            carInfo.setUserTel(userTel);
            carInfo.setExtend_One(userTel + Company_Oid);
            carInfo.save();
            LogUtil.d("-->> save=" + carInfo.save());
        }
    }

    /**
     * 检查数据库中是否有该条数据
     * @return
     */
    private boolean dbHasInfo(String CarCode) {
        List<GpsTrackCarInfo> list = DataSupport.where("Extend_One = ?", userTel + Company_Oid).find(GpsTrackCarInfo.class);
        if (list != null && list.size() >= 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCarCode().equals(CarCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (dataList != null && dataList.get(position) != null) {
            GpsTrackCarInfo carInfo = dataList.get(position);
            MyFleetRecordActivity.start(EmployeeGpsTrackActivity.this, carInfo.getCarCode_Key());
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    @Override
    public void onLookPic(Model_LetterTrace_Node node) {

    }

    @Override
    public void OnMonitorLoadCarCode(mCarInfo mCarInfo) {
        if (mCarInfo != null) {
            //保存到数据库
            if (!dbHasInfo(mCarInfo.CarCode)) {
                //数据库中没有该条数据插入数据
                saveInfoToDb(mCarInfo.CarCode, mCarInfo.CarCode_Key, mCarInfo.Driver, mCarInfo.Driver_Tel, mCarInfo.Line_Oid);
            }

            MyFleetRecordActivity.start(EmployeeGpsTrackActivity.this, mCarInfo.CarCode_Key);
            /****刷新数据,显示****/
            dataList = checkInfoListFromDb();
            mAdapter.setList(dataList);
            mAdapter.notifyDataSetChanged();
            if (dataList != null && dataList.size() >= 0) {
                historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), dataList.size()));
            } else {
                historyNumTv.setText(String.format(Utils.getResourcesString(R.string.my_history_num), 0));
            }
        }
    }

    @Override
    public void OnHistorLoadCarCode(mCarInfo mCarInfo) {

    }

    @OnClick({R.id.back_llayout, R.id.right_tv, R.id.clear_tv, R.id.bj_ll, R.id.lc_ll, R.id.gj_ll, R.id.scanning_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.scanning_et:
                showActivity(this, DriverCarTreeListActivity.class);
                break;
            case R.id.clear_tv:
                cleanDb();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationChooseCarCallBackManager.newStance().remove(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}