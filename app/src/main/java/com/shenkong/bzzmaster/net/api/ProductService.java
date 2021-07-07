package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HEAD;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface ProductService {
    /**
     * 查询所有产品
     *
     * @return 返回所有产品列表
     */
    @POST(ModelPath.Product + "/select/all")
    Observable<ResultBean<List<ProductBean>>> requestAllProduct();

    /**
     * 查询产品对应的产品计划
     *
     * @param productBean 产品ID
     * @return 返回单个产品的所有计划
     */
    @POST(ModelPath.Product + "/select/selectPlanOne")
    Observable<ResultBean<List<ProductPlanBean>>> requestProductPlan(@Body ProductBean productBean);

    /**
     * 查询最热门的产品
     *
     * @param top 查询热门产品的个数
     * @return 返回热门产品列表，然后调用{@code requestProductPlan}方法获得产品计划
     */
    @POST(ModelPath.Product + "/select/selectPopularProducts")
    Observable<ResultBean<List<ProductBean>>> requestHotProduct(@Body int top);
}
