package com.story.change.android.mvp.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by story on 2017/4/18 0018 下午 1:16.
 * 自定义CountDownTimer，解决延迟问题(此倒计时要求onTick不能做延时操作)
 */
public abstract class CountTimerBase {
    /**
     * Millis since epoch when alarm should stop.
     */
    private final long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private final long mCountdownInterval;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    /**
     * @param millisInFuture The number of millis in the future from the call
     *   to {@link #start()} until the countdown is done and {@link #onFinish()}
     *   is called.
     * @param countDownInterval The interval along the way to receive
     *   {@link #onTick(long)} callbacks.
     */
    public CountTimerBase(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CountTimerBase start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mHandler.sendMessage(mHandler.obtainMessage(MSG, mMillisInFuture));
        return this;
    }


    /**
     * Callback fired on regular interval.
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;


    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountTimerBase.this) {
                if (mCancelled) {
                    return;
                }

                final long millisLeft = (long) msg.obj;

                if (millisLeft <= 0) {
                    // 剩余时间小于等于0，结束
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {
                    // 剩余时间小于mCountdownInterval，不执行onTick延迟millisLeft
                    sendMessageDelayed(obtainMessage(MSG, millisLeft - mCountdownInterval), millisLeft);
                } else {
                    //剩余时间大于mCountdownInterval，执行一次onTick然后延迟mCountdownInterval
                    onTick(millisLeft);
                    sendMessageDelayed(obtainMessage(MSG, millisLeft - mCountdownInterval), mCountdownInterval);
                }
            }
        }
    };
}
