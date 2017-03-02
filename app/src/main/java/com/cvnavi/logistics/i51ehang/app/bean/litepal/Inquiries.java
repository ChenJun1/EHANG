package com.cvnavi.logistics.i51ehang.app.bean.litepal;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/18.
 */
public class Inquiries extends DataSupport {
    private String userTel;//手机号
    private String orderId;//货单号
    private String arriving;//到达地
    private String data;//搜索日期
    private String bengining;//始发地
    private String All_Ticket_No;//全票号
    private String tickFee;//应收款
    private String goodsNum;//应收款

    private String extend_one;
    private String extend_two;
    private String extend_three;

    public String getExtend_one() {
        return extend_one;
    }

    public void setExtend_one(String extend_one) {
        this.extend_one = extend_one;
    }

    public String getExtend_two() {
        return extend_two;
    }

    public void setExtend_two(String extend_two) {
        this.extend_two = extend_two;
    }

    public String getExtend_three() {
        return extend_three;
    }

    public void setExtend_three(String extend_three) {
        this.extend_three = extend_three;
    }

    public String getTickFee() {
        return tickFee;
    }

    public void setTickFee(String tickFee) {
        this.tickFee = tickFee;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getArriving() {
        return arriving;
    }

    public void setArriving(String arriving) {
        this.arriving = arriving;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBengining() {
        return bengining;
    }

    public void setBengining(String bengining) {
        this.bengining = bengining;
    }

    public String getAll_Ticket_No() {
        return All_Ticket_No;
    }

    public void setAll_Ticket_No(String all_Ticket_No) {
        All_Ticket_No = all_Ticket_No;
    }


    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
