package com.shenkong.bzzmaster.ui.base;

import androidx.lifecycle.ViewModel;

import com.shenkong.bzzmaster.event.BaseEven;

public class BaseViewMode<V extends BaseEven> extends ViewModel {

    public V uiRefreshCallBack;

    public void setUiRefreshCallBack(V uiRefreshCallBack) {
        this.uiRefreshCallBack = uiRefreshCallBack;
    }
}
