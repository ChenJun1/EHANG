package com.cvnavi.logistics.i51ehang.app.utils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by JohnnyYuan on 2016/7/5.
 */
public class GsonUtil {
    private static GsonUtil msGsonUtil = null;
    private Gson mGson = new Gson();

    private GsonUtil() {
    }

    public static synchronized GsonUtil newInstance() {
        return msGsonUtil = new GsonUtil();
    }

    public <T> T fromJson(JSONObject jsonObject, Class<T> classOfT) {
        try{
            return mGson.fromJson(jsonObject.toString(), classOfT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromJson(String jsonStr, Class<T> classOfT) {
        try{
         return mGson.fromJson(jsonStr, classOfT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Type typeOfT = new TypeToken<ArrayList<T>>() {
     * }.getType();
     *
     * @param jsonObject
     * @param typeOfT
     * @param <T>
     * @return
     */
    public <T> T fromJson(JSONObject jsonObject, Type typeOfT) {
        try{
            return mGson.fromJson(jsonObject.toString(), typeOfT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public JSONObject toJson(Object src) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(mGson.toJson(src));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public String toJsonStr(Object src) {
        return mGson.toJson(src);
    }

}
