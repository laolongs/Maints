package cn.tties.maint.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.tties.maint.R;
import cn.tties.maint.common.MyApplication;


/**
 * 全局取消编辑弹出框
 *
 *
 */
public class AllEditorDialog extends BaseCustomDialog {

    private AllEditorDialog dialog;

    public AllEditorDialog() {
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
        confirm.setText("继续编辑");
        cancel.setText("放弃");
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnClickIsConfirmListener();
                AllEditorDialog.this.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listeners.OnClickIsCancelListener();
                AllEditorDialog.this.dismiss();
            }
        });
        changeShow();
    }
    public void loadDialog(String title, OnClickIsConfirm listener,OnClickIsCancel listeners) {
        dialog = new AllEditorDialog();
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
