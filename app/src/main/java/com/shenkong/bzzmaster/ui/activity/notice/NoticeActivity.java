package com.shenkong.bzzmaster.ui.activity.notice;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.databinding.ItemNoticeBinding;
import com.shenkong.bzzmaster.model.bean.NoticeBean;
import com.shenkong.bzzmaster.ui.activity.web.WebViewActivity;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;

public class NoticeActivity extends BaseMvpActivity<NoticePresenter> implements NoticeEvent {
    private android.widget.RelativeLayout titleLayout;
    private androidx.appcompat.widget.AppCompatImageView ivArrowBack;
    private com.google.android.material.textview.MaterialTextView tvTitle;
    private androidx.appcompat.widget.AppCompatImageView ivEmptyView;
    private androidx.recyclerview.widget.RecyclerView recyclerNotice;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout refreshLayout;
    private NoticeAdapter noticeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        ivEmptyView = findViewById(R.id.ivEmptyView);
        recyclerNotice = findViewById(R.id.recyclerNotice);
        refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setColorSchemeResources(
                R.color.blue_primary,
                R.color.red_primary,
                R.color.orange_primary,
                R.color.blue_primary,
                R.color.green_primary,
                R.color.red_primary
        );

        recyclerNotice.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.requestNotice();
        });
    }

    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);
        initDataSubscribe();

        noticeAdapter = new NoticeAdapter();
        recyclerNotice.setAdapter(noticeAdapter);

        mPresenter.requestNotice();
    }

    private void initDataSubscribe() {
        mPresenter.setNoticeListLiveData(new MutableLiveData<>());
        mPresenter.getNoticeListLiveData().observe(this, noticeBeans -> {
            if (noticeBeans.size() > 0) {
                ivEmptyView.setVisibility(View.GONE);
            } else {
                ivEmptyView.setVisibility(View.VISIBLE);
            }
            noticeAdapter.resetData(noticeBeans);
        });
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
    public void refreshLayoutCancel(boolean b) {
        refreshLayout.setRefreshing(b);
    }

    class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {
        private List<NoticeBean> dataList = new ArrayList<>();

        @NonNull
        @Override
        public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemNoticeBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {
            NoticeBean noticeBean = dataList.get(position);
            holder.itemNoticeBinding.tvNoticeTitle.setText(noticeBean.getTitle());
            holder.itemNoticeBinding.tvNoticeMessage.setText(noticeBean.getMessage());
            holder.itemNoticeBinding.tvNoticeCreateTime.setText(noticeBean.getPushtime());

            holder.itemNoticeBinding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(NoticeActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.TITLE, noticeBean.getTitle());
                intent.putExtra(WebViewActivity.URL, noticeBean.getLink());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void resetData(List<NoticeBean> datas) {
            this.dataList.clear();
            this.dataList.addAll(datas);
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ItemNoticeBinding itemNoticeBinding;

            public ViewHolder(@NonNull ItemNoticeBinding itemNoticeBinding) {
                super(itemNoticeBinding.getRoot());
                this.itemNoticeBinding = itemNoticeBinding;
            }
        }

    }

}
