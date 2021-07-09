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
    private Long uid;

    /**
     * 计划
     */
    private Long pid;

    /**
     * 数量
     * default: 1
     */
    private Long number;

    /**
     * 状态
     * default: 0
     */
    private Long staue;

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
    private Long aid;

    /**
     * 审核意见
     */
    private String message;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getStaue() {
        return staue;
    }

    public void setStaue(Long staue) {
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

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                '}';
    }
}
