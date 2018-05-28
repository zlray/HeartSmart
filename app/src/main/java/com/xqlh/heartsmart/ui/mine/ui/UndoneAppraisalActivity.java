package com.xqlh.heartsmart.ui.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.xqlh.heartsmart.bean.EntityUndoneAppraisal;
import com.xqlh.heartsmart.ui.appraisal.ui.AppraisalInstructionActivity;
import com.xqlh.heartsmart.ui.home.adapter.AdapterAppraisalUndone;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UndoneAppraisalActivity extends BaseActivity {

    @BindView(R.id.rv_appraisal_undone)
    RecyclerView rv_appraisal_undone;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;

    //每页的大小
    private int pageSize = 6;
    //当前是第几页
    private int mCurrentPage = 1;
    private String token;
    SharedPreferencesHelper sp;
    AdapterAppraisalUndone adapterAppraisalUndone;


    @Override
    public int setContent() {
        return R.layout.activity_undone_appraisal;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        rv_appraisal_undone.setLayoutManager(new LinearLayoutManager(this));
        initTtileBar();
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();
        getUndon(token, mCurrentPage, pageSize);
        initRefresh();
    }

    public void initTtileBar() {
        titlebar.setLeftImageResource(R.drawable.return_button);
        titlebar.setTitle("未完成测评");
        titlebar.setTitleColor(Color.WHITE);
        titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getUndon(String token, int mCurrentPage, int pageSize) {
        RetrofitHelper.getApiService()
                .getAppraisalUndone(token, mCurrentPage, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUndoneAppraisal>() {
                    @Override
                    public void onSuccess(final EntityUndoneAppraisal response) {
                        if (response.getCode() == 1) {
                            if (response.getResult().size() > 0) {
                                adapterAppraisalUndone = new AdapterAppraisalUndone(
                                        R.layout.item_rv_appraisal_undone,
                                        response.getResult());
                                rv_appraisal_undone.setAdapter(adapterAppraisalUndone);
                                adapterAppraisalUndone.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(UndoneAppraisalActivity.this, AppraisalInstructionActivity.class);
                                        //传递测评id
                                        intent.putExtra("PsyID", response.getResult().get(position).getPsyID());
                                        intent.putExtra("TestRecordId", response.getResult().get(position).getID());
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                smartRefreshLayout.setVisibility(View.GONE);
                                rl_empty.setVisibility(View.VISIBLE);
                            }

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
                getUndon(token, 1, 6);
                refreshlayout.finishRefresh();
            }
        });

        //重置没有更多数据状态
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mCurrentPage++;
                getRefereshUndon(token, mCurrentPage, pageSize * mCurrentPage);
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    public void getRefereshUndon(String token, int mCurrentPage, int pageSize) {
        RetrofitHelper.getApiService()
                .getAppraisalUndone(token, mCurrentPage, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUndoneAppraisal>() {
                    @Override
                    public void onSuccess(final EntityUndoneAppraisal response) {
                        if (response.getCode() == 1) {
                            adapterAppraisalUndone.addUndoneList(response.getResult());
                            adapterAppraisalUndone.notifyDataSetChanged();
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
