/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.liu.utils;

import android.support.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author banghong.lbh
 * @version $$Id: HttpUtils.java, v 0.1 2015/11/20 13:41 banghong.lbh Exp $$
 */
public class HttpUtils {

    private static final int TIMEOUT_IN_MILLIONS = 5000;

    public interface CallBack {
        void onRequestComplete(String result);
    }

    /**
     * 异步的Get请求
     *
     * @param url
     * @param callBack
     */
    public static void doGetAsync(final String url, final CallBack callBack) {
        new Thread() {
            public void run() {
                try{
                    String result = doGet(url);
                    if (callBack != null){
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            };
        }.start();
    }

    /**
     * 异步的Post请求
     * @param url
     * @param params
     * @param callBack
     * @throws Exception
     */
    public static void doPostAsync(final String url, final String params,
                                  final CallBack callBack) throws Exception {
        new Thread() {
            public void run() {
                try {
                    String result = doPost(url, params);
                    if (callBack != null) {
                        callBack.onRequestComplete(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        }.start();

    }

    /**
     * doGet: 以重写 URL 的方式提交数据到服务器，URL 最大长度不超过 1K
     *
     * @param urlPath 服务器地址url(可以携带参数)
     * @return response 服务器返回的response字符串
     */
    public static String doGet(@NonNull String urlPath) {
        HttpURLConnection conn = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //1. 请求服务器
            // 创建 url 路径对象
            URL url = new URL(urlPath);
            // 通过 url 打开网络连接
            conn = (HttpURLConnection) url.openConnection();
            // 设置网络超时时间：5s
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);
            // 设置请求方式为 GET
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection","Keep-Alive");

            // 2.服务器响应请求
            // 响应代码
            int code = conn.getResponseCode();
            // code == 200 ：OK状态，表示正常响应、正常请求。
            if (code == 200) {
                // 从连接中获得响应的输入流
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while((len = is.read(buf)) != -1){
                    baos.write(buf,0,len);
                }
                baos.flush();
                //流数据转换为字符串
                return baos.toString();
            } else {
                throw new RuntimeException("Response code is not 200 ... ");
            }
        } catch (Exception e) {
            return "";
        } finally {
            if(conn != null){
                conn.disconnect();
            }

            try {
                if(is != null){
                    is.close();
                }

                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * doPost: 流的方式，浏览器直接把数据写给服务器
     *
     * @param urlPath  不带参数的url地址
     * @param param post的表单参数字符串
     * @return response 服务器返回的response字符串
     */
    public static String doPost(@NonNull String urlPath, @NonNull String param) {
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            //1.请求服务器
            URL url = new URL(urlPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            // 内容类型 -- 指定是来自表单的数据
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 内容长度
            conn.setRequestProperty("Content-Length", String.valueOf(param.length()));
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(TIMEOUT_IN_MILLIONS);
            conn.setConnectTimeout(TIMEOUT_IN_MILLIONS);

            //发送请求参数
            if(!param.trim().equals("")){
                out = conn.getOutputStream();
                out.write(param.getBytes());
                out.flush();
            }

            //读取响应的输入流
            int code = conn.getResponseCode();
            if(code == 200){
                // 从连接中获得响应的输入流
                in = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = -1;
                byte[] buf = new byte[1024];
                while((len = in.read(buf)) != -1){
                    baos.write(buf,0,len);
                }
                baos.flush();
                //流数据转换为字符串
                return baos.toString();
            } else {
                throw new RuntimeException("Response code is not 200 ... ");
            }
        } catch (Exception e) {
            return "";
        } finally {
            if(conn != null){
                conn.disconnect();
            }

            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
