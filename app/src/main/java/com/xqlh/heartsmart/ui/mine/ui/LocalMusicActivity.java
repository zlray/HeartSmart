package com.xqlh.heartsmart.ui.mine.ui;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.PlayBarBaseActivity;
import com.xqlh.heartsmart.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

public class LocalMusicActivity extends PlayBarBaseActivity {

    private TabLayout tabLayout;
    private MyViewPager viewPager;
    private MyAdapter fragmentAdapter;
    private List<String> titleList = new ArrayList<>(4);
    private List<Fragment> fragments = new ArrayList<>(4);
    private SingleFragment singleFragment;
    private SingerFragment singerFragment;
    private AlbumFragment albumFragment;
    private FolderFragment folderFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        init();
    }

    private void init(){
        addTapData();
        viewPager = (MyViewPager)findViewById(R.id.local_viewPager);
        tabLayout = (TabLayout)findViewById(R.id.local_tab);
        fragmentAdapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2); //预加载页面数
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
    }


    //滑动布局
    private void addTapData() {
        titleList.add("单曲");
        titleList.add("歌手");
        titleList.add("专辑");
        titleList.add("文件夹");

        if (singleFragment == null) {
            singleFragment = new SingleFragment();
            fragments.add(singleFragment);
        }
        if (singerFragment == null) {
            singerFragment = new SingerFragment();
            fragments.add(singerFragment);
        }
        if (albumFragment == null) {
            albumFragment = new AlbumFragment();
            fragments.add(albumFragment);
        }
        if (folderFragment == null) {
            folderFragment = new FolderFragment();
            fragments.add(folderFragment);
        }
    }
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        /**
         * 用来显示tab上的名字
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
