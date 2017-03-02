package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;

/**
 * 车辆排班司机选择
 * Created by JohnnyYuan on 2016/7/6.
 */
public class mCarSchedulingDriver implements Serializable{
    public String Serial_Oid;//	司机ID
    public String Company_Oid;//	公司ID
    public String Driver;//	司机姓名
    public String Search_JM;//	姓名简码
    public String Driver_Tel;//	电话
    public String Operate_Org;//	添加机构
    public String Operate_Name;//	添加人
    public String Operate_OrgName;//	添加机构名
    public String Operate_DateTime;//	添加时间
    public String Driver_License_Type_Oid;//	驾驶证类型Oid
    public String Card_Oid;
    public String Driver_License_Type;//	驾驶证类型

}
