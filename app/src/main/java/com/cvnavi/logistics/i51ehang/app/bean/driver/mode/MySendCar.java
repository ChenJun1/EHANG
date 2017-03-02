package com.cvnavi.logistics.i51ehang.app.bean.driver.mode;

import java.io.Serializable;

/**
 * Created by ${ChenJ} on 2016/10/9.
 */

public class MySendCar implements Serializable {
    /**
     * Letter_Oid : A0000002016091500001
     * Driver : 22222
     * Driver_Tel : 22222
     * CarCode : 2134
     * Line_Name : 总公司-北京测试TMS
     * Line_Type : 干线
     * CarCodeStatus : 未开始
     * Operate_DateTime : 2016-09-15 20:35:09
     * Shuttle_Fee : 0.0
     * TicketCount : 2
     * Goods_Num : 555
     * Goods_Weight : 1550.0
     * Bulk_Weight : 60.0
     */

    private String Letter_Oid;
    private String Driver;
    private String Driver_Tel;
    private String CarCode;
    private String BoxCarCode;//挂车号
    private String Line_Name;
    private String Line_Type;
    private String CarCodeStatus;
    private String Operate_DateTime;
    private String Leave_DateTime;//装车时间
    private String Arrive_DateTime;//到车时间
    private String Shuttle_Fee;
    private String TicketCount;
    private String Ticket_Count;//票数
    private String Goods_Num;
    private String Goods_Weight;
    private String Bulk_Weight;
    private String Driver_Key;
    private String LPSCarCode_Key;

    public String getCarCode_Key() {
        return CarCode_Key;
    }

    public void setCarCode_Key(String carCode_Key) {
        CarCode_Key = carCode_Key;
    }

    private String CarCode_Key;
    private String CarCode_Date;
    private String MainDriver;//	主驾驶;
    private String MainDriver_Tel;//	主驾驶电话;
    private String SecondDriver;//	副驾驶;
    private String SecondDriver_Tel;//	副驾驶电话;
    private String Shuttle_Oid;//	班次ID;
    private String Shuttle_No;//	班次号;
    private String Traffic_Mode;//	班次号;

    public String getTraffic_Mode() {
        return Traffic_Mode;
    }

    public void setTraffic_Mode(String traffic_Mode) {
        Traffic_Mode = traffic_Mode;
    }

    public String getTicket_Count() {
        return Ticket_Count;
    }

    public void setTicket_Count(String ticket_Count) {
        Ticket_Count = ticket_Count;
    }

    public String getMainDriver() {
        return MainDriver;
    }

    public void setMainDriver(String mainDriver) {
        MainDriver = mainDriver;
    }

    public String getMainDriver_Tel() {
        return MainDriver_Tel;
    }

    public void setMainDriver_Tel(String mainDriver_Tel) {
        MainDriver_Tel = mainDriver_Tel;
    }

    public String getSecondDriver() {
        return SecondDriver;
    }

    public void setSecondDriver(String secondDriver) {
        SecondDriver = secondDriver;
    }

    public String getSecondDriver_Tel() {
        return SecondDriver_Tel;
    }

    public void setSecondDriver_Tel(String secondDriver_Tel) {
        SecondDriver_Tel = secondDriver_Tel;
    }

    public String getShuttle_Oid() {
        return Shuttle_Oid;
    }

    public void setShuttle_Oid(String shuttle_Oid) {
        Shuttle_Oid = shuttle_Oid;
    }

    public String getShuttle_No() {
        return Shuttle_No;
    }

    public void setShuttle_No(String shuttle_No) {
        Shuttle_No = shuttle_No;
    }

    public String getCarCode_Date() {
        return CarCode_Date;
    }

    public void setCarCode_Date(String carCode_Date) {
        CarCode_Date = carCode_Date;
    }

    public String getDriver_Key() {
        return Driver_Key;
    }

    public void setDriver_Key(String driver_Key) {
        Driver_Key = driver_Key;
    }

    public String getLPSCarCode_Key() {
        return LPSCarCode_Key;
    }

    public void setLPSCarCode_Key(String LPSCarCode_Key) {
        this.LPSCarCode_Key = LPSCarCode_Key;
    }

    public String getLetter_Oid() {
        return Letter_Oid;
    }

    public void setLetter_Oid(String letter_Oid) {
        Letter_Oid = letter_Oid;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public String getDriver_Tel() {
        return Driver_Tel;
    }

    public void setDriver_Tel(String driver_Tel) {
        Driver_Tel = driver_Tel;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getBoxCarCode() {
        return BoxCarCode;
    }

    public void setBoxCarCode(String boxCarCode) {
        BoxCarCode = boxCarCode;
    }

    public String getLine_Name() {
        return Line_Name;
    }

    public void setLine_Name(String line_Name) {
        Line_Name = line_Name;
    }

    public String getLine_Type() {
        return Line_Type;
    }

    public void setLine_Type(String line_Type) {
        Line_Type = line_Type;
    }

    public String getCarCodeStatus() {
        return CarCodeStatus;
    }

    public void setCarCodeStatus(String carCodeStatus) {
        CarCodeStatus = carCodeStatus;
    }

    public String getOperate_DateTime() {
        return Operate_DateTime;
    }

    public void setOperate_DateTime(String operate_DateTime) {
        Operate_DateTime = operate_DateTime;
    }

    public String getLeave_DateTime() {
        return Leave_DateTime;
    }

    public void setLeave_DateTime(String leave_DateTime) {
        Leave_DateTime = leave_DateTime;
    }

    public String getArrive_DateTime() {
        return Arrive_DateTime;
    }

    public void setArrive_DateTime(String arrive_DateTime) {
        Arrive_DateTime = arrive_DateTime;
    }

    public String getShuttle_Fee() {
        return Shuttle_Fee;
    }

    public void setShuttle_Fee(String shuttle_Fee) {
        Shuttle_Fee = shuttle_Fee;
    }

    public String getTicketCount() {
        return TicketCount;
    }

    public void setTicketCount(String ticketCount) {
        TicketCount = ticketCount;
    }

    public String getGoods_Num() {
        return Goods_Num;
    }

    public void setGoods_Num(String goods_Num) {
        Goods_Num = goods_Num;
    }

    public String getGoods_Weight() {
        return Goods_Weight;
    }

    public void setGoods_Weight(String goods_Weight) {
        Goods_Weight = goods_Weight;
    }

    public String getBulk_Weight() {
        return Bulk_Weight;
    }

    public void setBulk_Weight(String bulk_Weight) {
        Bulk_Weight = bulk_Weight;
    }
}
