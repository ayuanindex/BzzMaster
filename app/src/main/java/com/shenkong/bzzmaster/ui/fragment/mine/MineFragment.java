package com.shenkong.bzzmaster.ui.fragment.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.activity.contact.ContactActivity;
import com.shenkong.bzzmaster.ui.activity.orders.OrderActivity;
import com.shenkong.bzzmaster.ui.activity.receive.ReceivePaymentActivity;
import com.shenkong.bzzmaster.ui.activity.settings.SettingsActivity;
import com.shenkong.bzzmaster.ui.activity.transfer.TransferActivity;
import com.shenkong.bzzmaster.ui.base.BaseFragment;

import java.util.List;

public class MineFragment extends BaseFragment<MineViewModel, MineEvent> implements MineEvent {
    public static MineFragment mineFragment;
    private ShapeableImageView ivHeadPortrait;
    private MaterialTextView tvUserPhone;
    private MaterialButton btnCollection;
    private MaterialButton btnTransferAccounts;
    private MaterialTextView tvUSDTBalance;
    private MaterialTextView tvXCHBalance;
    private MaterialTextView tvMyOrder;
    private MaterialTextView tvRevenueAndExpenditure;
    private MaterialTextView tvContactUs;
    private MaterialTextView tvSetting;
    private ConstraintLayout clWalletCard;
    private MaterialTextView tvUSDTAvailable;
    private MaterialTextView tvXCHAvailable;
    private MaterialCardView materialCardView;
    private MaterialCardView materialCardView2;

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
        tvUSDTBalance = inflate.findViewById(R.id.tvUSDTBalance);
        tvXCHBalance = inflate.findViewById(R.id.tvXCHBalance);
        tvMyOrder = inflate.findViewById(R.id.tvMyOrder);
        tvRevenueAndExpenditure = inflate.findViewById(R.id.tvRevenueAndExpenditure);
        tvContactUs = inflate.findViewById(R.id.tvContactUs);
        tvSetting = inflate.findViewById(R.id.tvSetting);
        clWalletCard = inflate.findViewById(R.id.clWalletCard);
        tvUSDTAvailable = inflate.findViewById(R.id.tvUSDTAvailable);
        tvXCHAvailable = inflate.findViewById(R.id.tvXCHAvailable);
        materialCardView = inflate.findViewById(R.id.materialCardView);
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
        customerViewModel.setProductBeanListLiveData(new MutableLiveData<>());
        customerViewModel.getProductBeanListLiveData().observe(this, productBeanList -> customerViewModel.requestAllBalance(productBeanList));

        customerViewModel.setCapitalBeanListLiveData(new MutableLiveData<>());
        customerViewModel.getCapitalBeanListLiveData().observe(this, capitalBeans -> {
            tvUSDTAvailable.setText(capitalBeans.get(1).getName() + "可用余额");
            tvUSDTBalance.setText(Formatter.numberFormat(capitalBeans.get(1).getBalance()) + "");

            tvXCHAvailable.setText(capitalBeans.get(0).getName() + "可用余额");
            tvXCHBalance.setText(Formatter.numberFormat(capitalBeans.get(0).getBalance()) + "");
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
