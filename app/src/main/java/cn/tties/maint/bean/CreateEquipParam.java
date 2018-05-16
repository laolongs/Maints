package cn.tties.maint.bean;

import java.util.List;

/**
 * Created by Justin on 2018/1/11.
 */

public class CreateEquipParam {
    private String comEquName;
    private Integer equipmentId;
    private Integer eleAccountId;
    private Integer pid;
    private CreateBoxBody boxBody;
    private List<CreateEquipTwoParam> equNameArrTwo;
    private List<CreateEquipItemParam> comItemThree;
    private List<CreateEquipFourParam> equArrFour;

    public String getComEquName() {
        return comEquName;
    }

    public void setComEquName(String comEquName) {
        this.comEquName = comEquName;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(Integer eleAccountId) {
        this.eleAccountId = eleAccountId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public CreateBoxBody getBoxBody() {
        return boxBody;
    }

    public void setBoxBody(CreateBoxBody boxBody) {
        this.boxBody = boxBody;
    }

    public List<CreateEquipTwoParam> getEquNameArrTwo() {
        return equNameArrTwo;
    }

    public void setEquNameArrTwo(List<CreateEquipTwoParam> equNameArrTwo) {
        this.equNameArrTwo = equNameArrTwo;
    }

    public List<CreateEquipItemParam> getComItemThree() {
        return comItemThree;
    }

    public void setComItemThree(List<CreateEquipItemParam> comItemThree) {
        this.comItemThree = comItemThree;
    }

    public List<CreateEquipFourParam> getEquArrFour() {
        return equArrFour;
    }

    public void setEquArrFour(List<CreateEquipFourParam> equArrFour) {
        this.equArrFour = equArrFour;
    }
}
