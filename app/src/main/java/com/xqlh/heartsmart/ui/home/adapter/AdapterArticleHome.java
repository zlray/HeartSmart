package com.xqlh.heartsmart.ui.home.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */

public class AdapterArticleHome
        extends BaseMultiItemQuickAdapter<MultipleArticleHomeEntity, BaseViewHolder>
        implements BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener {

    public AdapterArticleHome(Context context, List<MultipleArticleHomeEntity> data) {
        super(data);
        addItemType(MultipleArticleHomeEntity.BANNER, R.layout.item_article_home_banner_layout);
        addItemType(MultipleArticleHomeEntity.BUTTON, R.layout.item_article_home_button_layout);

    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleArticleHomeEntity item) {
        switch (helper.getItemViewType()){
        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
