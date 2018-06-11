package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by Justin on 2018/1/11.
 */

public class CreateEquipItemParam {
    private Integer equId;
    private String equName;
    private Integer companyId;
    private Integer companyEquipmentId;
    private boolean flag;
    private List<CreateEquipFiveParam> comItemThree;
    private List<CreateEquipFourParam> equArrFour;

    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }


    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getEquId() {
        return equId;
    }

    public void setEquId(Integer equId) {
        this.equId = equId;
    }

    public String getEquName() {
        return equName;
    }

    public void setEquName(String equName) {
        this.equName = equName;
    }

    public List<CreateEquipFourParam> getEquArrFour() {
        return equArrFour;
    }

    public void setEquArrFour(List<CreateEquipFourParam> equArrFour) {
        this.equArrFour = equArrFour;
    }

    public List<CreateEquipFiveParam> getComItemThree() {
        return comItemThree;
    }

    public void setComItemThree(List<CreateEquipFiveParam> comItemThree) {
        this.comItemThree = comItemThree;
    }
}
