package com.cvnavi.logistics.i51ehang.app.bean.model.StowageStatisticsSummary;

/**
 * Created by fan on 2016/7/21.
 */
public class DetailList {
    public String Letter_Date;//	日期
    public int Letter_Count;//	发车数量
    public String Shuttle_Fee;//	成本
    public String Ticket_Fee;//	收入
    public String Profit_Fee;//	利润
    public String EntiretyCount;//	整车数量
    public String BulkloadCount;//	配货数量

    public String Bulk_Weight;// 总体积
    public String Goods_Weight;// 重量
    public String Goods_Num;// 件数

    public String Day;
    public String TotalTicket_Profit;

    public String DayCarCount;
    public String Letter_DateStr;


    public String DayLetterCount;
    //            日总委托次数
    public String DayTicketCount;
    //            日总票数
    public String DayGoods_Num;
    //            日总件数
    public String DayGoods_Weight;
    //            日总重量
    public String DayBulk_Weight;
    //            日总方量
    public String DayTotalPay_Fee;
    //            日总成本
    public String DayTotal_Fee;
    //            日总收入
    public String DayShuttle_Profit;
    //            日总利润


}
