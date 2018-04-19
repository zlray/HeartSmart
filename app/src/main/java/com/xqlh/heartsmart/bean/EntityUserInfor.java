package com.xqlh.heartsmart.bean;

/**
 * Created by Administrator on 2018/4/11.
 */

public class EntityUserInfor {


    /**
     * code : 1
     * msg : OK
     * Result : {"ID":"e55827a8a1584e4eacfbb444da34585e","Name":"xiaomeinv","UserName":"xiaomeinv","Nickname":"一见你就#^_^#","Headimgurl":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqyvkemLAicx6NZaLNa0yEjmfCju1pYsTZqXCx2QbuLExbHTEWruxbwdCiaiapouwrXic0SwlsicW3OicyQ/0","Sex":"1","IsAdmin":false,"IsHighestAdmin":false,"IsSystemAdmin":false,"BirthDate":"2000-01-03","Age":"18","Telephone":"15732632663","Email":null,"EducationLevel":"0","Residence":null,"province":"","provincestr":null,"city":"","citystr":null,"EnterpriseID":null,"GroupID":null,"GroupName":null,"ConsultantTitle":"0","AdminUserID":null,"AdminUserName":null,"AdminHeadimgurl":null,"Openid":"ocYJ602fAXWl1EA-kcu_GE8UHi8w","IDNum":null}
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
         * ID : e55827a8a1584e4eacfbb444da34585e
         * Name : xiaomeinv
         * UserName : xiaomeinv
         * Nickname : 一见你就#^_^#
         * Headimgurl : http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqyvkemLAicx6NZaLNa0yEjmfCju1pYsTZqXCx2QbuLExbHTEWruxbwdCiaiapouwrXic0SwlsicW3OicyQ/0
         * Sex : 1
         * IsAdmin : false
         * IsHighestAdmin : false
         * IsSystemAdmin : false
         * BirthDate : 2000-01-03
         * Age : 18
         * Telephone : 15732632663
         * Email : null
         * EducationLevel : 0
         * Residence : null
         * province :
         * provincestr : null
         * city :
         * citystr : null
         * EnterpriseID : null
         * GroupID : null
         * GroupName : null
         * ConsultantTitle : 0
         * AdminUserID : null
         * AdminUserName : null
         * AdminHeadimgurl : null
         * Openid : ocYJ602fAXWl1EA-kcu_GE8UHi8w
         * IDNum : null
         */

        private String ID;
        private String Name;
        private String UserName;
        private String Nickname;
        private String Headimgurl;
        private String Sex;
        private boolean IsAdmin;
        private boolean IsHighestAdmin;
        private boolean IsSystemAdmin;
        private String BirthDate;
        private String Age;
        private String Telephone;
        private Object Email;
        private String EducationLevel;
        private Object Residence;
        private String province;
        private Object provincestr;
        private String city;
        private Object citystr;
        private Object EnterpriseID;
        private Object GroupID;
        private Object GroupName;
        private String ConsultantTitle;
        private Object AdminUserID;
        private Object AdminUserName;
        private Object AdminHeadimgurl;
        private String Openid;
        private Object IDNum;

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

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getNickname() {
            return Nickname;
        }

        public void setNickname(String Nickname) {
            this.Nickname = Nickname;
        }

        public String getHeadimgurl() {
            return Headimgurl;
        }

        public void setHeadimgurl(String Headimgurl) {
            this.Headimgurl = Headimgurl;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public boolean isIsAdmin() {
            return IsAdmin;
        }

        public void setIsAdmin(boolean IsAdmin) {
            this.IsAdmin = IsAdmin;
        }

        public boolean isIsHighestAdmin() {
            return IsHighestAdmin;
        }

        public void setIsHighestAdmin(boolean IsHighestAdmin) {
            this.IsHighestAdmin = IsHighestAdmin;
        }

        public boolean isIsSystemAdmin() {
            return IsSystemAdmin;
        }

        public void setIsSystemAdmin(boolean IsSystemAdmin) {
            this.IsSystemAdmin = IsSystemAdmin;
        }

        public String getBirthDate() {
            return BirthDate;
        }

        public void setBirthDate(String BirthDate) {
            this.BirthDate = BirthDate;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String Age) {
            this.Age = Age;
        }

        public String getTelephone() {
            return Telephone;
        }

        public void setTelephone(String Telephone) {
            this.Telephone = Telephone;
        }

        public Object getEmail() {
            return Email;
        }

        public void setEmail(Object Email) {
            this.Email = Email;
        }

        public String getEducationLevel() {
            return EducationLevel;
        }

        public void setEducationLevel(String EducationLevel) {
            this.EducationLevel = EducationLevel;
        }

        public Object getResidence() {
            return Residence;
        }

        public void setResidence(Object Residence) {
            this.Residence = Residence;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public Object getProvincestr() {
            return provincestr;
        }

        public void setProvincestr(Object provincestr) {
            this.provincestr = provincestr;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getCitystr() {
            return citystr;
        }

        public void setCitystr(Object citystr) {
            this.citystr = citystr;
        }

        public Object getEnterpriseID() {
            return EnterpriseID;
        }

        public void setEnterpriseID(Object EnterpriseID) {
            this.EnterpriseID = EnterpriseID;
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

        public String getConsultantTitle() {
            return ConsultantTitle;
        }

        public void setConsultantTitle(String ConsultantTitle) {
            this.ConsultantTitle = ConsultantTitle;
        }

        public Object getAdminUserID() {
            return AdminUserID;
        }

        public void setAdminUserID(Object AdminUserID) {
            this.AdminUserID = AdminUserID;
        }

        public Object getAdminUserName() {
            return AdminUserName;
        }

        public void setAdminUserName(Object AdminUserName) {
            this.AdminUserName = AdminUserName;
        }

        public Object getAdminHeadimgurl() {
            return AdminHeadimgurl;
        }

        public void setAdminHeadimgurl(Object AdminHeadimgurl) {
            this.AdminHeadimgurl = AdminHeadimgurl;
        }

        public String getOpenid() {
            return Openid;
        }

        public void setOpenid(String Openid) {
            this.Openid = Openid;
        }

        public Object getIDNum() {
            return IDNum;
        }

        public void setIDNum(Object IDNum) {
            this.IDNum = IDNum;
        }
    }
}
