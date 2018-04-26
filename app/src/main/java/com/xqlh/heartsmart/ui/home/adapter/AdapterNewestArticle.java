package com.xqlh.heartsmart.ui.home.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityArticleNewest;
import com.xqlh.heartsmart.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/23.
 */

public class AdapterNewestArticle extends BaseQuickAdapter<EntityArticleNewest.ResultBean, BaseViewHolder> {

    private Context mContext;
    List<EntityArticleNewest.ResultBean> listNewest;

    public AdapterNewestArticle(int layoutResId, Context mContext, List<EntityArticleNewest.ResultBean> list) {
        super(layoutResId,list);
        this.mContext = mContext;
        this.listNewest = list;
    }

    public void addNewestList(List<EntityArticleNewest.ResultBean> list) {
        //增加数据
        int position = listNewest.size();
        listNewest.addAll(position, list);
        notifyItemInserted(position);
    }


    @Override
    protected void convert(BaseViewHolder helper, EntityArticleNewest.ResultBean item) {
        Log.i("lz", item.getTitle() + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_type, item.getArticleTypeStr())
                .setText(R.id.tv_article_reading_times, item.getShowTimes() + "人阅读")
                .setText(R.id.tv_article_date, Constants.getYYD(item.getCreateTime()));
        Glide.with(mContext).load(item.getTitlePic()).into((ImageView) helper.getView(R.id.iv_article_titlepic));
    }
}
