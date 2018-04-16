package com.xqlh.heartsmart.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.ui.article.ArticleHomeActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseLazyFragment {

    @BindView(R.id.ib_article)
    ImageButton ib_article;
    @BindView(R.id.banner)
    Banner banner;
    private List<Uri> mList = new ArrayList<>();
    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        initBanner();

    }

    @Override
    protected void lazyLoad() {

    }


    @OnClick({R.id.ib_article})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ib_article:
                startActivity(new Intent(getActivity(), ArticleHomeActivity.class));
                break;
        }
    }
    public void initBanner() {

        mList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));
        mList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));
        mList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));
        mList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));

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

}
