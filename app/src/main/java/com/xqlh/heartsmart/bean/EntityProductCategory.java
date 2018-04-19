package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8.
 */

public class EntityProductCategory {

    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"XXXXXXXXXXXXXXXXX","Name":"拥抱系统","ProductStyle":0,"ProductTypeID":"xxxxxxxxxxxxx","AbstractStr":"简介","DescribeStr":"介绍","PicURL":"http://resource.bnuxq.com/img/2017/05/xxxxx.jpg","MainPic":"http://resource.bnuxq.com/img/2017/05/xxxxx.jpg","IsPublish":true,"CreateTime":"2017-05-02","State":true,"Recommend":true},{"ID":"XXXXXXXXXXXXXXXXX","Name":"击打系统","ProductStyle":0,"ProductTypeID":"xxxxxxxxxxxxx","AbstractStr":"简介","DescribeStr":"介绍","PicURL":"http://resource.bnuxq.com/img/2017/05/xxxxx.jpg","MainPic":"http://resource.bnuxq.com/img/2017/05/xxxxx.jpg","IsPublish":true,"CreateTime":"2017-05-02","State":true,"Recommend":true}]
     * Total : 20
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
         * ID : XXXXXXXXXXXXXXXXX
         * Name : 拥抱系统
         * ProductStyle : 0
         * ProductTypeID : xxxxxxxxxxxxx
         * AbstractStr : 简介
         * DescribeStr : 介绍
         * PicURL : http://resource.bnuxq.com/img/2017/05/xxxxx.jpg
         * MainPic : http://resource.bnuxq.com/img/2017/05/xxxxx.jpg
         * IsPublish : true
         * CreateTime : 2017-05-02
         * State : true
         * Recommend : true
         */

        private String ID;
        private String Name;
        private int ProductStyle;
        private String ProductTypeID;
        private String AbstractStr;
        private String DescribeStr;
        private String PicURL;
        private String MainPic;
        private boolean IsPublish;
        private String CreateTime;
        private boolean State;
        private boolean Recommend;

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

        public int getProductStyle() {
            return ProductStyle;
        }

        public void setProductStyle(int ProductStyle) {
            this.ProductStyle = ProductStyle;
        }

        public String getProductTypeID() {
            return ProductTypeID;
        }

        public void setProductTypeID(String ProductTypeID) {
            this.ProductTypeID = ProductTypeID;
        }

        public String getAbstractStr() {
            return AbstractStr;
        }

        public void setAbstractStr(String AbstractStr) {
            this.AbstractStr = AbstractStr;
        }

        public String getDescribeStr() {
            return DescribeStr;
        }

        public void setDescribeStr(String DescribeStr) {
            this.DescribeStr = DescribeStr;
        }

        public String getPicURL() {
            return PicURL;
        }

        public void setPicURL(String PicURL) {
            this.PicURL = PicURL;
        }

        public String getMainPic() {
            return MainPic;
        }

        public void setMainPic(String MainPic) {
            this.MainPic = MainPic;
        }

        public boolean isIsPublish() {
            return IsPublish;
        }

        public void setIsPublish(boolean IsPublish) {
            this.IsPublish = IsPublish;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public boolean isState() {
            return State;
        }

        public void setState(boolean State) {
            this.State = State;
        }

        public boolean isRecommend() {
            return Recommend;
        }

        public void setRecommend(boolean Recommend) {
            this.Recommend = Recommend;
        }
    }
}
