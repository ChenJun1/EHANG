package com.cvnavi.logistics.i51ehang.app;

import com.baidu.mapapi.SDKInitializer;
import com.cvnavi.logistics.i51ehang.app.bean.model.CompanyBean;
import com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo;
import com.cvnavi.logistics.i51ehang.app.bean.model.mVerifyCode;
import com.cvnavi.logistics.i51ehang.app.bean.response.GetAppLoginResponse;
import com.cvnavi.logistics.i51ehang.app.cache.BasicDataBuffer;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.litepal.LitePalApplication;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * 继承litepal数据库的application
 */
public class MyApplication extends LitePalApplication {
    private volatile static MyApplication ms_MyApplication;
    private static final String TAG = "EHANG";
    private mVerifyCode verifyCode;


    public MyApplication() {
    }

    public static MyApplication getInstance() {
        if (ms_MyApplication == null) {
            synchronized (MyApplication.class) {
                if (ms_MyApplication == null) {
                    ms_MyApplication = new MyApplication();
                }
            }
        }
        return ms_MyApplication;
    }


    public mVerifyCode getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(mVerifyCode verifyCode) {
        this.verifyCode = verifyCode;
    }

    private GetAppLoginResponse loginInfo;//登录信息
    private List<mLineInfo.ShuttleListBean> ShuttleList;//班次列表
    private List<CompanyBean> companyBeanList;//公司列表


    public List<mLineInfo.ShuttleListBean> getShuttleList() {
        return ShuttleList;
    }

    public void setShuttleList(List<mLineInfo.ShuttleListBean> shuttleList) {
        ShuttleList = shuttleList;
    }

    public List<CompanyBean> getCompanyBeanList() {
        return companyBeanList;
    }

    public void setCompanyBeanList(List<CompanyBean> companyBeanList) {
        this.companyBeanList = companyBeanList;
    }

    public GetAppLoginResponse getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(GetAppLoginResponse loginInfo) {
        this.loginInfo = loginInfo;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ms_MyApplication = this;
        BasicDataBuffer.newInstance().init(this);
        init();
        JPushInterface.setDebugMode(Constants.IS_DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);            // 初始化 JPush
        Logger.init(TAG);
        // OkHttp 请求的一些配置 （设置连接超时以及读取时间的超时均为10秒）
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));//设置永久性cookie
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);//设置访问所有的https网址
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .cookieJar(cookieJar)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void init() {
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }


}
