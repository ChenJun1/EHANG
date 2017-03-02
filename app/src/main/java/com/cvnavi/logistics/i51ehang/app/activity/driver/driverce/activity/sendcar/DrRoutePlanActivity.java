package com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.MySendCarLineNoteBean;
import com.cvnavi.logistics.i51ehang.app.bean.driver.request.CarLineNodeRequestBean;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.GetMySendCarLineNoteResponse;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict: 线路跟踪 页面
 */

public class DrRoutePlanActivity extends BaseActivity implements OnGetRoutePlanResultListener, HandlerUtils.OnReceiveMessageListener {

    private static final String TAG = "DrRoutePlanActivity";

    private static final String LETTER_OID = "LETTER_OID";//页面传参
    private static final String CARCODE_KEY = "CARCODE_KEY";//页面传参
    private static final String LINE_NAME = "LINE_NAME";//页面传参
    private static final String DESTINATION = "DESTINATION";//页面传参

    @BindView(R.id.map)
    MapView mMap;
    @BindView(R.id.Line_Name_tv)
    TextView mLineNameTv;
    @BindView(R.id.whither_tv)
    TextView mWhitherTv;
    @BindView(R.id.position_tv)
    TextView mPositionTv;
    @BindView(R.id.distance_tv)
    TextView mDistanceTv;
    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.back_llayout)
    LinearLayout mBackLlayout;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.Line_Ll)
    LinearLayout mLineLl;
    @BindView(R.id.mudi_ll)
    LinearLayout mMudiLl;


    private String Letter_Oid;
    private String CarCode_Key;
    private String Line_Name;
    private String destination;
    private BaiduMap mBaidumap = null;
    private String netx_node_name;//下一节点名
    private HandlerUtils.HandlerHolder mHandlerHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dr_route_plan_cativity);
        ButterKnife.bind(this);
        mHandlerHolder = new HandlerUtils.HandlerHolder(this);
        init();
        initMap();
    }

    @Override
    protected void onResume() {
        mMap.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMap.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mSearch != null) {
            mSearch.destroy();
        }
        mMap.onDestroy();
        mMap = null;
        super.onDestroy();
    }

    public static void start(Context context, String Letter_Oid, String CarCode_Key, String Line_Name, String destination) {
        Intent starter = new Intent(context, DrRoutePlanActivity.class);
        starter.putExtra(LETTER_OID, Letter_Oid);
        starter.putExtra(CARCODE_KEY, CarCode_Key);
        starter.putExtra(LINE_NAME, Line_Name);
        starter.putExtra(DESTINATION, destination);
        context.startActivity(starter);
    }

    private SweetAlertDialog lodingDialog;

    private void init() {
        mTitleTv.setText("线路跟踪");
        lodingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        dataRequestBase = new DataRequestBase();
        //获取页面传过来的参数
        Letter_Oid = getIntent().getStringExtra(LETTER_OID);
        CarCode_Key = getIntent().getStringExtra(CARCODE_KEY);
        Line_Name = getIntent().getStringExtra(LINE_NAME);
        destination = getIntent().getStringExtra(DESTINATION);
        if (!TextUtils.isEmpty(Letter_Oid) && !TextUtils.isEmpty(CarCode_Key)) {
            loadDataRequest(DriverService.GetCarLineNode_Request_Url);//数据请求
        }
    }

    private void initMap() {
        mBaidumap = mMap.getMap();

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

    // 搜索相关 规划路线
    RoutePlanSearch mSearch = null;

    private void driverRoutePlan(LatLng la, LatLng lb) {
        PlanNode stNode = PlanNode.withLocation(la);
        PlanNode enNode = PlanNode.withLocation(lb);

        mSearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode).to(enNode));
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    boolean useDefaultIcon = true;
    OverlayManager routeOverlay = null;

    //驾车路线规划
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(DrRoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//             result.getSuggestAddrInfo();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            nodeIndex = -1;
            int Distance = result.getRouteLines().get(0).getDistance();
            StringBuilder stringBuilder = new StringBuilder();
            int DistanceTv = 0;
            if (Distance > 1000) {
                DistanceTv = Distance / 1000;
                if (DistanceTv > 59) {    //计算时间
                    int h = DistanceTv / 60;
                    int m = DistanceTv % 60;
                    stringBuilder.append(h + "小时");
                    if (m > 0) {
                        stringBuilder.append(m + "分钟");
                    }
                } else {
                    stringBuilder.append(DistanceTv + "分钟");
                }
                mDistanceTv.setText(netx_node_name + "；" + DistanceTv + " km；" + stringBuilder.toString() + "；");
            } else {
                int s = Distance / 60;
                stringBuilder.append(s + "秒");
                mDistanceTv.setText(netx_node_name + "；" + Distance + "m；" + stringBuilder.toString() + "；");
            }

            if (result.getRouteLines().size() > 0) {
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaidumap);
                routeOverlay = overlay;
                mBaidumap.setOnMarkerClickListener(overlay);
                overlay.setData(result.getRouteLines().get(0));
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

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null) {
            lodingDialog.dismiss();
        }
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                List<MySendCarLineNoteBean> dataList = (List<MySendCarLineNoteBean>) msg.obj;
                if (dataList != null) {
                    setDataView(dataList);
                }
                break;
            case Constants.REQUEST_FAIL:
                DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R
                        .string.request_Fill) : msg.obj.toString());
                break;
            case Constants.DELETE_SUCC:
                DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                break;
            case Constants.REQUEST_ERROR:
                DialogUtils.showMessageDialogOfDefaultSingleBtn(DrRoutePlanActivity.this, Utils
                        .getResourcesString(R.string.request_error));
                break;
        }
    }

    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
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

    private DataRequestBase dataRequestBase;

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        dataRequestBase.DataValue = new CarLineNodeRequestBean(Letter_Oid, CarCode_Key); //JsonUtils.toJsonData(getDriverListRequest);
        LoggerUtil.d(TAG, dataRequestBase.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, GsonUtil.newInstance().toJson
                (dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(TAG, response.toString());
                GetMySendCarLineNoteResponse response1 = GsonUtil.newInstance().fromJson(response.toString(),
                        GetMySendCarLineNoteResponse.class);
                Message msg = Message.obtain();
                if (response1 == null) {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                } else {
                    if (response1.Success) {
                        msg.obj = response1.DataValue;
                        msg.what = Constants.REQUEST_SUCC;
                    } else {
                        msg.obj = response1.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                    }
                }
                mHandlerHolder.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                mHandlerHolder.sendMessage(msg);
            }
        });

    }

    private LatLng now = null;
    private LatLng end = null;

    private void setDataView(List<MySendCarLineNoteBean> dataList) {

        for (MySendCarLineNoteBean carLineNoteBean : dataList) {
            if (!TextUtils.isEmpty(carLineNoteBean.getNode_Oid()) && carLineNoteBean.getNode_Oid().equals("3")) {
                now = new LatLng(Double.valueOf(carLineNoteBean.getBLat()), Double.valueOf(carLineNoteBean.getBLng()));
                SetViewValueUtil.setTextViewValue(mPositionTv, carLineNoteBean.getNode_Name());
            } else if (!TextUtils.isEmpty(carLineNoteBean.getNode_Oid()) && carLineNoteBean.getNode_Oid().equals("2")) {
                end = new LatLng(Double.valueOf(carLineNoteBean.getBLat()), Double.valueOf(carLineNoteBean.getBLng()));
                netx_node_name = carLineNoteBean.getNode_Name();
            }
        }
        if (!TextUtils.isEmpty(destination)) {
            mMudiLl.setVisibility(View.VISIBLE);
            SetViewValueUtil.setTextViewValue(mWhitherTv, destination);
        } else {
            mLineLl.setVisibility(View.VISIBLE);
            SetViewValueUtil.setTextViewValue(mLineNameTv, Line_Name);
        }


        if (now != null && end != null) {
            driverRoutePlan(now, end);
        } else {
            DialogUtils.showNormalToast("路线规划失败!");
            return;
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
}
