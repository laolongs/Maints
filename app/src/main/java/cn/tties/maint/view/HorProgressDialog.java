package cn.tties.maint.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import cn.tties.maint.util.StringUtil;

/**
 * 水平进度条
 * author chensi
 */
public class HorProgressDialog {
    private Context mContext;
    private ProgressDialog mDialog;

    public HorProgressDialog(Context context) {
        mContext = context;
    }

    public ProgressDialog loadDialog(String title, int total) {
        return loadDialog(title, total, null, null);
    }

    public ProgressDialog loadDialog(String title, int total, String buttonText1, DialogInterface.OnClickListener onClickListener1l) {
        return loadDialog(title, total, buttonText1, onClickListener1l, null, null);
    }

    public ProgressDialog loadDialog(String title, int total, String buttonText1, DialogInterface.OnClickListener onClickListener1, String buttonText2, DialogInterface.OnClickListener onClickListener2) {
        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle(title);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setMax(total);
        mDialog.setCancelable(false);
        if (!StringUtil.isEmpty(buttonText1)) {
            mDialog.setButton(buttonText1, onClickListener1);
        }
        if (!StringUtil.isEmpty(buttonText2)) {
            mDialog.setButton2(buttonText2, onClickListener2);
        }
        mDialog.show();
        return mDialog;
    }

    public void removeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setValue(int value) {
        if (mDialog != null) {
            mDialog.setProgress(value);
        }
    }
}