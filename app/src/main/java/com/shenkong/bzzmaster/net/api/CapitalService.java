package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.CapitalBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface CapitalService {

    @POST(value = ModelPath.Capital + "/balance/select")
    Observable<ResultBean<List<CapitalBean>>> requestBalance(@Body CapitalBean capitalBean);

    @POST(value = ModelPath.Capital + "/balance/selectall")
    Observable<ResultBean<List<CapitalBean>>> requestAllBalance(@Body CapitalBean capitalBean);

    @POST(value = ModelPath.Capital  + "/balance/transferOut")
    Observable<ResultBean<Boolean>> requestTransferOut(@Body CapitalBean capitalBean);
}
