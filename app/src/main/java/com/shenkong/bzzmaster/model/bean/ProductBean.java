package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;
import com.shenkong.bzzmaster.ui.fragment.wallet.Types;

public class ProductBean implements MultiLayoutAdapter.LayoutType {
    @Override
    public int getLayoutType() {
        return Types.SIMPLE;
    }


    /**
     * 产品ID
     */
    private long productid;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 状态
     */
    private long staues;
    /**
     * 权限
     * default: 0
     */
//    private long jurisdiction;
    /**
     * 产品图片
     */
    private String pic;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 管理员
     */
    private long aid;

    /**
     * 管理员名字
     */
    private String aname;

    /**
     * 置顶
     * default: 0
     */
    private long top;

    private String currency;

    private double rate;
    private String createtime;
    private String updatetime;

    private long updateaid;
    /**
     * 管理员名字
     */
    private String updatename;
    /**
     * 总余额
     */
    private double balance;

    private CapitalBean capitalBean;

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStaues() {
        return staues;
    }

    public void setStaues(long staues) {
        this.staues = staues;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public long getTop() {
        return top;
    }

    public void setTop(long top) {
        this.top = top;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public long getUpdateaid() {
        return updateaid;
    }

    public void setUpdateaid(long updateaid) {
        this.updateaid = updateaid;
    }

    public String getUpdatename() {
        return updatename;
    }

    public void setUpdatename(String updatename) {
        this.updatename = updatename;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public CapitalBean getCapitalBean() {
        return capitalBean;
    }

    public void setCapitalBean(CapitalBean capitalBean) {
        this.capitalBean = capitalBean;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "productid=" + productid +
                ", name='" + name + '\'' +
                ", staues=" + staues +
                ", pic='" + pic + '\'' +
                ", introduce='" + introduce + '\'' +
                ", aid=" + aid +
                ", aname='" + aname + '\'' +
                ", top=" + top +
                ", currency='" + currency + '\'' +
                ", rate=" + rate +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", updateaid=" + updateaid +
                ", updatename='" + updatename + '\'' +
                ", balance=" + balance +
                ", capitalBean=" + capitalBean +
                '}';
    }
}
