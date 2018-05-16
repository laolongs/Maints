package cn.tties.maint.view;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.common.MyApplication;

/**
 * Created by Justin on 2018/1/12.
 */

public class MeasureDialog extends BaseCustomDialog {

    public MeasureDialog() {
        super(MyApplication.curActivity, null);
    }

    TextView textInfo;
    TextView textErrinfo;
    boolean flag;
    String errMsg;

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
                MeasureDialog.this.dismiss();
            }
        });
        changeShow();
//        super.setContentView();
    }

    public void loadDialog(String errMsg, boolean flag) {
        this.errMsg = errMsg;
        this.flag = flag;
        if (textInfo != null) {
            changeShow();
        }
        this.show();
    }

    private void changeShow() {
        textErrinfo.setVisibility(View.INVISIBLE);
        if (flag) {
            textInfo.setText("召测成功");
            textInfo.setTextColor(ContextCompat.getColor(x.app(), R.color.lightgreen));
        } else {
            textInfo.setText("召测失败");
            textInfo.setTextColor(ContextCompat.getColor(x.app(), R.color.crimson));
            if (errMsg != null) {
                textErrinfo.setVisibility(View.VISIBLE);
                textErrinfo.setText(errMsg);
            }
        }
    }
}
