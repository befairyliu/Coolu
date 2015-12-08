package com.liu.utils;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * 常用单位转换的辅助类
 */
public class DisplayUtils {

    /**
     * 获取屏幕的像素点坐标
     *
     * @param context 上下文
     * @return 点
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        Logger.get().i("DisplayUtils","Screen---Width = " + w_screen + " Height = " + h_screen + " densityDpi = "
                + dm.densityDpi);
        return new Point(w_screen, h_screen);

    }

    /**
     * 计算屏幕的高/宽的比率
     *
     * @param context 上下文
     * @return 比率
     */
    public static float getScreenRate(Context context) {
        Point P = getScreenMetrics(context);
        float H = P.y;
        float W = P.x;
        return (H / W);
    }

    /**
     * dip2px
     *
     * @param context  上下文
     * @param dipValue dip值
     * @return px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px2dip
     *
     * @param context 上下文
     * @param pxValue px值
     * @return dip值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px2sp
     *
     * @param context 上下文
     * @param pxValue px值
     * @return sp值
     */
    public static float px2sp(Context context, float pxValue){

        return (pxValue / context.getResources().getDisplayMetrics().scaledDensity);
    }


    /**
     * sp2px
     *
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spValue, context.getResources().getDisplayMetrics());
    }

    /**
     * dp2px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

}
