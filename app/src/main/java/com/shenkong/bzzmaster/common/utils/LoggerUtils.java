package com.shenkong.bzzmaster.common.utils;

import android.util.Log;
import com.shenkong.bzzmaster.BuildConfig;

/**
 * @author ayuan
 */
public class LoggerUtils {


    /*private static final String TAG = "LoggerUtils";*/
    private static final boolean DEBUG = BuildConfig.DEBUG;
    private static final int D = 745;
    private static final int E = 421;
    private static final int V = 674;
    private static final String CUT_OFF = "..........................";
    private static final String CUT_OFF_END = ".........................." +
            ".................................................";
    private static Throwable e;

    public static void d(String tag, String... values) {
        printf(D, tag, values);
    }

    public static void e(String tag, String... values) {
        printf(E, tag, values);
    }

    public static void e(String tag, Throwable e, String... values) {
        LoggerUtils.e = e;
        printf(E, tag, values);
    }

    public static void v(String tag, String... values) {
        printf(V, tag, values);
    }

    private static void printf(int mark, String tag, String... values) {
        if (DEBUG) {
            //需要打印的内容
            StringBuilder value = new StringBuilder();
            for (int i = 0; i < values.length; i++) {
                value.append(values[i]);
                if (i == values.length - 1) {
                    break;
                }
                value.append(", ");
            }

            // 打印
            switch (mark) {
                case D:
                    printfLine(D, tag);
                    Log.d(tag, value.toString());
                    Log.d(tag, CUT_OFF_END);
                    break;
                case E:
                    printfLine(E, tag);
                    if (e != null) {
                        Log.e(tag, value.toString(), e);
                        Log.e(tag, CUT_OFF_END);
                        e = null;
                    }
                    Log.e(tag, value.toString());
                    Log.e(tag, CUT_OFF_END);
                    break;
                case V:
                    printfLine(V, tag);
                    Log.v(tag, value.toString());
                    Log.v(tag, CUT_OFF_END);
                    break;
            }
        }
    }

    private static void printfLine(int mark, String tag) {
        String startLine = CUT_OFF + getPosition(tag) + CUT_OFF;
        switch (mark) {
            case D:
                Log.d(tag, " ");
                Log.d(tag, startLine);
                break;
            case E:
                Log.e(tag, " ");
                Log.e(tag, startLine);
                break;
            case V:
                Log.v(tag, " ");
                Log.v(tag, startLine);
                break;
        }
    }

    private static String getPosition(String tag) {
        StringBuilder sb = new StringBuilder();
        StackTraceElement element = getTargetStack(tag);

        if (null == element) {
            return null;
        }

        sb.append("(")
                .append(element.getFileName())
                .append(":")
                .append(element.getLineNumber())
                .append(")");
        return sb.toString();
    }

    private static StackTraceElement getTargetStack(String tag) {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().contains(tag)) {
                //返回调用位置的 element
                return element;
            }
        }
        return null;
    }
}
