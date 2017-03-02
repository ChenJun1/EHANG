package com.cvnavi.logistics.i51ehang.app.bean.response;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/5.
 */
public class QuerySimpleCodeLengthResponse {


    /**
     * Success : true
     * IsRepeatError : false
     * DataValue : 6
     * RowCount : 0
     * IsZip : false
     * Status : 成功
     */

    private boolean Success;
    private boolean IsRepeatError;
    private String DataValue;
    private int RowCount;
    private boolean IsZip;
    private String Status;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public boolean isIsRepeatError() {
        return IsRepeatError;
    }

    public void setIsRepeatError(boolean IsRepeatError) {
        this.IsRepeatError = IsRepeatError;
    }

    public String getDataValue() {
        return DataValue;
    }

    public void setDataValue(String DataValue) {
        this.DataValue = DataValue;
    }

    public int getRowCount() {
        return RowCount;
    }

    public void setRowCount(int RowCount) {
        this.RowCount = RowCount;
    }

    public boolean isIsZip() {
        return IsZip;
    }

    public void setIsZip(boolean IsZip) {
        this.IsZip = IsZip;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}
