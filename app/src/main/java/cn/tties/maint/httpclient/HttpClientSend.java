package cn.tties.maint.httpclient;


import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.common.MyApplication;

/**
 * 登录
 * author chensi
 */
public class HttpClientSend {

    private static HttpClientSend httpClientSend = new HttpClientSend();

    /**
     * XXX:send改造完改回private.
     */
    private HttpClientSend() {

    }

    public static HttpClientSend getInstance() {
        return httpClientSend;
    }

    public void sendFile(ClinetRequestParams params, List<File> fileList, Callback.CommonCallback<String> callback) {
        if (callback instanceof BaseAlertCallback) {
            MyApplication.showWaitDialog();
        }
        RequestParams par = params.getFileParams();
        List<KeyValue> list = new ArrayList<KeyValue>();
        for (int i = 0; i < fileList.size(); i++) {
            list.add(new KeyValue("file", fileList.get(i)));
        }
        MultipartBody body = new MultipartBody(list, "UTF-8");
        par.setRequestBody(body);
        x.http().post(par, callback);
    }

    public void send(ClinetRequestParams params, Callback.CommonCallback<String> callback) {
        if (callback instanceof BaseAlertCallback) {
            MyApplication.showWaitDialog();
        }
        x.http().post(params.getParams(), callback);
    }

    public void sendGet(ClinetRequestParams params, Callback.CommonCallback<String> callback) {
        x.http().get(params.getParams(), callback);
    }
}
