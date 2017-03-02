package com.cvnavi.logistics.i51ehang.app.bean.model.home;

import java.util.List;

/**
 * Created by ${ChenJ} on 2016/10/8.
 */

public class HomePageBean {

    /**
     * Letter_Oid : A2101502016061700001
     * CarCode : 沪A888888
     * BoxCarCode : 挂.沪A99999
     * Line_Name : 上海-苏州-成都
     * Ticket_Count : 3
     * Goods_Num : 12
     * Leave_DateTime : 2016/10/8 9:55:54
     * Line_Type : 干
     * DeliveryNum : 12
     * SendNum : 34
     * CarCodeStatus 进行中
     */

    private LetterObjBean LetterObj;
    /**
     * AdvertImgList : [{"ImgId":"1","ImgName":"one","ImgUrl":"http://imgserver.i51ey.com:48181/HomePageImg/AppImg/1.png","ImgAdvert":"http://www.cvnavi.com/"},{"ImgId":"2","ImgName":"two","ImgUrl":"http://imgserver.i51ey.com:48181/HomePageImg/AppImg/2.png","ImgAdvert":"http://www.cvnavi.com/"},{"ImgId":"3","ImgName":"three","ImgUrl":"http://imgserver.i51ey.com:48181/HomePageImg/AppImg/3.png","ImgAdvert":"http://www.cvnavi.com/"}]
     * LetterObj : {"Letter_Oid":"A2101502016061700001","CarCode":"沪A888888","BoxCarCode":"挂.沪A99999","Line_Name":"上海-苏州-成都","Ticket_Count":"3","Goods_Num":"12","Leave_DateTime":"2016/10/8 9:55:54","Line_Type":"干","DeliveryNum":"12","SendNum":"34"}
     * EmployeePage : null
     */

    private EmployeePageBean EmployeePage;
    /**
     * ImgId : 1
     * ImgName : one
     * ImgUrl : http://imgserver.i51ey.com:48181/HomePageImg/AppImg/1.png
     * ImgAdvert : http://www.cvnavi.com/
     */

    private List<AdvertImgListBean> AdvertImgList;

    public LetterObjBean getLetterObj() {
        return LetterObj;
    }

    public void setLetterObj(LetterObjBean LetterObj) {
        this.LetterObj = LetterObj;
    }

    public EmployeePageBean getEmployeePage() {
        return EmployeePage;
    }

    public void setEmployeePage(EmployeePageBean EmployeePage) {
        this.EmployeePage = EmployeePage;
    }

    public List<AdvertImgListBean> getAdvertImgList() {
        return AdvertImgList;
    }

    public void setAdvertImgList(List<AdvertImgListBean> AdvertImgList) {
        this.AdvertImgList = AdvertImgList;
    }

    public static class EmployeePageBean{
        private String onWayNum;
        private String AlreadyPlanNum;
        private String ReachNum;
        private String NotifyNum;

        public String getOnWayNum() {
            return onWayNum;
        }

        public void setOnWayNum(String onWayNum) {
            this.onWayNum = onWayNum;
        }

        public String getAlreadyPlanNum() {
            return AlreadyPlanNum;
        }

        public void setAlreadyPlanNum(String alreadyPlanNum) {
            AlreadyPlanNum = alreadyPlanNum;
        }

        public String getReachNum() {
            return ReachNum;
        }

        public void setReachNum(String reachNum) {
            ReachNum = reachNum;
        }

        public String getNotifyNum() {
            return NotifyNum;
        }

        public void setNotifyNum(String notifyNum) {
            NotifyNum = notifyNum;
        }
    }

    public static class LetterObjBean {
        private String Letter_Oid;
        private String CarCode;
        private String BoxCarCode;
        private String Line_Name;
        private String Ticket_Count;
        private String Goods_Num;
        private String Leave_DateTime;
        private String Line_Type;
        private String DeliveryNum;
        private String SendNum;
        private String CarCodeStatus;
        private String CarCode_Date;
        private String MainDriver;//	主驾驶;
        private String MainDriver_Tel;//	主驾驶电话;
        private String SecondDriver;//	副驾驶;
        private String SecondDriver_Tel;//	副驾驶电话;
        private String Shuttle_Oid;//	班次ID;
        private String Shuttle_No;//
        private String  MsgCount;//消息数量
        private String  Traffic_Mode;//发车状态

        public String getTraffic_Mode() {
            return Traffic_Mode;
        }

        public void setTraffic_Mode(String traffic_Mode) {
            Traffic_Mode = traffic_Mode;
        }

        public String getMsgCount() {
            return MsgCount;
        }

        public void setMsgCount(String msgCount) {
            MsgCount = msgCount;
        }

        public String getCarCode_Date() {
            return CarCode_Date;
        }

        public void setCarCode_Date(String carCode_Date) {
            CarCode_Date = carCode_Date;
        }

        public String getMainDriver() {
            return MainDriver;
        }

        public void setMainDriver(String mainDriver) {
            MainDriver = mainDriver;
        }

        public String getMainDriver_Tel() {
            return MainDriver_Tel;
        }

        public void setMainDriver_Tel(String mainDriver_Tel) {
            MainDriver_Tel = mainDriver_Tel;
        }

        public String getSecondDriver() {
            return SecondDriver;
        }

        public void setSecondDriver(String secondDriver) {
            SecondDriver = secondDriver;
        }

        public String getSecondDriver_Tel() {
            return SecondDriver_Tel;
        }

        public void setSecondDriver_Tel(String secondDriver_Tel) {
            SecondDriver_Tel = secondDriver_Tel;
        }

        public String getShuttle_Oid() {
            return Shuttle_Oid;
        }

        public void setShuttle_Oid(String shuttle_Oid) {
            Shuttle_Oid = shuttle_Oid;
        }

        public String getShuttle_No() {
            return Shuttle_No;
        }

        public void setShuttle_No(String shuttle_No) {
            Shuttle_No = shuttle_No;
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
        public String getCarCodeStatus() {
            return CarCodeStatus;
        }

        public void setCarCodeStatus(String Letter_State) {
            this.CarCodeStatus = Letter_State;
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

        public String getLeave_DateTime() {
            return Leave_DateTime;
        }

        public void setLeave_DateTime(String Letter_Date) {
            this.Leave_DateTime = Letter_Date;
        }

        public String getLine_Type() {
            return Line_Type;
        }

        public void setLine_Type(String Line_Type) {
            this.Line_Type = Line_Type;
        }

        public String getDeliveryNum() {
            return DeliveryNum;
        }

        public void setDeliveryNum(String DeliveryNum) {
            this.DeliveryNum = DeliveryNum;
        }

        public String getSendNum() {
            return SendNum;
        }

        public void setSendNum(String SendNum) {
            this.SendNum = SendNum;
        }
    }

    public static class AdvertImgListBean {
        private String ImgId;
        private String ImgName;
        private String ImgUrl;
        private String ImgAdvert;

        public String getImgId() {
            return ImgId;
        }

        public void setImgId(String ImgId) {
            this.ImgId = ImgId;
        }

        public String getImgName() {
            return ImgName;
        }

        public void setImgName(String ImgName) {
            this.ImgName = ImgName;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }

        public String getImgAdvert() {
            return ImgAdvert;
        }

        public void setImgAdvert(String ImgAdvert) {
            this.ImgAdvert = ImgAdvert;
        }
    }
}
