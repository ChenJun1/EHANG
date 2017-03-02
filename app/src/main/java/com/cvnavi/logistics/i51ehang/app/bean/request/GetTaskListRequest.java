package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * Created by Administrator on 2016/7/19.
 */
public class GetTaskListRequest {
    public String BeginDate;  //查询开始时间
    public String EndDate;//查询结束时间
    public String Driver_Tel;//司机电话
    public String DeliverStatus;//清单完成标志(0全部,1已完成,2未完成)
    public String SendMan_Tel;
}
