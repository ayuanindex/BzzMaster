package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.activity.productinfo.ProductInfoActivity;
import com.shenkong.bzzmaster.ui.activity.submit.SubmitOrderActivity;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class HomeHotProductViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
    private static final String TAG = "HomeHotProductViewHolde";
    private final FragmentActivity fragmentActivity;
    public View rootView;
    public MaterialTextView tvProductTitle;
    public LinearLayoutCompat llTags;
    public MaterialTextView tvPrice;
    public MaterialTextView tvPriceUnit;
    public MaterialTextView tvMinimumSale;
    public MaterialTextView tvRevenueDate;
    public MaterialTextView tvDay;
    public MaterialTextView tvTip;
    public MaterialButton btnPurchase;
    private MultipleAdapter multipleAdapter;
    private ProductPlanBean productPlanBean;

    public HomeHotProductViewHolder(View rootView, FragmentActivity fragmentActivity) {
        super(rootView);
        this.rootView = rootView;
        this.fragmentActivity = fragmentActivity;
        this.tvProductTitle = (MaterialTextView) rootView.findViewById(R.id.tvProductTitle);
        this.llTags = (LinearLayoutCompat) rootView.findViewById(R.id.llTags);
        this.tvPrice = (MaterialTextView) rootView.findViewById(R.id.tvPrice);
        this.tvPriceUnit = (MaterialTextView) rootView.findViewById(R.id.tvPriceUnit);
        this.tvMinimumSale = (MaterialTextView) rootView.findViewById(R.id.tvMinimumSale);
        this.tvRevenueDate = (MaterialTextView) rootView.findViewById(R.id.tvRevenueDate);
        this.tvDay = (MaterialTextView) rootView.findViewById(R.id.tvDay);
        this.tvTip = (MaterialTextView) rootView.findViewById(R.id.tvTip);
        this.btnPurchase = (MaterialButton) rootView.findViewById(R.id.btnPurchase);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
        this.multipleAdapter = multipleAdapter;
        productPlanBean = (ProductPlanBean) multipleAdapter.getBean(position);

        tvProductTitle.setText(productPlanBean.getName());

        llTags.removeAllViews();
        if (productPlanBean.getTag() != null && !productPlanBean.getTag().isEmpty()) {
            for (String s : productPlanBean.getTag().split(",")) {
                llTags.addView(createTag(s));
            }
        }

        tvPrice.setText(String.valueOf(productPlanBean.getPrice()));
        tvPriceUnit.setText(productPlanBean.getCurrency());
        tvMinimumSale.setText(productPlanBean.getMincompany() + "TiB起售");
        tvRevenueDate.setText(productPlanBean.getRuntime() + "天");
        tvDay.setText(productPlanBean.getPacktime() + "天");

        rootView.setOnClickListener(null);
        rootView.setOnClickListener(v -> {
            SharedBean.putData(SharedBean.ProductPlanBean, productPlanBean);
            Intent intent = new Intent(fragmentActivity, ProductInfoActivity.class);
            fragmentActivity.startActivity(intent);
        });

        btnPurchase.setOnClickListener(null);
        btnPurchase.setOnClickListener(v -> {
            SharedBean.putData(SharedBean.ProductPlanBean, productPlanBean);
            Intent intent = new Intent(fragmentActivity, SubmitOrderActivity.class);
            fragmentActivity.startActivity(intent);
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
