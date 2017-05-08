package com.story.change.android.mvp;

import android.app.Application;
import com.story.change.android.mvp.bean.user.User;

/**
 * Created by story on 2017/5/2 0002 下午 5:26.
 * 应用数据初始化
 */
public class AppApplication extends Application {

    private static AppApplication instance;
    private User userInfo;//用户信息

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 程序运行中均可获取实例
     * @return instance
     */
    public static AppApplication getInstance() {
        return instance;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
