package cn.tties.maint.holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.tties.maint.R;

public class PatrolInfoHolder extends BaseLayoutHolder {

    public Button saveBtn;

    public TextView textName;

    public PatrolInfoHolder(View view) {
        this.saveBtn = (Button) view.findViewById(R.id.btn_save);
        this.textName = (TextView) view.findViewById(R.id.text_name);
    }
}