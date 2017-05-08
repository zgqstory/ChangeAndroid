package com.story.change.android.mvp.ui.user;

import com.story.change.android.mvp.ui.base.BaseView;

/**
 * Created by story on 2017/5/8 0008 上午 9:40.
 * 用户设置View操作
 */
public interface UserSetView extends BaseView {

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 获取旧密码
     */
    String getPwdNow();

    /**
     * 获取新密码
     */
    String getPwdNew();

    /**
     * 返回上一界面
     */
    void back();
}
