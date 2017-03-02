package com.cvnavi.logistics.i51ehang.app.bean.eventbus;

/**
 * Created by george on 2016/10/10.
 * 时间
 */

public class TimeEvent {
    private String startTime;
    private String endTime;

    public TimeEvent(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
