package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by george on 2016/11/8.
 */

public class SendCarMonitorResponse {

    /**
     * DataValue : [{"num":0,"Line_Oid":"af4bbb89-8e00-4626-b645-4bb0d1bc7c93","Shuttle_Oid":"98334453-9322-458e-80b0-c38694d2e6b7","CarCode_Key":"ed0f6391-33cb-4af8-81ae-dacc9a934317","CarCode":"沪BBR383","Shuttle_No":"003 ","End_Marker_Name":"广州分公司","Begin_Marker_Name":"赛托斯分公司1","StartTime":"2016-11-06 05:22:44","CurrentNodeName":"重庆分公司","NextNodeName":"广州分公司","Should_Arr_DateTime":"2016-11-07 05:10:15","Answer_Arr_DateTime":"2016-11-06 23:00","NextNodeMil":null,"Leave_DateTime":"2016-11-06 15:11:09","Arr_DateTime":"2016-11-06 14:56:11","Node_No":1,"Line_Name":"上海-重庆-广州","BeginLat":"31.374542","BeginLong":"121.27633","EndLat":"23.194387","EndLong":"113.309978","CurrentAddress":"上海市普陀区武宁路766号；港鸿大酒店东北166米附近","CurrentBLng":"121.42276202682","CurrentBLat":"31.2509084697934"}]
     * Success : true
     * ErrorText : null
     * MsgType : null
     * RowCount : 0
     */

    private boolean Success;
    private Object ErrorText;
    private Object MsgType;
    private int RowCount;
    /**
     * num : 0
     * Line_Oid : af4bbb89-8e00-4626-b645-4bb0d1bc7c93
     * Shuttle_Oid : 98334453-9322-458e-80b0-c38694d2e6b7
     * CarCode_Key : ed0f6391-33cb-4af8-81ae-dacc9a934317
     * CarCode : 沪BBR383
     * Shuttle_No : 003
     * End_Marker_Name : 广州分公司
     * Begin_Marker_Name : 赛托斯分公司1
     * StartTime : 2016-11-06 05:22:44
     * CurrentNodeName : 重庆分公司
     * NextNodeName : 广州分公司
     * Should_Arr_DateTime : 2016-11-07 05:10:15
     * Answer_Arr_DateTime : 2016-11-06 23:00
     * NextNodeMil : null
     * Leave_DateTime : 2016-11-06 15:11:09
     * Arr_DateTime : 2016-11-06 14:56:11
     * Node_No : 1
     * Line_Name : 上海-重庆-广州
     * BeginLat : 31.374542
     * BeginLong : 121.27633
     * EndLat : 23.194387
     * EndLong : 113.309978
     * CurrentAddress : 上海市普陀区武宁路766号；港鸿大酒店东北166米附近
     * CurrentBLng : 121.42276202682
     * CurrentBLat : 31.2509084697934
     *
     *
     *
     /// <summary>
     /// 线路Key
     /// </summary>
     public String Line_Oid { get; set; }
     /// <summary>
     /// 班次Key
     /// </summary>
     public String Shuttle_Oid { get; set; }
     /// <summary>
     /// 车辆Key
     /// </summary>
     public String CarCode_Key { get; set; }
     /// <summary>
     /// 车牌号
     /// </summary>
     public String CarCode { get; set; }
     /// <summary>
     /// 班次名
     /// </summary>
     public String Shuttle_No { get; set; }
     /// <summary>
     /// 目的地
     /// </summary>
     public String End_Marker_Name { get; set; }
     /// <summary>
     /// 始发地
     /// </summary>
     public String Begin_Marker_Name { get; set; }
     /// <summary>
     /// 实际发车时间
     /// </summary>
     public String StartTime { get; set; }
     /// <summary>
     /// 当前节点名
     /// </summary>
     public String CurrentNodeName { get; set; }
     /// <summary>
     /// 当前节点纬度
     /// </summary>
     public String CurrentLat { get; set; }
     /// <summary>
     /// 当前节点经度
     /// </summary>
     public String CurrentLong { get; set; }
     /// <summary>
     /// 即将到达的节点名
     /// </summary>
     public String NextNodeName { get; set; }
     /// <summary>
     /// 下一节点纬度
     /// </summary>
     public String NextLat { get; set; }
     /// <summary>
     /// 下一节点经度
     /// </summary>
     public String NextLong { get; set; }
     /// <summary>
     /// 预计到达的时间
     /// </summary>
     public String Should_Arr_DateTime { get; set; }
     /// <summary>
     /// 排序规则时间差
     /// </summary>
     public double Interval { get; set; }
     /// <summary>
     /// 预计时间描述
     /// </summary>
     public String Describe { get; set; }
     /// <summary>
     /// 晚点时间描述
     /// </summary>
     public String LateDescribe { get; set; }
     /// <summary>
     /// 运行时间描述
     /// </summary>
     public String FunctionTimeDescribe { get; set; }
     /// <summary>
     /// 到达节点时间
     /// </summary>
     public String NextNode_Arr_DateTime { get; set; }
     /// <summary>
     /// 应到达的时间
     /// </summary>
     public String Answer_Arr_DateTime { get; set; }
     /// <summary>
     /// 剩余里程
     /// </summary>
     public String NextNodeMil { get; set; }
     /// <summary>
     /// 离开当前节点的时间
     /// </summary>
     public String Leave_DateTime { get; set; }
     /// <summary>
     /// 到达当前节点的时间
     /// </summary>
     public String Arr_DateTime { get; set; }
     /// <summary>
     /// 当前节点序号
     /// </summary>
     public Int32 Node_No { get; set; }
     /// <summary>
     /// 线路名称
     /// </summary>
     public String Line_Name { get; set; }
     /// <summary>
     /// 始发地纬度
     /// </summary>
     public String BeginLat { get; set; }
     /// <summary>
     /// 始发地经度
     /// </summary>
     public String BeginLong { get; set; }
     /// <summary>
     /// 目的地纬度
     /// </summary>
     public String EndLat { get; set; }
     /// <summary>
     /// 目的地经度
     /// </summary>
     public String EndLong { get; set; }
     /// <summary>
     /// 时间类型（0：定时点，1：定时长）
     /// </summary>
     public String Dispatch_Type_Oid { get; set; }
     /// <summary>
     /// 当前地址
     /// </summary>
     public String CHS_Address { get; set; }
     /// <summary>
     /// 进度条
     /// </summary>
     public String Run_Ratio { get; set; }
     /// <summary>
     /// gps是否异常
     /// </summary>
     public Int32 GPS_Status { get; set; }
     *
     */

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

