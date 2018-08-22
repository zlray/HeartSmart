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

    //service名
    public static final String SERVICE_NAME = "com.example.vinyl.service.MediaPlayerService";//服务的名称为包名+类名

    public static final String MP_FILTER = "com.example.vinyl.start_mediaplayer";


    //播放状态
    public static final String STATUS = "status";

    public static final int STATUS_STOP = 0; //停止状态
    public static final int STATUS_PLAY = 1; //播放状态
    public static final int STATUS_PAUSE = 2; //暂停状态
    public static final int STATUS_RUN = 3;  //   状态

    public static final String CHECKINFOR = "CheckInfor";

    public static final String IS_LOGIN = "isLogin";

    public static final String IS_FIRST = "isFirst";

    public static final String MESSAGE_TOKEN = "messageToken";

    public static final String LOGIN_TOKEN = "loginToken";

    public static final String USER_ID = "userId";

    public static final String ACCOUNT = "account";

    public static final String GET_PHONE_BY_ACCOUNT_TOKEN = "getPhoneByAccount";

    public static final String KEYWORD = "keyword";

    public static final String TOPIC_INDEX = "topicIndex";

    public static final String COLLECT = "collect";

    //音乐表
    public static final String MUSIC_TABLE = "music_table";

    public static final String ID_COLUMN = "id";
    public static final String MUSIC_ID_COLUMN = "music_id";
    public static final String NAME_COLUMN = "name";
    public static final String SINGER_COLUMN = "singer";
    public static final String DURATION_COLUMN = "duration";
    public static final String ALBUM_COLUMN = "album";
    public static final String PATH_COLUMN = "path";
    public static final String PARENT_PATH_COLUMN = "parent_path";
    public static final String FIRST_LETTER_COLUMN = "first_letter";
    public static final String LOVE_COLUMN = "love";


    //播放模式
    public static final int PLAYMODE_SEQUENCE = -1;
    public static final int PLAYMODE_SINGLE_REPEAT = 1;
    public static final int PLAYMODE_RANDOM = 2;

    public static final String PLAYMODE_SEQUENCE_TEXT = "顺序播放";
    public static final String PLAYMODE_RANDOM_TEXT = "随机播放";
    public static final String PLAYMODE_SINGLE_REPEAT_TEXT = "单曲循环";


    //Activity label

    public static final String LABEL = "label";
    public static final String LABEL_MYLOVE = "我的喜爱";
    public static final String LABEL_LAST = "最近播放";
    public static final String LABEL_LOCAL = "本地音乐";

    public static final int ACTIVITY_LOCAL = 20; //我喜爱
    public static final int ACTIVITY_RECENTPLAY = 21;//最近播放
    public static final int ACTIVITY_MYLOVE = 22; //我喜爱
    public static final int ACTIVITY_MYLIST = 24;//我的歌单


    //SharedPreferences key 常量
    public static final String KEY_ID = "id";
    public static final String KEY_PATH = "path";
    public static final String KEY_MODE = "mode";
    public static final String KEY_LIST = "list";
    public static final String KEY_LIST_ID = "list_id";
    public static final String KEY_CURRENT = "current";
    public static final String KEY_DURATION = "duration";

    public static final String COMMAND = "cmd";
    public static final int COMMAND_INIT = 1; //初始化命令
    public static final int COMMAND_PLAY = 2; //播放命令
    public static final int COMMAND_PAUSE = 3; //暂停命令
    public static final int COMMAND_STOP = 4; //停止命令
    public static final int COMMAND_PROGRESS = 5; //改变进度命令
    public static final int COMMAND_RELEASE = 6; //退出程序时释放

    //handle常量
    public static final int SCAN_ERROR = 0;
    public static final int SCAN_COMPLETE = 1;
    public static final int SCAN_UPDATE = 2;
    public static final int SCAN_NO_MUSIC = 3;


    public static final String APP_ID = "wxcd1027521b897209"; //微信ID


    //主题
    public static final String THEME = "theme";


    //最近播放表
    public static final String LAST_PLAY_TABLE = "last_play_table";

    //歌单表
    public static final String PLAY_LIST_TABLE = "play_list_table";
    //歌单歌曲表
    public static final String PLAY_LISY_MUSIC_TABLE = "play_list_music_table";


    //数据库版本号
    private static final int VERSION = 2;

    //音乐表建表语句
    public static final String createMusicTable = "create table if not exists " + MUSIC_TABLE + "("
            + ID_COLUMN + " integer PRIMARY KEY ,"
            + NAME_COLUMN + " text,"
            + SINGER_COLUMN + " text,"
            + ALBUM_COLUMN + " text,"
            + DURATION_COLUMN + " long,"
            + PATH_COLUMN + " text,"
            + PARENT_PATH_COLUMN + " text,"
            + LOVE_COLUMN + " integer,"
            + FIRST_LETTER_COLUMN + " text );";

    //创建播放历史表
    public static final String createLastPlayTable = "create table if not exists " + LAST_PLAY_TABLE + " ("
            + ID_COLUMN + " integer,"
            + "FOREIGN KEY(id) REFERENCES " + MUSIC_TABLE + " (id) ON DELETE CASCADE);";


    //创建歌单表
    public static final String createPlaylistTable = "create table if not exists " + PLAY_LIST_TABLE + " ("
            + ID_COLUMN + " integer PRIMARY KEY autoincrement,"
            + NAME_COLUMN + " text);";

    //创建歌单歌曲表
    public static final String createListinfoTable = "create table if not exists " + PLAY_LISY_MUSIC_TABLE + " ("
            + ID_COLUMN + " integer,"
            + MUSIC_ID_COLUMN + " integer,"
            + "FOREIGN KEY(id) REFERENCES " + PLAY_LIST_TABLE + "(id) ON DELETE CASCADE,"
            + "FOREIGN KEY(music_id) REFERENCES " + MUSIC_TABLE + " (id) ON DELETE CASCADE) ;";


    //歌曲列表常量
    public static final int LIST_ALLMUSIC = -1;
    public static final int LIST_MYLOVE = 10000;
    public static final int LIST_LASTPLAY = 10001;
    public static final int LIST_DOWNLOAD = 10002;
    public static final int LIST_MYPLAY = 10003; //我的歌单列表
    public static final int LIST_PLAYLIST = 10004;    //歌单音乐列表

    public static final int LIST_SINGER = 10005;    //歌手
    public static final int LIST_ALBUM = 10006;        //专辑
    public static final int LIST_FOLDER = 10007;    //文件夹


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String DB = "record.db";
    public static final int DB_VERSION = 2;

    //创建用户信息表
    public static final String CREATE_RECORD =
            "create table records ("
                    + "_id integer PRIMARY KEY AUTOINCREMENT NOT NULL," //
                    + "record text)"; //记录


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
