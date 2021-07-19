package com.shenkong.bzzmaster.ui.activity.start;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.ActivityStartBinding;
import com.shenkong.bzzmaster.model.bean.User;
import com.shenkong.bzzmaster.net.NetManager;
import com.shenkong.bzzmaster.net.ObjectLoader;
import com.shenkong.bzzmaster.net.api.UserService;
import com.shenkong.bzzmaster.ui.activity.login.LoginActivity;
import com.shenkong.bzzmaster.ui.activity.main.MainActivity;
import com.shenkong.bzzmaster.ui.base.RxAppCompatActivity;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class StartPagerActivity extends RxAppCompatActivity {
    private static final String TAG = "StartPagerActivity";
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

        boolean loginStatus = SpUtil.getBoolean(StartPagerActivity.this, SpUtil.loginStatus, false);
        // 如果已经是登录状态，检查token是否过期
        if (loginStatus) {
            loginCheck();
        } else {
            startLogin();
        }
    }

    private void loginCheck() {
        binding.tvLoginTip.setVisibility(View.VISIBLE);
        binding.tvLoginTip.setText("登录验证中请稍后");
        // 进行token国旗和登录验证
        Disposable start = ObjectLoader.observeat(NetManager.getInstance().getRetrofit().create(UserService.class).requestDetail(), this)
                .subscribe(new Consumer<ResultBean<User>>() {
                    @Override
                    public void accept(ResultBean<User> userResultBean) throws Exception {
                        LoggerUtils.d(TAG, "请求登录" + userResultBean.toString());
                        uiHandler.postDelayed(() -> {
                            // 检查是否已经登录
                            boolean loginStatus = SpUtil.getBoolean(StartPagerActivity.this, SpUtil.loginStatus, false);
                            if (loginStatus && userResultBean.getCode() == 200) {
                                startActivity(new Intent(StartPagerActivity.this, MainActivity.class));
                            } else if (userResultBean.getCode() == 400) {
                                ToastUtil.showToast(StartPagerActivity.this, "登录过期, 请重新登录!!!");
                                Intent intent = new Intent(StartPagerActivity.this, LoginActivity.class);
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartPagerActivity.this, binding.startIcon, "start").toBundle());
                            }
                        }, 1000);

                        uiHandler.postDelayed(() -> finish(), 2000);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.showToast(StartPagerActivity.this, "登录验证失败, 请重新登录!!!");
                        throwable.printStackTrace();
                        LoggerUtils.d(TAG, "请求出错,进入登录界面", throwable.getMessage());
                        Intent intent = new Intent(StartPagerActivity.this, LoginActivity.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartPagerActivity.this, binding.startIcon, "start").toBundle());
                        uiHandler.postDelayed(() -> finish(), 2000);
                    }
                });
    }

    private void startLogin() {
        uiHandler.postDelayed(() -> {
            Intent intent = new Intent(StartPagerActivity.this, LoginActivity.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartPagerActivity.this, binding.startIcon, "start").toBundle());
        }, 1000);

        uiHandler.postDelayed(this::finish, 2000);
    }

    @Override
    public void onBackPressed() {
    }
}
