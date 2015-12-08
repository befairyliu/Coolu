/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package com.liu.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liu.coolu.R;

/**
 *  DialogHelper主要进行progressDialog的操作，例如显示、隐藏等。
 */
public class DialogHelper {

    protected static final String TAG = "DialogHelper";
    private Activity mActivity;
    private AlertDialog mAlertDialog;

    public DialogHelper(Activity activity) {
        this.mActivity = activity;
    }

    //show progress dialog
    public void showProgressDialog(boolean showProgressBar, String msg) {
        this.showProgressDialog(msg, true, (DialogInterface.OnCancelListener)null, showProgressBar);
    }

    //show progress dialog
    public void showProgressDialog(String msg) {
        this.showProgressDialog(msg, true, (DialogInterface.OnCancelListener)null, true);
    }

    //show progress dialog
    public void showProgressDialog(final String msg, final boolean cancelable, final DialogInterface.OnCancelListener cancelListener, final boolean showProgressBar) {
        this.dismissProgressDialog();
        this.mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if(DialogHelper.this.mActivity != null && !DialogHelper.this.mActivity.isFinishing()) {
                    DialogHelper.this.mAlertDialog = DialogHelper.this.new LoadingDialog(DialogHelper.this.mActivity);
                    DialogHelper.this.mAlertDialog.setMessage(msg);
                    ((DialogHelper.LoadingDialog)DialogHelper.this.mAlertDialog).setProgressVisiable(showProgressBar);
                    DialogHelper.this.mAlertDialog.setCancelable(cancelable);
                    DialogHelper.this.mAlertDialog.setOnCancelListener(cancelListener);
                    DialogHelper.this.mAlertDialog.setCanceledOnTouchOutside(false);

                    try {
                        DialogHelper.this.mAlertDialog.show();
                    } catch (Throwable e) {
                        Log.e(TAG, "DialogHelper.showProgressDialog()" + e);
                        DialogHelper.this.mAlertDialog = null;
                    }
                }

            }
        });
    }

    //dismiss progress dialog
    public void dismissProgressDialog() {
        this.mActivity.runOnUiThread(new Runnable() {
            public void run() {
                if(DialogHelper.this.mAlertDialog != null
                        && DialogHelper.this.mAlertDialog.isShowing()
                        && !DialogHelper.this.mActivity.isFinishing()) {

                    try {
                        DialogHelper.this.mAlertDialog.dismiss();
                    } catch (Throwable e) {
                        Log.w(TAG, "DialogHelper.dismissProgressDialog(): exception=" + e);
                    } finally {
                        DialogHelper.this.mAlertDialog = null;
                    }
                }

            }
        });
    }

    /**
     *  自定义的progress bar对话框
     */
    public class LoadingDialog extends AlertDialog {

        private ProgressBar mProgress;
        private TextView mMessageView;
        private CharSequence mMessage;
        private boolean mProgressVisiable;

        public LoadingDialog(Context context) {
            super(context);
        }

        protected LoadingDialog(Context context, int theme) {
            super(context, theme);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.loading_dialog);
            this.mProgress = (ProgressBar) findViewById(R.id.progress_bar);
            this.mMessageView = (TextView) findViewById(R.id.dialog_msg);
            this.setMessageAndView();
        }

        private void setMessageAndView() {
            this.mMessageView.setText(this.mMessage);
            if(this.mMessage == null || "".equals(this.mMessage)) {
                this.mMessageView.setVisibility(View.GONE);
            }

            this.mProgress.setVisibility(this.mProgressVisiable ? View.VISIBLE : View.GONE);
        }

        public void setMessage(CharSequence message) {
            this.mMessage = message;
        }

        public void setProgressVisiable(boolean progressVisiable) {
            this.mProgressVisiable = progressVisiable;
        }
    }

}
