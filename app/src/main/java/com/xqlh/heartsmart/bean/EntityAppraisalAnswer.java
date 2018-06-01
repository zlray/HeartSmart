package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/7.
 */

public class EntityAppraisalAnswer {
    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"78ff9b8ee88a42099744f328ef5a6566","PsychtestTopicID":"210544bcaf8f4c6a97fd7c3e5071a7e4","PsychtestID":"1","OptionNumber":"A","Content":"没有","Score":0},{"ID":"8058bfc8b97d4390ac67f39ae8f9d5e9","PsychtestTopicID":"210544bcaf8f4c6a97fd7c3e5071a7e4","PsychtestID":"1","OptionNumber":"B","Content":"很轻","Score":1},{"ID":"b9329efb41ca44c0adbd9f807caff727","PsychtestTopicID":"210544bcaf8f4c6a97fd7c3e5071a7e4","PsychtestID":"1","OptionNumber":"C","Content":"中等","Score":2},{"ID":"c878e2c6c177475ca1c6a1437628edf6","PsychtestTopicID":"210544bcaf8f4c6a97fd7c3e5071a7e4","PsychtestID":"1","OptionNumber":"D","Content":"偏重","Score":3},{"ID":"c721f33b5cd74dc5bd94dd5405fe13a4","PsychtestTopicID":"210544bcaf8f4c6a97fd7c3e5071a7e4","PsychtestID":"1","OptionNumber":"E","Content":"严重","Score":4}]
     * Total : 0
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
         * ID : 78ff9b8ee88a42099744f328ef5a6566
         * PsychtestTopicID : 210544bcaf8f4c6a97fd7c3e5071a7e4
         * PsychtestID : 1
         * OptionNumber : A
         * Content : 没有
         * Score : 0
         */

        private String ID;
        private String PsychtestTopicID;
        private String PsychtestID;
        private String OptionNumber;
        private String Content;
        private double Score;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getPsychtestTopicID() {
            return PsychtestTopicID;
        }

        public void setPsychtestTopicID(String PsychtestTopicID) {
            this.PsychtestTopicID = PsychtestTopicID;
        }

        public String getPsychtestID() {
            return PsychtestID;
        }

        public void setPsychtestID(String PsychtestID) {
            this.PsychtestID = PsychtestID;
        }

        public String getOptionNumber() {
            return OptionNumber;
        }

        public void setOptionNumber(String OptionNumber) {
            this.OptionNumber = OptionNumber;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public double getScore() {
            return Score;
        }

        public void setScore(double Score) {
            this.Score = Score;
        }
    }
}
