package com.xqlh.heartsmart.ui.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.bean.EntityUserReport;
import com.xqlh.heartsmart.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2018/4/18.
 */

public class AdapterUserReport extends BaseQuickAdapter<EntityUserReport.ResultBean, BaseViewHolder> {

    private Context mContext;
    List<EntityUserReport.ResultBean> listReport;

    public AdapterUserReport(int layoutResId, Context mContext, List<EntityUserReport.ResultBean> list) {
        super(layoutResId, list);
        listReport = list;
        this.mContext = mContext;
    }

    public void addUserReport(List<EntityUserReport.ResultBean> list) {
        //增加数据
        int position = listReport.size();
        listReport.addAll(position, list);
        notifyItemInserted(position);
    }

    @Override
    protected void convert(BaseViewHolder helper, EntityUserReport.ResultBean item) {
        helper.setText(R.id.tv_report_name, item.getName())
                .setText(R.id.tv_report_title, item.getTitle())
                .setText(R.id.tv_report_type, item.getEvaluationTypeStr())
                .setText(R.id.tv_report_date, Constants.getYYD(item.getCreateTime()));

        Glide.with(mContext).load(item.getPsychtestPic())
                .error(R.drawable.empty)//错误图片显示
                .fallback(R.drawable.empty)//url为空的显示的 图片
                .into((ImageView) helper.getView(R.id.iv_report));
    }
}
