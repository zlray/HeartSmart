package com.xqlh.heartsmart.ui.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.xqlh.heartsmart.bean.EntityUserCollect;
import com.xqlh.heartsmart.ui.home.ui.ArticleDetailActivity;
import com.xqlh.heartsmart.ui.mine.adapter.AdapterCollect;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.rv_collect)
    RecyclerView rv_collect;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    //每页的大小
    private int pageSize = 6;
    //当前是第几页
    private int mCurrentPage = 1;
    private String token;
    SharedPreferencesHelper sp;
    AdapterCollect adapterCollect;


    @Override
    public int setContent() {
        return R.layout.activity_collect;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        rv_collect.setLayoutManager(new LinearLayoutManager(this));


        initTtileBar();
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();
        getCollect(token, mCurrentPage, pageSize, "");
        initRefresh();

    }

    public void initTtileBar() {
        titleBar.setLeftImageResource(R.drawable.return_button);
        titleBar.setTitle("我的收藏");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getCollect(String token, int mCurrentPage, int pageSize, String ArticleTypeID) {
        RetrofitHelper.getApiService()
                .getCollect(token, mCurrentPage, pageSize, ArticleTypeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUserCollect>() {
                    @Override
                    public void onSuccess(final EntityUserCollect response) {
                        if (response.getCode() == 1) {
                            adapterCollect = new AdapterCollect(R.layout.item_rv_newest, CollectActivity.this, response.getResult());
                            rv_collect.setAdapter(adapterCollect);
                            adapterCollect.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(CollectActivity.this, ArticleDetailActivity.class);
                                    intent.putExtra("id", response.getResult().get(position).getID());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void initRefresh() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getCollect(token, 1, 6, "");
                refreshlayout.finishRefresh();
            }
        });

        //重置没有更多数据状态
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mCurrentPage++;
                getRefereshNewest(mCurrentPage, pageSize * mCurrentPage);
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    public void getRefereshNewest(int mCurrentPage, int pageSize) {
        RetrofitHelper.getApiService()
                .getCollect(token, mCurrentPage, pageSize, "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUserCollect>() {
                    @Override
                    public void onSuccess(final EntityUserCollect response) {
                        if (response.getCode() == 1) {
                            adapterCollect.addNewestList(response.getResult());
                            adapterCollect.notifyDataSetChanged();

                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
