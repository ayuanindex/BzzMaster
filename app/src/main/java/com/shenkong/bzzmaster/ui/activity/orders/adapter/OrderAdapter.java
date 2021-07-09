package com.shenkong.bzzmaster.ui.activity.orders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.databinding.ItemOrderBinding;
import com.shenkong.bzzmaster.model.bean.OrderBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<OrderBean> orderBeanList = new ArrayList<>();

    public OrderAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemOrderBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderBean orderBean = orderBeanList.get(position);
        holder.itemOrderBinding.tvOrderDate.setText(orderBean.getCreatetime().toString());
        holder.itemOrderBinding.tvOrderStatus.setText("待支付");
        holder.itemOrderBinding.tvProductName.setText(orderBean.getMessage());
    }

    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }

    public void resetData(List<OrderBean> orderBeanList) {
        this.orderBeanList.clear();
        this.orderBeanList.addAll(orderBeanList);
        notifyDataSetChanged();
    }

    public void addData(List<OrderBean> orderBeanList) {
        this.orderBeanList.addAll(orderBeanList);
        notifyDataSetChanged();
    }

    public List<OrderBean> getOrderBeanList() {
        return orderBeanList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ItemOrderBinding itemOrderBinding;

        public ViewHolder(@NonNull ItemOrderBinding itemOrderBinding) {
            super(itemOrderBinding.getRoot());
            this.itemOrderBinding = itemOrderBinding;
        }
    }
}
