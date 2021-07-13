package com.shenkong.bzzmaster.ui.activity.orders.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.databinding.ItemOrderBinding;
import com.shenkong.bzzmaster.model.bean.OrderBean;

import java.util.ArrayList;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        OrderBean orderBean = orderBeanList.get(position);
        holder.itemOrderBinding.tvProductName.setText(orderBean.getPname());
        holder.itemOrderBinding.tvOrderDate.setText(orderBean.getCreatetime());
        holder.itemOrderBinding.tvOrderStatus.setText("已支付");// TODO: 2021/7/11 这里根据status字段判断订单状态
        holder.itemOrderBinding.tvOrderMoney.setText(Formatter.numberFormat(orderBean.getAmount()));
        holder.itemOrderBinding.tvProductCount.setText(String.valueOf(orderBean.getNumber()));
        holder.itemOrderBinding.tvProductSize.setText(orderBean.getNumber() + "个");
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
