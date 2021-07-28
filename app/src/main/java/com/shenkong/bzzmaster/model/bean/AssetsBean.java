package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.activity.plan.Types;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

import io.reactivex.annotations.NonNull;

public class AssetsBean implements MultiLayoutAdapter.LayoutType {

    @Override
    public int getLayoutType() {
        if (this.pledgetime > 0) {
            return Types.PLEDGE;
        } else {
            return Types.NORMAL;
        }
    }

    /**
     * 我的资产ID
     */
    private String assetsid;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 用户购买的计划
     */
    private Long pid;

    /**
     * 数量
     */
    private Long number;

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
    private Long staue;

    /**
     * 运行时间
     */
    private Long runtime;

    /**
     * 购买订单
     */
    private String orderid;

    /**
     * 封装时间
     */
    private Long packtime;

    /**
     * 质押时间
     */
    private long pledgetime = 0L;

    /**
     * 质押结束
     */
    private int endOfPledge;

    /**
     * 返还金额
     */
    private double refundamount;

    /**
     * 币种
     */
    private String currency;

    /**
     * 未释放金额
     */
    private double unreleasedAmount;

    public String getAssetsid() {
        return assetsid;
    }

    public void setAssetsid(String assetsid) {
        this.assetsid = assetsid;
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

    public Long getStaue() {
        return staue;
    }

    public void setStaue(Long staue) {
        this.staue = staue;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Long getPacktime() {
        return packtime;
    }

    public void setPacktime(Long packtime) {
        this.packtime = packtime;
    }

    public Long getPledgetime() {
        return pledgetime;
    }

    public void setPledgetime(Long pledgetime) {
        this.pledgetime = pledgetime;
    }

    public int getEndOfPledge() {
        return endOfPledge;
    }

    public void setEndOfPledge(int endOfPledge) {
        this.endOfPledge = endOfPledge;
    }

    public void setPledgetime(long pledgetime) {
        this.pledgetime = pledgetime;
    }

    public double getRefundamount() {
        return refundamount;
    }

    public void setRefundamount(double refundamount) {
        this.refundamount = refundamount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getUnreleasedAmount() {
        return unreleasedAmount;
    }

    public void setUnreleasedAmount(double unreleasedAmount) {
        this.unreleasedAmount = unreleasedAmount;
    }

    @NonNull
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
                ", pledgetime=" + pledgetime +
                ", endOfPledge=" + endOfPledge +
                ", refundamount=" + refundamount +
                ", currency='" + currency + '\'' +
                ", unreleasedAmount=" + unreleasedAmount +
                '}';
    }
}
