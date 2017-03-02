package com.cvnavi.logistics.i51ehang.app.activity.cargo.me;

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
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.FeedbackActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.MyInfoActivity;
import com.cvnavi.logistics.i51ehang.app.activity.employee.me.ReponseActivity;
import com.cvnavi.logistics.i51ehang.app.activity.login.LoginActivity;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.mode.ShipperStatistics;
import com.cvnavi.logistics.i51ehang.app.bean.cargo.response.GetShipperStatistics;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.config.TMSService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.HandlerUtils;
import com.cvnavi.logistics.i51ehang.app.utils.LogUtil;
import com.cvnavi.logistics.i51ehang.app.utils.LoggerUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SetViewValueUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

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
 * Depict: 我的 页面
 */

public class CargoMeFragment extends BaseFragment implements HandlerUtils.OnReceiveMessageListener{
    private static final String TAG = "CargoMeFragment";
    @BindView(R.id.reponse_iv)
    ImageView reponseIv;
    @BindView(R.id.reponse_rl)
    RelativeLayout reponseRl;
    @BindView(R.id.about_iv)
    ImageView aboutIv;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.about_rl)
    RelativeLayout aboutRl;
    @BindView(R.id.exit_app_btn)
    TextView exitAppBtn;
    @BindView(R.id.cargo_set_rl)
    RelativeLayout cargoSetRl;
    @BindView(R.id.pullToRefresh_sv)
    PullToRefreshScrollView mPullToRefreshSv;
    @BindView(R.id.ll_icon)
    LinearLayout mLlIcon;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.tv_Total_Fee)
    TextView mTvTotalFee;
    @BindView(R.id.tv_SendCount)
    TextView mTvSendCount;

    private HandlerUtils.HandlerHolder mHandlerHolder;
    public static CargoMeFragment instantiation() {
        return new CargoMeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cargo_me, container, false);
        ButterKnife.bind(this, view);
        mHandlerHolder=new HandlerUtils.HandlerHolder(this);
        lodingDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        mPullToRefreshSv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadDataRequest(TMSService.GetShipperStatistics_Url);
            }
        });
        mUserName.setText(MyApplication.getInstance().getLoginInfo().DataValue.User_Tel);
        loadDataRequest(TMSService.GetShipperStatistics_Url);
        return view;
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }

    //退出登录
    private void Exit() {
        //停止极光推送
        JPushInterface.stopPush(getActivity());
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_User_Key, null);

        VolleyManager.newInstance().PostJsonRequest(LoginService.ExitLogin_TAG, LoginService.ExitLogin_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.d(TAG, response.toString());
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

    @OnClick({R.id.reponse_rl, R.id.about_rl, R.id.cargo_set_rl, R.id.iv_icon,
            R.id.user_name, R.id.exit_app_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_name:
//                startActivity(new Intent(getActivity(), CargoMyinfoActivity.class));//以前的我的信息页面
                MyInfoActivity.startActivity(getActivity());
                break;
            case R.id.iv_icon:
                MyInfoActivity.startActivity(getActivity());
                break;
            case R.id.reponse_rl:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.about_rl:
                startActivity(new Intent(getActivity(), ReponseActivity.class));
                break;
            case R.id.cargo_set_rl:
                startActivity(new Intent(getActivity(), CargoSettingsActivity.class));
                break;
            case R.id.exit_app_btn:
                DialogUtils.showMessageDialog(getActivity(), "提示", "确定退出？", "确定", "取消", new CustomDialogListener() {
                    @Override
                    public void onDialogClosed(int closeType) {
                        if (CustomDialogListener.BUTTON_NO == closeType) {
                        } else if (CustomDialogListener.BUTTON_OK == closeType) {
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
            json.put("SendMan_Tel", MyApplication.getInstance().getLoginInfo().DataValue.User_Tel);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject=GsonUtil.newInstance().toJson
                (dataRequestBase);
        try {
            jsonObject.put("DataValue",json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // dataRequestBase.DataValue = json;
        LoggerUtil.json(TAG, jsonObject.toString());
        VolleyManager.newInstance().PostJsonRequest(TAG, Url,jsonObject , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LoggerUtil.json(TAG, response.toString());
                GetShipperStatistics response1 = GsonUtil.newInstance().fromJson(response.toString(),
                        GetShipperStatistics.class);
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

    private void SetViewData(ShipperStatistics mShipperStatistics) {
        SetViewValueUtil.setTextViewValue(mTvTotalFee, mShipperStatistics.getTotal_Fee());
        SetViewValueUtil.setTextViewValue(mTvSendCount, mShipperStatistics.getSendCount());
    }

    @Override
    public void handlerMessage(Message msg) {
        if (lodingDialog != null)
            lodingDialog.dismiss();

        mPullToRefreshSv.onRefreshComplete();
        switch (msg.what) {
            case Constants.REQUEST_SUCC:
                ShipperStatistics mShipperStatistics = (ShipperStatistics) msg.obj;
                if (mShipperStatistics != null)
                    SetViewData(mShipperStatistics);
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
}
