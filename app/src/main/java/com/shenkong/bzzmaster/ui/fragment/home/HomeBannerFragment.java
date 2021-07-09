package com.shenkong.bzzmaster.ui.fragment.home;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.CarouselBean;
import com.shenkong.bzzmaster.ui.base.BaseFragment;

public class HomeBannerFragment extends BaseFragment {

    private CarouselBean carouselBean = null;
    private int defaultDrawableResId = R.drawable.img_banner_1;
    private AppCompatImageView imgBanner;

    public HomeBannerFragment(int defaultDrawableResId) {
        this.defaultDrawableResId = defaultDrawableResId;
    }

    /**
     * @param carouselBean         网络中的图片地址
     * @param defaultDrawableResId 默认图片地址
     */
    public HomeBannerFragment(CarouselBean carouselBean, @DrawableRes int defaultDrawableResId) {
        this.carouselBean = carouselBean;
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

    }

    @Override
    protected void initData() {
        if (carouselBean != null) {
            Glide.with(requireContext())
                    .load(carouselBean.getSkiplink())
                    .placeholder(defaultDrawableResId)
                    .error(defaultDrawableResId)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imgBanner);

            imgBanner.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(carouselBean.getSkiplink());
                intent.setData(content_url);
                startActivity(intent);
            });
        } else {
            imgBanner.setImageResource(defaultDrawableResId);
        }
    }
}
