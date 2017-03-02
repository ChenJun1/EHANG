package com.cvnavi.logistics.i51ehang.app.bean.driver.mode;

/**
 * 版权所有 势航网络
 * Created by ${ChenJ} on 2016/12/2.
 */

public class StartCarStatistics {
    private String StartCarNumber;    //当月发车
    private String THCarNumber;        //当月提货
    private String SHCarNumber; //当月送货

    public String getStartCarNumber() {
        return StartCarNumber;
    }

    public void setStartCarNumber(String startCarNumber) {
        StartCarNumber = startCarNumber;
    }

    public String getTHCarNumber() {
        return THCarNumber;
    }

    public void setTHCarNumber(String THCarNumber) {
        this.THCarNumber = THCarNumber;
    }

    public String getSHCarNumber() {
        return SHCarNumber;
    }

    public void setSHCarNumber(String SHCarNumber) {
        this.SHCarNumber = SHCarNumber;
    }
}
