package com.shenkong.bzzmaster.ui.activity.receive;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.view.View;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class ReceivePaymentActivity extends BaseMvpActivity<ReceivePaymentPresenter> implements ReceivePaymentEvent {
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private com.google.android.material.textview.MaterialTextView tvNameOfCurrency;
    private androidx.appcompat.widget.AppCompatImageView ivQrCode;
    private com.google.android.material.textview.MaterialTextView tvCollectionAddress;
    private com.google.android.material.textview.MaterialTextView tvCopyAddress;
    private com.google.android.material.textview.MaterialTextView tvTransferTips;
    private ClipboardManager clipboardManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receivepayment;
    }

    @Override
    protected void initView() {
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvNameOfCurrency = findViewById(R.id.tvNameOfCurrency);
        ivQrCode = findViewById(R.id.ivQrCode);
        tvCollectionAddress = findViewById(R.id.tvCollectionAddress);
        tvCopyAddress = findViewById(R.id.tvCopyAddress);
        tvTransferTips = findViewById(R.id.tvTransferTips);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        tvCopyAddress.setOnClickListener(v -> {
            clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("address", tvCollectionAddress.getText().toString().trim())));
            ToastUtil.showToast(this, "地址复制成功");
        });
    }

    @Override
    protected void initData() {
        // 获取剪贴板管理器
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        mPresenter.createQRCodeBitmap(tvCollectionAddress.getText().toString().trim(), 200);
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

    @Override
    public void setQrCodeToView(Bitmap qrCodeBitmap) {
        ivQrCode.setImageBitmap(qrCodeBitmap);
    }
}
