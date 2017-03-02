package com.cvnavi.logistics.i51ehang.app.activity.employee.home.gps;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.MyFleetRecordActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.BaiduMapUtils;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.eventbus.TimeEvent;
import com.cvnavi.logistics.i51ehang.app.bean.model.gpstrack.GetGpsTrackResponse;
import com.cvnavi.logistics.i51ehang.app.bean.model.gpstrack.GpsTrackDeatilInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetHistoryLocusRequest;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DateUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.view.MySeekBar;

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

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午3:31
*描述：轨迹播放
************************************************************************************/

public class GpsTrackPlayFragment extends BaseFragment {

    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.seebar)
    MySeekBar seebar;
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
    private BaiduMap mBaiduMap;
    private Polyline mPolyline;
    private Marker mMoveMarker;

    private final String STARTIME = " 00:00";
    private final String ENDTIME = " 23:59";
    private ArrayList<LatLng> srcList;
    private boolean isFlag = false;

    // 地图大头针
    BitmapDescriptor start = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_nav_start);
    BitmapDescriptor end = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_nav_end);

    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private int TIME_INTERVAL = 80;
    private double DISTANCE = 0.00001;
    private static final int Speed = 4;//越大越慢
    private float scaleMap = 6.5f;//地图缩放级别
    private int index = 0;//当前的节点（LIst）
    private int stopIndex;//停止的节点

    private String startTime;
    private String endTime;
    private SweetAlertDialog loadingDialog = null;
    private boolean loadSucc = false;

    public static GpsTrackPlayFragment getInstance() {
        return new GpsTrackPlayFragment();
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
        startTv.setText(DateUtil.strOldFormat2NewFormat(time.getStartTime(), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));
        endTv.setText(DateUtil.strOldFormat2NewFormat(time.getEndTime(), DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));
        requestData(time.getStartTime(), time.getEndTime());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gps_track_play, container, false);
        ButterKnife.bind(this, view);
        srcList = new ArrayList<>();
        bmapView.showZoomControls(false);
        initSeekBar();
        return view;
    }

    private void initSeekBar() {
        startTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + STARTIME;
        endTime = DateUtil.getNowTime(DateUtil.FORMAT_YMD) + ENDTIME;
        startTv.setText(DateUtil.strOldFormat2NewFormat(startTime, DateUtil.FORMAT_YMDHM, DateUtil.FORMAT_MDHM));
        endTv.setText(DateUtil.getNowTime(DateUtil.FORMAT_MDHM));
        //初始化速度,默认值
        changeSpeed(0);
        //初始化view的颜色
        tv05.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
        tv1.setTextColor(Utils.getResourcesColor(R.color.color_000000));
        tv2.setTextColor(Utils.getResourcesColor(R.color.color_000000));
        tv4.setTextColor(Utils.getResourcesColor(R.color.color_000000));
    }

    /**
     * 请求数据
     * 一个带经纬度的list，可能会有空数据需要筛选一下
     * 直接去除空数据返回一个新的 list
     *
     * @param startTime
     * @param endTime
     */
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
                GetGpsTrackResponse analysisRequest = GsonUtil.newInstance().fromJson(response, GetGpsTrackResponse.class);
                Message msg = Message.obtain();
                if (analysisRequest.Success) {
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = analysisRequest.DataValue;
                    handData.sendMessage(msg);
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                    handData.sendMessage(msg);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                handData.sendMessage(msg);
            }
        });
    }

    /**
     * 处理消息信息
     */
    private Handler handData = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            loadingDialog.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    List<GpsTrackDeatilInfo> datalist = (List<GpsTrackDeatilInfo>) msg.obj;
                    if (datalist != null && datalist.size() > 0) {
                        MLog.print("-->> size = " + datalist.size());
                        canvas(datalist);
                    } else {
                        DialogUtils.showNormalToast("暂无轨迹!");
                    }
                    loadSucc = true;
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

    /**
     * 根据数据画地图界面
     * 画界面
     */
    private void canvas(List<GpsTrackDeatilInfo> datalist) {
        if (srcList != null && srcList.size() > 0 && mBaiduMap != null) {
            srcList.clear();
            mBaiduMap.clear();
            index = 0;
        } else {
            mBaiduMap = bmapView.getMap();
            mBaiduMap.clear();
        }

        if (datalist != null && datalist.size() > 0) {
            //获取经纬度List过滤掉假数据
            for (GpsTrackDeatilInfo latLng : datalist) {
                if (!TextUtils.isEmpty(latLng.getMapLatitude()) && !TextUtils.isEmpty(latLng.getMapLongitude())) {
                    double Blat = Double.valueOf(latLng.getMapLatitude());
                    double Blnt = Double.valueOf(latLng.getMapLongitude());
                    srcList.add(new LatLng(Blat, Blnt));
                }
            }
        }

        if (srcList == null || (srcList != null && srcList.size() <= 0)) {
            return;
        }

        setZoom(srcList);

        //画线
        drawPolyLine();


        palyAndStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFlag) {
                    if (index == srcList.size() - 1) {
                        mBaiduMap.clear();
                        index = 0;
                        drawPolyLine();

                    }
                    palyAndStop.setImageResource(R.drawable.track_stop);
                    myOperateHander.sendEmptyMessage(1);
                } else {
                    palyAndStop.setImageResource(R.drawable.track_play);
                    myOperateHander.sendEmptyMessage(2);
                }
            }
        });
        seebar.setMax(srcList.size() - 1);
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
        int mid = srcList.size();
