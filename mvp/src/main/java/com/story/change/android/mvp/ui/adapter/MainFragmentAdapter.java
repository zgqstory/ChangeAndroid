package com.story.change.android.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.ui.main.OneFragment;
import com.story.change.android.mvp.ui.main.TwoFragment;
import com.story.change.android.mvp.ui.main.UserFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by story on 2017/4/28 0028 下午 5:06.
 * 主页面Fragment适配器
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {

    private Context context;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private UserFragment userFragment;
    private List<ImageView> tabImageList;
    private List<TextView> tabTxtList;

    public MainFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabImageList = new ArrayList<>();
        tabTxtList = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (oneFragment == null) {
                oneFragment = new OneFragment();
            }
            return oneFragment;
        } else if (position == 1) {
            if (twoFragment == null) {
                twoFragment = new TwoFragment();
            }
            return twoFragment;
        } else if (position == 2) {
            if (userFragment == null) {
                userFragment = new UserFragment();
            }
            return userFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    /**
     * 初始化Tab样式
     * @return
     */
    public List<View> createTabView() {
        List<View> viewList = new ArrayList<>();
        for (int position=0;position<3;position++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_main_tab, null);
            ImageView tabImg = (ImageView) view.findViewById(R.id.img_tab);
            TextView tabTv = (TextView) view.findViewById(R.id.tv_tab);
            tabImageList.add(tabImg);
            tabTxtList.add(tabTv);
            if (position == 0) {
                tabImg.setImageResource(R.drawable.main_tab_user_default);
                tabTv.setText(R.string.main_tab_user);
                tabTv.setTextColor(context.getResources().getColor(R.color.main_tab_default));
            } else if (position == 1) {
                tabImg.setImageResource(R.drawable.main_tab_user_default);
                tabTv.setText(R.string.main_tab_user);
                tabTv.setTextColor(context.getResources().getColor(R.color.main_tab_default));
            } else if (position == 2) {
                tabImg.setImageResource(R.drawable.main_tab_user_default);
                tabTv.setText(R.string.main_tab_user);
                tabTv.setTextColor(context.getResources().getColor(R.color.main_tab_default));
            }
            viewList.add(view);
        }
        return viewList;
    }

    /**
     * 设置Tab为选中状态
     * @param position pos
     */
    public void setSelected(int position) {
        for (int i=0;i<3;i++) {
            ImageView tabImg = tabImageList.get(i);
            TextView tabTv = tabTxtList.get(i);
            if (position == i) {
                tabTv.setTextColor(context.getResources().getColor(R.color.main_tab_selected));
                if (position == 0) {
                    tabImg.setImageResource(R.drawable.main_tab_user_selected);
                } else if (position == 1) {
                    tabImg.setImageResource(R.drawable.main_tab_user_selected);
                } else if (position == 2) {
                    tabImg.setImageResource(R.drawable.main_tab_user_selected);
                }
            } else {
                tabTv.setTextColor(context.getResources().getColor(R.color.main_tab_default));
                if (position == 0) {
                    tabImg.setImageResource(R.drawable.main_tab_user_default);
                } else if (position == 1) {
                    tabImg.setImageResource(R.drawable.main_tab_user_default);
                } else if (position == 2) {
                    tabImg.setImageResource(R.drawable.main_tab_user_default);
                }
            }
        }
    }

}
