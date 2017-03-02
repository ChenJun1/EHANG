package com.cvnavi.logistics.i51ehang.app.bean.model.myFleet;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/8.
 *
 */
public class mCarCode_Schedule {

    public String CarCode_No;
    /// <summary>
    /// 查询排班时间起
    /// </summary>
    public String BeginDate;//
    /// <summary>
    /// 查询排班时间止
    /// </summary>
    public String EndDate ;//
    /// <summary>
    /// 排班记录Key
    /// </summary>
    public String Serial_Oid ;//
    /// <summary>
    /// 车的主键
    /// </summary>
    public String CarCodeSerial_Oid ;//
    /// <summary>
    /// 排班日期，主键 (精确到日)
    /// </summary>
    public String CarCode_Date ;//
    /// <summary>
    /// 线路
    /// </summary>
    public String Line_Oid ;//
    /// <summary>
    /// 预计发车时间
    /// </summary>
    public String Forecast_Leave_DateTime ;//
    /// <summary>
    /// 排班顺序
    /// </summary>
    public String CarCode_NO ;//
    /// <summary>
    /// 主驾驶
    /// </summary>
    public String MainDriverSerial_Oid ;//
    /// <summary>
    /// 副驾驶
    /// </summary>
    public String SecondDriverSerial_Oid ;//
    /// <summary>
    /// 排班记事
    /// </summary>
    public String CarSchedule_Note ;//
    /// <summary>
    /// 操作人
    /// </summary>
    public String Operate_Name ;//
    /// <summary>
    /// 操作机构
    /// </summary>
    public String Operate_Org ;//
    /// <summary>
    /// 	操作时间
    /// </summary>
    public String Operate_DateTime ;//
    /// <summary>
    /// 联系电话
    /// </summary>
    public String Driver_Tel ;//
    /// <summary>
    /// 车牌号
    /// </summary>
    public String CarCode ;//
    /// <summary>
    /// 线路类型
    /// </summary>
    public String Line_Type ;//
    /// <summary>
    /// 线路名称
    /// </summary>
    public String Line_Name ;//
    /// <summary>
    /// 运输模式（配货和整车）
    /// </summary>
    public String Traffic_Mode ;//
    /// <summary>
    /// 排班状态（已发车、已配载、未配载、已完成）
    /// </summary>
    public String Schedule_Status ;//
    /// <summary>
    /// 实际发车时间
    /// </summary>
    public String Leave_DateTime ;//
    /// <summary>
    /// 主驾驶姓名
    /// </summary>
    public String MainDriver ;//	主驾驶姓名
    /// <summary>
    /// 副驾驶姓名
    /// </summary>
    public String SecondDriver ;//	副驾驶姓名
    /// <summary>
    /// GPSKey(LPSCarCode_Key)
    /// </summary>
    public String LPSCarCode_Key ;//	GPSKey(LPSCarCode_Key)
}
