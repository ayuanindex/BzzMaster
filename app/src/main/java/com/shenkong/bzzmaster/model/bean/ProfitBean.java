package com.shenkong.bzzmaster.model.bean;

import com.shenkong.bzzmaster.ui.fragment.home.Types;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class ProfitBean implements MultipleAdapter.LayoutType {
    @Override
    public int getLayoutType() {
        return Types.PROFIT_LAYOUT;
    }
}
