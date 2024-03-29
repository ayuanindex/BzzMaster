package com.shenkong.bzzmaster.net;

import com.blankj.utilcode.util.Utils;
import com.google.gson.GsonBuilder;
import com.shenkong.bzzmaster.BuildConfig;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.common.utils.SpUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    public static NetManager sInstance = new NetManager();
    private static final String TAG = "HttpLog";
    /**
     * token令牌
     */
    private Map<String, String> mXCsrfToken = new HashMap<>();
    private Retrofit mRetrofit;

    public NetManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 添加请求头
        builder.addInterceptor(chain -> {
            Request request = chain.request();
            Request.Builder newBuilder = request.newBuilder();
            newBuilder.addHeader("token", SpUtil.getString(Utils.getApp().getApplicationContext(), SpUtil.token, ""));
            newBuilder.addHeader("userid", SpUtil.getString(Utils.getApp().getApplicationContext(), SpUtil.userId, ""));
            Request.Builder method = newBuilder.method(request.method(), request.body());
            Request build = method.build();
            return chain.proceed(build);
        });

        // 根据版本类型判断需要的服务器地址
        String baseUrl = "";
        if (BuildConfig.DEBUG) {
            baseUrl = ModelPath.testUrl;
        } else {
            // 根据渠道选择服务器地址
            switch (BuildConfig.firm) {
                case "mifan":
                    baseUrl = ModelPath.miFanUrl;
                    break;
                default:
                    baseUrl = ModelPath.baseUrl;
                    break;
            }
        }

        mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }

    public static NetManager getInstance() {
        return sInstance;
    }

    public static void setsInstance(NetManager sInstance) {
        NetManager.sInstance = sInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
