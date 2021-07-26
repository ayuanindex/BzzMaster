package com.shenkong.bzzmaster.ui.activity.currencyinfo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.config.ConstantPool;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.DetailBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.activity.receive.ReceivePaymentActivity;
import com.shenkong.bzzmaster.ui.activity.shouzhi.adapter.DetailItemAdapter;
import com.shenkong.bzzmaster.ui.activity.transfer.TransferActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;
import com.squareup.picasso.Picasso;

public class CurrencyInfoActivity extends BaseMvpActivity<CurrencyInfoPresenter> implements CurrencyInfoEvent {

    private RelativeLayout titleLayout;
    private AppCompatImageView ivArrowBack;
    private MaterialTextView tvTitle;
    private AppBarLayout appbar;
    private ShapeableImageView ivCurrencyIcon;
    private MaterialTextView tvBalance;
    private MaterialButton btnCopyAddress;
    private MaterialButton btnRecharge;
    private MaterialButton btnWithdrawal;
    private MaterialTextView tvTopTip;
    private RecyclerView rcRechargeRecord;
    private ClipboardManager clipboardManager;
    private ProductBean productBean;
    private AppCompatImageView ivEmptyView;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout refreshLayout;
    private com.google.android.material.tabs.TabLayout tabSwitchDetailType;
    private DetailItemAdapter detailItemAdapter;
    private int type = ConstantPool.Detail_Transaction;

    @Override
    public int getLayoutId() {
        return R.layout.activity_receivepay;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        appbar = findViewById(R.id.appbar);
        ivCurrencyIcon = findViewById(R.id.ivCurrencyIcon);
        tvBalance = findViewById(R.id.tvBalance);
        btnCopyAddress = findViewById(R.id.btnCopyAddress);
        btnRecharge = findViewById(R.id.btnRecharge);
        btnWithdrawal = findViewById(R.id.btnWithdrawal);
        tvTopTip = findViewById(R.id.tvTopTip);
        rcRechargeRecord = findViewById(R.id.rcRechargeRecord);
        ivEmptyView = findViewById(R.id.ivEmptyView);
        refreshLayout = findViewById(R.id.refreshLayout);
        tabSwitchDetailType = findViewById(R.id.tabSwitchDetailType);

        rcRechargeRecord.setLayoutManager(new LinearLayoutManager(this));

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

        // 充值
        btnRecharge.setOnClickListener(v -> {
            SharedBean.putData(SharedBean.Product, productBean);
            jumpActivity(ReceivePaymentActivity.class);
        });

        // 提币
        btnWithdrawal.setOnClickListener(v -> {
            SharedBean.putData(SharedBean.Product, productBean);
            Intent intent = new Intent(this, TransferActivity.class);
            intent.putExtra("isSelect", 1);
            startActivity(intent);
        });

        // 拷贝地址
        btnCopyAddress.setOnClickListener(v -> {
            clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("address", btnCopyAddress.getText().toString().trim())));
            showToastMsg("地址复制成功", 0);
        });

        tabSwitchDetailType.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (productBean != null) {
                    switch (tabSwitchDetailType.getSelectedTabPosition()) {
                        case 0:
                            type = ConstantPool.Detail_Transaction;
                            mPresenter.requestProductShouZhiDetail(productBean.getProductid(), ConstantPool.Detail_Transaction);
                            break;
                        case 1:
                            type = ConstantPool.Detail_Apply;
                            mPresenter.requestProductShouZhiDetail(productBean.getProductid(), ConstantPool.Detail_Apply);
                            break;
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.requestProductShouZhiDetail(productBean.getProductid(), type);
        });
    }

    @Override
    protected void initData() {
        mPresenter.bind(this);

        // 获取剪贴板管理器
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        productBean = SharedBean.getData(ProductBean.class, SharedBean.Product);

        tvTitle.setText(productBean.getCurrency().toUpperCase());
        btnCopyAddress.setText(productBean.getCapitalBean().getAdress());
        tvBalance.setText(Formatter.numberFormat(productBean.getCapitalBean().getBalance()));

        Picasso.get().load(productBean.getPic()).into(ivCurrencyIcon);

        if ("USDT".equals(productBean.getCurrency().toUpperCase())) {
            btnWithdrawal.setEnabled(false);
        }

        if (TextUtils.isEmpty(productBean.getCapitalBean().getAdress())) {
            btnCopyAddress.setVisibility(View.GONE);
            btnRecharge.setEnabled(false);
        }

        detailItemAdapter = new DetailItemAdapter(this);
        rcRechargeRecord.setAdapter(detailItemAdapter);

        showLoading();
        mPresenter.requestProductShouZhiDetail(productBean.getProductid(), type);
    }

    @Override
    public void updateRecycerViewDatas(DetailBean detailBeans) {
        if (type == ConstantPool.Detail_Transaction) {
            if (detailBeans.getTransactions().size() <= 0) {
                ivEmptyView.setVisibility(View.VISIBLE);
            } else {
                ivEmptyView.setVisibility(View.GONE);
            }
            detailItemAdapter.resetData(detailBeans.getTransactions());
        } else if (type == ConstantPool.Detail_Apply) {
            if (detailBeans.getApplys().size() <= 0) {
                ivEmptyView.setVisibility(View.VISIBLE);
            } else {
                ivEmptyView.setVisibility(View.GONE);
            }
            detailItemAdapter.resetData(detailBeans.getApplys());
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showToastMsg(String msg, int type) {
        ToastUtil.showToast(this, msg);
    }
}
