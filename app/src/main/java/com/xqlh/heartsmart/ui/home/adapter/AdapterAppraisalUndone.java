package com.xqlh.heartsmart.ui.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityUndoneAppraisal;

import java.util.List;

/**
 * Created by Administrator on 2018/5/10.
 */

public class AdapterAppraisalUndone extends BaseQuickAdapter<EntityUndoneAppraisal.ResultBean, BaseViewHolder> {
    private Context mContext;
    List<EntityUndoneAppraisal.ResultBean> listUndone;

    public AdapterAppraisalUndone(int layoutResId, @Nullable List<EntityUndoneAppraisal.ResultBean> list) {
        super(layoutResId, list);
        this.mContext = mContext;
        this.listUndone = list;
    }

    public void addUndoneList(List<EntityUndoneAppraisal.ResultBean> list) {
        //增加数据
        int position = listUndone.size();
        listUndone.addAll(position, list);
        notifyItemInserted(position);
    }


    @Override
    protected void convert(BaseViewHolder helper, EntityUndoneAppraisal.ResultBean item) {
        helper.setText(R.id.tv_appraisal_name, item.getPsychtestName())
                .setText(R.id.tv_appraisal_title, item.getPsychtestTitle())
                .setText(R.id.tv_appraisal_topic_number, "题目数:" + item.getPsychtestTopicCount())
                .setText(R.id.tv_appraisal_test_time, "测试耗时" + item.getTestTime() + "分钟")
                .setText(R.id.tv_appraisal_create_time, item.getPData());

    }
}
