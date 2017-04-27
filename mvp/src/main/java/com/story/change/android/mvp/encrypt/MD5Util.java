package com.story.change.android.mvp.encrypt;

import com.story.change.android.mvp.util.StringFormatUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by story on 2017/1/16 0016.
 * MD5加密实现逻辑
 */
public class MD5Util {

    /**
     * 做MD5加密
     * @param str str
     * @return str
     */
    public static String getMD5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            md.digest();
            return StringFormatUtil.BcdToHexStr(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
