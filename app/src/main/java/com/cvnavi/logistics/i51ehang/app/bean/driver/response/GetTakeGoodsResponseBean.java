package com.cvnavi.logistics.i51ehang.app.bean.driver.response;

import java.util.List;

/**
 * Created by ${ChenJ} on 2016/10/11.
 */

public class GetTakeGoodsResponseBean {


    /**
     * DataValue : [{"Letter_Oid":"B6100202016100700055","Ticket_No":"2000259560","Goods_Breed":"进口食品","Goods_Casing":"托盘","Goods_Num":"13","Goods_Weight":"8396.000","Bulk_Weight":"36.000","SendMan_Address":"上海市浦东新区龙东大道5385号龙东大厦207室","Operate_DateTime":"2016-10-07 20:02:27","Deliver_DateTime":"2016-10-07 20:02:27","Operate_Code":"EA","Complete_Status":"0","SendStation":null,"ArrStation":null}]
     * Success : false
     * ErrorText : null
     * MsgType : null
     * RowCount : 0
     */

    private boolean Success;
    private Object ErrorText;
    private Object MsgType;
    private int RowCount;
    /**
     * Letter_Oid : B6100202016100700055
     * Ticket_No : 2000259560
     * Goods_Breed : 进口食品
     * Goods_Casing : 托盘
     * Goods_Num : 13
     * Goods_Weight : 8396.000
     * Bulk_Weight : 36.000
     * SendMan_Address : 上海市浦东新区龙东大道5385号龙东大厦207室
     * Operate_DateTime : 2016-10-07 20:02:27
     * Deliver_DateTime : 2016-10-07 20:02:27
     * Operate_Code : EA
     * Complete_Status : 0
     * SendStation : null
     * ArrStation : null
     */

    private List<TakeBean> DataValue;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public Object getErrorText() {
        return ErrorText;
    }

    public void setErrorText(Object ErrorText) {
        this.ErrorText = ErrorText;
    }

    public Object getMsgType() {
        return MsgType;
    }

    public void setMsgType(Object MsgType) {
        this.MsgType = MsgType;
    }

    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int RowCount) {
        this.RowCount = RowCount;
    }

    public List<TakeBean> getDataValue() {
        return DataValue;
    }

    public void setDataValue(List<TakeBean> DataValue) {
        this.DataValue = DataValue;
    }

    public static class TakeBean {

        private String Letter_Oid;
        private String Ticket_No;
        private String All_Ticket_No;
        private String Goods_Breed;
        private String Goods_Casing;
        private String Goods_Num;
        private String Goods_Weight;
        private String Bulk_Weight;
        private String SendMan_Address;
        private String Operate_DateTime;
        private String Deliver_DateTime;
        private String Operate_Code;
        private String Complete_Status;
        private String SendStation;
        private String ArrStation;
        private String Org_Code ;

        public String getOrg_Code() {
            return Org_Code;
        }

        public void setOrg_Code(String org_Code) {
            Org_Code = org_Code;
        }

        public String getAll_Ticket_No() {
            return All_Ticket_No;
        }

        public void setAll_Ticket_No(String all_Ticket_No) {
            All_Ticket_No = all_Ticket_No;
        }

        public String getLetter_Oid() {
            return Letter_Oid;
        }

        public void setLetter_Oid(String Letter_Oid) {
            this.Letter_Oid = Letter_Oid;
        }

        public String getTicket_No() {
            return Ticket_No;
        }

        public void setTicket_No(String Ticket_No) {
            this.Ticket_No = Ticket_No;
        }

        public String getGoods_Breed() {
            return Goods_Breed;
        }

        public void setGoods_Breed(String Goods_Breed) {
            this.Goods_Breed = Goods_Breed;
        }

        public String getGoods_Casing() {
            return Goods_Casing;
        }

        public void setGoods_Casing(String Goods_Casing) {
            this.Goods_Casing = Goods_Casing;
        }

        public String getGoods_Num() {
            return Goods_Num;
        }

        public void setGoods_Num(String Goods_Num) {
            this.Goods_Num = Goods_Num;
        }

        public String getGoods_Weight() {
            return Goods_Weight;
        }

        public void setGoods_Weight(String Goods_Weight) {
            this.Goods_Weight = Goods_Weight;
        }

        public String getBulk_Weight() {
            return Bulk_Weight;
        }

        public void setBulk_Weight(String Bulk_Weight) {
            this.Bulk_Weight = Bulk_Weight;
        }

        public String getSendMan_Address() {
            return SendMan_Address;
        }

        public void setSendMan_Address(String SendMan_Address) {
            this.SendMan_Address = SendMan_Address;
        }

        public String getOperate_DateTime() {
            return Operate_DateTime;
        }

        public void setOperate_DateTime(String Operate_DateTime) {
            this.Operate_DateTime = Operate_DateTime;
        }

        public String getDeliver_DateTime() {
            return Deliver_DateTime;
        }

        public void setDeliver_DateTime(String Deliver_DateTime) {
            this.Deliver_DateTime = Deliver_DateTime;
        }

        public String getOperate_Code() {
            return Operate_Code;
        }

        public void setOperate_Code(String Operate_Code) {
            this.Operate_Code = Operate_Code;
        }

        public String getComplete_Status() {
            return Complete_Status;
        }

        public void setComplete_Status(String Complete_Status) {
            this.Complete_Status = Complete_Status;
        }

        public String getSendStation() {
            return SendStation;
        }

        public void setSendStation(String SendStation) {
            this.SendStation = SendStation;
        }

        public String getArrStation() {
            return ArrStation;
        }

        public void setArrStation(String ArrStation) {
            this.ArrStation = ArrStation;
        }
    }
}
