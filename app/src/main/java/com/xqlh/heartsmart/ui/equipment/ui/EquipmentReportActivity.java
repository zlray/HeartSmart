package com.xqlh.heartsmart.ui.equipment.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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
import com.xqlh.heartsmart.bean.EntityEquipmentReport;
import com.xqlh.heartsmart.ui.equipment.adapter.AdapterEquipmentReport;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EquipmentReportActivity extends BaseActivity {
    private String id;
    private String title;
    @BindView(R.id.rv_equipment_report)
    RecyclerView rv_equipment_report;

    @BindView(R.id.equipment_report_titlebar)
    TitleBar equipment_report_titlebar;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_empty)
    TextView tv_empty;

    private SharedPreferencesHelper sp_login_token;
    private String token;

    //每页的大小
    private int pageSize = 6;
    //当前是第几页
    private int page = 1;
    private AdapterEquipmentReport adapterEquipmentReport;

    @Override
    public int setContent() {
        return R.layout.activity_equipment_report;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");

        sp_login_token = new SharedPreferencesHelper(
                this, Constants.CHECKINFOR);

        token = sp_login_token.getSharedPreference(Constants.LOGIN_TOKEN, "").toString().trim();

        Log.i(TAG, "设备的id" + id);
        Log.i(TAG, "设备的title" + title);
        Log.i(TAG, "用户登录的token" + token);
        initTtileBar();
        initRv();

        initData(token, id, page, pageSize);

        initRefresh(id);
    }

    public void initTtileBar() {
        equipment_report_titlebar.setTitle(title);
        equipment_report_titlebar.setTitleColor(Color.WHITE);
        equipment_report_titlebar.setLeftImageResource(R.drawable.return_button);
        equipment_report_titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initRv() {
        rv_equipment_report.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initData(String token, String id, final int page, int pageSize) {
        RetrofitHelper.getApiService()
                .getReport(token, id, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityEquipmentReport>() {
                    @Override
                    public void onSuccess(final EntityEquipmentReport response) {
                        if (response.getCode() == 1) {
                            if (response.getResult().size() > 0) {

                                adapterEquipmentReport = new AdapterEquipmentReport(R.layout.item_equipment_report, response.getResult());

                                rv_equipment_report.setAdapter(adapterEquipmentReport);

                                adapterEquipmentReport.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(EquipmentReportActivity.this, EquipmentReportDetailActivity.class);
                                        intent.putExtra("name", response.getResult().get(position).getName());
                                        intent.putExtra("id", response.getResult().get(position).getID());
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                tv_empty.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void initRefresh(final String id) {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initData(token, id, page, pageSize);
                refreshlayout.finishRefresh();
            }
        });

        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                page++;
                getRefereshNewest(token, id, page, pageSize * page);
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    public void getRefereshNewest(String token, final String id, final int page, final int pageSize) {
        RetrofitHelper.getApiService()
                .getReport(token, id, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityEquipmentReport>() {
                    @Override
                    public void onSuccess(final EntityEquipmentReport response) {
                        if (response.getCode() == 1) {
                            adapterEquipmentReport.addEquipmentReportList(response.getResult());
                            adapterEquipmentReport.notifyDataSetChanged();
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
