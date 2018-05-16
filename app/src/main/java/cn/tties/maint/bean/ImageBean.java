package cn.tties.maint.bean;

/**
 * Created by Justin on 2018/1/11.
 */

public class ImageBean {

    private Integer imageId;
    //后台返回图片地址
    private String imageUrl;

    private boolean isLocal = false;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }
}
