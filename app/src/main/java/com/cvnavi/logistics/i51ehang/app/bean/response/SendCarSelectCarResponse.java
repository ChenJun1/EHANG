package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.util.List;

/************************************************************************************
 * 唯一标识：
 * 创建人：  george
 * 电子邮箱：604639402@qq.com
 * 手机号：18301969307
 * 创建时间：2017/1/6
 * 版本：
 * 描述：
 * <p>
 * =====================================================================
 * 修改标记
 * 修改时间：
 * 修改人：
 * 电子邮箱：
 * 手机号：
 * 版本：
 * 描述：
 ************************************************************************************/

public class SendCarSelectCarResponse {

    /**
     * DataValue : [{"CarCode_Key":"fd59333a-b1bf-466e-b739-296475b75346","Company_Oid":"","Direction":null,"SIM_Oid":"","Device_Oid":"","Device_Type_Oid":"","CarCode":"佳DP1586","Add_Status_Oid":"","Device_Status_Oid":"","Use_Org":"","Org_Name":"总公司","Device_Type":"","TSP_CarCode_Key":"","CHS_Address":"","CarStatus_Oid":"4","BLng":"","BLat":"","Driver":"","Line_Oid":"4ac9d729-f542-4960-a84d-15c3f6a9cb96","Line_Name":"上海分公司-武汉分公司-成都分公司","Driver_Tel":""},{"CarCode_Key":"4b3890b8-02ac-4cd6-8c23-5d49853baec4","Company_Oid":"","Direction":null,"SIM_Oid":"","Device_Oid":"","Device_Type_Oid":"","CarCode":"佳DP1587","Add_Status_Oid":"","Device_Status_Oid":"","Use_Org":"","Org_Name":"总公司","Device_Type":"","TSP_CarCode_Key":"","CHS_Address":"","CarStatus_Oid":"4","BLng":"","BLat":"","Driver":"","Line_Oid":"4ac9d729-f542-4960-a84d-15c3f6a9cb96","Line_Name":"上海分公司-武汉分公司-成都分公司","Driver_Tel":""}]
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
         * CarCode_Key : fd59333a-b1bf-466e-b739-296475b75346
         * Company_Oid :
         * Direction : null
         * SIM_Oid :
         * Device_Oid :
         * Device_Type_Oid :
         * CarCode : 佳DP1586
         * Add_Status_Oid :
         * Device_Status_Oid :
         * Use_Org :
         * Org_Name : 总公司
         * Device_Type :
         * TSP_CarCode_Key :
         * CHS_Address :
         * CarStatus_Oid : 4
         * BLng :
         * BLat :
         * Driver :
         * Line_Oid : 4ac9d729-f542-4960-a84d-15c3f6a9cb96
         * Line_Name : 上海分公司-武汉分公司-成都分公司
         * Driver_Tel :
         */

        private boolean isSelect = false;
        private String CarCode_Key;
        private String Company_Oid;
        private Object Direction;
        private String SIM_Oid;
        private String Device_Oid;
        private String Device_Type_Oid;
        private String CarCode;
        private String Add_Status_Oid;
        private String Device_Status_Oid;
        private String Use_Org;
        private String Org_Name;
        private String Device_Type;
        private String TSP_CarCode_Key;
        private String CHS_Address;
        private String CarStatus_Oid;
        private String BLng;
        private String BLat;
        private String Driver;
        private String Line_Oid;
        private String Line_Name;
        private String Driver_Tel;

        public DataValueBean(boolean isSelect, String carCode_Key, String carCode) {
            this.isSelect = isSelect;
            CarCode_Key = carCode_Key;
            CarCode = carCode;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getCarCode_Key() {
            return CarCode_Key;
        }

        public void setCarCode_Key(String CarCode_Key) {
            this.CarCode_Key = CarCode_Key;
        }

        public String getCompany_Oid() {
            return Company_Oid;
        }

        public void setCompany_Oid(String Company_Oid) {
            this.Company_Oid = Company_Oid;
        }

        public Object getDirection() {
            return Direction;
        }

        public void setDirection(Object Direction) {
            this.Direction = Direction;
        }

        public String getSIM_Oid() {
            return SIM_Oid;
        }

        public void setSIM_Oid(String SIM_Oid) {
            this.SIM_Oid = SIM_Oid;
        }

        public String getDevice_Oid() {
            return Device_Oid;
        }

        public void setDevice_Oid(String Device_Oid) {
            this.Device_Oid = Device_Oid;
        }

        public String getDevice_Type_Oid() {
            return Device_Type_Oid;
        }

        public void setDevice_Type_Oid(String Device_Type_Oid) {
            this.Device_Type_Oid = Device_Type_Oid;
        }

        public String getCarCode() {
            return CarCode;
        }

        public void setCarCode(String CarCode) {
            this.CarCode = CarCode;
        }

        public String getAdd_Status_Oid() {
            return Add_Status_Oid;
        }

        public void setAdd_Status_Oid(String Add_Status_Oid) {
            this.Add_Status_Oid = Add_Status_Oid;
        }

        public String getDevice_Status_Oid() {
            return Device_Status_Oid;
        }

        public void setDevice_Status_Oid(String Device_Status_Oid) {
            this.Device_Status_Oid = Device_Status_Oid;
        }

        public String getUse_Org() {
            return Use_Org;
        }

        public void setUse_Org(String Use_Org) {
            this.Use_Org = Use_Org;
        }

        public String getOrg_Name() {
            return Org_Name;
        }

        public void setOrg_Name(String Org_Name) {
            this.Org_Name = Org_Name;
        }

        public String getDevice_Type() {
            return Device_Type;
        }

        public void setDevice_Type(String Device_Type) {
            this.Device_Type = Device_Type;
        }

        public String getTSP_CarCode_Key() {
            return TSP_CarCode_Key;
        }

        public void setTSP_CarCode_Key(String TSP_CarCode_Key) {
            this.TSP_CarCode_Key = TSP_CarCode_Key;
        }

        public String getCHS_Address() {
            return CHS_Address;
        }

        public void setCHS_Address(String CHS_Address) {
            this.CHS_Address = CHS_Address;
        }

        public String getCarStatus_Oid() {
            return CarStatus_Oid;
        }

        public void setCarStatus_Oid(String CarStatus_Oid) {
            this.CarStatus_Oid = CarStatus_Oid;
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

        public String getDriver() {
            return Driver;
        }

        public void setDriver(String Driver) {
            this.Driver = Driver;
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

        public String getDriver_Tel() {
            return Driver_Tel;
        }

        public void setDriver_Tel(String Driver_Tel) {
            this.Driver_Tel = Driver_Tel;
        }
    }
}
