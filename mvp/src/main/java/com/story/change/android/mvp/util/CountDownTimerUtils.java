package com.story.change.android.mvp.util;

/**
 * Created by story on 2017/4/12 0012 下午 8:43.
 * 倒计时工具类
 */
public class CountDownTimerUtils extends CountTimerBase {

    private CountTimerListener listener;

    public CountDownTimerUtils(long millisInFuture, long countDownInterval, CountTimerListener listener) {
        super(millisInFuture, countDownInterval);
        this.listener = listener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (listener != null) {
            System.out.println("millisUntilFinished : " + millisUntilFinished);
            if (millisUntilFinished > 0) {
                listener.onTick((int) (millisUntilFinished/1000));
            }
        }
    }

    @Override
    public void onFinish() {
        if (listener != null) {
            System.out.println("finish");
            listener.onFinish();
        }
    }
}
