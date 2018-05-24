package com.xqlh.heartsmart.ui.appraisal.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntitySearchHistory;

import java.util.List;


public class AdapterSearchHistory extends BaseMultiItemQuickAdapter<EntitySearchHistory, BaseViewHolder> {
    public AdapterSearchHistory(List<EntitySearchHistory> data) {
        super(data);
        addItemType(EntitySearchHistory.TYPE_TEXT, R.layout.item_channel_title);
        addItemType(EntitySearchHistory.TYPE_HISTORY, R.layout.channel_rv_item);
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final EntitySearchHistory channel) {
        switch (baseViewHolder.getItemViewType()) {
            case EntitySearchHistory.TYPE_TEXT:
                //我的历史
                baseViewHolder.setText(R.id.tvTitle, channel.getChannelName());
                //点击编辑
                baseViewHolder.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //清空历史

                    }
                });
                break;
            case EntitySearchHistory.TYPE_HISTORY:
                //点击搜索
                baseViewHolder.setText(R.id.tv_channelname, channel.getChannelName());
                baseViewHolder.getView(R.id.tv_channelname).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                    }
                });
                break;
        }
    }
}
