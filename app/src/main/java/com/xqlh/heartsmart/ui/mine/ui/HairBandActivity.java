package com.xqlh.heartsmart.ui.mine.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.DynamicLineChartManager;
import com.xqlh.heartsmart.utils.InstrumentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;


public class HairBandActivity extends BaseActivity {

    @BindView(R.id.line_chart)
    LineChart line_chart;

    BluetoothAdapter mBluetoothAdapter;

    @BindView(R.id.tv_infor)
    TextView tv_infor;
    @BindView(R.id.bt_connect)
    Button bt_connect;

    @BindView(R.id.instrumentView_attention)
    InstrumentView instrumentView_attention;


    @BindView(R.id.instrumentView_relaxation)
    InstrumentView instrumentView_relaxation;

    private DynamicLineChartManager dynamicLineChartManager;

    private List<Integer> list = new ArrayList<>(); //数据集合
    private List<String> names = new ArrayList<>(); //折线名字集合
    private List<Integer> colour = new ArrayList<>();//折线颜色集合

    private ArrayList<Integer> attention = new ArrayList<>();//专注度
    private ArrayList<Integer> relaxation = new ArrayList<>();//放松度

    TGDevice tgDevice;
    int subjectContactQuality_last;
    int subjectContactQuality_cnt;
    private boolean onClick;

    @Override
    public int setContent() {
        return R.layout.activity_hair_band;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBleDevice();
        subjectContactQuality_last = -1; /* start with impossible value */
        subjectContactQuality_cnt = 200; /* start over the limit, so it gets reported the 1st time */
        initLinerChartData();
    }

    public void initLinerChartData() {
        names.add("delta");
        names.add("theta");
        names.add("高alpha");
        names.add("低alpha");
        names.add("高Beta");
        names.add("低Beta");
        names.add("中Gamma");
        names.add("低Gamma");

        colour.add(Color.GREEN);
        colour.add(Color.BLACK);
        colour.add(Color.RED);
        colour.add(Color.BLUE);
        colour.add(Color.YELLOW);
        colour.add(Color.GRAY);
        colour.add(Color.LTGRAY);
        colour.add(Color.DKGRAY);

        dynamicLineChartManager = new DynamicLineChartManager(line_chart, names, colour);
        //设置y轴
        dynamicLineChartManager.setYAxis(10, 0, 10);
        dynamicLineChartManager.setDescription("");
    }

