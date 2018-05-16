package cn.tties.maint.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.tties.maint.R;

public class PatrolInputBodyItemHolder {

    public TextView textTitle;

    public TextView textName1;

    public TextView textName2;

    public TextView textName3;

//    public TextView textUnit1;

    public TextView textUnit2;

    public TextView textUnit3;

    public EditText editValue1;

    public EditText editValue2;

    public EditText editValue3;

     public PatrolInputBodyItemHolder(View contentView) {
        this.textTitle = contentView.findViewById(R.id.text_title);
        this.textName1 = contentView.findViewById(R.id.text_name1);
        this.textName2 = contentView.findViewById(R.id.text_name2);
        this.textName3 = contentView.findViewById(R.id.text_name3);

//        this.textUnit1 = contentView.findViewById(R.id.text_unit1);
        this.textUnit2 = contentView.findViewById(R.id.text_unit2);
        this.textUnit3 = contentView.findViewById(R.id.text_unit3);

        this.editValue1 = contentView.findViewById(R.id.edit_value1);
        this.editValue2 = contentView.findViewById(R.id.edit_value2);
        this.editValue3 = contentView.findViewById(R.id.edit_value3);

    }
}