package com.cvnavi.logistics.i51ehang.app.activity.employee.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.BaseFragment;
import com.cvnavi.logistics.i51ehang.app.Constants;
import com.cvnavi.logistics.i51ehang.app.R;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.ChoiceLineActivity;
import com.cvnavi.logistics.i51ehang.app.activity.driver.home.transportation.DriverManagerActivity;
import com.cvnavi.logistics.i51ehang.app.activity.login.LoginActivity;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.DataResponseBase;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.utils.DialogUtils;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;
import com.cvnavi.logistics.i51ehang.app.utils.Utils;
import com.cvnavi.logistics.i51ehang.app.widget.dialog.custom.CustomDialogListener;

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
*创建时间：2017/1/16 下午4:09
*描述：员工我的界面 包含退出当前账号的接口
************************************************************************************/

public class EmployeeMeFragment extends BaseFragment {
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.driver_iv)
    ImageView driverIv;
    @BindView(R.id.my_driver_rl)
    RelativeLayout myDriverRl;
    @BindView(R.id.line_iv)
    ImageView lineIv;
    @BindView(R.id.my_line_rl)
    RelativeLayout myLineRl;
    @BindView(R.id.desc_iv)
    ImageView descIv;
    @BindView(R.id.desc_rl)
    RelativeLayout descRl;
    @BindView(R.id.reponse_iv)
    ImageView reponseIv;
    @BindView(R.id.reponse_rl)
    RelativeLayout reponseRl;
    @BindView(R.id.about_iv)
    ImageView aboutIv;
    @BindView(R.id.about_rl)
    RelativeLayout aboutRl;
    @BindView(R.id.exit_app_btn)
    TextView exitAppBtn;

    public static EmployeeMeFragment instantiation() {
        return new EmployeeMeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_me, container, false);
        ButterKnife.bind(this, view);
        userName.setText(SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UER_TEL, ""));
        return view;
    }

    /**
     * 防止在切换fragment的时候出现重影
     *
     * @param menuVisible
     */
    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (this.getView() != null)
            this.getView()
                    .setVisibility(menuVisible ? View.VISIBLE : View.GONE);
    }


    @OnClick(R.id.exit_app_btn)
    public void onClick() {
        DialogUtils.showMessageDialog(getActivity(), "温馨提示", "要退出当前账号吗？", "确定", "取消", new CustomDialogListener() {
            @Override
            public void onDialogClosed(int closeType) {
                if (closeType == CustomDialogListener.BUTTON_OK) {
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
            }
        });

    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.REQUEST_SUCC:
                    //退出登录成功，保存Token，公司的oid
                    SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Token, null);
                    SharedPreferencesTool.putString(SharedPreferencesTool.LOGIN_Company_Oid, null);
                    skipActivity(getActivity(), LoginActivity.class);
                    break;
                case Constants.REQUEST_FAIL:
                    DialogUtils.showFailToast(Utils.getResourcesString(R.string.exit_login_fail));
                    break;
            }
        }
    };

    @OnClick({R.id.reponse_rl, R.id.about_rl, R.id.desc_rl, R.id.my_driver_rl, R.id.my_line_rl, R.id.info_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reponse_rl:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.about_rl:
                startActivity(new Intent(getActivity(), ReponseActivity.class));
                break;
            case R.id.desc_rl:
                CommonAddressActivity.startActivity(getActivity(), CommonAddressActivity.TYPE_FROM_ME);
                break;
            case R.id.my_driver_rl:
                DriverManagerActivity.start(getActivity(), 0x11);
                break;
            case R.id.my_line_rl:
                ChoiceLineActivity.start(getActivity(), 0x11);
                break;
            case R.id.info_ll:
                MyInfoActivity.startActivity(getActivity());
                break;
        }
    }

}
