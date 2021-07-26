package com.shenkong.bzzmaster.ui.activity.currencyinfo;

import android.graphics.Bitmap;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.DetailBean;

public interface CurrencyInfoEvent extends BaseEven {
    /**
     * 给列表设置数据
     *
     * @param detailBeans 数据
     */
    void updateRecycerViewDatas(DetailBean detailBeans);
}
