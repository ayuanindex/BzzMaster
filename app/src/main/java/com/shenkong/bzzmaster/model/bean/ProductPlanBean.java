package com.shenkong.bzzmaster.model.bean;

import java.util.Date;

public class ProductPlanBean {

    /**
     * 计划ID
     */
    private int planid;

    /**
     * 产品ID
     */
    private int productid;

    /**
     * 计划名称
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
    private int staues;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * 管理员ID
     */
    private int aid;

    /**
     * 标签
     */
    private String tag;

    /**
     * 最小出售
     */
    private int mincompany;

    /**
     * 挖矿周期
     */
    private String runtime;

    /**
     * 封装周期
     */
    private String packtime;

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
    private String updatetime;

    public ProductPlanBean() {
    }

    public int getPlanid() {
        return planid;
    }

    public void setPlanid(int planid) {
        this.planid = planid;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
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

    public int getStaues() {
        return staues;
    }

    public void setStaues(int staues) {
        this.staues = staues;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getMincompany() {
        return mincompany;
    }

    public void setMincompany(int mincompany) {
        this.mincompany = mincompany;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getPacktime() {
        return packtime;
    }

    public void setPacktime(String packtime) {
        this.packtime = packtime;
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

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
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
                ", createtime='" + createtime + '\'' +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", aid=" + aid +
                ", tag='" + tag + '\'' +
                ", mincompany=" + mincompany +
                ", runtime='" + runtime + '\'' +
                ", packtime='" + packtime + '\'' +
                ", note='" + note + '\'' +
                ", detailslink='" + detailslink + '\'' +
                ", servicecharge=" + servicecharge +
                ", updatetime='" + updatetime + '\'' +
                '}';
    }
}
