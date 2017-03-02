package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetmonitor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetMileStatisticActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetRecordActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetalarminfo.MyFleetAlarmInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.home.car.CarMonitorSelectCarActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.BaiduMapUtils;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.employee.home.carfleet.CarFleetMonitorBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetMyCarFleetResponse;
import com.cvnavi.logistics.i51ehang.app.config.EmployeeService;
import com.cvnavi.logistics.i51ehang.app.utils.BitmapUtil;
import com.cvnavi.logistics.i51ehang.app.utils.ContextUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.ObjectAnimationUtils;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * Created by ${ChenJ} on 2016/8/8.
 */
public class MyFleetMonitorMapActivity extends BaseActivity implements BaiduMap.OnMapLoadedCallback {
    private static final String TAG = "MyFleetMonitorMapActivity";
    private static final String KEY_ORGLIST = "ORGLIST";
    private static final int KEY_Reques = 1000;
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
    @BindView(R.id.monitoring_map)
    MapView monitoringMap;
    @BindView(R.id.CarCode_tv)
    TextView CarCodeTv;
    @BindView(R.id.car_status_tv)
    TextView carStatusTv;
    @BindView(R.id.addr_tv)
    TextView addrTv;
    @BindView(R.id.driver)
    TextView driver;
    @BindView(R.id.driver_name_tv)
    TextView driverNameTv;
    @BindView(R.id.driver_tel_tv)
    TextView driverTelTv;
    @BindView(R.id.alarm_ll)
    LinearLayout alarmLl;
    @BindView(R.id.mileage_ll)
    LinearLayout mileageLl;
    @BindView(R.id.locus_ll)
    LinearLayout locusLl;
    @BindView(R.id.layout_ll)
    LinearLayout layoutLl;
    @BindView(R.id.data_search_text)
    TextView dataSearchText;
    @BindView(R.id.data_search_img)
    ImageView dataSearchImg;
    @BindView(R.id.data_search_layout)
    LinearLayout dataSearchLayout;
    @BindView(R.id.arrow)
    LinearLayout arrow;
    @BindView(R.id.root_select)
    RelativeLayout rootSelect;

    private ClusterManager<MyItem> mClusterManager;//聚合管理器

    private boolean isClur = false;//是否聚合
    private BaiduMap mBaiduMap;
    private MapStatus ms;//标注状态点
    private static final float maxZoom = 18;
    private List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> list;//添加到聚合点list的
    private List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> dataList;
    private List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> carList;//筛选空数据后的list
    private List<CarFleetMonitorBean.DataValueBean> flagList;
    private List<CarFleetMonitorBean.DataValueBean> selectFlagList;//筛选过后无假数据的list


    private GetMyCarFleetResponse.DataValueBean.MCarInfoListBean bean;//标注物实体类
    private List<Marker> markerList = null;//添加标注物到地图列表
    private List<Marker> flagMarkList = null;//添加标注物到地图列表
    private List<LatLng> locationList;

    public static void start(Context context, List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> list) {
        Intent starter = new Intent(context, MyFleetMonitorMapActivity.class);
        starter.putExtra(KEY_ORGLIST, (Serializable) list);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_monitor);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        list = new ArrayList<>();
        dataList = new ArrayList<>();
        carList = new ArrayList<>();
        markerList = new ArrayList<>();
        flagMarkList = new ArrayList<>();
        flagList = new ArrayList<>();
        selectFlagList = new ArrayList<>();
        locationList = new ArrayList<>();

        titleTv.setText(Utils.getResourcesString(R.string.fleet_monitor));
        dataSearchText.setText("选择车辆");
        rootSelect.setBackgroundColor(0xff51575d);
        operationBtn.setText("取消聚合");
        dataSearchText.setTextColor(0xffffffff);
        operationBtn.setVisibility(View.VISIBLE);

        arrow.setVisibility(View.VISIBLE);
        dataSearchLayout.setVisibility(View.GONE);

        if (getIntent().getSerializableExtra(KEY_ORGLIST) != null) {
            List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean> data = (List<GetMyCarFleetResponse.DataValueBean.MCarInfoListBean>) getIntent().getSerializableExtra(KEY_ORGLIST);
            if (data != null) {
                list.addAll(data);
                dataList.addAll(data);
            }
        }

