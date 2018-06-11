package cn.tties.maint.view;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.common.MyApplication;


/**
 * 全局取消弹出框
 *
 */
public class AllCancelDialog extends BaseCustomDialog {

    private AllCancelDialog dialog;

    public AllCancelDialog() {
        super(MyApplication.curActivity, null);
    }

    TextView texttitle;
    String msg;
    OnClickIsConfirm listener;
    OnClickIsCancel listeners;
    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_cancel);
        texttitle = findViewById(R.id.text_title);
        Button confirm = findViewById(R.id.btn_confirm);
        Button cancel = findViewById(R.id.btn_cancel);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickIsConfirmListener();
                AllCancelDialog.this.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listeners.OnClickIsCancelListener();
                AllCancelDialog.this.dismiss();
            }
        });
        changeShow();
    }
    public void loadDialog(String title, OnClickIsConfirm listener,OnClickIsCancel listeners) {
        dialog = new AllCancelDialog();
        dialog.listener=listener;
        dialog.listeners=listeners;
        dialog.msg=title;
        dialog.show();
    }
    public  interface OnClickIsConfirm{
        public void OnClickIsConfirmListener();
    }
    public  interface OnClickIsCancel{
        public void OnClickIsCancelListener();
    }
    private void changeShow() {
        texttitle.setText(msg);
    }
}
