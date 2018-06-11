package com.xqlh.heartsmart.ui.mine.ui;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.BrainWaveBean;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.InstrumentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.view.LineChartView;


public class HairBandActivity extends BaseActivity {

    @BindView(R.id.line_chart)
    LineChartView line_chart;

    BluetoothAdapter mBluetoothAdapter;

    @BindView(R.id.tv_infor)
    TextView tv_infor;
    @BindView(R.id.bt_connect)
    Button bt_connect;

    @BindView(R.id.instrumentView_attention)
    InstrumentView instrumentView_attention;


    @BindView(R.id.instrumentView_relaxation)
    InstrumentView instrumentView_relaxation;


    private String projectId;
    private List<Integer> colors = new ArrayList<>();
    private List<BrainWaveBean> listTemp = new ArrayList<BrainWaveBean>();//数据
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();   //x轴方向的坐标数据
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();            //y轴方向的坐标数据
    private List<Float> distanceList = new ArrayList<Float>();
    private boolean hasAxes = true;       //是否有轴，x和y轴
    private boolean hasAxesNames = true;   //是否有轴的名字
    private boolean hasLines = true;       //是否有线（点和点连接的线，选择false只会出现点）
    private boolean hasPoints = true;       //是否有点（每个值的点）
    private ValueShape shape = ValueShape.CIRCLE;    //点显示的形式，圆形，正方向，菱形
    private boolean isFilled = false;                //是否是填充
    private boolean hasLabels = true;               //每个点是否有名字
    private boolean isCubic = false;                 //是否是立方的，线条是直线还是弧线
    private boolean hasLabelForSelected = false;       //每个点是否可以选择（点击效果）
    private LineChartData data;          // 折线图封装的数据类


    TGDevice tgDevice;
    int subjectContactQuality_last;
    int subjectContactQuality_cnt;

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


    }

    public void checkBleDevice() {
        if (mBluetoothAdapter != null) {
            // create the TGDevice
            tgDevice = new TGDevice(mBluetoothAdapter, handler);
            bt_connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tgDevice.connect(true);
                }
            });

            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(enableBtIntent);
            }
        } else {
            Toasty.warning(ContextUtils.getContext(), "该手机不支持蓝牙", Toast.LENGTH_SHORT, true).show();
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
                    tv_infor.append("Model Identified\n");
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
                            tv_infor.append("正在连接\n");
                            break;
                        case TGDevice.STATE_CONNECTED:
                            tv_infor.append("连接成功\n");
                            tgDevice.start();
                            break;
                        case TGDevice.STATE_NOT_FOUND:
                            tv_infor.append("脑波发带未扫描到.\n");
                            break;
                        case TGDevice.STATE_ERR_NO_DEVICE:
                            tv_infor.append("请打开脑波发带进行蓝牙匹配\n");
                            break;
                        case TGDevice.STATE_ERR_BT_OFF:
                            tv_infor.append("手机蓝牙没打开或者不可用\n");
                            break;
                        case TGDevice.STATE_DISCONNECTED:
                            tv_infor.append("断开连接.\n");
                    } /* end switch on msg.arg1 */
                    break;
                case TGDevice.MSG_RAW_DATA:      //raw样本数值
                    /* Handle raw EEG/EKG data here */
                    break;
                case TGDevice.MSG_ATTENTION: //专注度等级
                    instrumentView_attention.setProgress(msg.arg1);
                    instrumentView_attention.setText("专注度");
                    break;
                case TGDevice.MSG_MEDITATION:
                    instrumentView_relaxation.setProgress(msg.arg1);
                    instrumentView_relaxation.setText("冥想度");
                    break;
                case TGDevice.MSG_EEG_POWER:
                    TGEegPower e = (TGEegPower) msg.obj;
                    Log.i(TAG, "脑电波 ; " + "delta: " + e.delta
                            + " theta: " + e.theta
                            + " 低的alpha: " + e.lowAlpha
                            + " 高的alpha: " + e.highAlpha);
                    break;
                case TGDevice.MSG_BLINK:
                    tv_infor.append("眨眼: " + msg.arg1 + "\n");
                    Log.i(TAG, "眨眼: " + msg.arg1);
                    break;
                case TGDevice.MSG_RELAXATION:

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
                case TGDevice.MSG_ERR_CFG_OVERRIDE: //用户配置已经被重写
                    switch (msg.arg1) {
                        case TGDevice.ERR_MSG_BLINK_DETECT:
                            tv_infor.append("Override: blinkDetect" + "\n");
                            Toast.makeText(getApplicationContext(), "Override: blinkDetect", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_TASKFAMILIARITY:
                            tv_infor.append("Override: Familiarity" + "\n");
                            Toast.makeText(getApplicationContext(), "Override: Familiarity", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_TASKDIFFICULTY:
                            tv_infor.append("Override: Difficulty" + "\n");
                            Toast.makeText(getApplicationContext(), "Override: Difficulty", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_POSITIVITY:
                            tv_infor.append("Override: Positivity" + "\n");
                            Toast.makeText(getApplicationContext(), "Override: Positivity", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_RESPIRATIONRATE:
                            tv_infor.append("Override: Resp Rate" + "\n");
                            Toast.makeText(getApplicationContext(), "Override: Resp Rate", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            tv_infor.append("Override: code: " + msg.arg1 + "\n");
                            Toast.makeText(getApplicationContext(), "Override: code: " + msg.arg1 + "", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
                case TGDevice.MSG_ERR_NOT_PROVISIONED:
                    switch (msg.arg1) {
                        case TGDevice.ERR_MSG_BLINK_DETECT:
                            tv_infor.append("No Support: blinkDetect" + "\n");
                            Toast.makeText(getApplicationContext(), "No Support: blinkDetect", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_TASKFAMILIARITY:
                            tv_infor.append("No Support: Familiarity" + "\n");
                            Toast.makeText(getApplicationContext(), "No Support: Familiarity", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_TASKDIFFICULTY:
                            tv_infor.append("No Support: Difficulty" + "\n");
                            Toast.makeText(getApplicationContext(), "No Support: Difficulty", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_POSITIVITY:
                            tv_infor.append("No Support: Positivity" + "\n");
                            Toast.makeText(getApplicationContext(), "No Support: Positivity", Toast.LENGTH_SHORT).show();
                            break;
                        case TGDevice.ERR_MSG_RESPIRATIONRATE:
                            tv_infor.append("No Support: Resp Rate" + "\n");
                            Toast.makeText(getApplicationContext(), "No Support: Resp Rate", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            tv_infor.append("No Support: code: " + msg.arg1 + "\n");
                            Toast.makeText(getApplicationContext(), "No Support: code: " + msg.arg1 + "", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    break;
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
