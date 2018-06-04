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
import com.xqlh.heartsmart.bean.EntityUserReport;
import com.xqlh.heartsmart.ui.mine.adapter.AdapterUserReport;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserReportCategoryActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @BindView(R.id.rv_report_category)
    RecyclerView rv_report_category;

    //每页的大小
    private int pageSize = 6;
    //当前是第几页
    private int page = 1;

    SharedPreferencesHelper sp;
    private String token;
    AdapterUserReport adapterUserReport;

    @Override
    public int setContent() {
        return R.layout.activity_user_report;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);

        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();

        rv_report_category.setLayoutManager(new LinearLayoutManager(this));

        getReport(token, page, pageSize);
        initRefresh(token);
        initTtileBar();
    }

    public void initTtileBar() {
        titlebar.setLeftImageResource(R.drawable.return_button);
        titlebar.setTitle("我的报告");
        titlebar.setTitleColor(Color.WHITE);
        titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getReport(String token, int page, int pageSize) {
        RetrofitHelper.getApiService()
                .getUserReport(token, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUserReport>() {
                    @Override
                    public void onSuccess(final EntityUserReport response) {
                        if (response.getCode() == 1) {
                            adapterUserReport = new AdapterUserReport(R.layout.item_rv_report,
                                    UserReportCategoryActivity.this,
                                    response.getResult());

                            rv_report_category.setAdapter(adapterUserReport);

                            adapterUserReport.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(UserReportCategoryActivity.this, AppraisalUserReportActivity.class);
                                    intent.putExtra("reportId",response.getResult().get(position).getID());
                                    startActivity(intent);
                                }
                            });
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void initRefresh(final String token) {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                getReport(token, 1, 6);

                refreshlayout.finishRefresh();
            }
        });

        //重置没有更多数据状态
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                page++;
                getRefereshNewest(token, page, pageSize * page);
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    public void getRefereshNewest(String token, int page, int pageSize) {
        RetrofitHelper.getApiService()
                .getUserReport(token, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUserReport>() {
                    @Override
                    public void onSuccess(final EntityUserReport response) {
                        if (response.getCode() == 1) {
                            adapterUserReport.addUserReport(response.getResult());
                            adapterUserReport.notifyDataSetChanged();
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
