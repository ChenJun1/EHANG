package com.cvnavi.logistics.i51ehang.app.bean.response;

/**
 * Created by ${ChenJ} on 2016/8/18.
 */
public class ResultBase<T> {
    public boolean Success;
    public Object ErrorText;
    public Object MsgType;
    public int RowCount;
    public T DataValue;
}
