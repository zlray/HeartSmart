package com.xqlh.heartsmart.ui.home.ui;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.widget.CustomViewPager;

import butterknife.BindView;

public class ArticleChannelActivity extends BaseActivity {

    @BindView(R.id.viewpager)
    CustomViewPager mViewpager;

    @BindView(R.id.SlidingTabLayout)
    com.flyco.tablayout.SlidingTabLayout SlidingTabLayout;

    @Override
    public int setContent() {
        return R.layout.activity_article_channel;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {

    }
}
