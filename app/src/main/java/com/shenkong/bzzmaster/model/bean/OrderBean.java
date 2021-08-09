package com.shenkong.bzzmaster.model.bean;

import java.util.Date;

import io.reactivex.annotations.NonNull;

public class OrderBean {

    /**
     * 订单号
     */
    private String orderid;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 计划
     */
    private long pid;

    /**
     * 数量
     * default: 1
     */
    private long number;

    /**
     * 状态
     * default: 0
     */
    private long staue;

    /**
     * 金额
     */
    private double amount;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 支付时间
     */
    private String paytime;

    /**
     * 审核管理员ID
     */
    private long aid;

    /**
     * 审核意见
     */
    private String message;

    /**
     * 购买计划的名字
     */
    private String pname;

    /**
     * 返还金额
     */
    private double refundamount;

    /**
     * 混合支付返回
     */
    private double hybridpaymen;

    /**
     * 购买计划的币种
     */
    private String currency;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getStaue() {
        return staue;
    }

    public void setStaue(long staue) {
        this.staue = staue;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(double refundamount) {
        this.refundamount = refundamount;
    }

    public double getHybridpaymen() {
        return hybridpaymen;
    }

    public void setHybridpaymen(double hybridpaymen) {
        this.hybridpaymen = hybridpaymen;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @NonNull
    @Override
    public String toString() {
        return "OrderBean{" +
                "orderid='" + orderid + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", number=" + number +
                ", staue=" + staue +
                ", amount=" + amount +
                ", createtime='" + createtime + '\'' +
                ", paytime='" + paytime + '\'' +
                ", aid=" + aid +
                ", message='" + message + '\'' +
                ", pname='" + pname + '\'' +
                ", refundamount=" + refundamount +
                ", hybridpaymen=" + hybridpaymen +
                ", currency='" + currency + '\'' +
                '}';
    }
}
