package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 *
 */
@Table(name = "overhaul_item",
        onCreated = "CREATE INDEX overhaul_item_search ON overhaul_item(overhaulItemId)")
public class OverhaulItemEntity extends AbstractEntity {

    @Column(name = "overhaulItemId", isId = true, autoGen = true)
    private Integer overhaulItemId;

    @Column(name = "itemContent")
    private String itemContent;

    @Column(name = "itemType")
    private Integer itemType;

    public Integer getOverhaulItemId() {
        return overhaulItemId;
    }

    public void setOverhaulItemId(Integer overhaulItemId) {
        this.overhaulItemId = overhaulItemId;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }
}
