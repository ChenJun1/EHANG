package com.cvnavi.logistics.i51ehang.app.bean.model.gpstrack;

import java.util.List;

/**
 * Created by george on 2016/10/11.
 */

public class GpsTrackDeatilInfo {


    /**
     * CarCode :
     * CarCode_Key :
     * MapLongitude : 121.51737840103092
     * MapLatitude : 30.957587264564324
     * GPSSpeed : 0.0
     * Direction : 89
     * Mileage : 120364.4
     * Position_DateTime :
     * GPSTime : 2016-10-10 15:17:08
     * LocationDesc : 上海市>奉贤区>奉贤区农民街与南百公路交叉口南偏西27米
     */

    private String CarCode;
    private String CarCode_Key;
    private String MapLongitude;
    private String MapLatitude;
    private String GPSSpeed;
    private String Direction;
    private String Mileage;
    private String Position_DateTime;
    private String GPSTime;
    private String LocationDesc;

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

    public String getMapLongitude() {
        return MapLongitude;
    }

    public void setMapLongitude(String mapLongitude) {
        MapLongitude = mapLongitude;
    }

    public String getMapLatitude() {
        return MapLatitude;
    }

    public void setMapLatitude(String mapLatitude) {
        MapLatitude = mapLatitude;
    }

    public String getGPSSpeed() {
        return GPSSpeed;
    }

    public void setGPSSpeed(String GPSSpeed) {
        this.GPSSpeed = GPSSpeed;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getMileage() {
        return Mileage;
    }

    public void setMileage(String mileage) {
        Mileage = mileage;
    }

    public String getPosition_DateTime() {
        return Position_DateTime;
    }

    public void setPosition_DateTime(String position_DateTime) {
        Position_DateTime = position_DateTime;
    }

    public String getGPSTime() {
        return GPSTime;
    }

    public void setGPSTime(String GPSTime) {
        this.GPSTime = GPSTime;
    }

    public String getLocationDesc() {
        return LocationDesc;
    }

    public void setLocationDesc(String locationDesc) {
        LocationDesc = locationDesc;
    }
}
