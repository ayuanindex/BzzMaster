package com.shenkong.bzzmaster.model.bean;

import androidx.annotation.DrawableRes;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class BannerBean implements MultipleAdapter.LayoutType {
    @DrawableRes
    private int defaultImgRes;

    @DrawableRes
    private int[] imgResources = {};

    private String[] imgUrls = {};

    public BannerBean(@DrawableRes int defaultImgRes, @DrawableRes int... imgResources) {
        this.defaultImgRes = defaultImgRes;
        this.imgResources = imgResources;
    }

    public BannerBean(@DrawableRes int defaultImgRes, String... imgUrls) {
        this.defaultImgRes = defaultImgRes;
        this.imgUrls = imgUrls;
    }

    public int getDefaultImgRes() {
        return defaultImgRes;
    }

    public void setDefaultImgRes(@DrawableRes int defaultImgRes) {
        this.defaultImgRes = defaultImgRes;
    }

    public int[] getImgResources() {
        return imgResources;
    }

    public void setImgResources(@DrawableRes int... imgResources) {
        this.imgResources = imgResources;
    }

    public String[] getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String... imgUrls) {
        this.imgUrls = imgUrls;
    }

    @Override
    public int getLayoutType() {
        return Types.BANNER_LAYOUT;
    }
}
