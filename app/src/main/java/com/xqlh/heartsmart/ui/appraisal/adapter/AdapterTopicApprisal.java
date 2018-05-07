package com.xqlh.heartsmart.ui.appraisal.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityAppraisalTopic;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterTopicApprisal extends BaseQuickAdapter<EntityAppraisalTopic.ResultBean, BaseViewHolder> {

    private Context mContext;

    public AdapterTopicApprisal(int layoutResId, Context mContext, List<EntityAppraisalTopic.ResultBean> list) {
        super(layoutResId, list);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, EntityAppraisalTopic.ResultBean item) {
        helper.setText(R.id.tv_topic, item.getTopicNumber() + ".  " + item.getContent());
    }
}
