package com.shenkong.bzzmaster.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.shenkong.bzzmaster.common.utils.LoggerUtils;

public class LogOutBroadCast extends BroadcastReceiver {
    private static final String TAG = "LogOutBroadCast";
    private CustomerCallBack customerCallBack;

    @Override
    public void onReceive(Context context, Intent intent) {
        LoggerUtils.d(TAG, "收到了广播");
        customerCallBack.finish();
    }

    public void setCallBack(CustomerCallBack customerCallBack) {
        this.customerCallBack = customerCallBack;
    }

    public interface CustomerCallBack {
        void finish();
    }
}
