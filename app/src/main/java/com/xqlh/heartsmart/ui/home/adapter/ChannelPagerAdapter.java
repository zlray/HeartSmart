//package com.xqlh.heartsmart.ui.home.adapter;
//
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2018/4/24.
// */
//
//public class ChannelPagerAdapter extends FragmentStatePagerAdapter {
//
//    private List<Channel> mChannels;
//
//    public ChannelPagerAdapter(FragmentManager fm, List<Channel> channels) {
//        super(fm);
//        this.mChannels = channels;
//    }
//
//    public void updateChannel(List<Channel> channels){
//        this.mChannels = channels;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public BaseFragment getItem(int position) {
//        return DetailFragment.newInstance(mChannels.get(position).getChannelId(), position);
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mChannels.get(position).getChannelName();
//    }
//
//    @Override
//    public int getCount() {
//        return mChannels != null ? mChannels.size() : 0;
//    }
//
//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }
//
//}