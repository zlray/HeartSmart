package com.xqlh.heartsmart.ui.mine.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.MusicInfo;
import com.xqlh.heartsmart.bean.PlayListInfo;
import com.xqlh.heartsmart.sqliteHelp.DBManager;
import com.xqlh.heartsmart.ui.mine.adapter.HomeListViewAdapter;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.widget.TitleBar;

import java.util.List;

import butterknife.BindView;

public class MusicRelaxActivity extends BaseActivity {
    private static final String TAG = MusicRelaxActivity.class.getName();
    @BindView(R.id.titlebar)
    TitleBar titlebar;
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

    private PlayBarFragment playBarFragment;
    private List<MusicInfo> musicInfoList;
    private boolean scanning = false;

    @Override
    public int setContent() {
        return R.layout.activity_music_relax;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
//        startScanLocalMusic();
        dbManager = DBManager.getInstance(MusicRelaxActivity.this);
        initView();
        show();
    }



//    public void startScanLocalMusic() {
//        new Thread() {
//
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    String[] muiscInfoArray = new String[]{
//                            MediaStore.Audio.Media.TITLE,               //歌曲名称
//                            MediaStore.Audio.Media.ARTIST,              //歌曲歌手
//                            MediaStore.Audio.Media.ALBUM,               //歌曲的专辑名
//                            MediaStore.Audio.Media.DURATION,            //歌曲时长
//                            MediaStore.Audio.Media.DATA};               //歌曲文件的全路径
//                    Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                            muiscInfoArray, null, null, null);
//                    if (cursor!= null && cursor.getCount() != 0){
//                        musicInfoList = new ArrayList<MusicInfo>();
//                        Log.i(TAG, "run: cursor.getCount() = " + cursor.getCount());
//                        while (cursor.moveToNext()) {
//                            if (!scanning){
//                                return;
//                            }
//                            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE));
//                            String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST));
//                            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM));
//                            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA));
//                            String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
//
//                            if (filterCb.isChecked() && duration != null && Long.valueOf(duration) < 1000 * 60){
//                                Log.e(TAG, "run: name = "+name+" duration < 1000 * 60" );
//                                continue;
//                            }
//
//                            File file = new File(path);
//                            String parentPath = file.getParentFile().getPath();
//
//                            name = replaseUnKnowe(name);
//                            singer = replaseUnKnowe(singer);
//                            album = replaseUnKnowe(album);
//                            path = replaseUnKnowe(path);
//
//                            MusicInfo musicInfo = new MusicInfo();
//
//                            musicInfo.setName(name);
//                            musicInfo.setSinger(singer);
//                            musicInfo.setAlbum(album);
//                            musicInfo.setPath(path);
//                            Log.e(TAG, "run: parentPath = "+parentPath );
//                            musicInfo.setParentPath(parentPath);
//                            musicInfo.setFirstLetter(ChineseToEnglish.StringToPinyinSpecial(name).toUpperCase().charAt(0)+"");
//
//                            musicInfoList.add(musicInfo);
//                            progress++;
//                            scanPath = path;
//                            musicCount = cursor.getCount();
//                            msg = new Message();    //每次都必须new，必须发送新对象，不然会报错
//                            msg.what = Constant.SCAN_UPDATE;
//                            msg.arg1 = musicCount;
////                                Bundle data = new Bundle();
////                                data.putInt("progress", progress);
////                                data.putString("scanPath", scanPath);
////                                msg.setData(data);
//                            handler.sendMessage(msg);  //更新UI界面
//                            try {
//                                sleep(50);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        //扫描完成获取一下当前播放音乐及路径
//                        curMusicId = MyMusicUtil.getIntShared(Constant.KEY_ID);
//                        curMusicPath = dbManager.getMusicPath(curMusicId);
//
//                        // 根据a-z进行排序源数据
//                        Collections.sort(musicInfoList);
//                        dbManager.updateAllMusic(musicInfoList);
//
//                        //扫描完成
//                        msg = new Message();
//                        msg.what = Constant.SCAN_COMPLETE;
//                        handler.sendMessage(msg);  //更新UI界面
//
//                    }else {
//                        msg = new Message();
//                        msg.what = Constant.SCAN_NO_MUSIC;
//                        handler.sendMessage(msg);  //更新UI界面
//                    }
//                    if (cursor != null) {
//                        cursor.close();
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                    Log.e(TAG, "run: error = ",e );
//                    //扫描出错
//                    msg = new Message();
//                    msg.what = Constant.SCAN_ERROR;
//                    handler.sendMessage(msg);
//                }
//            }
//        }.start();
//    }

    private void show() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (playBarFragment == null) {
            playBarFragment = PlayBarFragment.newInstance();
            ft.add(R.id.fragment_playbar, playBarFragment).commit();
        } else {
            ft.show(playBarFragment).commit();
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        count = dbManager.getMusicCount(Constants.LIST_ALLMUSIC);
        localMusicCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constants.LIST_LASTPLAY);
        lastPlayCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constants.LIST_MYLOVE);
        myLoveCountTv.setText(count + "");
        count = dbManager.getMusicCount(Constants.LIST_MYPLAY);
        myPLCountTv.setText("(" + count + ")");
        adapter.updateDataList();
    }
}
