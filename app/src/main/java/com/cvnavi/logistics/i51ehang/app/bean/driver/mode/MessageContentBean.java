package com.cvnavi.logistics.i51ehang.app.bean.driver.mode;

/**
 * 版权所有 势航网络
 * Created by ${ChenJ} on 2016/11/8.
 */

public class MessageContentBean {
    private String Serial_Oid;
    private String Company_Oid;
    private String User_Oid;
    private String Operate_Code;
    private String Operate_DateTime;
    private String Content;
    private String Ticket_No;
    private String All_Ticket_No;
    private String Letter_Oid;
    private String IsRead;

    public String getIsRead() {
        return IsRead;
    }

    public void setIsRead(String isRead) {
        IsRead = isRead;
    }

    public String getTicket_No() {
        return Ticket_No;
    }

    public void setTicket_No(String ticket_No) {
        Ticket_No = ticket_No;
    }

    public String getAll_Ticket_No() {
        return All_Ticket_No;
    }

    public void setAll_Ticket_No(String all_Ticket_No) {
        All_Ticket_No = all_Ticket_No;
    }

    public String getLetter_Oid() {
        return Letter_Oid;
    }

    public void setLetter_Oid(String letter_Oid) {
        Letter_Oid = letter_Oid;
    }

    public MessageContentBean(){

    }
    public MessageContentBean(String Serial_Oid,String User_Oid){
        this.Serial_Oid=Serial_Oid;
        this.User_Oid=User_Oid;
    }

    public MessageContentBean(String serial_Oid, String company_Oid, String user_Oid, String operate_Code, String operate_DateTime, String content) {
        Serial_Oid = serial_Oid;
        Company_Oid = company_Oid;
        User_Oid = user_Oid;
        Operate_Code = operate_Code;
        Operate_DateTime = operate_DateTime;
        Content = content;
    }

    public String getSerial_Oid() {
        return Serial_Oid;
    }

    public void setSerial_Oid(String serial_Oid) {
        Serial_Oid = serial_Oid;
    }

    public String getCompany_Oid() {
        return Company_Oid;
    }

    public void setCompany_Oid(String company_Oid) {
        Company_Oid = company_Oid;
    }

    public String getUser_Oid() {
        return User_Oid;
    }

    public void setUser_Oid(String user_Oid) {
        User_Oid = user_Oid;
    }

    public String getOperate_Code() {
        return Operate_Code;
    }

    public void setOperate_Code(String operate_Code) {
        Operate_Code = operate_Code;
    }

    public String getOperate_DateTime() {
        return Operate_DateTime;
    }

    public void setOperate_DateTime(String operate_DateTime) {
        Operate_DateTime = operate_DateTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
