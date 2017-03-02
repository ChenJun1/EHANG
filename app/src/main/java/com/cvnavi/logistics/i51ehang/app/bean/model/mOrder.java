package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/19.
 */
public class mOrder implements Serializable{
    public String All_Ticket_No; //全票号
    public String Ticket_No;//货单号
    public String SendStation;//始发地
    public String ArrStation;//到达地
    public String ArrStation_Area;//到达站区县
    public String Ticket_Fee; //票面收入
    public String Goods_Breed;//品名
    public String Goods_Num;//件数
    public String Goods_Weight;//重量
    public String Bulk_Weight;//体积
    public String Operate_DateTime;//制单时间
    public String Deliver_Status;//签收状态

   public String num	;//序号
   public String Search_No	;//简码
   public String Ticket_Status;//	是否作废
   public String Waste_Cause	;//作废原因
   public String Deliver_DateTime	;//签收时间
   public String SendGoods_DateTime;//	发货时间
   public String Delegate_DateTime;//
   public String  Total_Fee;//	票面收入




}
