package com.liu.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ares on 2015/8/20.
 */
public class AssetsUtils {

    public static Drawable getDrawableFromAssets(Context ctx, String path) {
        InputStream is = null;
        try {
            is = ctx.getAssets().open(path);
            TypedValue value = new TypedValue();
            value.density = 160;
            return Drawable.createFromResourceStream(ctx.getResources(), value, is, path);
        } catch (IOException e) {
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
