package com.xqlh.heartsmart.api.bean;

/**
 * Created by Administrator on 2018/4/2.
 */

public class EntityCheckMessage {
    //校验短信验证码是否正确
    private int code;
    private String msg;
    private String Result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
