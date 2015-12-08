/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.liu.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.util.List;

/**
 *  BaseActivity是该demo工程中具体业务activity的基础类，
 *  主要实现了进度条的显示和取消。如果后续有其他公共部分，也会添加进来。
 */
public class BaseActivity extends FragmentActivity {

    private static final String TAG = "BaseActivity";
    private long startProgress = 0;
    //最小加载时间1500ms
    public static final long MIN_LOADING_TIME = 1500;
    private DialogHelper dialogHelper;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogHelper = new DialogHelper(this);
    }

    //显示进度条
    public void showProgress(String msg) {
        startProgress = System.currentTimeMillis();
        dialogHelper.showProgressDialog(msg);
    }

    //取消进度条
    public void dismissProgress() {
        long timeLimit = startProgress + MIN_LOADING_TIME;
        long current = System.currentTimeMillis();
        if (current >= timeLimit) {
            dialogHelper.dismissProgressDialog();
            startProgress = 0;
        } else {
            handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startProgress = 0;
                    dismissProgress();
                }
            },(timeLimit - current));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        int index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                Log.w(TAG, "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }
    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode, Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }
}
