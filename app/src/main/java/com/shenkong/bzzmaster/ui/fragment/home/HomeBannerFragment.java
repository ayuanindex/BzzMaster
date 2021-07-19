package com.shenkong.bzzmaster.ui.fragment.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shenkong.bzzmaster.databinding.FragmentHomebannerBinding;
import com.shenkong.bzzmaster.model.bean.CarouselBean;
import com.squareup.picasso.Picasso;

public class HomeBannerFragment extends Fragment {

    private CarouselBean carouselBean = null;
    private final int defaultDrawableResId;
    private FragmentHomebannerBinding binding;

    public HomeBannerFragment(int defaultDrawableResId) {
        this.defaultDrawableResId = defaultDrawableResId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomebannerBinding.inflate(inflater, container, false);
        initData();
        initEvent();
        return binding.getRoot();
    }

    /**
     * @param carouselBean         网络中的图片地址
     * @param defaultDrawableResId 默认图片地址
     */
    public HomeBannerFragment(CarouselBean carouselBean, @DrawableRes int defaultDrawableResId) {
        this.carouselBean = carouselBean;
        this.defaultDrawableResId = defaultDrawableResId;
    }

    protected void initEvent() {

    }

    protected void initData() {
        if (carouselBean != null) {
            Picasso.get().load(carouselBean.getSkiplink())
                    .placeholder(defaultDrawableResId)
                    .error(defaultDrawableResId)
                    .into(binding.imgBanner);

            // 图片跳转
           /* binding.imgBanner.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(carouselBean.getSkiplink());
                intent.setData(content_url);
                startActivity(intent);
            });*/
        } else {
            binding.imgBanner.setImageResource(defaultDrawableResId);
        }
    }
}
