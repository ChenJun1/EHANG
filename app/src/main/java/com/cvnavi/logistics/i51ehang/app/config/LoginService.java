package com.cvnavi.logistics.i51ehang.app.config;

/**
 * Created by JohnnyYuan on 2016/7/4.
 */
public class LoginService extends HostAddress {

    public static final String LoginService_Address = Host_Url + "/Login/Login/";

    // 获取验证码
    public static final String VerifyCode_TAG = "VerifyCode";
    public static final String VerifyCode_Request_Url = LoginService_Address + VerifyCode_TAG;

    // 获取版本号
    public static final String GetAppVersion_TAG = "GetAppVersion";
    public static final String GetAppVersion_Request_Url = LoginService_Address + GetAppVersion_TAG;

    // 获取APP下载地址
    public static final String GetAppDownPath_TAG = "GetAppDownPath";
    public static final String GetAppDownPath_Request_Url = LoginService_Address + GetAppDownPath_TAG;

    // 7日免登陆
    public static final String GetAppAutoLogin_TAG = "GetAppAutoLogin";
    public static final String GetAppAutoLogin_Request_Url = LoginService_Address + GetAppAutoLogin_TAG;

    // 登录
    public static final String GetAppLogin_TAG = "GetAppLogin";
    public static final String GetAppLogin_Request_Url = LoginService_Address + GetAppLogin_TAG;

    // 退出登录
    public static final String ExitLogin_TAG = "ExitLogin";
    public static final String ExitLogin_Request_Url = LoginService_Address + ExitLogin_TAG;

    //获取版本号
    public static final String GetVersion_TAG = "GetVersion";
    public static final String GetVersion_Request_Url = LoginService_Address + GetVersion_TAG;

    //APP下载地址
    public static final String GetAPP_TAG = "GetAppDownUrl";
    public static final String GetAPP_Request_Url = LoginService_Address + GetAPP_TAG;


    //我的信息
    public static final String GetShipperInfo_TAG = "GetShipperInfo";
    public static final String GetShipperInfo_Request_Url = LoginService_Address + GetShipperInfo_TAG;

    //后台服务检查token
    public static final String GetIsNewToken_TAG = "GetIsNewToken";
    public static final String GetIsNewToken_Request_Url = LoginService_Address + GetIsNewToken_TAG;

    //用户提供反馈的接口
    public static final String SetFeedBack_TAG  ="SetFeedBack";
    public static final String SetFeedBack_Request_Url  = LoginService_Address+ SetFeedBack_TAG;

    //首页
    public static final String GetHomePage_TAG  ="GetHomePage";
    public static final String GetHomePage_Request_Url  = LoginService_Address+ GetHomePage_TAG;

}
