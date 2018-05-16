package cn.tties.maint.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.util.StringUtil;


/**
 * 圆形进度条
 * author chensi
 */
public class CriProgressDialog {
    private Context mContext;
    private Dialog mDialog;
    private TextView text;
    private String msgStr;

    public CriProgressDialog() {
        mContext = x.app();
    }

    public CriProgressDialog(Context context) {
        mContext = context;
    }

    private void changeStr(String str) {
        this.msgStr = str;
        Message msg = Message.obtain(mHandler);
        msg.what = 0;
        mHandler.sendMessage(msg);
    }

    public Dialog loadDialog(String str) {
        if (mDialog != null && mDialog.isShowing()) {
            changeStr(str);
            return mDialog;
        }
        mDialog = new Dialog(mContext, R.style.dialog);
        LayoutInflater in = LayoutInflater.from(mContext);
        View viewDialog = in.inflate(R.layout.dialog_progress, null);
//        viewDialog.setBackgroundColor(0x7f000000);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 这里可以设置dialog的大小，当然也可以设置dialog title等
        // LayoutParams layoutParams = new LayoutParams(width * 80 / 100, 50);
        // mDialog.setContentView(viewDialog, layoutParams);
        if (!StringUtil.isEmpty(str)) {
            text = (TextView) viewDialog.findViewById(R.id.textView);
            text.setText(str);
        }
        mDialog.setContentView(viewDialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.show();
        return mDialog;
    }

    public void removeDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public void setText(String str) {
        text.setText(str);
    }

    private Handler mHandler = new Handler(){
        @Override  
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub  
            int what = msg.what;  
            if (what == 0) {    //update  
                TextView tv = (TextView) mDialog.findViewById(R.id.textView);
                tv.setText(msgStr);
//                if(mDialog.isShowing()){
//                    mHandler.sendEmptyMessageDelayed(0,1000);
//                }
            }
        }  
    };  
}