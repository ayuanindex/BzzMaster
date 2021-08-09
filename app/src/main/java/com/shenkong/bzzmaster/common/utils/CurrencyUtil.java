package com.shenkong.bzzmaster.common.utils;

import com.shenkong.bzzmaster.model.bean.ProductBean;

import java.nio.file.ClosedWatchServiceException;
import java.util.List;

public class CurrencyUtil {
    public static String getUnit(String currency) {
        switch (currency.toLowerCase()) {
            case "bzz":
            case "ebzz":
                return "节点";
            case "xch":
            case "fil":
            default:
                return "TiB";
        }
    }

    public static String getProfitUnit(String currency) {
        switch (currency.toLowerCase()) {
            case "ebzz":
            case "bzz":
                return "节点";
            case "xch":
            case "fil":
            default:
                return "T";
        }
    }

    /**
     * 根据ID获取币种名称
     *
     * @param productId       产品ID
     * @param productBeanList 产品列表
     * @return 返回币种名称
     */
    public static String getCurrencyById(long productId, List<ProductBean> productBeanList) {
        for (ProductBean productBean : productBeanList) {
            if (productBean.getProductid() == productId) {
                return productBean.getCurrency();
            }
        }
        return "";
    }
}
