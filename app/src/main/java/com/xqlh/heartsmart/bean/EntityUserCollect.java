package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/21.
 */

public class EntityUserCollect {

    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"d2756697621d4805a969a86a66229635","ArticleTitle":"那些极度自律的人，后来都怎么样了","PublishDate":"2017-04-21T09:56:02","ShowTimes":0,"PraiseTimes":0,"CommentTimes":0,"UserID":"bcd32aab148b462dbf15c33d697496a1","UserName":"小编","AuditorID":"bcd32aab148b462dbf15c33d697496a1","AuditorTime":"2017-04-21T09:58:19","AricleState":1,"AricleStateStr":null,"TitlePic":"http://resources.bnuxq.com:8082/2017042109554981E6CAQ4QT.jpg","Content":" 255);\">作者：苏希西，内心纯真的写字匠，信奉所有梦想都需全力以赴，分享温暖、优雅和有品质的生活方式。公众号：苏希西（ID：bysunxixi）<br/>原题：《那些自律到极致的人，后来都怎么样了》<\/p>","ArticleTypeID":"153dd1e6ebab4279931875d654ddc001","ArticleTypeStr":"成长","Lables":"成长|学习"}]
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
         * ID : d2756697621d4805a969a86a66229635
         * ArticleTitle : 那些极度自律的人，后来都怎么样了
         * PublishDate : 2017-04-21T09:56:02
         * ShowTimes : 0
         * PraiseTimes : 0
         * CommentTimes : 0
         * UserID : bcd32aab148b462dbf15c33d697496a1
         * UserName : 小编
         * AuditorID : bcd32aab148b462dbf15c33d697496a1
         * AuditorTime : 2017-04-21T09:58:19
         * AricleState : 1
         * AricleStateStr : null
         * TitlePic : http://resources.bnuxq.com:8082/2017042109554981E6CAQ4QT.jpg
         * Content :  255);">作者：苏希西，内心纯真的写字匠，信奉所有梦想都需全力以赴，分享温暖、优雅和有品质的生活方式。公众号：苏希西（ID：bysunxixi）<br/>原题：《那些自律到极致的人，后来都怎么样了》</p>
         * ArticleTypeID : 153dd1e6ebab4279931875d654ddc001
         * ArticleTypeStr : 成长
         * Lables : 成长|学习
         */

        private String ID;
        private String ArticleTitle;
        private String PublishDate;
        private int ShowTimes;
        private int PraiseTimes;
        private int CommentTimes;
        private String UserID;
        private String UserName;
        private String AuditorID;
        private String AuditorTime;
        private int AricleState;
        private Object AricleStateStr;
        private String TitlePic;
        private String Content;
        private String ArticleTypeID;
        private String ArticleTypeStr;
        private String Lables;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getArticleTitle() {
            return ArticleTitle;
        }

        public void setArticleTitle(String ArticleTitle) {
            this.ArticleTitle = ArticleTitle;
        }

        public String getPublishDate() {
            return PublishDate;
        }

        public void setPublishDate(String PublishDate) {
            this.PublishDate = PublishDate;
        }

        public int getShowTimes() {
            return ShowTimes;
        }

        public void setShowTimes(int ShowTimes) {
            this.ShowTimes = ShowTimes;
        }

        public int getPraiseTimes() {
            return PraiseTimes;
        }

        public void setPraiseTimes(int PraiseTimes) {
            this.PraiseTimes = PraiseTimes;
        }

        public int getCommentTimes() {
            return CommentTimes;
        }

        public void setCommentTimes(int CommentTimes) {
            this.CommentTimes = CommentTimes;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getAuditorID() {
            return AuditorID;
        }

        public void setAuditorID(String AuditorID) {
            this.AuditorID = AuditorID;
        }

        public String getAuditorTime() {
            return AuditorTime;
        }

        public void setAuditorTime(String AuditorTime) {
            this.AuditorTime = AuditorTime;
        }

        public int getAricleState() {
            return AricleState;
        }

        public void setAricleState(int AricleState) {
            this.AricleState = AricleState;
        }

        public Object getAricleStateStr() {
            return AricleStateStr;
        }

        public void setAricleStateStr(Object AricleStateStr) {
            this.AricleStateStr = AricleStateStr;
        }

        public String getTitlePic() {
            return TitlePic;
        }

        public void setTitlePic(String TitlePic) {
            this.TitlePic = TitlePic;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getArticleTypeID() {
            return ArticleTypeID;
        }

        public void setArticleTypeID(String ArticleTypeID) {
            this.ArticleTypeID = ArticleTypeID;
        }

        public String getArticleTypeStr() {
            return ArticleTypeStr;
        }

        public void setArticleTypeStr(String ArticleTypeStr) {
            this.ArticleTypeStr = ArticleTypeStr;
        }

        public String getLables() {
            return Lables;
        }

        public void setLables(String Lables) {
            this.Lables = Lables;
        }
    }
}
