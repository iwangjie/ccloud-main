package com.ccloud.main.util;

import com.ccloud.main.pojo.enumeration.CloudUtilEnum;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author wangjie
 */
public class CloudUtil {
    private static ThreadLocal<Map<CloudUtilEnum, Object>> threadLocal = new ThreadLocal();
    private static Map<CloudUtilEnum, Object> data = new Hashtable<>();

    static {
        threadLocal.set(data);
    }

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    public static Object get(CloudUtilEnum key) {
        return data.get(key);

    }

    /**
     * 设置值
     *
     * @param key
     * @param val
     * @return
     */
    public static void set(CloudUtilEnum key, Object val) {
        data.put(key, val);
    }


    /**
     * 是否移动端
     *
     * @return
     */
    public static boolean isClient() {
        return data.get(CloudUtilEnum.IS_CLIENT) == null ? false : true;
    }

}
