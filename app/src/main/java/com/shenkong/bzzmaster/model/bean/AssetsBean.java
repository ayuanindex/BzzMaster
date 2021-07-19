package com.shenkong.bzzmaster.model.bean;

import java.util.Date;

public class AssetsBean {
    /**
     * 我的资产ID
     */
    private String assetsid;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 用户购买的计划
     */
    private long pid;

    /**
     * 数量
     */
    private long number;

    /**
     * 计划名字
     */
    private String pname;

    /**
     * 购买时间
     */
    private String createtime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * 是否已结束
     */
    private long staue;

    /**
     * 运行时间
     */
    private long runtime;

    /**
     * 购买订单
     */
    private String orderid;

    /**
     * 封装时间
     */
    private long packtime;

    public String getAssetsid() {
        return assetsid;
    }

    public void setAssetsid(String assetsid) {
        this.assetsid = assetsid;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public long getStaue() {
        return staue;
    }

    public void setStaue(long staue) {
        this.staue = staue;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public long getPacktime() {
        return packtime;
    }

    public void setPacktime(long packtime) {
        this.packtime = packtime;
    }

    @Override
    public String toString() {
        return "AssetsBean{" +
                "assetsid='" + assetsid + '\'' +
                ", uid=" + uid +
                ", pid=" + pid +
                ", number=" + number +
                ", pname='" + pname + '\'' +
                ", createtime='" + createtime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", staue=" + staue +
                ", runtime=" + runtime +
                ", orderid='" + orderid + '\'' +
                ", packtime=" + packtime +
                '}';
    }
}
