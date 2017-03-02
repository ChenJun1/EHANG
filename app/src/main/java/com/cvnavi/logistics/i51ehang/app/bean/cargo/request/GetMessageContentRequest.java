package com.cvnavi.logistics.i51ehang.app.bean.cargo.request;

/**
 * Created by fan on 2016/11/8.
 */

public class GetMessageContentRequest {
    public String Serial_Oid;//	String	否	ID
    public String User_Oid;//	15211111101	否	用户ID或电话
    public String Operate_Code;//	String	否	操作码
    public String BeginDate	;//时间格式String	否	起始操作时间
    public String EndDate	;//时间格式String	否	截至操作时间
}
