package com.cvnavi.logistics.i51ehang.app.activity.driver.home.statistics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseActivity;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.adapter.home.statistics.DriverMileageSearchAdapter;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.request.GetMileagesRequest;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetMileageResponse;
import com.cvnavi.logistics.i51ehang.app.config.LPSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.LoadingDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

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
*创建时间：2017/1/17 下午2:06
*描述：里程统计搜索的结果
************************************************************************************/

public class DriverMileageSearchDeatilActivity extends BaseActivity {
    public static final String Intent_INFO_START_TIME = "Intent_INFO_START_TIME";
    public static final String Intent_INFO_END_TIME = "Intent_INFO_END_TIME";
    public static final String Intent_INFO_CARCODE_KEY = "Intent_INFO_CARCODE_KEY";
    @BindView(R.id.back_llayout)
    LinearLayout backLlayout;
    @BindView(R.id.titlt_tv)
    TextView titltTv;
    @BindView(R.id.list)
    PullToRefreshListView list;
    @BindView(R.id.total_mile)
    TextView totalMile;
    @BindView(R.id.car_code_tv)
    TextView carCodeTv;
    @BindView(R.id.total_ll)
    RelativeLayout totalLl;
    private Context context;

    private DriverMileageSearchAdapter adapter;

    private LoadingDialog dialog;

    public static void startActivity(Activity activity, String starTime, String endTime, String carCode_Key) {
        Intent intent = new Intent(activity, DriverMileageSearchDeatilActivity.class);
        intent.putExtra(Intent_INFO_START_TIME, starTime);
        intent.putExtra(Intent_INFO_END_TIME, endTime);
        intent.putExtra(Intent_INFO_CARCODE_KEY, carCode_Key);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mileage_search_detail);
        ButterKnife.bind(this);
        context = this;
        Intent dataInfo = getIntent();
        if (dataInfo != null) {
            getListInfo(dataInfo.getStringExtra(Intent_INFO_START_TIME), dataInfo.getStringExtra(Intent_INFO_END_TIME), dataInfo.getStringExtra(Intent_INFO_CARCODE_KEY));
        } else {
            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                @Override
                public void onDialogClosed(int closeType) {
                    if (closeType == CustomDialogListener.BUTTON_OK) {
                        DriverMileageSearchDeatilActivity.this.finish();
                    }

                }
            });

        }

    }


    private void getListInfo(String starTime, String endTime, String carCode_Key) {
        showLoading();
        GetAppLoginResponse loginInfo = MyApplication.getInstance().getLoginInfo();
        if (loginInfo != null) {
            GetMileagesRequest dataValue = new GetMileagesRequest();
            dataValue.CarCode_Key = carCode_Key;
            dataValue.StartTime = starTime;
            dataValue.EndTime = endTime;

            DataRequestBase dataRequestBase = new DataRequestBase();
            dataRequestBase.DataValue = dataValue;
            dataRequestBase.User_Key = loginInfo.DataValue.User_Key;
            dataRequestBase.UserType_Oid = loginInfo.DataValue.UserType_Oid;
            dataRequestBase.Token = loginInfo.DataValue.Token;
            dataRequestBase.Company_Oid = loginInfo.DataValue.Company_Oid;

            LogUtil.d("-->> request = " + new Gson().toJson(dataRequestBase));

            VolleyManager.newInstance().PostJsonRequest(LPSService.GetMileages_TAG, LPSService.GetMileages_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    LogUtil.d("-->> response = " + response.toString());
                    GetMileageResponse info = GsonUtil.newInstance().fromJson(response, GetMileageResponse.class);
                    Message msg = Message.obtain();

                    if (info != null) {
                        if (info.isSuccess()) {
                            msg.what = Constants.REQUEST_SUCC;
                            msg.obj = info;
                        } else {
                            msg.what = Constants.REQUEST_FAIL;
                        }

                    } else {
                        msg.what = Constants.REQUEST_FAIL;
                    }
                    handler.sendMessage(msg);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Message msg = Message.obtain();
                    msg.what = Constants.REQUEST_ERROR;
                    handler.sendMessage(msg);

                }
            });


        }

    }


//    private void showLoading() {
//        if (dialog == null) {
//            dialog = LoadingDialog.getInstance(this);
//        }
//        dialog.setMessage(Utils.getResourcesString(R.string.el_loading_message));
//        dialog.show();
//    }
//
//    private void dismissDia() {
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            dismissDia();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    GetMileageResponse info = (GetMileageResponse) msg.obj;
                    if (info != null) {
                        if (info.getDataValue().getListMileage().size() == 0) {
                            titltTv.setText(Utils.getResourcesString(R.string.mile_list_null));
                            DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverMileageSearchDeatilActivity.this, Utils.getResourcesString(R.string.mile_list_null), new CustomDialogListener() {
                                @Override
                                public void onDialogClosed(int closeType) {
                                    if (closeType == CustomDialogListener.BUTTON_OK) {
                                        DriverMileageSearchDeatilActivity.this.finish();
                                    }

                                }
                            });
                            totalLl.setVisibility(View.GONE);
                        } else {
                            LogUtil.d("-->> list = " + info.getDataValue().getListMileage() + "||size = " + info.getDataValue().getListMileage().size());
                            adapter = new DriverMileageSearchAdapter(info.getDataValue().getListMileage(), context);
                            list.setAdapter(adapter);
                            titltTv.setText(info.getDataValue().getCarCode());
                            carCodeTv.setText(info.getDataValue().getCarCode());
                            totalLl.setVisibility(View.VISIBLE);
                            totalMile.setText(String.format(Utils.getResourcesString(R.string.total_mile), info.getDataValue().getTotalMileage()));
                        }
                    } else {
                        titltTv.setText(info.getDataValue().getCarCode());
                        totalLl.setVisibility(View.GONE);
                        DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverMileageSearchDeatilActivity.this, Utils.getResourcesString(R.string.get_data_fail), new CustomDialogListener() {
                            @Override
                            public void onDialogClosed(int closeType) {
                                if (closeType == CustomDialogListener.BUTTON_OK) {
                                    DriverMileageSearchDeatilActivity.this.finish();
                                }

                            }
                        });
                    }

                    break;
                case Constants.REQUEST_FAIL:
                    titltTv.setText(Utils.getResourcesString(R.string.mile_list_null));
                    totalLl.setVisibility(View.GONE);
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverMileageSearchDeatilActivity.this, Utils.getResourcesString(R.string.mile_list_error), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            if (closeType == CustomDialogListener.BUTTON_OK) {
                                DriverMileageSearchDeatilActivity.this.finish();
                            }

                        }
                    });
                    break;
                case Constants.REQUEST_ERROR:
                    titltTv.setText(Utils.getResourcesString(R.string.mile_list_null));
                    totalLl.setVisibility(View.GONE);
                    DialogUtils.showMessageDialogOfDefaultSingleBtnNoCancel(DriverMileageSearchDeatilActivity.this, Utils.getResourcesString(R.string.request_error), new CustomDialogListener() {
                        @Override
                        public void onDialogClosed(int closeType) {
                            if (closeType == CustomDialogListener.BUTTON_OK) {
                                DriverMileageSearchDeatilActivity.this.finish();
                            }

                        }
                    });
                    break;
            }


        }
    };


    @OnClick(R.id.back_llayout)
    public void onClick() {
        finish();
    }
}
