package com.story.change.android.mvp.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.ui.base.BaseFragment;

/**
 * Created by story on 2017/4/28 0028 下午 4:46.
 * 第二页面（测试用）
 */
public class TwoFragment extends BaseFragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(rootView);
        super.onDestroyView();
    }
}
