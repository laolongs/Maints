package cn.tties.maint.view;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.enums.RoleType;
import cn.tties.maint.httpclient.result.MbStaffResult;

/**
 * Created by Justin on 2018/1/12.
 */

public class UserRoleDialog extends BaseCustomDialog {

    public MbStaffResult account;

    public RadioGroup radioRole;

    public UserRoleDialog(Activity context, MbStaffResult account, View.OnClickListener clickListener) {
        super(context, clickListener);
        this.account = account;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_user_role);

        TextView username = (TextView) findViewById(R.id.text_staffname);
        username.setText(account.getStaffNo());

        radioRole = (RadioGroup) findViewById(R.id.radio_role);

        for (RoleType e : RoleType.values()) {
            if (e == RoleType.MANAGER) {
                continue;
            }
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(e.getInfo());
            radioButton.setId(e.getValue());
            radioButton.setPadding(15, 15, 15, 15);
            radioButton.setTextColor(ContextCompat.getColor(x.app(), R.color.black));
            if (account.getRoleId() == (e.getValue())) {
                radioButton.setChecked(true);
            }
            radioRole.addView(radioButton);
        }
        super.setContentView();
    }
}
