package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.ChatBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ChatService {
    @POST(value = ModelPath.Chat + "/selectChat")
    Observable<ResultBean<List<ChatBean>>> requestChat();
}
