package com.story.change.android.mvp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.story.change.android.mvp.AppApplication;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.ui.base.BaseFragment;
import com.story.change.android.mvp.ui.user.UserSetActivity;

/**
 * Created by story on 2017/4/28 0028 下午 4:45.
 * 用户信息Fragment
 */
public class UserFragment extends BaseFragment implements UserView{

    @Bind(R.id.img_avatar)
    ImageView avatarImg;
    @Bind(R.id.tv_phone)
    TextView phoneTv;
    @Bind(R.id.tv_name)
    TextView nameTv;

    private static final int REQUEST_RESET = 1;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    private void initViews() {
        setUserInfoViews();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(rootView);
        super.onDestroyView();
    }

    @OnClick(R.id.lay_avatar)
    public void showPhotoSelectDialog(View view) {
        showSelectPhotoPath();
    }

    @OnClick(R.id.lay_name)
    public void toSetNamePage(View view) {
        toSetName();
    }

    @OnClick(R.id.lay_password)
    public void toSetPassword(View view) {
        toSetPassword();
    }

    /**
     * 设置界面的用户信息
     */
    private void setUserInfoViews() {
        User user = AppApplication.getInstance().getUserInfo();
        if (user != null) {
            phoneTv.setText(user.getPhone());
            nameTv.setText(user.getName());
        }
    }

    @Override
    public void showSelectPhotoPath() {

    }

    @Override
    public void toTakePhoto() {

    }

    @Override
    public void toSelectPhoto() {

    }

    @Override
    public void toSetName() {
        Intent intent = new Intent(getActivity(), UserSetActivity.class);
        intent.putExtra("type", 0);
        startActivityForResult(intent, REQUEST_RESET);
    }

    @Override
    public void toSetPassword() {
        Intent intent = new Intent(getActivity(), UserSetActivity.class);
        intent.putExtra("type", 1);
        startActivityForResult(intent, REQUEST_RESET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RESET && resultCode == Activity.RESULT_OK) {
            setUserInfoViews();
        }
    }
}
