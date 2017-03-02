package com.cvnavi.logistics.i51ehang.app.bean.request;

/**
 * Created by fan on 2016/7/19.
 */
public class GetReceivablesListRequest {

    public String Fee_Name;//	传入值：费用名称
    public String BeginDate;//	开始时间，传入格式 yyyy-MM-dd 00:00:00
    public String EndDate;//	结束时间，传入格式yyyy-MM-dd 00:00:00
    public String Gather_Status;//	传入值为0、1、2;  收款状态：（0未收款、1差额收款、2已收款）
}
