package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.FrontPage;
import com.shenkong.bzzmaster.model.bean.OrderBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {

    @POST(value = ModelPath.Order + "/shop")
    Observable<ResultBean<Boolean>> requestAddOrder(@Body OrderBean orderBean);

    @POST(value = ModelPath.Order + "/select")
    Observable<ResultBean<FrontPage<List<OrderBean>>>> requestOrders(@Body FrontPage frontPage);

    @POST(value = ModelPath.Order + "/select/id")
    Observable<ResultBean<OrderBean>> requestOrder(@Body OrderBean orderBean);
}
