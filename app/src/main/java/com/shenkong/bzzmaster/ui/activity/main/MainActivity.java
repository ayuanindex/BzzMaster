package com.shenkong.bzzmaster.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.king.app.updater.AppUpdater;
import com.king.app.updater.callback.AppUpdateCallback;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.broadcast.LogOutBroadCast;
import com.shenkong.bzzmaster.common.config.ExternalLinks;
import com.shenkong.bzzmaster.common.utils.AlertDialogUtil;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.common.utils.download.AppFileProvider;
import com.shenkong.bzzmaster.common.utils.download.IntentUtil;
import com.shenkong.bzzmaster.databinding.DialogUpdateAppBinding;
import com.shenkong.bzzmaster.model.bean.AppUpdateBean;
import com.shenkong.bzzmaster.ui.activity.notice.NoticeActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainEvent {
    private MaterialTextView tvTitle;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2Replace;
    private long mExitTime;
    private AppCompatImageView ivNoticeIcon;
    private MaterialTextView tvNoticeCount;
    private LinearLayoutCompat llNoticeLayout;
    private boolean downloadSuccess = false;
    private File apkFile;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2Replace = findViewById(R.id.viewPager2Replace);
        tvNoticeCount = findViewById(R.id.tvNoticeCount);
        ivNoticeIcon = findViewById(R.id.ivNoticeIcon);
        llNoticeLayout = findViewById(R.id.llNoticeLayout);

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
                llNoticeLayout.setVisibility(View.GONE);
                int currentItemPosition = 0;
                switch (item.getItemId()) {
                    case R.id.home:
                        llNoticeLayout.setVisibility(View.VISIBLE);
                        mPresenter.requestNotice();
                        currentItemPosition = 0;
                        break;
                    case R.id.product:
                        currentItemPosition = 1;
                        break;
/*
                    case R.id.invitation:
                        currentItemPosition = 2;
                        break;
*/
                    case R.id.mine:
                        currentItemPosition = 2;
                        break;
                }
                viewPager2Replace.setCurrentItem(currentItemPosition, false);
            }
            return true;
        });

        llNoticeLayout.setOnClickListener(v -> {
            // TODO: 2021/7/12 公告界面
            jumpActivity(NoticeActivity.class);
        });
    }

    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);
        initDataSubscribe();

        // 请求公告信息
        mPresenter.requestNotice();

        // 检查app更新
        // mPresenter.checkAppWhetherUpdate(ApkVersionInfoUtil.getVersionCode(this));
        showUpdateDialog(new AppUpdateBean());

        mPresenter.initViewPager();

        LogOutBroadCast receiver = new LogOutBroadCast();
        receiver.setCallBack(this::finish);
        registerReceiver(receiver, new IntentFilter("logOut"));
    }

    private void initDataSubscribe() {
        mPresenter.setNoticeListLiveData(new MutableLiveData<>());
        mPresenter.getNoticeListLiveData().observe(this, noticeBeans -> {
            tvNoticeCount.setVisibility(View.VISIBLE);
            tvNoticeCount.setText(String.valueOf(noticeBeans.size()));
        });
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
        apkFile = null;
        downloadSuccess = false;
        DialogUpdateAppBinding updateAppBinding = DialogUpdateAppBinding.inflate(getLayoutInflater());
        AlertDialog alertDialog = AlertDialogUtil.getAlertDialog(this, updateAppBinding.getRoot());
        alertDialog.setCancelable(false);

        updateAppBinding.tvUpdateTip.setText(appUpdateBean.getMessage());
        updateAppBinding.btnNextTime.setOnClickListener(v -> alertDialog.dismiss());

        AppUpdater appUpdater = new AppUpdater.Builder()
                .setInstallApk(true)
                .setFilename("BzzMaster.apk")
                .setUrl(ExternalLinks.APP_DOWNLOAD_LINK)
                .setAuthority(AppFileProvider.AUTHORITY)
                .build(this);

        appUpdater.setUpdateCallback(new AppUpdateCallback() {
            private int previousProgress;

            @Override
            public void onStart(String url) {
                super.onStart(url);
                previousProgress = 0;
                updateAppBinding.btnNextTime.setEnabled(false);
                updateAppBinding.btnUpdate.setEnabled(false);
                updateAppBinding.tvDownloadProgress.setVisibility(View.VISIBLE);
                updateAppBinding.downloadProgress.setVisibility(View.VISIBLE);
                updateAppBinding.downloadProgress.setProgress(0);
            }

            @Override
            public void onProgress(long progress, long total, boolean isChange) {
                int currentProgress = (int) (progress / (total * 0.01));
                if (currentProgress > previousProgress) {
                    previousProgress = currentProgress;
                    updateAppBinding.tvDownloadProgress.setText("下载进度" + currentProgress + "%");
                    updateAppBinding.downloadProgress.setProgress(currentProgress);
                }
            }

            @Override
            public void onFinish(File file) {
                apkFile = file;
                downloadSuccess = true;
                updateAppBinding.btnUpdate.setEnabled(true);
                updateAppBinding.tvDownloadProgress.setText("下载完成");
                updateAppBinding.btnUpdate.setText("立即安装");
                updateAppBinding.btnNextTime.setEnabled(false);
                LoggerUtils.d(TAG, "下载完成" + file.getAbsolutePath());
            }

            @Override
            public void onError(Exception e) {
                super.onError(e);
                e.printStackTrace();
                LoggerUtils.d(TAG, "下载出错", e.getMessage());
                downloadSuccess = false;
                ToastUtil.showToast(MainActivity.this, "下载出错");
                updateAppBinding.btnUpdate.setEnabled(true);
                updateAppBinding.btnUpdate.setText("重新下载");
            }

            @Override
            public void onCancel() {
                super.onCancel();
                LoggerUtils.d(TAG, "下载取消");
            }
        });

        updateAppBinding.btnUpdate.setOnClickListener(v -> {
            if (downloadSuccess) {
                IntentUtil.installApk(this, apkFile.getPath(), AppFileProvider.AUTHORITY);
            } else {
                appUpdater.start();
            }
        });

        alertDialog.show();
    }
}