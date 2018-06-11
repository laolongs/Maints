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
import cn.tties.maint.enums.CommProtocolType;
import cn.tties.maint.enums.ConnModeType;
import cn.tties.maint.enums.EditVoltageType;
import cn.tties.maint.enums.LineInType;
import cn.tties.maint.enums.MeterEquipmentType;

public class CollectionMessageHolder extends BaseLayoutHolder {

    public TextView editCollectonPointNames;
    public TextView editAreaCodes;
    public TextView editTerminalAddresss;
    public TextView points;
    public TextView editCurrentTransformerRatios;
    public TextView editGPRSs;
    public TextView editAmmeterNumbers;
    //额定电流
    public TextView editRatedCurrents;
    //额定电压
    public TextView  editRatedVoltages;
    //额定功率
    public TextView editRatedPowers;

    //设备类型
    public TextView equipmentTypes;
    //电表类型
    public TextView lineInTypes;
    //电压互感器倍率
    public TextView editVoltageTransformerRatios;
    //通信协议类型
    public TextView commProtocolTypes;
    //测量点电源接线方式
    public TextView spinnerConnModes;
    //关联变压器
    public TextView spinnerTransformers;

    public CollectionMessageHolder(View contentView) {
        this.editCollectonPointNames = (TextView) contentView.findViewById(R.id.edit_collecton_point_names);
        this.editAreaCodes = (TextView) contentView.findViewById(R.id.edit_area_codes);
        this.editTerminalAddresss = (TextView) contentView.findViewById(R.id.edit_terminal_addresss);
        this.points = (TextView) contentView.findViewById(R.id.edit_points);
        this.editCurrentTransformerRatios = (TextView) contentView.findViewById(R.id.edit_current_transformer_ratios);
        this.editGPRSs = (TextView) contentView.findViewById(R.id.edit_GPRSs);
        this.editRatedCurrents = (TextView) contentView.findViewById(R.id.edit_rated_currents);
        this.editRatedVoltages = (TextView) contentView.findViewById(R.id.edit_rated_voltages);
        this.editRatedPowers = (TextView) contentView.findViewById(R.id.edit_rated_powers);
        this.editAmmeterNumbers = (TextView) contentView.findViewById(R.id.edit_ammeter_numbers);
        this.equipmentTypes = (TextView) contentView.findViewById(R.id.equipment_types);
        this.lineInTypes = (TextView) contentView.findViewById(R.id.spinner_ammeter_line_in_types);
        this.editVoltageTransformerRatios = (TextView) contentView.findViewById(R.id.edit_voltage_transformer_ratios);
        this.commProtocolTypes = (TextView) contentView.findViewById(R.id.comm_protocol_types);
        this.spinnerConnModes = (TextView) contentView.findViewById(R.id.spinner_conn_modes);
        this.spinnerTransformers = (TextView) contentView.findViewById(R.id.spinner_transformers);

    }


}