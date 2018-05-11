package com.xqlh.heartsmart.ui.appraisal.ui;


import android.app.Fragment;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.bean.EntityAppraisalRecommend;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterAppraisalHome;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppraisalFragment extends BaseLazyFragment {


    @BindView(R.id.appraisal_titlebar)
    TitleBar appraisal_titlebar;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;


    @BindView(R.id.rv_appraisal_home)
    RecyclerView rv_appraisal_home;

    @BindView(R.id.ib_top)
    ImageButton ib_top;
    private List<Uri> bannerList = new ArrayList<>();
    AdapterAppraisalHome adapterAppraisalHome;

    private List<IconTitleModel> eightList = new ArrayList<>();


    @Override
    protected int setContentView() {
        return R.layout.fragment_appraisal;
    }

    @Override
    protected void init() {
        adapterAppraisalHome = new AdapterAppraisalHome(getActivity());
        initTtileBar();
        initRv();
    }

    @Override
    protected void lazyLoad() {
        initData();
        getRecommendAppraisal();
        getHotAppraisal();
    }

    public void initTtileBar() {
        appraisal_titlebar.setTitle("心理测评");
        appraisal_titlebar.setTitleColor(Color.WHITE);
    }

    public void initData() {
        //banner数据
        bannerList.clear();
        
        bannerList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));
        bannerList.add(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.banner));

        adapterAppraisalHome.setBannerList(bannerList);
        //8个按钮
        eightList.clear();

        eightList.add(new IconTitleModel(R.drawable.health, "学习素质", "1e6dbe0ea7c14b8c9098335f0eae3150"));
        eightList.add(new IconTitleModel(R.drawable.workplace, "职业指导", "79fc5ca675ac4ecca6948bbfd5544ad0"));
        eightList.add(new IconTitleModel(R.drawable.knowledge, "人际交往", "7b826408f2fe4b3d846f0818e28592f3"));
        eightList.add(new IconTitleModel(R.drawable.popular_science, "个性人格", "9e3af7070e22404fa87afb5ba1418d02"));
        eightList.add(new IconTitleModel(R.drawable.sex_psychology, "婚恋家庭", "a193fd29ca8a41f3b7f5d32bc572dc57"));
        eightList.add(new IconTitleModel(R.drawable.beautiful_article, "智力能力", "cbfa55abae374d2587633b84e45a44e5"));
        eightList.add(new IconTitleModel(R.drawable.marriage, "心理健康", "fedcfb5f986c439cacc84a4f93907e5b"));
        eightList.add(new IconTitleModel(R.drawable.health, "情绪测评", "d5984b6874a440f2875f1e43e419e927"));

        adapterAppraisalHome.setEightList(eightList);
        adapterAppraisalHome.notifyDataSetChanged();

    }

    public void initRv() {
        GridLayoutManager GridLayoutManager = new GridLayoutManager(getActivity(), 1);
        GridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= 9) {
                    ib_top.setVisibility(View.GONE);
                } else {
                    ib_top.setVisibility(View.VISIBLE);
                }
                return 1;//只能返回1
            }
        });
        rv_appraisal_home.setLayoutManager(GridLayoutManager);
    }

    @OnClick({R.id.ib_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_top:
                rv_appraisal_home.scrollToPosition(0);
                break;
        }
    }

    public void getHotAppraisal() {
        RetrofitHelper.getApiService()
                .getAppraisalHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityAppraisalRecommend>() {
                    @Override
                    public void onSuccess(final EntityAppraisalRecommend response) {
                        if (response.getCode() == 1) {
                            adapterAppraisalHome.setHotList(response.getResult());

                            adapterAppraisalHome.notifyDataSetChanged();

                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }

    public void getRecommendAppraisal() {
        RetrofitHelper.getApiService()
                .getAppraisalRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityAppraisalRecommend>() {
                    @Override
                    public void onSuccess(final EntityAppraisalRecommend response) {
                        if (response.getCode() == 1) {

                            adapterAppraisalHome.setRecommendList(response.getResult());
                            rv_appraisal_home.setAdapter(adapterAppraisalHome);

                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }

}
