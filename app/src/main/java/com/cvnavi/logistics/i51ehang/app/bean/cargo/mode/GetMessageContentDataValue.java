package com.cvnavi.logistics.i51ehang.app.bean.cargo.mode;

/**
 * Created by fan on 2016/11/8.
 */

public class GetMessageContentDataValue {
    public String Serial_Oid;//	ID
    public String Company_Oid	;//表示该信息是在某公司下的
    public String User_Oid;//	用户ID或电话（传入货主电话）
    public String Operate_Code	;//操作码
                                 // EC	货单装车
                                 //IB	货单发车
                                 //BV	到达确认（到车3）货单到车
                                 //IE	配送出库（送货4）车辆发车
                                 //AH	签收（签收5）

    public String Operate_DateTime;//	操作时间
    public String Content;//	消息内容
    public String All_Ticket_No	;//全票号
    public String Letter_Oid;//	清单号（配载单号）
    public String IsRead;//1 已读 0	未读
}
