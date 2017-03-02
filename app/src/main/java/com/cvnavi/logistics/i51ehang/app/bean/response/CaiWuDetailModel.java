package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.util.List;

/**
 * Created by george on 2016/12/17.
 */

public class CaiWuDetailModel {

    /**
     * DataValue : [{"Ticket_No":"0000011111","All_Ticket_No":"0000000000000000000A0000011111","Operate_DateTime":"2016-04-18 11:46:30","Total_Fee":"0.0","AllTotal_Fee":"0.0","FastTotal_Fee":"0.0","TotalPay_Fee":"0.0","Pay_Fee":"0.0","Ticket_Profit":"0.0","Total_DD_Fee":"0.0","Goods_Fee":"0.0","Gather_Fee":"0.0","SurplusGather_Fee":"0.0"}]
     * Success : true
     * ErrorText : null
     * MsgType : null
     * RowCount : 0
     * Status : null
     * CompanyList : null
     */

    private boolean Success;
    private Object ErrorText;
    private Object MsgType;
    private int RowCount;
    private Object Status;
    private Object CompanyList;
    private List<DataValueBean> DataValue;

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

    public Object getStatus() {
        return Status;
    }

    public void setStatus(Object Status) {
        this.Status = Status;
    }

    public Object getCompanyList() {
        return CompanyList;
    }

    public void setCompanyList(Object CompanyList) {
        this.CompanyList = CompanyList;
    }

    public List<DataValueBean> getDataValue() {
        return DataValue;
    }

    public void setDataValue(List<DataValueBean> DataValue) {
        this.DataValue = DataValue;
    }

    public static class DataValueBean {
        /**
         * Ticket_No : 0000011111
         * All_Ticket_No : 0000000000000000000A0000011111
         * Operate_DateTime : 2016-04-18 11:46:30
         * Total_Fee : 0.0
         * AllTotal_Fee : 0.0
         * FastTotal_Fee : 0.0
         * TotalPay_Fee : 0.0
         * Pay_Fee : 0.0
         * Ticket_Profit : 0.0
         * Total_DD_Fee : 0.0
         * Goods_Fee : 0.0
         * Gather_Fee : 0.0
         * SurplusGather_Fee : 0.0
         * Ticket_No
         * 货单号
         * All_Ticket_No
         * 全票号
         * Operate_DateTime
         * 制单时间
         * Total_Fee
         * 票面收入
         * AllTotal_Fee
         * 应收款
         * FastTotal_Fee
         * 实际收入
         * TotalPay_Fee
         * 成本
         * Pay_Fee
         * 已支出
         * Ticket_Profit
         * 利润
         * Total_DD_Fee
         * 代垫费
         * Goods_Fee
         * 到收货款
         * Gather_Fee
         * 已收款
         * SurplusGather_Fee
         * 未收款
         */

        private String Ticket_No;
        private String All_Ticket_No;
        private String Operate_DateTime;
        private String Total_Fee;
        private String AllTotal_Fee;
        private String FastTotal_Fee;
        private String TotalPay_Fee;
        private String Pay_Fee;
        private String Ticket_Profit;
        private String Total_DD_Fee;
        private String Goods_Fee;
        private String Gather_Fee;
        private String SurplusGather_Fee;

        public String getTicket_No() {
            return Ticket_No;
        }

        public void setTicket_No(String Ticket_No) {
            this.Ticket_No = Ticket_No;
        }

        public String getAll_Ticket_No() {
            return All_Ticket_No;
        }

        public void setAll_Ticket_No(String All_Ticket_No) {
            this.All_Ticket_No = All_Ticket_No;
        }

        public String getOperate_DateTime() {
            return Operate_DateTime;
        }

        public void setOperate_DateTime(String Operate_DateTime) {
            this.Operate_DateTime = Operate_DateTime;
        }

        public String getTotal_Fee() {
            return Total_Fee;
        }

        public void setTotal_Fee(String Total_Fee) {
            this.Total_Fee = Total_Fee;
        }

        public String getAllTotal_Fee() {
            return AllTotal_Fee;
        }

        public void setAllTotal_Fee(String AllTotal_Fee) {
            this.AllTotal_Fee = AllTotal_Fee;
        }

        public String getFastTotal_Fee() {
            return FastTotal_Fee;
        }

        public void setFastTotal_Fee(String FastTotal_Fee) {
            this.FastTotal_Fee = FastTotal_Fee;
        }

        public String getTotalPay_Fee() {
            return TotalPay_Fee;
        }

        public void setTotalPay_Fee(String TotalPay_Fee) {
            this.TotalPay_Fee = TotalPay_Fee;
        }

        public String getPay_Fee() {
            return Pay_Fee;
        }

        public void setPay_Fee(String Pay_Fee) {
            this.Pay_Fee = Pay_Fee;
        }

        public String getTicket_Profit() {
            return Ticket_Profit;
        }

        public void setTicket_Profit(String Ticket_Profit) {
            this.Ticket_Profit = Ticket_Profit;
        }

        public String getTotal_DD_Fee() {
            return Total_DD_Fee;
        }

        public void setTotal_DD_Fee(String Total_DD_Fee) {
            this.Total_DD_Fee = Total_DD_Fee;
        }

        public String getGoods_Fee() {
            return Goods_Fee;
        }

        public void setGoods_Fee(String Goods_Fee) {
            this.Goods_Fee = Goods_Fee;
        }

        public String getGather_Fee() {
            return Gather_Fee;
        }

        public void setGather_Fee(String Gather_Fee) {
            this.Gather_Fee = Gather_Fee;
        }

        public String getSurplusGather_Fee() {
            return SurplusGather_Fee;
        }

        public void setSurplusGather_Fee(String SurplusGather_Fee) {
            this.SurplusGather_Fee = SurplusGather_Fee;
        }
    }
}
