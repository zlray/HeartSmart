package com.xqlh.heartsmart.ui.home.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.widget.TitleBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;

public class ArticleHomeActivity extends BaseActivity {
    @BindView(R.id.article_titlebar)
    TitleBar article_titlebar;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.banner)
    Banner banner;

    @BindView(R.id.rv_article_newest)
    RecyclerView rv_article_newest;

    @BindView(R.id.rv_beautiful_article)
    RecyclerView rv_beautiful_article;


//    AdapterArticleSelection adapterArticleSelection;
//
//    AdapterArticle adapterArticle;
//
//    private List<Uri> mList = new ArrayList<>();

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
//        initRv();
//        getHotArticle();
//        getNewest();
//        //加载刷新数据
//        initRefresh();
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

        //设置banner的各种属性
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
//                .setImages(presenter.getBannerImages())
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();

//        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
//        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
//        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
//        mList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));

    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);

        }
    }
//
//    public void initRv() {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rv_article_selection.setLayoutManager(linearLayoutManager);
//        rv_article_newest.setLayoutManager(new LinearLayoutManager(this));
//    }
//
//    public void initRefresh() {
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
//                refreshLayout.getLayout().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        //加载数据
//                        refreshLayout.finishLoadMore();
//                    }
//                }, 2000);
//            }
//        });
//    }
//
//
//    public void getHotArticle() {
//        RetrofitHelper.getApiService()
//                .getArticleQuery("", "", new String[]{""}, 1, 6, 2)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserval<EntityArticleNewest>() {
//                    @Override
//                    public void onSuccess(final EntityArticleNewest response) {
//                        if (response.getCode() == 1) {
//                            adapterArticleSelection = new AdapterArticleSelection(ArticleHomeActivity.this, response.getResult(), new RvListener() {
//                                @Override
//                                public void onItemClick(int id, int position) {
//                                    Intent intent = new Intent(ArticleHomeActivity.this, ArticleDetailActivity.class);
//                                    intent.putExtra("id", response.getResult().get(position).getID());
//                                    startActivity(intent);
//                                }
//                            });
//                            rv_article_selection.setAdapter(adapterArticleSelection);
//                        } else {
//                            Toasty.warning(Utils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
//                        }
//                    }
//                });
//    }
//
//    public void getNewest() {
//        RetrofitHelper.getApiService()
//                .getArticleQuery("", "", new String[]{""}, 1, 6, 0)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserval<EntityArticleNewest>() {
//                    @Override
//                    public void onSuccess(final EntityArticleNewest response) {
//                        if (response.getCode() == 1) {
//                            adapterArticle = new AdapterArticle(ArticleHomeActivity.this, response.getResult(), new RvListener() {
//                                @Override
//                                public void onItemClick(int id, int position) {
//                                    Intent intent = new Intent(ArticleHomeActivity.this, ArticleDetailActivity.class);
//                                    intent.putExtra("id", response.getResult().get(position).getID());
//                                    startActivity(intent);
//                                }
//                            });
//                            rv_article_newest.setAdapter(adapterArticle);
//                        } else {
//                            Toasty.warning(Utils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
//                        }
//                    }
//                });
//    }
//

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
