package com.shenkong.bzzmaster.net.api;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.config.ModelPath;
import com.shenkong.bzzmaster.model.bean.NoticeBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface NoticeService {

    /**
     * 查询公告
     */
    @POST(value = ModelPath.Notice + "/selectNotice")
    Observable<ResultBean<List<NoticeBean>>> requestNotices();
}
