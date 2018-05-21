package com.xqlh.heartsmart.utils;

import com.xqlh.heartsmart.app.App;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import okhttp3.MediaType;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String BASE_URL = "http://open.bnuxq.com/";
    public static final int DEFAULT_TIMEOUT = 20000;

    public static final String CHECKINFOR = "CheckInfor";

    public static final String IS_LOGIN = "isLogin";

    public static final String IS_FIRST = "isFirst";

    public static final String MESSAGE_TOKEN = "messageToken";

    public static final String LOGIN_TOKEN = "loginToken";

    public static final String ACCOUNT = "account";

    public static final String GET_PHONE_BY_ACCOUNT_TOKEN = "getPhoneByAccount";

    public static final String TOPIC_INDEX = "topicIndex";

    public static final String COLLECT = "collect";


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String getYYD(String time) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(time);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
//        System.out.println((date.getYear()+1900)+"-"+(date.getMonth()+1)+"-"+(date.getDay()+12));
    }

}
