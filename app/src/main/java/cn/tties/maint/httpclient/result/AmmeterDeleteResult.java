package cn.tties.maint.httpclient.result;


import com.google.gson.JsonElement;

public class AmmeterDeleteResult {

    int errorCode;

    String errorMessage;


    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
