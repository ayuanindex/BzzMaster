package com.shenkong.bzzmaster.common.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SpUtil {
    private static SharedPreferences sharedPreferences;

    private static SharedPreferences doesItExist(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void putString(Context context, String key, String value) {
        doesItExist(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return doesItExist(context).getString(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        doesItExist(context).edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, String key, int defValue) {
        return doesItExist(context).getInt(key, defValue);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        doesItExist(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return doesItExist(context).getBoolean(key, defValue);
    }

    public static void removeTag(Context context, String key) {
        doesItExist(context).edit().remove(key).apply();
    }
}
