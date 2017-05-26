package com.story.change.android.mvp.ui.common;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.ui.base.BaseActivity;
import com.story.view.image.RectOrCircleImageView;

/**
 * Created by story on 2017/5/11 0011 上午 11:25.
 * 图片裁剪页面
 */
public class ImageCropActivity extends BaseActivity {

    @Bind(R.id.img_content)
    RectOrCircleImageView contentImg;
    @Bind(R.id.tv_common_title_name)
    TextView titleTv;
    @Bind(R.id.tv_common_title_right)
    TextView rightTv;

    private Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_corp);
        ButterKnife.bind(this);
        getDataFromParent();
        initViews();
    }

    private void getDataFromParent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uri = bundle.getParcelable("uri");
        }
    }

    private void initViews() {
        titleTv.setText(getString(R.string.image_crop_title));
        rightTv.setVisibility(View.VISIBLE);
        if (uri != null) {
            Picasso.with(this).load(uri).into(contentImg);
        }
    }

    @OnClick(R.id.img_common_title_back)
    public void back(View view) {
        finish();
    }

    /**
     * 确定裁剪
     * @param view
     */
    @OnClick(R.id.tv_common_title_right)
    public void sure(View view) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
