package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by Justin on 2018/1/11.
 */

public class CreateEquipItemParam {
    private Integer equId;
    private String equName;

    private List<CreateEquipTwoParam> comItemThree;
    private List<CreateEquipFourParam> equArrFour;

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

    public List<CreateEquipTwoParam> getComItemThree() {
        return comItemThree;
    }

    public void setComItemThree(List<CreateEquipTwoParam> comItemThree) {
        this.comItemThree = comItemThree;
    }
}
