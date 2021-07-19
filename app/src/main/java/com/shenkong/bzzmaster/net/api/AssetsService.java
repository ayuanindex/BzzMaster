package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.AssetsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface AssetsService {
    @POST(value = ModelPath.Assets + "/select")
    Observable<ResultBean<List<AssetsBean>>> requestAssets();
}
