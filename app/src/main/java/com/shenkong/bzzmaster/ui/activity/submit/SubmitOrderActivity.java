package com.shenkong.bzzmaster.ui.activity.submit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.AlertDialogUtil;
import com.shenkong.bzzmaster.common.utils.CurrencyUtil;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.DialogSubmitOrderBinding;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.activity.receive.ReceivePaymentActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.List;

public class SubmitOrderActivity extends BaseMvpActivity<SubmitOrderPresenter> implements SubmitOrderEvent {
    private RelativeLayout titleLayout;
    private AppCompatImageView ivArrowBack;
    private MaterialTextView tvTitle;
    private MaterialTextView tvProductName;
    private MaterialTextView tvProductPriceTip;
    private MaterialTextView tvProductPrice;
    private MaterialTextView tvNeedCountTip;
    private LinearLayoutCompat llNeedCountLayout;
    private MaterialButton btnReduce;
    private AppCompatEditText etNeedCount;
    private MaterialButton btnAdd;
    private MaterialTextView tvOrderAmountTip;
    private MaterialTextView tvOrderAmount;
    private MaterialTextView tvWalletBalanceTip;
    private MaterialTextView tvWalletBalance;
    private MaterialButton btnRechargeImmediately;
    private MaterialButton btnSubmitOrder;

    private long count = 1;
    private long minCount = 0;
    private ProductPlanBean productPlanBean;
    private androidx.core.widget.ContentLoadingProgressBar progress;

    @Override
    public int getLayoutId() {
        return R.layout.activity_submit_order;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPriceTip = findViewById(R.id.tvProductPriceTip);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvNeedCountTip = findViewById(R.id.tvNeedCountTip);
        llNeedCountLayout = findViewById(R.id.llNeedCountLayout);
        btnReduce = findViewById(R.id.btnReduce);
        etNeedCount = findViewById(R.id.etNeedCount);
        btnAdd = findViewById(R.id.btnAdd);
        tvOrderAmountTip = findViewById(R.id.tvOrderAmountTip);
        tvOrderAmount = findViewById(R.id.tvOrderAmount);
        tvWalletBalanceTip = findViewById(R.id.tvWalletBalanceTip);
        tvWalletBalance = findViewById(R.id.tvWalletBalance);
        btnRechargeImmediately = findViewById(R.id.btnRechargeImmediately);
        btnSubmitOrder = findViewById(R.id.btnSubmitOrder);
        progress = findViewById(R.id.progress);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        btnAdd.setOnClickListener(v -> etNeedCount.setText(String.valueOf(++count)));

        btnReduce.setOnClickListener(v -> {
            if (count > minCount) {
                etNeedCount.setText(String.valueOf(--count));
            }
        });

        etNeedCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 修改需要支付的金额
                LoggerUtils.d(TAG, s.toString());
                if (!TextUtils.isEmpty(s.toString())) {
                    count = Integer.parseInt(s.toString());
                    tvOrderAmount.setText(Formatter.numberFormat(count * productPlanBean.getPrice()));
                } else {
                    tvOrderAmount.setText(Formatter.numberFormat(0));
                }
            }
        });

        btnRechargeImmediately.setOnClickListener(v -> startActivity(new Intent(this, ReceivePaymentActivity.class)));

        btnSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count >= minCount) {
                    showSubmitDialog();
                } else {
                    ToastUtil.showToast(SubmitOrderActivity.this, "购买数量少于最低购买数量");
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);
        initDataSubscribe();

        productPlanBean = (ProductPlanBean) SharedBean.getData(SharedBean.ProductPlanBean);
        count = minCount = productPlanBean.getMincompany();

        tvProductName.setText(productPlanBean.getName());
        tvProductPrice.setText("1份=" + minCount + CurrencyUtil.getUnit(productPlanBean.getCurrency()) + "=" + Formatter.numberFormat(productPlanBean.getPrice() * minCount) + "USDT");
        tvOrderAmount.setText(Formatter.numberFormat(count * productPlanBean.getPrice()));

        etNeedCount.setText(String.valueOf(count));

        mPresenter.selectUSDTBalance();
    }

    private void initDataSubscribe() {
        mPresenter.setCapitalBeanListLiveData(new MutableLiveData<>());
        mPresenter.getCapitalBeanListLiveData().observe(this, new Observer<List<CapitalBean>>() {
            @Override
            public void onChanged(List<CapitalBean> capitalBeans) {
                setBalanceText(capitalBeans.get(0));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showSubmitDialog() {
        CapitalBean capitalBean = mPresenter.getCapitalBean();
        if (capitalBean == null) {
            ToastUtil.showToast(this, "正在加载钱包可用余额");
            return;
        }

        // 计算所需金额
        double needPrice = productPlanBean.getPrice() * count;

        DialogSubmitOrderBinding submitOrderBinding = DialogSubmitOrderBinding.inflate(getLayoutInflater());
        AlertDialog alertDialog = AlertDialogUtil.getAlertDialog(this, submitOrderBinding.getRoot());
        alertDialog.setCancelable(false);

        if (capitalBean.getBalance() < needPrice) {
            submitOrderBinding.tvPriceDifference.setText("余额不足，还差" + Formatter.numberFormat(needPrice - capitalBean.getBalance()) + "USDT");
            submitOrderBinding.tvPriceDifference.setVisibility(View.VISIBLE);
            submitOrderBinding.btnPayImmediately.setEnabled(false);
        }

        submitOrderBinding.tvNeedPrice.setText("订单需支付(USDT):" + Formatter.numberFormat(productPlanBean.getPrice() * count));
        submitOrderBinding.tvBalance.setText("账户余额(USDT):" + Formatter.numberFormat(capitalBean.getBalance()));
        submitOrderBinding.btnCancel.setOnClickListener(v -> alertDialog.dismiss());
        submitOrderBinding.btnPayImmediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                progress.show();
                mPresenter.requestAddOrder(productPlanBean, needPrice, count);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void setBalanceText(CapitalBean capitalBean) {
        tvWalletBalance.setText(Formatter.numberFormat(capitalBean.getBalance()));
    }

    @Override
    public void setAddOrderStatus(boolean date) {
        progress.hide();
        mPresenter.selectUSDTBalance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.selectUSDTBalance();
    }
}
