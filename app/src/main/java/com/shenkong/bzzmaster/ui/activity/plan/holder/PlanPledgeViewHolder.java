package com.shenkong.bzzmaster.ui.activity.plan.holder;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.shenkong.bzzmaster.common.utils.AlertDialogUtil;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.databinding.DialogConfirmCancelBinding;
import com.shenkong.bzzmaster.databinding.ItemPlanPledgeBinding;
import com.shenkong.bzzmaster.model.bean.AssetsBean;
import com.shenkong.bzzmaster.ui.activity.plan.PlanActivity;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

import java.util.Date;

public class PlanPledgeViewHolder extends MultiLayoutAdapter.MultipleLayoutViewHolder {

    private final ItemPlanPledgeBinding itemPlanPledgeBinding;
    private final PlanActivity planActivity;
    private AssetsBean assetsBean;
    private boolean flag;

    public PlanPledgeViewHolder(@NonNull ItemPlanPledgeBinding itemPlanPledgeBinding, PlanActivity planActivity) {
        super(itemPlanPledgeBinding.getRoot());
        this.itemPlanPledgeBinding = itemPlanPledgeBinding;
        this.planActivity = planActivity;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void load(MultiLayoutAdapter multiLayoutAdapter, int position) {
        assetsBean = multiLayoutAdapter.getBean(AssetsBean.class, position);

        itemPlanPledgeBinding.ivPlanStatus.setVisibility(View.GONE);
        itemPlanPledgeBinding.tvEndTime.setVisibility(View.GONE);
        itemPlanPledgeBinding.btnReturnPledge.setEnabled(false);

        Date createTime = Formatter.parseDate(assetsBean.getCreatetime());
        Date currentDate = new Date();
        long dayFormat = Formatter.dateToDayFormat(new Date(currentDate.getTime() - createTime.getTime()));

        if (dayFormat > assetsBean.getPacktime() + assetsBean.getPledgetime()) {
            itemPlanPledgeBinding.tvEndTime.setVisibility(View.VISIBLE);
            itemPlanPledgeBinding.btnReturnPledge.setEnabled(true);
            itemPlanPledgeBinding.tvEndTime.setText("结束时间:" + Formatter.formatData(new Date()));
        }

        if (assetsBean.getStaue() == 2L) {
            itemPlanPledgeBinding.ivPlanStatus.setVisibility(View.VISIBLE);
            itemPlanPledgeBinding.btnReturnPledge.setEnabled(false);
        }

        itemPlanPledgeBinding.tvPlanName.setText(assetsBean.getPname());
        itemPlanPledgeBinding.tvPlanStartDate.setText("购买日期:" + assetsBean.getCreatetime());
        itemPlanPledgeBinding.tvPledgeTime.setText("质押时间:" + assetsBean.getPledgetime());
        itemPlanPledgeBinding.tvPackingTime.setText("封装时间:" + assetsBean.getPacktime());
        itemPlanPledgeBinding.tvRunTime.setText("运行时间:" + assetsBean.getRuntime());
        itemPlanPledgeBinding.tvPurchaseCount.setText("购买数量:" + assetsBean.getNumber());

        itemPlanPledgeBinding.btnReturnPledge.setOnClickListener(null);
        itemPlanPledgeBinding.btnReturnPledge.setOnClickListener(v -> {
            showConfirmDialog();
        });

        planActivity.mPresenter.getBooleanLiveData().observe(planActivity, aBoolean -> {
            if (aBoolean) {
                planActivity.mPresenter.requestAssets();
            }
        });
    }

    private void showConfirmDialog() {
        flag = true;
        DialogConfirmCancelBinding binding = DialogConfirmCancelBinding.inflate(LayoutInflater.from(planActivity));
        AlertDialog alertDialog = AlertDialogUtil.getAlertDialog(planActivity, binding.getRoot());
        alertDialog.setCancelable(false);
        binding.btnConfirm.setOnClickListener(v -> {
            alertDialog.dismiss();
            if (flag) {
                flag = false;
                planActivity.mPresenter.requestCancelPledge(assetsBean);
            }
        });
        binding.btnCancel.setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }
}
