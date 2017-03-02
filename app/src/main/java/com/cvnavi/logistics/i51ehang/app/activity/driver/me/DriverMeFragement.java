package com.cvnavi.logistics.i51ehang.app.activity.driver.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.MyApplication;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendcar.DrMySendCar;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.sendgoods.DrMySendGoods;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.activity.takegoods.DrMyTakeGoods;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.history.activity.Dr_HistorySendCar;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.history.activity.Dr_HistorySendGoods;
import com.cvnavi.logistics.i51ehang.app.activity.driver.driverce.history.activity.Dr_HistoryTakeGoods;
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.FeedbackActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.MyInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.ReponseActivity;
import com.cvnavi.logistics.i51ehang.app.activity.login.LoginActivity;
import com.cvnavi.logistics.i51ehang.app.bean.driver.mode.StartCarStatistics;
import com.cvnavi.logistics.i51ehang.app.bean.driver.response.ResponseDataBean;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.config.DriverService;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.king.photo.util.PublicWay;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import volley.VolleyManager;

/**
 * 版权所有 上海势航网络科技
 * Created:  ChenJun
 * Email:  791954958@qq.com
 * Phone:  17774530310
 * CreatedTime:  2017/1/16 14:10
 * version: 2.3.2
 * Depict:司机我的页面
 */
