package com.story.change.android.mvp.util;

/**
 * Created by story on 2017/1/16 0016.
 * 字符串格式间的相互转换
 */
public class StringFormatUtil {

    /**
     * BCD码字节数组转为16进制串字符串(0x12,0x34 -> "1234")
     * @param bytes bytes
     * @return string
     */
    public static String BcdToHexStr(byte[] bytes) {
        char Stra[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
                'B', 'C', 'D', 'E', 'F' };
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append(Stra[(bytes[i] & 0xf0) >> 4]);
            temp.append(Stra[bytes[i] & 0x0f]);
        }
        return temp.toString();
    }

    /**
     * 16进制字符串转为BCD码字节数组("1234" -> 0x12,0x34)
     * @param asc asc
     * @return
     */
    public static byte[] HexStrToBcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

}
