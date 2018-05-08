package com.xqlh.heartsmart.bean;

/**
 * Created by Administrator on 2018/5/8.
 */

public class EntityUpdateUserInfor {

    /**
     * code : 1
     * msg : OK
     * Result : null
     * ResultMsg : null
     */

    private int code;
    private String msg;
    private Object Result;
    private Object ResultMsg;

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

    public Object getResult() {
        return Result;
    }

    public void setResult(Object Result) {
        this.Result = Result;
    }

    public Object getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(Object ResultMsg) {
        this.ResultMsg = ResultMsg;
    }
}
