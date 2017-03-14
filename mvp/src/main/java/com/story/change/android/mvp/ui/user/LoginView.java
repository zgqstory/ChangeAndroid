package com.story.change.android.mvp.ui.user;

import com.story.change.android.mvp.data.User;
import com.story.change.android.mvp.ui.base.BaseView;

/**
 * Created by story on 2017/3/10 0010 下午 2:38.
 * 登录页面操作
 */
public interface LoginView extends BaseView {
    void toSignActivity();
    void changeLoginStyle(boolean isPwd);
    void toResetPwdActivity();
    String getUserName();
    String getPassWord();
    String getCheck();
    void setCheckBtnStatus();
    void setLoginBtnStatus();
    void toMainActivity(User user);
}
