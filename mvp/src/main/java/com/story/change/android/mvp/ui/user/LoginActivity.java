package com.story.change.android.mvp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.story.change.android.mvp.MainActivity;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.data.User;
import com.story.change.android.mvp.ui.base.BaseActivity;
import com.story.view.alert_view_ios.AlertView;
import com.story.view.alert_view_ios.Style;

/**
 * Created by story on 2017/3/10 0010 下午 2:58.
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements LoginView {

    @Bind(R.id.et_user)
    EditText phoneEt;
    @Bind(R.id.et_pwd)
    EditText pwdEt;
    @Bind(R.id.et_check)
    EditText checkEt;
    @Bind(R.id.lay_check)
    LinearLayout checkLayout;
    @Bind(R.id.lay_pwd)
    RelativeLayout pwdLayout;
    @Bind(R.id.btn_check_get)
    Button getCheckBtn;
    @Bind(R.id.btn_login)
    Button loginBtn;
    @Bind(R.id.tv_switch_check)
    TextView switchCheckTv;
    @Bind(R.id.tv_switch_pwd)
    TextView switchPwdTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_check_get)
    public void getCheck(View view) {}

    @OnClick(R.id.btn_login)
    public void login(View view) {}

    @OnClick(R.id.tv_to_register)
    public void register(View view) {
        toSignActivity();
    }

    @OnClick(R.id.tv_switch_check)
    public void switchCheck(View view) {
        changeLoginStyle(false);
    }

    @OnClick(R.id.tv_switch_pwd)
    public void switchPwd(View view) {
        changeLoginStyle(true);
    }

    @Override
    public void toSignActivity() {
//        startActivity(new Intent(this, SignActivity.class));
        onError("测试");
    }

    @Override
    public void changeLoginStyle(boolean isPwd) {
        if (isPwd) {
            pwdLayout.setVisibility(View.VISIBLE);
            checkLayout.setVisibility(View.GONE);
            switchCheckTv.setVisibility(View.VISIBLE);
            switchPwdTv.setVisibility(View.GONE);
        } else {
            pwdLayout.setVisibility(View.GONE);
            checkLayout.setVisibility(View.VISIBLE);
            switchCheckTv.setVisibility(View.GONE);
            switchPwdTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void toResetPwdActivity() {
        startActivity(new Intent(this, ResetPwdActivity.class));
    }

    @Override
    public String getUserName() {
        return phoneEt.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return pwdEt.getText().toString().trim();
    }

    @Override
    public String getCheck() {
        return checkEt.getText().toString().trim();
    }

    @Override
    public void setCheckBtnStatus() {

    }

    @Override
    public void setLoginBtnStatus() {

    }

    @Override
    public void toMainActivity(User user) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String message) {
        new AlertView(message, null, getString(R.string.sure), null, null, this, Style.Alert, null).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
