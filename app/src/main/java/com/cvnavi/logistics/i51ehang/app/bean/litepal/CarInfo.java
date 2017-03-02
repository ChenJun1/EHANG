package com.cvnavi.logistics.i51ehang.app.bean.litepal;

import org.litepal.crud.DataSupport;

/**
 * Created by george on 2016/10/7.
 * <p>
 * Gps中carInfo
 */

public class CarInfo extends DataSupport {
    private String userTel;//手机号
    private String CarCode;//车牌号
    private String CarCode_Key;//车辆Key
    private String Device_Status_Oid;//设备是否启用
    private String Line_Oid;//线路ID
    private String Driver;           // 司机姓名
    private String Driver_Tel;    //司机电话

    private String TSP_CarCode_Key;
    private String Extend_One;
    private String Extend_Two;
    private String Extend_Three;

    public String getTSP_CarCode_Key() {
        return TSP_CarCode_Key;
    }

    public void setTSP_CarCode_Key(String TSP_CarCode_Key) {
        this.TSP_CarCode_Key = TSP_CarCode_Key;
    }

    public String getExtend_One() {
        return Extend_One;
    }

    public void setExtend_One(String extend_One) {
        Extend_One = extend_One;
    }

    public String getExtend_Two() {
        return Extend_Two;
    }

    public void setExtend_Two(String extend_Two) {
        Extend_Two = extend_Two;
    }

    public String getExtend_Three() {
        return Extend_Three;
    }

    public void setExtend_Three(String extend_Three) {
        Extend_Three = extend_Three;
    }


    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getCarCode() {
        return CarCode;
    }

    public void setCarCode(String carCode) {
        CarCode = carCode;
    }

    public String getCarCode_Key() {
        return CarCode_Key;
    }

    public void setCarCode_Key(String carCode_Key) {
        CarCode_Key = carCode_Key;
    }

    public String getDevice_Status_Oid() {
        return Device_Status_Oid;
    }

    public void setDevice_Status_Oid(String device_Status_Oid) {
        Device_Status_Oid = device_Status_Oid;
    }

    public String getLine_Oid() {
        return Line_Oid;
    }

    public void setLine_Oid(String line_Oid) {
        Line_Oid = line_Oid;
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
}
