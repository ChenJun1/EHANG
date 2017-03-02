package com.cvnavi.logistics.i51ehang.app.bean.cargo.request;

/**
 * Created by fan on 2016/11/2.
 */

public class SetTakeManifestsRequest {
    public String SendMan_Name	;//	是	发货人
    public String SendMan_Address;//		是	发货地址
    public String SendMan_Tel	;//	是	发货人电话
    public String BLng	;//	是	发货地址经度
    public String BLat	;//	是	发货地址纬度
    public String Goods_Breed	;//	是	品名
    public String Goods_Casing;//		是	规格（包装）
    public String Goods_Num	;//	三选一	件数
    public String Goods_Weight	;//		重量
    public String Bulk_Weight	;//		体积
}
