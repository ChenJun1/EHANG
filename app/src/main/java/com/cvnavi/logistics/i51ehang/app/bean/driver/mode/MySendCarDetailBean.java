package com.cvnavi.logistics.i51ehang.app.bean.driver.mode;

import java.util.List;

/**
 * Created by ${ChenJ} on 2016/10/10.
 */

public class MySendCarDetailBean {


    /**
     * Letter_Oid : A0000002016041800006
     * CarCode : 4322
     * BoxCarCode :
     * Line_Name : 总公司-石家庄测试TMS-北京测试TMS2
     * Line_Type : 干
     * Leave_DateTime :
     * Operate_DateTime :
     * Arrive_DateTime :
     * Ticket_Count :
     * Goods_Num : 22
     * Goods_Weight : 194.118
     * Bulk_Weight : 1.941
     * CarCodeStatus :
     * Shuttle_Fee : 0.0
     * Line_Nodes : [{"Letter_Oid":"A0000002016041800006","Node_id":"2","Node_Key":"dfd02eaf-ba93-4732-825c-636a26467455","Leave":"1","Leave_DateTime":"2016-04-18 15:42:31","Arrive":"1","Arrive_DateTime":"2016-04-18 15:42:28","Org_Name":"石家庄测试TMS","City":"河北省石家庄市","ArriveImgNo":"0"},{"Letter_Oid":"A0000002016041800006","Node_id":"3","Node_Key":"9c180652-8d99-4a60-90db-a5ca036b371b","Leave":"0","Leave_DateTime":null,"Arrive":"1","Arrive_DateTime":"2016-04-19 10:16:52","Org_Name":"北京测试TMS3","City":"北京市","ArriveImgNo":"0"}]
     */

    private String Letter_Oid;
    private String CarCode;
    private String BoxCarCode;
    private String Line_Name;
    private String Line_Type;
    private String Leave_DateTime;
    private String Operate_DateTime;
    private String Arrive_DateTime;
    private String Ticket_Count;
    private String Goods_Num;
    private String Goods_Weight;
    private String Bulk_Weight;
    private String CarCodeStatus;
    private String Shuttle_Fee;
    private String Driver_Key;
    private String LPSCarCode_Key;

    public String getCarCode_Key() {
        return CarCode_Key;
    }

    public void setCarCode_Key(String carCode_Key) {
        CarCode_Key = carCode_Key;
    }

    private String CarCode_Key;
    private String Traffic_Mode;//	配载方式
    private String FullCar_Destination;//目的地


    public String getFullCar_Destination() {
        return FullCar_Destination;
    }

    public void setFullCar_Destination(String fullCar_Destination) {
        FullCar_Destination = fullCar_Destination;
    }

    public String getTraffic_Mode() {
        return Traffic_Mode;
    }

    public void setTraffic_Mode(String traffic_Mode) {
        Traffic_Mode = traffic_Mode;
    }

    public String getLPSCarCode_Key() {
        return LPSCarCode_Key;
    }

    public void setLPSCarCode_Key(String LPSCarCode_Key) {
        this.LPSCarCode_Key = LPSCarCode_Key;
    }

    public String getDriver_Key() {
        return Driver_Key;
    }

    public void setDriver_Key(String driver_Key) {
        Driver_Key = driver_Key;
    }

    /**
     * Letter_Oid : A0000002016041800006
     * Node_id : 2
     * Node_Key : dfd02eaf-ba93-4732-825c-636a26467455
     * Leave : 1
     * Leave_DateTime : 2016-04-18 15:42:31
     * Arrive : 1
     * Arrive_DateTime : 2016-04-18 15:42:28
     * Org_Name : 石家庄测试TMS
     * City : 河北省石家庄市
     * ArriveImgNo : 0
     */

    private List<LineNodesBean> Line_Nodes;

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

    public String getBoxCarCode() {
        return BoxCarCode;
    }

    public void setBoxCarCode(String BoxCarCode) {
        this.BoxCarCode = BoxCarCode;
    }

    public String getLine_Name() {
        return Line_Name;
    }

    public void setLine_Name(String Line_Name) {
        this.Line_Name = Line_Name;
    }

    public String getLine_Type() {
        return Line_Type;
    }

    public void setLine_Type(String Line_Type) {
        this.Line_Type = Line_Type;
    }

    public String getLeave_DateTime() {
        return Leave_DateTime;
    }

    public void setLeave_DateTime(String Leave_DateTime) {
        this.Leave_DateTime = Leave_DateTime;
    }

    public String getOperate_DateTime() {
        return Operate_DateTime;
    }

    public void setOperate_DateTime(String Operate_DateTime) {
        this.Operate_DateTime = Operate_DateTime;
    }

    public String getArrive_DateTime() {
        return Arrive_DateTime;
    }

    public void setArrive_DateTime(String Arrive_DateTime) {
        this.Arrive_DateTime = Arrive_DateTime;
    }

    public String getTicket_Count() {
        return Ticket_Count;
    }

    public void setTicket_Count(String Ticket_Count) {
        this.Ticket_Count = Ticket_Count;
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

    public String getCarCodeStatus() {
        return CarCodeStatus;
    }

    public void setCarCodeStatus(String CarCodeStatus) {
        this.CarCodeStatus = CarCodeStatus;
    }

    public String getShuttle_Fee() {
        return Shuttle_Fee;
    }

    public void setShuttle_Fee(String Shuttle_Fee) {
        this.Shuttle_Fee = Shuttle_Fee;
    }

    public List<LineNodesBean> getLine_Nodes() {
        return Line_Nodes;
    }

    public void setLine_Nodes(List<LineNodesBean> Line_Nodes) {
        this.Line_Nodes = Line_Nodes;
    }

    public static class LineNodesBean {
        private String Letter_Oid;
        private String Node_id;
        private String Node_Key;
        private String Leave;
        private String Leave_DateTime;
        private String Arrive;
        private String Arrive_DateTime;
        private String Org_Name;
        private String City;
        private String ArriveImgNo;

        public String getLetter_Oid() {
            return Letter_Oid;
        }

        public void setLetter_Oid(String Letter_Oid) {
            this.Letter_Oid = Letter_Oid;
        }

        public String getNode_id() {
            return Node_id;
        }

        public void setNode_id(String Node_id) {
            this.Node_id = Node_id;
        }

        public String getNode_Key() {
            return Node_Key;
        }

        public void setNode_Key(String Node_Key) {
            this.Node_Key = Node_Key;
        }

        public String getLeave() {
            return Leave;
        }

        public void setLeave(String Leave) {
            this.Leave = Leave;
        }

        public String getLeave_DateTime() {
            return Leave_DateTime;
        }

        public void setLeave_DateTime(String Leave_DateTime) {
            this.Leave_DateTime = Leave_DateTime;
        }

        public String getArrive() {
            return Arrive;
        }

        public void setArrive(String Arrive) {
            this.Arrive = Arrive;
        }

        public String getArrive_DateTime() {
            return Arrive_DateTime;
        }

        public void setArrive_DateTime(String Arrive_DateTime) {
            this.Arrive_DateTime = Arrive_DateTime;
        }

        public String getOrg_Name() {
            return Org_Name;
        }

        public void setOrg_Name(String Org_Name) {
            this.Org_Name = Org_Name;
        }

        public String getCity() {
            return City;
        }

        public void setCity(String City) {
            this.City = City;
        }

        public String getArriveImgNo() {
            return ArriveImgNo;
        }

        public void setArriveImgNo(String ArriveImgNo) {
            this.ArriveImgNo = ArriveImgNo;
        }
    }
}
