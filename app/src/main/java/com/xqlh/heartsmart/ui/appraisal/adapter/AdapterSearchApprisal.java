package com.xqlh.heartsmart.ui.appraisal.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntitySearchHistory;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterSearchApprisal extends BaseQuickAdapter<EntitySearchHistory.ResultBean, BaseViewHolder> {

    private Context mContext;

    List<EntitySearchHistory.ResultBean> listSearch;

    public AdapterSearchApprisal(int layoutResId, Context mContext, List<EntitySearchHistory.ResultBean> list) {
        super(layoutResId, list);
        this.mContext = mContext;
        this.listSearch = list;
    }

    public void addRecordList(List<EntitySearchHistory.ResultBean> list) {
        //增加数据
        int position = listSearch.size();
        listSearch.addAll(position, list);
        notifyItemInserted(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, EntitySearchHistory.ResultBean item) {
        helper.setText(R.id.tv_appraisal_title, item.getName())
                .setText(R.id.tv_appraisal_introduction, item.getTestAbstract())
                .setText(R.id.tv_appraisal_test_number, item.getTestMans() + "人测评")
                .setText(R.id.tv_appraisal_price, "￥" + item.getPrice());

        Glide.with(mContext).load(item.getPsychtestPic())
                .into((ImageView) helper.getView(R.id.iv_appraisal));
    }
}
