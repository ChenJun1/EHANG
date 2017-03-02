package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/9.
 * 定位信息的返回类
 */
public class GetCarGPSAggregateResponse extends DataResponseBase {

    private DataValueBean DataValue;

    public DataValueBean getDataValue() {
        return DataValue;
    }

    public void setDataValue(DataValueBean DataValue) {
        this.DataValue = DataValue;
    }

    public static class DataValueBean {
        private CarCodeScheduleBean CarCode_Schedule;
        private CarGPSInfoBean CarGPSInfo;
        private Object CarLineNode;
        private LetterListBean Letter_List;
        private List<CarMinleagesBean> CarMinleages;


        public LetterListBean getLetter_List() {
            return Letter_List;
        }

        public void setLetter_List(LetterListBean letter_List) {
            Letter_List = letter_List;
        }

        public CarCodeScheduleBean getCarCode_Schedule() {
            return CarCode_Schedule;
        }

        public void setCarCode_Schedule(CarCodeScheduleBean CarCode_Schedule) {
            this.CarCode_Schedule = CarCode_Schedule;
        }

        public CarGPSInfoBean getCarGPSInfo() {
            return CarGPSInfo;
        }

        public void setCarGPSInfo(CarGPSInfoBean CarGPSInfo) {
            this.CarGPSInfo = CarGPSInfo;
        }

        public Object getCarLineNode() {
            return CarLineNode;
        }

        public void setCarLineNode(Object CarLineNode) {
            this.CarLineNode = CarLineNode;
        }

        public List<CarMinleagesBean> getCarMinleages() {
            return CarMinleages;
        }

        public void setCarMinleages(List<CarMinleagesBean> CarMinleages) {
            this.CarMinleages = CarMinleages;
        }


        public static class LetterListBean implements Serializable {
            /**
             * num : 1
             * Letter_Oid : B2101062016101400014
             * Ticket_Count : 1票数
             * Goods_Num : 20214件数
             * Goods_Weight : 11892.08重量
             * Bulk_Weight : 0.0放量
             * Letter_Type : 派车
             * Letter_Type_Oid : B
             * TicketStatus : 未完成
             * Letter_Date : 2016-10-14
             * Line_Oid :
             * Line_Name :
             * Line_Type :
             * Traffic_Mode : 自有车辆
             * AllReceivableAccount : 0.0
             * Shuttle_Fee : 0.0
             * Ticket_Fee : 13081.29
             * Profit_Fee : 13081.29
             * Prepayments : 0.0
             * CarCode_No :
             * ZX_Fee : 0.0
             * Settlement_Fee : 0.0
             * Pay_Type_Oid : A
             * Pay_Type :
             * DriverSerial_Oid : 2e106050-135b-4c5a-9d7a-f62fa22de689
             * Driver : 司机1
             * Driver_Tel : 13111111101
             * CarCodeSerial_Oid : 1025885b-8098-499e-87da-d3b646e222bf
             * CarCode : 浙ABV776
             * GPS_Key : 14bd95ad-d08a-4024-aaa8-b5d94dc4d721
             * GPS : 浙ABV776
             * BoxCarCode : 无
             * BoxGPS_Key :
             * Operate_Name : bj
             * Operate_Org : 101004
             * Operate_DateTime : 2016-10-14 21:19:55
             * IsAddLetter : 否
             * Transaction_Status_Oid : 0
             * fullCar_Destination :
             * BLat :
             * BLng :
             * Transaction_Status : 未发送
             */

