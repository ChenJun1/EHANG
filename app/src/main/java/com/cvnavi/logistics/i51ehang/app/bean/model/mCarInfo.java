package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.util.List;

/**
 * 车辆列表查询
 * Created by JohnnyYuan on 2016/7/4.
 */
public class mCarInfo {

    public String id;//自增ID
    public String parentId;//机构自增ID
    public String CarCode;//车牌号
    public String CarCode_Key;//车辆Key
    public String CarCode_Type_Oid;//车辆类型
    public String CarCode_Type;//类型
    public String Icon_Type_Oid;//车辆图标
    public String TemperatureCount;//温度计数量
    public String SIM_Oid;//sim卡号
    public String Org_Code;//车辆所属的机构code
    public String Use_Org;//车辆所属的机构code
    public String Device_Status_Oid;//设备是否启用
    public String Org_Name;//所属机构名字
    public String Content;//线路
    public String Line_Oid;//线路ID
    public String Driver;           // 司机姓名
    public String Driver_Tel;    //司机电话
    public String IsOnline_Status;    //司机是否在线
    public String TSP_CarCode_Key;
    public boolean isShowOrgName = false;

    public List<mCarInfo> CarList;//车辆列表


}
