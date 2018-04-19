package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 */

public class EntityArticleNewest {


    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"xxxxxxxxxxxxxxxxxxx","Title":"xxxxxxxxxxxxxxx","introduction":"xxxxxxxxxxx","UserID":"xxxxxxxxxxxxxx","UserName":"xxxxxxxxxxxx","CreateTime":"2015-02-01","ShowTimes":10,"PraiseTimes":1,"CommentTimes":12,"ArticleTypeID":"xxxxxxxxxxxxxxxxxxxx","ArticleTypeStr":"xxxxxxxxxxx","TitlePic":"http://resource.bnuxq.com/img/xxxx.jpg","Lables":["xxxxx0","xxxxxxx","xxxxxxxx"],"CollectionTimes":10,"IsPraise":false,"IsCollection":false}]
     * Total : 12
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
         * ID : xxxxxxxxxxxxxxxxxxx
         * Title : xxxxxxxxxxxxxxx
         * introduction : xxxxxxxxxxx
         * UserID : xxxxxxxxxxxxxx
         * UserName : xxxxxxxxxxxx
         * CreateTime : 2015-02-01
         * ShowTimes : 10
         * PraiseTimes : 1
         * CommentTimes : 12
         * ArticleTypeID : xxxxxxxxxxxxxxxxxxxx
         * ArticleTypeStr : xxxxxxxxxxx
         * TitlePic : http://resource.bnuxq.com/img/xxxx.jpg
         * Lables : ["xxxxx0","xxxxxxx","xxxxxxxx"]
         * CollectionTimes : 10
         * IsPraise : false
         * IsCollection : false
         */

        private String ID;
        private String Title;
        private String introduction;
        private String UserID;
        private String UserName;
        private String CreateTime;
        private int ShowTimes;
        private int PraiseTimes;
        private int CommentTimes;
        private String ArticleTypeID;
        private String ArticleTypeStr;
        private String TitlePic;
        private int CollectionTimes;
        private boolean IsPraise;
        private boolean IsCollection;
        private List<String> Lables;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
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

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
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

        public String getTitlePic() {
            return TitlePic;
        }

        public void setTitlePic(String TitlePic) {
            this.TitlePic = TitlePic;
        }

        public int getCollectionTimes() {
            return CollectionTimes;
        }

        public void setCollectionTimes(int CollectionTimes) {
            this.CollectionTimes = CollectionTimes;
        }

        public boolean isIsPraise() {
            return IsPraise;
        }

        public void setIsPraise(boolean IsPraise) {
            this.IsPraise = IsPraise;
        }

        public boolean isIsCollection() {
            return IsCollection;
        }

        public void setIsCollection(boolean IsCollection) {
            this.IsCollection = IsCollection;
        }

        public List<String> getLables() {
            return Lables;
        }

        public void setLables(List<String> Lables) {
            this.Lables = Lables;
        }
    }
}
