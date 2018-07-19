
package com.xqlh.heartsmart.ui.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.utils.DynamicLineChartManager1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class DetectionReportActivity extends BaseActivity {

    @BindView(R.id.line_chart)
    LineChart line_chart;

    @BindView(R.id.tv_attention_average)
    TextView tv_attention_average;

    @BindView(R.id.tv_attention_max)
    TextView tv_attention_max;

    @BindView(R.id.tv_attention_min)
    TextView tv_attention_min;

//    @BindView(R.id.tv_attention_square)
//    TextView tv_attention_square;
//
//    @BindView(R.id.tv_attention_standard)
//    TextView tv_attention_standard;

    @BindView(R.id.tv_relax_average)
    TextView tv_relax_average;

    @BindView(R.id.tv_relax_max)
    TextView tv_relax_max;

    @BindView(R.id.tv_relax_min)
    TextView tv_relax_min;

//    @BindView(R.id.tv_relax_square)
//    TextView tv_relax_square;
//
//    @BindView(R.id.tv_relax_standard)
//    TextView tv_relax_standard;

    private DynamicLineChartManager1 dynamicLineChartManager;

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
        Log.i(TAG, attention.size() + "");
        Log.i(TAG, attention.size() + "");
        initLinerChartData();
        setData();
    }

    public void initLinerChartData() {
        names.add("专注度");
        names.add("冥想度");

        colour.add(Color.RED);
        colour.add(Color.BLUE);

        dynamicLineChartManager = new DynamicLineChartManager1(line_chart, names, colour);
        //设置y轴
        dynamicLineChartManager.setYAxis(100, 0, 10);
        dynamicLineChartManager.setDescription("");

        if (attention.size() > 0 && relaxation.size() > 0) {
            for (int i = 0; i < attention.size(); i++) {
                list.add(attention.get(i));
                list.add(relaxation.get(i));
                dynamicLineChartManager.addEntry(list);
                list.clear();
            }
        }
    }

    public void setData() {
        int attentionAverage = 0;
        for (int i = 0; i < attention.size(); i++) {
            attentionAverage = attention.get(i) + attentionAverage;
        }
        if (attention.size() > 0) {
            tv_attention_average.setText(attentionAverage / attention.size() + "");
        } else {
            tv_attention_average.setText(0 + "");
        }
        tv_attention_max.setText(Collections.max(attention) + "");
        tv_attention_min.setText(Collections.min(attention) + "");


        int relaxAverage = 0;
        for (int i = 0; i < relaxation.size(); i++) {
            relaxAverage = relaxation.get(i) + relaxAverage;
        }
        if (relaxation.size() > 0) {
            tv_relax_average.setText(relaxAverage / relaxation.size() + "");
        } else {
            tv_relax_average.setText(0 + "");
        }
        tv_relax_max.setText(Collections.max(relaxation) + "");
        tv_relax_min.setText(Collections.min(relaxation) + "");
    }
}
