package com.shenkong.bzzmaster.ui.fragment.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.databinding.ItemCurrencyBalanceBinding;
import com.shenkong.bzzmaster.model.bean.CapitalBean;

import java.util.ArrayList;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.ViewHolder> {
    private final List<CapitalBean> capitalBeanList = new ArrayList<>();
    private Context context;

    public WalletAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public WalletAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LogUtils.d("test", "创建");
        return new ViewHolder(ItemCurrencyBalanceBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WalletAdapter.ViewHolder holder, int position) {
        CapitalBean capitalBean = capitalBeanList.get(position);

        holder.itemCurrencyBalanceBinding.tvCurrencyName.setText(capitalBean.getName());
        holder.itemCurrencyBalanceBinding.tvCurrency.setText(capitalBean.getCurrency());
        holder.itemCurrencyBalanceBinding.tvAvailable.setText(Formatter.numberFormat(capitalBean.getBalance()));
    }

    @Override
    public int getItemCount() {
        return capitalBeanList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ItemCurrencyBalanceBinding itemCurrencyBalanceBinding;

        public ViewHolder(@NonNull ItemCurrencyBalanceBinding itemCurrencyBalanceBinding) {
            super(itemCurrencyBalanceBinding.getRoot());
            this.itemCurrencyBalanceBinding = itemCurrencyBalanceBinding;
        }
    }

    public void resetData(List<CapitalBean> capitalBeanList) {
        this.capitalBeanList.clear();
        this.capitalBeanList.addAll(capitalBeanList);
        notifyDataSetChanged();
    }
}
