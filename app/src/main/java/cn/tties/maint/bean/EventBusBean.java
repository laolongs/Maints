package cn.tties.maint.bean;

import java.io.Serializable;

public class EventBusBean implements Serializable {

    private String kind;

    private boolean success;

    private Object obj;

    private Object objs;

    private String message;

    private String name;

    private boolean isAdd;

    private boolean isNew;

    private int position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public <T> T getObj() {
        return (T) obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public <T> T getObjs() {
        return (T)objs;
    }

    public void setObjs(Object objs) {
        this.objs = objs;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