        //筛选有经纬度的数据 得到一个新的carList；
        if (dataList != null && dataList.size() > 0) {
            for (int i = 0; i < dataList.size(); i++) {
                if (!TextUtils.isEmpty(dataList.get(i).getBLat()) && !TextUtils.isEmpty(dataList.get(i).getBLng())) {
                    carList.add(dataList.get(i));
                }
            }
            getOrgGps();
        }
//        else {
//            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetMonitorMapActivity.this, Utils.getResourcesString(R.string.not_monitor_info), new CustomDialogListener() {
//                @Override
//                public void onDialogClosed(int closeType) {
//                    if (closeType == CustomDialogListener.BUTTON_OK) {
//                        MyFleetMonitorMapActivity.this.finish();
//                    }
//                }
//            });
//            return;
//        }
    }

    /**
     * 获取机构列表
     */
    private void getOrgGps() {
        showLoading();
        DataRequestBase requestBase = new DataRequestBase();
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        requestBase.Org_Key = MyApplication.getInstance().getLoginInfo().DataValue.Org_Key;
        requestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        VolleyManager.newInstance().PostJsonRequest(EmployeeService.OrgGPS_Url, EmployeeService.OrgGPS_Url, GsonUtil.newInstance().toJson(requestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                dissLoading();
                MLog.json(jsonObject.toString());
                CarFleetMonitorBean info = GsonUtil.newInstance().fromJson(jsonObject, CarFleetMonitorBean.class);
                if (info != null && info.isSuccess() && info.getDataValue() != null && info.getDataValue().size() > 0) {
                    flagList.addAll(info.getDataValue());
                }

                initMap(20f);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dissLoading();
            }
        });
    }

    /**
     * 0=行驶中
     * 1=停止
     * 2=信号中断
     *
     * @param mCarInfoListBean
     */
    private void SetViewData(GetMyCarFleetResponse.DataValueBean.MCarInfoListBean mCarInfoListBean) {
        String CarStatus = "";
        if (!TextUtils.isEmpty(mCarInfoListBean.getCarStatus_Oid())) {
            switch (Integer.parseInt(mCarInfoListBean.getCarStatus_Oid())) {
                case 1:
                    CarStatus = "空闲中";
                    break;
                case 2:
                    CarStatus = "已计划";
                    break;
                case 3:
                case 0:
                    CarStatus = "在途中";
                    break;
                case 4:
                    CarStatus = "全部";
                    break;
            }
        }

        SetViewValueUtil.setTextViewValue(CarCodeTv, mCarInfoListBean.getCarCode());
        SetViewValueUtil.setTextViewValue(carStatusTv, CarStatus);
        SetViewValueUtil.setTextViewValue(addrTv, mCarInfoListBean.getCHS_Address());
        SetViewValueUtil.setTextViewValue(driverNameTv, mCarInfoListBean.getDriver());
        SetViewValueUtil.setTextViewValue(driverTelTv, mCarInfoListBean.getDriver_Tel());
        layoutLl.setVisibility(View.VISIBLE);
        ObjectAnimationUtils.showScaleXScalexY(layoutLl);

    }

    @Override
    public void onMapLoaded() {
        ms = new MapStatus.Builder().zoom(4.81f).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
    }

    /**
     * 向地图添加Marker点
     */
    public void addMarkers() {
        List<MyItem> items = new ArrayList<MyItem>();
        for (GetMyCarFleetResponse.DataValueBean.MCarInfoListBean mCarInfoListBean : list) {
            if (!TextUtils.isEmpty(mCarInfoListBean.getBLat()) && !TextUtils.isEmpty(mCarInfoListBean.getBLng()) && !TextUtils.isEmpty(mCarInfoListBean.getCarCode())) {
                LatLng llA = new LatLng(Double.valueOf(mCarInfoListBean.getBLat()), Double.valueOf(mCarInfoListBean.getBLng()));
                int rotaion = 0;
                if (!TextUtils.isEmpty(mCarInfoListBean.getDirection())) {
                    rotaion = Integer.parseInt(mCarInfoListBean.getDirection());
                    if (rotaion >= 180 && rotaion < 360) {
                        rotaion = rotaion - 360;
                    }

                } else {
                    rotaion = 360;
                }

                items.add(new MyItem(llA, mCarInfoListBean.getCarCode(), rotaion));
                locationList.add(llA);
            }
        }

        mClusterManager.addItems(items);

    }

    /**
     * 直接在地图上 添加标注覆盖物
     */
    private void addOverlay(float zoom) {
        if (mBaiduMap != null) {
            mBaiduMap.clear();
            markerList.clear();
            flagMarkList.clear();
            selectFlagList.clear();
            mClusterManager.clearItems();
        }

        //添加marker点到地图上
        for (GetMyCarFleetResponse.DataValueBean.MCarInfoListBean mCarInfoListBean : carList) {
            LatLng llA = new LatLng(Double.valueOf(mCarInfoListBean.getBLat()), Double.valueOf(mCarInfoListBean.getBLng()));
//            float d = 0;
//            if (!TextUtils.isEmpty(mCarInfoListBean.getDirection())) {
//                d = 360 - Float.parseFloat(mCarInfoListBean.getDirection());
//            } else {
//                d = 180;
//            }  百度地图的旋转方式是逆时针方向[0,360]
//            MarkerOptions moo1 = new MarkerOptions().position(llA).icon(BitmapDescriptorFactory.fromResource(R.drawable.point_location)).zIndex(9).rotate(d);

            //返回的数据是 0-360,但是Marix旋转的角度范围是[-180，180]
            int rotaion = 0;
            if (!TextUtils.isEmpty(mCarInfoListBean.getDirection())) {
                rotaion = Integer.parseInt(mCarInfoListBean.getDirection());
                if (rotaion >= 180 && rotaion < 360) {
                    rotaion = rotaion - 360;
                }
            } else {
                rotaion = 270;
            }

            //让标注物旋转一定角度生成新的bitmap
            Bitmap srcBit = BitmapFactory.decodeResource(getResources(), R.drawable.point_location);
            Bitmap newBitmap = BitmapUtil.rotateBitmap(srcBit, rotaion);
            BitmapDescriptor bdA = BitmapDescriptorFactory
                    .fromBitmap(newBitmap);
            MarkerOptions moo1 = new MarkerOptions().position(llA).icon(bdA);
            Marker car = (Marker) mBaiduMap.addOverlay(moo1);
            car.setTitle("car");
            markerList.add(car);
        }

        /**
         * 添加小红旗
         */
        for (CarFleetMonitorBean.DataValueBean bean : flagList) {
            if (!TextUtils.isEmpty(bean.getLat()) && !TextUtils.isEmpty(bean.getLong())) {
                LatLng llA = new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLong()));
                MarkerOptions moo1 = new MarkerOptions().position(llA).icon(BitmapDescriptorFactory.fromResource(R.drawable.myfeet_flag)).zIndex(9);
                Marker flag = (Marker) mBaiduMap.addOverlay(moo1);
                flag.setTitle("flag");
                flagMarkList.add(flag);
                selectFlagList.add(bean);
            }

        }
        //设置焦点位置
        ms = new MapStatus.Builder().target(new LatLng(Double.valueOf(carList.get(0).getBLat()), Double.valueOf(carList.get(0).getBLng()))).zoom(zoom).build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

    }


    /**
     * 获取list中Pos值
     * <p>
     * <p>
     * 3 + 5
     *
     * @param marker
     * @return
     */
    private int getMarkerFromList(Marker marker) {
        if (markerList == null || markerList != null && markerList.size() < 0) {
            return 0;
        }

        for (int i = 0; i < markerList.size(); i++) {
            if (marker == markerList.get(i)) {
                if (i > carList.size() - 1) {
                    return 0;
                } else {
                    return i;
                }
            }
        }

        return 0;
    }


    /**
     * 获取list中Pos值
     *
     * @param marker
     * @return
     */
    private int getFlagMarkerFromList(Marker marker) {
        if (flagMarkList == null || flagMarkList != null && flagMarkList.size() < 0) {
            return 0;
        }

        for (int i = 0; i < flagMarkList.size(); i++) {
            if (marker == flagMarkList.get(i)) {
                if (i > selectFlagList.size() - 1) {
                    return 0;
                } else {
                    return i;
                }
            }
        }
        return 0;
    }


    /**
     * 弹出window
     *
     * @param mLatLng
     * @param Carcode
     */
    private void showStop(LatLng mLatLng, String Carcode) {
        View popu = View.inflate(this, R.layout.pop_myfleet_monitor, null);
        TextView title = (TextView) popu.findViewById(R.id.showpop_title_text);

        title.setText(Carcode.replaceAll("；", "；\n　　　"));
        title.setTextColor(Color.BLACK);

        InfoWindow.OnInfoWindowClickListener mListener = new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                mBaiduMap.hideInfoWindow();
            }
        };

        InfoWindow mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(popu), mLatLng, -80, mListener);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }


    @OnClick({R.id.back_llayout, R.id.driver_tel_tv, R.id.alarm_ll, R.id.mileage_ll, R.id.locus_ll, R.id.root_select, R.id.operation_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.driver_tel_tv:
                ContextUtil.callAlertDialog(driverTelTv.getText().toString(), MyFleetMonitorMapActivity.this);
                break;
            case R.id.alarm_ll:
                if (bean != null) {
                    MyFleetAlarmInfoActivity.start(this, bean.getCarCode_Key(), bean.getCarCode());
                }
                break;
            case R.id.mileage_ll:
                if (bean != null) {
                    MyFleetMileStatisticActivity.startActivity(this, bean.getCarCode_Key(), bean.getCarCode(), bean.getTSP_CarCode_Key());
                }
                break;
            case R.id.locus_ll:
                if (bean != null) {
                    MyFleetRecordActivity.start(this, bean.getCarCode_Key());
                }
                break;
//            case R.id.operation_btn:
//                MyFleetCarTreeListActivity.startActivity(this, KEY_Reques);
//                break;
            case R.id.root_select:
                CarMonitorSelectCarActivity.start(this, carList);
                break;
            case R.id.operation_btn:
                float zoomLevel = mBaiduMap.getMapStatus().zoom;
                if (isClur) {
                    if (zoomLevel == maxZoom) {
                        initMap(9);
                    } else {
                        initMap(zoomLevel);
                    }
                    operationBtn.setText("取消聚合");
                } else {
                    if (zoomLevel == maxZoom) {
                        addOverlay(9);
                    } else {
                        addOverlay(zoomLevel);
                    }
                    operationBtn.setText("聚合");
                }
                isClur = !isClur;
                layoutLl.setVisibility(View.GONE);
                break;
        }
    }

    private void initMap(float zoom) {
        mBaiduMap = monitoringMap.getMap();
        if (mBaiduMap != null) {
            mBaiduMap.clear();
            flagMarkList.clear();
            selectFlagList.clear();
            locationList.clear();
        }

        mBaiduMap.setOnMapLoadedCallback(this);
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
                layoutLl.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {

                return false;
            }
        });

        //设置当前界面的焦点
        if (carList != null && carList.size() > 0) {
            for (int i = carList.size() - 1; i >= 0; i--) {
                if (!TextUtils.isEmpty(carList.get(i).getBLat()) && !TextUtils.isEmpty(carList.get(i).getBLng())) {
                    ms = new MapStatus.Builder().target(new LatLng(Double.valueOf(carList.get(i).getBLat()), Double.valueOf(carList.get(i).getBLng()))).zoom(zoom).build();
                    break;
                }
            }
        } else {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetMonitorMapActivity.this, Utils.getResourcesString(R.string.not_monitor_info), new CustomDialogListener() {

                @Override
                public void onDialogClosed(int closeType) {
                    if (closeType == CustomDialogListener.BUTTON_OK) {
                        MyFleetMonitorMapActivity.this.finish();
                    }
                }
            });

        }
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));


        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(this, mBaiduMap);
        // 添加Marker点
        addMarkers();

        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        mBaiduMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.getMarkerCollection().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String title = marker.getTitle();
                for (GetMyCarFleetResponse.DataValueBean.MCarInfoListBean mCarInfoListBean : list) {
                    if (!TextUtils.isEmpty(mCarInfoListBean.getCarCode())) {
                        if (title.equals(mCarInfoListBean.getCarCode())) {
                            bean = mCarInfoListBean;
                            SetViewData(mCarInfoListBean);
//                            dataSearchText.setText(bean.getCarCode());
                            showStop(marker.getPosition(), title);
                        }
                    }
                }

                return false;
            }
        });

        mClusterManager.getClusterMarkerCollection().setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                return false;
            }
        });

        //设置marker点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (TextUtils.isEmpty(marker.getTitle())) {
                    return false;
                }

                if (marker.getTitle().equals("car")) {
                    //点击车
                    GetMyCarFleetResponse.DataValueBean.MCarInfoListBean mCarInfoListBean = carList.get(getMarkerFromList(marker));
                    if (mCarInfoListBean != null) {
                        SetViewData(mCarInfoListBean);
                        showStop(marker.getPosition(), mCarInfoListBean.getCarCode());
                    }
                } else if (marker.getTitle().equals("flag")) {
                    //点击机构
                    CarFleetMonitorBean.DataValueBean flag = selectFlagList.get(getFlagMarkerFromList(marker));
                    if (flag != null) {
                        layoutLl.setVisibility(View.GONE);
                        showStop(marker.getPosition(), flag.getOrg_Name());
                    }
                }
                return false;
            }
        });


        if (flagList != null && flagList.size() < 1) {
            return;
        }

        /**
         * 添加小红旗
         */
        for (CarFleetMonitorBean.DataValueBean bean : flagList) {
            if (!TextUtils.isEmpty(bean.getLat()) && !TextUtils.isEmpty(bean.getLong())) {
                //筛选数据并添加标注物
                LatLng llA = new LatLng(Double.valueOf(bean.getLat()), Double.valueOf(bean.getLong()));
                MarkerOptions moo1 = new MarkerOptions().position(llA).icon(BitmapDescriptorFactory.fromResource(R.drawable.myfeet_flag)).zIndex(9);
                Marker flag = (Marker) mBaiduMap.addOverlay(moo1);
                flag.setTitle("flag");
                flagMarkList.add(flag);
                selectFlagList.add(bean);
            }
        }


    }


    @Override
    protected void onPause() {
        monitoringMap.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        monitoringMap.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        monitoringMap.onDestroy();
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;
        private String mTitle;
        private int rotation;

        public MyItem(LatLng latLng, String Title, int rotation) {
            mPosition = latLng;
            mTitle = Title;
            this.rotation = rotation;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            Bitmap srcBit = BitmapFactory.decodeResource(getResources(), R.drawable.point_location);
//            Bitmap newBitmap = adjustPhotoRotation(srcBit, -90);
            Bitmap newBitmap = BitmapUtil.rotateBitmap(srcBit, rotation);
            return BitmapDescriptorFactory.fromBitmap(newBitmap);
        }

        @Override
        public String getTitle() {
            return mTitle;
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loactionPos(GetMyCarFleetResponse.DataValueBean.MCarInfoListBean infoListBean) {
        if (infoListBean != null && !TextUtils.isEmpty(infoListBean.getBLat()) && !TextUtils.isEmpty(infoListBean.getBLng())) {
            bean = infoListBean;
//            dataSearchText.setText(infoListBean.getCarCode());
            LatLng latLng = new LatLng(Double.parseDouble(infoListBean.getBLat()), Double.parseDouble(infoListBean.getBLng()));
            showStop(latLng, infoListBean.getCarCode());
            if (mBaiduMap != null) {
                MapStatus.Builder builder = new MapStatus.Builder().target(latLng).zoom(maxZoom);
                SetViewData(infoListBean);
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        } else {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(this, "无法定位该车!", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
        }
    }


    /**
     * 自动设置缩放级别
     */
    private void setZoom(ArrayList<LatLng> srcList) {
        if (srcList == null || srcList.size() <= 0) {
            return;
        }

        LatLng max = getMax(srcList);

        if (max == null) {
            return;
        }
        //缩放级别处理
        double distance = BaiduMapUtils.GetDistance(max.latitude, max.longitude, srcList.get(0).latitude, srcList.get(0).longitude);
        float zoomSize = BaiduMapUtils.getZoomSize(distance);

        LatLng latLngCenter = new LatLng((srcList.get(0).latitude + max.latitude) / 2D, (srcList.get(0).longitude + max.longitude) / 2D);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLngCenter);
        builder.zoom(zoomSize);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    /**
     * 获取最大值
     * 最小值
     *
     * @param srcList
     * @return
     */
    private LatLng getMax(ArrayList<LatLng> srcList) {
        if (srcList == null || srcList != null && srcList.size() < 2) {
            return null;
        }

        double max = BaiduMapUtils.GetDistance(srcList.get(0).latitude, srcList.get(0).longitude, srcList.get(1).latitude, srcList.get(1).longitude);
        LatLng maxLat = null;

        for (int i = 1; i < srcList.size(); i++) {
            double value = BaiduMapUtils.GetDistance(srcList.get(0).latitude, srcList.get(0).longitude, srcList.get(i).latitude, srcList.get(i).longitude);
            if (value > max) {
                max = value;
                maxLat = srcList.get(i);
            }
        }

        return maxLat;
    }


    private Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);

        return bm1;
    }


}
