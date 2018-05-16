package cn.tties.maint.view;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.common.MyApplication;


/**
 * 更新结果弹出框
 * author chensi
 */
public class InfoDialog extends BaseCustomDialog {

    public InfoDialog() {
        super(MyApplication.curActivity, null);
    }

    TextView textInfo;
    TextView textErrinfo;
    boolean flag;
    String errMsg;
    String msg;

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_info);

        textInfo = findViewById(R.id.text_info);
        textErrinfo = findViewById(R.id.text_errinfo);
        Button confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoDialog.this.dismiss();
            }
        });
        changeShow();
    }

    public void loadDialog(Boolean flag, String msg, String errMsg) {
        InfoDialog dialog = new InfoDialog();
        dialog.errMsg = errMsg;
        dialog.flag = flag;
        dialog.msg = msg;
        dialog.show();
    }

    private void changeShow() {
        textErrinfo.setVisibility(View.INVISIBLE);
        if (flag) {
            textInfo.setText(msg);
            textInfo.setTextColor(ContextCompat.getColor(x.app(), R.color.lightgreen));
        } else {
            textInfo.setText(msg);
            textInfo.setTextColor(ContextCompat.getColor(x.app(), R.color.crimson));
            if (errMsg != null) {
                textErrinfo.setVisibility(View.VISIBLE);
                textErrinfo.setText(errMsg);
            }
        }
    }
}