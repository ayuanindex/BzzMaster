package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.AppUpdateBean;
import com.shenkong.bzzmaster.model.bean.EditionDTO;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface AppService {

    @POST(value = ModelPath.App + "/edition")
    Observable<ResultBean<AppUpdateBean>> resultCheckAppUpdate(@Body EditionDTO editionDTO);

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
