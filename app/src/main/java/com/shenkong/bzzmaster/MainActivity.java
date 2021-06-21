package com.shenkong.bzzmaster;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {
    private MaterialTextView tvTitle;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 配置bottomNavigationView
        navController = Navigation.findNavController(this, R.id.navigationHostFragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }

    @Override
    protected void initEvent() {
        // 切换title文字
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                tvTitle.setText(destination.getLabel());
            }
        });
    }

    @Override
    protected void initData() {
    }
}