package com.shenkong.bzzmaster.ui.activity.start;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.databinding.ActivityStartBinding;
import com.shenkong.bzzmaster.ui.activity.login.LoginActivity;
import com.shenkong.bzzmaster.ui.activity.main.MainActivity;

public class StartPagerActivity extends AppCompatActivity {

    private ActivityStartBinding binding;
    private Handler uiHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startTimer();
    }

    private void startTimer() {
        uiHandler = new Handler(getMainLooper());
        uiHandler.postDelayed(() -> {
            // 检查是否已经登录
            boolean loginStatus = SpUtil.getBoolean(this, SpUtil.loginStatus, false);
            if (loginStatus) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, binding.startIcon, "start").toBundle());
            }
        }, 1000);

        uiHandler.postDelayed(this::finish, 2000);
    }

    @Override
    public void onBackPressed() {
    }
}
