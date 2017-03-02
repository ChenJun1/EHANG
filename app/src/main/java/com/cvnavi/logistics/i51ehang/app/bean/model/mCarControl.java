package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * 车辆监控
 * Created by JohnnyYuan on 2016/7/4.
 */
public class mCarControl implements Serializable {

    public String CarCodeKey;//请求不返回此字段

    public String CarCode;//	车辆Code
    public String CHS_Provice;//	省
    public String CHS_City;//	市
    public String CHS_Address;//	详细地址
    public String Ticket_No;//	运单号
    public String BLng;//	百度经度
    public String BLat;//	百度纬度
    public String GLng;//	谷歌经度
    public String GLat;//	谷歌纬度
    public String Status;//	车辆状态
    public String Serial_Oid;//	当前任务
    public String Speed;//	当前时速
    public String Mileage;//	行车里程
    public String StartAddress;//	起始地
    public String CarStartTime;//	起始时间
    public String CarArrivaTime;//	到车时间
    public String CarleaveTime;//	离开时间
    public String DestinationAddress;//	目的地
    public String Line_Oid;//线路ID
    public String Driver;           // 司机姓名
    public String Driver_Tel;    //司机电话
    public String IsOnline_Status;    //司机是否在线
    public String TSP_CarCode_Key;

    public List<mCarLineTrend> CarLineList;//车辆线路详细记录
    /**
     * 请求不返回
     */
    public String Letter_Oid;   //清单号
}
