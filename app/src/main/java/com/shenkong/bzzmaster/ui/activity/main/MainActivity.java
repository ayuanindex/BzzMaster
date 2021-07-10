package com.shenkong.bzzmaster.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.broadcast.LogOutBroadCast;
import com.shenkong.bzzmaster.common.base.ResultBean;
import com.shenkong.bzzmaster.common.utils.AlertDialogUtil;
import com.shenkong.bzzmaster.common.utils.ApkVersionInfoUtil;
import com.shenkong.bzzmaster.databinding.DialogUpdateAppBinding;
import com.shenkong.bzzmaster.model.bean.AppUpdateBean;
import com.shenkong.bzzmaster.ui.activity.login.LoginActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.ArrayList;


public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainEvent {
    private MaterialTextView tvTitle;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2Replace;
    private long mExitTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2Replace = findViewById(R.id.viewPager2Replace);

        // 设置ViewPager2预加载和禁止滑动
        viewPager2Replace.setUserInputEnabled(false);
        viewPager2Replace.setOffscreenPageLimit(4);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void initEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            tvTitle.setText(item.getTitle());
            if (viewPager2Replace.getAdapter() != null) {
                int currentItemPosition = 0;
                switch (item.getItemId()) {
                    case R.id.home:
                        currentItemPosition = 0;
                        break;
                    case R.id.product:
                        currentItemPosition = 1;
                        break;
                    case R.id.invitation:
                        currentItemPosition = 2;
                        break;
                    case R.id.mine:
                        currentItemPosition = 3;
                        break;
                }
                viewPager2Replace.setCurrentItem(currentItemPosition, false);
            }
            return true;
        });
    }

    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);

        mPresenter.checkAppWhetherUpdate(ApkVersionInfoUtil.getVersionCode(this));

        mPresenter.initViewPager();

        LogOutBroadCast receiver = new LogOutBroadCast();
        receiver.setCallBack(this::finish);
        registerReceiver(receiver, new IntentFilter("logOut"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort(R.string.exit_tips);
            mExitTime = System.currentTimeMillis();
        } else {
            onBackPressed();
            uiHandler.postDelayed(AppUtils::exitApp, 1000);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToastMsg(String msg, int type) {

    }

    @Override
    public void updateViewPagerAdapter(ArrayList<Fragment> fragments) {
        viewPager2Replace.setAdapter(new FragmentStateAdapter(this) {
            @Override
            public int getItemCount() {
                return fragments.size();
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }
        });
    }

    /**
     * app更新提示弹窗
     */
    @Override
    public void showUpdateDialog(AppUpdateBean appUpdateBean) {
        DialogUpdateAppBinding updateAppBinding = DialogUpdateAppBinding.inflate(getLayoutInflater());
        AlertDialog alertDialog = AlertDialogUtil.getAlertDialog(this, updateAppBinding.getRoot());

        updateAppBinding.tvUpdateTip.setText(appUpdateBean.getMessage());
        updateAppBinding.btnNextTime.setOnClickListener(v -> alertDialog.dismiss());

        updateAppBinding.btnUpdate.setOnClickListener(v -> {
            // 跳转到浏览器下载
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(appUpdateBean.getDownloadurl());
            intent.setData(content_url);
            startActivity(intent);
            alertDialog.dismiss();
        });
        alertDialog.show();
    }
}