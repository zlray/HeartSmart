package com.xqlh.heartsmart.ui.home.ui;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityArticleBeautiful;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.ui.home.adapter.AdapterArticleHome;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;
import com.xqlh.heartsmart.utils.Utils;
import com.xqlh.heartsmart.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleHomeActivity extends BaseActivity {
    @BindView(R.id.article_titlebar)
    TitleBar article_titlebar;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @BindView(R.id.rv_article_home)
    RecyclerView rv_article_home;


    private AdapterArticleHome adapterArticleHome;
    private List<Uri> bannerList = new ArrayList<>();
    private List<IconTitleModel> eightList = new ArrayList<>();
    List<EntityArticleNewest.ResultBean> beautifulList = new ArrayList<>();
    List<EntityArticleNewest.ResultBean> newestList = new ArrayList<>();


    //每页的大小
    private int PAGE_SIZE = 6;
    //当前是第几页
    private int mCurrentPage = 1;
    //是否没有更多数据了
    private boolean isNoMoreData = false;


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

        adapterArticleHome = new AdapterArticleHome(this);


        getNewest(mCurrentPage, PAGE_SIZE);

        initData();

        getBeautifulArticle();

        initRv();


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

    public void initData() {
        //banner数据
        bannerList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
        bannerList.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.banner));
        adapterArticleHome.setBannerList(bannerList);
        //8个按钮
        eightList.add(new IconTitleModel(R.drawable.health, "成长"));
        eightList.add(new IconTitleModel(R.drawable.workplace, "职场"));
        eightList.add(new IconTitleModel(R.drawable.knowledge, "小知识"));
        eightList.add(new IconTitleModel(R.drawable.popular_science, "科普"));
        eightList.add(new IconTitleModel(R.drawable.sex_psychology, "性心理"));
        eightList.add(new IconTitleModel(R.drawable.beautiful_article, "经典美文"));
        eightList.add(new IconTitleModel(R.drawable.marriage, "婚恋"));
        eightList.add(new IconTitleModel(R.drawable.health, "健康"));

        adapterArticleHome.setEightList(eightList);

        rv_article_home.setAdapter(adapterArticleHome);

        RetrofitHelper.getApiService()
                .getArticleBeautiful("", "", new String[]{""}, 1, 6, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleBeautiful>() {
                    @Override
                    public void onSuccess(final EntityArticleBeautiful response) {
                        if (response.getCode() == 1) {
                            Log.i(TAG, "网络获取美文集合" + response.getResult().size());
                            adapterArticleHome.setBeautifulList(response.getResult());
                            rv_article_home.setAdapter(adapterArticleHome);

                        } else {
                            Toasty.warning(Utils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }


    public void initRv() {

        rv_article_home.setLayoutManager(new LinearLayoutManager(this));
    }


    public void getBeautifulArticle() {

    }

    public void getNewest(int page, int PAGE_SIZE) {
        RetrofitHelper.getApiService()
                .getArticleQuery("", "", new String[]{""}, 1, 6, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleNewest>() {
                    @Override
                    public void onSuccess(final EntityArticleNewest response) {
                        if (response.getCode() == 1) {
                            Log.i(TAG, "网络获取最新集合" + response.getResult().size());
                            adapterArticleHome.setNewestList(response.getResult());
                            rv_article_home.setAdapter(adapterArticleHome);
                        } else {
                            Toasty.warning(Utils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }


//    public void initRefresh() {
//        //重置没有更多数据状态
//        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
//                mCurrentPage++;
//                getNewest(mCurrentPage, PAGE_SIZE * mCurrentPage);
//                adapterNewestArticle.notifyDataSetChanged();
//                smartRefreshLayout.finishLoadMore();
//            }
//        });
//    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        //开始轮播
//        banner.startAutoPlay();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        //结束轮播
//        banner.stopAutoPlay();
//    }
}
