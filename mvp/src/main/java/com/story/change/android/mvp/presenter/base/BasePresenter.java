package com.story.change.android.mvp.presenter.base;

import com.story.change.android.mvp.data.DataManager;
import com.story.change.android.mvp.ui.base.BaseView;

/**
 * Created by story on 2017/3/8 0008 下午 2:50.
 * 基本Presenter定义
 */
public class BasePresenter<V extends BaseView> {

    private static final String TAG = "BasePresenter";
    private DataManager dataManager;
    private V view;

    public BasePresenter(V view, DataManager dataManager) {
        this.view = view;
        this.dataManager = dataManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public V getView() {
        return view;
    }
}
