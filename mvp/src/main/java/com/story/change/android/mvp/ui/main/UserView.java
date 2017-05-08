package com.story.change.android.mvp.ui.main;

import com.story.change.android.mvp.ui.base.BaseView;

/**
 * Created by story on 2017/5/2 0002 下午 4:58.
 * 个人中心相关操作
 */
public interface UserView extends BaseView {

    /**
     * 展示选择图片方式
     */
    void showSelectPhotoPath();

    /**
     * 去拍照
     */
    void toTakePhoto();

    /**
     * 去相册
     */
    void toSelectPhoto();

    /**
     * 进入用户名设置页面
     */
    void toSetName();

    /**
     * 进入密码设置页面
     */
    void toSetPassword();

}
