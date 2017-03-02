package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;

/**
 * 车辆排班查询
 * Created by JohnnyYuan on 2016/7/5.
 */
public class mCarShift implements Serializable {
    /// <summary>
    /// 车牌号
    /// </summary>
    public String CarCode;
    /// <summary>
    /// 装车状态
    /// </summary>
    public String Traffic_Mode;
    /// <summary>
    /// 线路状态
    /// </summary>
    public String Line_Type;

    public String Line_Type_Oid;

    /// <summary>
    /// 线路
    /// </summary>
    public String Line_Oid;


    /// <summary>
    /// 配载状态
    /// </summary>
    public String Schedule_Status;
    /// <summary>
    /// 排班日期
    /// </summary>
    public String CarCode_Date;
    /// <summary>
    /// 预发车时间
    /// </summary>
    public String Forecast_Leave_DateTime;
    /// <summary>
    /// 实际发车时间
    /// </summary>
    public String Leave_DateTime;
    /// <summary>
    /// 排班顺序
    /// </summary>
    public String CarCode_No;
    /// <summary>
    /// 车辆
    /// </summary>
    public String CarCodeSerial_Oid;
    /// <summary>
    /// 主驾
    /// </summary>
    public String MainDriverSerial_Oid;
    /// <summary>
    /// 联系电话
    /// </summary>
    public String Driver_Tel;
    /// <summary>
    /// 副驾
    /// </summary>
    public String SecondDriverSerial_Oid;
    /// <summary>
    /// 备注
    /// </summary>
    public String CarSchedule_Note;

    /**
     * 主司机姓名
     */
    public String MainDriver;
    public String MainDriver_Tel;

    /**
     * 副司机姓名
     */
    public String SecondDriver;
    public String SecondDriver_Tel;

    /**
     * 路线名称
     */
    public String Line_Name;

    public String Shuttle_Oid;

    public String Shuttle_No;


    //流水号
    public String Serial_Oid;

    public String LPSCarCode_Key;

    public String BoxCarCode_Key;//挂车Key

    public String BoxCarCode = null;//挂车号

    public String Destination;//目的地

    public String SendWay;//发车方式（0：整车，1：配货）


    public String Add_Status ;////添加状态 0-系统添加、1-TMS添加、2-ehang 添加、3-VMS添加


    public mCarShift() {
    }

    public mCarShift(String carCode, String line_Name, String boxCarCode, String leave_DateTime) {
        CarCode = carCode;
        Line_Name = line_Name;
        BoxCarCode = boxCarCode;
        Leave_DateTime = leave_DateTime;
    }

    public mCarShift(String carCode, String line_Name, String boxCarCode, String carCode_Date, String mainDriver, String mainDriver_Tel, String secondDriver, String secondDriver_Tel, String shuttle_Oid, String shuttle_No, String Schedule_Status) {
        this.CarCode = carCode;
        this.Line_Name = line_Name;
        this.BoxCarCode = boxCarCode;
        this.CarCode_Date = carCode_Date;
        this.MainDriver = mainDriver;
        this.MainDriver_Tel = mainDriver_Tel;
        this.SecondDriver = secondDriver;
        this.SecondDriver_Tel = secondDriver_Tel;
        this.Shuttle_Oid = shuttle_Oid;
        this.Shuttle_No = shuttle_No;
        this.Schedule_Status = Schedule_Status;
    }
}