//        builder.target(srcList.get(mid / 2));
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


    /**
     * 先绘制路线，标记起始坐标点
     */
    private void drawPolyLine() {

        PolylineOptions polylineOptions = new PolylineOptions().points(srcList).width(10).color(Utils.getResourcesColor(R.color.track_line_color));

        mPolyline = (Polyline) mBaiduMap.addOverlay(polylineOptions);
        OverlayOptions markerOptions;

        //画开始
        MarkerOptions moo1 = new MarkerOptions().position(srcList.get(0)).icon(start).zIndex(9);
        mBaiduMap.addOverlay(moo1);

        //画结束
        MarkerOptions moo = new MarkerOptions().position(srcList.get(srcList.size() - 1)).icon(end).zIndex(9);
        mBaiduMap.addOverlay(moo);

        //旋转角度
        markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.point_location)).position(srcList.get(0))
                .rotate((float) getAngle(0));
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
    }

    /**
     * 根据点获取图标转的角度
     */
    private double getAngle(int startIndex) {
        if (mPolyline == null) {
            return 180;
        }

        if ((startIndex + 1) >= mPolyline.getPoints().size()) {
            throw new RuntimeException("index out of bonds");
        }
        LatLng startPoint = mPolyline.getPoints().get(startIndex);
        LatLng endPoint = mPolyline.getPoints().get(startIndex + 1);
        return getAngle(startPoint, endPoint);
    }

    /**
     * 根据两点算取图标转的角度
     */
    private double getAngle(LatLng fromPoint, LatLng toPoint) {
        if (fromPoint == null || toPoint == null) {
            return 180;
        }

        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 90;
        return angle;
    }

    /**
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {
        if (point == null) {
            return Double.MAX_VALUE;
        }

        double interception = point.latitude - slope * point.longitude;
        return interception;
    }

    /**
     * 算斜率
     */
    private double getSlope(LatLng fromPoint, LatLng toPoint) {

        if (fromPoint == null || toPoint == null) {
            return Double.MAX_VALUE;
        }

        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;
    }


    @Override
    public void onResume() {
        super.onResume();
        bmapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        palyAndStop.setImageResource(R.drawable.track_play);
        Message message = new Message();
        message.what = 2;
        myOperateHander.sendMessage(message);
        bmapView.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bmapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bmapView.onDestroy();
        if (mBaiduMap != null) {
            mBaiduMap.clear();
        }
    }

    /**
     * 计算x方向每次移动的距离
     */
    private double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    /**
     * 开始绘制
     */
    private void drawAnimationRoute() {
        if (isFlag) {
            if (stopIndex != 0) {
                index = stopIndex;
                stopIndex = 0;
            }
            runLine(index);
            index++;
            if (index != srcList.size() - 1) {
                drawAnimationRoute();
            } else {
                Message message = new Message();
                message.what = 3;
                myOperateHander.sendMessage(message);
            }
        } else {
            stopIndex = index;
        }
    }


    /**
     * 可以从任意的Pos开始跑
     */
    private void runLine(int fromPos) {
        if (mMoveMarker != null) {
            mMoveMarker.remove();
        }

        OverlayOptions markerOptions;

        if (srcList != null && srcList.get(fromPos) != null && mBaiduMap != null) {
            markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.point_location)).position(srcList.get(fromPos))
                    .rotate((float) getAngle(fromPos)).animateType(MarkerOptions.MarkerAnimateType.none);
            mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);
        }

        if (srcList != null && srcList.get(fromPos) != null && srcList.get(fromPos + 1) != null) {
            final LatLng startPoint = srcList.get(fromPos);
            final LatLng endPoint = srcList.get(fromPos + 1);

            if (mMoveMarker != null) {
                mMoveMarker.setPosition(startPoint);
            }

            if (bmapView == null || startPoint == null || endPoint == null || mMoveMarker == null) {
                return;
            }

            mMoveMarker.setRotate((float) getAngle(startPoint,
                    endPoint));


            /**
             * 短距离让小车平滑行走
             *
             */

            //斜率
            double slope = getSlope(startPoint, endPoint);
            // 是不是正向的标示

            boolean isReverse = (startPoint.latitude > endPoint.latitude);
            //根据点和斜率算取截距
            double intercept = getInterception(slope, startPoint);
            double xMoveDistance = isReverse ? getXMoveDistance(slope) :
                    -1 * getXMoveDistance(slope);

            for (double j = startPoint.latitude; !((j > endPoint.latitude) ^ isReverse);
                 j = j - xMoveDistance) {
                if (!isFlag) {
                    return;
                }

                LatLng latLng = null;
                if (slope == Double.MAX_VALUE) {
                    latLng = new LatLng(j, startPoint.longitude);
                } else {
                    latLng = new LatLng(j, (j - intercept) / slope);
                }


                final LatLng finalLatLng = latLng;

                if (bmapView == null) {
                    return;
                }
                if (mMoveMarker != null) {
                    mMoveMarker.setPosition(finalLatLng);
                }
                try {
                    Thread.sleep(TIME_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            seebar.setProgress(index + 1);
        }
    }


    private Handler myOperateHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    isFlag = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            drawAnimationRoute();
                        }
                    }).start();
                    break;
                case 2:
                    isFlag = false;
                    break;
                case 3:
                    isFlag = false;
                    palyAndStop.setImageResource(R.drawable.track_play);
                    break;
            }
        }
    };

    @OnClick({R.id.ll_1, R.id.ll_2, R.id.ll_3, R.id.ll_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_1:
                changeSpeed(0);
                tv05.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                tv1.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv2.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv4.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                break;
            case R.id.ll_2:
                changeSpeed(1);
                tv05.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv1.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                tv2.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv4.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                break;
            case R.id.ll_3:
                changeSpeed(2);
                tv05.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv1.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv2.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                tv4.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                break;
            case R.id.ll_4:
                changeSpeed(4);
                tv05.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv1.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv2.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                tv4.setTextColor(Utils.getResourcesColor(R.color.colorPrimary));
                break;
        }
    }

    /**
     * 改变速度
     *
     * @param num
     */
    private void changeSpeed(int num) {
        if (num == 0) {
            TIME_INTERVAL = 2 * Speed;
        } else {
            TIME_INTERVAL = Speed;
            TIME_INTERVAL = TIME_INTERVAL / num;
        }
    }

    private void createList() {
        for (int i = 0; i < latlngs.length; i++) {
            srcList.add(latlngs[i]);
        }
    }

    private static final LatLng[] latlngs = new LatLng[]{
            new LatLng(40.055826, 116.307917),
            new LatLng(40.055916, 116.308455),
            new LatLng(40.055967, 116.308549),
            new LatLng(40.056014, 116.308574),
            new LatLng(40.056440, 116.308485),
            new LatLng(40.056816, 116.308352),
            new LatLng(40.057997, 116.307725),
            new LatLng(40.058022, 116.307693),
            new LatLng(40.058029, 116.307590),
            new LatLng(40.057913, 116.307119),
            new LatLng(40.057850, 116.306945),
            new LatLng(40.057756, 116.306915),
            new LatLng(40.057225, 116.307164),
            new LatLng(40.056134, 116.307546),
            new LatLng(40.055879, 116.307636),
            new LatLng(40.055826, 116.307697)
//            new LatLng(32.544050387869, 117.70401156231),
//            new LatLng(32.544120468483, 117.70343449085),
//            new LatLng(32.544249215785, 117.70278780071),
//            new LatLng(32.54431556775, 117.70257894468),
//            new LatLng(32.544524819609, 117.70197213931),
//            new LatLng(32.5446104225, 117.70172348834),
//            new LatLng(32.54519700934, 117.69987315865),
//            new LatLng(32.545613453971, 117.69859001906),
//            new LatLng(32.546020081015, 117.69732682185),
//            new LatLng(32.546347800972, 117.69612327213),
//            new LatLng(32.546832264271, 117.69637785191),
//            new LatLng(32.547013585415, 117.69644756042),
//            new LatLng(32.549426042119, 117.69764239658),
//            new LatLng(32.549487901074, 117.69666737578),
//            new LatLng(32.549761358224, 117.69361322688),
//            new LatLng(32.549848325639, 117.69234976018),
//            new LatLng(32.549874195195, 117.69212096176),
//            new LatLng(32.550114705616, 117.69215060584),
//            new LatLng(32.550796363856, 117.69224034657),
//            new LatLng(32.552080231919, 117.69244956192),
//            new LatLng(32.554165774207, 117.69274833833),
//            new LatLng(32.555359588385, 117.69295737402),
//            new LatLng(32.55645197023, 117.69308673002),
//            new LatLng(32.557896285667, 117.69332540979),
//            new LatLng(32.558447331773, 117.69338505728),
//            new LatLng(32.558577123441, 117.69337517592),
//            new LatLng(32.558855649941, 117.69329558605),
//            new LatLng(32.559272334909, 117.69311655376),
//            new LatLng(32.559808841508, 117.69292746045),
//            new LatLng(32.559908123783, 117.69288775535),
//            new LatLng(32.565630095042, 117.69080620182),
//            new LatLng(32.56797464725, 117.68994921836),
//            new LatLng(32.571562027083, 117.68867299572),
//            new LatLng(32.573151765341, 117.68808460561),
//            new LatLng(32.573251413043, 117.68806475306),
//            new LatLng(32.573390919637, 117.68803483949),
//            new LatLng(32.575914703664, 117.6876943817),
//            new LatLng(32.580842946067, 117.68705281191),
//            new LatLng(32.581441159125, 117.68695274067),
//            new LatLng(32.581630928351, 117.68694267965),
//            new LatLng(32.583298826474, 117.68684153045),
//            new LatLng(32.587013436137, 117.68656907439),
//            new LatLng(32.587063404335, 117.68656907439),
//            new LatLng(32.587622483431, 117.68651885911),
//            new LatLng(32.587841292235, 117.68943350117),
//            new LatLng(32.58802922248, 117.69102556802),
//            new LatLng(32.58814535724, 117.69247821786),
//            new LatLng(32.588285600979, 117.69412975254),
//            new LatLng(32.588476876653, 117.69635817902),
//            new LatLng(32.589048647763, 117.70247671751),
//            new LatLng(32.589119681586, 117.70310364493),
//            new LatLng(32.589133827501, 117.70334250436),
//            new LatLng(32.589087511028, 117.70355154006),
//            new LatLng(32.589474925902, 117.70457632698),
//            new LatLng(32.589569231584, 117.70483503897),
//            new LatLng(32.589925006469, 117.70579029705),
//            new LatLng(32.590124492277, 117.70637742953),
//            new LatLng(32.590216515697, 117.70650678552),
//            new LatLng(32.590521333088, 117.70681508397),
//            new LatLng(32.590899387335, 117.70733241812),
//            new LatLng(32.591400339054, 117.70804890659),
//            new LatLng(32.591633588267, 117.70893499515),
//            new LatLng(32.591675948753, 117.70909426471),
//            new LatLng(32.591888511388, 117.70997056174),
//            new LatLng(32.592078106182, 117.71066773664),
//            new LatLng(32.592284888009, 117.71117563858),
//            new LatLng(32.592422843694, 117.71178334226),
//            new LatLng(32.592544676485, 117.7127299766),
//            new LatLng(32.592550456313, 117.71320841412),
//            new LatLng(32.592557833199, 117.71384639069),
//            new LatLng(32.592573575624, 117.7143648028),
//            new LatLng(32.592665748605, 117.71456413679),
//            new LatLng(32.592759138295, 117.71488312508),
//            new LatLng(32.592823933037, 117.71535177106),
//            new LatLng(32.593040371471, 117.71600977985),
//            new LatLng(32.593355826122, 117.7165979903),
//            new LatLng(32.593647932478, 117.71683711923),
//            new LatLng(32.593832047966, 117.71731582623),
//            new LatLng(32.593894712548, 117.71763499418),
//            new LatLng(32.594015858876, 117.71777459086),
//            new LatLng(32.594206285665, 117.71783423834),
//            new LatLng(32.594626607246, 117.71788373498),
//            new LatLng(32.59528663055, 117.71790304854),
//            new LatLng(32.595936002301, 117.71784268241)
    };
}
