package com.xqlh.heartsmart.ui.appraisal.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityAppraisalAnswer;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterAnswerApprisal extends BaseQuickAdapter<EntityAppraisalAnswer.ResultBean, BaseViewHolder> {

    private Context mContext;

    public AdapterAnswerApprisal(int layoutResId, Context mContext, List<EntityAppraisalAnswer.ResultBean> list) {
        super(layoutResId, list);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, EntityAppraisalAnswer.ResultBean item) {

        helper.setText(R.id.tv_answer_number, item.getOptionNumber() + ".  ");

        helper.setText(R.id.tv_answer, item.getContent());
    }
}
