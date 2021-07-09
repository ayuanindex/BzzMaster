package com.shenkong.bzzmaster.ui.activity.transfer;

import android.text.TextUtils;

import com.shenkong.bzzmaster.model.presenter.BasePresenter;

public class TransferPresent extends BasePresenter<TransferEvent> {
    public void confirmTransfer(String address, String amountOfMoney) {
        if (TextUtils.isEmpty(address) || TextUtils.isEmpty(amountOfMoney)) {
            mView.showToastMsg("请输入完整信息", 0);
            return;
        }

        double doubleAmountOfMoney = Double.parseDouble(amountOfMoney);
        if (doubleAmountOfMoney <= 0) {
            mView.showToastMsg("转账金额必须大于0", 0);
        }

        mView.showConfirmDialog(address, doubleAmountOfMoney);
    }

}
