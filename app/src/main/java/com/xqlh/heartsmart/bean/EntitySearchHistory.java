package com.xqlh.heartsmart.bean;

import java.util.List;

public class EntitySearchHistory {


    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"1","Name":"心理健康状况测试（SCL-90）","SpecialtyName":"心理健康临床症状自评量表","Title":"来测测你的心理健康程度吧","PsychtestPic":"http://resources.bnuxq.com:8082/2017091310370619WT3ETO35.jpg","Price":0,"TestTime":120,"TestMans":0,"TopicCount":90,"TestAbstract":"SCL-90由Derogatis在1973年编制,在国外应用甚广，80年代引入我国，并在精神科和心理健康门诊的临床工作中得到广泛应用。心理健康临床症状自评量表包含了90个项目，它是用来测查人的精神症状及其严重程度和变化情况的量表，包含了比较广泛的精神症状学内容，从感觉、情感、思维...","PsychtestTypeStr":"心理健康","ShowTimes":0,"PsychtestType":0,"CreateTime":null}]
     * Total : 121
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
         * ID : 1
         * Name : 心理健康状况测试（SCL-90）
         * SpecialtyName : 心理健康临床症状自评量表
         * Title : 来测测你的心理健康程度吧
         * PsychtestPic : http://resources.bnuxq.com:8082/2017091310370619WT3ETO35.jpg
         * Price : 0
         * TestTime : 120
         * TestMans : 0
         * TopicCount : 90
         * TestAbstract : SCL-90由Derogatis在1973年编制,在国外应用甚广，80年代引入我国，并在精神科和心理健康门诊的临床工作中得到广泛应用。心理健康临床症状自评量表包含了90个项目，它是用来测查人的精神症状及其严重程度和变化情况的量表，包含了比较广泛的精神症状学内容，从感觉、情感、思维...
         * PsychtestTypeStr : 心理健康
         * ShowTimes : 0
         * PsychtestType : 0
         * CreateTime : null
         */

        private String ID;
        private String Name;
        private String SpecialtyName;
        private String Title;
        private String PsychtestPic;
        private double Price;
        private int TestTime;
        private int TestMans;
        private int TopicCount;
        private String TestAbstract;
        private String PsychtestTypeStr;
        private int ShowTimes;
        private int PsychtestType;
        private Object CreateTime;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getSpecialtyName() {
            return SpecialtyName;
        }

        public void setSpecialtyName(String SpecialtyName) {
            this.SpecialtyName = SpecialtyName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getPsychtestPic() {
            return PsychtestPic;
        }

        public void setPsychtestPic(String PsychtestPic) {
            this.PsychtestPic = PsychtestPic;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public int getTestTime() {
            return TestTime;
        }

        public void setTestTime(int TestTime) {
            this.TestTime = TestTime;
        }

        public int getTestMans() {
            return TestMans;
        }

        public void setTestMans(int TestMans) {
            this.TestMans = TestMans;
        }

        public int getTopicCount() {
            return TopicCount;
        }

        public void setTopicCount(int TopicCount) {
            this.TopicCount = TopicCount;
        }

        public String getTestAbstract() {
            return TestAbstract;
        }

        public void setTestAbstract(String TestAbstract) {
            this.TestAbstract = TestAbstract;
        }

        public String getPsychtestTypeStr() {
            return PsychtestTypeStr;
        }

        public void setPsychtestTypeStr(String PsychtestTypeStr) {
            this.PsychtestTypeStr = PsychtestTypeStr;
        }

        public int getShowTimes() {
            return ShowTimes;
        }

        public void setShowTimes(int ShowTimes) {
            this.ShowTimes = ShowTimes;
        }

        public int getPsychtestType() {
            return PsychtestType;
        }

        public void setPsychtestType(int PsychtestType) {
            this.PsychtestType = PsychtestType;
        }

        public Object getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(Object CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
