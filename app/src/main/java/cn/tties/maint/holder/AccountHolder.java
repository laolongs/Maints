package cn.tties.maint.holder;

import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import cn.tties.maint.R;

public class AccountHolder extends BaseLayoutHolder {

    @NotEmpty(message = "用户名不能为空")
    public EditText editUid;

    @NotEmpty(message = "真实姓名不能为空")
    public EditText editUsername;

    @Password(min = 0, message = "密码不能为空")
    public EditText editPassword;

    @NotEmpty(message = "确认密码不能为空")
    @ConfirmPassword(message = "两次密码不一致")
    public EditText editPasswordConfirm;

    public AccountHolder(View contentView) {
        this.editUid = (EditText) contentView.findViewById(R.id.edit_uid);
        this.editUsername = (EditText) contentView.findViewById(R.id.edit_username);
        this.editPassword = (EditText) contentView.findViewById(R.id.edit_password);
        this.editPasswordConfirm = (EditText) contentView.findViewById(R.id.edit_password_confirm);
    }

    public void clear() {
        setClear(this.editUid);
        setClear(this.editUsername);
        setClear(this.editPassword);
        setClear(this.editPasswordConfirm);
    }
}