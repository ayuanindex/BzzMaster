package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.bean.ProductBean;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class ProductViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
    public View rootView;
    public MaterialTextView tvProductTitle;
    public LinearLayoutCompat llTags;
    public MaterialTextView tvPrice;
    public MaterialTextView tvPriceUnit;
    public MaterialTextView tvRevenueDate;
    public MaterialTextView tvDay;
    public MaterialTextView tvTip;
    public MaterialButton btnPurchase;
    private MultipleAdapter multipleAdapter;
    private ProductBean productBean;

    public ProductViewHolder(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.tvProductTitle = (MaterialTextView) rootView.findViewById(R.id.tvProductTitle);
        this.llTags = (LinearLayoutCompat) rootView.findViewById(R.id.llTags);
        this.tvPrice = (MaterialTextView) rootView.findViewById(R.id.tvPrice);
        this.tvPriceUnit = (MaterialTextView) rootView.findViewById(R.id.tvPriceUnit);
        this.tvRevenueDate = (MaterialTextView) rootView.findViewById(R.id.tvRevenueDate);
        this.tvDay = (MaterialTextView) rootView.findViewById(R.id.tvDay);
        this.tvTip = (MaterialTextView) rootView.findViewById(R.id.tvTip);
        this.btnPurchase = (MaterialButton) rootView.findViewById(R.id.btnPurchase);
    }

    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
        this.multipleAdapter = multipleAdapter;
        productBean = (ProductBean) multipleAdapter.getBean(position);
        this.tvProductTitle.setText(productBean.getTitle());


        rootView.setOnClickListener(v -> {
            ToastUtil.showToast(multipleAdapter.getFragmentActivity(), productBean.getTitle());
        });

        btnPurchase.setOnClickListener(v -> {
            ToastUtil.showToast(multipleAdapter.getFragmentActivity(), "购买" + productBean.getTitle());
        });
    }
}
