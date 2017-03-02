package com.cvnavi.logistics.i51ehang.app.bean.request;

import com.cvnavi.logistics.i51ehang.app.bean.model.MyTask.ImageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
public class CarConfirmRequest {
    public String Letter_Oid;        //清单号
    public String DFConfirmFlag;        //类型
    public String Node_Key;        //操作节点
    public String Arrive_Confirm_Type_Oid;        //确认类型0TMS, 1LPS, 3app
    public String Arrive_DateTime;        //到达时间
    public String Letter_Note;        //记事
    public List<ImageBean> IMGList;        //
}
