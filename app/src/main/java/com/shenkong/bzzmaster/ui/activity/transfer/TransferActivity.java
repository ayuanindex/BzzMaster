package com.shenkong.bzzmaster.ui.activity.transfer;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.AlertDialogUtil;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.DialogConfirmBinding;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class TransferActivity extends BaseMvpActivity<TransferPresent> implements TransferEvent {

    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private com.google.android.material.card.MaterialCardView cardSelectCurrency;
    private androidx.appcompat.widget.AppCompatSpinner spCurrency;
    private androidx.appcompat.widget.LinearLayoutCompat llBalanceContent;
    private com.google.android.material.textview.MaterialTextView tvBalance;
    private com.google.android.material.textfield.TextInputLayout inputAddressLayout;
    private com.google.android.material.textfield.TextInputEditText inputAddress;
    private com.google.android.material.textfield.TextInputLayout inputAmountOfMoneyLayout;
    private com.google.android.material.textfield.TextInputEditText inputAmountOfMoney;
    private androidx.appcompat.widget.LinearLayoutCompat tvTipLayout;
    private com.google.android.material.textview.MaterialTextView tvWaringTip;
    private com.google.android.material.button.MaterialButton btnConfirmTransfer;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        cardSelectCurrency = findViewById(R.id.cardSelectCurrency);
        spCurrency = findViewById(R.id.spCurrency);
        llBalanceContent = findViewById(R.id.llBalanceContent);
        tvBalance = findViewById(R.id.tvBalance);
        inputAddressLayout = findViewById(R.id.inputAddressLayout);
        inputAddress = findViewById(R.id.inputAddress);
        inputAmountOfMoneyLayout = findViewById(R.id.inputAmountOfMoneyLayout);
        inputAmountOfMoney = findViewById(R.id.inputAmountOfMoney);
        tvTipLayout = findViewById(R.id.tvTipLayout);
        tvWaringTip = findViewById(R.id.tvWaringTip);
        btnConfirmTransfer = findViewById(R.id.btnConfirmTransfer);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        btnConfirmTransfer.setOnClickListener(v -> {
            mPresenter.confirmTransfer(inputAddress.getEditableText().toString().trim(), inputAmountOfMoney.getEditableText().toString().trim());
        });
    }

    @Override
    protected void initData() {

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

    @SuppressLint("SetTextI18n")
    @Override
    public void showConfirmDialog(String address, double doubleAmountOfMoney) {
        DialogConfirmBinding confirmBinding = DialogConfirmBinding.inflate(getLayoutInflater());
        AlertDialog alertDialog = AlertDialogUtil.getAlertDialog(this, confirmBinding.getRoot());
        alertDialog.setCancelable(false);
        confirmBinding.tvAddress.setText("目标收款地址:" + address);
        confirmBinding.tvBalance.setText("转账金额:" + doubleAmountOfMoney);
        confirmBinding.btnCancel.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }
}
