package com.xqlh.heartsmart.ui.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.ui.home.model.IconTitleModel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AdapterEightButton extends BaseQuickAdapter<IconTitleModel, BaseViewHolder> {

    private List<IconTitleModel> list;

    public AdapterEightButton(int layoutResId, @Nullable List<IconTitleModel> data) {
        super(layoutResId, data);
        this.list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, IconTitleModel item) {

        helper.setImageResource(R.id.iv_icon_title,item.getIconResource());


        helper.setText(R.id.tv_icon_title,item.getTitle());

    }
}
