package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by Justin on 2018/1/11.
 */

public class CreateBoxBody {
    private Integer boxBodyId;

    private List<CreateEquipFiveParam> boxBodyEquItemArr;

    public Integer getBoxBodyId() {
        return boxBodyId;
    }

    public void setBoxBodyId(Integer boxBodyId) {
        this.boxBodyId = boxBodyId;
    }

    public List<CreateEquipFiveParam> getBoxBodyEquItemArr() {
        return boxBodyEquItemArr;
    }

    public void setBoxBodyEquItemArr(List<CreateEquipFiveParam> boxBodyEquItemArr) {
        this.boxBodyEquItemArr = boxBodyEquItemArr;
    }
}
