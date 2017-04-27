package com.story.change.android.mvp.ui.user;

import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.ui.base.BaseView;

/**
 * Created by story on 2017/3/10 0010 下午 4:04.
 * 注册页面方法定义
 */
public interface SignView extends BaseView {

    /**
     * 获取userPhone
     */
    String getUserPhone();

    /**
     * 获取phoneCheck
     */
    String getPhoneCheck();

    /**
     * 设置获取验证码按钮状态：若second大于0，显示second秒后重新发送；否则，显示点击获取验证码
     */
    void setCheckBtnStatus(int second);

    /**
     * 设置登录按钮状态
     */
    void setSignBtnStatus();

    /**
     * 进入主页面
     * @param user 用户信息
     */
    void toMainActivity(User user);

    /**
     * 关闭本页面
     */
    void closePage();

}
