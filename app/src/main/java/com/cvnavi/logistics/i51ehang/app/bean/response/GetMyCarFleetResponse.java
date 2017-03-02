package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/9.
 */
public class GetMyCarFleetResponse extends DataResponseBase {


    /**
     * MoveCarSum : 150
     * FreeCarSum : 20
     * TotalCarSum : 170
     * mCarInfoList : [{"id":null,"parentId":"2","CarCode":"沪A00001","CarCode_Key":"EE5AF185-E5F5-4F45-BEC0-D5A96B877A07","CarCode_Type_Oid":"AD","CarCode_Type":"中型厢式货车","Icon_Type_Oid":"","TemperatureCount":"1","SIM_Oid":"15618407584","Org_Code":"101","Device_Status_Oid":"0","Org_Name":"赛托斯总部","Content":"","Line_Oid":"b5002633-bec1-4701-b0b5-fcf17b03df4b","Driver":"高劲松","Driver_Tel":"15221661039","IsOnline_Status":"0","BLng":"121.4763438","BLat":"30.9758971","GLng":"21.4698351","GLat":"30.9698915","mCarInfoList":null},{"id":null,"parentId":"2","CarCode":"沪A00002","CarCode_Key":"EE5AF185-E5F5-4F45-BEC0-D5A96B877A08","CarCode_Type_Oid":"AD","CarCode_Type":"中型厢式货车","Icon_Type_Oid":"","TemperatureCount":"1","SIM_Oid":"15618407584","Org_Code":"101","Device_Status_Oid":"0","Org_Name":"赛托斯总部","Content":"","Line_Oid":"b5002633-bec1-4701-b0b5-fcf17b03df4b","Driver":"高劲松","Driver_Tel":"15221661039","IsOnline_Status":"0","BLng":"120.4763438","BLat":"32.9758971","GLng":"120.4763438","GLat":"32.9758971","mCarInfoList":null}]
     */

    private DataValueBean DataValue;

    public DataValueBean getDataValue() {
        return DataValue;
    }

    public void setDataValue(DataValueBean DataValue) {
        this.DataValue = DataValue;
    }

    public static class DataValueBean {
        private String MoveCarSum;
        private String FreeCarSum;
        private String TotalCarSum;
        private String WillArriveSum;
        private String AlreadyplanSum;

        public String getWillArriveSum() {
            return WillArriveSum;
        }

        public void setWillArriveSum(String willArriveSum) {
            WillArriveSum = willArriveSum;
        }

        public String getAlreadyplanSum() {
            return AlreadyplanSum;
        }

        public void setAlreadyplanSum(String alreadyplanSum) {
            AlreadyplanSum = alreadyplanSum;
        }

        /**
         * id : null
         * parentId : 2
         * CarCode : 沪A00001
         * CarCode_Key : EE5AF185-E5F5-4F45-BEC0-D5A96B877A07
         * CarCode_Type_Oid : AD
         * CarCode_Type : 中型厢式货车
         * Icon_Type_Oid :
         * TemperatureCount : 1
         * SIM_Oid : 15618407584
         * Org_Code : 101
         * Device_Status_Oid : 0
         * Org_Name : 赛托斯总部
         * Content :
         * Line_Oid : b5002633-bec1-4701-b0b5-fcf17b03df4b
         * Driver : 高劲松
         * Driver_Tel : 15221661039
         * IsOnline_Status : 0
         * BLng : 121.4763438
         * BLat : 30.9758971
         * GLng : 21.4698351
         * GLat : 30.9698915
         * mCarInfoList : null
         */

        private List<MCarInfoListBean> mCarInfoList;

        public String getMoveCarSum() {
            return MoveCarSum;
        }

        public void setMoveCarSum(String MoveCarSum) {
            this.MoveCarSum = MoveCarSum;
        }

        public String getFreeCarSum() {
            return FreeCarSum;
        }

        public void setFreeCarSum(String FreeCarSum) {
            this.FreeCarSum = FreeCarSum;
        }

        public String getTotalCarSum() {
            return TotalCarSum;
        }

        public void setTotalCarSum(String TotalCarSum) {
            this.TotalCarSum = TotalCarSum;
        }

        public List<MCarInfoListBean> getMCarInfoList() {
            return mCarInfoList;
        }

