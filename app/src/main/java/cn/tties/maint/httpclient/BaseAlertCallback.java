package cn.tties.maint.httpclient;

import org.xutils.common.Callback;

import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.util.JsonUtils;

/**
 * Created by Justin on 2018/1/8.
 */

public class BaseAlertCallback implements Callback.CommonCallback<String> {

    String successStr;

    String failStr;

    public BaseAlertCallback(String successStr, String failStr) {
        this.successStr = successStr;
        this.failStr = failStr;
    }


    @Override
    public void onSuccess(String result) {
        BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
        MyApplication.waitDialog.removeDialog();
        if (ret.getErrorCode() != 0) {
            MyApplication.showFaileInfo(failStr, ret.getErrorMessage());
            return;
        }
        MyApplication.showSuccessInfo(successStr);
        onSuccessed(result);
    }

    public void onSuccessed(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        MyApplication.waitDialog.removeDialog();
        MyApplication.showFaileInfo(failStr, "连接异常");
        ex.printStackTrace();
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
