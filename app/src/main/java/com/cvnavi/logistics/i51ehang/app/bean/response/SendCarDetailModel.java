package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.util.List;

/**
 * Created by george on 2016/12/18.
 */


public class SendCarDetailModel {


    /**
     * DataValue : [{"Letter_DateStr":"2016-07-06","Letter_Oid":"B0000002016070600001","CarCode":"4322","Ticket_Count":"1","Total_Goods_Num":"0","Total_Goods_Weight":"0.0","Total_Bulk_Weight":"0.0","Operate_Org":"101"},{"Letter_DateStr":"2016-07-06","Letter_Oid":"B0000002016070600002","CarCode":"4322","Ticket_Count":"1","Total_Goods_Num":"0","Total_Goods_Weight":"0.0","Total_Bulk_Weight":"0.0","Operate_Org":"101002"},{"Letter_DateStr":"2016-07-06","Letter_Oid":"B0000002016070600003","CarCode":"0000000000000","Ticket_Count":"2","Total_Goods_Num":"200","Total_Goods_Weight":"1400.0","Total_Bulk_Weight":"20.0","Operate_Org":"101"}]
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
         * Letter_DateStr : 2016-07-06
         * Letter_Oid : B0000002016070600001
         * CarCode : 4322
         * Ticket_Count : 1
         * Total_Goods_Num : 0
         * Total_Goods_Weight : 0.0
         * Total_Bulk_Weight : 0.0
         * Operate_Org : 101
         */

        private String Letter_DateStr;
        private String Letter_Oid;
        private String CarCode;
        private String Ticket_Count;
        private String Total_Goods_Num;
        private String Total_Goods_Weight;
        private String Total_Bulk_Weight;
        private String Operate_Org;

        private String Letter_Date;
        //                承运委托日期
        private String ThreeCompany;
        //                承运公司
        private String Driver;
        //                司机
        private String Driver_Tel;
        //                司机电话
        private String Leave_Name;
        //                离开确认人
        private String Leave_DateTime;
        //                离开确认时间
        private String Operate_Name;
        //                操作人
        private String Operate_OrgName;
        //                操作机构名称
        private String Operate_DateTime;
        //                操作时间
        private String Goods_Num;
        //                件数
        private String Goods_Weight;
        //                重量
        private String Bulk_Weight;
        //                方量
        private String Total_Goods_Fee;
        //                总代收货款
        private String Total_Fee;
        //                票面收入
        private String Total_DF_Fee;
        //                总到付款
        private String AllTotal_Fee;
        //                签收总应收款
        private String TotalPay_Fee;
        //                总成本
        private String Shuttle_Profit;
        //                利润
        private String ZX_Fee;
        //                其它费
        private String Settlement_Fee;
//                剩余结款


        public String getLetter_Date() {
            return Letter_Date;
        }

        public void setLetter_Date(String letter_Date) {
            Letter_Date = letter_Date;
        }

        public String getThreeCompany() {
            return ThreeCompany;
        }

        public void setThreeCompany(String threeCompany) {
            ThreeCompany = threeCompany;
        }

        public String getDriver() {
            return Driver;
        }

        public void setDriver(String driver) {
            Driver = driver;
        }

        public String getDriver_Tel() {
            return Driver_Tel;
        }

        public void setDriver_Tel(String driver_Tel) {
            Driver_Tel = driver_Tel;
        }

        public String getLeave_Name() {
            return Leave_Name;
        }

        public void setLeave_Name(String leave_Name) {
            Leave_Name = leave_Name;
        }

        public String getLeave_DateTime() {
            return Leave_DateTime;
        }

        public void setLeave_DateTime(String leave_DateTime) {
            Leave_DateTime = leave_DateTime;
        }

        public String getOperate_Name() {
            return Operate_Name;
        }

        public void setOperate_Name(String operate_Name) {
            Operate_Name = operate_Name;
        }

        public String getOperate_OrgName() {
            return Operate_OrgName;
        }

