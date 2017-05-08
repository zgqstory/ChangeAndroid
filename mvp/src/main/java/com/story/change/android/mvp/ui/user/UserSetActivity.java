package com.story.change.android.mvp.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.story.change.android.mvp.AppApplication;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.presenter.user.UserSetPresenter;
import com.story.change.android.mvp.ui.base.BaseActivity;
import com.story.change.android.mvp.util.StringCheckUtil;
import com.story.view.input.ClearEditText;

/**
 * Created by story on 2017/5/2 0002 下午 7:22.
 * 用户信息设置页面
 */
public class UserSetActivity extends BaseActivity implements UserSetView {

    @Bind(R.id.tv_common_title_name)
    TextView titleTv;
    @Bind(R.id.et_name)
    ClearEditText userNameEt;
    @Bind(R.id.et_pwd_now)
    ClearEditText nowPwdEt;
    @Bind(R.id.et_pwd_new)
    ClearEditText newPwdEt;
    @Bind(R.id.lay_name)
    LinearLayout userNameLayout;
    @Bind(R.id.lay_password)
    LinearLayout userPwdLayout;

    private UserSetPresenter userSetPresenter;
    private int setType;//信息类型：0，用户名；1，密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set);
        ButterKnife.bind(this);
        userSetPresenter = new UserSetPresenter(this, this);
        getParentData();
        initViews();
    }

    private void getParentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            setType = bundle.getInt("type", 0);
        }
    }

    private void initViews() {
        User user = AppApplication.getInstance().getUserInfo();
        if (setType == 0) {
            titleTv.setText(getString(R.string.user_settings_name_title));
            userNameLayout.setVisibility(View.VISIBLE);
            userPwdLayout.setVisibility(View.GONE);
            if (!StringCheckUtil.isNullAndEmpty(user.getName())) {
                userNameEt.setText(user.getName());
                userNameEt.setSelection(userNameEt.getText().toString().length());
            }
        } else if (setType == 1) {
            titleTv.setText(getString(R.string.user_settings_password_title));
            userNameLayout.setVisibility(View.GONE);
            userPwdLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 确定设置
     */
    @OnClick(R.id.btn_sure)
    public void sureSet(View view) {
        userSetPresenter.setUserInfo(setType);
    }

    /**
     * 返回
     * @param view
     */
    @OnClick(R.id.img_common_title_back)
    public void back(View view) {
        finish();
    }

    @Override
    public String getUserName() {
        return userNameEt.getText().toString().trim();
    }

    @Override
    public String getPwdNow() {
        return nowPwdEt.getText().toString().trim();
    }

    @Override
    public String getPwdNew() {
        return newPwdEt.getText().toString().trim();
    }

    @Override
    public void back() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
