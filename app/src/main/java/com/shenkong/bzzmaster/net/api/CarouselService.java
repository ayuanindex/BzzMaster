package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.CarouselBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface CarouselService {
    @POST(value = ModelPath.Carousel + "/carouseMap")
    Observable<ResultBean<List<CarouselBean>>> requestCarousel();
}
