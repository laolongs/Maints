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
    private Integer companyEquipmentId;
//    private CreateBoxBody boxBody;
    private CreateBoxBody boxBody;

//    private List<CreateEquipTwoParam> equNameArrTwo;
    private List<CreateEquipItemParam> equNameArrThree;
    private List<CreateEquipTwoParam> comItemTwo;
//    private List<CreateEquipFourParam> equArrFour;


    public Integer getCompanyEquipmentId() {
        return companyEquipmentId;
    }

    public void setCompanyEquipmentId(Integer companyEquipmentId) {
        this.companyEquipmentId = companyEquipmentId;
    }

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

    public List<CreateEquipItemParam> getEquNameArrThree() {
        return equNameArrThree;
    }

    public void setEquNameArrThree(List<CreateEquipItemParam> equNameArrThree) {
        this.equNameArrThree = equNameArrThree;
    }

    public List<CreateEquipTwoParam> getComItemTwo() {
        return comItemTwo;
    }

    public void setComItemTwo(List<CreateEquipTwoParam> comItemTwo) {
        this.comItemTwo = comItemTwo;
    }
}
