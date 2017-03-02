package com.cvnavi.logistics.i51ehang.app.bean.model.MyTask;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/20.
 */
public class TaskDetailedOrderListBean implements Serializable {
    public String All_Ticket_No;       //全票单号
    public String Ticket_No;       //货单号
    public String Goods_Num;       //数量
    public String Goods_Weight;       //重量
    public String Bulk_Weight;       //体积
    public String Goods_Breed;       //品名
    public String Send_Station;       //发货站
    public String sendCity;       //发货站所属地市
    public String arrive_Station;       //到达站
    public String arriveCity;       //到达站所属地市
    public String fullStatus;       //是否分批配载(是,否)
    public String Delegate_DateTime;       //发货时间
    public String ReceiveMan_Name;       //联系人
    public String ReceiveMan_Address;       //地址
    public String ReceiveMan_Tel;       //联系电话
    public String ReceivableAccount;       //应收款
    public String ticketType;       //货单类型(送货,提货)
    public String complete_Status;       //货单(签收、提货)完成状态(1完成，0未完成)
    public String Search_No;       //简码
    public String Org_Code;       //制单机构
    public String YSK_Fee;       //费用


    public TaskDetailedOrderListBean() {
    }

    public TaskDetailedOrderListBean(String All_Ticket_No, String Ticket_No,String Bulk_Weight,
                                     String Goods_Num, String Goods_Weight,String YSK_Fee) {
        this.All_Ticket_No=All_Ticket_No;
        this.Ticket_No=Ticket_No;
        this.Bulk_Weight=Bulk_Weight;
        this.Goods_Num=Goods_Num;
        this.Goods_Weight=Goods_Weight;
        this.YSK_Fee=YSK_Fee;
    }
    public TaskDetailedOrderListBean(String All_Ticket_No, String Ticket_No,String Bulk_Weight,
                                     String Goods_Num, String Goods_Weight) {
        this.All_Ticket_No=All_Ticket_No;
        this.Ticket_No=Ticket_No;
        this.Bulk_Weight=Bulk_Weight;
        this.Goods_Num=Goods_Num;
        this.Goods_Weight=Goods_Weight;
    }
//    public TaskDetailedOrderListBean(String All_Ticket_No, String Ticket_No,String Bulk_Weight,
//                                     String Goods_Num, String Goods_Weight,String YSK_Fee) {
//        this.All_Ticket_No=All_Ticket_No;
//        this.Ticket_No=Ticket_No;
//        this.Bulk_Weight=Bulk_Weight;
//        this.Goods_Num=Goods_Num;
//        this.Goods_Weight=Goods_Weight;
//        this.YSK_Fee=YSK_Fee;
//    }


}
