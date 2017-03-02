package com.cvnavi.logistics.i51ehang.app.activity.driver.home.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.location.DriverCarTreeListAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.myFleet.MyFleetCarTreeAdapter;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetCarListResponse;
import com.cvnavi.logistics.i51ehang.app.callback.MyOnClickItemListener;
import com.cvnavi.logistics.i51ehang.app.callback.manager.LocationChooseCarCallBackManager;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.JsonUtils;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import volley.VolleyManager;

/**
 * Created by Cjun on 2016/7/5.
 */
public class DriverCarTreeListActivity extends BaseActivity implements MyOnClickItemListener {
    private static final String TAG = DriverCarTreeListActivity.class.getName();

    @BindView(R.id.back_llayout)
    LinearLayout backLinearLayout;
    @BindView(R.id.title_tv)
    TextView titltTextView;
    @BindView(R.id.carList_lv)
    ListView carListLv;
    private SweetAlertDialog loadingDialog = null;
    private ArrayList<mCarInfo> parents = new ArrayList<>();
    private DriverCarTreeListAdapter carTreeListAdapter;

    private MyFleetCarTreeAdapter adapter;
    private static final String SELECT_TYPE = "SELECT_TYPE";
    private int type = TYPE_ALL;
    public static final int TYPE_CAR = 0;
    public static final int TYPE_GUA = 1;
    public static final int TYPE_ALL = 2;
    public static final int TYPE_GPS = 3;

    private Boolean flag = false;//是否是首页车辆列表

    public static void startActivity(Activity activity, int requestCode, int type) {
        Intent intent = new Intent(activity, DriverCarTreeListActivity.class);
        intent.putExtra(SELECT_TYPE, type);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra(SELECT_TYPE, TYPE_ALL);
        init();
        RequestCarList(LPSService.GetCarList_Request_Url);
    }

    @OnClick({R.id.back_llayout})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
        }
    }

    private void init() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constants.HOME))) {
            flag = true;
        }

        titltTextView.setText(Utils.getResourcesString(R.string.car_list));
        adapter = new MyFleetCarTreeAdapter(parents, this, this);
        carListLv.setAdapter(adapter);
    }

    private void showDia(boolean showDia) {
        if (loadingDialog == null) {
            loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        }

        if (showDia) {
            loadingDialog.show();
        }
    }

    private void dimiss() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dimiss();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    List<mCarInfo> carInfos = (List<mCarInfo>) msg.obj;
                    if (carInfos != null) {
                        parents.clear();
                        for (int i = 0; i < carInfos.size(); i++) {
                            if (carInfos.get(i) != null && carInfos.get(i).CarList != null) {
                                List<mCarInfo> tempList = carInfos.get(i).CarList;
//                                reSetOrgNameShow(tempList);
                                parents.addAll(tempList);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R.string.get_data_fail) : msg.obj.toString());
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(DriverCarTreeListActivity.this, Utils.getResourcesString(R.string.request_error));
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 重置第一条数据
     *
     * @param list
     */
    private void reSetOrgNameShow(List<mCarInfo> list) {
        mCarInfo info = list.get(0);
        info.isShowOrgName = true;
    }


    private void RequestCarList(final String Url) {
        showDia(true);
        DataRequestBase requestBase = new DataRequestBase();
        requestBase.DataValue = null;
        requestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        requestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue.UserType_Oid;
        requestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue.Company_Oid;
        requestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        JSONObject jsonObject = GsonUtil.newInstance().toJson(requestBase);

        VolleyManager.newInstance().PostJsonRequest(TAG, Url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                MLog.json(jsonObject.toString());
                Message msg = Message.obtain();
                GetCarListResponse carListResponse = JsonUtils.parseData(jsonObject.toString(), GetCarListResponse.class);
                if (carListResponse.Success) {
                    msg.what = Constants.REQUEST_SUCC;
                    msg.obj = carListResponse.DataValue;
                } else {
                    msg.what = Constants.REQUEST_FAIL;
                }
                mHandler.sendMessage(msg);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "-->>" + error.getMessage(), error);
                Message msg = Message.obtain();
                msg.what = Constants.REQUEST_ERROR;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    public void myOnClickItem(int position, Object data) {
        if (flag) {
            return;
        }

        mCarInfo carBean = (mCarInfo) data;
        if (carBean == null) {
            return;
        }

        if (type == TYPE_CAR) {
            if (carBean.CarCode.contains("挂")) {
                DialogUtils.showWarningToast("不能选择挂车！");
            } else {
                LocationChooseCarCallBackManager.newStance().fire(carBean);
                finish();
            }
        } else if (type == TYPE_GUA) {
            if (carBean.CarCode.contains("挂")) {
                Intent intent = new Intent();
                intent.putExtra("CarCode", carBean.CarCode);
                intent.putExtra("CarCodeKey", carBean.CarCode_Key);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                DialogUtils.showWarningToast("请选择挂车！");
            }
        } else {
            LocationChooseCarCallBackManager.newStance().fire(carBean);
            finish();
        }


    }
}
