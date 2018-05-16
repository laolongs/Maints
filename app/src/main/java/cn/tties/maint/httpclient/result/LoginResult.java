package cn.tties.maint.httpclient.result;


import org.xutils.http.annotation.HttpResponse;

import java.io.Serializable;

import cn.tties.maint.bean.UserInfoBean;
import cn.tties.maint.widget.JsonResponseParser;

/**
 * 登录
 * author chensi
 */
@HttpResponse(parser = JsonResponseParser.class)
public class LoginResult implements Serializable {


    @Override
    public String toString() {
        return super.toString();
    }

    private UserInfoBean userInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

}
