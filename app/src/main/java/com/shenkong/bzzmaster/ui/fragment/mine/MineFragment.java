package com.shenkong.bzzmaster.ui.fragment.mine;

import android.content.Intent;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.ui.activity.transfer.TransferActivity;
import com.shenkong.bzzmaster.ui.activity.contact.ContactActivity;
import com.shenkong.bzzmaster.ui.activity.orders.OrderActivity;
import com.shenkong.bzzmaster.ui.activity.receive.ReceivePaymentActivity;
import com.shenkong.bzzmaster.ui.activity.settings.SettingsActivity;
import com.shenkong.bzzmaster.ui.base.BaseFragment;

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
        ivHeadPortrait = (ShapeableImageView) inflate.findViewById(R.id.ivHeadPortrait);
        tvUserPhone = (MaterialTextView) inflate.findViewById(R.id.tvUserPhone);
        btnCollection = (MaterialButton) inflate.findViewById(R.id.btnCollection);
        btnTransferAccounts = (MaterialButton) inflate.findViewById(R.id.btnTransferAccounts);
        tvUSDTBalance = (MaterialTextView) inflate.findViewById(R.id.tvUSDTBalance);
        tvXCHBalance = (MaterialTextView) inflate.findViewById(R.id.tvXCHBalance);
        tvMyOrder = (MaterialTextView) inflate.findViewById(R.id.tvMyOrder);
        tvRevenueAndExpenditure = (MaterialTextView) inflate.findViewById(R.id.tvRevenueAndExpenditure);
        tvContactUs = (MaterialTextView) inflate.findViewById(R.id.tvContactUs);
        tvSetting = (MaterialTextView) inflate.findViewById(R.id.tvSetting);
    }

    @Override
    protected void initEvent() {
        btnCollection.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ReceivePaymentActivity.class));
        });

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

        customerViewModel.requestBalance();

        customerViewModel.requestAllBalance();

        // 从Sp中获取用户登录信息
        String phone = SpUtil.getString(getContext(), SpUtil.phone, "");
        tvUserPhone.setText(phone.replace(phone.substring(3), "********"));
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
}
