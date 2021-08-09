package com.shenkong.bzzmaster.ui.fragment.home.viewholder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.config.ConstantPool;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.model.bean.ProductPlanBean;
import com.shenkong.bzzmaster.ui.activity.productinfo.ProductInfoActivity;
import com.shenkong.bzzmaster.ui.activity.submitOrder.blend.SubmitMixedOrderActivity;
import com.shenkong.bzzmaster.ui.activity.submitOrder.ordinary.SubmitOrderActivity;
import com.shenkong.bzzmaster.ui.fragment.home.adapter.MultipleAdapter;

public class HomeProductPlanViewHolder extends MultipleAdapter.MultipleBaseViewHolder {
    private static final String TAG = "HomeHotProductViewHolde";
    private final FragmentActivity fragmentActivity;
    public View rootView;
    public MaterialTextView tvProductTitle;
    public MaterialTextView tvProductPlanStatus;
    public LinearLayoutCompat llTags;
    public MaterialTextView tvPrice;
    public MaterialTextView tvPriceUnit;
    public MaterialTextView tvLockUp;
    public MaterialTextView tvLockUpTip;
    public MaterialTextView tvMinimumSale;
    public Group miningGroup;
    public MaterialTextView tvRevenueDate;
    public MaterialTextView tvMiningCycleTip;
    public Group packingGroup;
    public MaterialTextView tvDay;
    public MaterialTextView tvPackagingCycleTip;
    public MaterialTextView tvPledgeTime;
    public MaterialButton btnPurchase;
    private MultipleAdapter multipleAdapter;
    private ProductPlanBean productPlanBean;

