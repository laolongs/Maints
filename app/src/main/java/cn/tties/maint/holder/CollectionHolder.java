package cn.tties.maint.holder;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.xutils.x;

import cn.tties.maint.R;
import cn.tties.maint.enums.CommProtocolType;
import cn.tties.maint.enums.ConnModeType;
import cn.tties.maint.enums.EditVoltageType;
import cn.tties.maint.enums.LineInType;
import cn.tties.maint.enums.MeterEquipmentType;

public class CollectionHolder extends BaseLayoutHolder {

    @NotEmpty(message = "采集点名称不能为空")
    public EditText editCollectonPointName;
    @NotEmpty(message = "区号不能为空")
    public EditText editAreaCode;
    @NotEmpty(message = "终端地址不能为空")
    public EditText editTerminalAddress;
    @NotEmpty(message = "测量点号不能为空")
    public EditText point;
    @NotEmpty(message = "电流互感器倍率不能为空")
    public EditText editCurrentTransformerRatio;
    @NotEmpty(message = "GPRS卡编号不能为空")
    public AutoCompleteTextView editGPRS;
    @NotEmpty(message = "电表出厂编号不能为空")
    public EditText editAmmeterNumber;
    //额定电流
    public EditText editRatedCurrent;
    //额定电压
    public EditText  editRatedVoltage;
    //额定功率
    public EditText editRatedPower;

    //设备类型
    public Spinner equipmentType;
    //电表类型
    public Spinner lineInType;
    //电压互感器倍率
    public Spinner editVoltageTransformerRatio;
    //通信协议类型
    public Spinner commProtocolType;
    //测量点电源接线方式
    public Spinner spinnerConnMode;
    //关联变压器
    public Spinner spinnerTransformer;


    public LinearLayout ammeter_collection_message_delete;


    public CollectionHolder(View contentView) {
        this.editCollectonPointName = (EditText) contentView.findViewById(R.id.edit_collecton_point_name);
        this.editAreaCode = (EditText) contentView.findViewById(R.id.edit_area_code);
        this.editTerminalAddress = (EditText) contentView.findViewById(R.id.edit_terminal_address);
        this.point = (EditText) contentView.findViewById(R.id.edit_point);
        this.editCurrentTransformerRatio = (EditText) contentView.findViewById(R.id.edit_current_transformer_ratio);
        this.editGPRS = (AutoCompleteTextView) contentView.findViewById(R.id.edit_GPRS);
        this.editRatedCurrent = (EditText) contentView.findViewById(R.id.edit_rated_current);
        this.editRatedVoltage = (EditText) contentView.findViewById(R.id.edit_rated_voltage);
        this.editRatedPower = (EditText) contentView.findViewById(R.id.edit_rated_power);
        this.editAmmeterNumber = (EditText) contentView.findViewById(R.id.edit_ammeter_number);
        this.equipmentType = (Spinner) contentView.findViewById(R.id.equipment_type);
        this.lineInType = (Spinner) contentView.findViewById(R.id.spinner_ammeter_line_in_type);
        this.editVoltageTransformerRatio = (Spinner) contentView.findViewById(R.id.edit_voltage_transformer_ratio);
        this.commProtocolType = (Spinner) contentView.findViewById(R.id.comm_protocol_type);
        this.spinnerConnMode = (Spinner) contentView.findViewById(R.id.spinner_conn_mode);
        this.spinnerTransformer = (Spinner) contentView.findViewById(R.id.spinner_transformer);
        this.ammeter_collection_message_delete = (LinearLayout) contentView.findViewById(R.id.ammeter_collection_message_delete);
        //设备类型
        ArrayAdapter<String> industryAdapter1 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, MeterEquipmentType.getInfoList());
        //电表类型
        ArrayAdapter<String> lineInTypeAdapter2 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, LineInType.getInfoList());
        //电压互感器倍率
        ArrayAdapter<String> industryAdapter5 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, EditVoltageType.getInfoList());
        //通信协议类型
        ArrayAdapter<String> industryAdapter3 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, CommProtocolType.getInfoList());
        //测量点电源接线方式
        ArrayAdapter<String> industryAdapter4 = new ArrayAdapter<String>(x.app(), R.layout.spinner_text, ConnModeType.getInfoList());
        //关联变压器

        equipmentType.setAdapter(industryAdapter1);
        lineInType.setAdapter(lineInTypeAdapter2);
        commProtocolType.setAdapter(industryAdapter3);
        spinnerConnMode.setAdapter(industryAdapter4);
        editVoltageTransformerRatio.setAdapter(industryAdapter5);
    }

    public void clear() {
        this.dbId = null;
        setClear(this.editCollectonPointName);
        setClear(this.editAmmeterNumber);
        setClear(this.editTerminalAddress);
        setClear(this.editAreaCode);
        setClear(this.editGPRS);
        setClear(this.point);
        setClear(this.editCurrentTransformerRatio);
        setClear(this.editRatedCurrent);
        setClear(this.editRatedVoltage);
        setClear(this.editRatedPower);
        spinnerConnMode.setSelection(1);
        equipmentType.setSelection(0);
        commProtocolType.setSelection(1);
        lineInType.setSelection(0);
        editVoltageTransformerRatio.setSelection(1);
    }
}