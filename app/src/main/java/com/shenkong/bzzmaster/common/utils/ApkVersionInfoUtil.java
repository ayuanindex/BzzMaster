package com.shenkong.bzzmaster.common.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public class ApkVersionInfoUtil {
    /**
     * 获取当前apk版本号
     *
     * @param context 上下文
     * @return 返回versionCode
     */
    public static int getVersionCode(Context context) {
        int versionCode = 1;
        try {
            PackageManager packageManager = context.getPackageManager();
            versionCode = packageManager.getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取当前apk版本名
     *
     * @param context 上下文
     * @return 返回versionName
     */
    public static String getVersionName(Context context) {
        String versionName = "1.0";
        try {
            PackageManager packageManager = context.getPackageManager();
            versionName = packageManager.getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
