package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.AssetsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AssetsService {
    /**
     * 查询所有用户的计划
     *
     * @return 返回已购计划列表
     */
    @POST(value = ModelPath.Assets + "/select")
    Observable<ResultBean<List<AssetsBean>>> requestAssets();

    /**
     * 请求取消计划(联合挖矿计划)
     *
     * @param assetsBean 需要取消的计划
     * @return 返回取消计划是否成功的状态true表示成功, false表示你失败
     */
    @POST(value = ModelPath.Assets + "/cancel")
    Observable<ResultBean<Boolean>> requestCancel(@Body AssetsBean assetsBean);
}
