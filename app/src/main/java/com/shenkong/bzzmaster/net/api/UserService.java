package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.User;

import java.util.HashMap;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * 登录
 */
public interface UserService {

    /**
     * 验证码登录
     * @param user
     * @return
     */
    @POST(ModelPath.User+"/login/code")
    Observable<ResultBean<User>> LoginCode(@Body HashMap<String,String> user);

    @POST(value = ModelPath.User + "/details")
    Observable<ResultBean<User>> requestDetail();

    /**
     * 查询用户详情
     */
    @POST(ModelPath.User+"/details")
    Observable<ResultBean<User>> AboutMe(@HeaderMap Map<String, String> csrfToken);


}
