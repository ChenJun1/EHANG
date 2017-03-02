package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * Created by fan on 2016/7/20.
 */
public class GetOrederStatisticsListRequest {
    public String BeginDate;//	查询开始时间
    public String EndDate;//	查询结束时间
    public String DeliverStatus;//	签收状态(0,1,2)		已签收、未签收、部分签收
    public String SendMan_Tel;//	发货人电话
    public String Search_No	;//简码
    public String Ticket_Status;//	是否作废
}
