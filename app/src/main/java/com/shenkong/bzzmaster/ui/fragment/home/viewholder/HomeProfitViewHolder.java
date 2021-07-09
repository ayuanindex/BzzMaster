package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.activity.orders.OrdersActivity;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class HomeProfitViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
    private final FragmentActivity fragmentActivity;
    public View rootView;
    public MaterialCardView cardSwarm;
    public MaterialTextView tvProfitName;
    public TabLayout tabSwitchDisplay;
    public WebView webViewChart;

    public HomeProfitViewHolder(View rootView, FragmentActivity fragmentActivity) {
        super(rootView);
        this.rootView = rootView;
        this.fragmentActivity = fragmentActivity;
        this.cardSwarm = (MaterialCardView) rootView.findViewById(R.id.cardSwarm);
        this.tvProfitName = (MaterialTextView) rootView.findViewById(R.id.tvProfitName);
        this.tabSwitchDisplay = (TabLayout) rootView.findViewById(R.id.tabSwitchDisplay);
        this.webViewChart = (WebView) rootView.findViewById(R.id.webViewChart);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
        this.cardSwarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragmentActivity, OrdersActivity.class);
                intent.putExtra("type", 0);
                fragmentActivity.startActivity(intent);
            }
        });

        webViewChart.getSettings().setJavaScriptEnabled(true);
        webViewChart.loadUrl("file:///android_asset/web/line-simple.html");
    }
}
