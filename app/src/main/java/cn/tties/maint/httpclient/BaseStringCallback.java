package cn.tties.maint.httpclient;

import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by Justin on 2018/1/8.
 */

public class BaseStringCallback implements Callback.CommonCallback<String> {

    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
        Toast.makeText(x.app(), "数据连接失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
