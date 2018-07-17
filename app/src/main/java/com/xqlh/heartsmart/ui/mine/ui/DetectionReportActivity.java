


package com.xqlh.heartsmart.ui.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.utils.DynamicLineChartManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DetectionReportActivity extends BaseActivity {

    @BindView(R.id.line_chart)
    LineChart line_chart;

    private DynamicLineChartManager dynamicLineChartManager;

    private ArrayList<Integer> attention = new ArrayList<>();//专注度
    private ArrayList<Integer> relaxation = new ArrayList<>();//放松度

    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合

    private List<Integer> list = new ArrayList<>(); //数据集合


    @Override
    public int setContent() {
        return R.layout.activity_detection_report;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        //获得传送的值
        Intent intent = getIntent();
        attention = intent.getIntegerArrayListExtra("attention");
        relaxation = intent.getIntegerArrayListExtra("relaxation");
        Log.i(TAG,attention.size()+"");
        Log.i(TAG,attention.size()+"");
        initLinerChartData();

    }

    public void initLinerChartData() {
        names.add("专注度");
        names.add("冥想度");

        colour.add(Color.RED);
        colour.add(Color.BLUE);

        dynamicLineChartManager = new DynamicLineChartManager(line_chart, names, colour);
        //设置y轴
        dynamicLineChartManager.setYAxis(100, 0, 10);
        dynamicLineChartManager.setDescription("");

        if (attention.size() > 0 && relaxation.size() > 0) {

            for (int i = 0; i < attention.size(); i++) {
                list.add(attention.get(i));
                list.add(relaxation.get(i));
            }
        }
        dynamicLineChartManager.addEntry(list);
    }
}
