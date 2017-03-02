package com.cvnavi.logistics.i51ehang.app.callback.manager;

import com.cvnavi.logistics.i51ehang.app.bean.model.mCarSchedulingDriver;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.transportation.DriverChoiceCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnnyYuan on 2016/7/7.
 */
public class DriverChoiceCallBackManager {

    private static DriverChoiceCallBackManager ms_DriverChoiceCallBackManager = new DriverChoiceCallBackManager();

    private List<DriverChoiceCallback> callbacks = new ArrayList<>();

    private DriverChoiceCallBackManager() {
    }

    public static synchronized DriverChoiceCallBackManager newInstance() {
        return ms_DriverChoiceCallBackManager;
    }

    public synchronized void add(DriverChoiceCallback callback) {
        callbacks.add(callback);
    }

    public synchronized void remove(DriverChoiceCallback callback) {
        callbacks.remove(callback);
    }

    public void fire(mCarSchedulingDriver driverInfo) {
        for (DriverChoiceCallback callback : callbacks) {
            try {
                callback.onDriverChoice(driverInfo);
            } catch (Exception ex) {
                continue;
            }
        }
    }

}
