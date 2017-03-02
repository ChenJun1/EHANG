package com.cvnavi.logistics.i51ehang.app.bean.request;

import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.ImageBean;

import java.util.List;

/**
 * Created by ${ChenJ} on 2016/7/26.
 */
public class DeliveryConfirmRequest {
    public String Letter_Oid;          //清单Oid    string
    public String All_Ticket_No;          //货单全票号  string
    public String Operate_Code;          //操作码默认string
    public String Goods_Num;          //提货件数   Int
    public String Goods_Weight;          //提货重量   decimal
    public String Bulk_Weight;          //提货立方   decimal
    public String Ticket_Note;          //记事       string
    public List<ImageBean> IMGList;          //图片信息列表集合
}
