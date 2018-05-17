package com.xqlh.heartsmart.ui.appraisal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityUserReport;
import com.xqlh.heartsmart.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterUserReportSimple extends BaseQuickAdapter<EntityUserReport.ResultBean, BaseViewHolder> {


    public AdapterUserReportSimple(int layoutResId, List<EntityUserReport.ResultBean> list) {
        super(layoutResId, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, EntityUserReport.ResultBean item) {
        helper.setText(R.id.tv_report_name, item.getName());
        helper.setText(R.id.tv_report_date, Constants.getYYD(item.getCreateTime()));
    }
}
