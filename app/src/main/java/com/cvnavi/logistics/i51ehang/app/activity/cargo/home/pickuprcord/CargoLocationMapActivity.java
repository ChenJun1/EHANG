package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickuprcord;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.DestinationBean;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.DestinationCallback;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.MyInputMethodManager;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.cargolocationmap.PoiOverlay;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 货主 取货地址获取 页面
 */

public class CargoLocationMapActivity extends FragmentActivity implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener,
        View.OnClickListener {

    public MyLocationListenner myListener = new MyLocationListenner();
    double latitude = 0.0;
    double longitude = 0.0;

    // 定位相关
    LocationClient mLocClient;
    MapView mMapView;
    boolean isFirstLoc = true; // 是否首次定位

    private Button optenButten;

    private LinearLayout back_linearLayout;
    private TextView titlt_textView;
    private EditText editCity;
    private EditText editSearchKey;

    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private BaiduMap mBaiduMap = null;
    private List<String> suggest;
    /**
     * 搜索关键字输入窗口
     */
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;
    private BitmapDescriptor bitmap;
    private String address = "";

    private String searchStr;//搜索内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_location_map);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 头部沉侵
        }
        initView();// 控件
        initMap();// 定位
        initSearchMap();// POI搜索模块

        MyInputMethodManager.setImeOptions(editSearchKey, EditorInfo.IME_ACTION_SEARCH);
        MyInputMethodManager.setOnKeyListener(editSearchKey, new MyInputMethodManager.MyInputMethodOnKeyListener() {
            @Override
            public void onInputMethodeOnkey() {
                onSearch();
            }
        });
    }

    private void initSearchMap() {
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
        sugAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);

        bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.startcar);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                return false;
            }
        });

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                // 获取经纬度
                latitude = arg0.latitude;
                longitude = arg0.longitude;
                // System.out.println("latitude(维度)=" + latitude
                // + "longitude(经度)=" + longitude);

                // 重新定位并以此坐标为中心点
                LatLng ll = new LatLng(latitude, longitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory
                        .newMapStatus(builder.build()));
                mLocClient.requestLocation();

                mBaiduMap.clear();// 先清理图层
                // 定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(bitmap);
                // 在地图上添加Marker，并显示
                mBaiduMap.addOverlay(options);
                // 实例化一个地理编码查询对象
                GeoCoder geoCoder = GeoCoder.newInstance();
                // 设置反地理编码位置坐标
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(arg0);
                // 发起反地理编码请求(经纬度->地址信息)
                geoCoder.reverseGeoCode(op);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                    @Override
                    public void onGetReverseGeoCodeResult(
                            ReverseGeoCodeResult arg0) {
                        // 获取点击的坐标地址
                        address = arg0.getAddress();
                        // System.out.println("latitude(维度)=" + latitude
                        // + "longitude(经度)=" + longitude + "点击获取的地址是："
                        // + address);

                        Show(arg0.getLocation(), arg0.getAddress());
                        editSearchKey.setHint(address);
                    }
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult arg0) {}
                });
            }
        });
        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {}

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,int arg2, int arg3) {}

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,int arg3) {
                if (cs.length() <= 0) {
                    return;
                }
                String city = ((EditText) findViewById(R.id.city)).getText().toString();
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                        .keyword(cs.toString()).city(city));
            }
        });
    }

    // 定位初始化
    private void initMap() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.map);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        mLocClient.setLocOption(option);
        mLocClient.start();
        View child = mMapView.getChildAt(1);
        // 隐藏百度logo
        if (child != null
                && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        titlt_textView = (TextView) findViewById(R.id.title_tv);
        titlt_textView.setText("定位选择");
        back_linearLayout = (LinearLayout) findViewById(R.id.back_llayout);
        editCity = (EditText) findViewById(R.id.city);
        editSearchKey = (EditText) findViewById(R.id.searchkey);
        back_linearLayout.setOnClickListener(this);
        optenButten = (Button) findViewById(R.id.operation_btn);
        optenButten.setOnClickListener(this);
        optenButten.setVisibility(View.VISIBLE);
        optenButten.setText("确定");
    }

    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mMapView.onDestroy();
        mPoiSearch.destroy();
        if (mLocClient.isStarted()) {
            mLocClient.stop();
        }
        // 关闭定位图层
        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(false);
        }
        mMapView = null;
        mSuggestionSearch.destroy();
        bitmap.recycle();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    // 搜索
    private void onSearch() {

        searchStr = editSearchKey.getText().toString();

        if (searchStr == null || searchStr.trim().length() <= 0) {
            // 没有内容，不进行搜索，提示请输入内容
            DialogUtils.showWarningToast(Utils.getResourcesString(R.string.please_enter_search_content));
        } else {
            // 关闭输入法
            MyInputMethodManager.closeInputMethod(this, editSearchKey);
            mPoiSearch.searchInCity((new PoiCitySearchOption())
                    .city(editCity.getText().toString())
                    .keyword(searchStr)
                    .pageNum(load_Index));
        }
    }

    /**
     * 影响搜索按钮点击事件
     *
     * @param v
     */
    public void searchButtonProcess(View v) {
//        EditText editCity = (EditText) findViewById(R.id.city);
//        EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
//        mPoiSearch.searchInCity((new PoiCitySearchOption())
//                .city(editCity.getText().toString())
//                .keyword(editSearchKey.getText().toString())
//                .pageNum(load_Index));
        onSearch();
    }

    public void goToNextPage(View v) {
        load_Index++;
        searchButtonProcess(null);
    }

    public void onGetPoiResult(PoiResult result) {
        if (result == null
                || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(CargoLocationMapActivity.this, "未找到结果",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mBaiduMap.clear();
            PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            overlay.setData(result);
            overlay.addToMap();
            overlay.zoomToSpan();
            return;
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";
            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "找到结果";
            Toast.makeText(CargoLocationMapActivity.this, strInfo,
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(CargoLocationMapActivity.this, "抱歉，未找到结果",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(CargoLocationMapActivity.this,
                    result.getName() + ": " + result.getAddress(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }

        suggest = new ArrayList<String>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }
        sugAdapter = new ArrayAdapter<String>(CargoLocationMapActivity.this, android.R.layout.simple_dropdown_item_1line, suggest);
        keyWorldsView.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
    }

    private void Show(LatLng mLatLng, String mString) {
        View popu = View.inflate(this, R.layout.activity_fragment_2_showpop,
                null);
        TextView title = (TextView) popu.findViewById(R.id.showpop_title_text);

        title.setText(address);
        InfoWindow.OnInfoWindowClickListener mListener = new InfoWindow.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick() {

                mBaiduMap.hideInfoWindow();
                DestinationBean bean = new DestinationBean();
                bean.ArriveAddressBLng = longitude + ""; // 经度
                bean.ArriveAddressBLat = latitude + "";// 纬度
                bean.ArriveAddress = address; // 地址
                DestinationCallback.getCallback().addAddressBiz(bean);
                finish();
            }
        };

        title.setTextColor(Color.RED);
        InfoWindow mInfoWindow = new InfoWindow(
                BitmapDescriptorFactory.fromView(popu), mLatLng, -30, mListener);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.operation_btn:

                DestinationBean bean = new DestinationBean();
                bean.ArriveAddressBLng = longitude + ""; // 经度
                bean.ArriveAddressBLat = latitude + "";// 纬度
                bean.ArriveAddress = address; // 地址
                DestinationCallback.getCallback().addAddressBiz(bean);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                latitude = location.getLatitude();
                longitude = location.getLongitude();
                address = location.getAddrStr();
                Show(ll, location.getAddrStr());
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {}
    }

    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult arg0) {
        // TODO Auto-generated method stub
    }
}
