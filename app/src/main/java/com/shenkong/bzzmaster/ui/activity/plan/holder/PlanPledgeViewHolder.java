package com.shenkong.bzzmaster.ui.activity.plan.holder;

import android.annotation.SuppressLint;
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
    private static final String TAG = "PlanPledgeViewHolder";

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
        itemPlanPledgeBinding.tvNotReleased.setVisibility(View.GONE);

        // 计算剩余实现判断取消计划按钮是否可用
        Date createTime = Formatter.parseDate(assetsBean.getCreatetime());
        Date currentDate = new Date();
        long dayFormat = Formatter.dateToDayFormat(new Date(currentDate.getTime() - createTime.getTime()));

        if (dayFormat > assetsBean.getPacktime() + assetsBean.getPledgetime()) {
            itemPlanPledgeBinding.btnReturnPledge.setEnabled(true);
        }

        // 根据statue判断联合挖矿计划是否结束从而显示结束是否显示,取消计划按钮是否可用
        if (assetsBean.getStaue() == 2L) {
            itemPlanPledgeBinding.tvEndTime.setVisibility(View.VISIBLE);
            itemPlanPledgeBinding.ivPlanStatus.setVisibility(View.VISIBLE);
            itemPlanPledgeBinding.tvEndTime.setText("计划于" + assetsBean.getEndtime() + "结束");
            itemPlanPledgeBinding.btnReturnPledge.setEnabled(false);
        }

        // 判断是否有锁仓金额，如果有显示锁仓金额
        if (assetsBean.getUnreleasedAmount() > 0) {
            itemPlanPledgeBinding.tvNotReleased.setVisibility(View.VISIBLE);
            itemPlanPledgeBinding.tvNotReleased.setText("锁仓:" + Formatter.numberFormat(assetsBean.getUnreleasedAmount()));
        }

        itemPlanPledgeBinding.tvPlanName.setText(assetsBean.getPname());
        itemPlanPledgeBinding.tvPlanStartDate.setText(assetsBean.getCreatetime());
        itemPlanPledgeBinding.tvPledgeTime.setText("质押时间(天):" + assetsBean.getPledgetime());
        itemPlanPledgeBinding.tvPackingTime.setText("封装时间(天):" + assetsBean.getPacktime());
        itemPlanPledgeBinding.tvRunTime.setText("运行时间(天):" + assetsBean.getRuntime());
        itemPlanPledgeBinding.tvPurchaseCount.setText("购买数量:" + assetsBean.getNumber());
        itemPlanPledgeBinding.tvRefundableCount.setText("可退质押币(" + assetsBean.getCurrency() + "):" + assetsBean.getRefundamount());

        itemPlanPledgeBinding.btnReturnPledge.setOnClickListener(null);
        itemPlanPledgeBinding.btnReturnPledge.setOnClickListener(v -> {
            showConfirmDialog();
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
