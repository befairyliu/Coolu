/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.liu.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast工具类
 */
public class ToastUtils {

    /**
     * 输出toast
     */
    public static void showToast(Context context, String str) {
        if (context != null) {
            Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            // 可以控制toast显示的位置
            toast.setGravity(Gravity.TOP, 0, 30);
            toast.show();
        }
    }
}
