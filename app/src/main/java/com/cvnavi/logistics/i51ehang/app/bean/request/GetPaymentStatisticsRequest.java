package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * Created by fan on 2016/7/19.
 * 应付款统计
 */
public class GetPaymentStatisticsRequest {
    public String BeginDate;//	开始时间，传入格式 yyyy-MM-dd 00:00:00
    public String EndDate;//	结束时间，传入格式yyyy-MM-dd 00:00:00
    public String Pay_Status;//	传入值为0、1、2;  收款状态：（0未支出、1差额支出、2已支出）
    public String Fee_Name;//	传入值：费用名称
    public String Operate_Code;//	费用名称操作码
}
