package cn.tties.maint.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import org.xutils.x;

import cn.tties.maint.R;

public class EleAccountLayoutHolder  {

    public EditText editEleAccount;

    public EditText editEleVolume;

    public ImageView imageView;

    public View contentView;

    public EleAccountLayoutHolder(ViewGroup parent) {
        this.contentView  = View.inflate(x.app(), R.layout.layout_edittext_eleaccount, parent);
        this.editEleAccount = (EditText) contentView.findViewById(R.id.edit_ele_account);
        this.editEleVolume = (EditText) contentView.findViewById(R.id.edit_ele_volume);
        this.imageView = (ImageView) contentView.findViewById(R.id.image_add);
    }
}