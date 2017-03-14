package com.story.change.android.mvp.ui.base;

/**
 * Created by story on 2017/3/8 0008 下午 2:45.
 * 基本视图接口
 */
public interface BaseView {
    void showLoading();

    void hideLoading();

    void onError(String message);
}
