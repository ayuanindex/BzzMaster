package com.shenkong.bzzmaster.common.config;

public class ConstantPool {
    public static final int permissionCode = 5;

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

    /**
     * 用户计划质押周期未结束
     */
    public static final Long Pledge = 1L;
    /**
     * 用户计划质押周期结束
     */
    public static final Long PledgeEnd = 2L;

    /*-----------------------计划类型------------------------*/

    /**
     * 普通计划
     */
    public static final int PlanType_Normal = 1;

    /**
     * 质押计划
     */
    public static final int PlanType_Pledge = 2;

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

    /*--------------------收支详情------------------------*/
    /**
     * 转帐（入）
     */
    public static final int Detail_Transaction = 1;
    /**
     * 提币
     */
    public static final int Detail_Apply = 2;
    /**
     * 收益
     */
    public static final int Detail_Revenue = 3;

    /*------------------App版本类型----------------------*/

    /**
     * 正常发布 用户可取消
     */
    public static final Long A_PUSH = 501L;

    /**
     * 测试发布 测试版本可见
     */
    public static final Long A_TEST = 502L;

    /**
     * 版本下架 不再可见
     */
    public static final Long A_PULL = 503L;

    /**
     * 强制更新
     */
    public static final Long A_FORCE = 504L;
}
