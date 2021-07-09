package com.shenkong.bzzmaster.ui.activity.receive;

import android.graphics.Bitmap;

import com.shenkong.bzzmaster.event.BaseEven;

public interface ReceivePaymentEvent extends BaseEven {
    void setQrCodeToView(Bitmap qrCodeBitmap);
}
