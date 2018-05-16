package cn.tties.maint.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 *
 */
@Table(name = "industry",
        onCreated = "CREATE INDEX industry_search ON industry(industryId)")
public class IndustryEntity extends AbstractEntity {

    @Column(name = "id", isId = true, autoGen = true)
    private Integer id;

    @Column(name = "industryId")
    private Integer industryId;

    @Column(name = "industryName")
    private String industryName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
