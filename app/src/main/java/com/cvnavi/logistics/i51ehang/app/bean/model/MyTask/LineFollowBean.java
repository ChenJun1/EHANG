package com.cvnavi.logistics.i51ehang.app.bean.model.MyTask;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class LineFollowBean {
    public String Letter_Oid;   //清单号
    public String Letter_Status;   //运输状态:运输中/已到达
    public List<Model_LetterTrace_Node> Line_Nodes;   //线路各节点信息 List<Model_LetterTrace_Node>
}
