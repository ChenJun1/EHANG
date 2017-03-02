package com.cvnavi.logistics.i51ehang.app.config;

import static com.cvnavi.logistics.i51ehang.app.config.HostAddress.Host_Url;

/**
 * Created by george on 2016/11/3.
 * 员工服务列表
 */

public class EmployeeService {
    public static final String Employee_Address = Host_Url + "/Employee/Employee/";

    //常用目的地
    public static final String GetDestination_TAG = "GetDestination";
    public static final String GetDestination_URL = Employee_Address + GetDestination_TAG;

    //发车监控
    public static final String CarTimeMonitorList_Tag = "CarTimeMonitorList";
    public static final String CarTimeMonitorList_Url = Employee_Address + CarTimeMonitorList_Tag;


    //到车监控
    public static final String GetMonitorLineInfo_Tag = "GetMonitorLineInfo";
    public static final String GetMonitorLineInfo_Url = Employee_Address + GetMonitorLineInfo_Tag;


    //财务统计
    public static final String GetStatisticsCollect_Tag = "GetStatisticsCollect";
    public static final String GetStatisticsCollect_Url = Employee_Address + GetStatisticsCollect_Tag;

    //财务明细
    public static final String GetStatisticsTicket_Tag = "GetStatisticsTicket";
    public static final String GetStatisticsTicket_Url = Employee_Address + GetStatisticsTicket_Tag;

    //库存
    public static final String GetGoods_Room_Collect_Tag = "GetGoods_Room_Collect";
    public static final String GetGoods_Room_Collect_Url = Employee_Address + GetGoods_Room_Collect_Tag;

    //库存明细
    public static final String GetGoods_Room_CollectDetails_Tag = "GetGoods_Room_CollectDetails";
    public static final String GetGoods_Room_CollectDetails_Url = Employee_Address + GetGoods_Room_CollectDetails_Tag;

    //派车单
    public static final String GetReadCarStatistics_Tag = "GetReadCarStatistics";
    public static final String GetReadCarStatistics_Url = Employee_Address + GetReadCarStatistics_Tag;

    //派车单明细
    public static final String GetReadDataCarDetail_Tag = "GetReadDataCarDetail";
    public static final String GetReadDataCarDetail_Url = Employee_Address + GetReadDataCarDetail_Tag;

    //派车单详情
    public static final String GetDataCarDetailByOid_Tag = "GetDataCarDetailByOid";
    public static final String GetDataCarDetailByOid_Url = Employee_Address + GetDataCarDetailByOid_Tag;

    //委托统计
    public static final String GetDelegateStatistics_Tag = "GetDelegateStatistics";
    public static final String GetDelegateStatistics_Url = Employee_Address + GetDelegateStatistics_Tag;

    //委托明细
    public static final String GetDelegateList_Tag = "GetDelegateList";
    public static final String GetDelegateList_Url = Employee_Address + GetDelegateList_Tag;

    //委托明细
    public static final String GetDelegateDetailByOid_Tag = "GetDelegateDetailByOid";
    public static final String GetDelegateDetailByOid_Url = Employee_Address + GetDelegateDetailByOid_Tag;

    //获取机构号
    public static final String GetOrgCodebyCompanOid_Tag = "GetOrgCodebyCompanOid";
    public static final String GetOrgCodebyCompanOid_Url = Employee_Address + GetOrgCodebyCompanOid_Tag;

    //获取公司
    public static final String OrgGPS_Tag = "OrgGPS";
    public static final String OrgGPS_Url = Employee_Address + OrgGPS_Tag;

    //发车监控车辆筛选接口
    public static final String GetOnWayCar_Tag = "GetOnWayCar";
    public static final String GetOnWayCar_Url = Employee_Address + GetOnWayCar_Tag;

    //发车监控车辆筛选接口
    public static final String GetLinesName_Tag = "GetLinesName";
    public static final String GetLinesName_URL = Employee_Address + GetLinesName_Tag;




}
