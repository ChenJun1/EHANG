package com.cvnavi.logistics.i51ehang.app.callback.manager;

import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.location.LocationChooseCarCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnnyYuan on 2016/7/7.
 */
public class LocationChooseCarCallBackManager {

//    public static int MonitorLoadCarCode = 1;
//    public static int HistorLoadCarCode = 2;

    private static LocationChooseCarCallBackManager ms_LocationChooseCarCallBackManager = new LocationChooseCarCallBackManager();

    private List<LocationChooseCarCallback> callbacks = new ArrayList<>();

    private LocationChooseCarCallBackManager() {
    }

    public static synchronized LocationChooseCarCallBackManager newStance() {
        return ms_LocationChooseCarCallBackManager;
    }

    public synchronized void add(LocationChooseCarCallback callback) {
        callbacks.add(callback);
    }

    public synchronized void remove(LocationChooseCarCallback callback) {
        callbacks.remove(callback);
    }

//    public void fire(mCarInfo info, int type) {
//        for (LocationChooseCarCallback callback : callbacks) {
//            if (type == MonitorLoadCarCode) {
//                try {
//                    callback.OnMonitorLoadCarCode(info);
//                } catch (Exception ex) {
//                    continue;
//                }
//            } else if (type == HistorLoadCarCode) {
//                try {
//                    callback.OnHistorLoadCarCode(info);
//                } catch (Exception ex) {
//                    continue;
//                }
//            }
//        }
//    }

    public void fire(mCarInfo carInfo) {
        for (LocationChooseCarCallback callback : callbacks) {
            try {
                callback.OnMonitorLoadCarCode(carInfo);
                callback.OnHistorLoadCarCode(carInfo);
            } catch (Exception ex) {
                continue;
            }
        }
    }

}
