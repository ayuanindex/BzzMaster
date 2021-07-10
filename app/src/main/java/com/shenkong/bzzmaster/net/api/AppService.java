package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.AppUpdateBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AppService {

    @POST(value = ModelPath.App + "/edition")
    Observable<ResultBean<AppUpdateBean>> resultCheckAppUpdate(@Body AppUpdateBean appUpdateBean);
}
