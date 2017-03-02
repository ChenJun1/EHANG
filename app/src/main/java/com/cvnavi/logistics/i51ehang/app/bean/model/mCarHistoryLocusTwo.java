package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;

/**
 * Created by fan on 2016/7/12.
 */
public class mCarHistoryLocusTwo implements Serializable{
    public String BLng ;//百度经度
    public String BLat ;//百度纬度
    public String Speed ;//速度
    public String Direction ;//方向
    public String Mileage ;//里程数
    public String Position_DateTime ;//数据包发送时间
    public String CHS_Provice ;//当前省
    public String CHS_City ;//当前市
    public String CHS_Address ;//当前地址

    public String CarCode_Key;// 	车辆key
    public String CarCode;// 	车牌号
    public String Status_Oid;// 	状态编码;//   1:停车 0:运动
}
