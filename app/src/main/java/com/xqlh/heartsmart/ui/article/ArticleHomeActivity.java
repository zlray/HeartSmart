package com.xqlh.heartsmart.ui.article;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.base.RvListener;
import com.xqlh.heartsmart.ui.article.adapter.AdapterArticle;
import com.xqlh.heartsmart.ui.article.adapter.AdapterArticleSelection;
import com.xqlh.heartsmart.ui.bean.EntityArticleNewest;
import com.xqlh.heartsmart.utils.Utils;
import com.xqlh.heartsmart.widget.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleHomeActivity extends BaseActivity {
    @BindView(R.id.article_titlebar)
    TitleBar article_titlebar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_article_selection)
    RecyclerView rv_article_selection;

    @BindView(R.id.rv_article_newest)
    RecyclerView rv_article_newest;

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;


    AdapterArticleSelection adapterArticleSelection;

    AdapterArticle adapterArticle;

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
        initRv();
        getHotArticle();
        getNewest();
        //加载刷新数据
        initRefresh();
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

    public void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_article_selection.setLayoutManager(linearLayoutManager);
        rv_article_newest.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initRefresh() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //加载数据
                        refreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });
    }


    public void getHotArticle() {
        RetrofitHelper.getApiService()
                .getArticleQuery("", "", new String[]{""}, 1, 6, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleNewest>() {
                    @Override
                    public void onSuccess(final EntityArticleNewest response) {
                        if (response.getCode() == 1) {
                            adapterArticleSelection = new AdapterArticleSelection(ArticleHomeActivity.this, response.getResult(), new RvListener() {
                                @Override
                                public void onItemClick(int id, int position) {
                                    Intent intent = new Intent(ArticleHomeActivity.this, ArticleDetailActivity.class);
                                    intent.putExtra("id", response.getResult().get(position).getID());
                                    startActivity(intent);
                                }
                            });
                            rv_article_selection.setAdapter(adapterArticleSelection);
                        } else {
                            Toasty.warning(Utils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void getNewest() {
        RetrofitHelper.getApiService()
                .getArticleQuery("", "", new String[]{""}, 1, 6, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleNewest>() {
                    @Override
                    public void onSuccess(final EntityArticleNewest response) {
                        if (response.getCode() == 1) {
                            adapterArticle = new AdapterArticle(ArticleHomeActivity.this, response.getResult(), new RvListener() {
                                @Override
                                public void onItemClick(int id, int position) {
                                    Intent intent = new Intent(ArticleHomeActivity.this, ArticleDetailActivity.class);
                                    intent.putExtra("id", response.getResult().get(position).getID());
                                    startActivity(intent);
                                }
                            });
                            rv_article_newest.setAdapter(adapterArticle);
                        } else {
                            Toasty.warning(Utils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
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
