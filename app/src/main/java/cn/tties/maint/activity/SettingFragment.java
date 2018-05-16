package cn.tties.maint.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.UpdatePasswordParams;
import cn.tties.maint.httpclient.params.VersionParams;
import cn.tties.maint.httpclient.result.VersionResult;
import cn.tties.maint.httpclient.send.VersionSend;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.NoFastClickUtils;

/**
 * 设置.
 */
@ContentView(R.layout.fragment_settings)
public class SettingFragment extends BaseFragment {

    private static final String TAG = "SettingFragment";

    @ViewInject(R.id.edit_setting_oldPwd)
    @NotEmpty(message = "密码不能为空")
    private EditText editOldPwd;

    @ViewInject(R.id.edit_setting_newPwd)
    @NotEmpty(message = "新密码不能为空")
    private EditText editNewPwd;

    @ViewInject(R.id.edit_setting_newPwd2)
    @NotEmpty(message = "新密码不能为空")
    private EditText editNewPwd2;

    @ViewInject(R.id.text_version)
    private TextView textVersion;

    @ViewInject(R.id.layout_version)
    private LinearLayout layoutVersion;

    @ViewInject(R.id.text_login)
    private TextView textLogin;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        textLogin.setText( MyApplication.getUserInfo().getStaffNo());
        super.onViewCreated(view, savedInstanceState);
        VersionResult result = ACache.getInstance().getAsObject(Constants.CACHE_VERSION);

        if (result !=null) {
            textVersion.setText(result.getVersionName());
        }
        layoutVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new VersionSend().send(new VersionParams(), true);
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        UpdatePasswordParams updatePasswordParams = new UpdatePasswordParams();
        UserInfoBean userInfoBean = MyApplication.getUserInfo();
        if (!editNewPwd.getText().toString().equals(editNewPwd2.getText().toString())){
            Toast.makeText(x.app(), "两次密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }
        updatePasswordParams.setOldPwd(editOldPwd.getText().toString());
        updatePasswordParams.setNewPwd(editNewPwd.getText().toString());
        updatePasswordParams.setStaffId(userInfoBean.getStaffId());
        editOldPwd.setText("");
        editNewPwd.setText("");
        editNewPwd2.setText("");
        HttpClientSend.getInstance().send(updatePasswordParams, new BaseAlertCallback("保存成功","保存失败") {
            @Override
            public void onSuccessed(String result) {

            }
        });
    }

    @Event(value = {R.id.btn_setting_save_pwd, R.id.btn_setting_logout})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_setting_save_pwd:
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                validator.validate();
                break;
            case R.id.btn_setting_logout:
                ActivityCollector.finishAll();
                //默认登录状态
                ACache.getInstance().put(Constants.CACHE_LOGIN_STATUS, false);
                Intent intent = new Intent(SettingFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
