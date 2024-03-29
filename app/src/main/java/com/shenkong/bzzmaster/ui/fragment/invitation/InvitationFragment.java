package com.shenkong.bzzmaster.ui.fragment.invitation;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.Utils;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.utils.SpUtil;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.base.BaseFragment;
import com.shenkong.bzzmaster.ui.fragment.product.adapter.ProductAdapter;

import java.util.ArrayList;

public class InvitationFragment extends BaseFragment<InvitationViewModel, InvitationEvent> implements InvitationEvent {
    public static InvitationFragment invitationFragment;
    private SwipeRefreshLayout refreshLayout;
    private MaterialTextView tvInvitationLink;
    private MaterialTextView tvInvitationCode;
    private ClipboardManager clipboardManager;
    private RecyclerView rcInvitationRecord;

    public static InvitationFragment getInstance() {
        if (invitationFragment == null) {
            synchronized (InvitationFragment.class) {
                if (invitationFragment == null) {
                    invitationFragment = new InvitationFragment();
                }
            }
        }
        return invitationFragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_invitation;
    }

    @Override
    protected void initView(View inflate) {
        refreshLayout = inflate.findViewById(R.id.refreshLayout);
        tvInvitationLink = inflate.findViewById(R.id.tvInvitationLink);
        tvInvitationCode = inflate.findViewById(R.id.tvInvitationCode);
        rcInvitationRecord = (RecyclerView) inflate.findViewById(R.id.rcInvitationRecord);

        rcInvitationRecord.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initEvent() {
        refreshLayout.setOnRefreshListener(() -> uiHandler.postDelayed(() -> refreshLayout.setRefreshing(false), 1000));

        tvInvitationLink.setOnClickListener(v -> {
            ToastUtil.showToast(getContext(), "链接复制成功");
            clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("共享链接", tvInvitationLink.getText().toString().trim())));
        });

        tvInvitationCode.setOnClickListener(v -> {
            ToastUtil.showToast(getContext(), "邀请码复制成功");
            clipboardManager.setPrimaryClip(new ClipData(ClipData.newPlainText("邀请码", tvInvitationCode.getText().toString().trim())));
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        initViewModel(InvitationViewModel.class);
        customerViewModel.setUiRefreshCallBack(this);

        // 获取剪切板管理器
        clipboardManager = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        // 回填邀请码
        tvInvitationCode.setText(SpUtil.getString(Utils.getApp(), SpUtil.code, "1234"));
        tvInvitationLink.setText("https://shenkonghongyuan.com:10080/?code=" + SpUtil.getString(Utils.getApp(), SpUtil.code, ""));

        //https://shenkonghongyuan.com:10080/?code=75102489
        /*ProductAdapter adapter = new ProductAdapter(getContext());
        rcInvitationRecord.setAdapter(adapter);
        ArrayList<ProductPlanBean> productBeans = new ArrayList<>();
        adapter.addDataList(productBeans);*/
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
}
