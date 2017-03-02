package com.cvnavi.logistics.i51ehang.app.bean.cargo.mode;

/**
 * 版权所有 势航网络
 * Created by ${ChenJ} on 2016/12/2.
 */

public class ShipperStatistics {
    private String Total_Fee;//	当月运费总计
    private String SendCount;//	当月发货总数

    public String getTotal_Fee() {
        return Total_Fee;
    }

    public void setTotal_Fee(String total_Fee) {
        Total_Fee = total_Fee;
    }

    public String getSendCount() {
        return SendCount;
    }

    public void setSendCount(String sendCount) {
        SendCount = sendCount;
    }
}
