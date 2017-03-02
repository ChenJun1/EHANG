package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetRecordActivity;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TrackEvent;
import com.cvnavi.logistics.i51ehang.app.bean.model.gpstrack.GetGpsTrackResponse;
import com.cvnavi.logistics.i51ehang.app.bean.model.gpstrack.GpsTrackDeatilInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetHistoryLocusRequest;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * Created by ${ChenJ} on 2016/8/9.
 */
public class MyFleetLocusFragment extends BaseDelayFragment {
    @BindView(R.id.locus_map)
    MapView locusMap;
    @BindView(R.id.seebar)
    SeekBar seebar;
    @BindView(R.id.start_tv)
    TextView startTv;
    @BindView(R.id.end_tv)
    TextView endTv;
    @BindView(R.id.paly_and_stop)
    ImageView palyAndStop;
    @BindView(R.id.play_stop)
    RelativeLayout playStop;
    @BindView(R.id.tv_05)
    TextView tv05;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_4)
    TextView tv4;

    private int speed = 1000;


    private BaiduMap mBaiduMap;
    private LocationClient mLocClient;
    boolean isFirstLoc = true; // 是否首次定位
    // 经纬度
    private MyFleetRecordActivity context;
    private List<GpsTrackDeatilInfo> list = new ArrayList<>();
    // 地图大头针
    BitmapDescriptor start = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_nav_start);
    BitmapDescriptor end = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_nav_end);

    // 覆盖物 坐标
    private List<MarkerOptions> listMarker;
    MapStatus ms;
    private Marker mMarker;// 标注
    BitmapDescriptor icon;// 标注图标
    Integer index = 0;
    boolean flag;
    List<LatLng> points = new ArrayList<LatLng>();// 所有路线点集合
    List<LatLng> points2 = new ArrayList<LatLng>();

    private String startTime;
    private String endTime;
    private SweetAlertDialog loadingDialog = null;
    private boolean loadSucc = false;


    public static MyFleetLocusFragment getInstance() {
        return new MyFleetLocusFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !loadSucc) {
            requestData(startTime, endTime);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setTime(TimeEvent time) {
        startTv.setText(time.getStartTime());
        endTv.setText(time.getEndTime());
        requestData(time.getStartTime(), time.getEndTime());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getListForTrack(TrackEvent trackEvent) {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fleet_locus, container, false);
        ButterKnife.bind(this, view);
        startTime = DateUtil.getBeforeOneDayTime();
        endTime = DateUtil.getCurDateStr(DateUtil.FORMAT_YMDHM);
        startTv.setText(startTime);
        endTv.setText(endTime);

        return view;
    }


    @Override
    protected void lazyLoad() {
//        init();
    }


    @OnClick(R.id.paly_and_stop)
    public void onClick() {
        resetOverlay(palyAndStop);
    }


    // 初始化
    public void initOverlay() {
        OverlayOptions ooPolyline = new PolylineOptions().width(5)
                .color(0xAAFF0000).points(points);
        mBaiduMap.addOverlay(ooPolyline);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(points.get(0));
        mBaiduMap.setMapStatus(u);

        //画开始
        icon = BitmapDescriptorFactory.fromResource(R.drawable.icon_nav_start);
        OverlayOptions ooA = new MarkerOptions().position(points.get(index))
                .icon(icon).draggable(true);
        mMarker = (Marker) (mBaiduMap.addOverlay(ooA));

        //画结束
        MarkerOptions moo = new MarkerOptions().position(points.get(points.size() - 1)).icon(end).zIndex(9);
        mBaiduMap.addOverlay(moo);

        if (points.size() < 2) {
            DialogUtils.showNormalToast("暂无轨迹信息！");
        } else {
            OverlayOptions oop = new PolylineOptions().width(8).color(Utils.getResourcesColor(R.color.darkslateblue)).points(points);
            mBaiduMap.addOverlay(oop);
        }
    }

    public void resetOverlay(View view) {
        if (!flag) {
            if (index == points.size()) {
                mBaiduMap.clear();
                index = 0;
                initOverlay();// 初始化

            }
            palyAndStop.setImageResource(R.drawable.track_stop);
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        } else {
            palyAndStop.setImageResource(R.drawable.track_play);
            Message message = new Message();
            message.what = 2;
            handler.sendMessage(message);
        }
    }

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                flag = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        start();
                    }
                }).start();
            }

            if (msg.what == 2) {
                flag = false;
            }
            if (msg.what == 3) {
                palyAndStop.setImageResource(R.drawable.track_play);
                points2.clear();
                flag = false;
                seebar.setProgress(0);
            }
            return false;
        }
    });


    // 画轨迹
    public void start() {
        if (flag) {
            if (mMarker != null) {
                mMarker.remove();
            }
            icon = BitmapDescriptorFactory.fromResource(R.drawable.startcar);
            OverlayOptions ooA = new MarkerOptions()
                    .position(points.get(index)).icon(icon).draggable(true);
            mMarker = (Marker) (mBaiduMap.addOverlay(ooA));
            if (points2.size() <= 1) {
                points2.add(points.get(index));
                points2.add(points.get(index + 1));
            } else {
                points2.add(points.get(index));
            }
            //设置中心点
            MapStatusUpdate u =
                    MapStatusUpdateFactory.newLatLng(points.get(index));
            mBaiduMap.setMapStatus(u);

            OverlayOptions s = new PolylineOptions().width(8)
                    .color(0xAA000000).points(points2);
            mBaiduMap.addOverlay(s);
            seebar.setProgress(index + 1);
            if (speed <= 0) {
                speed = 10;
            }

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            index++;
            if (index != points.size()) {
                start();
                seebar.setProgress(index + 1);
            } else {
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
                seebar.setProgress(0);
            }
        }
    }

    @Override
    public void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        if (locusMap != null) {
            locusMap.onPause();
        }
        palyAndStop.setImageResource(R.drawable.track_play);
        Message message = new Message();
        message.what = 2;
        handler.sendMessage(message);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (locusMap != null) {
            locusMap.onDestroy();
        }
        if (mLocClient != null) {
            mLocClient.stop();
        }
        start.recycle();
        end.recycle();

        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Override
    public void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        if (locusMap != null) {
            locusMap.onResume();
        }
        super.onResume();
    }

    /**
     * 刷新画面
     */
    public void refresh() {
//        initOverlay();
    }

    @OnClick({R.id.tv_05, R.id.tv_1, R.id.tv_2, R.id.tv_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_05:
                checkSpeed(50);
                tv05.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                tv1.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv2.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv4.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                break;
            case R.id.tv_1:
                checkSpeed(100);
                tv05.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv1.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                tv2.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv4.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                break;
            case R.id.tv_2:
                checkSpeed(150);
                tv05.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv1.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv2.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                tv4.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                break;
            case R.id.tv_4:
                checkSpeed(200);
                tv05.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv1.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv2.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv4.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                break;
        }
    }


    private void checkSpeed(int offset) {
        if (speed < 0) {
            DialogUtils.showWarningToast("速度已经最快!");
            return;
        } else {
            speed = speed - offset;
        }

    }


    private void requestData(String startTime, String endTime) {

        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        }

        loadingDialog.show();
        DataRequestBase requestBase = new DataRequestBase();
        final GetHistoryLocusRequest request = new GetHistoryLocusRequest();
        request.StarTime = startTime;
        request.EndTime = endTime;
        request.KeyList = ((MyFleetRecordActivity) getActivity()).getCarKey();

        requestBase.DataValue = JsonUtils.toJsonData(request);
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;

        final JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);
        VolleyManager.newInstance().PostJsonRequest("TAG", LPSService.GetHistoryLocus_Request_Url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->>onResponse============>>" + response.toString());
                GetGpsTrackResponse analysisRequest = GsonUtil.newInstance().fromJson(response, GetGpsTrackResponse.class);
                Message msg = Message.obtain();
                if (analysisRequest.Success) {
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = analysisRequest.DataValue;
                    mHandler.sendMessage(msg);
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                    mHandler.sendMessage(msg);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        });

    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingDialog.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    List<GpsTrackDeatilInfo> datalist = (List<GpsTrackDeatilInfo>) msg.obj;
                    LogUtil.d("-->> track size = " + datalist.size());
                    if (datalist != null) {
                        canvas(datalist);
                        loadSucc = true;
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast("暂无轨迹!");
                    loadSucc = false;
                    break;

                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), Utils.getResourcesString(R.string.request_error));
                    break;
            }
        }
    };

    private void canvas(List<GpsTrackDeatilInfo> datalist) {
        if (list != null && list.size() > 0 && mBaiduMap != null) {
            list.clear();
            points.clear();
            mBaiduMap.clear();
            index = 0;
            points2.clear();
            speed = 1000;
        } else {
            mBaiduMap = locusMap.getMap();
            mBaiduMap.clear();
        }

        list.addAll(datalist);

        if (list != null && list.size() > 0) {
            //获取经纬度List过滤掉假数据
            for (GpsTrackDeatilInfo latLng : list) {
                if (!TextUtils.isEmpty(latLng.getMapLatitude()) && !TextUtils.isEmpty(latLng.getMapLongitude())) {
                    double Blat = Double.valueOf(latLng.getMapLatitude());
                    double Blnt = Double.valueOf(latLng.getMapLongitude());
                    points.add(new LatLng(Blat, Blnt));
                }
            }
        } else {
            //没有数据直接弹提示
            DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), "暂无轨迹!");
            return;
        }

        // 设置地图缩放级别
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(10f);
        mBaiduMap.setMapStatus(msu);

        LogUtil.d("-->> point.size = " + points.size());
        // 移动地图中心到此点
        LatLng l = new LatLng(points.get(0).latitude, points.get(0).longitude);
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(l);
        mBaiduMap.setMapStatus(u);

        // 画轨迹
        initOverlay();

        seebar.setMax(points.size());
        seebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
                index = seebar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
            }
        });
    }

