package com.cvnavi.logistics.i51ehang.app.bean.request;

import com.cvnavi.logistics.i51ehang.app.bean.model.ImageInfo;

import java.util.List;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/26.
 */
public class OrderAbnormalPhotoRequest {
    public String All_Ticket_No;
    public String Exception_Mode_Oid;//提货:HH、装车:HJ、到达:HI、缺件:HK'、破损:HA、签收:HC	是	类型
    public String TrackRecord_Note;//是	拍照记事	类型
    public List<ImageInfo> IMGList;
}
