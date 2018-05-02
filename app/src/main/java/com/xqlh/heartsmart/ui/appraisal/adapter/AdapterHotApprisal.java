package com.xqlh.heartsmart.ui.appraisal.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityAppraisalRecommend;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterHotApprisal extends BaseQuickAdapter<EntityAppraisalRecommend.ResultBean, BaseViewHolder> {

    private Context mContext;

    public AdapterHotApprisal(int layoutResId, Context mContext, List<EntityAppraisalRecommend.ResultBean> list) {
        super(layoutResId, list);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, EntityAppraisalRecommend.ResultBean item) {
        helper.setText(R.id.tv_title, item.getTitle());

        Glide.with(mContext).load(item.getPsyPic())
                .into((ImageView) helper.getView(R.id.iv_article_selection));
    }
}
