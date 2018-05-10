package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class EntityUndoneAppraisal {

    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"2cf52503036540708f3b49d3b5bed794","PsyID":"35","PsychtestName":"人格三因素测验","PsychtestTitle":"专业的人格测评，测一下，看看你属于哪类人","PData":"2017-12-25","PsychTypeStr":"个性人格","PsychtestSource":"购买","PsychtestTopicCount":85,"TestTime":120}]
     * Total : 3
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
         * ID : 2cf52503036540708f3b49d3b5bed794
         * PsyID : 35
         * PsychtestName : 人格三因素测验
         * PsychtestTitle : 专业的人格测评，测一下，看看你属于哪类人
         * PData : 2017-12-25
         * PsychTypeStr : 个性人格
         * PsychtestSource : 购买
         * PsychtestTopicCount : 85
         * TestTime : 120
         */

        private String ID;
        private String PsyID;
        private String PsychtestName;
        private String PsychtestTitle;
        private String PData;
        private String PsychTypeStr;
        private String PsychtestSource;
        private int PsychtestTopicCount;
        private int TestTime;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getPsyID() {
            return PsyID;
        }

        public void setPsyID(String PsyID) {
            this.PsyID = PsyID;
        }

        public String getPsychtestName() {
            return PsychtestName;
        }

        public void setPsychtestName(String PsychtestName) {
            this.PsychtestName = PsychtestName;
        }

        public String getPsychtestTitle() {
            return PsychtestTitle;
        }

        public void setPsychtestTitle(String PsychtestTitle) {
            this.PsychtestTitle = PsychtestTitle;
        }

        public String getPData() {
            return PData;
        }

        public void setPData(String PData) {
            this.PData = PData;
        }

        public String getPsychTypeStr() {
            return PsychTypeStr;
        }

        public void setPsychTypeStr(String PsychTypeStr) {
            this.PsychTypeStr = PsychTypeStr;
        }

        public String getPsychtestSource() {
            return PsychtestSource;
        }

        public void setPsychtestSource(String PsychtestSource) {
            this.PsychtestSource = PsychtestSource;
        }

        public int getPsychtestTopicCount() {
            return PsychtestTopicCount;
        }

        public void setPsychtestTopicCount(int PsychtestTopicCount) {
            this.PsychtestTopicCount = PsychtestTopicCount;
        }

        public int getTestTime() {
            return TestTime;
        }

        public void setTestTime(int TestTime) {
            this.TestTime = TestTime;
        }
    }
}