//    private void drawingLocus() {
//        mBaiduMap.clear();
//        List<LatLng> points = new ArrayList<LatLng>();
//        listMarker = new ArrayList<>();
//        Boolean flag = true;
//
//        for (int i = 0; i < list.size(); i++) {
//            if (!TextUtils.isEmpty(list.get(i).BLat) && !TextUtils.isEmpty(list.get(i).BLng)) {
//                double Blat = Double.valueOf(list.get(i).BLat);
//                double Blnt = Double.valueOf(list.get(i).BLng);
//                LatLng latlnt = new LatLng(Blat, Blnt);
//                points.add(latlnt);
//                if (flag) {
//                    flag = false;
//                    MarkerOptions moo = new MarkerOptions().position(latlnt).icon(start).zIndex(9);
//                    mBaiduMap.addOverlay(moo);
//                }
//            }
//        }
//
//        for (int i = list.size() - 1; i >= 0; i--) {
//            if (!TextUtils.isEmpty(list.get(i).BLat) && !TextUtils.isEmpty(list.get(i).BLng)) {
//                double Blat = Double.valueOf(list.get(i).BLat);
//                double Blnt = Double.valueOf(list.get(i).BLng);
//                LatLng latlnt = new LatLng(Blat, Blnt);
//                MarkerOptions moo = new MarkerOptions().position(latlnt).icon(end).zIndex(9);
//                mBaiduMap.addOverlay(moo);
//                break;
//            }
//        }
//
//        if (points.size() < 2) {
//            DialogUtils.showNormalToast("暂无轨迹信息！");
//
//        } else {
//            OverlayOptions oop = new PolylineOptions().width(8).color(Utils.getResourcesColor(R.color.darkslateblue)).points(points);
//            mBaiduMap.addOverlay(oop);
//        }
//
//    }


//    @Override
//    public void onMapLoaded() {
////        ms = new MapStatus.Builder().zoom(9).build();
////        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));
//    }

//    /**
//     * 初始化定位
//     */
//    public void intiLocation() {
//        // 隐藏缩放控件
//        // 开启定位图层
//        mBaiduMap.setMyLocationEnabled(true);
//        // 定位初始化
//        mLocClient = new LocationClient(getActivity());
//        mLocClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setOpenGps(true); // 打开gps
//        option.setCoorType("bd09ll"); // 设置坐标类型
//        option.setScanSpan(1000);
//        mLocClient.setLocOption(option);
//        mLocClient.start();
////        mapBool = true;
//    }


    /**
     //     * 定位SDK监听函数
     //     */
//    public class MyLocationListenner implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            // map view 销毁后不在处理新接收的位置
//            if (location == null || locusMap == null) {
//                return;
//            }
//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                    // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(latitude).longitude(longitude)
//                    .build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(latitude, longitude);
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.f - 5);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory
//                        .newMapStatus(builder.build()));
//            }
//
//        }
//
//        public void onReceivePoi(BDLocation poiLocation) {
//
//        }
//    }
}
