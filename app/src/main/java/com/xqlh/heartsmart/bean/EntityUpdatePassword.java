package com.xqlh.heartsmart.bean;

/**
 * Created by Administrator on 2018/4/11.
 */

public class EntityUpdatePassword {
//    code=1,----1.成功；0.失败
//            msg = "OK",
//            Result = null

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