            private String num;
            private String Letter_Oid;
            private String Ticket_Count;
            private String Goods_Num;
            private String Goods_Weight;
            private String Bulk_Weight;
            private String Letter_Type;
            private String Letter_Type_Oid;
            private String TicketStatus;
            private String Letter_Date;
            private String Line_Oid;
            private String Line_Name;
            private String Line_Type;
            private String Traffic_Mode;
            private String AllReceivableAccount;
            private String Shuttle_Fee;
            private String Ticket_Fee;
            private String Profit_Fee;
            private String Prepayments;
            private String CarCode_No;
            private String ZX_Fee;
            private String Settlement_Fee;
            private String Pay_Type_Oid;
            private String Pay_Type;
            private String DriverSerial_Oid;
            private String Driver;
            private String Driver_Tel;
            private String CarCodeSerial_Oid;
            private String CarCode;
            private String GPS_Key;
            private String GPS;
            private String BoxCarCode;
            private String BoxGPS_Key;
            private String Operate_Name;
            private String Operate_Org;
            private String Operate_DateTime;
            private String IsAddLetter;
            private String Transaction_Status_Oid;
            private String fullCar_Destination;
            private String BLat;
            private String BLng;
            private String Transaction_Status;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getLetter_Oid() {
                return Letter_Oid;
            }

