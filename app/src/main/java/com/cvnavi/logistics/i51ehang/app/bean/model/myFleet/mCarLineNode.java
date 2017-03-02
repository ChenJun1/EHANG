package com.cvnavi.logistics.i51ehang.app.bean.model.myFleet;

import java.util.List;

/**
 * 版权所有势航网络
 * Created by Chuzy on 2016/8/8.
 */
public class mCarLineNode {
    /// <summary>
    /// 车辆Key
    /// </summary>
    public String CarCode_Key ;//
    /// <summary>
    /// 清单号
    /// </summary>
    public String  Letter_Oid	;//
    /// <summary>
    /// 运输状态:运输中/已到达
    /// </summary>
    public String  Letter_Status	;//
    /// <summary>
    /// 线路各节点信息
    /// </summary>
    public List<mCarLine_Nodes> Line_Nodes ;//
}
