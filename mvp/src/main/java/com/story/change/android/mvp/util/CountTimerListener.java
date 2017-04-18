package com.story.change.android.mvp.util;

/**
 * Created by story on 2017/4/12 0012 下午 8:45.
 * 倒计时操作监听
 */
public interface CountTimerListener {

    /**
     * 每次执行操作
     * @param second 剩余秒数
     */
    void onTick(int second);

    /**
     * 执行完成
     */
    void onFinish();
}
