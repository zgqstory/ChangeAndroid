package com.story.view.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import com.story.view.R;

/**
 * Created by story on 2017/5/3 0003 上午 9:55.
 * 自定义输入框，继承自EditText，具有清除和显示密码功能，两种功能不能同时出现（没有给设置方法，只能在布局中设置，且focusable和focusableInTouchMode无效）
 */
public class ClearEditText extends EditText implements View.OnFocusChangeListener, TextWatcher{

    private int showType;//显示模式：0，正常；1，带删除功能；2，带显示密码功能
    private Drawable showDrawable = null;
    private boolean hasFocus;//控件是否有焦点
    private boolean showPlain;//是否显示明文

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * 初始化操作
     */
    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
            showType = t.getInt(R.styleable.ClearEditText_clear_et_show_type, 0);
            t.recycle();
        }

        //设置两个功能图片的Drawable,getIntrinsicWidth()获取显示出来的大小而不是原图片的大小
        if (showType == 0) {
            showDrawable = null;
        } else if (showType == 1) {
            showDrawable = ContextCompat.getDrawable(context, R.drawable.clear_et_delete);
            showDrawable.setBounds(0, 0, showDrawable.getIntrinsicWidth(), showDrawable.getIntrinsicHeight());
            setIconVisible(false);//若是删除默认隐藏
        } else if (showType == 2) {
            showDrawable = ContextCompat.getDrawable(context, R.drawable.clear_et_show);
            showDrawable.setBounds(0, 0, showDrawable.getIntrinsicWidth(), showDrawable.getIntrinsicHeight());
        }

        //因无法获取焦点问题，将focusable和focusableInTouchMode强制设置为true
        setFocusable(true);
        setFocusableInTouchMode(true);

        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                //getTotalPaddingRight()图标左边缘至控件右边缘的距离
                //getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
                //getWidth() - getPaddingRight()表示最左边到图标右边缘的位置
                boolean touchable = (event.getX() > (getWidth() - getTotalPaddingRight()))
                        && (event.getX() < (getWidth() - getPaddingRight()));
                if (touchable) {
                    if (showType == 1) {
                        this.setText("");
                    } else if (showType == 2) {
                        if (showPlain) {
                            this.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            showPlain = false;
                        } else {
                            this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            showPlain = true;
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (showType == 1 || showType == 2) {
            if (hasFocus) {
                setIconVisible(getText().length() > 0);
            } else {
                setIconVisible(false);
            }
        }

    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (hasFocus) {
            if (showType == 1 || showType == 2) {
                setIconVisible(s.length() > 0);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    /**
     * 设置图片的显示
     */
    private void setIconVisible(boolean visible) {
        Drawable right = visible ? showDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

}
