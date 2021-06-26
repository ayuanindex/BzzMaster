package com.shenkong.bzzmaster.ui.fragment.home;

import com.shenkong.bzzmaster.event.BaseEven;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.util.ArrayList;

public interface HomeEvent extends BaseEven {
    void setRecyclerViewAdapter(ArrayList<MultipleAdapter.LayoutType> listBeans);
}