        public void setOperate_OrgName(String operate_OrgName) {
            Operate_OrgName = operate_OrgName;
        }

        public String getOperate_DateTime() {
            return Operate_DateTime;
        }

        public void setOperate_DateTime(String operate_DateTime) {
            Operate_DateTime = operate_DateTime;
        }

        public String getGoods_Num() {
            return Goods_Num;
        }

        public void setGoods_Num(String goods_Num) {
            Goods_Num = goods_Num;
        }

        public String getGoods_Weight() {
            return Goods_Weight;
        }

        public void setGoods_Weight(String goods_Weight) {
            Goods_Weight = goods_Weight;
        }

        public String getBulk_Weight() {
            return Bulk_Weight;
        }

        public void setBulk_Weight(String bulk_Weight) {
            Bulk_Weight = bulk_Weight;
        }

        public String getTotal_Goods_Fee() {
            return Total_Goods_Fee;
        }

        public void setTotal_Goods_Fee(String total_Goods_Fee) {
            Total_Goods_Fee = total_Goods_Fee;
        }

        public String getTotal_Fee() {
            return Total_Fee;
        }

        public void setTotal_Fee(String total_Fee) {
            Total_Fee = total_Fee;
        }

        public String getTotal_DF_Fee() {
            return Total_DF_Fee;
        }

        public void setTotal_DF_Fee(String total_DF_Fee) {
            Total_DF_Fee = total_DF_Fee;
        }

        public String getAllTotal_Fee() {
            return AllTotal_Fee;
        }

        public void setAllTotal_Fee(String allTotal_Fee) {
            AllTotal_Fee = allTotal_Fee;
        }

        public String getTotalPay_Fee() {
            return TotalPay_Fee;
        }

        public void setTotalPay_Fee(String totalPay_Fee) {
            TotalPay_Fee = totalPay_Fee;
        }

        public String getShuttle_Profit() {
            return Shuttle_Profit;
        }

        public void setShuttle_Profit(String shuttle_Profit) {
            Shuttle_Profit = shuttle_Profit;
        }

        public String getZX_Fee() {
            return ZX_Fee;
        }

        public void setZX_Fee(String ZX_Fee) {
            this.ZX_Fee = ZX_Fee;
        }

        public String getSettlement_Fee() {
            return Settlement_Fee;
        }

        public void setSettlement_Fee(String settlement_Fee) {
            Settlement_Fee = settlement_Fee;
        }

        public String getLetter_DateStr() {
            return Letter_DateStr;
        }

        public void setLetter_DateStr(String Letter_DateStr) {
            this.Letter_DateStr = Letter_DateStr;
        }

        public String getLetter_Oid() {
            return Letter_Oid;
        }

        public void setLetter_Oid(String Letter_Oid) {
            this.Letter_Oid = Letter_Oid;
        }

        public String getCarCode() {
            return CarCode;
        }

        public void setCarCode(String CarCode) {
            this.CarCode = CarCode;
        }

        public String getTicket_Count() {
            return Ticket_Count;
        }

        public void setTicket_Count(String Ticket_Count) {
            this.Ticket_Count = Ticket_Count;
        }

        public String getTotal_Goods_Num() {
            return Total_Goods_Num;
        }

        public void setTotal_Goods_Num(String Total_Goods_Num) {
            this.Total_Goods_Num = Total_Goods_Num;
        }

        public String getTotal_Goods_Weight() {
            return Total_Goods_Weight;
        }

        public void setTotal_Goods_Weight(String Total_Goods_Weight) {
            this.Total_Goods_Weight = Total_Goods_Weight;
        }

        public String getTotal_Bulk_Weight() {
            return Total_Bulk_Weight;
        }

        public void setTotal_Bulk_Weight(String Total_Bulk_Weight) {
            this.Total_Bulk_Weight = Total_Bulk_Weight;
        }

        public String getOperate_Org() {
            return Operate_Org;
        }

        public void setOperate_Org(String Operate_Org) {
            this.Operate_Org = Operate_Org;
        }
    }
}
