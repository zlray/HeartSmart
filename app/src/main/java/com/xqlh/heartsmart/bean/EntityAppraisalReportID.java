package com.xqlh.heartsmart.bean;

/**
 * Created by Administrator on 2018/5/14.
 */

public class    EntityAppraisalReportID {

    /**
     * code : 1
     * msg : OK
     * Result : true
     * ResultMsg : 5d5bcd9ee26e40dbb459a1441219caef
     */

    private int code;
    private String msg;
    private boolean Result;
    private String ResultMsg;

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

    public String getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(String ResultMsg) {
        this.ResultMsg = ResultMsg;
    }
}
