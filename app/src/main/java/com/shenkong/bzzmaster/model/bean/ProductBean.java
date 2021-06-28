package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class ProductBean implements MultipleAdapter.LayoutType {
    private String title;
    private String imgUrl;

    public ProductBean(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public int getLayoutType() {
        return Types.PRODUCT_LAYOUT;
    }
}
