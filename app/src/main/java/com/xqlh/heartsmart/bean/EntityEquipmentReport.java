package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class EntityEquipmentReport {


    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"d986d4513e8440bca0bb93be0dbca351","Name":"学习工作压力疏导 - 认知拥抱调节报告","PsychtestPic":null,"UserID":"e55827a8a1584e4eacfbb444da34585e","OpenID":"ocYJ602fAXWl1EA-kcu_GE8UHi8w","CreateTime":"2017-11-08T21:06:53","LockCode":"NFL8G0W1","EvaluationTypeID":"1","EvaluationTypeStr":"智能认知调整拥抱系统","TotalScore":0,"StandardDeviation":0,"TotalLevelName":"此次认知拥抱调节比较成功，再接再厉！","ResultPic":"http://111.207.18.3:8082\\images/2017/11\\f8594b79e2667d82d889018f9a524d55.jpg","CreateDate":"2017-11-08","PsychtestUserID":null,"OriginalScore":0,"AverageScore":0,"StandardScore":0,"Analys":null,"Advice":null,"Comment":null}]
     * Total : 1
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
         * ID : d986d4513e8440bca0bb93be0dbca351
         * Name : 学习工作压力疏导 - 认知拥抱调节报告
         * PsychtestPic : null
         * UserID : e55827a8a1584e4eacfbb444da34585e
         * OpenID : ocYJ602fAXWl1EA-kcu_GE8UHi8w
         * CreateTime : 2017-11-08T21:06:53
         * LockCode : NFL8G0W1
         * EvaluationTypeID : 1
         * EvaluationTypeStr : 智能认知调整拥抱系统
         * TotalScore : 0
         * StandardDeviation : 0
         * TotalLevelName : 此次认知拥抱调节比较成功，再接再厉！
         * ResultPic : http://111.207.18.3:8082\images/2017/11\f8594b79e2667d82d889018f9a524d55.jpg
         * CreateDate : 2017-11-08
         * PsychtestUserID : null
         * OriginalScore : 0
         * AverageScore : 0
         * StandardScore : 0
         * Analys : null
         * Advice : null
         * Comment : null
         */

        private String ID;
        private String Name;
        private Object PsychtestPic;
        private String UserID;
        private String OpenID;
        private String CreateTime;
        private String LockCode;
        private String EvaluationTypeID;
        private String EvaluationTypeStr;
        private int TotalScore;
        private int StandardDeviation;
        private String TotalLevelName;
        private String ResultPic;
        private String CreateDate;
        private Object PsychtestUserID;
        private int OriginalScore;
        private int AverageScore;
        private int StandardScore;
        private Object Analys;
        private Object Advice;
        private Object Comment;

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

        public Object getPsychtestPic() {
            return PsychtestPic;
        }

        public void setPsychtestPic(Object PsychtestPic) {
            this.PsychtestPic = PsychtestPic;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getOpenID() {
            return OpenID;
        }

        public void setOpenID(String OpenID) {
            this.OpenID = OpenID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getLockCode() {
            return LockCode;
        }

        public void setLockCode(String LockCode) {
            this.LockCode = LockCode;
        }

        public String getEvaluationTypeID() {
            return EvaluationTypeID;
        }

        public void setEvaluationTypeID(String EvaluationTypeID) {
            this.EvaluationTypeID = EvaluationTypeID;
        }

        public String getEvaluationTypeStr() {
            return EvaluationTypeStr;
        }

        public void setEvaluationTypeStr(String EvaluationTypeStr) {
            this.EvaluationTypeStr = EvaluationTypeStr;
        }

        public int getTotalScore() {
            return TotalScore;
        }

        public void setTotalScore(int TotalScore) {
            this.TotalScore = TotalScore;
        }

        public int getStandardDeviation() {
            return StandardDeviation;
        }

        public void setStandardDeviation(int StandardDeviation) {
            this.StandardDeviation = StandardDeviation;
        }

        public String getTotalLevelName() {
            return TotalLevelName;
        }

        public void setTotalLevelName(String TotalLevelName) {
            this.TotalLevelName = TotalLevelName;
        }

        public String getResultPic() {
            return ResultPic;
        }

        public void setResultPic(String ResultPic) {
            this.ResultPic = ResultPic;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public Object getPsychtestUserID() {
            return PsychtestUserID;
        }

        public void setPsychtestUserID(Object PsychtestUserID) {
            this.PsychtestUserID = PsychtestUserID;
        }

        public int getOriginalScore() {
            return OriginalScore;
        }

        public void setOriginalScore(int OriginalScore) {
            this.OriginalScore = OriginalScore;
        }

        public int getAverageScore() {
            return AverageScore;
        }

        public void setAverageScore(int AverageScore) {
            this.AverageScore = AverageScore;
        }

        public int getStandardScore() {
            return StandardScore;
        }

        public void setStandardScore(int StandardScore) {
            this.StandardScore = StandardScore;
        }

        public Object getAnalys() {
            return Analys;
        }

        public void setAnalys(Object Analys) {
            this.Analys = Analys;
        }

        public Object getAdvice() {
            return Advice;
        }

        public void setAdvice(Object Advice) {
            this.Advice = Advice;
        }

        public Object getComment() {
            return Comment;
        }

        public void setComment(Object Comment) {
            this.Comment = Comment;
        }
    }
}