public class DriverMeFragement extends BaseFragment {
    private static final String TAG = "DriverMeFragement";
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.contact_iv)
    ImageView contactIv;
    @BindView(R.id.contacts_rl)
    RelativeLayout contactsRl;
    @BindView(R.id.reponse_iv)
    ImageView reponseIv;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.reponse_rl)
    RelativeLayout reponseRl;
    @BindView(R.id.about_iv)
    ImageView aboutIv;
    @BindView(R.id.about_rl)
    RelativeLayout aboutRl;
    @BindView(R.id.exit_app_btn)
    TextView exitAppBtn;
    @BindView(R.id.seng_car_rl)
    RelativeLayout mSengCarRl;
    @BindView(R.id.take_goods_rl)
    RelativeLayout mTakeGoodsRl;
    @BindView(R.id.seng_goood_rl)
    RelativeLayout mSengGooodRl;
    @BindView(R.id.ll_icon)
    LinearLayout mLlIcon;
    @BindView(R.id.pullToRefresh_sv)
    PullToRefreshScrollView mPullToRefreshSv;
    @BindView(R.id.tv_StartCarNumber)
    TextView mTvStartCarNumber;
    @BindView(R.id.tv_THCarNumber)
    TextView mTvTHCarNumber;
    @BindView(R.id.tv_SHCarNumber)
    TextView mTvSHCarNumber;
    @BindView(R.id.month_send_car_ll)
    LinearLayout mMonthSendCarLl;
    @BindView(R.id.month_take_order_ll)
    LinearLayout mMonthTakeOrderLl;
    @BindView(R.id.month_send_order_ll)
    LinearLayout mMonthSendOrderLl;

    public static DriverMeFragement getInstance() {
        return new DriverMeFragement();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_driverce_me, container, false);
        ButterKnife.bind(this, view);
        lodingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        mPullToRefreshSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadDataRequest(DriverService.GetStartCarStatistics_Request_Url);
            }
        });
        userName.setText(SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UER_TEL, ""));
        loadDataRequest(DriverService.GetStartCarStatistics_Request_Url);
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    private void Exit() {
        JPushInterface.stopPush(getActivity());//停止推送
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_User_Key, null);

        VolleyManager.newInstance().PostJsonRequest(LoginService.ExitLogin_TAG, LoginService.ExitLogin_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Message message = Message.obtain();
                DataResponseBase dataResponseBase = GsonUtil.newInstance().fromJson(response, DataResponseBase.class);
                if (dataResponseBase.Success) {
                    message.what = Constants.REQUEST_SUCC;

                } else {
                    message.what = Constants.REQUEST_FAIL;
                }

                handler.sendMessage(message);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = Message.obtain();
                message.what = Constants.REQUEST_FAIL;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Token, null);
                    skipActivity(getActivity(), LoginActivity.class);
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.exit_login_fail));
                    break;
            }

        }
    };

    @OnClick({R.id.seng_car_rl, R.id.take_goods_rl, R.id.seng_goood_rl, R.id.contacts_rl, R.id.reponse_rl,
            R.id.about_rl, R.id.iv_icon, R.id.user_name, R.id.exit_app_btn, R.id.month_send_car_ll,
            R.id.month_take_order_ll, R.id.month_send_order_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.contacts_rl:
                break;
            case R.id.reponse_rl:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.about_rl:
                startActivity(new Intent(getActivity(), ReponseActivity.class));
                break;
            case R.id.seng_car_rl:
                //历史发车
//                DrMySendCar.start(getActivity());
                Dr_HistorySendCar.start(getActivity());
                break;
            case R.id.take_goods_rl:
                //历史提货
//                DrMyTakeGoods.start(getActivity());
                Dr_HistoryTakeGoods.start(getActivity());
                break;
            case R.id.seng_goood_rl:
                //历史发货
//                DrMySendGoods.start(getActivity());
                Dr_HistorySendGoods.start(getActivity());
                break;
            case R.id.iv_icon:
                MyInfoActivity.startActivity(getActivity());
                break;
            case R.id.user_name:
                MyInfoActivity.startActivity(getActivity());
                break;
            case R.id.month_send_car_ll:
                DrMySendCar.start(getActivity(), DrMySendCar.WAN);
                break;
            case R.id.month_take_order_ll:
                DrMyTakeGoods.start(getActivity(), DrMyTakeGoods.TOTAL);
                break;
            case R.id.month_send_order_ll:
                DrMySendGoods.start(getActivity(), DrMySendGoods.TOTAL);
                break;
            case R.id.exit_app_btn:
                DialogUtils.showMessageDialog(getActivity(), "提示", "确定退出？", "确定", "取消", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        if (CustomDialogListener.BUTTON_NO == closeType) {
                        } else if (CustomDialogListener.BUTTON_OK == closeType) {
                            for (Activity activity : PublicWay.activityList) {
                                if (activity.isFinishing() == false) {
                                    activity.finish();
                                }
                            }
                            PublicWay.activityList.clear();
                            Exit();
                        }
                    }
                });
                break;
        }
    }

    private SweetAlertDialog lodingDialog;
    private DataRequestBase dataRequestBase;

    private void loadDataRequest(final String Url) {
        lodingDialog.show();
        dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = MyApplication.getInstance().getLoginInfo().DataValue.User_Key;
        dataRequestBase.UserType_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .UserType_Oid;
        dataRequestBase.Token = MyApplication.getInstance().getLoginInfo().DataValue.Token;
        dataRequestBase.Company_Oid = MyApplication.getInstance().getLoginInfo().DataValue
                .Company_Oid;
        dataRequestBase.Org_Code = MyApplication.getInstance().getLoginInfo().DataValue.Org_Code;
        dataRequestBase.Org_Name = MyApplication.getInstance().getLoginInfo().DataValue.Org_Name;
        JSONObject json = new JSONObject();
        try {
            json.put("Driver_Tel", MyApplication.getInstance().getLoginInfo().DataValue.User_Tel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  dataRequestBase.DataValue = json; //JsonUtils.toJsonData(getDriverListRequest);
        JSONObject jsonObject = GsonUtil.newInstance().toJson
                (dataRequestBase);
        try {
            jsonObject.put("DataValue", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LoggerUtil.json(TAG, jsonObject.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(TAG, response.toString());
                ResponseDataBean response1 = GsonUtil.newInstance().fromJson(response.toString(),
                        ResponseDataBean.class);
                Message msg = Message.obtain();
                if (response1 == null) {
                    msg.obj = response1.ErrorText;
                    msg.what = Constants.REQUEST_FAIL;
                    myHandler.sendMessage(msg);
                } else {
                    if (response1.Success) {
                        msg.obj = response1.DataValue;
                        msg.what = Constants.REQUEST_SUCC;
                        myHandler.sendMessage(msg);
                    } else {
                        msg.obj = response1.ErrorText;
                        msg.what = Constants.REQUEST_FAIL;
                        myHandler.sendMessage(msg);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.d("-->>" + error.toString());
                Message msg = Message.obtain();
                msg.obj = error.getMessage();
                msg.what = Constants.REQUEST_ERROR;
                myHandler.sendMessage(msg);
            }
        });
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (lodingDialog != null)
                lodingDialog.dismiss();

            mPullToRefreshSv.onRefreshComplete();
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    StartCarStatistics msCarStatistics = (StartCarStatistics) msg.obj;
                    if (msCarStatistics != null)
                        SetViewData(msCarStatistics);
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showNormalToast(msg.obj == null ? Utils.getResourcesString(R
                            .string.request_Fill) : msg.obj.toString());
                    break;
                case Constants.DELETE_SUCC:
                    DialogUtils.showNormalToast(Utils.getResourcesString(R.string.dele_succ));
                    break;
                case Constants.REQUEST_ERROR:
                    DialogUtils.showMessageDialogOfDefaultSingleBtn(getActivity(), Utils
                            .getResourcesString(R.string.request_error));
                    break;


            }
        }
    };

    private void SetViewData(StartCarStatistics msCarStatistics) {
        SetViewValueUtil.setTextViewIntValue(mTvStartCarNumber, msCarStatistics.getStartCarNumber());
        SetViewValueUtil.setTextViewIntValue(mTvTHCarNumber, msCarStatistics.getTHCarNumber());
        SetViewValueUtil.setTextViewIntValue(mTvSHCarNumber, msCarStatistics.getSHCarNumber());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {

            handler.removeCallbacksAndMessages(null);
        }
        handler = null;
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        myHandler = null;
    }
}