    public List<DataValueBean> getDataValue() {
        return DataValue;
    }

    public void setDataValue(List<DataValueBean> DataValue) {
        this.DataValue = DataValue;
    }

    public static class DataValueBean implements Serializable {
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
        private Object NextNodeMil;
        private String Leave_DateTime;
        private String Arr_DateTime;
        private int Node_No;
        private String Line_Name;
        private String BeginLat;
        private String BeginLong;
        private String EndLat;
        private String EndLong;
        private String CurrentAddress;
        private String CurrentBLng;
        private String CurrentBLat;
        private String CHS_Address;
        private String Sum;

        public String CurrentLong;
        public String CurrentLat;
        public String NextLong;
        public String NextLat;
        public String Run_Ratio;//进度

        private String Describe;

        private String GPS_Status;//0正常 其余的异常

        private String  NextNode_Arr_DateTime;


        public String getGPS_Status() {
            return GPS_Status;
        }

        public void setGPS_Status(String GPS_Status) {
            this.GPS_Status = GPS_Status;
        }

        public String getDescribe() {
            return Describe;
        }

        public void setDescribe(String describe) {
            Describe = describe;
        }

        public String getNextNode_Arr_DateTime() {
            return NextNode_Arr_DateTime;
        }

        public void setNextNode_Arr_DateTime(String nextNode_Arr_DateTime) {
            NextNode_Arr_DateTime = nextNode_Arr_DateTime;
        }

        public String getRun_Ratio() {
            return Run_Ratio;
        }

        public void setRun_Ratio(String run_Ratio) {
            Run_Ratio = run_Ratio;
        }

        public String getCurrentLong() {
            return CurrentLong;
        }

        public void setCurrentLong(String currentLong) {
            CurrentLong = currentLong;
        }

        public String getCurrentLat() {
            return CurrentLat;
        }

        public void setCurrentLat(String currentLat) {
            CurrentLat = currentLat;
        }

        public String getNextLong() {
            return NextLong;
        }

        public void setNextLong(String nextLong) {
            NextLong = nextLong;
        }

        public String getNextLat() {
            return NextLat;
        }

        public void setNextLat(String nextLat) {
            NextLat = nextLat;
        }

        public String getSum() {
            return Sum;
        }

        public void setSum(String sum) {
            Sum = sum;
        }

        public String getCHS_Address() {
            return CHS_Address;
        }

        public void setCHS_Address(String CHS_Address) {
            this.CHS_Address = CHS_Address;
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

        public Object getNextNodeMil() {
            return NextNodeMil;
        }

        public void setNextNodeMil(Object NextNodeMil) {
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

        public String getCurrentAddress() {
            return CurrentAddress;
        }

        public void setCurrentAddress(String CurrentAddress) {
            this.CurrentAddress = CurrentAddress;
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
    }
}
