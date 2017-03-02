package com.cvnavi.logistics.i51ehang.app.bean.model.ReceivableAccount;

import java.io.Serializable;

/**
 * Created by fan on 2016/7/18.
 */
public class GatherFeeStatistics implements Serializable{
    public String Fee_Name;//	费用名称
    public String Fee;//	费用金额
    public String Gather_Fee;//	已收款金额
    public String Surplus_Gather_Fee;//	未收款金额
    public String Gather_Status;//	收款状态
}
