package com.cvnavi.logistics.i51ehang.app.bean.employee.home.statics;

import java.util.List;

/**
 * Created by george on 2016/10/12.
 */

public class MileStaticsResponse {


    /**
     * DataValue : [{"Summary_Date":"","Summary_Mileage":"","CarCode":"","DistanceGPS":"843.01","PlateCode":"浙AHS169","Detail":[{"DistanceGPS":"85.44","StartTime":"2016-10-01"},{"DistanceGPS":"86.48","StartTime":"2016-10-02"},{"DistanceGPS":"98.49","StartTime":"2016-10-03"},{"DistanceGPS":"66.04","StartTime":"2016-10-04"},{"DistanceGPS":"80.84","StartTime":"2016-10-05"},{"DistanceGPS":"78.59","StartTime":"2016-10-06"},{"DistanceGPS":"112.87","StartTime":"2016-10-07"},{"DistanceGPS":"114.53","StartTime":"2016-10-08"},{"DistanceGPS":"119.72","StartTime":"2016-10-10"}]}]
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
     * Summary_Date :
     * Summary_Mileage :
     * CarCode :
     * DistanceGPS : 843.01
     * PlateCode : 浙AHS169
     * Detail : [{"DistanceGPS":"85.44","StartTime":"2016-10-01"},{"DistanceGPS":"86.48","StartTime":"2016-10-02"},{"DistanceGPS":"98.49","StartTime":"2016-10-03"},{"DistanceGPS":"66.04","StartTime":"2016-10-04"},{"DistanceGPS":"80.84","StartTime":"2016-10-05"},{"DistanceGPS":"78.59","StartTime":"2016-10-06"},{"DistanceGPS":"112.87","StartTime":"2016-10-07"},{"DistanceGPS":"114.53","StartTime":"2016-10-08"},{"DistanceGPS":"119.72","StartTime":"2016-10-10"}]
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

    public static class DataValueBean {
        private String Summary_Date;
        private String Summary_Mileage;
        private String CarCode;
        private String DistanceGPS;
        private String PlateCode;
        /**
         * DistanceGPS : 85.44
         * StartTime : 2016-10-01
         */

        private List<DetailBean> Detail;

        public String getSummary_Date() {
            return Summary_Date;
        }

        public void setSummary_Date(String Summary_Date) {
            this.Summary_Date = Summary_Date;
        }

        public String getSummary_Mileage() {
            return Summary_Mileage;
        }

        public void setSummary_Mileage(String Summary_Mileage) {
            this.Summary_Mileage = Summary_Mileage;
        }

        public String getCarCode() {
            return CarCode;
        }

        public void setCarCode(String CarCode) {
            this.CarCode = CarCode;
        }

        public String getDistanceGPS() {
            return DistanceGPS;
        }

        public void setDistanceGPS(String DistanceGPS) {
            this.DistanceGPS = DistanceGPS;
        }

        public String getPlateCode() {
            return PlateCode;
        }

        public void setPlateCode(String PlateCode) {
            this.PlateCode = PlateCode;
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
