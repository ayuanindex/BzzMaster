package com.shenkong.bzzmaster.ui.fragment.wallet;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.ItemWalletCurrencyBalanceBinding;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.activity.currencyinfo.CurrencyInfoActivity;
import com.shenkong.bzzmaster.ui.activity.transfer.TransferActivity;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;
import com.shenkong.bzzmaster.ui.fragment.wallet.viewholder.BalanceHolder;

import java.util.ArrayList;

public class WalletFragment extends BaseFragment<WalletViewModel, WalletEvent> implements WalletEvent {
    private static WalletFragment walletFragment;
    private RecyclerView rcCurrencyBalance;
    private MultiLayoutAdapter multiLayoutAdapter;
    private boolean operationIsShow = true;
    private SwipeRefreshLayout refreshLayout;

    public static Fragment getInstance() {
        if (walletFragment == null) {
            synchronized (WalletFragment.class) {
                if (walletFragment == null) {
                    walletFragment = new WalletFragment();
                }
            }
        }
        return walletFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_wallet;
    }

    @Override
    protected void initView(View inflate) {
        refreshLayout = inflate.findViewById(R.id.refreshLayout);
        rcCurrencyBalance = inflate.findViewById(R.id.rcCurrencyBalance);

        refreshLayout.setColorSchemeResources(
                R.color.blue_primary,
                R.color.red_primary,
                R.color.orange_primary,
                R.color.blue_primary,
                R.color.green_primary,
                R.color.red_primary
        );

        rcCurrencyBalance.setLayoutManager(new LinearLayoutManager(getContext()));
        rcCurrencyBalance.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 0;
                outRect.bottom = 45;

                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = 45;
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                customerViewModel.requestAllBalance();
            }
        });
    }

    @Override
    protected void initData() {
        initViewModel(WalletViewModel.class);
        customerViewModel.bind(this);

        multiLayoutAdapter = new MultiLayoutAdapter() {
            @NonNull
            @Override
            public MultipleLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new BalanceHolder(ItemWalletCurrencyBalanceBinding.inflate(getLayoutInflater(), parent, false), WalletFragment.this);
            }
        };
        rcCurrencyBalance.setAdapter(multiLayoutAdapter);
    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(requireContext(), msg);
    }

    @Override
    public void setRecyclerViewData(ArrayList<MultiLayoutAdapter.LayoutType> layoutTypes) {
        multiLayoutAdapter.updateData(layoutTypes);
        refreshLayout.setRefreshing(false);
    }

    /**
     * 跳转到币种详情界面
     * @param productBean 币种信息
     */
    @Override
    public void startCurrencyInfoActivity(ProductBean productBean) {
        SharedBean.putData(SharedBean.Product, productBean);
        startActivity(new Intent(getContext(), CurrencyInfoActivity.class));
    }

    /**
     * 跳转到提现界œ面
     * @param productBean 币种信息
     */
    private void startTransferActivity(ProductBean productBean) {
        SharedBean.putData(SharedBean.Product, productBean);
        Intent intent = new Intent(getContext(), TransferActivity.class);
        intent.putExtra("isSelect", 1);
        startActivity(intent);
    }

    @Override
    public void showBottomSheetDialog(ProductBean productBean) {
/*
        if (operationIsShow) {
            operationIsShow = false;
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.bottomSheetDialog);
            DialogBottomOperationBinding operationBinding = DialogBottomOperationBinding.inflate(getLayoutInflater());
            bottomSheetDialog.setContentView(operationBinding.getRoot());

            View parent = (View) operationBinding.getRoot().getParent();
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
            operationBinding.getRoot().measure(0, 0);
            behavior.setPeekHeight(operationBinding.getRoot().getMeasuredHeight());
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            // 设置标题
            operationBinding.tvTitle.setText(productBean.getCurrency().toUpperCase());

            // 判断币种是否为USDT,USDT暂不可提现
            if ("USDT".equals(productBean.getCurrency().toUpperCase())) {
                operationBinding.btnWithdrawal.setEnabled(false);
                operationBinding.btnWithdrawal.setIconTintResource(R.color.gray_light);
            }

            // 判断币种数据是否存在 || 判断选择的币种是否有地址，没有地址不可充值
            if (productBean.getCapitalBean() == null || TextUtils.isEmpty(productBean.getCapitalBean().getAdress())) {
                operationBinding.btnRecharge.setEnabled(false);
                operationBinding.btnRecharge.setIconTintResource(R.color.gray_light);
                operationBinding.tvTitle.setText(productBean.getCurrency().toUpperCase());
            }

            // 充值按钮
            operationBinding.btnRecharge.setOnClickListener(v -> {
                startCurrencyInfoActivity(productBean);
            });

            // 提现按钮
            operationBinding.btnWithdrawal.setOnClickListener(v -> startTransferActivity(productBean));
            // 对话框关闭监听
            bottomSheetDialog.setOnDismissListener(dialog -> operationIsShow = true);
            bottomSheetDialog.show();
        }
*/
    }

    @Override
    public void onResume() {
        super.onResume();
        showLoading();
        customerViewModel.requestAllBalance();
    }
}
