package com.cvnavi.logistics.i51ehang.app.activity.employee.myutils.printutils;


import com.cvnavi.logistics.i51ehang.app.Constants;
import com.orhanobut.logger.Logger;

/**
 * Created by george on 2016/10/24.
 */

public class MLog {

    public static void json(String json) {
        if (Constants.IS_DEBUG) {
            Logger.json(json);
        }
    }


    public static void print(String object) {
        if (Constants.IS_DEBUG) {
            Logger.d(object);
        }
    }

    public static void setting() {

    }
}
