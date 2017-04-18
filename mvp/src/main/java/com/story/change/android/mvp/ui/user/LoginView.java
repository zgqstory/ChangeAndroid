package com.story.change.android.mvp.ui.user;

import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.ui.base.BaseView;

/**
 * Created by story on 2017/3/10 0010 下午 2:38.
 * 登录页面操作
 */
public interface LoginView extends BaseView {

    /**
     * 获取userName
     */
    String getUserName();

    /**
     * 获取passWord
     */
    String getPassWord();

    /**
     * 获取验证码
     */
    String getCheck();

    /**
     * 进入注册页面
     */
    void toSignActivity();

    /**
     * 切换登陆方式
     * @param isPwd 是否是密码登录
     */
    void changeLoginStyle(boolean isPwd);

    /**
     * 设置获取验证码按钮状态：若second大于0，显示second秒后重新发送；否则，显示点击获取验证码
     */
    void setCheckBtnStatus(int second);

    /**
     * 设置登录按钮状态
     */
    void setLoginBtnStatus();

    /**
     * 进入主页面
     * @param user 用户信息
     */
    void toMainActivity(User user);
}
