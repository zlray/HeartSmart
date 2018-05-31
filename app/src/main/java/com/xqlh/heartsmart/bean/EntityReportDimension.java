package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 * 测评报告的维度
 */

public class EntityReportDimension {


    /**
     * code : 1
     * msg : OK
     * Result : [{"DimensionId":"xxxxxxxxxxxxxxxx","DimensionName":"xxxx","ResultID":"xxx","Score":17,"MaxScore":0,"LevelName":"xxx","OriginalScore":17,"AverageScore":6.32,"AveStaDeviation":"xxx","DAnalysis":["xxxxxxxxxxx","xxxxxxxxxxxxxxxxxxxxxxxx"],"DAdvice":["xxxxxxxxxxxxxxxx","xxxxxxxxxxxxxxxxxxxxxxxxxx"]}]
     */

    private int code;
    private String msg;
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

    public List<ResultBean> getResult() {
        return Result;
    }

    public void setResult(List<ResultBean> Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * DimensionId : xxxxxxxxxxxxxxxx
         * DimensionName : xxxx
         * ResultID : xxx
         * Score : 17
         * MaxScore : 0
         * LevelName : xxx
         * OriginalScore : 17
         * AverageScore : 6.32
         * AveStaDeviation : xxx
         * DAnalysis : ["xxxxxxxxxxx","xxxxxxxxxxxxxxxxxxxxxxxx"]
         * DAdvice : ["xxxxxxxxxxxxxxxx","xxxxxxxxxxxxxxxxxxxxxxxxxx"]
         */

        private String DimensionId;
        private String DimensionName;
        private String ResultID;
        private int Score;
        private int MaxScore;
        private String LevelName;
        private int OriginalScore;
        private double AverageScore;
        private String AveStaDeviation;
        private List<String> DAnalysis;
        private List<String> DAdvice;

        public String getDimensionId() {
            return DimensionId;
        }

        public void setDimensionId(String DimensionId) {
            this.DimensionId = DimensionId;
        }

        public String getDimensionName() {
            return DimensionName;
        }

        public void setDimensionName(String DimensionName) {
            this.DimensionName = DimensionName;
        }

        public String getResultID() {
            return ResultID;
        }

        public void setResultID(String ResultID) {
            this.ResultID = ResultID;
        }

        public int getScore() {
            return Score;
        }

        public void setScore(int Score) {
            this.Score = Score;
        }

        public int getMaxScore() {
            return MaxScore;
        }

        public void setMaxScore(int MaxScore) {
            this.MaxScore = MaxScore;
        }

        public String getLevelName() {
            return LevelName;
        }

        public void setLevelName(String LevelName) {
            this.LevelName = LevelName;
        }

        public int getOriginalScore() {
            return OriginalScore;
        }

        public void setOriginalScore(int OriginalScore) {
            this.OriginalScore = OriginalScore;
        }

        public double getAverageScore() {
            return AverageScore;
        }

        public void setAverageScore(double AverageScore) {
            this.AverageScore = AverageScore;
        }

        public String getAveStaDeviation() {
            return AveStaDeviation;
        }

        public void setAveStaDeviation(String AveStaDeviation) {
            this.AveStaDeviation = AveStaDeviation;
        }

        public List<String> getDAnalysis() {
            return DAnalysis;
        }

        public void setDAnalysis(List<String> DAnalysis) {
            this.DAnalysis = DAnalysis;
        }

        public List<String> getDAdvice() {
            return DAdvice;
        }

        public void setDAdvice(List<String> DAdvice) {
            this.DAdvice = DAdvice;
        }
    }
}
