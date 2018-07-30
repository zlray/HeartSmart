package com.xqlh.heartsmart.ui.mine.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.PlayBarBaseActivity;
import com.xqlh.heartsmart.bean.MusicInfo;
import com.xqlh.heartsmart.bean.PlayListInfo;
import com.xqlh.heartsmart.service.MusicPlayerService;
import com.xqlh.heartsmart.sqliteHelp.DBManager;
import com.xqlh.heartsmart.ui.mine.adapter.HomeListViewAdapter;
import com.xqlh.heartsmart.utils.ChineseToEnglish;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.MyMusicUtil;
import com.xqlh.heartsmart.widget.TitleBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicRelaxActivity extends PlayBarBaseActivity {
    private static final String TAG = MusicRelaxActivity.class.getName();
    private TitleBar titlebar;
    private DBManager dbManager;
    private LinearLayout localMusicLl;
    private LinearLayout lastPlayLl;
    private LinearLayout myLoveLl;
    private LinearLayout myListTitleLl;
    private TextView localMusicCountTv;
    private TextView lastPlayCountTv;
    private TextView myLoveCountTv;
    private TextView myPLCountTv;
    private ImageView myPLArrowIv;
    private ImageView myPLAddIv;
    private ListView listView;
    private HomeListViewAdapter adapter;
    private List<PlayListInfo> playListInfos;
    private int count;
    private boolean isOpenMyPL = false; //标识我的歌单列表打开状态
    private boolean isStartTheme = false;

    private int curMusicId;
    private String curMusicPath;
    private String scanPath;
    private int musicCount = 0;
    private List<MusicInfo> musicInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_relax);
        dbManager = DBManager.getInstance(this);

        //初始化控件
        initView();

        //搜索本地歌曲
        startScanLocalMusic();
        //
        initTtileBar();
        initCurPlaying();

        Intent startIntent = new Intent(MusicRelaxActivity.this, MusicPlayerService.class);
        startService(startIntent);
    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intentBroadcast = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
