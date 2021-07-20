package com.shenkong.bzzmaster.ui.activity.shouzhi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.databinding.ItemShouzhiBinding;
import com.shenkong.bzzmaster.model.bean.DetailBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DetailItemAdapter extends RecyclerView.Adapter<DetailItemAdapter.ViewHolder> {
    private final Context context;
    private final List<DetailBean.DetailItem> detailItemList = new ArrayList<>();

    public DetailItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DetailItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemShouzhiBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DetailItemAdapter.ViewHolder holder, int position) {
        DetailBean.DetailItem detailItem = this.detailItemList.get(position);

        holder.itemShouzhiBinding.tvDetailDate.setText(detailItem.getCreatetime());
        holder.itemShouzhiBinding.tvDetailName.setText(detailItem.getName());
        holder.itemShouzhiBinding.tvCurrency.setText(detailItem.getCurrency().toUpperCase());
        holder.itemShouzhiBinding.tvDetailMoney.setText(Formatter.numberFormat(detailItem.getAmout()));
        if (TextUtils.isEmpty(detailItem.getAdress())) {
            holder.itemShouzhiBinding.llAddressLayout.setVisibility(View.GONE);
        } else {
            holder.itemShouzhiBinding.llAddressLayout.setVisibility(View.VISIBLE);
            holder.itemShouzhiBinding.tvDeatilAddress.setText(detailItem.getAdress());
        }
    }

    @Override
    public int getItemCount() {
        return detailItemList.size();
    }

    public void addAllData(List<DetailBean.DetailItem> detailItemList) {
        this.detailItemList.addAll(detailItemList);
        notifyDataSetChanged();
    }

    public void resetData(List<DetailBean.DetailItem> detailItemList) {
        this.detailItemList.clear();
        this.detailItemList.addAll(detailItemList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemShouzhiBinding itemShouzhiBinding;

        public ViewHolder(@NonNull ItemShouzhiBinding itemShouzhiBinding) {
            super(itemShouzhiBinding.getRoot());
            this.itemShouzhiBinding = itemShouzhiBinding;
        }
    }
}
