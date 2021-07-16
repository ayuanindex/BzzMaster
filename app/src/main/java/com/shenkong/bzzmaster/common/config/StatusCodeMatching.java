package com.shenkong.bzzmaster.common.config;

public class StatusCodeMatching {
    /**
     * 根据状态码匹配订单状态
     *
     * @return 返回订单状态字符串
     */
    public static String getOrderStatus(long orderStatusCode) {
        if (orderStatusCode == ConstantPool.Order_Unpaid) {
            return "未支付";
        } else if (orderStatusCode == ConstantPool.Order_Cancelled) {
            return "已取消";
        } else if (orderStatusCode == ConstantPool.Order_paymentSuccessful) {
            return "已支付";
        } else if (orderStatusCode == ConstantPool.Order_pendingReview) {
            return "待审核";
        } else if (orderStatusCode == ConstantPool.Order_ExaminationPassed) {
            return "审核通过";
        } else if (orderStatusCode == ConstantPool.Order_AuditFailed) {
            return "审核未通过";
        } else {
            return "待审核";
        }
    }
}