//            intentBroadcast.putExtra(Constants.COMMAND, Constants.COMMAND_RELEASE);
//            sendBroadcast(intentBroadcast);
//            Intent stopIntent = new Intent(MusicRelaxActivity.this, MusicPlayerService.class);
//            stopService(stopIntent);
//        }
//        return true;
//    }

    public void initTtileBar() {

        titlebar = (TitleBar) findViewById(R.id.titlebar);
        titlebar.setLeftImageResource(R.drawable.return_button);
        titlebar.setTitle("音乐放松");
        titlebar.setTitleColor(Color.WHITE);
        titlebar.setLeftClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
    }


    public void startScanLocalMusic() {
        //
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String[] muiscInfoArray = new String[]{
                            MediaStore.Audio.Media.TITLE,               //歌曲名称
                            MediaStore.Audio.Media.ARTIST,              //歌曲歌手
                            MediaStore.Audio.Media.ALBUM,               //歌曲的专辑名
                            MediaStore.Audio.Media.DURATION,            //歌曲时长
                            MediaStore.Audio.Media.DATA};               //歌曲文件的全路径
                    Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            muiscInfoArray, null, null, null);
                    Log.i("lz", "歌曲数目" + cursor.getCount() + "--------------");

                    if (cursor != null && cursor.getCount() != 0) {

                        //
                        musicInfoList = new ArrayList<MusicInfo>();
                        Log.i(TAG, "歌曲的数目" + cursor.getCount());
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
                            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
                            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
                            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
                            String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));

                            File file = new File(path);
                            String parentPath = file.getParentFile().getPath();

                            name = replaseUnKnowe(name);
                            singer = replaseUnKnowe(singer);
                            album = replaseUnKnowe(album);
                            path = replaseUnKnowe(path);

                            //
                            MusicInfo musicInfo = new MusicInfo();
                            musicInfo.setName(name);
                            musicInfo.setSinger(singer);
                            musicInfo.setAlbum(album);
                            musicInfo.setPath(path);
                            Log.e(TAG, "run: parentPath = " + parentPath);
                            musicInfo.setParentPath(parentPath);
                            musicInfo.setFirstLetter(ChineseToEnglish.StringToPinyinSpecial(name).toUpperCase().charAt(0) + "");

                            musicInfoList.add(musicInfo);
                            scanPath = path;
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        //扫描完成获取一下当前播放音乐及路径
                        curMusicId = MyMusicUtil.getIntShared(Constants.KEY_ID);
                        curMusicPath = dbManager.getMusicPath(curMusicId);

                        // 根据a-z进行排序源数据
                        Collections.sort(musicInfoList);
                        dbManager.updateAllMusic(musicInfoList);
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "run: error = ", e);
                }
            }
        }.start();


        count = dbManager.getMusicCount(Constants.LIST_ALLMUSIC);
        Log.i("lz", ".....................: " + count);
        localMusicCountTv.setText(count + "");

        count = dbManager.getMusicCount(Constants.LIST_LASTPLAY);
        lastPlayCountTv.setText(count + "");

        count = dbManager.getMusicCount(Constants.LIST_MYLOVE);
        Log.i("lz", "我喜爱的音乐个数" + count);
        myLoveCountTv.setText(count + "");

        count = dbManager.getMusicCount(Constants.LIST_MYPLAY);
        myPLCountTv.setText("(" + count + ")");

        adapter.updateDataList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        count = dbManager.getMusicCount(Constants.LIST_ALLMUSIC);
        localMusicCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constants.LIST_LASTPLAY);
        lastPlayCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constants.LIST_MYLOVE);
        Log.i("lz", "我喜欢的个数" + count);
        myLoveCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constants.LIST_MYPLAY);
        myPLCountTv.setText("(" + count + ")");
        adapter.updateDataList();
    }


    //初始化当前播放音乐，有可能当前正在播放音乐已经被过滤掉了
    private void initCurPlaying() {

        try {
            boolean contain = false;
            int id = 1;
            if (musicInfoList != null) {
                for (MusicInfo info : musicInfoList) {
                    Log.d(TAG, "initCurPlaying: info.getPath() = " + info.getPath());
                    Log.d(TAG, "initCurPlaying: curMusicPath = " + curMusicPath);
                    if (info.getPath().equals(curMusicPath)) {
                        contain = true;
                        Log.d(TAG, "initCurPlaying: musicInfoList.indexOf(info) = " + musicInfoList.indexOf(info));
                        id = musicInfoList.indexOf(info) + 1;
                    }
                }
            }
            if (contain) {
                Log.d(TAG, "initCurPlaying: contains");
                Log.d(TAG, "initCurPlaying: id = " + id);
                MyMusicUtil.setShared(Constants.KEY_ID, id);
            } else {
                Log.d(TAG, "initCurPlaying: !!!contains");
                Intent intent = new Intent(MusicPlayerService.PLAYER_MANAGER_ACTION);
                intent.putExtra(Constants.COMMAND, Constants.COMMAND_STOP);
                sendBroadcast(intent);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String replaseUnKnowe(String oldStr) {
        try {
            if (oldStr != null) {
                if (oldStr.equals("<unknown>")) {
                    oldStr = oldStr.replaceAll("<unknown>", "未知");
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "replaseUnKnowe: error = ", e);
        }
        return oldStr;
    }

    public void initView() {
        localMusicLl = (LinearLayout) findViewById(R.id.home_local_music_ll);
        lastPlayLl = (LinearLayout) findViewById(R.id.home_recently_music_ll);
        myLoveLl = (LinearLayout) findViewById(R.id.home_my_love_music_ll);
        myListTitleLl = (LinearLayout) findViewById(R.id.home_my_list_title_ll);
        listView = (ListView) findViewById(R.id.home_my_list_lv);
        localMusicCountTv = (TextView) findViewById(R.id.home_local_music_count_tv);
        lastPlayCountTv = (TextView) findViewById(R.id.home_recently_music_count_tv);
        myLoveCountTv = (TextView) findViewById(R.id.home_my_love_music_count_tv);
        myPLCountTv = (TextView) findViewById(R.id.home_my_list_count_tv);
        myPLArrowIv = (ImageView) findViewById(R.id.home_my_pl_arror_iv);
        myPLAddIv = (ImageView) findViewById(R.id.home_my_pl_add_iv);


        localMusicLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicRelaxActivity.this, LocalMusicActivity.class);
                startActivity(intent);
            }
        });

        lastPlayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicRelaxActivity.this, LastMyloveActivity.class);
                intent.putExtra(Constants.LABEL, Constants.LABEL_LAST);
                startActivity(intent);
            }
        });

        myLoveLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MusicRelaxActivity.this, LastMyloveActivity.class);
                intent.putExtra(Constants.LABEL, Constants.LABEL_MYLOVE);
                startActivity(intent);
            }
        });

        playListInfos = dbManager.getMyPlayList();
        adapter = new HomeListViewAdapter(playListInfos, this, dbManager);
        listView.setAdapter(adapter);
        myPLAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加歌单
                final AlertDialog.Builder builder = new AlertDialog.Builder(MusicRelaxActivity.this);
                View view = LayoutInflater.from(MusicRelaxActivity.this).inflate(R.layout.dialog_create_playlist, null);
                final EditText playlistEt = (EditText) view.findViewById(R.id.dialog_playlist_name_et);
                builder.setView(view);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = playlistEt.getText().toString();
                        if (TextUtils.isEmpty(name)) {
                            Toast.makeText(MusicRelaxActivity.this, "请输入歌单名", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        dbManager.createPlaylist(name);
                        dialog.dismiss();
                        adapter.updateDataList();
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();//配置好后再builder show
            }
        });
        myListTitleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //展现我的歌单
                if (isOpenMyPL) {
                    isOpenMyPL = false;
                    myPLArrowIv.setImageResource(R.drawable.arrow_right);
                    listView.setVisibility(View.GONE);
                } else {
                    isOpenMyPL = true;
                    myPLArrowIv.setImageResource(R.drawable.arrow_down);
                    listView.setVisibility(View.VISIBLE);
                    playListInfos = dbManager.getMyPlayList();
                    adapter = new HomeListViewAdapter(playListInfos, MusicRelaxActivity.this, dbManager);
                    listView.setAdapter(adapter);
                }
            }
        });
    }

    public void updatePlaylistCount() {
        count = dbManager.getMusicCount(Constants.LIST_MYPLAY);
        myPLCountTv.setText("(" + count + ")");
    }

}
