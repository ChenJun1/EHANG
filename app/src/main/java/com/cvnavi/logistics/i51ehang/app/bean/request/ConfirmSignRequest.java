package com.cvnavi.logistics.i51ehang.app.bean.request;

import com.cvnavi.logistics.i51ehang.app.bean.model.ImageInfo;

import java.util.List;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/22.
 * 请求类
 */
public class ConfirmSignRequest {
    public String All_Ticket_No;
    public String Letter_Oid;
    public String Goods_Num;
    public String Goods_Weight;
    public String Bulk_Weight;
    public String QianShouCount;
    public String QianShouZhongLiang;
    public String QianShouLiFang;
    public String Deliver_DateTime;
    public String Deliver_Name;
    public String Deliver_Note;
    public String ZD_Org;
    public String YSK_Fee;

    public List<ImageInfo> IMGList;

}
