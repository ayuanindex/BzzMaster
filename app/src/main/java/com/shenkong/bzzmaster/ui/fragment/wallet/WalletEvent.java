package com.shenkong.bzzmaster.ui.fragment.wallet;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

import java.util.ArrayList;

public interface WalletEvent extends BaseEven {
    /**
     * 给钱包列表设置数据
     *
     * @param layoutTypes 数据
     */
    void setRecyclerViewData(ArrayList<MultiLayoutAdapter.LayoutType> layoutTypes);

    /**
     * 跳转到充值界面
     *
     * @param productBean 币种信息
     */
    void startCurrencyInfoActivity(ProductBean productBean);

    /**
     * 提示操作对话框
     *
     * @param productBean 币种信息
     */
    void showBottomSheetDialog(ProductBean productBean);
}
