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
import androidx.lifecycle.ViewModelProvider;

import com.shenkong.bzzmaster.event.BaseEven;

public abstract class BaseFragment<M extends BaseViewMode<E>, E extends BaseEven> extends Fragment {
    public final String TAG = this.getClass().getSimpleName();
    public Handler uiHandler = new Handler(Looper.getMainLooper());
    public M customerViewModel;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutRes(), container, false);
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

    public void initViewModel(Class<M> mClass) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication());
        customerViewModel = new ViewModelProvider(this, factory).get(mClass);
    }
}
