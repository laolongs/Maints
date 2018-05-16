package cn.tties.maint.bean;

/**
 * Created by Justin on 2018/1/11.
 */

public class CommonListViewBean implements CommonListViewInterface {
    private int itemId;
    private String itemName;
    private boolean checked = false;


    public CommonListViewBean(int itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    @Override
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
