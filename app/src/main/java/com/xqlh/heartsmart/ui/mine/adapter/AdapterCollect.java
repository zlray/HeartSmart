package com.xqlh.heartsmart.ui.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityUserCollect;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class AdapterCollect extends BaseQuickAdapter<EntityUserCollect.ResultBean, BaseViewHolder> {
    private Context mContext;
    List<EntityUserCollect.ResultBean> listNewest;

    public AdapterCollect(int layoutResId, Context mContext, List<EntityUserCollect.ResultBean> list) {
        super(layoutResId, list);
        this.mContext = mContext;
        this.listNewest = list;
    }

    public void addNewestList(List<EntityUserCollect.ResultBean> list) {
        //增加数据
        int position = listNewest.size();
        listNewest.addAll(position, list);
        notifyItemInserted(position);
    }

    public void updateList(List<EntityUserCollect.ResultBean> list){
        listNewest = list;
    }


    @Override
    protected void convert(BaseViewHolder helper, final EntityUserCollect.ResultBean item) {
        helper.setText(R.id.tv_article_title, item.getArticleTitle())
                .setText(R.id.tv_article_type, item.getArticleTypeStr())
                .setText(R.id.tv_article_reading_times, item.getShowTimes() + "人阅读")
                .setText(R.id.tv_article_date, item.getPublishDate());

        helper.addOnClickListener(R.id.ll_newest_content).addOnClickListener(R.id.ll_delate);

        Glide.with(mContext).load(item.getTitlePic()).into((ImageView) helper.getView(R.id.iv_article_titlepic));
    }



}
