package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;

/**
 * Created by fan on 2016/7/8.
 */
public class mCarQuery implements Serializable {
    public String StartTime;
    public String EndTime;

//    public String BLng;//百度经度
//    public String BLat;//百度维度

    public String getCarCodeKey() {
        return carCodeKey;
    }

    public void setCarCodeKey(String carCodeKey) {
        this.carCodeKey = carCodeKey;
    }

    public String carCodeKey;

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }
}
