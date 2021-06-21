package com.shenkong.bzzmaster.ui.base;

import android.content.Context;
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
    public Context context;
    public static Handler uiHandler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutRes(), null, true);
        initView(inflate);
        return inflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        initEvent();
        initData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    protected abstract @LayoutRes
    int getLayoutRes();

    protected abstract void initView(View inflate);

    protected abstract void initEvent();

    protected abstract void initData();
}
