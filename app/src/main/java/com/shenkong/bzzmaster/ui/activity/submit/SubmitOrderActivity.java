package com.shenkong.bzzmaster.ui.activity.submit;

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

import com.blankj.utilcode.util.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.AlertDialogUtil;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.DialogSubmitOrderBinding;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.activity.receive.ReceivePaymentActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SubmitOrderActivity extends BaseMvpActivity<SubmitOrderPresenter> {
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

    private int count = 1;
    private int minCount = 0;
    private ProductPlanBean productPlanBean;

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
                // TODO: 2021/7/9 进行余额和最小购买数量判断 并决定是否需要弹出
                if (count >= minCount) {
                    showSubmitDialog();
                } else {
                    ToastUtil.showToast(SubmitOrderActivity.this, "购买数量少于最低购买数量");
                }
            }
        });
    }

    @Override
    protected void initData() {
        productPlanBean = (ProductPlanBean) SharedBean.getData(SharedBean.ProductPlanBean);
        count = minCount = productPlanBean.getMincompany();

        tvProductName.setText(productPlanBean.getName());
        tvProductPrice.setText(Formatter.numberFormat(productPlanBean.getPrice()));
        tvOrderAmount.setText(Formatter.numberFormat(count * productPlanBean.getPrice()));

        etNeedCount.setText(String.valueOf(count));
    }

    private void showSubmitDialog() {
        DialogSubmitOrderBinding submitOrderBinding = DialogSubmitOrderBinding.inflate(getLayoutInflater());
        AlertDialog alertDialog = AlertDialogUtil.getAlertDialog(this, submitOrderBinding.getRoot());
        submitOrderBinding.btnCancel.setOnClickListener(v -> alertDialog.dismiss());
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

    }
}
