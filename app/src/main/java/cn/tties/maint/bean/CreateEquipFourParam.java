package cn.tties.maint.bean;

/**
 * Created by Justin on 2018/1/11.
 */

import java.util.List;

public class CreateEquipFourParam {
    private Integer equIdFour;
    private List<CreateEquipFiveParam> equItemFourArr;

    public Integer getEquIdFour() {
        return equIdFour;
    }

    public void setEquIdFour(Integer equIdFour) {
        this.equIdFour = equIdFour;
    }

    public List<CreateEquipFiveParam> getEquItemFourArr() {
        return equItemFourArr;
    }

    public void setEquItemFourArr(List<CreateEquipFiveParam> equItemFourArr) {
        this.equItemFourArr = equItemFourArr;
    }
}
