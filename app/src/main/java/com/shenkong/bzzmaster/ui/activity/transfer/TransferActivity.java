package com.shenkong.bzzmaster.ui.activity.transfer;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.common.base.SharedBean;
import com.shenkong.bzzmaster.common.utils.AlertDialogUtil;
import com.shenkong.bzzmaster.common.utils.CurrencyUtil;
import com.shenkong.bzzmaster.common.utils.Formatter;
import com.shenkong.bzzmaster.common.utils.LoggerUtils;
import com.shenkong.bzzmaster.common.utils.ToastUtil;
import com.shenkong.bzzmaster.databinding.DialogConfirmBinding;
import com.shenkong.bzzmaster.databinding.ItemSpinnerDropdownBinding;
import com.shenkong.bzzmaster.model.bean.CapitalBean;
import com.shenkong.bzzmaster.model.bean.ProductBean;
import com.shenkong.bzzmaster.ui.base.BaseMvpActivity;

import java.util.List;

public class TransferActivity extends BaseMvpActivity<TransferPresent> implements TransferEvent {

    private RelativeLayout titleLayout;
    private AppCompatImageView ivArrowBack;
    private MaterialTextView tvTitle;
    private MaterialCardView cardSelectCurrency;
    private AppCompatSpinner spCurrency;
    private LinearLayoutCompat llBalanceContent;
    private MaterialTextView tvMaxTransactionCost;
    private MaterialTextView tvBalance;
    private MaterialTextView tvCurrency;
    private TextInputLayout inputAddressLayout;
    private TextInputEditText inputAddress;
    private TextInputLayout inputAmountOfMoneyLayout;
    private TextInputEditText inputAmountOfMoney;
    private LinearLayoutCompat tvTipLayout;
    private MaterialTextView tvWaringTip;
    private MaterialButton btnConfirmTransfer;
    private long productId;
    private int currentPosition;
    private final double maxAmountOfMoney = 1000000;
    private final double minAmountOfMoney = 0.01;
    private ProductBean productBean;
    private int isSelect;

    @Override
    public int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected void initView() {
        titleLayout = findViewById(R.id.titleLayout);
        ivArrowBack = findViewById(R.id.ivArrowBack);
        tvTitle = findViewById(R.id.tvTitle);
        cardSelectCurrency = findViewById(R.id.cardSelectCurrency);
        tvMaxTransactionCost = findViewById(R.id.tvMaxTransactionCost);
        spCurrency = findViewById(R.id.spCurrency);
        llBalanceContent = findViewById(R.id.llBalanceContent);
        tvBalance = findViewById(R.id.tvBalance);
        tvCurrency = findViewById(R.id.tvCurrency);
        inputAddressLayout = findViewById(R.id.inputAddressLayout);
        inputAddress = findViewById(R.id.inputAddress);
        inputAmountOfMoneyLayout = findViewById(R.id.inputAmountOfMoneyLayout);
        inputAmountOfMoney = findViewById(R.id.inputAmountOfMoney);
        tvTipLayout = findViewById(R.id.tvTipLayout);
        tvWaringTip = findViewById(R.id.tvWaringTip);
        btnConfirmTransfer = findViewById(R.id.btnConfirmTransfer);
    }

    @Override
    protected void initEvent() {
        ivArrowBack.setOnClickListener(v -> finish());

        btnConfirmTransfer.setOnClickListener(v -> {
            if (mPresenter.getProductBeanListLiveData().getValue() == null) {
                ToastUtil.showToast(this, "正在加载相关币种");
                mPresenter.requestAllProduct();
                return;
            }

            if (mPresenter.getCapitalBeanListLiveData().getValue() == null) {
                ToastUtil.showToast(this, "正在加载币种余额");
                mPresenter.requestBalance(currentPosition);
                return;
            }

            mPresenter.confirmTransfer(inputAddress.getEditableText().toString().trim(), inputAmountOfMoney.getEditableText().toString().trim());
        });

        spCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mPresenter.getProductBeanListLiveData().getValue() != null) {
                    ProductBean productBean = mPresenter.getProductBeanListLiveData().getValue().get(position);
                    productId = productBean.getProductid();
                }
                currentPosition = position;
                mPresenter.requestBalance(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.setLifecycleProvider(this);
        initDataSubscribe();

        isSelect = getIntent().getIntExtra("isSelect", 0);

        if (isSelect == 1) {
            // 拿到共享的数据
            productBean = SharedBean.getData(ProductBean.class, SharedBean.Product);
        }

        mPresenter.requestAllProduct();
    }

