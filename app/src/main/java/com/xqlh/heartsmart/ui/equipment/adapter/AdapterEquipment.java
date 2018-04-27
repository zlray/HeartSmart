package com.xqlh.heartsmart.ui.equipment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.ui.equipment.model.EquipmentIconTitleModel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AdapterEquipment extends BaseQuickAdapter<EquipmentIconTitleModel, BaseViewHolder> {

    private List<EquipmentIconTitleModel> list;

    public AdapterEquipment(int layoutResId, @Nullable List<EquipmentIconTitleModel> data) {
        super(layoutResId, data);
        this.list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, EquipmentIconTitleModel item) {

        helper.setImageResource(R.id.iv_equipment,item.getIconResource());

        helper.setText(R.id.tv_equipment,item.getTitle());

    }
}
