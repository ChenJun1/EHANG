package com.cvnavi.logistics.i51ehang.app.bean.model;

import java.io.Serializable;
import java.util.List;

/**
 * 服务list
 * Created by JohnnyYuan on 2016/7/5.
 */
public class mMainService implements Serializable{
    /// <summary>
    /// 服务类型(1业务/2定位)
    /// </summary>
    public String ServiceType;

    /// <summary>
    /// 服务ID
    /// </summary>
    public String ServiceID;

    /// <summary>
    /// 服务名称
    /// </summary>
    public String ServiceName;

    // 二级子菜单
    public List<mMainService> ServiceList;


}
