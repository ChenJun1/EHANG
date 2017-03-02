package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;

/**
 * 获取历史详轨迹记录分析
 * Created by JohnnyYuan on 2016/7/4.
 */
public class mCarHistoryLocusAnalysis implements Serializable{
    public String Status_Oid;//状态编码   1:停车 0:运动
    public String Begin_DateTime;//开始时间
    public String End_DateTime;//结束时间
    public String Time_Description;//间隔时间
    public String BLng;//百度经度
    public String BLat;//百度纬度
    public String CHS_Address ;//当前地址

    public String CarCode_Key;// 	车辆key
    public String CarCode;// 	车牌号
    public String Company_Oid;//	公司号

}
