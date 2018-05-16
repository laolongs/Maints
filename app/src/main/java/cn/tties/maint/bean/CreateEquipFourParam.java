package cn.tties.maint.bean;

/**
 * Created by Justin on 2018/1/11.
 */

import java.util.List;

public class CreateEquipFourParam {
    private Integer equIdFour;
    private List<CreateEquipItemParam> equItemFourArr;

    public Integer getEquIdFour() {
        return equIdFour;
    }

    public void setEquIdFour(Integer equIdFour) {
        this.equIdFour = equIdFour;
    }

    public List<CreateEquipItemParam> getEquItemFourArr() {
        return equItemFourArr;
    }

    public void setEquItemFourArr(List<CreateEquipItemParam> equItemFourArr) {
        this.equItemFourArr = equItemFourArr;
    }
}
