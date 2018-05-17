package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/17.
 */

public class EntityUserReport {

    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"d189de806ac4490cb453355efcfde5af","Name":"中学生身心健康筛查量表","Title":"你的身体和心理还好吗？","UserID":"e55827a8a1584e4eacfbb444da34585e","CreateTime":"2018-04-17 12:56:10","EvaluationTypeStr":"测评系统","TotalScore":"235","TotalLevelName":"亚健康状态","PsychtestPic":"http://resources.bnuxq.com:8082/20180315094843057RW4NHWQ.jpg","PsychtestSource":"自购","QrCodePic":"http://resources.bnuxq.com:8082/images/QrCode/ERd189de806ac4490cb453355efcfde5af.png","IsShowReport":true},{"ID":"d1892baea778400cb83c5c2622c7c387","Name":"音乐放松","Title":null,"UserID":"e55827a8a1584e4eacfbb444da34585e","CreateTime":"2018-04-17 11:23:36","EvaluationTypeStr":"生物反馈训练系统","TotalScore":"100","TotalLevelName":"训练已完成","PsychtestPic":null,"PsychtestSource":"未知","QrCodePic":"http://resources.bnuxq.com:8082/images/QrCode/ERd1892baea778400cb83c5c2622c7c387.png","IsShowReport":true}]
     * Total : 55
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
         * ID : d189de806ac4490cb453355efcfde5af
         * Name : 中学生身心健康筛查量表
         * Title : 你的身体和心理还好吗？
         * UserID : e55827a8a1584e4eacfbb444da34585e
         * CreateTime : 2018-04-17 12:56:10
         * EvaluationTypeStr : 测评系统
         * TotalScore : 235
         * TotalLevelName : 亚健康状态
         * PsychtestPic : http://resources.bnuxq.com:8082/20180315094843057RW4NHWQ.jpg
         * PsychtestSource : 自购
         * QrCodePic : http://resources.bnuxq.com:8082/images/QrCode/ERd189de806ac4490cb453355efcfde5af.png
         * IsShowReport : true
         */

        private String ID;
        private String Name;
        private String Title;
        private String UserID;
        private String CreateTime;
        private String EvaluationTypeStr;
        private String TotalScore;
        private String TotalLevelName;
        private String PsychtestPic;
        private String PsychtestSource;
        private String QrCodePic;
        private boolean IsShowReport;

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

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getEvaluationTypeStr() {
            return EvaluationTypeStr;
        }

        public void setEvaluationTypeStr(String EvaluationTypeStr) {
            this.EvaluationTypeStr = EvaluationTypeStr;
        }

        public String getTotalScore() {
            return TotalScore;
        }

        public void setTotalScore(String TotalScore) {
            this.TotalScore = TotalScore;
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

        public String getPsychtestSource() {
            return PsychtestSource;
        }

        public void setPsychtestSource(String PsychtestSource) {
            this.PsychtestSource = PsychtestSource;
        }

        public String getQrCodePic() {
            return QrCodePic;
        }

        public void setQrCodePic(String QrCodePic) {
            this.QrCodePic = QrCodePic;
        }

        public boolean isIsShowReport() {
            return IsShowReport;
        }

        public void setIsShowReport(boolean IsShowReport) {
            this.IsShowReport = IsShowReport;
        }
    }
}
