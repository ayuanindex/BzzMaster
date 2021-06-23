package com.shenkong.bzzmaster.ui.activity.login;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shenkong.bzzmaster.MainActivity;
import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private TextInputEditText tiePhone;
    private TextInputLayout tilVerificationCode;
    private TextInputEditText tieVerificationCode;
    private MaterialButton btnGetVerificationCode;
    private TextInputLayout tilInvitationCode;
    private TextInputEditText tieInvitationCode;
    private MaterialButton btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tiePhone = findViewById(R.id.tiePhone);
        tilVerificationCode = findViewById(R.id.tilVerificationCode);
        tieVerificationCode = findViewById(R.id.tieVerificationCode);
        btnGetVerificationCode = findViewById(R.id.btnGetVerificationCode);
        tilInvitationCode = findViewById(R.id.tilInvitationCode);
        tieInvitationCode = findViewById(R.id.tieInvitationCode);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    protected void initEvent() {
        btnLogin.setOnClickListener(v -> {
            jumpActivity(MainActivity.class);
            finish();
        });
    }

    @Override
    protected void initData() {

    }
}
