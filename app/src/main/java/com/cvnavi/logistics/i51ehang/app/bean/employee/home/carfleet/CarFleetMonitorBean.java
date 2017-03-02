package com.cvnavi.logistics.i51ehang.app.bean.employee.home.carfleet;

import java.util.List;

/**
 * Created by george on 2016/12/29.
 *
 *
 */

public class CarFleetMonitorBean {


    /**
     * DataValue : [{"Org_Key":"3ee72bd3-3000-4add-8f7f-06ac25969410","Org_Name":"总公司","Lat":null,"Long":null},{"Org_Key":"04b8db39-dcb6-4c77-89f9-162f3085ef77","Org_Name":"天津分公司","Lat":"39.082119","Long":"117.23151"},{"Org_Key":"cba24fbb-6141-4a27-b8f2-365cf620f53e","Org_Name":"上海分公司","Lat":"31.23533","Long":"121.477551"},{"Org_Key":"dfae6015-db4a-449d-9187-908adaa865ce","Org_Name":"成都分公司","Lat":"30.661057","Long":"104.08517"},{"Org_Key":"b12ecfef-c0cb-47c9-9bb6-9fec94894b3b","Org_Name":"重庆分公司","Lat":"29.564714","Long":"106.544433"},{"Org_Key":"e7069d8c-86ff-4683-ac1f-ce0cfeb76fe0","Org_Name":"北京分公司","Lat":"39.905194","Long":"116.381847"},{"Org_Key":"b55e2c72-7e41-4347-a34b-d07c5b521b7a","Org_Name":"武汉分公司","Lat":"30.633841","Long":"114.070016"}]
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
         * Org_Key : 3ee72bd3-3000-4add-8f7f-06ac25969410
         * Org_Name : 总公司
         * Lat : null
         * Long : null
         */

        private String Org_Key;
        private String Org_Name;
        private String Lat;
        private String Long;

        public String getOrg_Key() {
            return Org_Key;
        }

        public void setOrg_Key(String Org_Key) {
            this.Org_Key = Org_Key;
        }

        public String getOrg_Name() {
            return Org_Name;
        }

        public void setOrg_Name(String Org_Name) {
            this.Org_Name = Org_Name;
        }

        public String getLat() {
            return Lat;
        }

        public void setLat(String Lat) {
            this.Lat = Lat;
        }

        public String getLong() {
            return Long;
        }

        public void setLong(String Long) {
            this.Long = Long;
        }
    }
}
