package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 *
 */
@Table(name = "patrol_item_type",
        onCreated = "CREATE INDEX patrol_item_type_search ON patrol_item_type(patrolItemTypeId)")
public class PatrolItemTypeEntity extends AbstractEntity {

    @Column(name = "id", isId = true, autoGen = true)
    private Integer id;

    @Column(name = "patrolItemTypeId")
    private Integer patrolItemTypeId;

    @Column(name = "equipmentId")
    private Integer equipmentId;

    @Column(name = "typeName")
    private String typeName;

    public Integer getPatrolItemTypeId() {
        return patrolItemTypeId;
    }

    public void setPatrolItemTypeId(Integer patrolItemTypeId) {
        this.patrolItemTypeId = patrolItemTypeId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
