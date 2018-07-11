package com.xqlh.heartsmart.sqliteHelp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xqlh.heartsmart.utils.Constants;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/8/21.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper {


    public MySqliteOpenHelper(Context context) {
        super(context, Constants.DB, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_RECORD);
        db.execSQL(Constants.createMusicTable);			//创建音乐表
        db.execSQL(Constants.createLastPlayTable);		//创建播放历史表
        db.execSQL(Constants.createPlaylistTable);		//创建歌单表
        db.execSQL(Constants.createListinfoTable);		//创建歌单歌曲表
        Log.i("lz", "数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e(TAG, "onUpgrade: oldVersion ="+oldVersion );
        Log.e(TAG, "onUpgrade: newVersion ="+newVersion );
        if (oldVersion < Constants.DB_VERSION){
            db.execSQL("drop table if exists "+Constants.MUSIC_TABLE);
            db.execSQL("drop table if exists "+Constants.LAST_PLAY_TABLE);
            db.execSQL("drop table if exists "+ Constants.PLAY_LIST_TABLE);
            db.execSQL("drop table if exists "+ Constants.PLAY_LISY_MUSIC_TABLE);
            onCreate(db);
        }

    }
}
