package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * Created by fan on 2016/7/21.
 * 配载统计明细
 */
public class GetStowageStatisticsListRequest {
    public String BeginDate	;//查询开始时间		是
    public String EndDate	;//查询结束时间		是
    public String Traffic_Mode;//	整车 / 配货		配载类型
    public String DeliverStatus;//是否到车(1未到车，2已到车,0全部)
}
