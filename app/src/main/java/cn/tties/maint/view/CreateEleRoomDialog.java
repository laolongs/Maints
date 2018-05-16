package cn.tties.maint.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import cn.tties.maint.R;
import cn.tties.maint.activity.EleRoomCheckFragment;

/**
 * 创建电房弹框
 * Created by Justin on 2018/1/12.
 */

public class CreateEleRoomDialog extends BaseCustomDialog {

    @NotEmpty(message = "电房名称不能为空")
    public EditText eleRoomName;
    public CheckBox highRoom;
    public CheckBox lowRoom;
    public CheckBox transformer;

    private EleRoomCheckFragment mEleRoomCheckFragment;

    public CreateEleRoomDialog(EleRoomCheckFragment eleRoomCheckFragment, View.OnClickListener clickListener) {
        super(eleRoomCheckFragment.getActivity(), clickListener);
        this.mEleRoomCheckFragment = eleRoomCheckFragment;
    }

    @Override
    protected void setContentView() {
        // 指定布局
        this.setContentView(R.layout.dialog_create_room);

        eleRoomName = findViewById(R.id.edit_eleRoomName);
        highRoom = findViewById(R.id.high_room);
        lowRoom = findViewById(R.id.low_room);
        transformer = findViewById(R.id.transformer);
        super.setContentView();
    }

    @Override
    public void onValidationSucceeded() {
        this.mEleRoomCheckFragment.createEleRoom();
    }
}
