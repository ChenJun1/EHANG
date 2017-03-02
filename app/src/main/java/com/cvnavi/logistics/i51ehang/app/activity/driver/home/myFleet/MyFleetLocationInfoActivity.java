package com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
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
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.myFleet.fleetalarminfo.MyFleetAlarmInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverAddCarSchedulingActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.GetCarGPSAggregate;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetCarGPSAggregateResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetMyCarFleetResponse;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.BitmapUtil;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.myscrollview.MyScrollView;
import com.github.mikephil.charting.charts.LineChart;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/17 上午10:19
*描述：定位信息(显示和未显示)
************************************************************************************/

public class MyFleetLocationInfoActivity extends BaseActivity implements InfoWindow.OnInfoWindowClickListener {
    private static final int REQUEST_CODE = 0X12;
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.add_iv)
    ImageView addIv;
    @BindView(R.id.add_ll)
    LinearLayout addLl;
    @BindView(R.id.search_iv)
    ImageView searchIv;
    @BindView(R.id.search_ll)
    LinearLayout searchLl;
    @BindView(R.id.right_ll)
    LinearLayout rightLl;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.content_ll)
    LinearLayout contentLl;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.lineChart)
    LineChart lineChart;
    @BindView(R.id.location_iv)
    ImageView locationIv;
    @BindView(R.id.location_info_tv)
    TextView locationInfoTv;
    @BindView(R.id.doc_iv)
    ImageView docIv;
    @BindView(R.id.doc_info_tv)
    TextView docInfoTv;
    @BindView(R.id.flag_iv)
    ImageView flagIv;
    @BindView(R.id.flag_tv)
    TextView flagTv;
    @BindView(R.id.main_iv)
    ImageView mainIv;
    @BindView(R.id.main_tv)
    TextView mainTv;
    @BindView(R.id.other_iv)
    ImageView otherIv;
    @BindView(R.id.other_tv)
    TextView otherTv;
    @BindView(R.id.all_sl)
    MyScrollView allSl;
    @BindView(R.id.main_tel_tv)
    TextView mainTelTv;
    @BindView(R.id.main_rl)
    RelativeLayout mainRl;
    @BindView(R.id.other_tel_tv)
    TextView otherTelTv;
    @BindView(R.id.other_rl)
    RelativeLayout otherRl;
    @BindView(R.id.bj_ll)
    LinearLayout bjLl;
    @BindView(R.id.lc_ll)
    LinearLayout lcLl;
    @BindView(R.id.gj_ll)
    LinearLayout gjLl;
    @BindView(R.id.location_rl)
    RelativeLayout locationRl;
    @BindView(R.id.letter_oid_rl)
    RelativeLayout letterOidRl;
    @BindView(R.id.line_rl)
    RelativeLayout lineRl;
    @BindView(R.id.check_tv)
    TextView checkTv;
    @BindView(R.id.add)
    LinearLayout add;
    @BindView(R.id.custom_ll)
    LinearLayout customLl;
    @BindView(R.id.BoxCarCode_tv)
    TextView BoxCarCodeTv;
    @BindView(R.id.driver_title)
    LinearLayout driverTitle;
    @BindView(R.id.arrow_iv)
    ImageView arrowIv;
    @BindView(R.id.temp_iv)
    ImageView tempIv;
    @BindView(R.id.temp_info_tv)
    TextView tempInfoTv;
    @BindView(R.id.temp_rl)
    RelativeLayout tempRl;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.online_iv)
    ImageView onlineIv;
    @BindView(R.id.online_tv)
    TextView onlineTv;
    @BindView(R.id.map_fy)
    FrameLayout mapFy;
    @BindView(R.id.piao_tv)
    TextView piaoTv;
    @BindView(R.id.jian_tv)
    TextView jianTv;
    @BindView(R.id.zhongliang_tv)
    TextView zhongliangTv;
    @BindView(R.id.fangliang_tv)
    TextView fangliangTv;
    @BindView(R.id.schedu_ll)
    LinearLayout scheduLl;
    @BindView(R.id.speed_iv)
    ImageView speedIv;
    @BindView(R.id.speed_info_tv)
    TextView speedInfoTv;
    @BindView(R.id.speed_rl)
    RelativeLayout speedRl;

    private BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.point_location);
    private BaiduMap mBaiduMap;
    private Marker mMarkerA;

    private SweetAlertDialog loading;
    public static final String DATA_INFO = "DATA_INFO";
    private InfoWindow mInfoWindow;
    private GetMyCarFleetResponse.DataValueBean.MCarInfoListBean datInfo;

    public static void startActivity(Activity activity, GetMyCarFleetResponse.DataValueBean.MCarInfoListBean info) {
        Intent intent = new Intent(activity, MyFleetLocationInfoActivity.class);
        intent.putExtra(DATA_INFO, info);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fleet_location_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        datInfo = (GetMyCarFleetResponse.DataValueBean.MCarInfoListBean) getIntent().getSerializableExtra(DATA_INFO);
        if (datInfo != null) {
            titltTv.setText(datInfo.getCarCode());
        }
        requestForData();
    }

    @Override
    protected void onDestroy() {
        if (mapView != null) {
            mapView.onDestroy();
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

//    private void showLoading() {
//        if (loading == null) {
//            loading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
//        }
//        loading.show();
//    }
//
//    private void hideLoading() {
//        if (loading != null) {
//            loading.dismiss();
//        }
//    }


    /**
     *
     * 获取信息
     *
     */

    private void requestForData() {
        if (datInfo == null) {
            return;
        }

        showLoading();

        final GetCarGPSAggregate request = new GetCarGPSAggregate();
        request.CarCode_Key = datInfo.getCarCode_Key();
        request.Line_Oid = datInfo.getLine_Oid();
        request.Driver = datInfo.getDriver();
        request.Driver_Tel = datInfo.getDriver_Tel();
        request.TSP_CarCode_Key = datInfo.getTSP_CarCode_Key();

        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.DataValue = request;
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.User_Name = MyApplication.getInstance().getLoginInfo().DataValue.User_Name;
        VolleyManager.newInstance().PostJsonRequest(LPSService.GetCarGPSAggregate_TAG, LPSService.GetCarGPSAggregate_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        MLog.json(response.toString());
                        GetCarGPSAggregateResponse resInfo = GsonUtil.newInstance().fromJson(response, GetCarGPSAggregateResponse.class);
                        Message message = Message.obtain();
                        if (resInfo != null && resInfo.Success) {
                            message.obj = resInfo;
                            message.what = Constants.REQUEST_SUCC;
                        } else {
                            message.what = Constants.REQUEST_FAIL;
                        }
                        myHangler.sendMessage(message);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message msg = Message.obtain();
                        msg.what = Constants.REQUEST_ERROR;
                        myHangler.sendMessage(msg);
                    }
                });
    }

    private Handler myHangler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            hideLoading();
            dissLoading();

            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    if (msg.obj != null) {
                        setData((GetCarGPSAggregateResponse) msg.obj);
                        allSl.setVisibility(View.VISIBLE);
                    } else {
                        allSl.setVisibility(View.GONE);
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetLocationInfoActivity.this, Utils.getResourcesString(R.string.get_data_fail), null);
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    allSl.setVisibility(View.GONE);
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetLocationInfoActivity.this, Utils.getResourcesString(R.string.get_data_fail), null);
                    break;
                case Constants.REQUEST_ERROR:
                    allSl.setVisibility(View.GONE);
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(MyFleetLocationInfoActivity.this, Utils.getResourcesString(R.string.get_data_fail), null);
                    break;
            }
        }
    };

    /**
     * 设置数据
     *
     * @param data
     */
    private void setData(GetCarGPSAggregateResponse data) {
        initLocation(data);
        initSchedule(data);
    }

    /**
     * 初始化地图
     */
    private void initMap(final LatLng location, String place, String direction) {
        //获取地图实例
        mBaiduMap = mapView.getMap();

        int rotaion = 0;
        if (!TextUtils.isEmpty(direction)) {
            rotaion = Integer.parseInt(direction);
            if (rotaion >= 180 && rotaion < 360) {
                rotaion = rotaion - 360;
            }
        } else {
            rotaion = 270;
        }


        Bitmap srcBit = BitmapFactory.decodeResource(getResources(), R.drawable.point_location);

        Bitmap newBitmap = BitmapUtil.rotateBitmap(srcBit, rotaion);
        bdA = BitmapDescriptorFactory
                .fromBitmap(newBitmap);

        MarkerOptions ooA = new MarkerOptions().position(location).icon(bdA);
        //设置中心点
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(location);
        if (mBaiduMap != null) {
            mBaiduMap.setMapStatus(u);
        }

        // 掉下动画
        ooA.animateType(MarkerOptions.MarkerAnimateType.grow);

        //加入标注
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));


        View popu = View.inflate(MyFleetLocationInfoActivity.this, R.layout.activity_fragment_2_showpop,
                null);
        TextView title = (TextView) popu.findViewById(R.id.showpop_title_text);
        if (!TextUtils.isEmpty(place)) {
            String[] newPlace = place.split("；");
            if (!TextUtils.isEmpty(newPlace[0])) {
                title.setText(newPlace[0]);
            } else {
                title.setText("正在定位中...");
            }
        } else {
        }

        title.setTextColor(Color.BLACK);
        mInfoWindow = new InfoWindow(
                BitmapDescriptorFactory.fromView(popu), location, -90, this);
        mBaiduMap.showInfoWindow(mInfoWindow);
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker == mMarkerA) {
                    mBaiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });

