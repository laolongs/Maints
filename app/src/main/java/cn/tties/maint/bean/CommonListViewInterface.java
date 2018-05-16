package cn.tties.maint.bean;

import java.io.Serializable;

/**
 * Created by Justin on 2018/1/9.
 */

public interface CommonListViewInterface extends Serializable{

    public Integer getItemId();

    public String getItemName();

    public boolean isChecked();

    public void setChecked(boolean checked);
}
