package cn.tties.maint.holder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import cn.tties.maint.R;

public class PatrolItemHolder {

    public TextView textTitle;

    public TextView textType;

    public TextView textFaultHarm;

    public TextView textDealDesc;

    public RadioButton radio1;

    public RadioButton radio2;

    public RadioGroup radioGroup;

    public LinearLayout layoutInfo;

    public PatrolItemHolder(View contentView) {
        this.textTitle = (TextView) contentView.findViewById(R.id.text_title);
        this.textType = (TextView) contentView.findViewById(R.id.text_type);
        this.textFaultHarm = (TextView) contentView.findViewById(R.id.text_fault_harm);
        this.textDealDesc = (TextView) contentView.findViewById(R.id.text_deal_desc);
        this.radioGroup = (RadioGroup) contentView.findViewById(R.id.radioGroup);
        this.radio1 = (RadioButton) contentView.findViewById(R.id.radio1);
        this.radio2 = (RadioButton) contentView.findViewById(R.id.radio2);
        this.layoutInfo = (LinearLayout) contentView.findViewById(R.id.layout_info);
    }
}