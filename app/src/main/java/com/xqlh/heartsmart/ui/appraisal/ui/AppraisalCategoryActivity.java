package com.xqlh.heartsmart.ui.appraisal.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityAppraisalCategory;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterCategoryApprisal;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppraisalCategoryActivity extends BaseActivity {
    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @BindView(R.id.rv_appraisal_category)
    RecyclerView rv_appraisal_category;

    private String title;
    private String psychtestTypeID;

    AdapterCategoryApprisal adapterCategoryApprisal;

    @Override
    public int setContent() {
        return R.layout.activity_appraisal_category;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        psychtestTypeID = intent.getStringExtra("PsychtestTypeID");
        title = intent.getStringExtra("title");

        initTtileBar(title);

        rv_appraisal_category.setLayoutManager(new LinearLayoutManager(this));

        initData(psychtestTypeID);
    }

    public void initTtileBar(String title) {
        titlebar.setLeftImageResource(R.drawable.return_button);
        titlebar.setTitle(title);
        titlebar.setTitleColor(Color.WHITE);
        titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //
    public void initData(String psychtestTypeID) {
        RetrofitHelper.getApiService()
                .getAppraisalCategory(psychtestTypeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityAppraisalCategory>() {
                    @Override
                    public void onSuccess(final EntityAppraisalCategory response) {

                        Log.i(TAG, "内容" + response.getMsg() + response.getCode());

                        if (response.getCode() == 1) {

                            adapterCategoryApprisal = new AdapterCategoryApprisal(R.layout.item_rv_recommend,
                                    AppraisalCategoryActivity.this,
                                    response.getResult());

                            adapterCategoryApprisal.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(AppraisalCategoryActivity.this, AppraisalIntroduceActivity.class);
                                    intent.putExtra("PsyID", response.getResult().get(position).getID().trim());//id
                                    startActivity(intent);
                                }
                            });
                            rv_appraisal_category.setAdapter(adapterCategoryApprisal);
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }


}
