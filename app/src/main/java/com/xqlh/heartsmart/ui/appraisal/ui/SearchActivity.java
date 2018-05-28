package com.xqlh.heartsmart.ui.appraisal.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.xqlh.heartsmart.bean.EntityLogin;
import com.xqlh.heartsmart.bean.EntitySearchHistory;
import com.xqlh.heartsmart.sqliteHelp.MySqliteOpenHelper;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterSearchApprisal;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterSearchHistory;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.bt_search)
    Button bt_search;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.iv_delete)
    ImageView iv_delete;

    @BindView(R.id.rv_search_history)
    RecyclerView rv_search_history;

    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.rv_appraisal_category)
    RecyclerView rv_appraisal_categorys;

    @BindView(R.id.rl_text)
    RelativeLayout rl_text;

    private List<String> listHistory = new ArrayList<>();

    private AdapterSearchHistory adapterSearchHistory;

    private SQLiteDatabase db;

    MySqliteOpenHelper mySqliteOpenHelper;

    SharedPreferencesHelper sp;
    String token;

    AdapterSearchApprisal adapterSearchApprisal;

    //每页的大小
    private int pageSize = 6;
    //当前是第几页
    private int mCurrentPage = 1;

    private String keyword;


    @Override
    public int setContent() {
        return R.layout.activity_search;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        mySqliteOpenHelper = new MySqliteOpenHelper(getApplicationContext());

        db = mySqliteOpenHelper.getWritableDatabase();

        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();

        rv_appraisal_categorys.setLayoutManager(new LinearLayoutManager(this));

        initData();
        quary();
        initRefresh();
        et_search.addTextChangedListener(textWatcher);

    }

    public void initData() {
        //设置recuclerView的布局管理器
        GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 4);
        rv_search_history.setLayoutManager(manager);
        rv_appraisal_categorys.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick({R.id.iv_return, R.id.bt_search, R.id.iv_delete})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.bt_search:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                keyword = et_search.getText().toString();
                sp.put(Constants.KEYWORD, keyword);

                if (!TextUtils.isEmpty(et_search.getText().toString())) {
                    if (!hasData(et_search.getText().toString())) {
                        insert(et_search.getText().toString());
                        quary();
                        search(keyword, mCurrentPage, pageSize);
                    } else {
                        search(keyword, mCurrentPage, pageSize);
                    }
                } else {
                    Toasty.success(ContextUtils.getContext(), "请输入搜索内容", Toast.LENGTH_SHORT, true).show();
                }
                //网络搜索
                break;
            case R.id.iv_delete:
                Toasty.success(ContextUtils.getContext(), "清除成功", Toast.LENGTH_SHORT, true).show();
                db.execSQL("delete from records");
                if (adapterSearchHistory != null) {
                    adapterSearchHistory.celarList();
                    adapterSearchHistory.notifyDataSetChanged();
                }
                break;
        }
    }

    public void insert(String record) {
        //存入数据库
        db.execSQL("insert into records(record) values('" + record + "')");
    }

    private boolean hasData(String record) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = db.rawQuery(
                "select record from records where record = ?", new String[]{record});
        return cursor.moveToNext();
    }

    public void quary() {
        //
        listHistory.clear();
        Cursor cursor = db.rawQuery("select record from records ORDER BY _id  DESC", null);
        if (cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                if (cursor.getCount() != 0) {
                    //添加数据
                    listHistory.add(cursor.getString(cursor.getColumnIndex("record")));
                    //实例化适配器
                    adapterSearchHistory = new AdapterSearchHistory(R.layout.item_rv_record, listHistory);
                    //设置适配器
                    rv_search_history.setAdapter(adapterSearchHistory);
                    //设置点击事件
                    adapterSearchHistory.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            switch (view.getId()) {
                                case R.id.tv_record:
                                    Log.i(TAG, "onItemChildClick: " + "点击测试记录");
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                                    et_search.setText(listHistory.get(position));
                                    et_search.setSelection(listHistory.get(position).length());

                                    search(listHistory.get(position), 1, 6);
                                    break;
                            }
                        }
                    });
                }
            }
            cursor.close();
        }
    }

    public void search(String keyword, int page, int pageSize) {
        RetrofitHelper.getApiService()
                .searchAprraisal(token, keyword, "", 0, 0, page, pageSize, 1)
                .subscribeOn(Schedulers.io())
                .compose(ProgressUtils.<EntitySearchHistory>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntitySearchHistory>() {
                    @Override
                    public void onSuccess(final EntitySearchHistory response) {
                        if (response.getCode() == 1) {
                            if (response.getResult().size() > 0) {
                                rl_text.setVisibility(View.GONE);
                                rv_search_history.setVisibility(View.GONE);
                                smartRefreshLayout.setVisibility(View.VISIBLE);

                                adapterSearchApprisal = new AdapterSearchApprisal(R.layout.item_rv_recommend,
                                        SearchActivity.this,
                                        response.getResult());
                                adapterSearchApprisal.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent(SearchActivity.this, AppraisalIntroduceActivity.class);
                                        intent.putExtra("PsyID", response.getResult().get(position).getID().trim());//id
                                        startActivity(intent);
                                    }
                                });
                                rv_appraisal_categorys.setAdapter(adapterSearchApprisal);
                            } else {
                                Toasty.warning(ContextUtils.getContext(), "未查询到", Toast.LENGTH_SHORT, true).show();
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(et_search.getText().toString().trim())) {
                //显示搜索历史
                rl_text.setVisibility(View.VISIBLE);
                rv_search_history.setVisibility(View.VISIBLE);

                smartRefreshLayout.setVisibility(View.GONE);
            }
        }
    };

    public void initRefresh() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                search(sp.getSharedPreference(Constants.KEYWORD,"").toString(),
                        1,
                        6);
                refreshlayout.finishRefresh();
            }
        });

        //重置没有更多数据状态
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                mCurrentPage++;
                getRefereshSearch(mCurrentPage, pageSize * mCurrentPage);
                smartRefreshLayout.finishLoadMore();
            }
        });
    }

    public void getRefereshSearch(int page, int pageSize) {
        Log.i(TAG, "getRefereshSearch:" + sp.getSharedPreference(Constants.KEYWORD, "").toString());
        RetrofitHelper.getApiService()
                .searchAprraisal(token, sp.getSharedPreference(Constants.KEYWORD, "").toString(), "", 0, 0, page, pageSize, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntitySearchHistory>() {
                    @Override
                    public void onSuccess(final EntitySearchHistory response) {
                        if (response.getCode() == 1) {
                            adapterSearchApprisal.addRecordList(response.getResult());
                            adapterSearchApprisal.notifyDataSetChanged();
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
