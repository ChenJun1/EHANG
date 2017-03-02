package com.cvnavi.logistics.i51ehang.app.utils;

import com.cvnavi.logistics.i51ehang.app.bean.model.mCarInfo;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.List;


public class JsonUtils {

    public static final ObjectMapper JACKSON_MAPPER = new ObjectMapper().configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES,
            false);

    /**
     * 解析单个对象。
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseData(String jsonStr, Class<?> clazz) {
        try {
            return (T) JACKSON_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转换为JSON数据。
     *
     * @param object
     * @return
     */
    public static String toJsonData(Object object) {
        try {
            return JACKSON_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解析车辆列表查询
     *
     * @param jsonStr
     * @return
     */
    public static List<mCarInfo> parseCarInfo(String jsonStr) {
        List<mCarInfo> dataList = null;
        try {
            dataList = JACKSON_MAPPER.readValue(jsonStr.trim(), new TypeReference<List<mCarInfo>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }


}
