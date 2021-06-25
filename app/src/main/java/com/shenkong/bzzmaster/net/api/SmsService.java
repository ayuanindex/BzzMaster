package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.User;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 验证码
 */
public interface SmsService {
    /**
     * 发送登录验证码
     * @param user
     * @return
     */
    @POST(ModelPath.User+"/login/code")
    Observable<ResultBean<String>> LoginCode(@Body User user);
}
