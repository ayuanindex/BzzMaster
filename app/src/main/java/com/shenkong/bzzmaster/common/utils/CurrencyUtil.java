package com.shenkong.bzzmaster.common.utils;

public class CurrencyUtil {
    public static String getUnit(String currency) {
        switch (currency.toLowerCase()) {
            case "xch":
                return "TiB";
            case "bzz":
                return "节点";
            case "fil":
                return "TiB";
            default:
                return "TiB";
        }
    }
}
