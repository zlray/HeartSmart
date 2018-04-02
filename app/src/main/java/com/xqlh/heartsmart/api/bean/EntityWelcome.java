package com.xqlh.heartsmart.api.bean;

/**
 * Created by Administrator on 2018/4/2.
 */

public class EntityWelcome {
    private int code;
    private String msg;
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    class Result {
        private String ID;
        private String PicURL;
        private int AppType;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getPicURL() {
            return PicURL;
        }

        public void setPicURL(String picURL) {
            PicURL = picURL;
        }

        public int getAppType() {
            return AppType;
        }

        public void setAppType(int appType) {
            AppType = appType;
        }
    }

}
