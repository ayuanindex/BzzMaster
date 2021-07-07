package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class ProductBean implements MultipleAdapter.LayoutType {
    private int productid;
    private String name;
    private int staues;
    private String pic;
    private String introduce;
    private int aid;
    private int top;
    private String currency;

    public ProductBean() {
    }

    public ProductBean(int productid, String name, int staues, String pic, String introduce, int aid, int top, String currency) {
        this.productid = productid;
        this.name = name;
        this.staues = staues;
        this.pic = pic;
        this.introduce = introduce;
        this.aid = aid;
        this.top = top;
        this.currency = currency;
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

    public int getStaues() {
        return staues;
    }

    public void setStaues(int staues) {
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

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
                ", top=" + top +
                ", currency='" + currency + '\'' +
                '}';
    }

    @Override
    public int getLayoutType() {
        return Types.PRODUCT_LAYOUT;
    }
}
