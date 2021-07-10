package com.shenkong.bzzmaster.common.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
    private static DecimalFormat decimalFormat = new DecimalFormat("#00.0000");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("d");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String numberFormat(double value) {
        return decimalFormat.format(value);
    }

    public static String dateToDayFormat(Date date) {
        return String.valueOf(date.getTime() / (1000 * 24 * 60 * 60));
    }

}
