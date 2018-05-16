package cn.tties.maint.holder;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.tties.maint.R;

public class PatrolInputItemHolder {

    public TextView textTitle;

    public TextView textName1;

    public TextView textName2;

    public TextView textName3;

    public TextView textName4;

    public TextView textName5;

    public TextView textName6;

    public TextView textUnit1;

    public TextView textUnit2;

    public TextView textUnit3;

    public TextView textUnit4;

    public TextView textUnit5;

    public TextView textUnit6;

    public EditText editValue1;

    public EditText editValue2;

    public EditText editValue3;

    public EditText editValue4;

    public EditText editValue5;

    public EditText editValue6;

     public PatrolInputItemHolder(View contentView) {
        this.textTitle = contentView.findViewById(R.id.text_title);
        this.textName1 = contentView.findViewById(R.id.text_name1);
        this.textName2 = contentView.findViewById(R.id.text_name2);
        this.textName3 = contentView.findViewById(R.id.text_name3);
        this.textName4 = contentView.findViewById(R.id.text_name4);
        this.textName5 = contentView.findViewById(R.id.text_name5);
        this.textName6 = contentView.findViewById(R.id.text_name6);

        this.textUnit1 = contentView.findViewById(R.id.text_unit1);
        this.textUnit2 = contentView.findViewById(R.id.text_unit2);
        this.textUnit3 = contentView.findViewById(R.id.text_unit3);
        this.textUnit4 = contentView.findViewById(R.id.text_unit4);
        this.textUnit5 = contentView.findViewById(R.id.text_unit5);
        this.textUnit6 = contentView.findViewById(R.id.text_unit6);

        this.editValue1 = contentView.findViewById(R.id.edit_value1);
        this.editValue2 = contentView.findViewById(R.id.edit_value2);
        this.editValue3 = contentView.findViewById(R.id.edit_value3);
        this.editValue4 = contentView.findViewById(R.id.edit_value4);
        this.editValue5 = contentView.findViewById(R.id.edit_value5);
        this.editValue6 = contentView.findViewById(R.id.edit_value6);

    }

    public void setViewCount(int count) {
        switch (count) {
            case 1:
                textName1.setVisibility(View.VISIBLE);
                editValue1.setVisibility(View.VISIBLE);
                textUnit1.setVisibility(View.VISIBLE);
                textName2.setVisibility(View.VISIBLE);
                editValue2.setVisibility(View.VISIBLE);
                textUnit2.setVisibility(View.VISIBLE);
                textName3.setVisibility(View.VISIBLE);
                editValue3.setVisibility(View.VISIBLE);
                textUnit3.setVisibility(View.VISIBLE);
                textName4.setVisibility(View.VISIBLE);
                editValue4.setVisibility(View.VISIBLE);
                textUnit4.setVisibility(View.VISIBLE);
                textName5.setVisibility(View.VISIBLE);
                editValue5.setVisibility(View.VISIBLE);
                textUnit5.setVisibility(View.VISIBLE);
                textName6.setVisibility(View.VISIBLE);
                editValue6.setVisibility(View.VISIBLE);
                textUnit6.setVisibility(View.VISIBLE);

                textName2.setVisibility(View.GONE);
                editValue2.setVisibility(View.GONE);
                textUnit2.setVisibility(View.GONE);
            case 2:
                textName3.setVisibility(View.GONE);
                editValue3.setVisibility(View.GONE);
                textUnit3.setVisibility(View.GONE);
            case 3:
                textName4.setVisibility(View.GONE);
                editValue4.setVisibility(View.GONE);
                textUnit4.setVisibility(View.GONE);
                textName5.setVisibility(View.GONE);
                editValue5.setVisibility(View.GONE);
                textUnit5.setVisibility(View.GONE);
                textName6.setVisibility(View.GONE);
                editValue6.setVisibility(View.GONE);
                textUnit6.setVisibility(View.GONE);
            case 6:
                break;
        }
    }

}