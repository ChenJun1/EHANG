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

public class SendCarSelectLineResponse {

    private boolean Success;
    private Object ErrorText;
    private Object MsgType;
    private int RowCount;
    private Object Status;
    private Object CompanyList;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public Object getErrorText() {
        return ErrorText;
    }

    public void setErrorText(Object errorText) {
        ErrorText = errorText;
    }

    public Object getMsgType() {
        return MsgType;
    }

    public void setMsgType(Object msgType) {
        MsgType = msgType;
    }

    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int rowCount) {
        RowCount = rowCount;
    }

    public Object getStatus() {
        return Status;
    }

    public void setStatus(Object status) {
        Status = status;
    }

    public Object getCompanyList() {
        return CompanyList;
    }

    public void setCompanyList(Object companyList) {
        CompanyList = companyList;
    }

    public List<DataValueBean> getDataValue() {
        return DataValue;
    }

    public void setDataValue(List<DataValueBean> dataValue) {
        DataValue = dataValue;
    }

    private List<DataValueBean> DataValue;

    public static class DataValueBean {
        private String Line_Type;
        private String Line_Name;
        private String Line_Oid;
        private String Shuttle_No;
        private String Shuttle_Oid;
        private String Line_Type_Oid;

        private boolean isSelect = false;


        public DataValueBean(boolean isSelect, String line_Name, String line_Oid) {
            this.isSelect = isSelect;
            Line_Name = line_Name;
            Line_Oid = line_Oid;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getLine_Type() {
            return Line_Type;
        }

        public void setLine_Type(String line_Type) {
            Line_Type = line_Type;
        }

        public String getLine_Name() {
            return Line_Name;
        }

        public void setLine_Name(String line_Name) {
            Line_Name = line_Name;
        }

        public String getLine_Oid() {
            return Line_Oid;
        }

        public void setLine_Oid(String line_Oid) {
            Line_Oid = line_Oid;
        }

        public String getShuttle_No() {
            return Shuttle_No;
        }

        public void setShuttle_No(String shuttle_No) {
            Shuttle_No = shuttle_No;
        }

        public String getShuttle_Oid() {
            return Shuttle_Oid;
        }

        public void setShuttle_Oid(String shuttle_Oid) {
            Shuttle_Oid = shuttle_Oid;
        }

        public String getLine_Type_Oid() {
            return Line_Type_Oid;
        }

        public void setLine_Type_Oid(String line_Type_Oid) {
            Line_Type_Oid = line_Type_Oid;
        }
    }


}
