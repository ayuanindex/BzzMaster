package com.shenkong.bzzmaster.common.utils;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Formatter {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##############");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int oneDayTime = 86400000;

    public static String numberFormat(double value) {
        return decimalFormat.format(value);
    }

    public static String dateToDayFormat(Date date) {
        return String.valueOf(date.getTime() / (1000 * 24 * 60 * 60));
    }

    public static Date parseDate(String dateString) {
        return simpleDateFormat.parse(dateString, new ParsePosition(0));
    }

    public static Date getTheDayBeforeDate(Date date, int days) {
        long time = date.getTime();
        long l = time - (oneDayTime * days);
        return new Date(l);
    }

    public static String formatData(Date date) {
        return simpleDateFormat.format(date);
    }

    public static String generateNumberString(Date date) {
        String timeString = dateFormat.format(date);
        Random random = new Random(new Date().getTime());
        long l = random.nextInt(9999);
        return timeString + l;
    }
}
