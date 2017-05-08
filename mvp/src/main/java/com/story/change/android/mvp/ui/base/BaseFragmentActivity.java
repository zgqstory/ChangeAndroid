package com.story.change.android.mvp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import com.story.change.android.mvp.R;
import com.story.view.alert_view_ios.AlertView;
import com.story.view.alert_view_ios.Style;
import com.story.view.progress.ProgressDialog;

/**
 * Created by story on 2017/5/2 0002 上午 8:58.
 * 基本FragmentActivity定义
 */
public class BaseFragmentActivity extends FragmentActivity implements BaseView {
    private ProgressDialog progressDialog;//网络加载等待框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void showLoading(String message, int time) {
        if (message != null && !message.equals("") && time > 0) {
            progressDialog.show(message, time);
        }
        if (time > 0 && (message == null || message.equals(""))) {
            progressDialog.show(time);
        } else {
            progressDialog.show(message);
        }
    }

    @Override
    public void hideLoading() {
        progressDialog.cancel();
    }

    @Override
    public void alertMessage(String message) {
        new AlertView(message, null, BaseFragmentActivity.this.getString(R.string.sure), null, null, BaseFragmentActivity.this, Style.Alert, null).show();
    }
}
