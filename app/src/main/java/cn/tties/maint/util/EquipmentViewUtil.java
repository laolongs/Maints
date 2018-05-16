package cn.tties.maint.util;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.x;

import cn.tties.maint.entity.EquipmentItemEntity;

public class EquipmentViewUtil {


    public static View getTextView(EquipmentItemEntity entity) {
        LinearLayout layout = new LinearLayout(x.app());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView view = new TextView(x.app());
        view.setText(entity.getItemName());
        return view;
    }
}