package com.liu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by banghong.lbh on 2015/9/25.
 */
public class Utils {

    private static final String CHARSET = "UTF-8";
    private static Typeface font;
    /**
     * decode the input string
     *
     * @param encodedString  The encoded string.
     * @return plaintext     The decoded string.
     */
    public static String decode(String encodedString){

        String plaintext = "";
        try {
            plaintext = URLDecoder.decode(encodedString, CHARSET);
        } catch (Exception e){
            e.printStackTrace();
        }
        return plaintext;
    }

    /**
     * encode the input string
     *
     * @param plaintext  The plaintext string.
     * @return encodedString     The encoded string.
     */
    public static String encode(String plaintext){

        String encodedString = "";
        try {
            encodedString = URLEncoder.encode(plaintext, CHARSET);
        } catch (Exception e){
            e.printStackTrace();
        }
        return encodedString;
    }

    /**
     * grey the input bitmap
     *
     * @param bitmap  The input bitmap
     * @return greyImage   The output bitmap
     */
    public static Bitmap greyImage(Bitmap bitmap) {
        //Bitmap bitmap = BitmapFactory.decodeFile(source);
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Bitmap greyImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(greyImage);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
            paint.setColorFilter(colorMatrixFilter);
            canvas.drawBitmap(bitmap, 0, 0, paint);

            return greyImage;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * String convert to bitmap
     *
     * @param stream  The string(base64) will be convert to bitmap
     */
    public Bitmap str2Bitmap(String stream){

        Bitmap bitmap=null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(stream, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            Log.e("TAG", "The string stream convert to bitmap failed.");
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Bitmap convert to String
     *
     * @param bitmap  The bitmap will be convert to string
     */
    public String bitmap2String(Bitmap bitmap){

        String string=null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;
    }

    /**
     * dp convert to px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px convert to dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /*public static float sp2px(float spValue, int type) {
        switch (type) {
            case CHINESE:
                return spValue * scaledDensity;
            case NUMBER_OR_CHARACTER:
                return spValue * scaledDensity * 10.0f / 18.0f;
            default:
                return spValue * scaledDensity;
        }
    }*/


    /**
     * Get the font for svg icon
     *
     * @param context 
     */
    public static Typeface getFont(Context context) {
        //get the font for svg
        if (font == null) {
            synchronized (context) {
                if (font == null) {
                    font = Typeface.createFromAsset(context.getAssets(), "iconfont/iconfont.ttf");
                }
            }
        }
        return font;
    }
}
