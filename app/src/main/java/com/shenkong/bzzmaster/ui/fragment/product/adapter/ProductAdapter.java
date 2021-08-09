package com.shenkong.bzzmaster.ui.fragment.product.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.activity.submitOrder.ordinary.SubmitOrderActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private static final String TAG = "ProductAdapter";
    private List<ProductPlanBean> productPlanBeans = new ArrayList<>();
    private final FragmentActivity fragmentActivity;
    private OnItemClickListener onItemClickListener;

    public ProductAdapter(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        LoggerUtils.d(TAG, "执行了我");
        View inflate = LayoutInflater.from(fragmentActivity).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(inflate);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductPlanBean productPlanBean = productPlanBeans.get(position);

        holder.tvProductTitle.setText(productPlanBean.getName());

        holder.llTags.removeAllViews();
        for (String tag : productPlanBean.getTag().split(",")) {
            holder.llTags.addView(createTag(tag));
        }

        holder.tvPrice.setText(String.valueOf(productPlanBean.getPrice()));
        holder.tvPriceUnit.setText(productPlanBean.getCurrency());
        holder.tvMinimumSale.setText(productPlanBean.getMincompany() + "TiB起售");
        holder.tvRevenueDate.setText(productPlanBean.getRuntime() + "天");
        holder.tvDay.setText(productPlanBean.getPacktime() + "天");

        holder.rootView.setOnClickListener((View v) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, productPlanBeans.get(position), position);
            }
        });

        holder.btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedBean.remove(SharedBean.ProductPlanBean);
                SharedBean.putData(SharedBean.ProductPlanBean, productPlanBean);
                Intent intent = new Intent(fragmentActivity, SubmitOrderActivity.class);
                fragmentActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productPlanBeans.size();
    }

    public ProductPlanBean getItemBean(int position) {
        return this.productPlanBeans.get(position);
    }

    // 条目点击接口
    public interface OnItemClickListener {
        void onItemClick(View view, ProductPlanBean productPlanBean, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 添加标签
     *
     * @param tagTitle 标签文字
     * @return 返回标签控件
     */
    private MaterialTextView createTag(String tagTitle) {
        MaterialTextView materialTextView = new MaterialTextView(fragmentActivity);
        materialTextView.setText(tagTitle);
        materialTextView.setTextSize(12);
        materialTextView.setPadding(2, 2, 2, 2);
        materialTextView.setTextColor(Color.parseColor("#EC7636"));
        materialTextView.setBackgroundResource(R.drawable.xml_tag_background);
        return materialTextView;
    }


    public void addDataList(List<ProductPlanBean> productBeans) {
        this.productPlanBeans.addAll(productBeans);
        notifyDataSetChanged();
    }

    public void updateDataList(List<ProductPlanBean> productPlanBeans) {
        if (!this.productPlanBeans.containsAll(productPlanBeans) || productPlanBeans.isEmpty()) {
            this.productPlanBeans.clear();
            this.productPlanBeans.addAll(productPlanBeans);
            notifyDataSetChanged();
        }
    }

    public static
    class ViewHolder extends RecyclerView.ViewHolder {
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

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
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

    }
}
