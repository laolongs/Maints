package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 *
 */
@Table(name = "area",
        onCreated = "CREATE INDEX area_search ON area(areaId)")
public class AreaEntity extends AbstractEntity {

    @Column(name = "id", isId = true, autoGen = true)
    private Integer id;

    @Column(name = "areaId")
    private String areaId;

    @Column(name = "areaName")
    private String areaName;

    @Column(name = "level")
    private Integer level;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "areaOrder")
    private Integer areaOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getAreaOrder() {
        return areaOrder;
    }

    public void setAreaOrder(Integer areaOrder) {
        this.areaOrder = areaOrder;
    }
}
