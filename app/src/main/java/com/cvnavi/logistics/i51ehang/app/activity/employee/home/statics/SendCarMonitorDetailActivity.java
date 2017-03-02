package com.cvnavi.logistics.i51ehang.app.activity.employee.home.statics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.response.SendCarMonitorResponse;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/***********************************************************************************
 * 版权所有 上海势航网络科技
 * 创建人：  ChuZhiYuan
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/16 下午4:01
 * 描述：发车监控详情（有路径规划功能）
 ************************************************************************************/

public class SendCarMonitorDetailActivity extends BaseActivity implements OnGetRoutePlanResultListener {

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
    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.Line_Name_tv)
    TextView LineNameTv;
    @BindView(R.id.Line_Ll)
    LinearLayout LineLl;
    @BindView(R.id.whither_tv)
    TextView whitherTv;
    @BindView(R.id.mudi_ll)
    LinearLayout mudiLl;
    @BindView(R.id.position_tv)
    TextView positionTv;
    @BindView(R.id.distance_tv)
    TextView distanceTv;
    @BindView(R.id.root_ll)
    LinearLayout rootLl;

    private SendCarMonitorResponse.DataValueBean info;
    private RoutePlanSearch mSearch = null; // 搜索相关 规划路线
    private BaiduMap mBaidumap = null;
    private String netx_node_name;//下一节点名

    public static void start(Context context, SendCarMonitorResponse.DataValueBean info) {
        Intent starter = new Intent(context, SendCarMonitorDetailActivity.class);
        starter.putExtra("data", info);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_car_monitor_detail);
        ButterKnife.bind(this);
        info = (SendCarMonitorResponse.DataValueBean) getIntent().getSerializableExtra("data");
        if (info == null) {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(SendCarMonitorDetailActivity.this, "暂无监控信息！", null);
            return;
        }
        initMap();
    }

    /**
     * 设置view
     */
    private void setView() {
        if (!TextUtils.isEmpty(info.getCarCode())) {
            titleTv.setText(info.getCarCode());
        } else {
            titleTv.setText("线路跟踪");
        }

        if (!TextUtils.isEmpty(info.getCurrentLat())
                && !TextUtils.isEmpty(info.getCurrentLong())
                && !TextUtils.isEmpty(info.getNextLat())
                && !TextUtils.isEmpty(info.getNextLong())) {
            LatLng begin = new LatLng(Double.parseDouble(info.getCurrentLat()), Double.parseDouble(info.getCurrentLong()));
            LatLng end = new LatLng(Double.parseDouble(info.getNextLat()), Double.parseDouble(info.getNextLong()));

            if (begin != null && end != null) {
                showLoading();
                driverRoutePlan(begin, end);
            } else {
                DialogUtils.showNormalToast("路线规划失败!");
                return;
            }
        }
    }

    private void driverRoutePlan(LatLng la, LatLng lb) {

        PlanNode stNode = PlanNode.withLocation(la);
        PlanNode enNode = PlanNode.withLocation(lb);
        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode).to(enNode));
    }


    private void initMap() {
        mBaidumap = map.getMap();
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        mBaidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                onClickMap(marker);
                return false;
            }
        });
        setView();
    }

    private void onClickMap(Marker marker) {
        String str = marker.getTitle();
        if (!TextUtils.isEmpty(str)) {
            switch (str) {
                case "起点":
                    showPop(marker.getPosition(), "当前位置");
                    break;
                case "终点":
                    showPop(marker.getPosition(), netx_node_name);
                    break;
            }
        }
    }

    private void showPop(LatLng mLatLng, String titles) {
        View popu = View.inflate(this, R.layout.dr_lineplan,
                null);
        TextView title = (TextView) popu.findViewById(R.id.showpop_title_text);
        SetViewValueUtil.setTextViewValue(title, titles);
        title.setTextColor(Color.BLACK);

        InfoWindow.OnInfoWindowClickListener mListener = new InfoWindow.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick() {

                mBaidumap.hideInfoWindow();
            }
        };

        InfoWindow mInfoWindow = new InfoWindow(
                BitmapDescriptorFactory.fromView(popu), mLatLng, -60, mListener);
        mBaidumap.showInfoWindow(mInfoWindow);
    }

    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    boolean useDefaultIcon = true;
    OverlayManager routeOverlay = null;

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        dissLoading();
        rootLl.setVisibility(View.VISIBLE);
        netx_node_name = info.getNextNodeName();
        whitherTv.setText(netx_node_name);

        mudiLl.setVisibility(View.VISIBLE);
        SetViewValueUtil.setTextViewValue(positionTv, info.getCHS_Address());
        LineLl.setVisibility(View.VISIBLE);
        SetViewValueUtil.setTextViewValue(LineNameTv, info.getLine_Name());

        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(SendCarMonitorDetailActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }

        if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//             drivingRouteResult.getSuggestAddrInfo();
            return;
        }

        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;

            if (!TextUtils.isEmpty(info.getNextNode_Arr_DateTime())) {
                distanceTv.setText("到达时间:" + info.getNextNode_Arr_DateTime());
            } else {
                if (!TextUtils.isEmpty(info.getDescribe())) {
                    distanceTv.setText("大约需要：" + info.getDescribe());
                } else {
                    distanceTv.setText("大约需要：" + "--");
                }
            }

            if (drivingRouteResult.getRouteLines().size() > 0) {
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
                routeOverlay = overlay;
                mBaidumap.setOnMarkerClickListener(overlay);
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                overlay.zoomToSpan();
            } else {
                Log.d("route result", "结果数<0");
                return;
            }
        }

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }
    }

    @OnClick(R.id.back_llayout)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        map.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        map.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mSearch != null) {
            mSearch.destroy();
        }
        map.onDestroy();
        map = null;
        super.onDestroy();
    }
}
