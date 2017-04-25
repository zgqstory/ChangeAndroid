package com.story.change.android.mvp.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.ui.base.BaseActivity;

/**
 * Created by story on 2017/3/10 0010 下午 4:04.
 * 注册页面
 */
public class SignActivity extends BaseActivity implements SignView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
    }


}
