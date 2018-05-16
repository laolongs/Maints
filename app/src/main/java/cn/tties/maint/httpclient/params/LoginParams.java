package cn.tties.maint.httpclient.params;


import cn.tties.maint.httpclient.ClinetRequestParams;

/**
 * 登录
 * author chensi
 * param:userName:手机号或工号
 *       passWord：未加密密码
 */
public class LoginParams extends ClinetRequestParams {

    public static final String INTERFACE = "staffLoginApp.do";

    private Integer type;

    private Integer version;

    public Integer getType() {
        if (version == null) {
            return 3;
        }
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getVersion() {
        if (version == null) {
            return android.os.Build.VERSION.SDK_INT;
        }
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
