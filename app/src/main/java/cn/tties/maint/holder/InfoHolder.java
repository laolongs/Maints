package cn.tties.maint.holder;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.tties.maint.R;

public class InfoHolder {

    public TextView nameText;

    public Button saveBtn;

    public LinearLayout detailLayout;

    public InfoHolder(View view) {
        this.nameText = (TextView) view.findViewById(R.id.text_name);
        this.saveBtn = (Button) view.findViewById(R.id.btn_save);
        this.detailLayout = (LinearLayout) view.findViewById(R.id.layout_detail);
    }
}