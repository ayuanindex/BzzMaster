package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class HomeHotProductViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
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

    public HomeHotProductViewHolder(View rootView) {
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
        this.tvProductTitle.setText(productBean.getName());

        llTags.removeAllViews();
        llTags.addView(createTag("头矿红利"));
        llTags.addView(createTag("火爆热销"));

        rootView.setOnClickListener(null);
        rootView.setOnClickListener(v -> {
            ToastUtil.showToast(multipleAdapter.getFragmentActivity(), productBean.getName());
        });

        btnPurchase.setOnClickListener(null);
        btnPurchase.setOnClickListener(v -> {
            ToastUtil.showToast(multipleAdapter.getFragmentActivity(), "购买" + productBean.getName());
        });
    }

    /**
     * 添加标签
     *
     * @param tagTitle 标签文字
     * @return 返回标签控件
     */
    private MaterialTextView createTag(String tagTitle) {
        MaterialTextView materialTextView = new MaterialTextView(multipleAdapter.getFragmentActivity());
        materialTextView.setText(tagTitle);
        materialTextView.setTextSize(12);
        materialTextView.setPadding(2, 2, 2, 2);
        materialTextView.setTextColor(Color.parseColor("#EC7636"));
        materialTextView.setBackgroundResource(R.drawable.xml_tag_background);
        return materialTextView;
    }
}
