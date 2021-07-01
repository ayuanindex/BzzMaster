package com.shenkong.bzzmaster.ui.fragment.mine;

import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.SpUtil;
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
        // 我的订单
        tvMyOrder.setOnClickListener(v -> {

        });

        // 收支明细
        tvRevenueAndExpenditure.setOnClickListener(v -> {

        });

        // 联系我们
        tvContactUs.setOnClickListener(v -> {

        });

        // 设置
        tvSetting.setOnClickListener(v -> {

        });
    }

    @Override
    protected void initData() {
        initViewModel(MineViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);

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

    }
}
