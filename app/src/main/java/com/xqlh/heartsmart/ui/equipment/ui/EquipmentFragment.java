package com.xqlh.heartsmart.ui.equipment.ui;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseLazyFragment;
import com.xqlh.heartsmart.ui.equipment.adapter.AdapterEquipment;
import com.xqlh.heartsmart.ui.equipment.model.EquipmentIconTitleModel;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipmentFragment extends BaseLazyFragment {
    @BindView(R.id.rv_equipment)
    RecyclerView rv_equipment;

    @BindView(R.id.equipment_fragmet_titlebar)
    TitleBar equipment_fragmet_titlebar;

    private List<EquipmentIconTitleModel> equipmentList = new ArrayList<>();
    private AdapterEquipment adapterEquipment;

    @Override
    protected int setContentView() {
        return R.layout.fragment_equipment;
    }

    @Override
    protected void init() {
        initTtileBar();
        equipmentList.clear();
        equipmentList.add(new EquipmentIconTitleModel(R.drawable.equipment_appraisal, "心企心理检测咨询档案管理系统", "153dd1e6ebab4279931875d654ddc001"));
        equipmentList.add(new EquipmentIconTitleModel(R.drawable.equipment_bestray, "百思瑞生物反馈训练系统", "b60f031fecd9422f92faacc4f649994b"));
        equipmentList.add(new EquipmentIconTitleModel(R.drawable.equipment_hug, "心企智能认知调节拥抱系统", "1"));
        equipmentList.add(new EquipmentIconTitleModel(R.drawable.equipment_beat, "心企智能击打宣泄系统", "adbfad8a12564b87bbf502900a09ffc7"));
        equipmentList.add(new EquipmentIconTitleModel(R.drawable.equipment_whoop, "心企智能呐喊宣泄系统", "c8e738b521bd47d794590f443cce1351"));
        equipmentList.add(new EquipmentIconTitleModel(R.drawable.equipment_confidence, "心企智能自信心提升训练系统", "450bd9dfe8f549a8ba5d027ee85c4891"));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(ContextUtils.getContext(), 2);

        rv_equipment.setLayoutManager(gridLayoutManager);

        adapterEquipment = new AdapterEquipment(R.layout.item_equipment, equipmentList);

        rv_equipment.setAdapter(adapterEquipment);

        adapterEquipment.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), EquipmentReportActivity.class);
                intent.putExtra("id", equipmentList.get(position).getArticleTypeID());
                intent.putExtra("title", equipmentList.get(position).getTitle());
                startActivity(intent);
            }
        });
    }

    public void initTtileBar() {
        equipment_fragmet_titlebar.setTitle("设备互联");
        equipment_fragmet_titlebar.setTitleColor(Color.WHITE);
        equipment_fragmet_titlebar.setLeftTextColor(Color.WHITE);
    }

    @Override
    protected void lazyLoad() {

    }

}
