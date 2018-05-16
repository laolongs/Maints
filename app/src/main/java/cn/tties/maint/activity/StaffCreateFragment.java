package cn.tties.maint.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.tties.maint.R;
import cn.tties.maint.enums.RoleType;
import cn.tties.maint.httpclient.BaseAlertCallback;
import cn.tties.maint.httpclient.HttpClientSend;
import cn.tties.maint.httpclient.params.AddStaffParams;
import cn.tties.maint.util.NoFastClickUtils;
import cn.tties.maint.view.ConfirmDialog;

/**
 * 创建账号(员工).
 */
@ContentView(R.layout.fragment_account_create)
public class StaffCreateFragment extends BaseFragment {

    private static final String TAG = "AccountAddFragment";

    @ViewInject(R.id.radio_role)
    private RadioGroup radioRole;

    @ViewInject(R.id.radio2)
    private RadioButton radioButton;

    @ViewInject(R.id.edit_staffname)
    @NotEmpty(message = "姓名不能为空")
    private EditText editStaffName;

    @ViewInject(R.id.edit_staffno)
    @NotEmpty(message = "工号不能为空")
    private EditText editStaffNo;

    @ViewInject(R.id.edit_tel)
    @NotEmpty(message = "电话不能为空")
    private EditText editTel;

    @ViewInject(R.id.edit_pwd)
    @NotEmpty(message = "密码不能为空")
    private EditText editPwd;

    @ViewInject(R.id.edit_email)
    @NotEmpty(message = "密码不能为空")
    private EditText editEmail;

    private ConfirmDialog confirmDialog;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirmDialog = new ConfirmDialog(StaffCreateFragment.this.getContext());
    }

    @Override
    public void onValidationSucceeded() {
        AddStaffParams params = new AddStaffParams();
        params.setStaffName(editStaffName.getText().toString());
        params.setStaffNo(editStaffNo.getText().toString());
        params.setStaffTel(editTel.getText().toString());
        params.setStaffPwd(editPwd.getText().toString());
        params.setEmail(editEmail.getText().toString());
        RadioButton button = (RadioButton) StaffCreateFragment.this.getView().findViewById(radioRole.getCheckedRadioButtonId());
        params.setStaffRoleId(RoleType.getValue(button.getText().toString()));
        HttpClientSend.getInstance().send(params, new BaseAlertCallback("新建用户成功","新建用户失败") {
            @Override
            public void onSuccessed(String result) {
                try {
                    clearInput();
                    // 刷新员工列表.
                    StaffListFragment.staffListFragmentInstance.queryStaffList();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }
        });
    }

    @Event(value = {R.id.btn_save})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (NoFastClickUtils.isFastClick()) {
                    return;
                }
                validator.validate();
                break;
        }
    }

    private void clearInput() {
        radioButton.setChecked(true);
        setClear(editStaffName);
        setClear(editStaffNo);
        setClear(editTel);
        setClear(editPwd);
        setClear(editEmail);
    }
}
