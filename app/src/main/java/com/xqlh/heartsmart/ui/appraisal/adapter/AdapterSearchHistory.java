package com.xqlh.heartsmart.ui.appraisal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;

import java.util.List;


public class AdapterSearchHistory extends BaseQuickAdapter<String, BaseViewHolder> {
    List<String> historyList;

    public AdapterSearchHistory(int layoutResId,List<String> data) {
        super(layoutResId,data);
        this.historyList = data;
    }

    public void addHistoryList(List<String> list) {
        int position = historyList.size();
        historyList.addAll(position, list);
        notifyItemInserted(position);
    }

    public void celarList(){
        historyList.clear();
    }

    @Override
    protected void convert(final BaseViewHolder baseViewHolder, final String record) {
        //点击历史中的某一个搜索
        baseViewHolder.setText(R.id.tv_record,record );
        baseViewHolder.addOnClickListener(R.id.tv_record);
    }
}
