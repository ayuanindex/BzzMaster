package com.shenkong.bzzmaster.model.bean;

import java.util.Date;

public class RevenueBean {
    /**
     * 用户收益ID
     */
    private String revenueid;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 计划ID
     */
    private long pid;

    /**
     * 收益金额
     */
    private double money;

    /**
     * 是否添加到资产列表
     */
    private long hadadd;

    /**
     *
     * 日期
     */
    private Date createdate;
    /**
     * 时间
     */
    private Date createtime;

    public String getRevenueid() {
        return revenueid;
    }

    public void setRevenueid(String revenueid) {
        this.revenueid = revenueid;
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

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public long getHadadd() {
        return hadadd;
    }

    public void setHadadd(long hadadd) {
        this.hadadd = hadadd;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "RevenueBean{" +
                "revenueid='" + revenueid + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", money=" + money +
                ", hadadd=" + hadadd +
                ", createdate=" + createdate +
                ", createtime=" + createtime +
                '}';
    }
}