package com.xqlh.heartsmart.utils;

import com.xqlh.heartsmart.app.App;

import java.io.File;

/**
 * Created by codeest on 2016/8/3.
 */
public class Constants {

    public static final String PATH_DATA = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String BASE_URL = "http://open.bnuxq.com/";
    public static final int DEFAULT_TIMEOUT = 20000;

    public static final String MESSAGE_TOKEN = "messageToken";

}
