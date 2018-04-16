package com.xqlh.heartsmart.ui.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */

public class EntityArticleList {

    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"153dd1e6ebab4279931875d654ddc001","TypeStr":"成长"},{"ID":"2e8d670d44b9440282aa816b51a6a779","TypeStr":"职场"},{"ID":"6497552afcea4fbebe7588294372fb22","TypeStr":"小知识"},{"ID":"76f1cfba8c8e40ec888b563e6b8ea4f1","TypeStr":"科普"},{"ID":"850e7186768347daa7380627ca4fbc58","TypeStr":"性心理"},{"ID":"a3ece580903a432c87b48719d52fc768","TypeStr":"经典美文"},{"ID":"ca9818ee292b4927a73c9b7c805c7938","TypeStr":"婚恋"},{"ID":"f490f11854144b05a69eb42d7ddf962e","TypeStr":"健康"}]
     * Total : 8
     */

    private int code;
    private String msg;
    private int Total;
    private List<ResultBean> Result;

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

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * ID : 153dd1e6ebab4279931875d654ddc001
         * TypeStr : 成长
         */

        private String ID;
        private String TypeStr;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTypeStr() {
            return TypeStr;
        }

        public void setTypeStr(String TypeStr) {
            this.TypeStr = TypeStr;
        }
    }
}
