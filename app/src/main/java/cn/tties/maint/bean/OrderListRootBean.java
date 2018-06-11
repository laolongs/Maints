package cn.tties.maint.bean;

/**
 * Created by li on 2018/6/8
 * description：
 * author：guojlli
 */

public class OrderListRootBean implements CommonListViewInterface {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getItemId() {
        return null;
    }

    @Override
    public String getItemName() {
        return null;
    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void setChecked(boolean checked) {

    }
}
