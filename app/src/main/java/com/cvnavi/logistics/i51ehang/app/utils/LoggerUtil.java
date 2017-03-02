package com.cvnavi.logistics.i51ehang.app.utils;

import com.cvnavi.logistics.i51ehang.app.Constants;
import com.orhanobut.logger.Logger;

/**
 * Created by ${ChenJ} on 2016/10/26.
 */

public class LoggerUtil {
    private static Boolean mBoolean = Constants.IS_DEBUG;

    public static void d(String TAG, String msg) {
        if (mBoolean)
            Logger.d(TAG, msg);
    }

    public static void d(String msg) {
        if (mBoolean)
            Logger.d(msg);
    }

    public static void i(String msg) {
        if (mBoolean)
            Logger.i(msg);
    }

    public static void i(String TAG, String msg) {
        if (mBoolean)
            Logger.i(TAG, msg);
    }

    public static void e(String TAG, String msg) {
        if (mBoolean)
            Logger.e(TAG, msg);
    }

    public static void e(String msg) {
        if (mBoolean)
            Logger.e(msg);
    }

    public static void json(String TAG, String msg) {
        if (mBoolean)
            Logger.json(TAG, msg);
    }

    public static void json(String msg) {
        if (mBoolean)
            Logger.json(msg);
    }

}
