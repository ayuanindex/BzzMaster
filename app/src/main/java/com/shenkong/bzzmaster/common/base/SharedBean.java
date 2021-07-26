package com.shenkong.bzzmaster.common.base;

import java.util.HashMap;

public class SharedBean {
    private static final HashMap<String, Object> shareData = new HashMap<>();
    public static final String ProductPlanBean = "productPlanBean";
    public static final String Product = "product";

    public static void putData(String key, Object o) {
        shareData.put(key, o);
    }

    public static Object getData(String key) {
        return shareData.get(key);
    }

    public static <T> T getData(Class<T> mClass, String key) {
        return mClass.cast(shareData.get(key));
    }

    public static void remove(String key) {
        shareData.remove(key);
    }
}
