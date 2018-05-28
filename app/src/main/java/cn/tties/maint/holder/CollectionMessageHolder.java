package cn.tties.maint.holder;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.enums.LineInType;
import cn.tties.maint.enums.MeterEquipmentType;

public class CollectionMessageHolder extends BaseLayoutHolder {

    public TextView ammeter_message_name;
    public TextView ammeter_message_equipment;
    public TextView ammeter_message_ele;
    public TextView ammeter_message_area;
    public TextView ammeter_message_address;

    public CollectionMessageHolder(View contentView) {
        this.ammeter_message_name = (TextView) contentView.findViewById(R.id.ammeter_message_name);
        this.ammeter_message_equipment = (TextView) contentView.findViewById(R.id.ammeter_message_equipment);
        this.ammeter_message_ele = (TextView) contentView.findViewById(R.id.ammeter_message_ele);
        this.ammeter_message_area = (TextView) contentView.findViewById(R.id.ammeter_message_area);
        this.ammeter_message_address = (TextView) contentView.findViewById(R.id.ammeter_message_address);

    }

}