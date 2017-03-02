package com.cvnavi.logistics.i51ehang.app.bean.response;

import java.util.List;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/12.
 */
public class GetMileageResponse {


    /**
     * TotalMileage : 3423.345
     * CarCode : 沪AUD063
     * listMileage : [{"Summary_Date":"06-09","Summary_Mileage":"50.779","CarCode":"沪AUD063"},{"Summary_Date":"06-10","Summary_Mileage":"325.270","CarCode":"沪AUD063"}]
     */

    private DataValueBean DataValue;
    /**
     * DataValue : {"TotalMileage":"3423.345","CarCode":"沪AUD063","listMileage":[{"Summary_Date":"06-09","Summary_Mileage":"50.779","CarCode":"沪AUD063"},{"Summary_Date":"06-10","Summary_Mileage":"325.270","CarCode":"沪AUD063"}]}
     * Success : true
     */

    private boolean Success;

    public DataValueBean getDataValue() {
        return DataValue;
    }

    public void setDataValue(DataValueBean DataValue) {
        this.DataValue = DataValue;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public static class DataValueBean {
        private String TotalMileage;
        private String CarCode;
        /**
         * Summary_Date : 06-09
         * Summary_Mileage : 50.779
         * CarCode : 沪AUD063
         */

        private List<ListMileageBean> listMileage;

        public String getTotalMileage() {
            return TotalMileage;
        }

        public void setTotalMileage(String TotalMileage) {
            this.TotalMileage = TotalMileage;
        }

        public String getCarCode() {
            return CarCode;
        }

        public void setCarCode(String CarCode) {
            this.CarCode = CarCode;
        }

        public List<ListMileageBean> getListMileage() {
            return listMileage;
        }

        public void setListMileage(List<ListMileageBean> listMileage) {
            this.listMileage = listMileage;
        }

        public static class ListMileageBean {
            private String Summary_Date;
            private String Summary_Mileage;
            private String CarCode;

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
        }
    }
}
