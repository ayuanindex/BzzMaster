package com.shenkong.bzzmaster.common.base;

import java.util.HashMap;

public class SharedBean {
    private static final HashMap<String, Object> shareData = new HashMap<>();
    public static String ProductPlanBean = "productPlanBean";

    public static void putData(String key, Object o) {
        shareData.put(key, o);
    }

    public static Object getData(String key) {
        return shareData.get(key);
    }
}
