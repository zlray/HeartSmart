package com.xqlh.heartsmart.ui.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityArticleBeautiful;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterBeautifulArticle extends BaseQuickAdapter<EntityArticleBeautiful.ResultBean, BaseViewHolder> {

    private Context mContext;

    public AdapterBeautifulArticle(int layoutResId, Context mContext, List<EntityArticleBeautiful.ResultBean> list) {
        super(layoutResId, list);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, EntityArticleBeautiful.ResultBean item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_reading_number, item.getShowTimes() + "人阅读");

        Glide.with(mContext).load(item.getTitlePic())
                .into((ImageView) helper.getView(R.id.iv_article_selection));
    }
}
