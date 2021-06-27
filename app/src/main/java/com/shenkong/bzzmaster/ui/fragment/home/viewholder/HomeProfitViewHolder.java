package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class HomeProfitViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
    public View rootView;
    public MaterialTextView tvProfitName;
    public TabLayout tabSwitchDisplay;
    public WebView webViewChart;

    public HomeProfitViewHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.tvProfitName = (MaterialTextView) rootView.findViewById(R.id.tvProfitName);
        this.tabSwitchDisplay = (TabLayout) rootView.findViewById(R.id.tabSwitchDisplay);
        this.webViewChart = (WebView) rootView.findViewById(R.id.webViewChart);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
        webViewChart.getSettings().setJavaScriptEnabled(true);
        webViewChart.loadUrl("file:///android_asset/web/line-simple.html");
    }
}
