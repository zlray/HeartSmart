package com.xqlh.heartsmart.ui.equipment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityEquipmentReport;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AdapterEquipmentReport extends BaseQuickAdapter<EntityEquipmentReport.ResultBean, BaseViewHolder> {

    private List<EntityEquipmentReport.ResultBean> list;

    public AdapterEquipmentReport(int layoutResId, @Nullable List<EntityEquipmentReport.ResultBean> data) {
        super(layoutResId, data);
        this.list = data;
    }
    public void addEquipmentReportList(List<EntityEquipmentReport.ResultBean> list) {
        //增加数据
        int position = list.size();
        list.addAll(position, list);
        notifyItemInserted(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, EntityEquipmentReport.ResultBean item) {
        helper.setText(R.id.tv_report_name,item.getName());
        helper.setText(R.id.tv_report_result,"测试结果: 总体得分"+item.getTotalScore()+"");
        helper.setText(R.id.tv_report_date, item.getCreateDate());
    }
}