        public void setMCarInfoList(List<MCarInfoListBean> mCarInfoList) {
            this.mCarInfoList = mCarInfoList;
        }

        public static class MCarInfoListBean implements Serializable {
            private Object id;
            private String parentId;
            private String CarCode;
            private String CarCode_Key;
            private String CarCode_Type_Oid;
            private String CarCode_Type;
            private String Icon_Type_Oid;
            private String TemperatureCount;
            private String SIM_Oid;
            private String Org_Code;
            private String Device_Status_Oid;
            private String Org_Name;
            private String Content;
            private String Line_Oid;
            private String Driver;
            private String Driver_Tel;
            private String IsOnline_Status;
            private String BLng;
            private String BLat;
            private String GLng;
            private String GLat;
            private Object mCarInfoList;
            private String CarStatus;//CarStatus	车辆当前状态 0=行驶中 1=停止 2=信号中断
            private String Speed;//当前车速
            private String CHS_Address;//车辆当前地址信息
            private String TSP_CarCode_Key;
            private String CarStatus_Oid;
            private String Direction;

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

            public String getTSP_CarCode_Key() {
                return TSP_CarCode_Key;
            }

            public void setTSP_CarCode_Key(String TSP_CarCode_Key) {
                this.TSP_CarCode_Key = TSP_CarCode_Key;
            }

            public String getSpeed() {
                return Speed;
            }

            public void setSpeed(String speed) {
                Speed = speed;
            }

            public String getCHS_Address() {
                return CHS_Address;
            }

            public void setCHS_Address(String GPS_Address) {
                this.CHS_Address = CHS_Address;
            }

            public String getCarStatus() {
                return CarStatus;
            }

            public void setCarStatus(String carStatus) {
                CarStatus = carStatus;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getCarCode() {
                return CarCode;
            }

            public void setCarCode(String CarCode) {
                this.CarCode = CarCode;
            }

            public String getCarCode_Key() {
                return CarCode_Key;
            }

            public void setCarCode_Key(String CarCode_Key) {
                this.CarCode_Key = CarCode_Key;
            }

            public String getCarCode_Type_Oid() {
                return CarCode_Type_Oid;
            }

            public void setCarCode_Type_Oid(String CarCode_Type_Oid) {
                this.CarCode_Type_Oid = CarCode_Type_Oid;
            }

            public String getCarCode_Type() {
                return CarCode_Type;
            }

            public void setCarCode_Type(String CarCode_Type) {
                this.CarCode_Type = CarCode_Type;
            }

            public String getIcon_Type_Oid() {
                return Icon_Type_Oid;
            }

            public void setIcon_Type_Oid(String Icon_Type_Oid) {
                this.Icon_Type_Oid = Icon_Type_Oid;
            }

            public String getTemperatureCount() {
                return TemperatureCount;
            }

            public void setTemperatureCount(String TemperatureCount) {
                this.TemperatureCount = TemperatureCount;
            }

            public String getSIM_Oid() {
                return SIM_Oid;
            }

            public void setSIM_Oid(String SIM_Oid) {
                this.SIM_Oid = SIM_Oid;
            }

            public String getOrg_Code() {
                return Org_Code;
            }

            public void setOrg_Code(String Org_Code) {
                this.Org_Code = Org_Code;
            }

            public String getDevice_Status_Oid() {
                return Device_Status_Oid;
            }

            public void setDevice_Status_Oid(String Device_Status_Oid) {
                this.Device_Status_Oid = Device_Status_Oid;
            }

            public String getOrg_Name() {
                return Org_Name;
            }

            public void setOrg_Name(String Org_Name) {
                this.Org_Name = Org_Name;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public String getLine_Oid() {
                return Line_Oid;
            }

            public void setLine_Oid(String Line_Oid) {
                this.Line_Oid = Line_Oid;
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

            public String getIsOnline_Status() {
                return IsOnline_Status;
            }

            public void setIsOnline_Status(String IsOnline_Status) {
                this.IsOnline_Status = IsOnline_Status;
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

            public Object getMCarInfoList() {
                return mCarInfoList;
            }

            public void setMCarInfoList(Object mCarInfoList) {
                this.mCarInfoList = mCarInfoList;
            }
        }
    }
}
