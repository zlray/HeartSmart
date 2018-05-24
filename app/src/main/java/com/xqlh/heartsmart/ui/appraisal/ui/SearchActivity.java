package com.xqlh.heartsmart.ui.appraisal.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntitySearchHistory;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterSearchHistory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.rv_search_history)
    RecyclerView rv_search_history;

    private List<EntitySearchHistory> listHistory = new ArrayList<>();

    private AdapterSearchHistory adapterSearchHistory;

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
        initData();
    }

    public void initData() {
        EntitySearchHistory channel = new EntitySearchHistory();
        channel.setItemtype(EntitySearchHistory.TYPE_TEXT);
        channel.setChannelName("历史记录");
        listHistory.add(channel);

        EntitySearchHistory entitySearchHistory4 = new EntitySearchHistory();
        entitySearchHistory4.setItemtype(EntitySearchHistory.TYPE_HISTORY);
        entitySearchHistory4.setChannelName("哈哈哈");
        EntitySearchHistory entitySearchHistory5 = new EntitySearchHistory();
        entitySearchHistory5.setItemtype(EntitySearchHistory.TYPE_HISTORY);
        entitySearchHistory5.setChannelName("呵呵呵");
        EntitySearchHistory entitySearchHistory6 = new EntitySearchHistory();
        entitySearchHistory6.setItemtype(EntitySearchHistory.TYPE_HISTORY);
        entitySearchHistory6.setChannelName("喝喝合伙");

        listHistory.add(entitySearchHistory4);
        listHistory.add(entitySearchHistory5);
        listHistory.add(entitySearchHistory6);

        adapterSearchHistory = new AdapterSearchHistory(listHistory);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        rv_search_history.setLayoutManager(manager);
        rv_search_history.setAdapter(adapterSearchHistory);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapterSearchHistory.getItemViewType(position)) {
                    case EntitySearchHistory.TYPE_TEXT:
                        return 4;
                    case EntitySearchHistory.TYPE_HISTORY:
                        return 1;
                    default:
                        return 0;
                }

            }
        });
    }
}
