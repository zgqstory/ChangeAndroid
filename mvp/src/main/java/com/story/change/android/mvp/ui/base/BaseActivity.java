package com.story.change.android.mvp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.story.change.android.mvp.R;
import com.story.view.alert_view_ios.AlertView;
import com.story.view.alert_view_ios.Style;
import com.story.view.progress.ProgressDialog;

/**
 * Created by story on 2017/3/8 0008 下午 2:47.
 * 基本Activity定义
 */
public class BaseActivity extends AppCompatActivity implements BaseView {

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
        } else if (time > 0) {
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
        new AlertView(message, null, BaseActivity.this.getString(R.string.sure), null, null, BaseActivity.this, Style.Alert, null).show();
    }
}
