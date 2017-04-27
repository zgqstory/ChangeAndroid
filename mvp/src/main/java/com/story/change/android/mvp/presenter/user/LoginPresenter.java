package com.story.change.android.mvp.presenter.user;

import android.content.Context;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.base.ResponseBase;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.biz.RetrofitUtil;
import com.story.change.android.mvp.biz.user.IUserBiz;
import com.story.change.android.mvp.encrypt.MD5Util;
import com.story.change.android.mvp.presenter.base.BasePresenter;
import com.story.change.android.mvp.ui.user.LoginView;
import com.story.change.android.mvp.util.CountDownTimerUtils;
import com.story.change.android.mvp.util.CountTimerListener;
import com.story.change.android.mvp.util.StringCheckUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by story on 2017/3/10 0010 下午 4:34.
 * 登录Presenter定义
 */
public class LoginPresenter extends BasePresenter {

    private Context context;
    private IUserBiz userBiz;
    private LoginView loginView;
    private CountDownTimerUtils countTimer;

    public LoginPresenter(Context context, LoginView view) {
        this.context = context;
        this.loginView = view;
        this.userBiz = RetrofitUtil.getRetrofit().create(IUserBiz.class);
    }

    /**
     * 获取手机验证码
     */
    public void getCheck() {
        String userPhone = loginView.getUserName();
        if (StringCheckUtil.isNullAndEmpty(userPhone)) {
            loginView.alertMessage(context.getString(R.string.login_error_phone_null));
        } else {
            loginView.showLoading(null, 0);
            userBiz.userGetCheck(userPhone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase<String>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase<String> stringResponseBase) {
                            loginView.hideLoading();
                            if ("N".equals(stringResponseBase.getRspType())) {
                                if (countTimer != null) {
                                    countTimer.cancel();
                                }
                                countTimer = new CountDownTimerUtils(59000, 1000, countTimerListener);
                                countTimer.start();
                            } else {
                                loginView.alertMessage(stringResponseBase.getRspMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            loginView.hideLoading();
                            loginView.alertMessage(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    /**
     * 手机号+验证码登录
     */
    public void loginByPhone() {
        String userPhone = loginView.getUserName();
        String userCheck = loginView.getCheck();
        if (StringCheckUtil.isNullAndEmpty(userPhone)) {
            loginView.alertMessage(context.getString(R.string.login_error_phone_null));
        } else if (StringCheckUtil.isNullAndEmpty(userCheck)) {
            loginView.alertMessage(context.getString(R.string.login_error_check_null));
        } else {
            loginView.showLoading(null, 0);
            userBiz.userLoginByPhone(userPhone, userCheck)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loginObserver);
        }
    }

    /**
     * 用户名+密码登录
     */
    public void loginByPwd() {
        String userName = loginView.getUserName();
        String userPwd = loginView.getPassWord();
        if (StringCheckUtil.isNullAndEmpty(userName)) {
            loginView.alertMessage(context.getString(R.string.login_error_name_null));
        } else if (StringCheckUtil.isNullAndEmpty(userPwd)) {
            loginView.alertMessage(context.getString(R.string.login_error_pwd_null));
        } else {
            loginView.showLoading(null, 0);
            userBiz.userLoginByPwd(userName, MD5Util.getMD5(userPwd))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loginObserver);
        }
    }

    //登录回调处理
    private Observer<ResponseBase<User>> loginObserver = new Observer<ResponseBase<User>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBase<User> userResponseBase) {
            loginView.hideLoading();
            if ("N".equals(userResponseBase.getRspType())) {
                // TODO 保存user数据
                loginView.toMainActivity(null);
            } else {
                loginView.alertMessage(userResponseBase.getRspMsg());
            }
        }

        @Override
        public void onError(Throwable e) {
            loginView.hideLoading();
            loginView.alertMessage(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    };

    //倒计时处理
    private CountTimerListener countTimerListener = new CountTimerListener() {
        @Override
        public void onTick(int second) {
            loginView.setCheckBtnStatus(second);
        }

        @Override
        public void onFinish() {
            loginView.setCheckBtnStatus(0);
        }
    };

    @Override
    public void releaseData() {
        super.releaseData();
        if (countTimer != null) {
            countTimer.cancel();
        }
    }
}