    private void initDataSubscribe() {
        // 产品集合数据订阅
        mPresenter.setProductBeanListLiveData(new MutableLiveData<>());
        mPresenter.getProductBeanListLiveData().observe(this, productBeanList -> {
            // 设置spinner的适配器
            CustomerSpinnerAdapter customerSpinnerAdapter = new CustomerSpinnerAdapter(productBeanList);
            spCurrency.setAdapter(customerSpinnerAdapter);

            if (isSelect == 1) {
                // 选择默认币种
                for (int i = 0; i < productBeanList.size(); i++) {
                    if (productBeanList.get(i).getProductid() == productBean.getProductid()) {
                        currentPosition = i;
                        spCurrency.setSelection(currentPosition, true);
                        break;
                    }
                }
            }
        });

        // 币种余额数据订阅
        mPresenter.setCapitalBeanListLiveData(new MutableLiveData<>());
        mPresenter.getCapitalBeanListLiveData().observe(this, capitalBeans -> {
            setBalanceText(capitalBeans.get(0));
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
        ToastUtil.showToast(this, msg);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showConfirmDialog(String address, double doubleAmountOfMoney) {
        if (doubleAmountOfMoney > maxAmountOfMoney) {
            showToastMsg("转账金额不能大于1000000", 0);
            return;
        }

        if (doubleAmountOfMoney < minAmountOfMoney) {
            showToastMsg("转账金额不能小于0.01", 0);
            return;
        }

        DialogConfirmBinding confirmBinding = DialogConfirmBinding.inflate(getLayoutInflater());
        AlertDialog alertDialog = AlertDialogUtil.getAlertDialog(this, confirmBinding.getRoot());
        alertDialog.setCancelable(false);
        confirmBinding.tvAddress.setText("↓目标收款地址↓\n" + address);
        confirmBinding.tvBalance.setText("↓转账金额↓\n" + Formatter.numberFormat(doubleAmountOfMoney));
        confirmBinding.cbInformationConfirmation.setOnCheckedChangeListener((buttonView, isChecked) -> {
            confirmBinding.btnSubmitImmediately.setEnabled(isChecked);
        });
        confirmBinding.btnCancel.setOnClickListener(v -> alertDialog.dismiss());
        confirmBinding.btnSubmitImmediately.setOnClickListener(v -> {
            mPresenter.requestTransferOut(address, doubleAmountOfMoney, productId);
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setBalanceText(CapitalBean capitalBean) {
        tvCurrency.setText("余额(" + capitalBean.getName() + ")");
        tvBalance.setText(Formatter.numberFormat(capitalBean.getBalance()));
        tvMaxTransactionCost.setText("最大交易费用" + capitalBean.getName().toUpperCase());
    }

    @Override
    public void transferOutSuccess() {
        // 查询当前选择币种的余额
        mPresenter.requestBalance(currentPosition);
    }

    class CustomerSpinnerAdapter extends BaseAdapter {
        private final List<ProductBean> productBeanList;

        public CustomerSpinnerAdapter(List<ProductBean> productBeanList) {
            this.productBeanList = productBeanList;
        }

        @Override
        public int getCount() {
            return productBeanList.size();
        }

        @Override
        public ProductBean getItem(int position) {
            return productBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder(ItemSpinnerDropdownBinding.inflate(getLayoutInflater(), parent, false));
            viewHolder.itemSpinnerDropdownBinding.text1.setText(productBeanList.get(position).getCurrency());
            return viewHolder.itemSpinnerDropdownBinding.getRoot();
        }

        class ViewHolder {
            public ItemSpinnerDropdownBinding itemSpinnerDropdownBinding;

            public ViewHolder(ItemSpinnerDropdownBinding itemSpinnerDropdownBinding) {
                this.itemSpinnerDropdownBinding = itemSpinnerDropdownBinding;
            }
        }
    }
}
