package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * Created by JohnnyYuan on 2016/7/4.
 */
public class DataRequestBase extends PageRequestBase {

    /**
     * 所属系统
     */
    public String ActionSystem = "APP";

    /**
     * 用户Key
     */
    public String User_Key;

    /**
     * 用户类型
     */
    public String UserType_Oid;

    /**
     * 用户登录令牌Token
     */
    public String Token;

    /**
     * 公司ID
     */
    public String Company_Oid;

    public Object DataValue;

    public String Org_Code;//是	用户所属机构

    public String Org_Key;//是	用户所属机构

    public String Org_Name;//是	机构名称

    public String User_Name; //用户名

    public String User_Tel; //手机号

    public String AndroidVersion;//当前Android 版本号

}
