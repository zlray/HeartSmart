package com.xqlh.heartsmart.ui.home.ui;


import android.app.Fragment;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.bean.EntityUserReport;
import com.xqlh.heartsmart.ui.home.adapter.AdapterHome;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseLazyFragment {

    @BindView(R.id.rv_home)
    RecyclerView rv_home;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    AdapterHome adapterHome;

    //每页的大小
    private int PAGE_SIZE = 6;
    //当前是第几页
    private int mCurrentPage = 1;

    private List<Uri> bannerList = new ArrayList<>();
    private List<IconTitleModel> eightList = new ArrayList<>();
    SharedPreferencesHelper sp;
    private String token;


    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        adapterHome = new AdapterHome(getActivity());
        initRv();
    }

    @Override
    protected void lazyLoad() {
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();
        getNewest(mCurrentPage, PAGE_SIZE);
        initData();
        getReport(token);
        initRefresh();
    }


    public void initData() {
        //banner数据
        bannerList.clear();
        bannerList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));
        bannerList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));
        adapterHome.setBannerList(bannerList);
        //8个按钮
        eightList.clear();

        eightList.add(new IconTitleModel(R.drawable.psychological_test, "心理检测", "153dd1e6ebab4279931875d654ddc001"));
        eightList.add(new IconTitleModel(R.drawable.evaluation_archives, "音乐放松", "2e8d670d44b9440282aa816b51a6a779"));
        eightList.add(new IconTitleModel(R.drawable.music, "体感音乐", "b60f031fecd9422f92faacc4f649994b"));
        eightList.add(new IconTitleModel(R.drawable.hug, "认知拥抱", "1"));
        eightList.add(new IconTitleModel(R.drawable.article, "心理文章", "850e7186768347daa7380627ca4fbc58"));
        eightList.add(new IconTitleModel(R.drawable.self_confidence, "自信心", "450bd9dfe8f549a8ba5d027ee85c4891"));
        eightList.add(new IconTitleModel(R.drawable.whoop, "呐喊宣泄", "c8e738b521bd47d794590f443cce1351"));
        eightList.add(new IconTitleModel(R.drawable.beat, "击打宣泄", "adbfad8a12564b87bbf502900a09ffc7"));

        adapterHome.setEightList(eightList);
        //
        adapterHome.notifyDataSetChanged();
    }

    public void initRv() {
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public void getReport(String token) {
        RetrofitHelper.getApiService()
                .getUserReport(token, 1, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUserReport>() {
                    @Override
                    public void onSuccess(final EntityUserReport response) {
                        if (response.getCode() == 1) {
                            adapterHome.setReportList(response.getResult());
                            rv_home.setAdapter(adapterHome);
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });

    }

    public void getNewest(int page, int PAGE_SIZE) {
        RetrofitHelper.getApiService()
                .getArticleQuery("", "", new String[]{""}, page, PAGE_SIZE, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleNewest>() {
                    @Override
                    public void onSuccess(final EntityArticleNewest response) {
                        if (response.getCode() == 1) {
                            adapterHome.setNewestList(response.getResult());
                            rv_home.setAdapter(adapterHome);
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
                getRefereshNewest(mCurrentPage, PAGE_SIZE * mCurrentPage);

                getNewest(1, 6);
                adapterHome.notifyDataSetChanged();
                refreshlayout.finishRefresh();
            }
        });

        //重置没有更多数据状态
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mCurrentPage++;
                getRefereshNewest(mCurrentPage, PAGE_SIZE * mCurrentPage);
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    public void getRefereshNewest(int page, int PAGE_SIZE) {
        RetrofitHelper.getApiService()
                .getArticleQuery("", "", new String[]{""}, page, PAGE_SIZE, 3)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityArticleNewest>() {
                    @Override
                    public void onSuccess(final EntityArticleNewest response) {
                        if (response.getCode() == 1) {
                            adapterHome.addNewestList(response.getResult());
                            adapterHome.notifyDataSetChanged();
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }


}
