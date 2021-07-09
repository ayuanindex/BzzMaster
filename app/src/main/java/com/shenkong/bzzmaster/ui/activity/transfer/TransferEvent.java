package com.shenkong.bzzmaster.ui.activity.transfer;

import com.shenkong.bzzmaster.event.BaseEven;

public interface TransferEvent extends BaseEven {
    void showConfirmDialog(String address, double doubleAmountOfMoney);
}
