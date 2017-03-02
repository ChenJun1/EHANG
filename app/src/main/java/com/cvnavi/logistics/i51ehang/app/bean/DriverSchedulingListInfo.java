package com.cvnavi.logistics.i51ehang.app.bean;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/6/27.
 */
public class DriverSchedulingListInfo {
    public static int LINE_ZHI = 0;
    public static int LINE_GAN = 1;
    public static int TYPE_ONE = 0;
    public static int TYPE_TWO = 1;

    private int type = TYPE_ONE;//整车
    private int line = LINE_ZHI;//支路
    private int state;//状态，已配载，未配载
    private int order = 0;//顺序
    private String scheduleDate;//排班日期
    private String preDeparture;//预发车
    private String realDeparture;//实发车
    private String carLicense;//车牌号
    private String route;//线路


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getPreDeparture() {
        return preDeparture;
    }

    public void setPreDeparture(String preDeparture) {
        this.preDeparture = preDeparture;
    }

    public String getRealDeparture() {
        return realDeparture;
    }

    public void setRealDeparture(String realDeparture) {
        this.realDeparture = realDeparture;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
}
