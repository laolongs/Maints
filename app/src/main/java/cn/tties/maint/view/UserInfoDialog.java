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
import cn.tties.maint.httpclient.params.UpdateMbStaffByIdParams;
import cn.tties.maint.httpclient.result.BaseResult;
import cn.tties.maint.httpclient.result.MbStaffResult;
import cn.tties.maint.util.JsonUtils;

/**
 * Created by Justin on 2018/1/12.
 */

public class UserInfoDialog extends BaseCustomDialog {

    @NotEmpty(message = "姓名不能为空")
    public EditText editStaffName;

    @NotEmpty(message = "工号不能为空")
    public EditText editStaffNo;

    @NotEmpty(message = "电话不能为空")
    public EditText editTel;

    @NotEmpty(message = "电话不能为空")
    public EditText editEmail;

    public MbStaffResult account;

    public StaffListFragment mStaffListFragment;

    public UserInfoDialog(StaffListFragment staffListFragment, MbStaffResult account, View.OnClickListener clickListener) {
        super(staffListFragment.getActivity(), clickListener);
        this.mStaffListFragment = staffListFragment;
        this.account = account;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_user_info);

        editStaffName = (EditText) findViewById(R.id.edit_staffname);
        editStaffName.setText(account.getStaffName());

        editStaffNo = (EditText) findViewById(R.id.edit_staffno);
        editStaffNo.setText(account.getStaffNo());

        editTel = (EditText) findViewById(R.id.edit_tel);
        editTel.setText(account.getStaffTel());

        editEmail = (EditText) findViewById(R.id.edit_email);
        editEmail.setText(account.getEmail());

        super.setContentView();
    }

    @Override
    public void onValidationSucceeded() {
        String staffName =  editStaffName.getText().toString().trim();
        String staffNo = editStaffNo.getText().toString().trim();
        String tel = editTel.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        UpdateMbStaffByIdParams updateMbStaffByIdParams = new UpdateMbStaffByIdParams();
        updateMbStaffByIdParams.setStaffId(account.getStaffId());
        updateMbStaffByIdParams.setStaffName(staffName);
        updateMbStaffByIdParams.setStaffNo(staffNo);
        updateMbStaffByIdParams.setStaffTel(tel);
        updateMbStaffByIdParams.setEmail(email);
        HttpClientSend.getInstance().send(updateMbStaffByIdParams, new BaseStringCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    BaseResult ret = JsonUtils.deserialize(result, BaseResult.class);
                    if (ret.getErrorCode() !=0 ) {
                        Toast.makeText(x.app(), ret.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(x.app(), "修改成功！", Toast.LENGTH_SHORT).show();
                    UserInfoDialog.this.mStaffListFragment.queryStaffList();
                    UserInfoDialog.this.dismiss();
                } catch (Exception e) {
                    Toast.makeText(x.app(), "操作失败！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                }
            }
        });
    }
}
