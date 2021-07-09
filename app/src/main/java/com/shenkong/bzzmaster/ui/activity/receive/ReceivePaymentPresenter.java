package com.shenkong.bzzmaster.ui.activity.receive;

import android.graphics.Bitmap;

import com.king.zxing.util.CodeUtils;
import com.shenkong.bzzmaster.model.presenter.BasePresenter;

public class ReceivePaymentPresenter extends BasePresenter<ReceivePaymentEvent> {
    public void createQRCodeBitmap(String content, int size) {
        Bitmap qrCodeBitmap = CodeUtils.createQRCode(content, size);
        mView.setQrCodeToView(qrCodeBitmap);
    }
}
