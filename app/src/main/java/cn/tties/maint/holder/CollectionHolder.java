package cn.tties.maint.holder;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.enums.CommProtocolType;
import cn.tties.maint.enums.ConnModeType;
import cn.tties.maint.enums.LineInType;
import cn.tties.maint.enums.MeterEquipmentType;

public class CollectionHolder extends BaseLayoutHolder {

    @NotEmpty(message = "采集点名称不能为空")
    public EditText editCollectonPointName;

    @NotEmpty(message = "区号不能为空")
    public EditText editAreaCode;

    @NotEmpty(message = "终端地址不能为空")
    public EditText editTerminalAddress;

    public Spinner equipmentType;

    public Spinner lineInType;

    public LinearLayout ammeter_collection_message_delete;


    public CollectionHolder(View contentView) {
        this.editCollectonPointName = (EditText) contentView.findViewById(R.id.edit_collecton_point_name);
        this.editAreaCode = (EditText) contentView.findViewById(R.id.edit_area_code);
        this.editTerminalAddress = (EditText) contentView.findViewById(R.id.edit_terminal_address);
        this.equipmentType = (Spinner) contentView.findViewById(R.id.equipment_type);
        this.lineInType = (Spinner) contentView.findViewById(R.id.spinner_ammeter_line_in_type);
        this.ammeter_collection_message_delete = (LinearLayout) contentView.findViewById(R.id.ammeter_collection_message_delete);

        ArrayAdapter<String> industryAdapter2 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, MeterEquipmentType.getInfoList());
        ArrayAdapter<String> lineInTypeAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, LineInType.getInfoList());
        equipmentType.setAdapter(industryAdapter2);
        lineInType.setAdapter(lineInTypeAdapter);
    }

    public void clear() {
        this.dbId = null;
        setClear(this.editCollectonPointName);
        setClear(this.editTerminalAddress);
        equipmentType.setSelection(0);
        lineInType.setSelection(0);
    }
}