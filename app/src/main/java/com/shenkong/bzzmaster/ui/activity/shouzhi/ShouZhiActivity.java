package com.shenkong.bzzmaster.ui.activity.shouzhi;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.config.ConstantPool;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.ItemCountBinding;
import com.shenkong.bzzmaster.model.bean.DetailBean;
import com.shenkong.bzzmaster.ui.activity.shouzhi.adapter.DetailItemAdapter;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 充值、提币、收益
 */
public class ShouZhiActivity extends BaseMvpActivity<ShouZhiPresent> implements ShouZhiEvent {
    private AppCompatImageView ivArrowBack;
    private TabLayout tabSwitchDetailType;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutCompat llStatisticsLayout;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView rcShouZhi;
    private ContentLoadingProgressBar progressLoadingData;
    private AppCompatImageView ivEmptyView;
    private boolean isLoadMore = false;
    private boolean isRefresh = false;
    private int tabPosition = 0;
    private DetailItemAdapter detailItemAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_shouzhi;
    }

    @Override
    protected void initView() {
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tabSwitchDetailType = findViewById(R.id.tabSwitchDetailType);
        refreshLayout = findViewById(R.id.refreshLayout);
        llStatisticsLayout = findViewById(R.id.llStatisticsLayout);
        smartRefreshLayout = findViewById(R.id.smartRefreshLayout);
        rcShouZhi = findViewById(R.id.rcShouZhi);
        progressLoadingData = findViewById(R.id.progressLoadingData);
        ivEmptyView = findViewById(R.id.ivEmptyView);

        rcShouZhi.setLayoutManager(new LinearLayoutManager(this));
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(this));
        smartRefreshLayout.setEnableRefresh(false);

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
        ivArrowBack.setOnClickListener(v -> finish());

        tabSwitchDetailType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                showLoading();
                setLoadPage();
                mPresenter.requestShouZhi();
                /*if (mPresenter.getDetailBeanLiveData().getValue() != null) {
                    // 选择给列表重新填充数据
                    DetailBean detailBean = mPresenter.getDetailBeanLiveData().getValue();
                    switchAdapterResetData(detailBean);
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            if (!isLoadMore) {
                isRefresh = true;
                setLoadPage();
                // 请求收支记录
                mPresenter.requestShouZhi();
            }
        });

        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!isRefresh) {
                    isLoadMore = true;
                    setLoadPage();
                    mPresenter.requestShouZhi();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);
        initDataSubscribe();

        detailItemAdapter = new DetailItemAdapter(this);
        rcShouZhi.setAdapter(detailItemAdapter);

        showLoading();
        setLoadPage();
        mPresenter.requestShouZhi();
    }

    /**
     * 设置数据订阅
     */
    private void initDataSubscribe() {
        mPresenter.setDetailBeanLiveData(new MutableLiveData<>());
        mPresenter.getDetailBeanLiveData().observe(this, new Observer<DetailBean>() {
            @Override
            public void onChanged(DetailBean detailBean) {
                loadStatisticsView(detailBean.getStatistics());
                isRefresh = false;
                if (isLoadMore) {
                    isLoadMore = false;
                    switchAdapterAddData(detailBean);
                } else {
                    switchAdapterResetData(detailBean);
                }
                hideLoading();
            }
        });
    }

    /**
     * 加载统计布局的View
     *
     * @param statistics 统计信息
     */
    private void loadStatisticsView(ArrayList<DetailBean.Statistics> statistics) {
        List<DetailBean.Statistics> list;
        switch (tabPosition) {
            case 0:
                // 充值明细
                list = getSelectType(statistics, ConstantPool.Detail_Transaction);
                break;
            case 1:
                // 提币
                list = getSelectType(statistics, ConstantPool.Detail_Apply);
                break;
            case 2:
                // 收益
                list = getSelectType(statistics, ConstantPool.Detail_Revenue);
                break;
            default:
                list = new ArrayList<>();
                break;
        }
        llStatisticsLayout.removeAllViews();
        ItemCountBinding itemCountBinding = ItemCountBinding.inflate(getLayoutInflater(), llStatisticsLayout, false);
        llStatisticsLayout.addView(itemCountBinding.getRoot(), 0);
        for (DetailBean.Statistics bean : list) {
            if (TextUtils.isEmpty(bean.getCurrency())) {
                continue;
            }

            ItemCountBinding inflate = ItemCountBinding.inflate(getLayoutInflater(), llStatisticsLayout, false);
            inflate.tvCurrency.setText(bean.getCurrency().toUpperCase());
            inflate.tvMoney.setText(Formatter.numberFormat(bean.getAmout()));
            inflate.tvCount.setText(bean.getNumber() + "条");
            llStatisticsLayout.addView(inflate.getRoot());
        }
    }

    private List<DetailBean.Statistics> getSelectType(List<DetailBean.Statistics> statisticsList, int type) {
        List<DetailBean.Statistics> list = new ArrayList<>();
        for (DetailBean.Statistics statistics : statisticsList) {
            if (statistics.getType() == type) {
                list.add(statistics);
            }
        }
        return list;
    }

    /**
     * 根据选择的选项卡类型设置当前加载的页
     */
    private void setLoadPage() {
        int page = 1;
        switch (tabPosition) {
            case 0:
                // 充值明细
                mPresenter.setDetailVoType(ConstantPool.Detail_Transaction);
                if (isLoadMore) {
                    page = mPresenter.getTransactionPage() + 1;
                    mPresenter.setTransactionPage(page);
                } else {
                    page = 1;
                    mPresenter.setTransactionPage(page);
                }
                break;
            case 1:
                // 提币
                mPresenter.setDetailVoType(ConstantPool.Detail_Apply);
                if (isLoadMore) {
                    page = mPresenter.getApplyPage() + 1;
                    mPresenter.setApplyPage(page);
                } else {
                    page = 1;
                    mPresenter.setApplyPage(page);
                }
                break;
            case 2:
                // 收益
                mPresenter.setDetailVoType(ConstantPool.Detail_Revenue);
                if (isLoadMore) {
                    page = mPresenter.getRevenuesPage() + 1;
                    mPresenter.setRevenuesPage(page);
                } else {
                    page = 1;
                    mPresenter.setRevenuesPage(page);
                }
                break;
        }
        mPresenter.setPage(page);
    }

    /**
     * 重新设置数据
     */
    private void switchAdapterResetData(DetailBean detailBean) {
        // 根据tab选项卡位置选择数据
        switch (tabPosition) {
            case 0:
                // 充值明细
                setIsEmptyViewStatus(detailBean.getTransactions());
                detailItemAdapter.resetData(detailBean.getTransactions());
                break;
            case 1:
                // 提币
                setIsEmptyViewStatus(detailBean.getApplys());
                detailItemAdapter.resetData(detailBean.getApplys());
                break;
            case 2:
                // 收益
                setIsEmptyViewStatus(detailBean.getRevenues());
                detailItemAdapter.resetData(detailBean.getRevenues());
                break;
        }
    }

    /**
     * 根据选项卡位置选择需要添加的数据
     */
    private void switchAdapterAddData(DetailBean detailBean) {
        // 根据tab选项卡位置选择数据
        switch (tabPosition) {
            case 0:
                // 充值明细
                if (detailBean.getTransactions().size() < 1) {
                    int page = mPresenter.getTransactionPage() - 1;
                    mPresenter.setTransactionPage(page);
                } else {
                    detailItemAdapter.addAllData(detailBean.getTransactions());
                }
                break;
            case 1:
                // 提币
                if (detailBean.getApplys().size() < 1) {
                    int page = mPresenter.getApplyPage() - 1;
                    mPresenter.setApplyPage(page);
                } else {
                    detailItemAdapter.addAllData(detailBean.getApplys());
                }
                break;
            case 2:
                // 收益
                if (detailBean.getRevenues().size() < 1) {
                    int page = mPresenter.getRevenuesPage() - 1;
                    mPresenter.setRevenuesPage(page);
                } else {
                    detailItemAdapter.addAllData(detailBean.getRevenues());
                }
                break;
        }
    }

    public void setIsEmptyViewStatus(List<DetailBean.DetailItem> list) {
        if (list.size() < 1) {
            ivEmptyView.setVisibility(View.VISIBLE);
        } else {
            ivEmptyView.setVisibility(View.GONE);
        }
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
        refreshLayout.setRefreshing(false);
        smartRefreshLayout.finishLoadMore();
    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(this, msg);
    }
}
