package com.xqlh.heartsmart.ui.equipment.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;

public class EquipmentReportActivity extends BaseActivity {
    private String id;
    private String title;
    @BindView(R.id.rv_equipment_report)
    RecyclerView rv_equipment_report;

    @BindView(R.id.equipment_report_titlebar)
    TitleBar equipment_report_titlebar;

    @Override
    public int setContent() {
        return R.layout.activity_equipment_report;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        Log.i(TAG, "设备的id");
        Log.i(TAG, "设备的title");
        initTtileBar();
    }

    public void initTtileBar() {
        equipment_report_titlebar.setTitle(title);
        equipment_report_titlebar.setTitleColor(Color.WHITE);
        equipment_report_titlebar.setLeftImageResource(R.drawable.return_button);
        equipment_report_titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
