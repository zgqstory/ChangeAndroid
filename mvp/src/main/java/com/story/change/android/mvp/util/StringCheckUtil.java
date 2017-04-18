package com.story.change.android.mvp.util;

/**
 * Created by story on 2017/4/12 0012 下午 1:57.
 * 字符串校验工具类
 */
public class StringCheckUtil {

    public static boolean isNullAndEmpty(String string) {
        if (string == null || "".equals(string)) {
            return true;
        } else  {
            return false;
        }
    }
}
