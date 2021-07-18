package com.shenkong.bzzmaster.ui.fragment.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.config.ExternalLinks;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.DialogBottomWalletBinding;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.ui.activity.contact.ContactActivity;
import com.shenkong.bzzmaster.ui.activity.orders.OrderActivity;
import com.shenkong.bzzmaster.ui.activity.receive.ReceivePaymentActivity;
import com.shenkong.bzzmaster.ui.activity.settings.SettingsActivity;
import com.shenkong.bzzmaster.ui.activity.transfer.TransferActivity;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.mine.adapter.WalletAdapter;

public class MineFragment extends BaseFragment<MineViewModel, MineEvent> implements MineEvent {
    public static MineFragment mineFragment;
    private ShapeableImageView ivHeadPortrait;
    private MaterialTextView tvUserPhone;
    private MaterialButton btnCollection;
    private MaterialButton btnTransferAccounts;
    private MaterialTextView tvMyOrder;
    private MaterialTextView tvRevenueAndExpenditure;
    private MaterialTextView tvContactUs;
    private MaterialTextView tvSetting;
    private ConstraintLayout clWalletCard;
    private MaterialTextView tvUSDTAvailable;
    private MaterialTextView tvUSDTAvailableBalance;
    private MaterialButton btnSeeMore;
    private WalletAdapter walletAdapter = null;
    private MaterialTextView tvShareAndDownload;

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            synchronized (MineFragment.class) {
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                }
            }
        }
        return mineFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View inflate) {
        ivHeadPortrait = inflate.findViewById(R.id.ivHeadPortrait);
        tvUserPhone = inflate.findViewById(R.id.tvUserPhone);
        btnCollection = inflate.findViewById(R.id.btnCollection);
        btnTransferAccounts = inflate.findViewById(R.id.btnTransferAccounts);
        clWalletCard = inflate.findViewById(R.id.clWalletCard);
        tvUSDTAvailable = inflate.findViewById(R.id.tvUSDTAvailable);
        tvUSDTAvailableBalance = inflate.findViewById(R.id.tvUSDTAvailableBalance);
        btnSeeMore = inflate.findViewById(R.id.btnSeeMore);
        tvMyOrder = inflate.findViewById(R.id.tvMyOrder);
        tvRevenueAndExpenditure = inflate.findViewById(R.id.tvRevenueAndExpenditure);
        tvContactUs = inflate.findViewById(R.id.tvContactUs);
        tvSetting = inflate.findViewById(R.id.tvSetting);
        tvShareAndDownload = (MaterialTextView) inflate.findViewById(R.id.tvShareAndDownload);
    }

    @Override
    protected void initEvent() {
        // 收款
        btnCollection.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ReceivePaymentActivity.class);
            startActivity(intent);
        });

        // 转账
        btnTransferAccounts.setOnClickListener(v -> startActivity(new Intent(getContext(), TransferActivity.class)));

        // 钱包卡片
        clWalletCard.setOnClickListener(v -> {
            showBalanceBottomSheetDialog();
        });

        // 查看更多
        btnSeeMore.setOnClickListener(v -> showBalanceBottomSheetDialog());

        // 我的订单
        tvMyOrder.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), OrderActivity.class);
            intent.putExtra("type", 0);
            startActivity(intent);
        });

        // 收支明细
        tvRevenueAndExpenditure.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), OrderActivity.class);
            intent.putExtra("type", 1);
            startActivity(intent);
        });

        // 联系我们
        tvContactUs.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ContactActivity.class));
        });

        // 设置
        tvSetting.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), SettingsActivity.class));
        });

        // 分享链接按钮
        tvShareAndDownload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, ExternalLinks.APP_DOWNLOAD_LINK);
            startActivity(Intent.createChooser(intent, "下载链接"));
        });
    }

    private void showBalanceBottomSheetDialog() {
        customerViewModel.requestAllProduct();
        if (customerViewModel.getCapitalBeanListLiveData().getValue() != null && customerViewModel.getCapitalBeanListLiveData().getValue().size() > 0) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.bottomSheetDialog);
            DialogBottomWalletBinding walletBinding = DialogBottomWalletBinding.inflate(getLayoutInflater());
            bottomSheetDialog.setContentView(walletBinding.getRoot());

            View parent = (View) walletBinding.getRoot().getParent();
            BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
            walletBinding.getRoot().measure(0, 0);
            behavior.setPeekHeight(walletBinding.getRoot().getMeasuredHeight());
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            walletBinding.recyclerWallet.setLayoutManager(new LinearLayoutManager(getContext()));
            walletAdapter = new WalletAdapter(getContext());
            walletBinding.recyclerWallet.setAdapter(walletAdapter);
            walletAdapter.resetData(customerViewModel.getCapitalBeanListLiveData().getValue());
            bottomSheetDialog.show();
        }
    }

    @Override
    protected void initData() {
        initViewModel(MineViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);
        customerViewModel.setLifecycleProvider(this);

        initDataSubscribe();

        // 从Sp中获取用户登录信息
        String phone = SpUtil.getString(getContext(), SpUtil.phone, "");
        tvUserPhone.setText(phone.replace(phone.substring(3), "********"));
    }

    @SuppressLint("SetTextI18n")
    private void initDataSubscribe() {
        // 所有产品的列表
        customerViewModel.setProductBeanListLiveData(new MutableLiveData<>());
        customerViewModel.getProductBeanListLiveData().observe(this, productBeanList -> customerViewModel.requestAllBalance(productBeanList));

        customerViewModel.setCapitalBeanListLiveData(new MutableLiveData<>());
        customerViewModel.getCapitalBeanListLiveData().observe(this, capitalBeans -> {
            for (CapitalBean capitalBean : capitalBeans) {
                if ("usdt".equals(capitalBean.getCurrency().toLowerCase())) {
                    tvUSDTAvailable.setText(capitalBean.getCurrency() + "可用余额");
                    tvUSDTAvailableBalance.setText(Formatter.numberFormat(capitalBean.getBalance()));
                    break;
                }
            }

            if (walletAdapter != null) {
                walletAdapter.resetData(capitalBeans);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(getContext(), msg);
    }

    @Override
    public void onResume() {
        super.onResume();
        customerViewModel.requestAllProduct();
    }
}
