package com.xqlh.heartsmart.Event;

/**
 * Created by Administrator on 2018/5/8.
 */

public class EventUpdateUserInfor {
    private String msg;

    public EventUpdateUserInfor(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
