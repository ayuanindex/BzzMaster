package com.shenkong.bzzmaster.ui.fragment.product.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.model.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductBean> productBeans = new ArrayList<>();
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public ProductAdapter(Context context, List<ProductBean> productBeans) {
        this.context = context;
        this.productBeans = productBeans;
    }

    @NonNull
    @io.reactivex.annotations.NonNull
    @Override

    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull @io.reactivex.annotations.NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @io.reactivex.annotations.NonNull ProductAdapter.ViewHolder holder, int position) {
        ProductBean productBean = productBeans.get(position);
        holder.tvProductTitle.setText(productBean.getTitle());

        holder.rootView.setOnClickListener((View v) -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, productBeans.get(position), position);
            }
        });

        holder.llTags.removeAllViews();
        holder.llTags.addView(createTag("头矿红利"));
        holder.llTags.addView(createTag("火爆热销"));
    }

    @Override
    public int getItemCount() {
        return productBeans.size();
    }

    public ProductBean getItemBean(int position) {
        return this.productBeans.get(position);
    }

    // 条目点击接口
    public interface OnItemClickListener {
        void onItemClick(View view, ProductBean productBean, int position);
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
        MaterialTextView materialTextView = new MaterialTextView(context);
        materialTextView.setText(tagTitle);
        materialTextView.setTextSize(12);
        materialTextView.setPadding(2, 2, 2, 2);
        materialTextView.setTextColor(Color.parseColor("#EC7636"));
        materialTextView.setBackgroundResource(R.drawable.xml_tag_background);
        return materialTextView;
    }


    public void addDataList(List<ProductBean> productBeans) {
        this.productBeans.addAll(productBeans);
        notifyDataSetChanged();
    }

    public void updateDataList(List<ProductBean> productBeans) {
        if (!this.productBeans.containsAll(productBeans)) {
            this.productBeans.clear();
            this.productBeans.addAll(productBeans);
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
            this.tvRevenueDate = (MaterialTextView) rootView.findViewById(R.id.tvRevenueDate);
            this.tvDay = (MaterialTextView) rootView.findViewById(R.id.tvDay);
            this.tvTip = (MaterialTextView) rootView.findViewById(R.id.tvTip);
            this.btnPurchase = (MaterialButton) rootView.findViewById(R.id.btnPurchase);
        }

    }
}
