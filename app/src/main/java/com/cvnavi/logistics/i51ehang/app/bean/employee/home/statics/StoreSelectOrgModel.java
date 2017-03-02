package com.cvnavi.logistics.i51ehang.app.bean.employee.home.statics;

import java.util.List;

/**
 * Created by george on 2016/12/20.
 */

public class StoreSelectOrgModel {


    /**
     * DataValue : [{"Org_Code":"101","Org_Name":"赛托斯总部","Org_Key":"57954f45-6023-48a6-abf8-790716273612"},{"Org_Code":"101001","Org_Name":"测试","Org_Key":"bd4ecd5e-22e5-4df7-9a5b-ed55f1d38542"},{"Org_Code":"101002","Org_Name":"赛托斯分公司1","Org_Key":"01676eec-68a0-4099-9f22-0bc6dc3e4d84"},{"Org_Code":"101003","Org_Name":"赛托斯分公司2","Org_Key":"fccce156-30f0-4cd1-8699-31efbbc21376"},{"Org_Code":"101004","Org_Name":"北京分公司","Org_Key":"16e2ab0e-0afc-4cb8-b15c-63ce93b97c05"},{"Org_Code":"101005","Org_Name":"太原分公司","Org_Key":"6c9c0281-a040-48cc-981c-1de2086fec2c"},{"Org_Code":"101006","Org_Name":"重庆分公司","Org_Key":"e37311c0-c756-46b2-9465-c17edc62beec"},{"Org_Code":"101007","Org_Name":"上海分公司","Org_Key":"c7b9ad83-96ef-4932-bffa-7fa97695b18a"},{"Org_Code":"101008","Org_Name":"广州分公司","Org_Key":"4f3d0c9a-b018-4543-a132-6ca94cc5b965"},{"Org_Code":"101009","Org_Name":"昆山","Org_Key":"af192065-bf72-4ccd-836d-ad2400fe708e"},{"Org_Code":"101010","Org_Name":"万位测试1","Org_Key":"e85f0679-6b8b-43f4-8c8b-4242c6d9bdf9"},{"Org_Code":"101011","Org_Name":"成都分公司","Org_Key":"1d2bfba0-9a53-4aad-b7e3-c8f4233109ee"},{"Org_Code":"4110","Org_Name":"11111111111111","Org_Key":"b30d104f-2fc5-432f-9fd6-f866b67dfc0c"},{"Org_Code":"4174","Org_Name":"122222","Org_Key":"3c09b980-c88f-43df-bd59-20b5d50433d5"},{"Org_Code":"4174","Org_Name":"asbc","Org_Key":"ef2faba3-825e-411f-9cd9-ed5e66cec38f"},{"Org_Code":"4174","Org_Name":"1223122","Org_Key":"04c93a11-45cf-47ab-b65e-ba0af15e3c5c"},{"Org_Code":"4174","Org_Name":"1212","Org_Key":"410f694b-2d11-4420-9994-dbebd18341bb"}]
     * Success : true
     * ErrorText :
     * MsgType : null
     * RowCount : 0
     * Status : null
     * CompanyList : null
     */

    private boolean Success;
    private String ErrorText;
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

    public String getErrorText() {
        return ErrorText;
    }

    public void setErrorText(String ErrorText) {
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
         * Org_Code : 101
         * Org_Name : 赛托斯总部
         * Org_Key : 57954f45-6023-48a6-abf8-790716273612
         */

        private String Org_Code;
        private String Org_Name;
        private String Org_Key;

        public String getOrg_Code() {
            return Org_Code;
        }

        public void setOrg_Code(String Org_Code) {
            this.Org_Code = Org_Code;
        }

        public String getOrg_Name() {
            return Org_Name;
        }

        public void setOrg_Name(String Org_Name) {
            this.Org_Name = Org_Name;
        }

        public String getOrg_Key() {
            return Org_Key;
        }

        public void setOrg_Key(String Org_Key) {
            this.Org_Key = Org_Key;
        }
    }
}