    public HomeProductPlanViewHolder(View rootView, FragmentActivity fragmentActivity) {
        super(rootView);
        this.rootView = rootView;
        this.fragmentActivity = fragmentActivity;
        this.tvProductTitle = rootView.findViewById(R.id.tvProductTitle);
        this.tvProductPlanStatus = rootView.findViewById(R.id.tvProductPlanStatus);
        this.llTags = rootView.findViewById(R.id.llTags);
        this.tvPrice = rootView.findViewById(R.id.tvPrice);
        this.tvPriceUnit = rootView.findViewById(R.id.tvPriceUnit);
        this.tvLockUp = rootView.findViewById(R.id.tvLockUp);
        this.tvLockUpTip = rootView.findViewById(R.id.tvLockUpTip);
        this.tvMinimumSale = rootView.findViewById(R.id.tvMinimumSale);
        this.miningGroup = rootView.findViewById(R.id.miningGroup);
        this.tvRevenueDate = rootView.findViewById(R.id.tvRevenueDate);
        this.tvMiningCycleTip = rootView.findViewById(R.id.tvMiningCycleTip);
        this.packingGroup = rootView.findViewById(R.id.packingGroup);
        this.tvDay = rootView.findViewById(R.id.tvDay);
        this.tvPackagingCycleTip = rootView.findViewById(R.id.tvPackagingCycleTip);
        this.tvPledgeTime = rootView.findViewById(R.id.tvPledgeTime);
        this.btnPurchase = rootView.findViewById(R.id.btnPurchase);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void load(MultipleAdapter multipleAdapter, int position) {
        this.multipleAdapter = multipleAdapter;
        productPlanBean = (ProductPlanBean) multipleAdapter.getBean(position);

        // 设置计划名称
        tvProductTitle.setText(productPlanBean.getName());

        // 设置计划标签
        llTags.removeAllViews();
        if (productPlanBean.getTag() != null && !productPlanBean.getTag().isEmpty()) {
            for (String s : productPlanBean.getTag().split(",")) {
                llTags.addView(createTag(s));
            }
        }

        // 判断计划状态
        String status = "";
        if (productPlanBean.getStaues() == ConstantPool.Plan_PreSale) {
            // 预售
            status = "预售";
            btnPurchase.setText(productPlanBean.getType() == ConstantPool.PlanType_Pledge ? "立即质押" : "立即购买");
            btnPurchase.setEnabled(true);
        } else if (productPlanBean.getStaues() == ConstantPool.Pro_Sell) {
            // 销售
            status = "销售";
            btnPurchase.setText(productPlanBean.getType() == ConstantPool.PlanType_Pledge ? "立即质押" : "立即购买");
            btnPurchase.setEnabled(true);
        } else if (productPlanBean.getStaues() == ConstantPool.Pro_SuspensionOfSale) {
            // 暂停申购
            status = "暂停申购";
            btnPurchase.setEnabled(false);
            btnPurchase.setText("暂停申购");
        } else if (productPlanBean.getStaues() == ConstantPool.Pro_Finish) {
            // 完结
            status = "完结";
            btnPurchase.setEnabled(false);
            btnPurchase.setText("完结");
        } else if (productPlanBean.getStaues() == ConstantPool.Pro_OffShelf) {
            // 下架
            status = "下架";
            btnPurchase.setEnabled(false);
            btnPurchase.setText("该商品已下架");
        }
        tvProductPlanStatus.setVisibility(View.VISIBLE);
        tvProductPlanStatus.setText(status);

        tvLockUp.setVisibility(View.INVISIBLE);
        tvLockUpTip.setVisibility(View.INVISIBLE);
        tvPledgeTime.setVisibility(View.INVISIBLE);

        // 单价
        String price = Formatter.numberFormat(productPlanBean.getPrice());
        // 币种
        String currency = "USDT";
        // 价格单位
        String priceUnit = "/TiB";
        // 最低起售说明文字
        String minimumSale = productPlanBean.getMincompany() + "个";
        // 周期或锁仓提示文字
        String miningTip = "挖矿周期";
        String packingTip = "封装周期";
        // 周期
        String miningUnit = productPlanBean.getRuntime() + "天";
        String packingUnit = productPlanBean.getPacktime() + "天";
        // 锁仓
        String lockTime = "";
        String lockTimeTip = "锁仓周期";
        // 质押周期
        String pledgeTime = "";

        if (productPlanBean.getType() == ConstantPool.PlanType_Normal) {
            // 设置币种为默认的USDT
            currency = "USDT";
        } else if (productPlanBean.getType() == ConstantPool.PlanType_Pledge) {
            // 设置币种为计划本身的币种
            currency = productPlanBean.getCurrency();
            if (productPlanBean.getPledgetime() > 0) {
                pledgeTime = "质押周期" + productPlanBean.getPledgetime() + "天";
                tvPledgeTime.setVisibility(View.VISIBLE);
            }
        } else if (productPlanBean.getType() == ConstantPool.PlanType_Mixing) {
            // 混合支付计划
            currency = "";
            tvLockUp.setVisibility(View.GONE);
            tvLockUpTip.setVisibility(View.GONE);
        }

        // 根据币种判断控件是否需要隐藏
        if (productPlanBean.getCurrency() != null) {
            switch (productPlanBean.getCurrency().toLowerCase()) {
                case "xch":/*Chia币种*/
                    priceUnit = "/TiB";
                    minimumSale = "" + productPlanBean.getMincompany() + "TiB起售";
                    break;
                case "fil": /*FIL币种*/
                    priceUnit = "/TiB";
                    minimumSale = "" + productPlanBean.getMincompany() + "TiB起售";
                    if (productPlanBean.getLocktime() > 0) {
                        tvLockUp.setVisibility(View.VISIBLE);
                        tvLockUpTip.setVisibility(View.VISIBLE);
                        lockTime = productPlanBean.getLocktime() + "天";
                        lockTimeTip = "锁仓周期";
                    }
                    break;
                case "ebzz":
                    priceUnit = "/节点";
                    minimumSale = "节点数量" + productPlanBean.getMincompany() + "";
                    price = Formatter.numberFormat(productPlanBean.getPrice()) + productPlanBean.getCurrency() + " " + Formatter.numberFormat(productPlanBean.getMixedpayment()) + "USDT";
                    break;
                case "bzz":/*Swarm币种, 显示节点*/
                    priceUnit = "/节点";
                    minimumSale = "节点数量" + productPlanBean.getMincompany() + "";
                default:
                    priceUnit = "/节点";
                    minimumSale = "节点数量" +  productPlanBean.getMincompany() + "";
                    break;
            }
        }

        tvPrice.setText(price);
        tvPriceUnit.setText(currency + priceUnit);
        tvMinimumSale.setText(minimumSale);
        tvLockUp.setText(lockTime);
        tvLockUpTip.setText(lockTimeTip);
        tvMiningCycleTip.setText(miningTip);
        tvPackagingCycleTip.setText(packingTip);
        tvRevenueDate.setText(miningUnit);
        tvDay.setText(packingUnit);
        tvPledgeTime.setText(pledgeTime);

        // 条目点击按钮
        rootView.setOnClickListener(null);
        rootView.setOnClickListener(v -> {
            SharedBean.putData(SharedBean.ProductPlanBean, productPlanBean);
            Intent intent = new Intent(fragmentActivity, ProductInfoActivity.class);
            fragmentActivity.startActivity(intent);
        });

        // 购买按钮
        btnPurchase.setOnClickListener(null);
        btnPurchase.setOnClickListener(v -> {
            SharedBean.putData(SharedBean.ProductPlanBean, productPlanBean);
            Intent intent;
            // 判断计划类型
            if (productPlanBean.getType() == ConstantPool.PlanType_Mixing) {
                intent = new Intent(fragmentActivity, SubmitMixedOrderActivity.class);
            } else {
                intent = new Intent(fragmentActivity, SubmitOrderActivity.class);
            }
            fragmentActivity.startActivity(intent);
        });
    }

    /**
     * 添加标签
     *
     * @param tagTitle 标签文字
     * @return 返回标签控件
     */
    private MaterialTextView createTag(String tagTitle) {
        MaterialTextView materialTextView = new MaterialTextView(multipleAdapter.getFragmentActivity());
        materialTextView.setText(tagTitle);
        materialTextView.setTextSize(12);
        materialTextView.setPadding(2, 2, 2, 2);
        materialTextView.setTextColor(Color.parseColor("#EC7636"));
        materialTextView.setBackgroundResource(R.drawable.xml_tag_background);
        return materialTextView;
    }
}
