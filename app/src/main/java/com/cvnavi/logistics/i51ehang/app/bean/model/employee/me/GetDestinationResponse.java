package com.cvnavi.logistics.i51ehang.app.bean.model.employee.me;

import java.io.Serializable;
import java.util.List;

/**
 * Created by george on 2016/11/3.
 *
 *  常用地址数据
 */

public class GetDestinationResponse {


    /**
     * DataValue : [{"Serial_Oid":"00b53f62-57b4-4a16-85f8-5f8fb9ef0cd2","Company_Oid":"210106","Destination":"上海闵行","BLng":120.35631427186,"BLat":30.3730006559886,"Operate_Name":"总公司","Operate_Org":"101         ","Operate_DateTime":"2016-11-03 15:30:59"}]
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
     * Serial_Oid : 00b53f62-57b4-4a16-85f8-5f8fb9ef0cd2
     * Company_Oid : 210106
     * Destination : 上海闵行
     * BLng : 120.35631427186
     * BLat : 30.3730006559886
     * Operate_Name : 总公司
     * Operate_Org : 101
     * Operate_DateTime : 2016-11-03 15:30:59
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

    public static class DataValueBean implements Serializable{
        private String Serial_Oid;
        private String Company_Oid;
        private String Destination;
        private String BLng;
        private String BLat;
        private String Operate_Name;
        private String Operate_Org;
        private String Operate_DateTime;

        public String getSerial_Oid() {
            return Serial_Oid;
        }

        public void setSerial_Oid(String Serial_Oid) {
            this.Serial_Oid = Serial_Oid;
        }

        public String getCompany_Oid() {
            return Company_Oid;
        }

        public void setCompany_Oid(String Company_Oid) {
            this.Company_Oid = Company_Oid;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String Destination) {
            this.Destination = Destination;
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
    }
}
