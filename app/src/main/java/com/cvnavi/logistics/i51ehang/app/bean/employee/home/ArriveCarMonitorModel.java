package com.cvnavi.logistics.i51ehang.app.bean.employee.home;

import java.util.List;

/**
 * Created by george on 2016/12/16.
 */

public class ArriveCarMonitorModel {


    /**
     * DataValue : [{"num":1,"Line_Oid":"d200da01-0651-4194-9583-27b5d2b47f12","Shuttle_Oid":"58c2f86d-6d47-4bfb-9154-b28785a2493e","CarCode_Key":"11396209-8916-46d5-91d0-dca7036a181d","CarCode":"沪ATP698","Shuttle_No":"B001      ","End_Marker_Name":"赛托斯分公司1","Begin_Marker_Name":"成都分公司","StartTime":"2016-11-07 05:19:23","CurrentNodeName":"重庆分公司","NextNodeName":"赛托斯分公司1","Should_Arr_DateTime":"2016-12-14 12:28:08","Answer_Arr_DateTime":"2016-11-07 23:45","NextNodeMil":"17.9公里","Leave_DateTime":"0001-01-01 00:00:00","Arr_DateTime":"2016-11-07 11:05:56","Node_No":1,"Line_Name":"不允许修改删除-上海-重庆-成都","BeginLat":"30.474986","BeginLong":"104.373491","EndLat":"31.19318","EndLong":"121.384574","CHS_Address":"上海市浦东新区康沈路1933号-2室；佰田·大富苑附近46米附近","CurrentBLng":"121.586729859794","CurrentBLat":"31.1160606736507","LateDescribe":"36 天 12 小时 43 分钟 "}]
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
         * num : 1
         * Line_Oid : d200da01-0651-4194-9583-27b5d2b47f12
         * Shuttle_Oid : 58c2f86d-6d47-4bfb-9154-b28785a2493e
         * CarCode_Key : 11396209-8916-46d5-91d0-dca7036a181d
         * CarCode : 沪ATP698
         * Shuttle_No : B001
         * End_Marker_Name : 赛托斯分公司1
         * Begin_Marker_Name : 成都分公司
         * StartTime : 2016-11-07 05:19:23
         * CurrentNodeName : 重庆分公司
         * NextNodeName : 赛托斯分公司1
         * Should_Arr_DateTime : 2016-12-14 12:28:08
         * Answer_Arr_DateTime : 2016-11-07 23:45
         * NextNodeMil : 17.9公里
         * Leave_DateTime : 0001-01-01 00:00:00
         * Arr_DateTime : 2016-11-07 11:05:56
         * Node_No : 1
         * Line_Name : 不允许修改删除-上海-重庆-成都
         * BeginLat : 30.474986
         * BeginLong : 104.373491
         * EndLat : 31.19318
         * EndLong : 121.384574
         * CHS_Address : 上海市浦东新区康沈路1933号-2室；佰田·大富苑附近46米附近
         * CurrentBLng : 121.586729859794
         * CurrentBLat : 31.1160606736507
         * LateDescribe : 36 天 12 小时 43 分钟
         */

        private int num;
        private String Line_Oid;
        private String Shuttle_Oid;
        private String CarCode_Key;
        private String CarCode;
        private String Shuttle_No;
        private String End_Marker_Name;
        private String Begin_Marker_Name;
        private String StartTime;
        private String CurrentNodeName;
        private String NextNodeName;
        private String Should_Arr_DateTime;
        private String Answer_Arr_DateTime;
        private String NextNodeMil;
        private String Leave_DateTime;
        private String Arr_DateTime;
        private int Node_No;
        private String Line_Name;
        private String BeginLat;
        private String BeginLong;
        private String EndLat;
        private String EndLong;
        private String CHS_Address;
        private String CurrentBLng;
        private String CurrentBLat;
        private String LateDescribe;
        private String Sum;

        public String getSum() {
            return Sum;
        }

