package com.xqlh.heartsmart.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */

public class EntityAppraisalRecommend {

    /**
     * code : 1
     * msg : OK
     * Result : [{"ID":"1","PsyName":"心理健康状况测试（SCL...","PsyPic":"http://resources.bnuxq.com:8082/2017091310370619WT3ETO35.jpg","Title":"来测测你的心理健康程度吧","TopicCount":90,"TestMan":20},{"ID":"35","PsyName":"人格三因素测验","PsyPic":"http://resources.bnuxq.com:8082/20171205115121777W4GO4PS.jpg","Title":"专业的人格测评，测一下，...","TopicCount":85,"TestMan":13},{"ID":"11","PsyName":"抑郁自评量表","PsyPic":"http://resources.bnuxq.com:8082/2017120510404569TQHKVUKJ.jpg","Title":"生活没乐趣？测测自己是否...","TopicCount":20,"TestMan":12},{"ID":"10","PsyName":"焦虑自评量表","PsyPic":"http://resources.bnuxq.com:8082/20171205102417208UCFZZ91.jpg","Title":"你最近是否经常焦虑呢？","TopicCount":20,"TestMan":11},{"ID":"8b50be2fc89245cd9611271dc8943ab7","PsyName":"十二星座的绝情指数 谁排...","PsyPic":"http://resource.3uol.com/images/a/ceshi/3017_83722386_m.jpg","Title":"十二星座的绝情指数 谁排...","TopicCount":1,"TestMan":8},{"ID":"d1a961247c9b425e9578d878d4d2d69c","PsyName":"五根手指测你的运势","PsyPic":"http://resource.3uol.com/images/a/ceshi/3017_76492519_m.jpg","Title":"五根手指测你的运势","TopicCount":1,"TestMan":6}]
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
         * ID : 1
         * PsyName : 心理健康状况测试（SCL...
         * PsyPic : http://resources.bnuxq.com:8082/2017091310370619WT3ETO35.jpg
         * Title : 来测测你的心理健康程度吧
         * TopicCount : 90
         * TestMan : 20
         */

        private String ID;
        private String PsyName;
        private String PsyPic;
        private String Title;
        private int TopicCount;
        private int TestMan;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getPsyName() {
            return PsyName;
        }

        public void setPsyName(String PsyName) {
            this.PsyName = PsyName;
        }

        public String getPsyPic() {
            return PsyPic;
        }

        public void setPsyPic(String PsyPic) {
            this.PsyPic = PsyPic;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public int getTopicCount() {
            return TopicCount;
        }

        public void setTopicCount(int TopicCount) {
            this.TopicCount = TopicCount;
        }

        public int getTestMan() {
            return TestMan;
        }

        public void setTestMan(int TestMan) {
            this.TestMan = TestMan;
        }
    }
}
