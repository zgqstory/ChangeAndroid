package com.story.change.android.mvp.presenter.user;

import com.story.change.android.mvp.data.DataManager;
import com.story.change.android.mvp.ui.user.LoginView;

/**
 * Created by story on 2017/3/10 0010 下午 4:34.
 * 登录Presenter定义
 */
public class LoginPresenter{

    private LoginView loginView;

    public LoginPresenter(LoginView view, DataManager dataManager) {
        this.loginView = view;
    }

    public void login() {
        loginView.showLoading();

    }
}
