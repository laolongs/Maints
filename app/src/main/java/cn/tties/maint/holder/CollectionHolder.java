package cn.tties.maint.holder;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

    @NotEmpty(message = "电表出厂编号不能为空")
    public EditText editAmmeterNumber;

    @NotEmpty(message = "终端地址不能为空")
    public EditText editTerminalAddress;

    @NotEmpty(message = "区号不能为空")
    public EditText editAreaCode;

    @NotEmpty(message = "GPRS卡编号不能为空")
    public EditText editGPRS;

    @NotEmpty(message = "电压互感器倍率不能为空")
    public EditText editVoltageTransformerRatio;

    @NotEmpty(message = "电流互感器倍率不能为空")
    public EditText editCurrentTransformerRatio;

    @NotEmpty(message = "测量点号不能为空")
    public EditText point;

    public Spinner equipmentType;

    public Spinner lineInType;

    public Spinner commProtocolType;

    public EditText editRatedCurrent;

    public EditText editRatedVoltage;

    public EditText editRatedPower;

    public Spinner spinnerConnMode;

    public Spinner spinnerTransformer;

    public CollectionHolder(View contentView) {
        this.editCollectonPointName = (EditText) contentView.findViewById(R.id.edit_collecton_point_name);
        this.editAmmeterNumber = (EditText) contentView.findViewById(R.id.edit_ammeter_number);
        this.editAreaCode = (EditText) contentView.findViewById(R.id.edit_area_code);
        this.editTerminalAddress = (EditText) contentView.findViewById(R.id.edit_terminal_address);
        this.editGPRS = (EditText) contentView.findViewById(R.id.edit_GPRS);
        this.editVoltageTransformerRatio = (EditText) contentView.findViewById(R.id.edit_voltage_transformer_ratio);
        this.editCurrentTransformerRatio = (EditText) contentView.findViewById(R.id.edit_current_transformer_ratio);
        this.editRatedCurrent = (EditText) contentView.findViewById(R.id.edit_rated_current);
        this.editRatedVoltage = (EditText) contentView.findViewById(R.id.edit_rated_voltage);
        this.editRatedPower = (EditText) contentView.findViewById(R.id.edit_rated_power);
        this.spinnerConnMode = (Spinner) contentView.findViewById(R.id.spinner_conn_mode);
        this.point = (EditText) contentView.findViewById(R.id.edit_point);
        this.equipmentType = (Spinner) contentView.findViewById(R.id.equipment_type);
        this.commProtocolType = (Spinner) contentView.findViewById(R.id.comm_protocol_type);
        this.lineInType = (Spinner) contentView.findViewById(R.id.spinner_ammeter_line_in_type);
        this.spinnerTransformer = (Spinner) contentView.findViewById(R.id.spinner_transformer);
        ArrayAdapter<String> industryAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, ConnModeType.getInfoList());
        ArrayAdapter<String> industryAdapter2 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, MeterEquipmentType.getInfoList());
        ArrayAdapter<String> industryAdapter3 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, CommProtocolType.getInfoList());
        ArrayAdapter<String> lineInTypeAdapter = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, LineInType.getInfoList());
        spinnerConnMode.setAdapter(industryAdapter);
        equipmentType.setAdapter(industryAdapter2);
        commProtocolType.setAdapter(industryAdapter3);
        lineInType.setAdapter(lineInTypeAdapter);
    }

    public void clear() {
        this.dbId = null;
        setClear(this.editCollectonPointName);
        setClear(this.editAmmeterNumber);
        setClear(this.editTerminalAddress);
        setClear(this.editAreaCode);
        setClear(this.editGPRS);
        setClear(this.point);
        setClear(this.editVoltageTransformerRatio);
        setClear(this.editCurrentTransformerRatio);
        setClear(this.editRatedCurrent);
        setClear(this.editRatedVoltage);
        setClear(this.editRatedPower);
        spinnerConnMode.setSelection(1);
        equipmentType.setSelection(0);
        commProtocolType.setSelection(1);
        lineInType.setSelection(0);
    }
}