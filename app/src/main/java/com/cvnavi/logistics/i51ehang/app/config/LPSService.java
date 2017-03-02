package com.cvnavi.logistics.i51ehang.app.config;

/**
 * Created by JohnnyYuan on 2016/7/4.
 */
public class LPSService extends HostAddress {
    public static final String LPSService_Address = Host_Url + "/LPS/LPS/";
    public static final String Employee_Address = Host_Url + "/Employee/Employee/";

    // 车辆列表查询
    public static final String GetCarList_TAG = "GetCarList";
    public static final String GetCarList_Request_Url = Employee_Address + GetCarList_TAG;

//    // 车辆监控
//    public static final String GetGPSInfo_TAG = "GetGPSInfo";
//    public static final String GetGPSInfo_Request_Url = Employee_Address + GetGPSInfo_TAG;

    // 历史轨迹记录分析
    public static final String GetHistoryLocusAnalysis_TAG = "GetHistoryLocusAnalysis";
    public static final String GetHistoryLocusAnalysis_Request_Url = Employee_Address + GetHistoryLocusAnalysis_TAG;

    // 历史轨迹记录详情
    public static final String GetHistoryLocus_TAG = "GetHistoryLocus";
    public static final String GetHistoryLocus_Request_Url = Employee_Address + GetHistoryLocus_TAG;

    // 获取车辆key，by车牌号
    public static final String GetCarCodeKey_TAG = "GetCarCodeKey";
    public static final String GetCarCodeKey_Request_Url = Employee_Address + GetCarCodeKey_TAG;


    public static final String GetMileages_TAG = "GetMileages";
//    public static String GetMileages_Request_Url = LPSService_Address + GetMileages_TAG;
    public static final String GetMileages_Request_Url = Employee_Address + GetMileages_TAG;

    //定位信息
    public static final String GetCarGPSAggregate_TAG = "GetCarGPSAggregate";
    public static final String GetCarGPSAggregate_Request_Url = Employee_Address + GetCarGPSAggregate_TAG;
  //报警信息
    public static final String GetAlarmInfo_TAG = "GetAlarmInfo";
    public static final String GetAlarmInfo_Request_Url = Employee_Address + GetAlarmInfo_TAG;

    //我的车队
    public static final String GetMyCarFleet_TAG = "GetMyCarFleet";
    public static final String GetMyCarFleet_Request_Url = Employee_Address + GetMyCarFleet_TAG;


}