    public void checkBleDevice() {
        if (mBluetoothAdapter != null) {
            // create the TGDevice
            tgDevice = new TGDevice(mBluetoothAdapter, handler);
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(enableBtIntent);
            }
        } else {
            Toasty.warning(ContextUtils.getContext(), "该手机不支持蓝牙", Toast.LENGTH_SHORT, true).show();
        }
    }

    @OnClick(R.id.bt_connect)
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_connect:
                //如果连接成功
                if (onClick) {
                    tgDevice.close();
                    //跳转到报告页面
                    if (attention.size() > 0 && relaxation.size() > 0) {
                        finish();
                        Intent intent = new Intent(HairBandActivity.this, DetectionReportActivity.class);
                        intent.putIntegerArrayListExtra("attention", attention);
                        intent.putIntegerArrayListExtra("relaxation", relaxation);
                        startActivity(intent);
                    } else {
                        Toasty.warning(ContextUtils.getContext(), "尚未收集到专注度和冥想度,请继续测试", Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    tgDevice.connect(true);
                }
                break;
        }
    }

    /**
     * Handles messages from TGDevice
     */
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TGDevice.MSG_MODEL_IDENTIFIED: //TGDevice 已经连接，可以接受数据
                    /*
                     * now there is something connected,
            		 * time to set the configurations we need
            		 */
                    tgDevice.setBlinkDetectionEnabled(true);
                    tgDevice.setTaskDifficultyRunContinuous(true);
                    tgDevice.setTaskDifficultyEnable(true);
                    tgDevice.setTaskFamiliarityRunContinuous(true);
                    tgDevice.setTaskFamiliarityEnable(true);
                    tgDevice.setRespirationRateEnable(true); /// not allowed on EEG hardware, here to show the override message
                    tgDevice.setPositivityEnable(true);
                    break;
                case TGDevice.MSG_STATE_CHANGE: //状态信息变化
                    switch (msg.arg1) {
                        case TGDevice.STATE_IDLE:
                            break;
                        case TGDevice.STATE_CONNECTING:
                            tv_infor.setText("正在连接");
                            break;
                        case TGDevice.STATE_CONNECTED:
                            tv_infor.setText("连接成功");
                            tgDevice.start();
                            //设置问true
                            onClick = true;
                            bt_connect.setText("断开连接");

                            break;
                        case TGDevice.STATE_NOT_FOUND:
                            tv_infor.setText("脑波发带未扫描到");
                            break;
                        case TGDevice.STATE_ERR_NO_DEVICE:
                            tv_infor.setText("请打开脑波发带进行蓝牙匹配");
                            break;
                        case TGDevice.STATE_ERR_BT_OFF:
                            tv_infor.setText("手机蓝牙没打开或者不可用");
                            break;
                        case TGDevice.STATE_DISCONNECTED:
                            tv_infor.setText("断开连接");
                            instrumentView_attention.setProgress(0);
                            instrumentView_relaxation.setProgress(0);

                    } /* end switch on msg.ar0g1 */
                    break;

                case TGDevice.MSG_ATTENTION: //专注度等级
                    instrumentView_attention.setProgress(msg.arg1);
                    Log.i(TAG, "专注度" + msg.arg1);
                    attention.add(msg.arg1);
                    instrumentView_attention.setText("专注度");
                    break;
                case TGDevice.MSG_MEDITATION:
                    instrumentView_relaxation.setProgress(msg.arg1);
                    relaxation.add(msg.arg1);
                    instrumentView_relaxation.setText("冥想度");
                    Log.i(TAG, "冥想度" + msg.arg1);
                    break;
                case TGDevice.MSG_LOW_BATTERY:
                    tv_infor.setText("发带电量低，请及时更换电池");

                case TGDevice.MSG_EEG_POWER:
                    TGEegPower e = (TGEegPower) msg.obj;
                    list.add((int) Math.log10(e.delta));
                    list.add((int) Math.log10(e.theta));
                    list.add((int) Math.log10(e.highAlpha));
                    list.add((int) Math.log10(e.lowAlpha));
                    list.add((int) Math.log10(e.highBeta));
                    list.add((int) Math.log10(e.lowBeta));
                    list.add((int) Math.log10(e.midGamma));
                    list.add((int) Math.log10(e.lowGamma));

                    dynamicLineChartManager.addEntry(list);

                    list.clear();

                    Log.i(TAG, "脑波");
                    Log.i(TAG, "delta" + (int) Math.log10(e.delta));
                    Log.i(TAG, "theta" + (int) Math.log10(e.theta));
                    Log.i(TAG, "highAlpha" + (int) Math.log10(e.highAlpha));
                    Log.i(TAG, "lowAlpha" + (int) Math.log10(e.lowAlpha));
                    Log.i(TAG, "highBeta" + (int) Math.log10(e.highBeta));
                    Log.i(TAG, "lowBeta" + (int) Math.log10(e.lowBeta));
                    Log.i(TAG, "midGamma" + (int) Math.log10(e.midGamma));
                    Log.i(TAG, "lowGamma" + (int) Math.log10(e.lowGamma));
                    Log.i(TAG, "脑波");
                    break;
                case TGDevice.MSG_BLINK:
//                    tv_infor.setText("眨眼: " + msg.arg1 + "\n");
//                    Log.i(TAG, "眨眼: " + msg.arg1);
                    break;
                case TGDevice.MSG_RELAXATION:
