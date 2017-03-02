package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.util.List;

/**
 * Created by george on 2016/12/17.
 */

public class KuCunDetailModel {

    /**
     * DataValue : [{"All_Ticket_No":"0000000000000000000A2342342368","Ticket_No":"2342342368","Search_No":"342368","SendStation":"","ArrStation":"北京市","Goods_Breed":"77","FastGoods_Num":"20","FastGoods_Weight":"20.0","FastBulk_Weight":"20.0","Total_Fee":"0.0","RoomTimeOutHour":"937","RK_DateTime":"2016-11-08 11:51:14","Goods_Num":"20","Goods_Weight":"20.0","Bulk_Weight":"20.0"}]
     * Success : false
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
         * All_Ticket_No : 0000000000000000000A2342342368
         * Ticket_No : 2342342368
         * Search_No : 342368
         * SendStation :
         * ArrStation : 北京市
         * Goods_Breed : 77
         * FastGoods_Num : 20
         * FastGoods_Weight : 20.0
         * FastBulk_Weight : 20.0
         * Total_Fee : 0.0
         * RoomTimeOutHour : 937
         * RK_DateTime : 2016-11-08 11:51:14
         * Goods_Num : 20
         * Goods_Weight : 20.0
         * Bulk_Weight : 20.0
         * <p>
         * <p>
         * <p>
         * All_Ticket_No
         * 全票号
         * <p>
         * Ticket_No
         * 货单号
         * <p>
         * Search_No
         * 查询简码
         * <p>
         * SendStation
         * 始发地
         * <p>
         * ArrStation
         * 到达地
         * <p>
         * Goods_Breed
         * 品名
         * <p>
         * FastGoods_Num
         * 实际库存件数
         * <p>
         * FastGoods_Weight
         * 实际库存重量
         * <p>
         * FastBulk_Weight
         * 实际库存体积
         * <p>
         * Total_Fee
         * 票面收入
         * <p>
         * RoomTimeOutHour
         * 库存小时
         * <p>
         * RK_DateTime
         * 入库时间
         * <p>
         * Goods_Num
         * 票面件数
         * <p>
         * Goods_Weight
         * 票面重量
         * <p>
         * Bulk_Weight
         * 票面体积
         */

        private String All_Ticket_No;
        private String Ticket_No;
        private String Search_No;
        private String SendStation;
        private String ArrStation;
        private String Goods_Breed;
        private String FastGoods_Num;
        private String FastGoods_Weight;
        private String FastBulk_Weight;
        private String Total_Fee;
        private String RoomTimeOutHour;
        private String RK_DateTime;
        private String Goods_Num;
        private String Goods_Weight;
        private String Bulk_Weight;

        public String getAll_Ticket_No() {
            return All_Ticket_No;
        }

        public void setAll_Ticket_No(String All_Ticket_No) {
            this.All_Ticket_No = All_Ticket_No;
        }

        public String getTicket_No() {
            return Ticket_No;
        }

        public void setTicket_No(String Ticket_No) {
            this.Ticket_No = Ticket_No;
        }

        public String getSearch_No() {
            return Search_No;
        }

        public void setSearch_No(String Search_No) {
            this.Search_No = Search_No;
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

        public String getFastGoods_Num() {
            return FastGoods_Num;
        }

        public void setFastGoods_Num(String FastGoods_Num) {
            this.FastGoods_Num = FastGoods_Num;
        }

        public String getFastGoods_Weight() {
            return FastGoods_Weight;
        }

        public void setFastGoods_Weight(String FastGoods_Weight) {
            this.FastGoods_Weight = FastGoods_Weight;
        }

        public String getFastBulk_Weight() {
            return FastBulk_Weight;
        }

        public void setFastBulk_Weight(String FastBulk_Weight) {
            this.FastBulk_Weight = FastBulk_Weight;
        }

        public String getTotal_Fee() {
            return Total_Fee;
        }

        public void setTotal_Fee(String Total_Fee) {
            this.Total_Fee = Total_Fee;
        }

        public String getRoomTimeOutHour() {
            return RoomTimeOutHour;
        }

        public void setRoomTimeOutHour(String RoomTimeOutHour) {
            this.RoomTimeOutHour = RoomTimeOutHour;
        }

        public String getRK_DateTime() {
            return RK_DateTime;
        }

        public void setRK_DateTime(String RK_DateTime) {
            this.RK_DateTime = RK_DateTime;
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
    }
}
