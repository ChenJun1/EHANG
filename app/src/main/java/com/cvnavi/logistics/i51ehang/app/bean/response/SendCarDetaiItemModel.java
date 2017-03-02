package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.util.List;

/**
 * Created by george on 2016/12/18.
 */

public class SendCarDetaiItemModel {


    /**
     * DataValue : {"StowageList":[{"Letter_Oid":"B0000002016070600003","CarCode":"0000000000000","Driver":"22222","Driver_Tel":"22222","All_Count":"2","AllGoods_Num":"200","AllGoods_Weight":"1400.000","AllBulk_Weight":"20.000","Operate_DateTime":"2016-07-06 13:54:36"}],"MainTicket":[{"Ticket_No":"0000000009","SendStation":"上海市","ArrStation":"浙江省杭州市","Goods_Breed":"药品","Goods_Casing":"纸箱","Goods_Num":"100","Goods_Weight":"1300.000","Bulk_Weight":"10.000","Operate_DateTime":"2016-07-06"},{"Ticket_No":"2016070600","SendStation":"上海市","ArrStation":"上海市","Goods_Breed":"药品","Goods_Casing":"纸箱","Goods_Num":"100","Goods_Weight":"100.000","Bulk_Weight":"10.000","Operate_DateTime":"2016-07-06"}]}
     * Success : true
     * ErrorText : null
     * MsgType : null
     * RowCount : 0
     * Status : null
     * CompanyList : null
     */

    private DataValueBean DataValue;
    private boolean Success;
    private Object ErrorText;
    private Object MsgType;
    private int RowCount;
    private Object Status;
    private Object CompanyList;

    public DataValueBean getDataValue() {
        return DataValue;
    }

    public void setDataValue(DataValueBean DataValue) {
        this.DataValue = DataValue;
    }

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

    public static class DataValueBean {
        private List<StowageListBean> StowageList;
        private List<MainTicketBean> MainTicket;

        public List<StowageListBean> getStowageList() {
            return StowageList;
        }

        public void setStowageList(List<StowageListBean> StowageList) {
            this.StowageList = StowageList;
        }

        public List<MainTicketBean> getMainTicket() {
            return MainTicket;
        }

        public void setMainTicket(List<MainTicketBean> MainTicket) {
            this.MainTicket = MainTicket;
        }

        public static class StowageListBean {
            /**
             * Letter_Oid : B0000002016070600003
             * CarCode : 0000000000000
             * Driver : 22222
             * Driver_Tel : 22222
             * All_Count : 2
             * AllGoods_Num : 200
             * AllGoods_Weight : 1400.000
             * AllBulk_Weight : 20.000
             * Operate_DateTime : 2016-07-06 13:54:36
             */

            private String Letter_Oid;
            private String CarCode;
            private String Driver;
            private String Driver_Tel;
            private String All_Count;
            private String AllGoods_Num;
            private String AllGoods_Weight;
            private String AllBulk_Weight;
            private String Operate_DateTime;
            private String ThreeCompany;
            private String AllTicket_Count;

            public String getAllTicket_Count() {
                return AllTicket_Count;
            }

            public void setAllTicket_Count(String allTicket_Count) {
                AllTicket_Count = allTicket_Count;
            }

            public String getThreeCompany() {
                return ThreeCompany;
            }

            public void setThreeCompany(String threeCompany) {
                ThreeCompany = threeCompany;
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

            public String getDriver() {
                return Driver;
            }

            public void setDriver(String Driver) {
                this.Driver = Driver;
            }

            public String getDriver_Tel() {
                return Driver_Tel;
            }

            public void setDriver_Tel(String Driver_Tel) {
                this.Driver_Tel = Driver_Tel;
            }

            public String getAll_Count() {
                return All_Count;
            }

            public void setAll_Count(String All_Count) {
                this.All_Count = All_Count;
            }

            public String getAllGoods_Num() {
                return AllGoods_Num;
            }

            public void setAllGoods_Num(String AllGoods_Num) {
                this.AllGoods_Num = AllGoods_Num;
            }

            public String getAllGoods_Weight() {
                return AllGoods_Weight;
            }

            public void setAllGoods_Weight(String AllGoods_Weight) {
                this.AllGoods_Weight = AllGoods_Weight;
            }

            public String getAllBulk_Weight() {
                return AllBulk_Weight;
            }

            public void setAllBulk_Weight(String AllBulk_Weight) {
                this.AllBulk_Weight = AllBulk_Weight;
            }

            public String getOperate_DateTime() {
                return Operate_DateTime;
            }

            public void setOperate_DateTime(String Operate_DateTime) {
                this.Operate_DateTime = Operate_DateTime;
            }
        }

        public static class MainTicketBean {
            /**
             * Ticket_No : 0000000009
             * SendStation : 上海市
             * ArrStation : 浙江省杭州市
             * Goods_Breed : 药品
             * Goods_Casing : 纸箱
             * Goods_Num : 100
             * Goods_Weight : 1300.000
             * Bulk_Weight : 10.000
             * Operate_DateTime : 2016-07-06
             * <p>
             * Deliver_DateTime   签收时间
             * CarStates               提送货状态： 已提货/未提货， 已送货/未送货
             */

            private String Ticket_No;
            private String SendStation;
            private String ArrStation;
            private String Goods_Breed;
            private String Goods_Casing;
            private String Goods_Num;
            private String Goods_Weight;
            private String Bulk_Weight;
            private String Operate_DateTime;
            private String Deliver_DateTime;//签收时间
            private String CarStates;//

            public String getDeliver_DateTime() {
                return Deliver_DateTime;
            }

            public void setDeliver_DateTime(String deliver_DateTime) {
                Deliver_DateTime = deliver_DateTime;
            }

            public String getCarStates() {
                return CarStates;
            }

            public void setCarStates(String carStates) {
                CarStates = carStates;
            }

            public String getTicket_No() {
                return Ticket_No;
            }

            public void setTicket_No(String Ticket_No) {
                this.Ticket_No = Ticket_No;
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

            public String getOperate_DateTime() {
                return Operate_DateTime;
            }

            public void setOperate_DateTime(String Operate_DateTime) {
                this.Operate_DateTime = Operate_DateTime;
            }
        }
    }
}
