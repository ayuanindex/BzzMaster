package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface PlanService {

    @POST(value = ModelPath.Plan + "/select/hot")
    Observable<ResultBean<List<ProductPlanBean>>> requestHotProductPlan();
}
