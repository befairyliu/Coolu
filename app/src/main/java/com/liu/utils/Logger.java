package com.liu.utils;

import android.util.Log;

public class Logger {

    public final static String PREFIX_TAG = "CreditSdk:";
    private static final Logger logger = new Logger();
    private static boolean DEBUG = true;

    static String makeTag(String tag) {
        StringBuilder builder = new StringBuilder();
        builder.append(PREFIX_TAG).append(tag);
        return builder.toString();
    }

    private Logger() {}

    public static final Logger get() {
        return logger;
    }


    public void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(makeTag(tag), msg);
        }
    }

    public void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(makeTag(tag), msg);
        }
    }

    public void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(makeTag(tag), msg);
        }
    }

    public void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(makeTag(tag), msg);
        }
    }

    public void e(String tag, String msg) {
        Log.e(makeTag(tag), msg);
    }

    public void e(String tag, Throwable t) {
        Log.e(makeTag(tag), makeTag(tag), t);
    }

}