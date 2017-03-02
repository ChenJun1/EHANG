package com.cvnavi.logistics.i51ehang.app.callback.manager;

import com.cvnavi.logistics.i51ehang.app.callback.driver.home.mytask.DriverChioceTimeCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public class DriverChioceTimeCallbackManager {
    private static DriverChioceTimeCallbackManager driverChioceTimeCallbackManager=new DriverChioceTimeCallbackManager();

    private List<DriverChioceTimeCallback> callbacks=new ArrayList<>();

    private DriverChioceTimeCallbackManager(){
    }

    public static  synchronized DriverChioceTimeCallbackManager newInstance(){
        return driverChioceTimeCallbackManager;
    }

    public synchronized void add(DriverChioceTimeCallback callback){callbacks.add(callback);}

    public synchronized void remove(DriverChioceTimeCallback callback){callbacks.remove(callback);}

    public void fire(String strTime,String endTime,int tag) {
        for (DriverChioceTimeCallback callback : callbacks) {
            try {
                callback.OnTimeChioce(strTime,endTime,tag);
            } catch (Exception ex) {
                continue;
            }
        }
    }
}
