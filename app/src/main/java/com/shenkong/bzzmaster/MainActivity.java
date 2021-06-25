package com.shenkong.bzzmaster;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.ui.base.BaseActivity;
import com.shenkong.bzzmaster.ui.fragment.home.HomeFragment;
import com.shenkong.bzzmaster.ui.fragment.invitation.InvitationFragment;
import com.shenkong.bzzmaster.ui.fragment.mine.MineFragment;
import com.shenkong.bzzmaster.ui.fragment.product.ProductFragment;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {
    private MaterialTextView tvTitle;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager2Replace;
    private static final ArrayList<Fragment> fragments = new ArrayList<>();
    private boolean single = true;
    private long mExitTime;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEven() {

    }

    @Override
    protected void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        viewPager2Replace = findViewById(R.id.viewPager2Replace);
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
        fragments.clear();
        fragments.add(HomeFragment.getInstance());
        fragments.add(ProductFragment.getInstance());
        fragments.add(InvitationFragment.getInstance());
        fragments.add(MineFragment.getInstance());

        viewPager2Replace.setUserInputEnabled(false);
        viewPager2Replace.setOffscreenPageLimit(4);

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

//    @Override
//    public void onBackPressed() {
//        // 回到主屏幕
//        Intent home = new Intent(Intent.ACTION_MAIN);
//        home.addCategory(Intent.CATEGORY_HOME);
//        startActivity(home);
//    }
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
            AppUtils.exitApp();
        }
    }
}