        public void setSum(String sum) {
            Sum = sum;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getLine_Oid() {
            return Line_Oid;
        }

        public void setLine_Oid(String Line_Oid) {
            this.Line_Oid = Line_Oid;
        }

        public String getShuttle_Oid() {
            return Shuttle_Oid;
        }

        public void setShuttle_Oid(String Shuttle_Oid) {
            this.Shuttle_Oid = Shuttle_Oid;
        }

        public String getCarCode_Key() {
            return CarCode_Key;
        }

        public void setCarCode_Key(String CarCode_Key) {
            this.CarCode_Key = CarCode_Key;
        }

        public String getCarCode() {
            return CarCode;
        }

        public void setCarCode(String CarCode) {
            this.CarCode = CarCode;
        }

        public String getShuttle_No() {
            return Shuttle_No;
        }

        public void setShuttle_No(String Shuttle_No) {
            this.Shuttle_No = Shuttle_No;
        }

        public String getEnd_Marker_Name() {
            return End_Marker_Name;
        }

        public void setEnd_Marker_Name(String End_Marker_Name) {
            this.End_Marker_Name = End_Marker_Name;
        }

        public String getBegin_Marker_Name() {
            return Begin_Marker_Name;
        }

        public void setBegin_Marker_Name(String Begin_Marker_Name) {
            this.Begin_Marker_Name = Begin_Marker_Name;
        }

        public String getStartTime() {
            return StartTime;
        }

        public void setStartTime(String StartTime) {
            this.StartTime = StartTime;
        }

        public String getCurrentNodeName() {
            return CurrentNodeName;
        }

        public void setCurrentNodeName(String CurrentNodeName) {
            this.CurrentNodeName = CurrentNodeName;
        }

        public String getNextNodeName() {
            return NextNodeName;
        }

        public void setNextNodeName(String NextNodeName) {
            this.NextNodeName = NextNodeName;
        }

        public String getShould_Arr_DateTime() {
            return Should_Arr_DateTime;
        }

        public void setShould_Arr_DateTime(String Should_Arr_DateTime) {
            this.Should_Arr_DateTime = Should_Arr_DateTime;
        }

        public String getAnswer_Arr_DateTime() {
            return Answer_Arr_DateTime;
        }

        public void setAnswer_Arr_DateTime(String Answer_Arr_DateTime) {
            this.Answer_Arr_DateTime = Answer_Arr_DateTime;
        }

        public String getNextNodeMil() {
            return NextNodeMil;
        }

        public void setNextNodeMil(String NextNodeMil) {
            this.NextNodeMil = NextNodeMil;
        }

        public String getLeave_DateTime() {
            return Leave_DateTime;
        }

        public void setLeave_DateTime(String Leave_DateTime) {
            this.Leave_DateTime = Leave_DateTime;
        }

        public String getArr_DateTime() {
            return Arr_DateTime;
        }

        public void setArr_DateTime(String Arr_DateTime) {
            this.Arr_DateTime = Arr_DateTime;
        }

        public int getNode_No() {
            return Node_No;
        }

        public void setNode_No(int Node_No) {
            this.Node_No = Node_No;
        }

        public String getLine_Name() {
            return Line_Name;
        }

        public void setLine_Name(String Line_Name) {
            this.Line_Name = Line_Name;
        }

        public String getBeginLat() {
            return BeginLat;
        }

        public void setBeginLat(String BeginLat) {
            this.BeginLat = BeginLat;
        }

        public String getBeginLong() {
            return BeginLong;
        }

        public void setBeginLong(String BeginLong) {
            this.BeginLong = BeginLong;
        }

        public String getEndLat() {
            return EndLat;
        }

        public void setEndLat(String EndLat) {
            this.EndLat = EndLat;
        }

        public String getEndLong() {
            return EndLong;
        }

        public void setEndLong(String EndLong) {
            this.EndLong = EndLong;
        }

        public String getCHS_Address() {
            return CHS_Address;
        }

        public void setCHS_Address(String CHS_Address) {
            this.CHS_Address = CHS_Address;
        }

        public String getCurrentBLng() {
            return CurrentBLng;
        }

        public void setCurrentBLng(String CurrentBLng) {
            this.CurrentBLng = CurrentBLng;
        }

        public String getCurrentBLat() {
            return CurrentBLat;
        }

        public void setCurrentBLat(String CurrentBLat) {
            this.CurrentBLat = CurrentBLat;
        }

        public String getLateDescribe() {
            return LateDescribe;
        }

        public void setLateDescribe(String LateDescribe) {
            this.LateDescribe = LateDescribe;
        }
    }
}
