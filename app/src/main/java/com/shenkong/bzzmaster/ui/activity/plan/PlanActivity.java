package com.shenkong.bzzmaster.ui.activity.plan;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.ItemPlanNormalBinding;
import com.shenkong.bzzmaster.databinding.ItemPlanPledgeBinding;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.activity.plan.holder.PlanNormalViewHolder;
import com.shenkong.bzzmaster.ui.activity.plan.holder.PlanPledgeViewHolder;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

import java.util.List;

public class PlanActivity extends BaseMvpActivity<PlanPresenter> implements PlanEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private androidx.appcompat.widget.LinearLayoutCompat llTop;
    private com.google.android.material.tabs.TabLayout tabSwitchProduct;
    private androidx.recyclerview.widget.RecyclerView rcMyPlan;
    private MultiLayoutAdapter multiLayoutAdapter;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout refreshLayout;
    private androidx.appcompat.widget.AppCompatImageView ivEmptyView;
    private androidx.core.widget.ContentLoadingProgressBar progressLoadingData;
    private List<ProductBean> productBeanList = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_plan;
    }

    @Override
    protected void initView() {
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tabSwitchProduct = findViewById(R.id.tabSwitchProduct);
        refreshLayout = findViewById(R.id.refreshLayout);
        rcMyPlan = findViewById(R.id.rcMyPlan);
        ivEmptyView = findViewById(R.id.ivEmptyView);
        progressLoadingData = findViewById(R.id.progressLoadingData);

        rcMyPlan.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setColorSchemeResources(
                R.color.blue_primary,
                R.color.red_primary,
                R.color.orange_primary,
                R.color.blue_primary,
                R.color.green_primary,
                R.color.red_primary
        );
    }

    @Override
    protected void initEvent() {
        // 返回按钮
        ivArrowBack.setOnClickListener(v -> finish());

        // 刷新控件
        refreshLayout.setOnRefreshListener(() -> {
            // 请求数据
            mPresenter.requestAssets();
        });
    }

    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);
        initDataSubscribe();

        multiLayoutAdapter = new MultiLayoutAdapter() {
            @NonNull
            @Override
            public MultipleLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                switch (viewType) {
                    case Types.PLEDGE:
                        return new PlanPledgeViewHolder(ItemPlanPledgeBinding.inflate(getLayoutInflater(), parent, false), PlanActivity.this);
                    case Types.NORMAL:
                    default:
                        return new PlanNormalViewHolder(ItemPlanNormalBinding.inflate(getLayoutInflater(), parent, false));
                }
            }
        };
        rcMyPlan.setAdapter(multiLayoutAdapter);

        showLoading();
        mPresenter.requestAssets();
    }

    private void initDataSubscribe() {
        mPresenter.getBooleanLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    mPresenter.requestAssets();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {
        progressLoadingData.setVisibility(View.VISIBLE);
        progressLoadingData.show();
    }

    @Override
    public void hideLoading() {
        progressLoadingData.hide();
        ivEmptyView.setVisibility(View.GONE);
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void showEmptyView() {
        ivEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updatePlanList(List<MultiLayoutAdapter.LayoutType> date) {
        multiLayoutAdapter.updateData(date);
        hideLoading();
    }
}
