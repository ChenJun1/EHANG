package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * 车辆排班添加
 * Created by JohnnyYuan on 2016/7/5.
 */
public class AddCarShiftRequest {
    public String CarCodeSerial_Oid;//	车辆
    public String MainDriverSerial_Oid;//	主驾
    public String Driver_Tel;//	联系电话
    public String SecondDriverSerial_Oid;//	副驾
    public String Line_Oid;//	线路
    public String CarCode_Date;//	排班日期
    public String Forecast_Leave_DateTime;//	预计发车时间
    public String CarCode_NO;//	排班序号
    public String CarCode;//车牌号
    public String CarSchedule_Note;//	备注

    public String CarCode_Key;
    public String BoxCarCode_Key;//挂车key

    public String MainDriver;
    public String MainDriver_Tel;
    public String SecondDriver_Tel;
    public String SecondDriver;
    public String Shuttle_No;
    public String Shuttle_Oid;

    public String SendWay;//发车方式0：整车，1：配货）

    public String Destination;//目的地

    public String BLng;//百度经度
    public String BLat;//百度纬度



    public String Add_Status;//添加状态
    public String Org_key;//添加状态


}
