package com.cvnavi.logistics.i51ehang.app.callback.manager;

import com.cvnavi.logistics.i51ehang.app.bean.model.mLineInfo;
import com.cvnavi.logistics.i51ehang.app.callback.driver.home.transportation.LineChoiceCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JohnnyYuan on 2016/7/7.
 */
public class LineChoiceCallBackManager {

    private static LineChoiceCallBackManager ms_LineChoiceCallBackManager = new LineChoiceCallBackManager();

    private List<LineChoiceCallback> callbacks = new ArrayList<>();

    private LineChoiceCallBackManager() {
    }

    public static synchronized LineChoiceCallBackManager newInstance() {
        return ms_LineChoiceCallBackManager;
    }

    public synchronized void add(LineChoiceCallback callback) {
        callbacks.add(callback);
    }

    public synchronized void remove(LineChoiceCallback callback) {
        callbacks.remove(callback);
    }

    public void fire(mLineInfo lineInfo) {
        for (LineChoiceCallback callback : callbacks) {
            try {
                callback.onLineChoice(lineInfo);
            } catch (Exception ex) {
                continue;
            }
        }
    }

}
