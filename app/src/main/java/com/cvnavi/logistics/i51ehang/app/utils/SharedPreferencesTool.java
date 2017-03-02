package com.cvnavi.logistics.i51ehang.app.utils;

import android.content.Context;

import com.cvnavi.logistics.i51ehang.app.MyApplication;

/**
 * 版权所有势航网络
 * Created by ${chuzy} on 2016/7/5.
 */
public class SharedPreferencesTool {

    // preferences名称
    private final static String PREFERENCE_NAME = "com_i51eyun";
    public static final String FRIST_LOGIN = "FRIST_LOGIN";//第一次登陆
    public static final String LOGIN_User_Key = "LOGIN_User_Key";
    public static final String LOGIN_UserType_Oid = "LOGIN_UserType_Oid";
    public static final String LOGIN_Token = "LOGIN_Token";
    public static final String LOGIN_Company_Oid = "LOGIN_Company_Oid";
    public static final String LOGIN_UER_TEL = "LOGIN_UER_TEL";
    public static final String LOGIN_UER_NAME = "LOGIN_UER_NAME";
    public static final String LOGIN_UER_VERIFY_CODE = "LOGIN_UER_VERIFY_CODE";
    public static final String LOGIN_UER_UUID = "LOGIN_UER_UUID";
    public static final String QUERY_LENGTH = "QUERY_LENGTH";


    /**
     * * 保存boolean
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean(key, value).commit();
    }

    /**
     * 获取boolean
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getBoolean(key, defValue);
    }

    /**
     * 保存int
     *
     * @param key
     * @param value
     */
    public static void putInt(String key, int value) {
        MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putInt(key, value).commit();
    }

    /**
     * 获取int
     *
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(String key, int defValue) {
        return MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getInt(key, defValue);
    }

    /**
     * 保存string
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putString(key, value).commit();
    }

    /**
     * 获取string
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        return MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getString(key, defValue);
    }

    /**
     * 保存long
     *
     * @param key
     * @param value
     */
    public static void putLong(String key, long value) {
        MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putLong(key, value).commit();
    }

    /**
     * 获取long
     *
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(String key, long defValue) {
        return MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getLong(key, defValue);
    }

    /**
     * 保存float
     *
     * @param key
     * @param value
     */
    public static void putFloat(String key, float value) {
        MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .edit().putFloat(key, value).commit();
    }

    /**
     * 获取float
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(String key, float defValue) {
        return MyApplication.getInstance()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                .getFloat(key, defValue);
    }

}
