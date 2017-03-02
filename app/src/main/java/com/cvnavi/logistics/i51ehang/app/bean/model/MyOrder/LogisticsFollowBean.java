package com.cvnavi.logistics.i51ehang.app.bean.model.MyOrder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/22.
 */
public class LogisticsFollowBean {
    public String Ticket_No;           //货单号
    public String Transport_Status;           //物流当前状态
    public String Goods_Num;           //件数
    public String Org_Name;           //承运来源
    public String Custom_Tel;           //客服电话
    public List<LogisticsFollowNoteBean> TrackTicket;
}
