package com.cvnavi.logistics.i51ehang.app.bean.model.MyTask;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/21.
 */
public class Model_LetterTrace_Node implements Serializable{
    public String letter_oid;         //清单号 (string)
    public String node_id;         //线路节点序号 1,2,3,…… (Int)
    public String node_key;         //线路节点Key
    public String Leave;         //离开状态 1离开, 0未离开 (Int)
    public String Leave_DateTime;         //离开时间(string)
    public String Arrive;         //到达状态 1已到达, 0未到达(Int)
    public String Arrive_DateTime;         //到达时间(string)
    public String Org_Name;         //机构名(string)
    public String City;         //城市名(string)
    public String ArriveImgNo;         //到达图片数量 (Int)
}
