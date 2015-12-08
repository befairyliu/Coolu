/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.liu.utils;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author banghong.lbh
 * @version $$Id: JSONUtils.java, v 0.1 2015/11/26 18:50 banghong.lbh Exp $$
 */
public class JSONUtils {

    //拼接JSON字符串
    private static String JointJson(@NonNull String leftJson,
                                    @NonNull String rightJson) throws JSONException {

        JSONObject left = new JSONObject(leftJson);
        JSONObject right = new JSONObject(rightJson);
        Iterator<String> it = right.keys();
        while (it.hasNext()) {
            String key = it.next();
            left.put(key, right.getString(key));
        }

        return left.toString();
    }
}
