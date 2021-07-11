package com.shenkong.bzzmaster.model.bean;

import java.util.Date;

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
    private Double amount;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 支付时间
     */
    private Date paytime;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getPaytime() {
        return paytime;
    }

    public void setPaytime(Date paytime) {
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

    @Override
    public String toString() {
        return "OrderBean{" +
                "orderid='" + orderid + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", number=" + number +
                ", staue=" + staue +
                ", amount=" + amount +
                ", createtime=" + createtime +
                ", paytime=" + paytime +
                ", aid=" + aid +
                ", message='" + message + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }
}
