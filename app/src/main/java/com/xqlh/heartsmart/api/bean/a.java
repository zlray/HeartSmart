package com.xqlh.heartsmart.api.bean;

/**
 * Created by Administrator on 2018/4/3.
 */

public class a {
    /**
     * code : 1
     * msg : OK
     * Result : {"ID":"bae545ccde3740448f71649b90b42e92","PicURL":"http://resources.bnuxq.com:8082/2018040206213179EFKCM9BD.jpg","AppType":1,"CreateTime":"2018-04-02T18:21:36"}
     * ResultMsg : null
     */
    private int code;
    private String msg;
    private ResultBean Result;
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

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public Object getResultMsg() {
        return ResultMsg;
    }

    public void setResultMsg(Object ResultMsg) {
        this.ResultMsg = ResultMsg;
    }

    public static class ResultBean {
        /**
         * ID : bae545ccde3740448f71649b90b42e92
         * PicURL : http://resources.bnuxq.com:8082/2018040206213179EFKCM9BD.jpg
         * AppType : 1
         * CreateTime : 2018-04-02T18:21:36
         */

        private String ID;
        private String PicURL;
        private int AppType;
        private String CreateTime;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getPicURL() {
            return PicURL;
        }

        public void setPicURL(String PicURL) {
            this.PicURL = PicURL;
        }

        public int getAppType() {
            return AppType;
        }

        public void setAppType(int AppType) {
            this.AppType = AppType;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
