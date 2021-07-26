package com.shenkong.bzzmaster.ui.fragment.wallet.viewholder;

import androidx.annotation.NonNull;

import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.databinding.ItemWalletCurrencyBalanceBinding;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;
import com.shenkong.bzzmaster.ui.fragment.wallet.WalletEvent;
import com.squareup.picasso.Picasso;

public class BalanceHolder extends MultiLayoutAdapter.MultipleLayoutViewHolder {
    public ItemWalletCurrencyBalanceBinding itemWalletCurrencyBalanceBinding;
    private final WalletEvent walletEvent;

    public BalanceHolder(@NonNull ItemWalletCurrencyBalanceBinding itemWalletCurrencyBalanceBinding, WalletEvent walletEvent) {
        super(itemWalletCurrencyBalanceBinding.getRoot());
        this.itemWalletCurrencyBalanceBinding = itemWalletCurrencyBalanceBinding;
        this.walletEvent = walletEvent;
    }

    @Override
    public void load(MultiLayoutAdapter multiLayoutAdapter, int position) {
        ProductBean productBean = multiLayoutAdapter.getBean(ProductBean.class, position);

        Picasso.get().load(productBean.getPic())
                .into(itemWalletCurrencyBalanceBinding.ivCurrencyIcon);
        itemWalletCurrencyBalanceBinding.tvCurrencyName.setText(productBean.getCurrency());
        if (productBean.getCapitalBean() != null) {
            itemWalletCurrencyBalanceBinding.tvAvailableBalance.setText(Formatter.numberFormat(productBean.getCapitalBean().getBalance()));
        } else {
            itemWalletCurrencyBalanceBinding.tvAvailableBalance.setText("0.0");
        }

        itemWalletCurrencyBalanceBinding.getRoot().setOnClickListener(null);
        itemWalletCurrencyBalanceBinding.getRoot().setOnClickListener(v -> {
            // 跳转到币种详情界面
            walletEvent.startCurrencyInfoActivity(productBean);
        });
    }
}