//        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Intent intent = new Intent(MyFleetLocationInfoActivity.this, MyTestActivity.class);
//                    intent.putExtra("location", location);
//                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MyFleetLocationInfoActivity.this, mapView, "myMap").toBundle());
//                }
//            }
//
//            @Override
//            public boolean onMapPoiClick(MapPoi mapPoi) {
//                return false;
//            }
//        });
    }

    /**
     * 初始化地图和定位信息
     * view
     *
     * @param data
     */
    private void initLocation(GetCarGPSAggregateResponse data) {
        GetCarGPSAggregateResponse.DataValueBean.CarGPSInfoBean infoBean = data.getDataValue().getCarGPSInfo();

        if (infoBean == null) {
            return;
        }

        /**
         * 定位信息
         * 绘制百度地图
         * 给出定位点
         */
        if (!TextUtils.isEmpty(infoBean.getBLat()) && !TextUtils.isEmpty(infoBean.getBLng()) && !TextUtils.isEmpty(infoBean.getCHS_Address())) {
            initMap(new LatLng(Double.parseDouble(infoBean.getBLat()), Double.parseDouble(infoBean.getBLng())), infoBean.getCHS_Address(), infoBean.getDirection());
        }

        //初始化位置
        if (!TextUtils.isEmpty(infoBean.getCHS_Address())) {
            locationInfoTv.setText(infoBean.getCHS_Address());
        }

        /**
         * 温度
         */
        if (TextUtils.isEmpty(infoBean.getTemperatureCount())) {
            tempInfoTv.setText("温度:无");
        } else {
            tempInfoTv.setText(infoBean.getTemperatureCount());
        }

        /**
         * 速度
         */
        if (TextUtils.isEmpty(infoBean.getSpeed())) {
            speedInfoTv.setText("速度：无");
        } else {
            speedInfoTv.setText("速度：" + infoBean.getSpeed() + " km/h");
        }

        /**
         * 定位时间
         */

        if (!TextUtils.isEmpty(infoBean.getPosition_DateTime())) {
            docInfoTv.setText("定位时间：" + infoBean.getPosition_DateTime());
        }

        /**
         * 关联线路
         */
        if (!TextUtils.isEmpty(infoBean.getLine_Name())) {
            flagTv.setText("关联线路：" + infoBean.getLine_Name());
        }

        /**
         * 在线状态
         */

        if (!TextUtils.isEmpty(infoBean.getStatus()) && infoBean.getStatus().equals("1")) {
            onlineTv.setText("在线状态：在线");
        } else {
            onlineTv.setText("在线状态：" + "离线" + infoBean.getLeaveDuration() + "小时");
        }

        driverTitle.setVisibility(View.VISIBLE);

        //主司机信息
        if (!TextUtils.isEmpty(infoBean.getDriver()) && !TextUtils.isEmpty(infoBean.getDriver_Tel())) {
            mainTv.setText(infoBean.getDriver());
            mainTelTv.setText(infoBean.getDriver_Tel());
        } else {
            mainTv.setText("无");
            mainTelTv.setText("");
        }

        //副司机信息
        if (!TextUtils.isEmpty(infoBean.getSecondDriver()) && !TextUtils.isEmpty(infoBean.getSecondDriver_Tel())) {
            otherTv.setText(infoBean.getSecondDriver());
            otherTelTv.setText(infoBean.getSecondDriver_Tel());
        } else {
            otherTv.setText("无");
            otherTelTv.setText("");
        }


    }


    /**
     * 初始化配置信息
     *
     * @param data
     */
    private void initSchedule(GetCarGPSAggregateResponse data) {

        GetCarGPSAggregateResponse.DataValueBean.LetterListBean bean = data.getDataValue().getLetter_List();

        //挂车号
        if (TextUtils.isEmpty(bean.getBoxCarCode())) {
            BoxCarCodeTv.setText("挂车号：" + "无");
        } else {
            BoxCarCodeTv.setText("挂车号：" + bean.getBoxCarCode());
        }

        //票数
        if (TextUtils.isEmpty(bean.getTicket_Count())) {
            piaoTv.setText("票数：" + "无");
        } else {
            piaoTv.setText("票数：" + bean.getTicket_Count() + "票");
        }

        //件数
        if (TextUtils.isEmpty(bean.getGoods_Num())) {
            jianTv.setText("件数：" + "无");
        } else {
            jianTv.setText("件数：" + bean.getGoods_Num() + "件");
        }

        //重量
        if (TextUtils.isEmpty(bean.getGoods_Weight())) {
            zhongliangTv.setText("重量：" + "无");
        } else {
            zhongliangTv.setText("重量：" + bean.getGoods_Weight() + "kg");
        }

        //方量
        if (TextUtils.isEmpty(bean.getBulk_Weight())) {
            fangliangTv.setText("方量：" + "无");
        } else {
            fangliangTv.setText("方量：" + bean.getBulk_Weight() + "m³");
        }

    }

    @OnClick({R.id.bj_ll, R.id.lc_ll, R.id.gj_ll, R.id.content_ll, R.id.back_llayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bj_ll:
                //报警
                MyFleetAlarmInfoActivity.start(this, datInfo.getCarCode_Key(), datInfo.getCarCode());
                break;
            case R.id.lc_ll:
                //里程统计
                MyFleetMileStatisticActivity.startActivity(MyFleetLocationInfoActivity.this, datInfo.getCarCode_Key(), datInfo.getCarCode(), datInfo.getTSP_CarCode_Key());
                break;
            case R.id.gj_ll:
                //轨迹
                MyFleetRecordActivity.start(this, datInfo.getCarCode_Key());
                break;
            case R.id.content_ll:
                //添加计划
                DriverAddCarSchedulingActivity.startActivity(MyFleetLocationInfoActivity.this, datInfo.getCarCode(), datInfo.getCarCode_Key(), REQUEST_CODE, DriverAddCarSchedulingActivity.INTENT_DATA_ADD_FROM_LOCATION);
                break;
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        if (mapView != null) {
            mapView.onResume();
        }

        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mapView != null) {
            mapView.onPause();
        }
        super.onPause();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resh(String addSuc) {
        if (addSuc.equals("Succ")) {
            requestForData();

        }
    }

    @Override
    public void onInfoWindowClick() {
        mBaiduMap.hideInfoWindow();
    }


    /**
     * private void initChart(List<GetCarGPSAggregateResponse.DataValueBean.CarMinleagesBean.DetailBean> ListMileage) {
     lineChart.animateX(0);
     XAxis xAxis = lineChart.getXAxis();
     xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
     lineChart.setDescription("日期");
     lineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
     lineChart.getXAxis().setGridColor(getResources().getColor(R.color.transparent));//隐藏Y轴线

     //从X轴进入的动画
     lineChart.animateX(1000);
     lineChart.animateY(1000);   //从Y轴进入的动画
     lineChart.animateXY(1000, 1000);    //从XY轴一起进入的动画
     lineChart.setDrawGridBackground(false); // 是否显示表格颜色
     lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
     //        YAxis leftAxis = lineChart.getAxisLeft();
     //        leftAxis.setAxisLineColor(Color.parseColor("#ffffff"));//设置轴线颜色
     lineChart.getAxisLeft().setDrawAxisLine(false);//隐藏轴线只显示数字标签

     MyMarkView myMarkView = new MyMarkView(this, R.layout.custom_marker_view, null);
     myMarkView.getXOffset(-myMarkView.getMeasuredWidth() / 2);
     myMarkView.getYOffset(-myMarkView.getMeasuredHeight());
     lineChart.setMarkerView(myMarkView);

     ArrayList<String> xValues = new ArrayList<>();
     ArrayList<Entry> yValue = new ArrayList<>();
     LineDataSet dataSet = new LineDataSet(yValue, "里程");
     if (ListMileage != null && ListMileage.size() > 0) {
     for (int i = 0; i < ListMileage.size(); i++) {
     GetCarGPSAggregateResponse.DataValueBean.CarMinleagesBean.DetailBean list = ListMileage.get(i);
     xValues.add(list.getStartTime());
     yValue.add(new Entry(Float.parseFloat(list.getDistanceGPS()), i));
     }
     }
     dataSet.setCircleSize(4f);
     dataSet.setLineWidth(1.75f); // 线宽
     dataSet.setDrawFilled(true);// 填充
     dataSet.setDrawCubic(true);  //设置曲线为圆滑的线
     dataSet.setValueTextSize(7f);//设置标注数据显示的大小
     ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
     dataSets.add(dataSet);

     LineData lineData = new LineData(xValues, dataSets);
     lineChart.setData(lineData);
     }
     */
}
