package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import io.reactivex.annotations.NonNull;

public class ProductPlanBean implements MultipleAdapter.LayoutType {
    @Override
    public int getLayoutType() {
        return Types.PRODUCT_LAYOUT;
    }

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
    private String updatetime;

    private long updateaid;

    /**
     * 类型
     */
    private int type;

    /**
     * 质押时间
     */
    private int pledgetime;

    /**
     * 管理员名字
     */
    private String aname;
    /**
     * 管理员名字
     */
    private String updatename;

    /**
     * 已购买数量
     */
    private int total;

    /**
     * 消耗币
     */
    private double consumed;

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

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getUpdatename() {
        return updatename;
    }

    public void setUpdatename(String updatename) {
        this.updatename = updatename;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPledgetime() {
        return pledgetime;
    }

    public void setPledgetime(int pledgetime) {
        this.pledgetime = pledgetime;
    }

    public double getConsumed() {
        return consumed;
    }

    public void setConsumed(double consumed) {
        this.consumed = consumed;
    }

    @NonNull
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
                ", packtime=" + packtime +
                ", runtime=" + runtime +
                ", locktime=" + locktime +
                ", lockmoney=" + lockmoney +
                ", note='" + note + '\'' +
                ", detailslink='" + detailslink + '\'' +
                ", servicecharge=" + servicecharge +
                ", updatetime='" + updatetime + '\'' +
                ", updateaid=" + updateaid +
                ", type=" + type +
                ", pledgetime=" + pledgetime +
                ", aname='" + aname + '\'' +
                ", updatename='" + updatename + '\'' +
                ", total=" + total +
                ", consumed=" + consumed +
                '}';
    }
}
