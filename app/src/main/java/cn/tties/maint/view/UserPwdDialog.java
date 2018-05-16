package cn.tties.maint.view;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.activity.StaffListFragment;
import cn.tties.maint.httpclient.BaseStringCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.UpdateMbStaffPasswordParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.MbStaffResult;
import cn.tties.maint.util.JsonUtils;

/**
 * Created by Justin on 2018/1/12.
 */

public class UserPwdDialog extends BaseCustomDialog {

    @NotEmpty(message = "旧密码不能为空")
    public EditText editOldPwd;

    @NotEmpty(message = "新密码不能为空")
    public EditText editNewPwd;

    public MbStaffResult account;
    private StaffListFragment mStaffListFragment;

    public UserPwdDialog(StaffListFragment staffListFragment, MbStaffResult account, View.OnClickListener clickListener) {
        super(staffListFragment.getActivity(), clickListener);
        this.mStaffListFragment = staffListFragment;
        this.account = account;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_user_pwd);

        editOldPwd = (EditText) findViewById(R.id.edit_setting_oldPwd);

        editNewPwd = (EditText) findViewById(R.id.edit_setting_newPwd);

        super.setContentView();
    }

    @Override
    public void onValidationSucceeded() {
        String newPwd = this.editNewPwd.getText().toString().trim();
        String newPwd2 = this.editOldPwd.getText().toString().trim();
        if (!newPwd.equals(newPwd2)) {
            Toast.makeText(x.app(), "密码不一致！", Toast.LENGTH_SHORT).show();
            return;
        }
        UpdateMbStaffPasswordParams updateMbStaffPasswordParams = new UpdateMbStaffPasswordParams();
        updateMbStaffPasswordParams.setLoginPwd(newPwd);
        updateMbStaffPasswordParams.setStaffId(account.getStaffId());
        HttpClientSend.getInstance().send(updateMbStaffPasswordParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if (ret.getErrorCode() !=0 ) {
                        Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(x.app(), "修改成功！", Toast.LENGTH_SHORT).show();
                    UserPwdDialog.this.dismiss();
                    UserPwdDialog.this.mStaffListFragment.queryStaffList();
                } catch (Exception e) {
                    Toast.makeText(x.app(), "操作失败！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }
}
