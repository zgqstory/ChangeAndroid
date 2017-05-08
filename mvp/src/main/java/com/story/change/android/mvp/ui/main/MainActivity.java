package com.story.change.android.mvp.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.ui.adapter.MainFragmentAdapter;
import com.story.change.android.mvp.ui.base.BaseFragmentActivity;

import java.util.List;

public class MainActivity extends BaseFragmentActivity implements MainView {

    @Bind(R.id.tab_bottom)
    TabLayout bottomLayout;
    @Bind(R.id.vp_content)
    ViewPager contentVp;

    private MainFragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        fragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), this);
        contentVp.setAdapter(fragmentAdapter);
        contentVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fragmentAdapter.setSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        bottomLayout.setTabMode(TabLayout.MODE_FIXED);
        bottomLayout.setupWithViewPager(contentVp);
        //初始化下方tab图片和问题
        List<View> viewList = fragmentAdapter.createTabView();
        if (viewList != null && viewList.size() == 3)
        for (int i=0;i<bottomLayout.getTabCount();i++) {
            TabLayout.Tab tab = bottomLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(viewList.get(i));
            }
        }
        //默认进入第一页
        fragmentAdapter.setSelected(0);
    }

    @Override
    public void closePage() {

    }
}
