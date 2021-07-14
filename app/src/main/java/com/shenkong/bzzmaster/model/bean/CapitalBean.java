package com.shenkong.bzzmaster.model.bean;

public class CapitalBean {
    /**
     * 资金ID
     */
    private long capitalid;

    /**
     * 币种名称
     */
    private String name;

    /**
     * 币种
     */
    private String currency;

    /**
     * 产品ID
     */
    private long pid;

    /**
     * 用户ID
     */
    private long uid;

    /**
     * 类型
     * default: 0
     */
    private long staue;

    /**
     * 余额
     * default: 0.00
     */
    private double balance;

    /**
     * 地址
     */
    private String adress;

    /**
     * 订单号 防重复提交
     */
    private String applyid;

    public long getCapitalid() {
        return capitalid;
    }

    public void setCapitalid(long capitalid) {
        this.capitalid = capitalid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getStaue() {
        return staue;
    }

    public void setStaue(long staue) {
        this.staue = staue;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CapitalBean{" +
                "capitalid=" + capitalid +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", pid=" + pid +
                ", uid=" + uid +
                ", staue=" + staue +
                ", balance=" + balance +
                ", adress='" + adress + '\'' +
                ", applyid='" + applyid + '\'' +
                '}';
    }
}