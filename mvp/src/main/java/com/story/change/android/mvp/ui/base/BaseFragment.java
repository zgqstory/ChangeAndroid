package com.story.change.android.mvp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by story on 2017/4/28 0028 下午 4:40.
 * 基本Fragment定义
 */
public class BaseFragment extends Fragment implements BaseView {

    private BaseActivity baseActivity = null;
    private BaseFragmentActivity mainActivity = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mainActivity = (BaseFragmentActivity) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            baseActivity = (BaseActivity) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLoading(String message, int time) {
        if (baseActivity != null) {
            baseActivity.showLoading(message, time);
        } else if (mainActivity != null) {
            mainActivity.showLoading(message, time);
        }
    }

    @Override
    public void hideLoading() {
        if (baseActivity != null) {
            baseActivity.hideLoading();
        } else if (mainActivity != null) {
            mainActivity.hideLoading();
        }
    }

    @Override
    public void alertMessage(String message) {
        if (baseActivity != null) {
            baseActivity.alertMessage(message);
        } else if (mainActivity != null) {
            mainActivity.alertMessage(message);
        }
    }

    @Override
    public void toastMessage(String message) {
        if (baseActivity != null) {
            baseActivity.toastMessage(message);
        } else if (mainActivity != null) {
            mainActivity.toastMessage(message);
        }
    }
}
