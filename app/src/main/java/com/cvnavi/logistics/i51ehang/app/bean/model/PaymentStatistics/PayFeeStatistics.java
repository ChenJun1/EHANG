package com.cvnavi.logistics.i51ehang.app.bean.model.PaymentStatistics;

import java.io.Serializable;

/**
 * Created by fan on 2016/7/19.
 * 应付款数据集合
 */
public class PayFeeStatistics implements Serializable{

    public String Ticket_No;//	货单号
    public String Fee_Name;// 	费用名称
    public String Operate_Code;//	费用名称操作码
    public String Operate_DateTime;//	产生支出时间
    public String Pay_Status;//	付款状态
    public String Fee;//	付款金额
    public String Pay_Fee;//	付款金额
    public String Surplus_Pay_Fee;//	剩余付款金额
}