//                    instrumentView_relaxation.setProgress(msg.arg1);
//                    relaxation.add(msg.arg1);
//                    instrumentView_relaxation.setText("放松度");
                    break;
                case TGDevice.MSG_HEART_RATE:
                    Log.i(TAG, "心率: " + msg.arg1);
                    break;
                case TGDevice.MSG_RESPIRATION:
                    Log.i(TAG, "呼吸率: " + msg.arg1);
                    break;
                case TGDevice.MSG_HEART_AGE:
                    Log.i(TAG, "呼吸率: " + msg.arg1);
                    break;
//                case TGDevice.MSG_ERR_CFG_OVERRIDE: //用户配置已经被重写
//                    switch (msg.arg1) {
//                        case TGDevice.ERR_MSG_BLINK_DETECT:
//                            tv_infor.setText("Override: blinkDetect" + "\n");
//                            Toast.makeText(getApplicationContext(), "Override: blinkDetect", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_TASKFAMILIARITY:
//                            tv_infor.setText("Override: Familiarity" + "\n");
//                            Toast.makeText(getApplicationContext(), "Override: Familiarity", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_TASKDIFFICULTY:
//                            tv_infor.setText("Override: Difficulty" + "\n");
//                            Toast.makeText(getApplicationContext(), "Override: Difficulty", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_POSITIVITY:
//                            tv_infor.setText("Override: Positivity" + "\n");
//                            Toast.makeText(getApplicationContext(), "Override: Positivity", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_RESPIRATIONRATE:
//                            //响应率
//                            tv_infor.setText("Override: Resp Rate" + "\n");
//                            Toast.makeText(getApplicationContext(), "Override: Resp Rate", Toast.LENGTH_SHORT).show();
//                            break;
//                        default:
//                            tv_infor.setText("Override: code: " + msg.arg1 + "\n");
//                            Toast.makeText(getApplicationContext(), "Override: code: " + msg.arg1 + "", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                    break;
//                case TGDevice.MSG_ERR_NOT_PROVISIONED:
//                    switch (msg.arg1) {
//                        case TGDevice.ERR_MSG_BLINK_DETECT:
//                            tv_infor.setText("No Support: blinkDetect" + "\n");
//                            Toast.makeText(getApplicationContext(), "No Support: blinkDetect", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_TASKFAMILIARITY:
//                            tv_infor.setText("No Support: Familiarity" + "\n");
//                            Toast.makeText(getApplicationContext(), "No Support: Familiarity", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_TASKDIFFICULTY:
//                            tv_infor.setText("No Support: Difficulty" + "\n");
//                            Toast.makeText(getApplicationContext(), "No Support: Difficulty", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_POSITIVITY:
//                            tv_infor.setText("No Support: Positivity" + "\n");
//                            Toast.makeText(getApplicationContext(), "No Support: Positivity", Toast.LENGTH_SHORT).show();
//                            break;
//                        case TGDevice.ERR_MSG_RESPIRATIONRATE:
//                            tv_infor.setText("No Support: Resp Rate" + "\n");
//                            Toast.makeText(getApplicationContext(), "No Support: Resp Rate", Toast.LENGTH_SHORT).show();
//                            break;
//                        default:
//                            tv_infor.setText("No Support: code: " + msg.arg1 + "\n");
//                            Toast.makeText(getApplicationContext(), "No Support: code: " + msg.arg1 + "", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                    break;
                default:
                    break;

            } /* end switch on msg.what */

        } /* end handleMessage() */

    }; /* end Handler */

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        //if (!bluetoothAdapter.isEnabled()) {
        //  Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //startActivityForResult(enableIntent, 1);
        //}
    }

    @Override
    public void onPause() {
        // tgDevice.close();
        super.onPause();
    }

    @Override
    public void onStop() {
        tgDevice.close();
        super.onStop();

    }

    @Override
    public void onDestroy() {
        //tgDevice.close();
        super.onDestroy();
    }


}