            public void setLetter_Oid(String Letter_Oid) {
                this.Letter_Oid = Letter_Oid;
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

            public String getLetter_Type() {
                return Letter_Type;
            }

            public void setLetter_Type(String Letter_Type) {
                this.Letter_Type = Letter_Type;
            }

            public String getLetter_Type_Oid() {
                return Letter_Type_Oid;
            }

            public void setLetter_Type_Oid(String Letter_Type_Oid) {
                this.Letter_Type_Oid = Letter_Type_Oid;
            }

            public String getTicketStatus() {
                return TicketStatus;
            }

            public void setTicketStatus(String TicketStatus) {
                this.TicketStatus = TicketStatus;
            }

            public String getLetter_Date() {
                return Letter_Date;
            }

            public void setLetter_Date(String Letter_Date) {
                this.Letter_Date = Letter_Date;
            }

            public String getLine_Oid() {
                return Line_Oid;
            }

            public void setLine_Oid(String Line_Oid) {
                this.Line_Oid = Line_Oid;
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

            public String getTraffic_Mode() {
                return Traffic_Mode;
            }

            public void setTraffic_Mode(String Traffic_Mode) {
                this.Traffic_Mode = Traffic_Mode;
            }

            public String getAllReceivableAccount() {
                return AllReceivableAccount;
            }

            public void setAllReceivableAccount(String AllReceivableAccount) {
                this.AllReceivableAccount = AllReceivableAccount;
            }

            public String getShuttle_Fee() {
                return Shuttle_Fee;
            }

            public void setShuttle_Fee(String Shuttle_Fee) {
                this.Shuttle_Fee = Shuttle_Fee;
            }

            public String getTicket_Fee() {
                return Ticket_Fee;
            }

            public void setTicket_Fee(String Ticket_Fee) {
                this.Ticket_Fee = Ticket_Fee;
            }

            public String getProfit_Fee() {
                return Profit_Fee;
            }

            public void setProfit_Fee(String Profit_Fee) {
                this.Profit_Fee = Profit_Fee;
            }

            public String getPrepayments() {
                return Prepayments;
            }

            public void setPrepayments(String Prepayments) {
                this.Prepayments = Prepayments;
            }

            public String getCarCode_No() {
                return CarCode_No;
            }

            public void setCarCode_No(String CarCode_No) {
                this.CarCode_No = CarCode_No;
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

            public void setSettlement_Fee(String Settlement_Fee) {
                this.Settlement_Fee = Settlement_Fee;
            }

            public String getPay_Type_Oid() {
                return Pay_Type_Oid;
            }

            public void setPay_Type_Oid(String Pay_Type_Oid) {
                this.Pay_Type_Oid = Pay_Type_Oid;
            }

            public String getPay_Type() {
                return Pay_Type;
            }

            public void setPay_Type(String Pay_Type) {
                this.Pay_Type = Pay_Type;
            }

            public String getDriverSerial_Oid() {
                return DriverSerial_Oid;
            }

            public void setDriverSerial_Oid(String DriverSerial_Oid) {
                this.DriverSerial_Oid = DriverSerial_Oid;
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

            public String getCarCodeSerial_Oid() {
                return CarCodeSerial_Oid;
            }

            public void setCarCodeSerial_Oid(String CarCodeSerial_Oid) {
                this.CarCodeSerial_Oid = CarCodeSerial_Oid;
            }

            public String getCarCode() {
                return CarCode;
            }

            public void setCarCode(String CarCode) {
                this.CarCode = CarCode;
            }

            public String getGPS_Key() {
                return GPS_Key;
            }

            public void setGPS_Key(String GPS_Key) {
                this.GPS_Key = GPS_Key;
            }

            public String getGPS() {
                return GPS;
            }

            public void setGPS(String GPS) {
                this.GPS = GPS;
            }

            public String getBoxCarCode() {
                return BoxCarCode;
            }

            public void setBoxCarCode(String BoxCarCode) {
                this.BoxCarCode = BoxCarCode;
            }

            public String getBoxGPS_Key() {
                return BoxGPS_Key;
            }

            public void setBoxGPS_Key(String BoxGPS_Key) {
                this.BoxGPS_Key = BoxGPS_Key;
            }

            public String getOperate_Name() {
                return Operate_Name;
            }

            public void setOperate_Name(String Operate_Name) {
                this.Operate_Name = Operate_Name;
            }

            public String getOperate_Org() {
                return Operate_Org;
            }

            public void setOperate_Org(String Operate_Org) {
                this.Operate_Org = Operate_Org;
            }

            public String getOperate_DateTime() {
                return Operate_DateTime;
            }

            public void setOperate_DateTime(String Operate_DateTime) {
                this.Operate_DateTime = Operate_DateTime;
            }

            public String getIsAddLetter() {
                return IsAddLetter;
            }

            public void setIsAddLetter(String IsAddLetter) {
                this.IsAddLetter = IsAddLetter;
            }

            public String getTransaction_Status_Oid() {
                return Transaction_Status_Oid;
            }

            public void setTransaction_Status_Oid(String Transaction_Status_Oid) {
                this.Transaction_Status_Oid = Transaction_Status_Oid;
            }


            public String getFullCar_Destination() {
                return fullCar_Destination;
            }

            public void setFullCar_Destination(String fullCar_Destination) {
                this.fullCar_Destination = fullCar_Destination;
            }

            public String getBLat() {
                return BLat;
            }

            public void setBLat(String BLat) {
                this.BLat = BLat;
            }

            public String getBLng() {
                return BLng;
            }

            public void setBLng(String BLng) {
                this.BLng = BLng;
            }

            public String getTransaction_Status() {
                return Transaction_Status;
            }

            public void setTransaction_Status(String Transaction_Status) {
                this.Transaction_Status = Transaction_Status;
            }

        }


        public static class CarCodeScheduleBean {
            private String CarCode_No;
            private String BeginDate;
            private String EndDate;
            private String Serial_Oid;
            private String CarCodeSerial_Oid;
            private String CarCode_Date;
            private String Line_Oid;
            private String Forecast_Leave_DateTime;
            private String CarCode_NO;
            private String MainDriverSerial_Oid;
            private String SecondDriverSerial_Oid;
            private String CarSchedule_Note;
            private String Operate_Name;
            private String Operate_Org;
            private String Operate_DateTime;
            private String Driver_Tel;
            private String CarCode;
            private String Line_Type;
            private String Line_Name;
            private String Traffic_Mode;
            private String Schedule_Status;
            private String Leave_DateTime;
            private String MainDriver;
            private String LPSCarCode_Key;
            private String BoxCarCode_Key;//挂车Key

            private String BoxCarCode = null;//挂车号
            private String SecondDriver = null;//挂车号
            private String SecondDriver_Tel = null;//
            private String MainDriver_Tel = null;//挂车号

            public String getSecondDriver_Tel() {
                return SecondDriver_Tel;
            }

            public void setSecondDriver_Tel(String secondDriver_Tel) {
                SecondDriver_Tel = secondDriver_Tel;
            }

            public String getMainDriver_Tel() {
                return MainDriver_Tel;
            }

            public void setMainDriver_Tel(String mainDriver_Tel) {
                MainDriver_Tel = mainDriver_Tel;
            }

            public String getBoxCarCode_Key() {
                return BoxCarCode_Key;
            }

            public void setBoxCarCode_Key(String boxCarCode_Key) {
                BoxCarCode_Key = boxCarCode_Key;
            }

            public String getBoxCarCode() {
                return BoxCarCode;
            }

            public void setBoxCarCode(String boxCarCode) {
                BoxCarCode = boxCarCode;
            }

            public String getCarCode_No() {
                return CarCode_No;
            }

            public void setCarCode_No(String CarCode_No) {
                this.CarCode_No = CarCode_No;
            }

            public String getBeginDate() {
                return BeginDate;
            }

            public void setBeginDate(String BeginDate) {
                this.BeginDate = BeginDate;
            }

            public String getEndDate() {
                return EndDate;
            }

            public void setEndDate(String EndDate) {
                this.EndDate = EndDate;
            }

            public String getSerial_Oid() {
                return Serial_Oid;
            }

            public void setSerial_Oid(String Serial_Oid) {
                this.Serial_Oid = Serial_Oid;
            }

            public String getCarCodeSerial_Oid() {
                return CarCodeSerial_Oid;
            }

            public void setCarCodeSerial_Oid(String CarCodeSerial_Oid) {
                this.CarCodeSerial_Oid = CarCodeSerial_Oid;
            }

            public String getCarCode_Date() {
                return CarCode_Date;
            }

            public void setCarCode_Date(String CarCode_Date) {
                this.CarCode_Date = CarCode_Date;
            }

            public String getLine_Oid() {
                return Line_Oid;
            }

            public void setLine_Oid(String Line_Oid) {
                this.Line_Oid = Line_Oid;
            }

            public String getForecast_Leave_DateTime() {
                return Forecast_Leave_DateTime;
            }

            public void setForecast_Leave_DateTime(String Forecast_Leave_DateTime) {
                this.Forecast_Leave_DateTime = Forecast_Leave_DateTime;
            }

            public String getCarCode_NO() {
                return CarCode_NO;
            }

            public void setCarCode_NO(String CarCode_NO) {
                this.CarCode_NO = CarCode_NO;
            }

            public String getMainDriverSerial_Oid() {
                return MainDriverSerial_Oid;
            }

            public void setMainDriverSerial_Oid(String MainDriverSerial_Oid) {
                this.MainDriverSerial_Oid = MainDriverSerial_Oid;
            }

            public String getSecondDriverSerial_Oid() {
                return SecondDriverSerial_Oid;
            }

            public void setSecondDriverSerial_Oid(String SecondDriverSerial_Oid) {
                this.SecondDriverSerial_Oid = SecondDriverSerial_Oid;
            }

            public String getCarSchedule_Note() {
                return CarSchedule_Note;
            }

            public void setCarSchedule_Note(String CarSchedule_Note) {
                this.CarSchedule_Note = CarSchedule_Note;
            }

            public String getOperate_Name() {
                return Operate_Name;
            }

            public void setOperate_Name(String Operate_Name) {
                this.Operate_Name = Operate_Name;
            }

            public String getOperate_Org() {
                return Operate_Org;
            }

            public void setOperate_Org(String Operate_Org) {
                this.Operate_Org = Operate_Org;
            }

            public String getOperate_DateTime() {
                return Operate_DateTime;
            }

            public void setOperate_DateTime(String Operate_DateTime) {
                this.Operate_DateTime = Operate_DateTime;
            }

            public String getDriver_Tel() {
                return Driver_Tel;
            }

            public void setDriver_Tel(String Driver_Tel) {
                this.Driver_Tel = Driver_Tel;
            }

            public String getCarCode() {
                return CarCode;
            }

            public void setCarCode(String CarCode) {
                this.CarCode = CarCode;
            }

            public String getLine_Type() {
                return Line_Type;
            }

            public void setLine_Type(String Line_Type) {
                this.Line_Type = Line_Type;
            }

            public String getLine_Name() {
                return Line_Name;
            }

            public void setLine_Name(String Line_Name) {
                this.Line_Name = Line_Name;
            }

            public String getTraffic_Mode() {
                return Traffic_Mode;
            }

            public void setTraffic_Mode(String Traffic_Mode) {
                this.Traffic_Mode = Traffic_Mode;
            }

            public String getSchedule_Status() {
                return Schedule_Status;
            }

            public void setSchedule_Status(String Schedule_Status) {
                this.Schedule_Status = Schedule_Status;
            }

            public String getLeave_DateTime() {
                return Leave_DateTime;
            }

            public void setLeave_DateTime(String Leave_DateTime) {
                this.Leave_DateTime = Leave_DateTime;
            }

            public String getMainDriver() {
                return MainDriver;
            }

            public void setMainDriver(String MainDriver) {
                this.MainDriver = MainDriver;
            }

            public String getSecondDriver() {
                return SecondDriver;
            }

            public void setSecondDriver(String SecondDriver) {
                this.SecondDriver = SecondDriver;
            }

            public String getLPSCarCode_Key() {
                return LPSCarCode_Key;
            }

            public void setLPSCarCode_Key(String LPSCarCode_Key) {
                this.LPSCarCode_Key = LPSCarCode_Key;
            }
        }

        public static class CarGPSInfoBean {
            private String CarCode;
            private String CHS_Provice;
            private String CHS_City;
            private String CHS_Address;
            private String Ticket_No;
            private String BLng;
            private String BLat;
            private String GLng;
            private String GLat;
            private String Status;
            private String Serial_Oid;
            private String Speed;//速度
            private String Mileage;
            private String StartAddress;
            private String CarStartTime;
            private String CarArrivaTime;
            private String CarleaveTime;
            private String DestinationAddress;
            private String CarCode_Key;
            private String TSP_CarCode_Key;
            private String TemperatureCount;//当前车辆温度
            private String Direction;//方向

            private String Position_DateTime;//定位时间
            private String LeaveDuration;//离线多长时间
            private String Line_Name;//关联线路

            private String Driver;    //司机名称
            private String Driver_Tel;//联系方式
            private String SecondDriver;    //副司机
            private String SecondDriver_Tel;//副司机手机号
            private String CarStatus_Oid;//车辆运行状态（0 在途 1 空闲 2 已计划 3 将到达）

            public String getDirection() {
                return Direction;
            }

            public void setDirection(String direction) {
                Direction = direction;
            }

            public String getCarStatus_Oid() {
                return CarStatus_Oid;
            }

            public void setCarStatus_Oid(String carStatus_Oid) {
                CarStatus_Oid = carStatus_Oid;
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

            public String getLine_Name() {
                return Line_Name;
            }

            public void setLine_Name(String line_Name) {
                Line_Name = line_Name;
            }

            public String getPosition_DateTime() {
                return Position_DateTime;
            }

            public void setPosition_DateTime(String position_DateTime) {
                Position_DateTime = position_DateTime;
            }


            public String getLeaveDuration() {
                return LeaveDuration;
            }

            public void setLeaveDuration(String leaveDuration) {
                LeaveDuration = leaveDuration;
            }

            public String getTemperatureCount() {
                return TemperatureCount;
            }

            public void setTemperatureCount(String temperatureCount) {
                TemperatureCount = temperatureCount;
            }

            public String getTSP_CarCode_Key() {
                return TSP_CarCode_Key;
            }

            public void setTSP_CarCode_Key(String TSP_CarCode_Key) {
                this.TSP_CarCode_Key = TSP_CarCode_Key;
            }

            public String getCarCode_Key() {
                return CarCode_Key;
            }

            public void setCarCode_Key(String carCode_Key) {
                CarCode_Key = carCode_Key;
            }

            public String getCarCode() {
                return CarCode;
            }

            public void setCarCode(String CarCode) {
                this.CarCode = CarCode;
            }

            public String getCHS_Provice() {
                return CHS_Provice;
            }

            public void setCHS_Provice(String CHS_Provice) {
                this.CHS_Provice = CHS_Provice;
            }

            public String getCHS_City() {
                return CHS_City;
            }

            public void setCHS_City(String CHS_City) {
                this.CHS_City = CHS_City;
            }

            public String getCHS_Address() {
                return CHS_Address;
            }

            public void setCHS_Address(String CHS_Address) {
                this.CHS_Address = CHS_Address;
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

            public String getTicket_No() {
                return Ticket_No;
            }

            public void setTicket_No(String Ticket_No) {
                this.Ticket_No = Ticket_No;
            }

            public String getBLng() {
                return BLng;
            }

            public void setBLng(String BLng) {
                this.BLng = BLng;
            }

            public String getBLat() {
                return BLat;
            }

            public void setBLat(String BLat) {
                this.BLat = BLat;
            }

            public String getGLng() {
                return GLng;
            }

            public void setGLng(String GLng) {
                this.GLng = GLng;
            }

            public String getGLat() {
                return GLat;
            }

            public void setGLat(String GLat) {
                this.GLat = GLat;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }

            public String getSerial_Oid() {
                return Serial_Oid;
            }

            public void setSerial_Oid(String Serial_Oid) {
                this.Serial_Oid = Serial_Oid;
            }

            public String getSpeed() {
                return Speed;
            }

            public void setSpeed(String Speed) {
                this.Speed = Speed;
            }

            public String getMileage() {
                return Mileage;
            }

            public void setMileage(String Mileage) {
                this.Mileage = Mileage;
            }

            public String getStartAddress() {
                return StartAddress;
            }

            public void setStartAddress(String StartAddress) {
                this.StartAddress = StartAddress;
            }

            public String getCarStartTime() {
                return CarStartTime;
            }

            public void setCarStartTime(String CarStartTime) {
                this.CarStartTime = CarStartTime;
            }

            public String getCarArrivaTime() {
                return CarArrivaTime;
            }

            public void setCarArrivaTime(String CarArrivaTime) {
                this.CarArrivaTime = CarArrivaTime;
            }

            public String getCarleaveTime() {
                return CarleaveTime;
            }

            public void setCarleaveTime(String CarleaveTime) {
                this.CarleaveTime = CarleaveTime;
            }

            public String getDestinationAddress() {
                return DestinationAddress;
            }

            public void setDestinationAddress(String DestinationAddress) {
                this.DestinationAddress = DestinationAddress;
            }
        }


        public static class CarMinleagesBean {
            private String Summary_Date;
            private String DistanceGPS;
            private String CarCode;
            private String PlateCode;
            private String Summary_Mileage;
            /**
             * DistanceGPS : 360.11
             * StartTime : 2016-10-03
             */

            private List<DetailBean> Detail;

            public String getSummary_Date() {
                return Summary_Date;
            }

            public void setSummary_Date(String Summary_Date) {
                this.Summary_Date = Summary_Date;
            }

            public String getDistanceGPS() {
                return DistanceGPS;
            }

            public void setDistanceGPS(String DistanceGPS) {
                this.DistanceGPS = DistanceGPS;
            }

            public String getCarCode() {
                return CarCode;
            }

            public void setCarCode(String CarCode) {
                this.CarCode = CarCode;
            }

            public String getPlateCode() {
                return PlateCode;
            }

            public void setPlateCode(String PlateCode) {
                this.PlateCode = PlateCode;
            }

            public String getSummary_Mileage() {
                return Summary_Mileage;
            }

            public void setSummary_Mileage(String Summary_Mileage) {
                this.Summary_Mileage = Summary_Mileage;
            }

            public List<DetailBean> getDetail() {
                return Detail;
            }

            public void setDetail(List<DetailBean> Detail) {
                this.Detail = Detail;
            }

            public static class DetailBean {
                private String DistanceGPS;
                private String StartTime;

                public String getDistanceGPS() {
                    return DistanceGPS;
                }

                public void setDistanceGPS(String DistanceGPS) {
                    this.DistanceGPS = DistanceGPS;
                }

                public String getStartTime() {
                    return StartTime;
                }

                public void setStartTime(String StartTime) {
                    this.StartTime = StartTime;
                }
            }
        }
    }
}
