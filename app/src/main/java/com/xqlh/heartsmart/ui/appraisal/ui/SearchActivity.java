package com.xqlh.heartsmart.ui.appraisal.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntitySearchHistory;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterSearchHistory;
import com.xqlh.heartsmart.ui.appraisal.customInterface.ItemDragHelperCallBack;
import com.xqlh.heartsmart.ui.appraisal.customInterface.OnChannelListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements OnChannelListener {
    @BindView(R.id.rv_search_history)
    RecyclerView rv_search_history;
    private List<EntitySearchHistory> listHistory = new ArrayList<>();

    private AdapterSearchHistory adapterSearchHistory;

    private OnChannelListener onChannelListener;

    private boolean isUpdate = false;


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
        channel.setItemtype(EntitySearchHistory.TYPE_MY);
        channel.setChannelName("历史记录");
        listHistory.add(channel);

        EntitySearchHistory entitySearchHistory1 = new EntitySearchHistory();
        entitySearchHistory1.setChannelType(EntitySearchHistory.TYPE_MY_CHANNEL);
        entitySearchHistory1.setChannelName("哈哈哈");
        EntitySearchHistory entitySearchHistory2 = new EntitySearchHistory();
        entitySearchHistory2.setChannelType(EntitySearchHistory.TYPE_MY_CHANNEL);
        entitySearchHistory2.setChannelName("呵呵呵");
        EntitySearchHistory entitySearchHistory3 = new EntitySearchHistory();
        entitySearchHistory3.setChannelType(EntitySearchHistory.TYPE_MY_CHANNEL);
        entitySearchHistory3.setChannelName("喝喝合伙");

        listHistory.add(entitySearchHistory1);
        listHistory.add(entitySearchHistory2);
        listHistory.add(entitySearchHistory3);


        EntitySearchHistory morechannel = new EntitySearchHistory();
        morechannel.setItemtype(EntitySearchHistory.TYPE_OTHER);
        morechannel.setChannelName("推荐搜索");
        listHistory.add(morechannel);


        EntitySearchHistory entitySearchHistory4 = new EntitySearchHistory();
        entitySearchHistory4.setChannelType(EntitySearchHistory.TYPE_OTHER_CHANNEL);
        entitySearchHistory4.setChannelName("哈哈哈");
        EntitySearchHistory entitySearchHistory5 = new EntitySearchHistory();
        entitySearchHistory5.setChannelType(EntitySearchHistory.TYPE_OTHER_CHANNEL);
        entitySearchHistory5.setChannelName("呵呵呵");
        EntitySearchHistory entitySearchHistory6 = new EntitySearchHistory();
        entitySearchHistory6.setChannelType(EntitySearchHistory.TYPE_OTHER_CHANNEL);
        entitySearchHistory6.setChannelName("喝喝合伙");

        listHistory.add(entitySearchHistory4);
        listHistory.add(entitySearchHistory5);
        listHistory.add(entitySearchHistory6);


        ItemDragHelperCallBack callback = new ItemDragHelperCallBack(this);
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv_search_history);

        adapterSearchHistory = new AdapterSearchHistory(listHistory, helper);

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        rv_search_history.setLayoutManager(manager);
        rv_search_history.setAdapter(adapterSearchHistory);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapterSearchHistory.getItemViewType(position);
                return itemViewType == EntitySearchHistory.TYPE_MY_CHANNEL || itemViewType == EntitySearchHistory.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });
//        mHelper = new ItemTouchHelper(callBack);
        adapterSearchHistory.OnChannelListener(this);
    }

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        this.onChannelListener = onChannelListener;
    }

    private String firstAddChannelName = "";

    private void onMove(int starPos, int endPos, boolean isAdd) {
        isUpdate = true;
        EntitySearchHistory startChannel = listHistory.get(starPos);
        //先删除之前的位置
        listHistory.remove(starPos);
        //添加到现在的位置
        listHistory.add(endPos, startChannel);
        adapterSearchHistory.notifyItemMoved(starPos, endPos);
        if (isAdd) {
            if (TextUtils.isEmpty(firstAddChannelName)) {
                firstAddChannelName = startChannel.getChannelName();
            }
        } else {
            if (startChannel.getChannelName().equals(firstAddChannelName)) {
                firstAddChannelName = "";
            }
        }
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        if (starPos < 0 || endPos < 0) return;
        if (listHistory.get(endPos).getChannelName().equals("头条")) return;
        //我的频道之间移动
        if (onChannelListener != null)
            onChannelListener.onItemMove(starPos - 1, endPos - 1);//去除标题所占的一个index
        onMove(starPos, endPos, false);

    }

    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {

    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {

    }

    @Override
    public void onFinish(String selectedChannelName) {

    }
}
