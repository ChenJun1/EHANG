package com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder;

import java.io.Serializable;

/**
 * Created by ${ChenJ} on 2016/7/29.
 */
public class ExceptionInfoBean implements Serializable{
    public String Serial_Oid;        //流水号
    public String All_Ticket_No;        //全票号
    public String Exception_Mode;        //异常类型
    public String Operate_DateTime;        //操作时间
    public String IMGCopies;

    //图片个数
    public String Exception_DateTime;     //操作时间
    public String Letter_Oid;
    public String Exception_Type; //异常类型

    public ExceptionInfoBean(){}

    public ExceptionInfoBean(String exception_Mode, String serial_Oid, String operate_DateTime, String IMGCopies) {
        Exception_Mode = exception_Mode;
        Serial_Oid = serial_Oid;
        Operate_DateTime = operate_DateTime;
        this.IMGCopies = IMGCopies;
    }
}
