package com.cvnavi.logistics.i51ehang.app.activity.employee.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.model.employee.me.GetDestinationResponse;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/***********************************************************************************
*版权所有 上海势航网络科技
*创建人：  ChuZhiYuan
*电子邮箱：604639402@qq.com
*手机号：18301969307
*创建时间：2017/1/16 下午4:08
*描述：常用目的地定位信息
************************************************************************************/


public class CommonAddressGpsActivity extends BaseActivity implements InfoWindow.OnInfoWindowClickListener {
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.monitoring_map)
    MapView monitoringMap;
    private BaiduMap mBaiduMap;
    private Marker mMarkerA;
    private InfoWindow mInfoWindow;
    private GetDestinationResponse.DataValueBean info;
    private BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.location_blue);

    public static void startActivity(Activity activity, GetDestinationResponse.DataValueBean info) {
        Intent intent = new Intent(activity, CommonAddressGpsActivity.class);
        intent.putExtra("info", info);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_addres_gps);
        ButterKnife.bind(this);
        info = (GetDestinationResponse.DataValueBean) getIntent().getSerializableExtra("info");
        titleTv.setText("定位信息");
        if (info == null || (info != null && TextUtils.isEmpty(info.getBLat())) || (info != null && TextUtils.isEmpty(info.getBLng()))) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(this, "无定位信息", new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    finish();
                }
            });
        } else {
            initMap();
        }
    }

    /**
     * 初始化地图
     */
    private void initMap() {
        //获取地图实例
        mBaiduMap = monitoringMap.getMap();
        MarkerOptions ooA = new MarkerOptions().position(new LatLng(Double.parseDouble(info.getBLat()), Double.parseDouble(info.getBLng()))).icon(bdA)
                .zIndex(9).draggable(true);
        //设置中心点
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(new LatLng(Double.parseDouble(info.getBLat()), Double.parseDouble(info.getBLng())));
        if (mBaiduMap != null) {
            mBaiduMap.setMapStatus(u);
        }
        // 掉下动画
        ooA.animateType(MarkerOptions.MarkerAnimateType.grow);

        //加入标注
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));

        View popu = View.inflate(CommonAddressGpsActivity.this, R.layout.activity_fragment_2_showpop,
                null);
        TextView title = (TextView) popu.findViewById(R.id.showpop_title_text);
        title.setText(info.getDestination().replaceAll("；", "；\n　　　"));
        title.setTextColor(Color.BLACK);
        mInfoWindow = new InfoWindow(
                BitmapDescriptorFactory.fromView(popu), new LatLng(Double.parseDouble(info.getBLat()), Double.parseDouble(info.getBLng())), -110, this);
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
    }


    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }

    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        if (monitoringMap != null) {
            monitoringMap.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        if (monitoringMap != null) {
            monitoringMap.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        if (monitoringMap != null) {
            monitoringMap.onDestroy();
        }

        if (mMarkerA != null) {
            mMarkerA = null;
        }
        // 回收 bitmap 资源
        super.onDestroy();
    }


    @Override
    public void onInfoWindowClick() {
        mBaiduMap.hideInfoWindow();
    }
}
