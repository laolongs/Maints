package cn.tties.maint.view;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.tties.maint.R;
import cn.tties.maint.httpclient.result.MbStaffResult;

/**
 * Created by Justin on 2018/1/12.
 */

public class UserHandoverDialog extends BaseCustomDialog {

    public TextView textStaffName;

    public Spinner overAccount;

    public MbStaffResult account;

    public List<MbStaffResult> userList;

    public UserHandoverDialog(Activity context, MbStaffResult account, List<MbStaffResult> userList, View.OnClickListener clickListener) {
        super(context, clickListener);
        this.account = account;
        this.userList = userList;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_user_handover);

        textStaffName = (TextView) findViewById(R.id.text_staffname);
        textStaffName.setText(account.getStaffName());
        overAccount = (Spinner) findViewById(R.id.spinner);
        List<String> strList =  new ArrayList<>();
        for (MbStaffResult entity : userList) {
            strList.add(entity.getStaffName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, strList);
        overAccount.setAdapter(adapter);
        super.setContentView();
    }
}
