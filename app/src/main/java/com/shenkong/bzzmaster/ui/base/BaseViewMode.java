package com.shenkong.bzzmaster.ui.base;

import androidx.lifecycle.ViewModel;

public class BaseViewMode<V> extends ViewModel {

    public V uiRefreshCallBack;

    public void setUiRefreshCallBack(V uiRefreshCallBack) {
        this.uiRefreshCallBack = uiRefreshCallBack;
    }
}
