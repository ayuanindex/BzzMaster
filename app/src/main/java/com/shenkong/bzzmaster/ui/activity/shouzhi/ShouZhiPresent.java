package com.shenkong.bzzmaster.ui.activity.shouzhi;

import com.shenkong.bzzmaster.model.presenter.BasePresenter;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

public class ShouZhiPresent extends BasePresenter<ShouZhiEvent> {
    private LifecycleProvider<ActivityEvent> lifecycleProvider;
    private int page = 1;

    public void setLifecycleProvider(LifecycleProvider<ActivityEvent> lifecycleProvider) {
        this.lifecycleProvider = lifecycleProvider;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    /**
     * 查询收支明细
     */
    public void requestShouZhi() {

    }
}
