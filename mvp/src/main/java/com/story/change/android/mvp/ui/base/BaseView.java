package com.story.change.android.mvp.ui.base;

/**
 * Created by story on 2017/3/8 0008 下午 2:45.
 * 基本视图接口
 */
public interface BaseView {

    /**
     * 展示进度框
     * @param message 提示文字
     * @param time 倒计时时间，小于等于0表示不需要
     */
    void showLoading(String message, int time);

    /**
     * 隐藏进度框
     */
    void hideLoading();

    /**
     * 弹出通用提示框
     * @param message 显示内容
     */
    void alertMessage(String message);

    /**
     * Toast方式输入提示
     * @param message 显示内容
     */
    void toastMessage(String message);

}
