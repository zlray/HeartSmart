package com.xqlh.heartsmart.ui.article;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.widget.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ArticleHomeActivity extends BaseActivity {
    @BindView(R.id.article_titlebar)
    TitleBar article_titlebar;
    @BindView(R.id.banner)
    Banner banner;
    private List<Uri> mList = new ArrayList<>();

    @Override
    public int setContent() {
        return R.layout.activity_article_home;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        initTtileBar();
        initBanner();

    }

    public void initTtileBar() {
        article_titlebar.setLeftImageResource(R.drawable.return_button);
        article_titlebar.setTitle("心理文章");
        article_titlebar.setTitleColor(Color.WHITE);
        article_titlebar.setLeftTextColor(Color.WHITE);
        article_titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initBanner() {

        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));

        //设置图片集合 18245096128
        banner.setImages(mList);

        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());

        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);

        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);

        //设置轮播时间
        banner.setDelayTime(1500);

        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }
}
