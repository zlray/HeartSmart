package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */

public class EntityReportBasics {


    /**
     * code : 1
     * msg : OK
     * Result : {"ID":"d1892baea778400cb83c5c2622c7c387","PsytestName":"音乐放松","UserName":"xiaomeinv","Sex":"男","Age":18,"EducationLevel":"","HomeAddress":null,"Email":null,"GroupID":null,"GroupName":null,"PsyTime":null,"PsyReportDate":"2018-04-17 11:23:36","TotalScore":100,"OriginalScore":0,"AverageScore":0,"StandardScore":0,"StandardDeviation":0,"TotalLevelName":"训练已完成","Analys":[],"Advice":[],"Comment":["放松音乐的瀑布(2).MPG已完成训练"],"PsychtestPic":"http://resources.bnuxq.com:8082\\images/2018/4\\ce387ca5c01ba9479ae200aef0465508.jpg"}
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
         * ID : d1892baea778400cb83c5c2622c7c387
         * PsytestName : 音乐放松
         * UserName : xiaomeinv
         * Sex : 男
         * Age : 18
         * EducationLevel :
         * HomeAddress : null
         * Email : null
         * GroupID : null
         * GroupName : null
         * PsyTime : null
         * PsyReportDate : 2018-04-17 11:23:36
         * TotalScore : 100
         * OriginalScore : 0
         * AverageScore : 0
         * StandardScore : 0
         * StandardDeviation : 0
         * TotalLevelName : 训练已完成
         * Analys : []
         * Advice : []
         * Comment : ["放松音乐的瀑布(2).MPG已完成训练"]
         * PsychtestPic : http://resources.bnuxq.com:8082\images/2018/4\ce387ca5c01ba9479ae200aef0465508.jpg
         */

        private String ID;
        private String PsytestName;
        private String UserName;
        private String Sex;
        private int Age;
        private String EducationLevel;
        private Object HomeAddress;
        private Object Email;
        private Object GroupID;
        private Object GroupName;
        private Object PsyTime;
        private String PsyReportDate;
        private int TotalScore;
        private int OriginalScore;
        private int AverageScore;
        private int StandardScore;
        private int StandardDeviation;
        private String TotalLevelName;
        private String PsychtestPic;
        private List<?> Analys;
        private List<?> Advice;
        private List<String> Comment;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getPsytestName() {
            return PsytestName;
        }

        public void setPsytestName(String PsytestName) {
            this.PsytestName = PsytestName;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getEducationLevel() {
            return EducationLevel;
        }

        public void setEducationLevel(String EducationLevel) {
            this.EducationLevel = EducationLevel;
        }

        public Object getHomeAddress() {
            return HomeAddress;
        }

        public void setHomeAddress(Object HomeAddress) {
            this.HomeAddress = HomeAddress;
        }

        public Object getEmail() {
            return Email;
        }

        public void setEmail(Object Email) {
            this.Email = Email;
        }

        public Object getGroupID() {
            return GroupID;
        }

        public void setGroupID(Object GroupID) {
            this.GroupID = GroupID;
        }

        public Object getGroupName() {
            return GroupName;
        }

        public void setGroupName(Object GroupName) {
            this.GroupName = GroupName;
        }

        public Object getPsyTime() {
            return PsyTime;
        }

        public void setPsyTime(Object PsyTime) {
            this.PsyTime = PsyTime;
        }

        public String getPsyReportDate() {
            return PsyReportDate;
        }

        public void setPsyReportDate(String PsyReportDate) {
            this.PsyReportDate = PsyReportDate;
        }

        public int getTotalScore() {
            return TotalScore;
        }

        public void setTotalScore(int TotalScore) {
            this.TotalScore = TotalScore;
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

        public String getPsychtestPic() {
            return PsychtestPic;
        }

        public void setPsychtestPic(String PsychtestPic) {
            this.PsychtestPic = PsychtestPic;
        }

        public List<?> getAnalys() {
            return Analys;
        }

        public void setAnalys(List<?> Analys) {
            this.Analys = Analys;
        }

        public List<?> getAdvice() {
            return Advice;
        }

        public void setAdvice(List<?> Advice) {
            this.Advice = Advice;
        }

        public List<String> getComment() {
            return Comment;
        }

        public void setComment(List<String> Comment) {
            this.Comment = Comment;
        }
    }
}
