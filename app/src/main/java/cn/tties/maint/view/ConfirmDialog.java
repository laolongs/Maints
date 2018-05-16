package cn.tties.maint.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import cn.tties.maint.util.StringUtil;


/**
 * 确认弹出框
 * author chensi
 */
public class ConfirmDialog {
    private Context mContext;
    private AlertDialog mDialog;

    public ConfirmDialog(Context context) {
        mContext = context;
    }

    public void loadDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, final DialogInterface.OnClickListener onClickListener) {
        loadDialog(title, message, onClickListener, false);
    }

    public void loadDialog(String title, String message, final DialogInterface.OnClickListener onClickListener, Boolean btn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton("确定", onClickListener);
        if (!btn) {
            builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {//响应事件
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, String btn1, final DialogInterface.OnClickListener onClickListener1) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton(btn1, onClickListener1);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, String btn1, String btn2, final DialogInterface.OnClickListener onClickListener1, final DialogInterface.OnClickListener onClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton(btn1, onClickListener1);
        builder.setNegativeButton(btn2, onClickListener2);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void loadDialog(String title, String message, String btn1, String btn2, String btn3,
                           final DialogInterface.OnClickListener onClickListener1,
                           final DialogInterface.OnClickListener onClickListener2,
                           final DialogInterface.OnClickListener onClickListener3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        if (!StringUtil.isEmpty(message)) {
            builder.setMessage(message);
        }
        builder.setPositiveButton(btn1, onClickListener1);
        builder.setNegativeButton(btn2, onClickListener2);
        builder.setNeutralButton(btn3, onClickListener3);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void removeDialog() {
        mDialog.dismiss();
    }

}