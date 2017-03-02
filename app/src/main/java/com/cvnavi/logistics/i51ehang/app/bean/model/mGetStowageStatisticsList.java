package com.cvnavi.logistics.i51ehang.app.bean.model;

import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.TaskBean;

import java.io.Serializable;

/**
 * Created by fan on 2016/7/21.
 * 配载统计数据列表
 */
public class mGetStowageStatisticsList extends TaskBean implements Serializable{
//    public String Letter_Oid	;//清单ID
//    public String Letter_Date	;//配载时间
//    public String Line_Oid	    ;//线路ID(与Dict_Line_Sheet表关联)
//    public String Line_Name	    ;//线路名
//    public String Traffic_Mode	;//配载方式（整车、配货）
//    public String Shuttle_Fee	;//班车成本
//    public String CarCode	    ;//车牌
//    public String Driver	    ;//司机
//    public String Driver_Tel	;//司机电话
//    public String Leave_Name	;//发货确认人
//    public String Leave_DateTime;//	发货确认时间
//    public String Arrive_Name	;//到达确认人
//    public String Arrive_DateTime	;//到达确认时间
//    public String Letter_Note	;//清单记事
//    public String Operate_Name	;//制单人
//    public String Operate_Org	;//制单机构
//    public String Operate_DateTime	;//制单时间
//    public String Ticket_Count	;//总票数
//    public String Goods_Num	    ;//货物数
//    public String Goods_Weight	;//货物重量
//    public String Bulk_Weight	;//体积
//    public String Ticket_Fee	;//收入
//    public String Profit_Fee	;//利润
//    public String Letter_Status	;//状态（已完成、未完成）
//    public String Line_Type ;//线路类型：干线、直线

    public String Leave_Name	;//发货确认人
    public String Leave_DateTime;//	发货确认时间
    public String Arrive_Name	;//到达确认人
    public String Arrive_DateTime	;//到达确认时间
    public String Letter_Note	;//清单记事
    public String Letter_Status	;//状态（已完成、未完成）

//    public String BoxCarCode	;//挂车号
}
