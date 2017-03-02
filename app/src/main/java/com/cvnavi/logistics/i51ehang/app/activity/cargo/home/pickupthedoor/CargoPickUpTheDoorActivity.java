package com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickupthedoor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.pickupthedoor.CargoProductNameGridViewAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.adapter.home.pickupthedoor.CargoSpecGridViewAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickuprcord.CargoLocationMapActivity;
import com.cvnavi.logistics.i51ehang.app.activity.cargo.home.pickuprcord.CargoPickUpRecordActivity;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.CargoProductName;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.CargoSpec;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.DestinationBean;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.GetTakeManifestsDataValue;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.request.SetTakeManifestsRequest;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.DestinationCallback;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.PIckupCallback;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.biz.IDestinationBiz;
import com.cvnavi.logistics.i51ehang.app.callback.cargo.biz.IPickupBiz;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
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
 * Depict: 货主上门取货 页面
 */

public class CargoPickUpTheDoorActivity extends BaseActivity implements IDestinationBiz,IPickupBiz {

    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.operation_btn)
    Button operationBtn;
    @BindView(R.id.operation_llayout)
    LinearLayout operationLlayout;
    @BindView(R.id.reponse_iv)
    ImageView reponseIv;
    @BindView(R.id.cargo_pick_location_rl)
    RelativeLayout cargoPickLocationRl;
    @BindView(R.id.gridView)
    GridView gridView;
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.space_gridView)
    GridView spaceGridView;
    @BindView(R.id.cargo_Pick_up_address)
    TextView cargoPickUpAddress;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.pickup_door_ok_button)
    Button pickupDoorOkButton;
    @BindView(R.id.Goods_Num_text)
    EditText GoodsNumText;
    @BindView(R.id.Goods_Weight_text)
    EditText GoodsWeightText;
    @BindView(R.id.Bulk_Weight_text)
    EditText BulkWeightText;

    //暂未使用
    private List<CargoProductName> cityList;
    private List<CargoSpec> specList;

    private CargoProductNameGridViewAdapter adapter;
    private CargoSpecGridViewAdapter mAdapter;

    private DataRequestBase dataRequestBase;

    //物品名称
    private final String[] productName = {
            Utils.getResourcesString(R.string.cargo_pickup_productName_parts),
            Utils.getResourcesString(R.string.cargo_pickup_productName_Clothes),
            Utils.getResourcesString(R.string.cargo_pickup_productName_food),
            Utils.getResourcesString(R.string.cargo_pickup_productName_liquid),
            Utils.getResourcesString(R.string.cargo_pickup_productName_other)

    };

    //物品规格
    private final String[] spec = {
            Utils.getResourcesString(R.string.cargo_pickup_spec_carton),
            Utils.getResourcesString(R.string.cargo_pickup_spec_Tray),
            Utils.getResourcesString(R.string.cargo_pickup_spec_WoodenBox),
            Utils.getResourcesString(R.string.cargo_pickup_spec_WovenBag),
            Utils.getResourcesString(R.string.cargo_pickup_spec_other)
    };


    private String BLn ; // 经度1
    private String BLa; // 纬度1
    private String ArriveAddress; // 目的地

    private String goodsBreed;//品名
    private String goodsCasing;//规格

    private SweetAlertDialog sweetAlertDialog;

    //百度地图
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargo_pick_up_the_door);
        ButterKnife.bind(this);

        DestinationCallback.getCallback().setCallback(this);
        PIckupCallback.getCallback().setCallback(this);

        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);
        getLocation();

        setData();
        initView();

    }

    private void setData() {
        cityList = new ArrayList<>();
        CargoProductName item = new CargoProductName();
        item.setCityName("深圳");
        for (int i = 0; i < productName.length; i++) {
            cityList.add(item);
        }


        specList = new ArrayList<>();
        CargoSpec specItem = new CargoSpec();
        specItem.setSpecName("纸箱");
        for (int i = 0; i < spec.length; i++) {
            specList.add(specItem);
        }
    }

    private void initView() {
        titleTv.setText("上门取货");
        operationBtn.setVisibility(View.VISIBLE);
        operationBtn.setText("取货记录");

        dataRequestBase = new DataRequestBase();
        sweetAlertDialog = new SweetAlertDialog(this,SweetAlertDialog.PROGRESS_TYPE);
        goodsBreed = "配件";
        goodsCasing = "纸箱";

        int size = productName.length;
        int length = 50;

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 6) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 重点
        gridView.setColumnWidth(itemWidth); // 重点
        gridView.setHorizontalSpacing(20); // 间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 重点

        spaceGridView.setLayoutParams(params); // 重点
        spaceGridView.setColumnWidth(itemWidth); // 重点
        spaceGridView.setHorizontalSpacing(20); // 间距
        spaceGridView.setStretchMode(GridView.NO_STRETCH);
        spaceGridView.setNumColumns(size); // 重点

        adapter = new CargoProductNameGridViewAdapter(getApplicationContext(), cityList, productName);
        gridView.setAdapter(adapter);

        mAdapter = new CargoSpecGridViewAdapter(this, specList, spec);
        spaceGridView.setAdapter(mAdapter);

        //物品名称
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                for (int i = 0; i < parent.getCount(); i++) {
                    View v = parent.getChildAt(i);
                    TextView textView = (TextView) v.findViewById(R.id.id_index_gallery_item_image);
                    if (position == i) { // 当前选中的Item改变背景颜色
                        textView.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
                        v.findViewById(R.id.cargo_pick_item_LLlayout).setBackgroundResource(R.drawable.shape_cargo_selected);
                    } else {
                        textView.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                        v.findViewById(R.id.cargo_pick_item_LLlayout).setBackgroundResource(R.drawable.shape_cargo_product_name_boder);
                    }
                }

                goodsBreed = productName[position].toString();

            }
        });


        //物品规格
        spaceGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                for (int i = 0; i < parent.getCount(); i++) {
                    View v = parent.getChildAt(i);
                    TextView textView = (TextView) v.findViewById(R.id.id_index_gallery_item_image);
                    if (position == i) { // 当前选中的Item改变背景颜色
                        textView.setTextColor(Utils.getResourcesColor(R.color.color_ffffff));
                        v.findViewById(R.id.cargo_pick_item_LLlayout).setBackgroundResource(R.drawable.shape_cargo_selected);
                    } else {
                        textView.setTextColor(Utils.getResourcesColor(R.color.color_000000));
                        v.findViewById(R.id.cargo_pick_item_LLlayout).setBackgroundResource(R.drawable.shape_cargo_product_name_boder);
                    }
                }

                goodsCasing = spec[position].toString();

            }
        });

    }

    @OnClick({R.id.back_llayout, R.id.cargo_pick_location_rl, R.id.operation_btn, R.id.pickup_door_ok_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
            case R.id.cargo_pick_location_rl:
                startActivity(new Intent(CargoPickUpTheDoorActivity.this, CargoLocationMapActivity.class));
                break;
            case R.id.operation_btn:
                startActivity(new Intent(CargoPickUpTheDoorActivity.this, CargoPickUpRecordActivity.class).putExtra("Cargo","Door"));
                break;
            case R.id.pickup_door_ok_button:

                if (TextUtils.isEmpty(cargoPickUpAddress.getText())){
                    DialogUtils.showNormalToast("请输入地址");
                    return;
                }
                if (TextUtils.isEmpty(GoodsNumText.getText())&&TextUtils.isEmpty(GoodsWeightText.getText())&&TextUtils.isEmpty(BulkWeightText.getText())){
                    DialogUtils.showNormalToast("件数、体积、重量  至少选填一个！");
                    return;
                }
                httpSetTakeManifests();
                break;
        }
    }

    @Override
    public void setDestination(DestinationBean bean) {
        BLn = bean.ArriveAddressBLng; // 经度1
        BLa = bean.ArriveAddressBLat; // 纬度1
        ArriveAddress = bean.ArriveAddress; // 目的地
        cargoPickUpAddress.setText(ArriveAddress);
    }


    @Override
    public void setPickup(GetTakeManifestsDataValue bean) {
        adapter.setItemPosition(bean.Goods_Breed);
        mAdapter.setSpec(bean.Goods_Casing);
        ArriveAddress = bean.SendMan_Address; // 目的地
        cargoPickUpAddress.setText(ArriveAddress);
        GoodsNumText.setText(bean.Goods_Num);
        GoodsWeightText.setText(bean.Goods_Weight);
        BulkWeightText.setText(bean.Bulk_Weight);
        goodsBreed = bean.Goods_Breed;
        goodsCasing = bean.Goods_Casing;

    }


    private void httpSetTakeManifests() {

        sweetAlertDialog.show();

        GetAppLoginResponse info = MyApplication.getInstance().getLoginInfo();
        SetTakeManifestsRequest datavalue = new SetTakeManifestsRequest();
        datavalue.SendMan_Name = info.DataValue.User_Name;
        datavalue.SendMan_Address = cargoPickUpAddress.getText().toString();
        datavalue.SendMan_Tel = info.DataValue.User_Tel;
        datavalue.BLng = BLn;
        datavalue.BLat = BLa;
        datavalue.Goods_Breed =goodsBreed;
        datavalue.Goods_Casing = goodsCasing;

        datavalue.Goods_Num = GoodsNumText.getText().toString();
        datavalue.Goods_Weight = GoodsWeightText.getText().toString();
        datavalue.Bulk_Weight = BulkWeightText.getText().toString();

        if (TextUtils.isEmpty(GoodsNumText.getText())){
            datavalue.Goods_Num = "0";
        }

        if (TextUtils.isEmpty(GoodsWeightText.getText())){
            datavalue.Goods_Weight = "0";
        }

        if (TextUtils.isEmpty(BulkWeightText.getText())){
            datavalue.Bulk_Weight = "0";
        }


        dataRequestBase.DataValue = datavalue;
        dataRequestBase.User_Key = info.DataValue.User_Key;
        dataRequestBase.UserType_Oid = info.DataValue.UserType_Oid;
        dataRequestBase.Token = info.DataValue.Token;
        dataRequestBase.Company_Oid = info.DataValue.Company_Oid;

        LogUtil.d("-->>request=" + new Gson().toJson(dataRequestBase));
        LoggerUtil.json(new Gson().toJson(dataRequestBase));
        VolleyManager.newInstance().PostJsonRequest(TMSService.SetTakeManifests_TAG, TMSService.SetTakeManifests_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtil.d("-->> respon=" + response.toString());
                LoggerUtil.json(response.toString());
                DataResponseBase getShiftListResponse = GsonUtil.newInstance().fromJson(response, DataResponseBase.class);
                Message msg = Message.obtain();
                if (getShiftListResponse!=null){
                    if (getShiftListResponse.Success ) {
                        msg.what = Constants.REQUEST_SUCC;
                        msg.obj = getShiftListResponse;
                    } else {
                        msg.what = Constants.REQUEST_FAIL;
                        msg.obj = getShiftListResponse.ErrorText;
                    }
                }else {
                    msg.what = Constants.REQUEST_FAIL;
                }

                handler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_FAIL;
                message.obj = error.toString();
                handler.sendMessage(message);
            }
        });

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            sweetAlertDialog.dismiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:

                    DataResponseBase bean = (DataResponseBase) msg.obj;
                    if (bean.Success){
                        DialogUtils.showMessageDialog(CargoPickUpTheDoorActivity.this,"提示","提交成功","确定","", new CustomDialogListener() {
                            @Override
                            public void onDialogClosed(int closeType) {
                                if (CustomDialogListener.BUTTON_NO==closeType){

                                }else if (CustomDialogListener.BUTTON_OK==closeType){
                                    finish();
                                }
                            }
                        });
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.get_data_fail));
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.request_Fill) : msg.obj.toString());
                    break;
            }

        }
    };


    /** 获得所在位置经纬度及详细地址 */
    public void getLocation() {
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();

    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 取消监听函数
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }

    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String address = location.getAddrStr();

                cargoPickUpAddress.setText(address);
                BLn =String.valueOf(longitude) ;
                BLa =String.valueOf(latitude) ;

                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位
                    mLocationClient.stop();
                }
            }
        }
    }
}
