package com.liu.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SignUtils {

    private static final String TAG = "Coolu_"+SignUtils.class.getSimpleName();
    /**
     * SHA1签名
     *
     * @param inStr 要签名的数据
     * @return 签名后字符串
     */
    public static String SHA1(String inStr) {
        MessageDigest md = null;
        String outStr = null;
        try {
            md = MessageDigest.getInstance("SHA-1"); //选择SHA-1，也可以选择MD5
            byte[] digest = md.digest(inStr.getBytes()); //返回的是byet[]，要转化为String存储比较方便
            outStr = bytetoString(digest);
        } catch (NoSuchAlgorithmException nsae) {
            Logger.get().e(TAG, nsae.toString());
        }
        return outStr;
    }

    private static String bytetoString(byte[] digest) {
        String str = "";
        String tempStr = "";

        for (int i = 1; i < digest.length; i++) {
            tempStr = (Integer.toHexString(digest[i] & 0xff));
            if (tempStr.length() == 1) {
                str = str + "0" + tempStr;
            } else {
                str = str + tempStr;
            }
        }
        return str.toLowerCase();
    }

    /**
     * MD5签名
     *
     * @param str 要签名的字符串
     * @return 签名后字符串
     */
    public static String MD5(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes("UTF-8"));
            //md.update(Contacts.DETECT_FACE_MD5_SALT.getBytes());
            byte tmp[] = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                sb.append(String.format("%02x", tmp[i]));
            }
            return sb.toString();
        } catch (Exception e) {
            Logger.get().e(TAG, e.toString());
            return null;
        }
    }
}
