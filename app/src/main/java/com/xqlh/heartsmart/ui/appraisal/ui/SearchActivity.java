package com.xqlh.heartsmart.ui.appraisal.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.Event.EventUpdateUserInfor;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntitySearchHistory;
import com.xqlh.heartsmart.sqliteHelp.MySqliteOpenHelper;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterSearchHistory;
import com.xqlh.heartsmart.utils.ContextUtils;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.bt_search)
    Button bt_search;

    @BindView(R.id.et_search)
    EditText et_search;

    @BindView(R.id.iv_delete)
    ImageView iv_delete;

    @BindView(R.id.rv_search_history)
    RecyclerView rv_search_history;

    private List<String> listHistory = new ArrayList<>();

    private AdapterSearchHistory adapterSearchHistory;

    private SQLiteDatabase db;

    MySqliteOpenHelper mySqliteOpenHelper;


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

        initData();
        quary();
    }

    public void initData() {
        //设置recuclerView的布局管理器
        GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 4);
        rv_search_history.setLayoutManager(manager);
    }

    @OnClick({R.id.iv_return, R.id.bt_search, R.id.iv_delete})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.bt_search:
                if (!TextUtils.isEmpty(et_search.getText().toString())) {
                    if (!hasData(et_search.getText().toString())) {
                        insert(et_search.getText().toString());
                        quary();
                    }
                } else {
                    Toasty.success(ContextUtils.getContext(), "请输入搜索内容", Toast.LENGTH_SHORT, true).show();
                }
                //网络搜索
                break;
            case R.id.iv_delete:
                Toasty.success(ContextUtils.getContext(), "清除成功", Toast.LENGTH_SHORT, true).show();
                db.execSQL("delete from records");
                if (adapterSearchHistory!=null){
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
                                    Toasty.success(ContextUtils.getContext(), "点击查询", Toast.LENGTH_SHORT, true).show();
                                    //网络搜索

                                    break;
                            }
                        }
                    });
                }
            }
            cursor.close();
        }
    }
}
