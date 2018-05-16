package cn.tties.maint.httpclient.send;


import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.bean.EventBusBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.EventKind;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.params.VersionParams;
import cn.tties.maint.httpclient.result.VersionResult;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.JsonUtils;

/**
 * author chensi
 */
public class VersionSend {

    private static final String TAG = "getVersion";

    public void send(final VersionParams params, final boolean showflag) {
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    Log.d(TAG, "检查新版本: " + result);
                    //结果转换json
                    VersionResult ret = JsonUtils.deserialize(result, VersionResult.class);
                    ACache.getInstance().put(Constants.CACHE_VERSION, ret);
                    // 获取packagemanager的实例
                    PackageManager packageManager = x.app().getPackageManager();
                    // getPackageName()是你当前类的包名，0代表是获取版本信息
                    PackageInfo packInfo = packageManager.getPackageInfo(x.app().getPackageName(), 0);
                    int versionCode = packInfo.versionCode;
                    if (ret.getVersionCode() > versionCode) {
                        UIData uiData = UIData.create().setDownloadUrl(ret.getUrl());
                        DownloadBuilder builder = AllenVersionChecker
                                .getInstance()
                                .downloadOnly(uiData);
                        builder.setCustomVersionDialogListener(createCustomDownloadingDialog());
                        builder.excuteMission(x.app());
                    } else {
                        if (showflag) {
                            Toast.makeText(MyApplication.curActivity, "已是最新版本", Toast.LENGTH_SHORT);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "获取版本信息失败");
                EventBusBean bean = new EventBusBean();
                bean.setKind(EventKind.EVENT_VERSION_SYCN);
                EventBus.getDefault().post(bean);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        };
        x.http().post(params.getParams(), callback);
    }

    private CustomVersionDialogListener createCustomDownloadingDialog() {

        return new CustomVersionDialogListener() {

            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_update);
                try {
                    VersionResult result = ACache.getInstance().getAsObject(Constants.CACHE_VERSION);
                    TextView textView = baseDialog.findViewById(R.id.tv_msg);
                    textView.setText(result.getDescription());
                    PackageManager packageManager = x.app().getPackageManager();
                    PackageInfo packInfo = packageManager.getPackageInfo(x.app().getPackageName(), 0);
                    int versionCode = packInfo.versionCode;
                    //小于最低版本强制更新
                    if (versionCode < result.getMinVersionCode()) {
                        baseDialog.findViewById(R.id.versionchecklib_version_dialog_cancel).setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return baseDialog;

            }
        };

    }

    class BaseDialog extends Dialog {

        private int res;

        public BaseDialog(Context context, int theme, int res) {

            super(context, theme);

            setContentView(res);

            this.res = res;

            setCanceledOnTouchOutside(false);

            setCancelable(true);
        }
    }
}
