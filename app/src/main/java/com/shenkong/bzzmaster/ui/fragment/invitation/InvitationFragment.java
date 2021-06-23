package com.shenkong.bzzmaster.ui.fragment.invitation;

import android.view.View;

import com.shenkong.bzzmaster.R;
import com.shenkong.bzzmaster.ui.base.BaseFragment;

public class InvitationFragment extends BaseFragment {
    public static InvitationFragment invitationFragment;

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

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {

    }
}
