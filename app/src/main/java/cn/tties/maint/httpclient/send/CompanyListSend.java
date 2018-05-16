package cn.tties.maint.httpclient.send;


import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.params.CompanyParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.CompanyResult;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.JsonUtils;

/**
 * author chensi
 */
public class CompanyListSend {

    private static final String TAG = "CompanyListSend";

    public void send(final CompanyParams params) {
        Callback.CommonCallback<String> callback =  new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                EventBusBean busBean = new EventBusBean();
                BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                if (ret.getErrorCode() != 0) {
                    Toast.makeText(x.app(), "获取企业信息失败", Toast.LENGTH_SHORT).show();
                } else {
                    final List<CompanyResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<CompanyResult>>() {
                    }.getType());
                    ACache.getInstance().put(Constants.CACHE_COMPANYLIST, (Serializable) list);
                }
                busBean.setKind(EventKind.EVENT_COMPANYLIST);
                busBean.setSuccess(true);
                EventBus.getDefault().post(busBean);
            }
        };
        x.http().post(params.getParams(), callback);
    };
}
