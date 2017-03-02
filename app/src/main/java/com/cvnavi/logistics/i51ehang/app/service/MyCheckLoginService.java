package com.cvnavi.logistics.i51ehang.app.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils.MLog;
import com.cvnavi.logistics.i51ehang.app.bean.request.DataRequestBase;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.config.LoginService;
import com.cvnavi.logistics.i51ehang.app.utils.GsonUtil;
import com.cvnavi.logistics.i51ehang.app.utils.SharedPreferencesTool;

import org.json.JSONObject;

import volley.VolleyManager;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/29.
 */
public class MyCheckLoginService extends Service {


    private static final int mileSpace = 5000;//毫秒

    @Override
    public void onCreate() {//重写onCreate方法
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {//重写onBind方法
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {//重写onStartCommand方法
        getHttpHandler.sendEmptyMessage(0x12);
        return super.onStartCommand(intent, flags, startId);
    }


    private Handler getHttpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            checkLogin();
        }
    };

    private void checkLogin() {
        DataRequestBase dataRequestBase = new DataRequestBase();
        dataRequestBase.User_Key = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_User_Key, null);
        dataRequestBase.UserType_Oid = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_UserType_Oid, null);
        dataRequestBase.Token = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_Token, null);
        dataRequestBase.Company_Oid = SharedPreferencesTool.getString(SharedPreferencesTool.LOGIN_Company_Oid, null);
        VolleyManager.newInstance().PostJsonRequest(LoginService.GetIsNewToken_TAG, LoginService.GetIsNewToken_Request_Url, GsonUtil.newInstance().toJson(dataRequestBase), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                GetAppLoginResponse info = GsonUtil.newInstance().fromJson(jsonObject, GetAppLoginResponse.class);
                if (!info.Success && info.ErrorText.equals("请重新登录")) {
                    MLog.print("-->> check");
                    Intent intent = new Intent();//创建Intent对象
                    intent.setAction("com.cvnavi.logistics.i51eyun.app.MyCheckLoginService");
                    sendBroadcast(intent);//发送广播
                    return;
                }
                getHttpHandler.sendEmptyMessageDelayed(0x12, mileSpace);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    @Override
    public void onDestroy() {//重写onDestroy方法
        super.onDestroy();
    }
}
