package com.shenkong.bzzmaster.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.shenkong.bzzmaster.R;

public class AlertDialogUtil {
    public static AlertDialog getAlertDialog(Context context, View inflate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(inflate);

        if (alertDialog.getWindow() != null) {
            Window window = alertDialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setWindowAnimations(R.style.dialogAnimation);
            window.setGravity(Gravity.CENTER);
        }
        return alertDialog;
    }
}
