package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * 车辆排班修改
 * Created by JohnnyYuan on 2016/7/5.
 */
public class EditCarShiftRequest {
   public String CarCodeSerial_Oid;//	车辆
   public String MainDriverSerial_Oid;//	主驾
   public String Driver_Tel;//	联系电话
   public String SecondDriverSerial_Oid;//	副驾
   public String Line_Oid;//	线路
   public String CarCode_Date;//	排班日期
   public String Forecast_Leave_DateTime;//	预计发车时间
   public String CarCode_NO;//	排班序号
   public String CarSchedule_Note;//	备注
   public String Serial_Oid;//流水号
   public String BoxCarCode_Key;//挂车号
}
