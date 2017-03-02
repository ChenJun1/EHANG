package com.cvnavi.logistics.i51ehang.app.bean.model.myFleet;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/8.
 */
public class mCarLine_Nodes {
    /// <summary>
    /// 清单号 (string)
    /// </summary>
    public String Letter_Oid	;//
    /// <summary>
    /// 线路节点序号 1,2,3,…… (Int)
    /// </summary>
    public String Node_id	;//
    /// <summary>
    /// 线路节点Key
    /// </summary>
    public String Node_Key	;//
    /// <summary>
    /// 离开状态 1离开, 0未离开 (Int)
    /// </summary>
    public String Leave	;//
    /// <summary>
    /// 离开时间(String)
    /// </summary>
    public String Leave_DateTime	;//
    /// <summary>
    /// 到达状态 1已到达, 0未到达(Int)
    /// </summary>
    public String Arrive	;//
    /// <summary>
    /// 到达时间(String)
    /// </summary>
    public String Arrive_DateTime	;//
    /// <summary>
    /// 机构名(String)
    /// </summary>
    public String Org_Name	;//
    /// <summary>
    /// 城市名(String)
    /// </summary>
    public String City	;//
    /// <summary>
    /// 到达图片数量 (Int)
    /// </summary>
    public String ArriveImgNo	;//
}
