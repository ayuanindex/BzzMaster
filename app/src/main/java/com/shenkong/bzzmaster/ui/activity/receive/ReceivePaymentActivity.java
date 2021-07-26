package com.shenkong.bzzmaster.ui.activity.receive;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

public class ReceivePaymentActivity extends BaseMvpActivity<ReceivePaymentPresenter> implements ReceivePaymentEvent {
    private AppCompatImageView ivArrowBack;
    private MaterialTextView tvTitle;
    private MaterialTextView tvNameOfCurrency;
    private ShapeableImageView ivQrCode;
    private MaterialTextView tvCollectionAddress;
    private MaterialTextView tvCopyAddress;
    private MaterialTextView tvTransferTips;
    private ClipboardManager clipboardManager;
    private ContentLoadingProgressBar progressLoading;

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
        progressLoading = findViewById(R.id.progressLoading);
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
        mPresenter.setLifecycleProvider(this);
        initDataSubscribe();

        // 获取剪贴板管理器
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        ProductBean productBean = SharedBean.getData(ProductBean.class, SharedBean.Product);

        switch (productBean.getCurrency().toUpperCase()) {
            case "FIL":
                tvNameOfCurrency.setText(R.string.FIL_Filecoin_Network);
                tvTransferTips.setText(R.string.FIL_TransferTips);
                break;
            case "USDT":
                tvNameOfCurrency.setText(R.string.USDT_TRC20_Network);
                tvTransferTips.setText(R.string.USDT_TransferTips);
            default:
                break;
        }

        progressLoading.hide();
        tvCollectionAddress.setVisibility(View.VISIBLE);
        tvCopyAddress.setVisibility(View.VISIBLE);
        tvCollectionAddress.setText(productBean.getCapitalBean().getAdress());
        mPresenter.createQRCodeBitmap(productBean.getCapitalBean().getAdress(), 200, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round));
        // mPresenter.requestBalance();
    }

    private void initDataSubscribe() {
        mPresenter.setCapitalBeanListLiveData(new MutableLiveData<>());
        mPresenter.getCapitalBeanListLiveData().observe(this, capitalBeans -> {
            progressLoading.hide();
            tvCollectionAddress.setVisibility(View.VISIBLE);
            tvCopyAddress.setVisibility(View.VISIBLE);
            tvCollectionAddress.setText(capitalBeans.get(0).getAdress());
            mPresenter.createQRCodeBitmap(capitalBeans.get(0).getAdress(), 200, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round));
        });
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
        ivQrCode.setVisibility(View.VISIBLE);
    }
}
