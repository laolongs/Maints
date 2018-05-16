package cn.tties.maint.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.xutils.x;

import java.util.List;

import cn.tties.maint.R;

public class BaseCustomDialog extends Dialog implements Validator.ValidationListener {

    /**
     * 上下文对象 *
     */
    Activity context;

    public Button btnSave;

    public Button btnCancel;

    public View.OnClickListener mClickListener;

    public Validator validator;

    public BaseCustomDialog(Activity context) {
        super(context);
        this.context = context;
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    public BaseCustomDialog(Activity context, View.OnClickListener clickListener) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.mClickListener = clickListener;
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }

    protected void setContentView() {
        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        //p.width = (int) (d.getWidth() * 0.25); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);

        // 根据id在布局中找到控件对象
        btnSave = (Button) findViewById(R.id.btn_save);
        // 为按钮绑定点击事件监听器
        btnSave.setOnClickListener(mClickListener);

        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
        this.setCancelable(true);
    }
    private void close() {
        this.dismiss();
    }

    @Override
    public void onValidationSucceeded() {
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(x.app());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(x.app(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}