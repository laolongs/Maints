package cn.tties.maint.widget;

/**
 * Created by guider on 2018/1/3.
 * Email guider@yeah.net
 * github https://github.com/guider
 */

public class TabClass {
    public String alias;
    public Class fragment;
    public String text;
    public int image;
    public int imagePressed;

    public TabClass(String alias, Class fragment, String text, int image, int imagePressed) {
        this.alias = alias;
        this.fragment = fragment;
        this.text = text;
        this.image = image;
        this.imagePressed = imagePressed;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    public int getImagePressed() {
        return imagePressed;
    }

    public void setImagePressed(int imagePressed) {
        this.imagePressed = imagePressed;
    }
}