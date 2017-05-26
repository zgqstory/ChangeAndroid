package com.story.change.android.mvp.presenter.user;

import android.content.Context;
import com.story.change.android.mvp.AppApplication;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.base.ResponseBase;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.biz.RetrofitUtil;
import com.story.change.android.mvp.biz.user.IUserBiz;
import com.story.change.android.mvp.encrypt.MD5Util;
import com.story.change.android.mvp.presenter.base.BasePresenter;
import com.story.change.android.mvp.ui.user.UserSetView;
import com.story.change.android.mvp.util.StringCheckUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by story on 2017/5/8 0008 上午 9:48.
 * 用户信息设置Presenter
 */
public class UserSetPresenter extends BasePresenter {

    private Context context;
    private IUserBiz userBiz;
    private UserSetView userSetView;

    public UserSetPresenter(Context context, UserSetView userSetView) {
        this.context = context;
        this.userSetView = userSetView;
        userBiz = RetrofitUtil.getRetrofit().create(IUserBiz.class);
    }

    /**
     * 设置用户信息
     * @param type 信息类型：0，用户名；1，密码
     */
    public void setUserInfo(int type) {
        String userName = userSetView.getUserName();
        String pwdNow = userSetView.getPwdNow();
        String pwdNew = userSetView.getPwdNew();

        if (type == 0 && StringCheckUtil.isNullAndEmpty(userName)) {
            userSetView.alertMessage(context.getString(R.string.user_settings_name_null));
        } else if (type == 1 && StringCheckUtil.isNullAndEmpty(pwdNow)) {
            userSetView.alertMessage(context.getString(R.string.user_settings_error_pwd_now_null));
        } else if (type == 1 && StringCheckUtil.isNullAndEmpty(pwdNew)) {
            userSetView.alertMessage(context.getString(R.string.user_settings_error_pwd_new_null));
        } else if (type == 0 || type == 1) {
            String data;
            if (type == 0) {
                data = userName;
            } else {
                data = MD5Util.getMD5(pwdNew);
            }
            userSetView.showLoading(null, 0);
            userBiz.updateUserData(type, AppApplication.getInstance().getUserInfo().getPhone(), data, MD5Util.getMD5(pwdNow))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase<User>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase<User> userResponseBase) {
                            userSetView.hideLoading();
                            if (!"N".equals(userResponseBase.getRspType())) {
                                userSetView.alertMessage(userResponseBase.getRspMsg());
                            } else {
                                AppApplication.getInstance().setUserInfo(userResponseBase.getRspData());
                                userSetView.back();
                                userSetView.toastMessage(context.getString(R.string.user_settings_success));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            userSetView.hideLoading();
                            userSetView.alertMessage(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }
}
