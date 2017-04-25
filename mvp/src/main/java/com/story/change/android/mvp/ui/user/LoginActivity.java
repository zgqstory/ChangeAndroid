package com.story.change.android.mvp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.story.change.android.mvp.MainActivity;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.presenter.user.LoginPresenter;
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

    private int type = 0;//登录方式：0，密码登录；1，验证码登录
    private LoginPresenter loginPresenter;//登录Presenter

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this, this);
        initViews();
    }

    private void initViews() {
        phoneEt.addTextChangedListener(inputWatcher);
        pwdEt.addTextChangedListener(inputWatcher);
        checkEt.addTextChangedListener(inputWatcher);
    }

    private TextWatcher inputWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            setLoginBtnStatus();
        }
    };

    @OnClick(R.id.btn_check_get)
    public void getCheck(View view) {
        loginPresenter.getCheck();
    }

    @OnClick(R.id.btn_login)
    public void login(View view) {
        if (type == 0) {
            loginPresenter.loginByPwd();
        } else {
            loginPresenter.loginByPhone();
        }
    }

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
        startActivity(new Intent(this, SignActivity.class));
    }

    @Override
    public void changeLoginStyle(boolean isPwd) {
        if (isPwd) {
            type = 0;
            pwdLayout.setVisibility(View.VISIBLE);
            checkLayout.setVisibility(View.GONE);
            switchCheckTv.setVisibility(View.VISIBLE);
            switchPwdTv.setVisibility(View.GONE);
        } else {
            type = 1;
            pwdLayout.setVisibility(View.GONE);
            checkLayout.setVisibility(View.VISIBLE);
            switchCheckTv.setVisibility(View.GONE);
            switchPwdTv.setVisibility(View.VISIBLE);
        }
        setLoginBtnStatus();
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
    public void setCheckBtnStatus(int second) {
        if (second > 0) {
            getCheckBtn.setEnabled(false);
            getCheckBtn.setText(String.format(LoginActivity.this.getString(R.string.login_check_get_after), second));
        } else {
            getCheckBtn.setEnabled(true);
            getCheckBtn.setText(LoginActivity.this.getString(R.string.login_check_get_btn));
        }
    }

    @Override
    public void setLoginBtnStatus() {
        String phone = getUserName();
        String pwd = getPassWord();
        String check = getCheck();
        if ((type == 0 && phone != null && !phone.equals("") && pwd != null && !pwd.equals("")) ||
                (type == 1 && phone != null && !phone.equals("") && check != null && !check.equals(""))) {
            loginBtn.setEnabled(true);
        } else {
            loginBtn.setEnabled(false);
        }
    }

    @Override
    public void toMainActivity(User user) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        loginPresenter.releaseData();
    }
}
