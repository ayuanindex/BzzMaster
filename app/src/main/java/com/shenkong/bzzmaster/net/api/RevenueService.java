package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.FrontPage;
import com.shenkong.bzzmaster.model.bean.RevenueBean;
import com.shenkong.bzzmaster.model.bean.RevenueListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RevenueService {

    @POST(value = ModelPath.Revenue + "/select")
    Observable<ResultBean<RevenueListBean>> requestRevenueRecord(@Body FrontPage frontPage);

    @POST(value = ModelPath.Revenue + "/selectChat")
    Observable<ResultBean<List<RevenueBean>>> requestRevenueDetails();
}
