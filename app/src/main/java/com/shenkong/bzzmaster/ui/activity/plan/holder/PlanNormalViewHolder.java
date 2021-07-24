package com.shenkong.bzzmaster.ui.activity.plan.holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.databinding.ItemPlanNormalBinding;
import com.shenkong.bzzmaster.databinding.ItemPlanPledgeBinding;
import com.shenkong.bzzmaster.model.bean.AssetsBean;
import com.shenkong.bzzmaster.ui.customerview.adapter.MultiLayoutAdapter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PlanNormalViewHolder extends MultiLayoutAdapter.MultipleLayoutViewHolder {

    private final ItemPlanNormalBinding itemPlanNormalBinding;
    private AssetsBean assetsBean;

    public PlanNormalViewHolder(@NonNull ItemPlanNormalBinding itemPlanNormalBinding) {
        super(itemPlanNormalBinding.getRoot());
        this.itemPlanNormalBinding = itemPlanNormalBinding;
    }

    @Override
    public void load(MultiLayoutAdapter multiLayoutAdapter, int position) {
        assetsBean = multiLayoutAdapter.getBean(AssetsBean.class, position);

        itemPlanNormalBinding.ivPlanStatus.setVisibility(View.GONE);
        itemPlanNormalBinding.tvEndTime.setVisibility(View.GONE);
        if (assetsBean.getStaue() == 2L) {
            itemPlanNormalBinding.ivPlanStatus.setVisibility(View.VISIBLE);
        }

        Date createTime = com.shenkong.bzzmaster.common.utils.Formatter.parseDate(assetsBean.getCreatetime());
        Date currentDate = new Date();
        long dayFormat = Formatter.dateToDayFormat(new Date(currentDate.getTime() - createTime.getTime()));

        if (dayFormat > assetsBean.getPacktime() + assetsBean.getPledgetime()) {
            itemPlanNormalBinding.tvEndTime.setVisibility(View.VISIBLE);
            itemPlanNormalBinding.tvEndTime.setText("结束时间:" + Formatter.formatData(new Date()));
        }


        itemPlanNormalBinding.tvPlanName.setText(assetsBean.getPname());
        itemPlanNormalBinding.tvPlanStartDate.setText("购买日期:" + assetsBean.getCreatetime());
        itemPlanNormalBinding.tvPackingTime.setText("封装时间(天):" + assetsBean.getPacktime());
        itemPlanNormalBinding.tvRunTime.setText("运行时间(天):" + assetsBean.getRuntime());
        itemPlanNormalBinding.tvPurchaseCount.setText("购买数量:" + assetsBean.getNumber());
    }
}
