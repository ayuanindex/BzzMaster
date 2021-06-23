package com.shenkong.bzzmaster;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

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

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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

    @Override
    public void onBackPressed() {
        // 回到主屏幕
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}