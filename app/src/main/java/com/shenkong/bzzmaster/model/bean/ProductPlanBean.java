package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.Date;

public class ProductPlanBean implements MultipleAdapter.LayoutType {

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
    private Date runtime;

    /**
     * 封装周期
     */
    private Date packtime;

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
    private Date updatetime;

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

    public Date getRuntime() {
        return runtime;
    }

    public void setRuntime(Date runtime) {
        this.runtime = runtime;
    }

    public Date getPacktime() {
        return packtime;
    }

    public void setPacktime(Date packtime) {
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public int getLayoutType() {
        return Types.PRODUCT_LAYOUT;
    }
}
