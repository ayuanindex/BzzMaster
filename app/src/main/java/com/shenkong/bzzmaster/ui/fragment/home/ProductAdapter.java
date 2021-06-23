package com.shenkong.bzzmaster.ui.fragment.home;

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

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> productList;
    private OnClickListener<ViewHolder> onRootViewClickListener;
    private OnClickListener<ViewHolder> onBtnPurchaseClickListener;

    public ProductAdapter(Context context, ArrayList<String> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_hotproduct, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        holder.tvProductTitle.setText(productList.get(position));

        holder.llTags.removeAllViews();
        holder.llTags.addView(createTag("头矿红利"));
        holder.llTags.addView(createTag("火爆热销"));

        holder.rootView.setOnClickListener(v -> {
            if (onRootViewClickListener != null) {
                onRootViewClickListener.onClick(v, holder, position);
            }
        });

        holder.btnPurchase.setOnClickListener(v -> {
            if (onBtnPurchaseClickListener != null) {
                onBtnPurchaseClickListener.onClick(v, holder, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setProductList(ArrayList<String> productList) {
        this.productList = productList;
    }

    /**
     * 创建子控件
     *
     * @param tagTitle tag的标题
     * @return 返回创建好的tag标签
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

    public interface OnClickListener<T extends RecyclerView.ViewHolder> {
        void onClick(View v, T holder, int position);
    }

    public void setOnRootViewClickListener(OnClickListener<ViewHolder> onRootViewClickListener) {
        this.onRootViewClickListener = onRootViewClickListener;
    }

    public void setOnBtnPurchaseClickListener(OnClickListener<ViewHolder> onBtnPurchaseClickListener) {
        this.onBtnPurchaseClickListener = onBtnPurchaseClickListener;
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


