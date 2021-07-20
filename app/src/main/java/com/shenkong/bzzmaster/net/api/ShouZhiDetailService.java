package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.DetailBean;
import com.shenkong.bzzmaster.model.bean.FrontPage;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ShouZhiDetailService {

    @POST(value = ModelPath.Detail + "/select")
    Observable<ResultBean<DetailBean>> requestShouZhiDetail(@Body FrontPage frontPage);
}
