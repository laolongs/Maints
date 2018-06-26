package cn.tties.maint.httpclient;


import android.util.Log;

import org.xutils.http.RequestParams;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cn.tties.maint.common.Constants;
import cn.tties.maint.util.StringUtil;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class ClinetRequestParams implements Serializable {

//    private String userName;
    private String maintStaffTel;

    private String passWord;

//    public String getUserName() {
//        if (StringUtil.isEmpty(userName)) {
////            userName = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_USERNAME);
//        }
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getMaintStaffTel() {
        if (StringUtil.isEmpty(maintStaffTel)) {
//            userName = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_USERNAME);
        }
        return maintStaffTel;
    }

    public void setMaintStaffTel(String maintStaffTel) {
        this.maintStaffTel = maintStaffTel;
    }

    public String getPassWord() {
        if (StringUtil.isEmpty(passWord)) {
//            password = ACache.getInstance().getAsString(Constants.CACHE_LOGIN_PASSWORD);
//            password = EncryptUtil.MD5Encrypt(password).toUpperCase();
        }
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public RequestParams getParams() {
        RequestParams param = new RequestParams();
        try {
            String url = Constants.API_URL + (String) this.getClass().getField("INTERFACE").get(null);
            param.setUri(url);
            Field[] field = this.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
            for (int j = 0; j < field.length; j++) { // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                String tempName = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].toString();
                if (type.contains("private")) {
                    Method m = this.getClass().getMethod("get" + tempName);
                    Object obj = m.invoke(this);
                    if (obj != null) {
                        param.addParameter(name, obj);
                    }
                }
            }
            Method u = this.getClass().getMethod("getMaintStaffTel");
            Object obju = u.invoke(this);
            param.addParameter("maintStaffTel", obju);
            Method p = this.getClass().getMethod("getPassWord");
            Object objp = p.invoke(this);
            param.addParameter("passWord", objp);
            Log.d("发送请求", param.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(this.getClass().getName(), e.getMessage());
        }
        param.setConnectTimeout(60000*2);
        param.setCacheMaxAge(60000*2);
        param.setReadTimeout(60000*2);
        return param;
    }

    public RequestParams getFileParams() {
        RequestParams params = getParams();
        params.setMultipart(true);//设置表单传送      
        params.setCancelFast(true);//设置可以立即被停止
        params.setConnectTimeout(60000*5);
        params.setCacheMaxAge(60000*5);
        params.setReadTimeout(60000*5);
        return params;
    }
}
