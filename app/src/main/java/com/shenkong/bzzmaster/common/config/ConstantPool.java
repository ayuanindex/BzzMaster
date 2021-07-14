package com.shenkong.bzzmaster.common.config;

public class ConstantPool {
    /*-------------------计划状态---------------------*/
    /**
     * 预售
     */
    public static final Long Plan_PreSale = 1L;

    /**
     * 销售
     */
    public static final Long Pro_Sell = 2L;

    /**
     * 暂停申售
     */
    public static final Long Pro_SuspensionOfSale = 3L;

    /**
     * 完结
     */
    public static final Long Pro_Finish = 4L;

    /**
     * 下架
     */
    public static final Long Pro_OffShelf = 5L;

    /*-------------------------订单状态----------------------*/
    /**
     * 未支付
     */
    public static final Long Order_Unpaid = 1L;
    /**
     * 已取消
     */
    public static final Long Order_Cancelled = 2L;
    /**
     * 支付成功
     */
    public static final Long Order_paymentSuccessful = 3L;
    /**
     * 待审核
     */
    public static final Long Order_pendingReview = 4L;
    /**
     * 审核通过
     */
    public static final Long Order_ExaminationPassed = 5L;
    /**
     * 审核未通过
     */
    public static final Long Order_AuditFailed = 6L;
}
