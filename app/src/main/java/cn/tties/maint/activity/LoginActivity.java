package cn.tties.maint.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.common.Constants;
import cn.tties.maint.common.MyApplication;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.LoginParams;
import cn.tties.maint.httpclient.params.QueryAllMbFunctionParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.LoginResult;
import cn.tties.maint.httpclient.result.MbFunctionResult;
import cn.tties.maint.util.ACache;
import cn.tties.maint.util.JsonUtils;
import cn.tties.maint.util.StringUtil;
import cn.tties.maint.view.CriProgressDialog;
import cn.tties.maint.widget.TabClass;

/**
 * 登录
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {


    @ViewInject(R.id.edittext_username)
    private EditText mUserName;

    @ViewInject(R.id.edittext_password)
    private EditText mPassword;

    private CriProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new CriProgressDialog(LoginActivity.this);
        UserInfoBean bean = MyApplication.getUserInfo();
        if (bean != null) {
            mUserName.setText(bean.getStaffTel());
        }
    }

    @Event(value = {R.id.btn_intoMain})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_intoMain:
                if (StringUtil.isEmpty(mUserName.getText().toString())) {
                    Toast.makeText(x.app(), "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtil.isEmpty(mPassword.getText().toString())) {
                    Toast.makeText(x.app(), "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.loadDialog("登录中..");
                LoginParams params = new LoginParams();
                params.setUserName(mUserName.getText().toString());
                params.setPassWord(mPassword.getText().toString());
                HttpClientSend.getInstance().send(params, new BaseStringCallback() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                            if (ret.getErrorCode() != 0) {//错误
                                dialog.removeDialog();
                                Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            LoginResult loginResult = JsonUtils.deserialize(ret.getResult(), LoginResult.class);
                            ACache.getInstance().put(Constants.CACHE_LOGIN_STATUS, true);
                            UserInfoBean bean = loginResult.getUserInfo();
                            bean.setStaffName(mUserName.getText().toString());
                            bean.setLoginPwd(mPassword.getText().toString());
                            ACache.getInstance().put(Constants.CACHE_USERINFO, loginResult.getUserInfo());
                            //查询用户菜单
                            LoginActivity.this.getAllMbFunction(loginResult.getUserInfo().getStaffId());
                        } catch (Exception e) {
                            e.printStackTrace();
                            dialog.removeDialog();
                            Toast.makeText(x.app(), "连接失败", Toast.LENGTH_SHORT).show();
                        } finally {
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        dialog.removeDialog();
                        super.onError(ex, isOnCallback);
                    }
                });
                break;
        }
    }

    /**
     * 根据员工ID查询员工所有的权限.
     * @param staffId
     */
    private void getAllMbFunction(int staffId) {
        QueryAllMbFunctionParams paramsAuth = new QueryAllMbFunctionParams();
        paramsAuth.setStaffId(staffId);
        HttpClientSend.getInstance().send(paramsAuth, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if(ret.getErrorCode()!=0){
                        dialog.removeDialog();
                        Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<String> userAuth = new ArrayList<>();
                    List<MbFunctionResult> list = JsonUtils.deserialize(ret.getResult(), new TypeToken<List<MbFunctionResult>>() {}.getType());
                    for (MbFunctionResult queryAllMbFunctionResult:list ) {
                        for (TabClass tabClass : Constants.menuList) {
                            if (tabClass.getAlias().equals(queryAllMbFunctionResult.getAlias())) {
                                userAuth.add(queryAllMbFunctionResult.getAlias());
                            }
                        }
                    }
                    UserInfoBean userInfoBean = MyApplication.getUserInfo();
                    userInfoBean.setMenuList(userAuth);
                    ACache.getInstance().put(Constants.CACHE_USERINFO, userInfoBean);

                    dialog.removeDialog();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    dialog.removeDialog();
                    Toast.makeText(x.app(), "连接失败", Toast.LENGTH_SHORT).show();
                } finally {
                }
            }
        });
    }
}
