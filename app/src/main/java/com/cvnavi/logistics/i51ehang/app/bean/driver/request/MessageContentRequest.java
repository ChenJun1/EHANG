package com.cvnavi.logistics.i51ehang.app.bean.driver.request;

/**
 * 版权所有 势航网络
 * Created by ${ChenJ} on 2016/11/8.
 */

public class MessageContentRequest {
    public String Serial_Oid;
    public String User_Oid;
    public String Operate_Code;
    public String BeginDate;
    public String EndDate;

    public MessageContentRequest(){}
    public MessageContentRequest(String User_Oid){
        this.User_Oid=User_Oid;
    }

}
