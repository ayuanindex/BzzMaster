package com.shenkong.bzzmaster.ui.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public final String TAG = this.getClass().getSimpleName();
    public static Handler uiHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutRes(), null, false);
        initView(inflate);
        initEvent();
        initData();
        return inflate;
    }

    protected abstract @LayoutRes
    int getLayoutRes();

    protected abstract void initView(View inflate);

    protected abstract void initEvent();

    protected abstract void initData();
}
