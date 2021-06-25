package com.shenkong.bzzmaster.ui.fragment.home;

import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.ui.base.BaseFragment;

public class HomeBannerFragment extends BaseFragment {

    private String imgUrl;
    private int defaultDrawableResId = R.drawable.img_banner_1;
    private AppCompatImageView imgBanner;


    /**
     * @param defaultDrawableResId 默认图片ID
     */
    public HomeBannerFragment(@DrawableRes int defaultDrawableResId) {
        this.defaultDrawableResId = defaultDrawableResId;
    }

    /**
     * @param imgUrl               网络中的图片地址
     * @param defaultDrawableResId 默认图片地址
     */
    public HomeBannerFragment(String imgUrl, @DrawableRes int defaultDrawableResId) {
        this.imgUrl = imgUrl;
        this.defaultDrawableResId = defaultDrawableResId;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_homebanner;
    }

    @Override
    protected void initView(View inflate) {
        imgBanner = (AppCompatImageView) inflate.findViewById(R.id.imgBanner);
    }

    @Override
    protected void initEvent() {
        imgBanner.setOnClickListener(v -> ToastUtil.showToast(getContext(), "Hello"));
    }

    @Override
    protected void initData() {
        imgBanner.setImageResource(defaultDrawableResId);
    }
}
