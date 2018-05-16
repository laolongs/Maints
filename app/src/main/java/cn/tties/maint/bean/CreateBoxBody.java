package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by Justin on 2018/1/11.
 */

public class CreateBoxBody {
    private Integer boxBodyId;

    private List<CreateEquipItemParam> boxBodyEquItemArr;

    public Integer getBoxBodyId() {
        return boxBodyId;
    }

    public void setBoxBodyId(Integer boxBodyId) {
        this.boxBodyId = boxBodyId;
    }

    public List<CreateEquipItemParam> getBoxBodyEquItemArr() {
        return boxBodyEquItemArr;
    }

    public void setBoxBodyEquItemArr(List<CreateEquipItemParam> boxBodyEquItemArr) {
        this.boxBodyEquItemArr = boxBodyEquItemArr;
    }
}
