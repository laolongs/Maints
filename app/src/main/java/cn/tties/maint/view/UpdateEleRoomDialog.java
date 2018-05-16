package cn.tties.maint.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import cn.tties.maint.R;
import cn.tties.maint.activity.EleRoomCheckFragment;
import cn.tties.maint.httpclient.result.EleRoomResult;

/**
 * 修改电房弹框.
 * Created by Justin on 2018/1/12.
 */

public class UpdateEleRoomDialog extends BaseCustomDialog {

    @NotEmpty(message = "电房名称不能为空")
    public EditText eleRoomName;
    public CheckBox highRoom;
    public CheckBox lowRoom;
    public CheckBox transformer;
    public EleRoomResult selectEleRoomResult;

    private EleRoomCheckFragment mEleRoomCheckFragment;

    public UpdateEleRoomDialog(EleRoomCheckFragment context, View.OnClickListener clickListener, EleRoomResult selectEleRoomResult) {
        super(context.getActivity(), clickListener);
        this.mEleRoomCheckFragment = context;
        this.selectEleRoomResult = selectEleRoomResult;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_update_room);
        eleRoomName = findViewById(R.id.edit_eleRoomName);
        eleRoomName.setText(selectEleRoomResult.getRoomName());
        highRoom = findViewById(R.id.high_room);
        highRoom.setChecked(selectEleRoomResult.getIsHigh());
        lowRoom = findViewById(R.id.low_room);
        lowRoom.setChecked(selectEleRoomResult.getIsLow());
        transformer = findViewById(R.id.transformer);
        transformer.setChecked(selectEleRoomResult.getIsTransformer());
        super.setContentView();
    }

    @Override
    public void onValidationSucceeded() {
        this.mEleRoomCheckFragment.updateEleRoom(selectEleRoomResult);
    }
}
