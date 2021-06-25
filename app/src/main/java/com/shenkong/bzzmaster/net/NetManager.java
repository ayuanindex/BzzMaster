package com.shenkong.bzzmaster.net;

import com.google.gson.GsonBuilder;
import com.shenkong.bzzmaster.common.config.ModelPath;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    private static final NetManager sInstance = new NetManager();
    private static final String TAG = "HttpLog";
    /**
     * token令牌
     */
    private Map<String, String> mXCsrfToken = new HashMap<>();
    private Retrofit mRetrofit;

    NetManager() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ModelPath.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
    }

    public static NetManager getInstance() {
        return sInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
