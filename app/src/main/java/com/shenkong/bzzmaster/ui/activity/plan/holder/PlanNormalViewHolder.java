package com.shenkong.bzzmaster.ui.activity.plan.holder;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;

import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.databinding.ItemPlanNormalBinding;
import com.shenkong.bzzmaster.model.bean.AssetsBean;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

public class PlanNormalViewHolder extends MultiLayoutAdapter.MultipleLayoutViewHolder {

    private final ItemPlanNormalBinding itemPlanNormalBinding;
    private AssetsBean assetsBean;

    public PlanNormalViewHolder(@NonNull ItemPlanNormalBinding itemPlanNormalBinding) {
        super(itemPlanNormalBinding.getRoot());
        this.itemPlanNormalBinding = itemPlanNormalBinding;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void load(MultiLayoutAdapter multiLayoutAdapter, int position) {
        assetsBean = multiLayoutAdapter.getBean(AssetsBean.class, position);

        itemPlanNormalBinding.ivPlanStatus.setVisibility(View.GONE);
        itemPlanNormalBinding.tvEndTime.setVisibility(View.GONE);
        itemPlanNormalBinding.tvNotReleased.setVisibility(View.GONE);

        // 根据statue判断当前计划是否已经结束，如果结束显示已结束的图片并设置结束时间
        if (assetsBean.getStaue() == 2L) {
            itemPlanNormalBinding.ivPlanStatus.setVisibility(View.VISIBLE);
            itemPlanNormalBinding.tvEndTime.setText("计划于" + assetsBean.getEndtime() + "结束");
        }

        // 判断是否有锁仓金额，如果有显示锁仓金额
        if (assetsBean.getUnreleasedAmount() > 0) {
            itemPlanNormalBinding.tvNotReleased.setVisibility(View.VISIBLE);
            itemPlanNormalBinding.tvNotReleased.setText("待释放:" + Formatter.numberFormat(assetsBean.getUnreleasedAmount()));
        }

/*
        Date createTime = com.shenkong.bzzmaster.common.utils.Formatter.parseDate(assetsBean.getCreatetime());
        Date currentDate = new Date();
        long dayFormat = Formatter.dateToDayFormat(new Date(currentDate.getTime() - createTime.getTime()));

        if (dayFormat > assetsBean.getPacktime() + assetsBean.getPledgetime()) {
            itemPlanNormalBinding.tvEndTime.setVisibility(View.VISIBLE);
            itemPlanNormalBinding.tvEndTime.setText("结束时间:" + Formatter.formatData(new Date()));
        }
*/


        itemPlanNormalBinding.tvPlanName.setText(assetsBean.getPname());
        itemPlanNormalBinding.tvPlanStartDate.setText(assetsBean.getCreatetime());
        itemPlanNormalBinding.tvPackingTime.setText("封装时间(天):" + assetsBean.getPacktime());
        itemPlanNormalBinding.tvRunTime.setText("运行时间(天):" + assetsBean.getRuntime());
        itemPlanNormalBinding.tvPurchaseCount.setText("购买数量:" + assetsBean.getNumber());
    }
}
