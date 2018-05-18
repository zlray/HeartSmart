package com.xqlh.heartsmart.ui.home.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.ui.home.adapter.AdapterNewestArticle;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleCategoryActivity extends BaseActivity {

    @BindView(R.id.article_titlebar)
    TitleBar article_titlebar;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @BindView(R.id.rv_article_category)
    RecyclerView rv_article_category;

    @BindView(R.id.ib_top)
    ImageButton ib_top;

    //每页的大小
    private int PAGE_SIZE = 6;
    //当前是第几页
    private int mCurrentPage = 1;

    private AdapterNewestArticle adapterNewestArticle;

    private List<EntityArticleNewest.ResultBean> listArticle = new ArrayList<>(); //最新
    private String articleTypeID;
    private String title;


    @Override
    public int setContent() {
        return R.layout.activity_article_category;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        articleTypeID = intent.getStringExtra("ArticleTypeID");
        title = intent.getStringExtra("title");
        initTtileBar(title);
        initRv();
        initData(articleTypeID, mCurrentPage, PAGE_SIZE);
        initRefresh(articleTypeID);

    }

    public void initTtileBar(String title) {
        article_titlebar.setLeftImageResource(R.drawable.return_button);
        article_titlebar.setTitle(title);
        article_titlebar.setTitleColor(Color.WHITE);
        article_titlebar.setLeftTextColor(Color.WHITE);
        article_titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initRv() {

        rv_article_category.setLayoutManager(new LinearLayoutManager(this));

    }

    public void initData(String ArticleTypeID, int page, int PAGE_SIZE) {
        RetrofitHelper.getApiService()
                .getArticleQuery("", ArticleTypeID, new String[]{""}, page, PAGE_SIZE, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleNewest>() {
                    @Override
                    public void onSuccess(final EntityArticleNewest response) {
                        if (response.getCode() == 1) {

                            listArticle = response.getResult();
                            adapterNewestArticle = new AdapterNewestArticle(R.layout.item_rv_newest,
                                    ArticleCategoryActivity.this,
                                    listArticle);
                            rv_article_category.setAdapter(adapterNewestArticle);

                            adapterNewestArticle.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(ArticleCategoryActivity.this, ArticleDetailActivity.class);
                                    intent.putExtra("id", listArticle.get(position).getID());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }


    public void initRefresh(final String articleTypeID) {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData(articleTypeID, 1, 6);
                refreshlayout.finishRefresh();
            }
        });

        //重置没有更多数据状态
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mCurrentPage++;
                getRefereshNewest(articleTypeID, mCurrentPage, PAGE_SIZE * mCurrentPage);
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    public void getRefereshNewest(String articleTypeID, int page, int PAGE_SIZE) {
        RetrofitHelper.getApiService()
                .getArticleQuery("", articleTypeID, new String[]{""}, page, PAGE_SIZE, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleNewest>() {
                    @Override
                    public void onSuccess(final EntityArticleNewest response) {
                        if (response.getCode() == 1) {
                            adapterNewestArticle.addNewestList(response.getResult());
                            adapterNewestArticle.notifyDataSetChanged();
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
