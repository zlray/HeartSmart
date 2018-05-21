package com.xqlh.heartsmart.bean;

/**
 * Created by Administrator on 2018/5/21.
 */

public class EntityCollect   {
    /**
     * code : 1
     * msg : OK
     * Result : true
     * ResultMsg : null
     */

    private int code;
    private String msg;
    private boolean Result;
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

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean Result) {
        this.Result = Result;
    }

    public Object getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(Object ResultMsg) {
        this.ResultMsg = ResultMsg;
    }
}
