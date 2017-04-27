package com.story.change.android.mvp.presenter.user;

import android.content.Context;
import com.story.change.android.mvp.R;
import com.story.change.android.mvp.bean.base.ResponseBase;
import com.story.change.android.mvp.bean.user.User;
import com.story.change.android.mvp.biz.RetrofitUtil;
import com.story.change.android.mvp.biz.user.IUserBiz;
import com.story.change.android.mvp.presenter.base.BasePresenter;
import com.story.change.android.mvp.ui.user.SignView;
import com.story.change.android.mvp.util.CountDownTimerUtils;
import com.story.change.android.mvp.util.CountTimerListener;
import com.story.change.android.mvp.util.StringCheckUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by story on 2017/4/25 0025 下午 4:33.
 * 注册Presenter定义
 */
public class SignPresenter extends BasePresenter {

    private Context context;
    private IUserBiz userBiz;
    private SignView signView;
    private CountDownTimerUtils countTimer;

    public SignPresenter(Context context, SignView signView) {
        this.context = context;
        this.signView = signView;
        userBiz = RetrofitUtil.getRetrofit().create(IUserBiz.class);
    }

    /**
     * 获取手机验证码
     */
    public void getCheck() {
        String userPhone = signView.getUserPhone();
        if (StringCheckUtil.isNullAndEmpty(userPhone)) {
            signView.alertMessage(context.getString(R.string.login_error_phone_null));
        } else {
            signView.showLoading(null, 0);
            userBiz.userGetCheck(userPhone)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase<String>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase<String> stringResponseBase) {
                            signView.hideLoading();
                            if (!"N".equals(stringResponseBase.getRspType())) {
                                signView.alertMessage(stringResponseBase.getRspMsg());
                            } else {
                                if (countTimer != null) {
                                    countTimer.cancel();
                                }
                                countTimer = new CountDownTimerUtils(59000, 1000, countTimerListener);
                                countTimer.start();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            signView.hideLoading();
                            signView.alertMessage(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    /**
     * 注册
     */
    public void sign() {
        String userPhone = signView.getUserPhone();
        String phoneCheck = signView.getPhoneCheck();
        if (StringCheckUtil.isNullAndEmpty(userPhone)) {
            signView.alertMessage(context.getString(R.string.login_error_phone_null));
        } else if (StringCheckUtil.isNullAndEmpty(phoneCheck)) {
            signView.alertMessage(context.getString(R.string.login_error_check_null));
        } else {
            signView.showLoading(null, 0);
            userBiz.userRegister(userPhone, phoneCheck)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBase<User>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBase<User> userResponseBase) {
                            signView.hideLoading();
                            if ("N".equals(userResponseBase.getRspType())) {
                                // TODO 保存user数据
                                signView.toMainActivity(null);
                            } else {
                                signView.alertMessage(userResponseBase.getRspMsg());
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            signView.hideLoading();
                            signView.alertMessage(e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    //倒计时处理
    private CountTimerListener countTimerListener = new CountTimerListener() {
        @Override
        public void onTick(int second) {
            signView.setCheckBtnStatus(second);
        }

        @Override
        public void onFinish() {
            signView.setCheckBtnStatus(0);
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
