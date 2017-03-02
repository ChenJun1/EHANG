package com.cvnavi.logistics.i51ehang.app.bean.model.myFleet;

/**
 * Created by ${ChenJ} on 2016/8/9.
 */
public class mAlarmBean {


    /**
     * "Serial_Oid":null,"Company_Oid":null,"Company_Name":null,"CarCode":"沪A00001",
     * "CarCode_Key":"5285F2C2-F2C2-4FC9-B321-67DDE9215B76","Alarm_Type_Oid":"AD",
     * "Alarm_DateTime":"2016-07-21 17:01:00","StartTime":"2016-07-21 17:01:00",
     * "EndTime":"2016-07-21 17:20:00","Alarm_Description":"在江苏省>苏州市>昆山市
     * ；公厕附近开始超速;于2016-05-14 15:17:47结束","User_Type_Oid":null,
     * "User_Key":null,"Alarm_Type":"超速报警","Alarm_Status_Oid":"0"
     *
     * 报警类型
     AD、AE、AF、AG、AH、AM、BN、EJ
     超速报警、停车报警、到达报警、离开报警、线路偏离报警、温度报警
     冷链传感器离线、离线报警
     */
  public String Serial_Oid;//	报警记录Key
  public String Company_Oid;//	公司Id
  public String Company_Name;//	公司名称
  public String CarCode;//	车牌号
  public String CarCode_Key;//	车辆Key
  public String Alarm_Type_Oid;//	报警类型Id
  public String Alarm_DateTime;//	报警时间
  public String StartTime;//	开始时间
  public String EndTime;//	结束时间
  public String Alarm_Description;//	报警记录
  public String User_Type_Oid;//	用户类型
  public String User_Key;//	用户Key
  public String Alarm_Type;//	报警类型
  public String Alarm_Status_Oid;//	报警状态Id
  public String OverSpeedTime;//	超速时长

}
