package cn.tties.maint.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 2018/1/11.
 */

public class EleBillBean {
    private int month;
    private int year;
    private int eleAccountId;
    private List<ImageBean> imageList;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<ImageBean> getImageList() {
        if (imageList == null) {
            imageList = new ArrayList<>();
        }
        return imageList;
    }

    public void setImageList(List<ImageBean> imageList) {
        this.imageList = imageList;
    }

    public void setImage(ImageBean imageBean) {
        if(imageList == null) {
            imageList = new ArrayList<>();
        }
        imageList.add(imageBean);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getEleAccountId() {
        return eleAccountId;
    }

    public void setEleAccountId(int eleAccountId) {
        this.eleAccountId = eleAccountId;
    }
}
