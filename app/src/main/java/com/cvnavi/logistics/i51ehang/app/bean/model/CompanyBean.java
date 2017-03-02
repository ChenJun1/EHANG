package com.cvnavi.logistics.i51ehang.app.bean.model;

/**
 * Created by george on 2016/11/14.
 * 公司
 */

public class CompanyBean {

    private String Company_Oid;//公司Id
    private String Company_Name;//公司名

    public CompanyBean(String company_Oid, String company_Name) {
        Company_Oid = company_Oid;
        Company_Name = company_Name;
    }

    public String getCompany_Oid() {
        return Company_Oid;
    }

    public void setCompany_Oid(String company_Oid) {
        Company_Oid = company_Oid;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }
}
