package com.shenkong.bzzmaster.model.bean;

import androidx.annotation.DrawableRes;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.List;

public class BannerBean implements MultipleAdapter.LayoutType {
    @DrawableRes
    private int defaultImgRes;

    private List<CarouselBean> carouselBeanList;

    public BannerBean(int defaultImgRes, List<CarouselBean> carouselBeanList) {
        this.defaultImgRes = defaultImgRes;
        this.carouselBeanList = carouselBeanList;
    }

    public int getDefaultImgRes() {
        return defaultImgRes;
    }

    public void setDefaultImgRes(@DrawableRes int defaultImgRes) {
        this.defaultImgRes = defaultImgRes;
    }

    public List<CarouselBean> getCarouselBeanList() {
        return carouselBeanList;
    }

    public void setCarouselBeanList(List<CarouselBean> carouselBeanList) {
        this.carouselBeanList = carouselBeanList;
    }

    @Override
    public int getLayoutType() {
        return Types.BANNER_LAYOUT;
    }
}
