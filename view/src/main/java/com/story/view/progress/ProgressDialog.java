package com.story.view.progress;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.TextView;
import com.story.view.R;

/**
 * Created by story on 2017/4/25 0025 下午 2:39.
 * 自定义进度框，用于网络请求等延时操作的等待对话框
 */
public class ProgressDialog extends Dialog {

    private Context context;
    private TextView textView;//加载中提示文字
    private TextView timeTv;//倒计时数字
    private TimeCount timeCount;//倒计时
    private boolean couldCancel = false;//是否可以取消，默认不可取消

    @SuppressLint("InflateParams")
    public ProgressDialog(Context context) {
        super(context, R.style.ProgressDialogStyle);
        this.context = context;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_progress_dialog_main,null);
        textView = (TextView) view.findViewById(R.id.tv_progress_show);
        timeTv = (TextView) view.findViewById(R.id.tv_progress_time);
        setContentView(view,layoutParams);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 显示对话框
     * @param message 提示内容
     */
    public void show(String message) {
        textView.setText(message == null ? context.getResources().getString(R.string.progress_progress_dialog_loading) : message);
        if (timeCount != null) {
            timeCount.cancel();
            timeTv.setVisibility(View.GONE);
        }
        super.show();
    }

    /**
     * 显示对话框和倒计时
     * @param message 提示内容
     * @param time 倒计时(秒)
     */
    public void show(String message, int time) {
        textView.setText(message == null ? context.getResources().getString(R.string.progress_progress_dialog_loading) : message);
        timeCount = new TimeCount(time * 1000, 1000);
        timeTv.setVisibility(View.VISIBLE);
        timeTv.setText(String.format(context.getResources().getString(R.string.progress_progress_dialog_time), time));
        timeCount.start();
        super.show();
    }

    /**
     * 显示对话框和倒计时
     * @param time 倒计时(秒)
     */
    public void show(int time) {
        timeCount = new TimeCount(time * 1000, 1000);
        timeTv.setVisibility(View.VISIBLE);
        timeTv.setText(String.format(context.getResources().getString(R.string.progress_progress_dialog_time), time));
        timeCount.start();
        super.show();
    }

    @Override
    public void cancel() {
        super.cancel();
        if (timeCount != null) {
            timeCount.cancel();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (timeCount != null) {
            timeCount.cancel();
        }
    }

    public void delayCancel() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cancel();
            }
        }, 1000);
    }

    /**
     * 是否可以取消
     */
    public void setCouldCancel(boolean could) {
        this.couldCancel = could;
    }


    /**
     * 自定义倒计时基础类，防止系统倒计时类不准确问题(此倒计时要求onTick不能做延时操作)
     */
    private abstract class CountTimerStory {

        private static final int MSG = 1;

        private final long mMillisInFuture;//计时总时间
        private final long mCountdownInterval;//每次变化时间
        private boolean mCancelled = false;//计时器开始标志

        public CountTimerStory(long millisInFuture, long countDownInterval) {
            mMillisInFuture = millisInFuture;
            mCountdownInterval = countDownInterval;
        }

        /**
         * 取消倒计时
         */
        public synchronized final void cancel() {
            mCancelled = true;
            mHandler.removeMessages(MSG);
        }

        /**
         * 开始倒计时
         */
        public synchronized final CountTimerStory start() {
            mCancelled = false;
            if (mMillisInFuture <= 0) {
                onFinish();
            } else {
                mHandler.sendMessage(mHandler.obtainMessage(MSG, mMillisInFuture));
            }
            return this;
        }

        public abstract void onTick(long millisUntilFinished);

        public abstract void onFinish();

        private Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                synchronized (CountTimerStory.this) {
                    if (mCancelled) {
                        return;
                    }
                    final long millisRest = (long) msg.obj;
                    if (millisRest < mCountdownInterval && millisRest > 0) {
                        // 剩余时间小于mCountdownInterval，不执行onTick延迟millisLeft
                        sendMessageDelayed(obtainMessage(MSG, millisRest - mCountdownInterval), millisRest);
                    } else if (millisRest <= 0) {
                        // 剩余时间小于等于0，结束
                        onFinish();
                    } else {
                        //剩余时间大于mCountdownInterval，执行一次onTick然后延迟mCountdownInterval
                        onTick(millisRest);
                        sendMessageDelayed(obtainMessage(MSG, millisRest - mCountdownInterval), mCountdownInterval);
                    }
                }
            }
        };
    }

    /**
     * 自定义倒计时
     */
    private class TimeCount extends CountTimerStory {
        /**
         * 计时结束
         */
        @Override
        public void onFinish() {
            dismiss();
        }

        /**
         * 计时过程
         * @param millisUntilFinished 完成时间
         */
        @Override
        public void onTick(long millisUntilFinished) {
            timeTv.setText(String.format(context.getResources().getString(R.string.progress_progress_dialog_time), millisUntilFinished/1000));
        }

        /**
         * @param millisInFuture 总时长
         * @param countDownInterval 计时间隔
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !couldCancel) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
