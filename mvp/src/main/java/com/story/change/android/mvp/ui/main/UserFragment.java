package com.story.change.android.mvp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import com.story.change.android.mvp.AppApplication;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.ui.base.BaseFragment;
import com.story.change.android.mvp.ui.common.ImageCropActivity;
import com.story.change.android.mvp.ui.user.UserSetActivity;
import com.story.change.android.mvp.util.StringCheckUtil;
import com.story.view.alert_view_ios.AlertView;
import com.story.view.alert_view_ios.OnItemClickListener;
import com.story.view.alert_view_ios.Style;

import java.io.File;

/**
 * Created by story on 2017/4/28 0028 下午 4:45.
 * 用户信息Fragment
 */
public class UserFragment extends BaseFragment implements UserView {

    @Bind(R.id.img_avatar)
    ImageView avatarImg;
    @Bind(R.id.tv_phone)
    TextView phoneTv;
    @Bind(R.id.tv_name)
    TextView nameTv;
    @Bind(R.id.tv_common_title_name)
    TextView titleTv;
    @Bind(R.id.img_common_title_back)
    ImageView backImg;

    private static final int REQUEST_RESET = 1;
    private static final int REQUEST_PHOTO_TAKE = 2;
    private static final int REQUEST_PHOTO_ELBUM = 3;
    private static final int REQUEST_PHOTO_CROP = 4;

    private View rootView;
    private AlertView selectPhotoDialog;
    private String photoPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        return rootView;
    }

    private void initViews() {
        titleTv.setText(getString(R.string.user_settings_title));
        backImg.setVisibility(View.INVISIBLE);
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
            if (!StringCheckUtil.isNullAndEmpty(user.getAvatar())) {
                Picasso.with(getActivity()).load(user.getAvatar()).error(R.drawable.user_avatar_default).into(avatarImg);
            }
        }
    }

    @Override
    public void showSelectPhotoPath() {
        if (selectPhotoDialog == null) {
            selectPhotoDialog = new AlertView(null, null, getString(R.string.cancel), null, new String[]{getString(R.string.picture_from_take), getString(R.string.picture_from_album)}, getActivity(), Style.ActionSheet, new OnItemClickListener() {
                @Override
                public void onItemClick(Object object, int position) {
                    if (position == 0) {
                        toTakePhoto();
                    } else if (position == 1) {
                        toSelectPhoto();
                    }
                }
            });
        }
        selectPhotoDialog.show();
    }

    @Override
    public void toTakePhoto() {
        String dirPath = Environment.getExternalStorageDirectory() + "/Change/.img/";
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        photoPath = dirPath + System.currentTimeMillis() + ".jpg";
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(photoPath)));
        startActivityForResult(takePhotoIntent, REQUEST_PHOTO_TAKE);
    }

    @Override
    public void toSelectPhoto() {
        Intent photoIntent = new Intent();
        photoIntent.setAction(Intent.ACTION_PICK);
        photoIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(photoIntent, getString(R.string.picture_album_title)), REQUEST_PHOTO_ELBUM);
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
        } else if (requestCode == REQUEST_PHOTO_TAKE && resultCode == Activity.RESULT_OK) {
            // path转化为uri
            Uri uri = Uri.parse(photoPath);
            toCropPage(uri);
        } else if (requestCode == REQUEST_PHOTO_ELBUM && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            toCropPage(uri);
        }
    }

    /**
     * 进入图片裁剪页面
     */
    private void toCropPage(Uri uri) {
        Intent intent = new Intent(getActivity(), ImageCropActivity.class);
        intent.putExtra("uri", uri);
        startActivityForResult(intent, REQUEST_PHOTO_CROP);
    }
}
