package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.Date;

public class ProductPlanBean implements MultipleAdapter.LayoutType {
    /**
     * 计划ID
     */
    private long planid;

    /**
     * 产品ID
     */
    private long productid;

    /**
     * 计划名字
     */
    private String name;

    /**
     * 介绍
     */
    private String introduce;

    /**
     * 图片
     */
    private String pic;

    /**
     * 价格
     */
    private double price;

    /**
     * 币种
     */
    private String currency;

    /**
     * 状态
     */
    private long staues;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 开始时间
     */
    private Date starttime;


    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 管理员ID
     */
    private long aid;

    /**
     * 标签
     */
    private String tag;

    /**
     * 最小出售
     */
    private long mincompany;

    /**
     * 封装周期
     */
    private long packtime;

    /**
     * 挖矿周期
     */
    private long runtime;

    /**
     * 锁仓周期
     */
    private long locktime;
    /**
     * 锁仓金额
     */
    private double lockmoney;

    /**
     * 信息
     */
    private String note;

    /**
     * 详情链接
     */
    private String detailslink;

    /**
     * 服务费
     */
    private double servicecharge;

    /**
     * 更新时间
     */
    // " updatetime is not null "
    private Date updatetime;

    private long updateaid;

    public long getPlanid() {
        return planid;
    }

    public void setPlanid(long planid) {
        this.planid = planid;
    }

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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getStaues() {
        return staues;
    }

    public void setStaues(long staues) {
        this.staues = staues;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public long getAid() {
        return aid;
    }

    public void setAid(long aid) {
        this.aid = aid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getMincompany() {
        return mincompany;
    }

    public void setMincompany(long mincompany) {
        this.mincompany = mincompany;
    }

    public long getPacktime() {
        return packtime;
    }

    public void setPacktime(long packtime) {
        this.packtime = packtime;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public long getLocktime() {
        return locktime;
    }

    public void setLocktime(long locktime) {
        this.locktime = locktime;
    }

    public double getLockmoney() {
        return lockmoney;
    }

    public void setLockmoney(double lockmoney) {
        this.lockmoney = lockmoney;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDetailslink() {
        return detailslink;
    }

    public void setDetailslink(String detailslink) {
        this.detailslink = detailslink;
    }

    public double getServicecharge() {
        return servicecharge;
    }

    public void setServicecharge(double servicecharge) {
        this.servicecharge = servicecharge;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public long getUpdateaid() {
        return updateaid;
    }

    public void setUpdateaid(long updateaid) {
        this.updateaid = updateaid;
    }

    @Override
    public int getLayoutType() {
        return Types.PRODUCT_LAYOUT;
    }

    @Override
    public String toString() {
        return "ProductPlanBean{" +
                "planid=" + planid +
                ", productid=" + productid +
                ", name='" + name + '\'' +
                ", introduce='" + introduce + '\'' +
                ", pic='" + pic + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", staues=" + staues +
                ", createtime=" + createtime +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", aid=" + aid +
                ", tag='" + tag + '\'' +
                ", mincompany=" + mincompany +
                ", runtime=" + runtime +
                ", packtime=" + packtime +
                ", note='" + note + '\'' +
                ", detailslink='" + detailslink + '\'' +
                ", servicecharge=" + servicecharge +
                ", updatetime=" + updatetime +
                '}';
    }
}
