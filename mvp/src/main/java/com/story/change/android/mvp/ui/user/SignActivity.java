package com.story.change.android.mvp.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.story.change.android.mvp.AppApplication;
import com.story.change.android.mvp.ui.main.MainActivity;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.presenter.user.SignPresenter;
import com.story.change.android.mvp.ui.base.BaseActivity;

/**
 * Created by story on 2017/3/10 0010 下午 4:04.
 * 注册页面
 */
public class SignActivity extends BaseActivity implements SignView {

    @Bind(R.id.et_phone)
    EditText phoneEt;
    @Bind(R.id.et_check)
    EditText checkEt;
    @Bind(R.id.btn_sign)
    Button signBtn;
    @Bind(R.id.btn_check_get)
    Button getCheckBtn;

    private SignPresenter signPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ButterKnife.bind(this);
        signPresenter = new SignPresenter(this, this);
        initViews();
    }

    private void initViews() {
        phoneEt.addTextChangedListener(inputWatcher);
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
            setSignBtnStatus();
        }
    };

    @OnClick(R.id.btn_check_get)
    public void getCheck(View view) {
        signPresenter.getCheck();
    }

    @OnClick(R.id.btn_sign)
    public void sign(View view) {
        signPresenter.sign();
    }

    @Override
    public String getUserPhone() {
        return phoneEt.getText().toString().trim();
    }

    @Override
    public String getPhoneCheck() {
        return checkEt.getText().toString().trim();
    }

    @Override
    public void setCheckBtnStatus(int second) {
        if (second > 0) {
            getCheckBtn.setEnabled(false);
            getCheckBtn.setText(String.format(SignActivity.this.getString(R.string.login_check_get_after), second));
        } else {
            getCheckBtn.setEnabled(true);
            getCheckBtn.setText(SignActivity.this.getString(R.string.login_check_get_btn));
        }
    }

    @Override
    public void setSignBtnStatus() {
        String phone = getUserPhone();
        String check = getPhoneCheck();
        if (phone != null && !phone.equals("") && check != null && !check.equals("")) {
            signBtn.setEnabled(true);
        } else {
            signBtn.setEnabled(false);
        }
    }

    @Override
    public void toMainActivity(User user) {
        AppApplication.getInstance().setUserInfo(user);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        signPresenter.releaseData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
