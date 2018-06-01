package com.xqlh.heartsmart.ui.appraisal.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityAppraisalAnswer;
import com.xqlh.heartsmart.bean.EntityAppraisalTopic;
import com.xqlh.heartsmart.bean.EntityReportAnswer;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterAnswerApprisal;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterAnswerApprisalOne;
import com.xqlh.heartsmart.ui.mine.ui.UndoneAppraisalActivity;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppraisalUndoneActivity extends BaseActivity {

    @BindView(R.id.tv_topic)
    TextView tv_topic;

    @BindView(R.id.tv_topic_number)
    TextView tv_topic_number;

    @BindView(R.id.rv_appraisal_answer)
    RecyclerView rv_appraisal_answer;
    @BindView(R.id.iv_topic)
    ImageView iv_topic;
    @BindView(R.id.tv_time1)
    TextView tv_time1;
    @BindView(R.id.tv_time2)
    TextView tv_time2;
    @BindView(R.id.tv_time3)
    TextView tv_time3;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.pb_bar)
    ProgressBar pb_bar;
    @BindView(R.id.tv_number1)
    TextView tv_number1;
    @BindView(R.id.tv_number2)
    TextView tv_number2;

    private String psyID;
    private String token;
    private String testRecordId;
    private String topicid;
    private String name;

    private List<EntityAppraisalTopic.ResultBean> lisTopic;
    private List<String> listAnswer = new ArrayList<>();

    private AdapterAnswerApprisal adapterAnswerApprisal;

    private AdapterAnswerApprisalOne adapterAnswerApprisalOne;

    private int topicIndex = 0;
    SharedPreferencesHelper sp;
    RxDialogSureCancel rxDialogSureCancel;

    private int time;
    private int a;

    int click;
    boolean onClick = true;

    @Override
    public int setContent() {
        return R.layout.activity_appraisal_undone_activity;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        rxDialogSureCancel = new RxDialogSureCancel(mContext);//提示弹窗

        Intent intent = getIntent();

        psyID = intent.getStringExtra("PsyID");//测评的id

        testRecordId = intent.getStringExtra("TestRecordId");

        Log.i(TAG, "测评记录的id       " + testRecordId);
        name = intent.getStringExtra("name");
        tv_name.setText(name);
        time = intent.getIntExtra("time", 0) * 60;

        rv_appraisal_answer.setLayoutManager(new LinearLayoutManager(this));

        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);

        topicIndex = (int) sp.getSharedPreference(testRecordId, 0);

        //设置进度
        pb_bar.setProgress(topicIndex + 1);

        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();

        initTopic(psyID);
        handler.postDelayed(runnable, 0);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            String formatLongToTimeStr = formatLongToTimeStr(time);
            String[] split = formatLongToTimeStr.split("：");
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    tv_time1.setText(split[0] + "时");
                }
                if (i == 1) {
                    tv_time2.setText(split[1] + "分");
                }
                if (i == 2) {
                    tv_time3.setText(split[2] + "秒");
                }

            }
            if (time > 0) {
                handler.postDelayed(this, 1000);
            }
        }
    };

    public String formatLongToTimeStr(int l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour + "：" + minute + "：" + second;
        return strtime;

    }


    //根据测评id获取题目的信息
    public void initTopic(String id) {
        RetrofitHelper.getApiService()
                .getAppraisalTopic(id)
                .subscribeOn(Schedulers.io())
                .compose(ProgressUtils.<EntityAppraisalTopic>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityAppraisalTopic>() {
                    @Override
                    public void onSuccess(final EntityAppraisalTopic response) {
                        if (response.getCode() == 1) {

                            lisTopic = response.getResult();

                            Log.i(TAG, "集合大小" + lisTopic.size() + "........");

                            pb_bar.setMax(lisTopic.size());
                            tv_number2.setText("/" + lisTopic.size() + "题");

                            if (topicIndex < lisTopic.size()) {


                                tv_number1.setText((Integer) sp.getSharedPreference(testRecordId, 0) + 1 + "");


                                Log.i(TAG, "题目id: " + lisTopic.get(topicIndex).getID());

                                tv_topic_number.setText(lisTopic.get(topicIndex).getTopicNumber() + ".   ");

                                //设置题目
                                //如果包含|线
                                String topic = lisTopic.get(topicIndex).getContent();

                                Log.i(TAG, "str" + topic);

                                if (topic.contains("|".toString())) {

                                    tv_topic.setText(topic.substring(0, topic.indexOf("|")));
                                    Log.i(TAG, "之前的" + topic.substring(0, topic.indexOf("|")));

                                    iv_topic.setVisibility(View.VISIBLE);

                                    Glide.with(mContext).load(topic.substring(topic.indexOf("|") + 1)).into(iv_topic);
                                    Log.i(TAG, "之后的" + topic.substring(topic.indexOf("|") + 1));
                                } else {
                                    //题目为图片
                                    if (topic.startsWith("http")) {
                                        tv_topic.setVisibility(View.GONE);

                                        iv_topic.setVisibility(View.VISIBLE);
                                        Glide.with(mContext).load(topic).into(iv_topic);
                                    } else {
                                        //题目为文字
                                        iv_topic.setVisibility(View.GONE);
                                        tv_topic.setText(topic);
                                    }
                                }

                                topicid = lisTopic.get(topicIndex).getID();//获取题目的id

                                initAnswer(topicid);

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                        //跳转到测试报告界面
                                        Intent intent = new Intent(AppraisalUndoneActivity.this, AppraisalReportActivity.class);
                                        intent.putExtra("TestRecordId", testRecordId);
                                        startActivity(intent);
                                        Log.i(TAG, "测评答案的集合" + listAnswer.size());
                                    }
                                });
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }

    public void initAnswer(final String topicid) {
        RetrofitHelper.getApiService()
                .getAppraisalAnswer(topicid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityAppraisalAnswer>() {
                    @Override
                    public void onSuccess(final EntityAppraisalAnswer response) {
                        if (response.getCode() == 1) {
                            //获得答案，判断答案的
                            String answer = response.getResult().get(0).getContent();

                            if (answer.startsWith("http")) {
                                adapterAnswerApprisalOne
                                        = new AdapterAnswerApprisalOne(
                                        R.layout.item_appraisal_answer_one,
                                        ContextUtils.getContext(),
                                        response.getResult());

                                rv_appraisal_answer.setAdapter(adapterAnswerApprisalOne);
                                if (onClick) {
                                    adapterAnswerApprisalOne.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            onClick = false;

                                            click++;
                                            Log.i(TAG, "onItemClick:点击" + click);

                                            pb_bar.incrementProgressBy(1);

                                            topicIndex++;
                                            sp.put(testRecordId, topicIndex);
                                            //提交答案
                                            reportAnswer(testRecordId, response.getResult().get(position).getOptionNumber(), topicid);
                                            listAnswer.add(response.getResult().get(position).getOptionNumber() + "");
                                        }
                                    });
                                }

                            } else {
                                adapterAnswerApprisal
                                        = new AdapterAnswerApprisal(
                                        R.layout.item_appraisal_answer,
                                        ContextUtils.getContext(),
                                        response.getResult());
                                adapterAnswerApprisal.openLoadAnimation();

                                rv_appraisal_answer.setAdapter(adapterAnswerApprisal);

                                if (onClick) {
                                    adapterAnswerApprisal.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                            onClick = false;
                                            click++;
                                            Log.i(TAG, "onItemClick:点击" + click);

                                            pb_bar.incrementProgressBy(1);

                                            topicIndex++;
                                            sp.put(testRecordId, topicIndex);
                                            //提交答案
                                            reportAnswer(testRecordId, response.getResult().get(position).getOptionNumber(), topicid);

                                            listAnswer.add(response.getResult().get(position).getOptionNumber() + "");
                                        }
                                    });
                                }
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }

//    提交测评答案【api/psychtest/savepuo】
//
//    POST 登录状态
//
//    参数	类型	说明
//    ptestUserid	string	测评记录ID
//    optionNumber	string	选项编号。如A，B，C，1,2,3等
//    topicID	string	题目ID

    public void reportAnswer(String ptestUserid, String optionNumber, String topicID) {
        a++;
        Log.i(TAG, "reportAnswer:答案数" + a);
        RetrofitHelper.getApiService()
                .reportAnswer(token, ptestUserid, optionNumber, topicID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityReportAnswer>() {
                    @Override
                    public void onSuccess(final EntityReportAnswer response) {
                        if (response.getCode() == 1) {
                            //提交成功刷新下一题
                            initTopic(psyID);//刷新下一题
                            onClick = true;//答案提交成功
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDoalog();
        }
        return true;
    }

    public void showDoalog() {

        rxDialogSureCancel.getContentView().setText("是否退出测试");
        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.put(testRecordId, topicIndex);
                Intent intent = new Intent(AppraisalUndoneActivity.this, UndoneAppraisalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSureCancel.cancel();
            }
        });
        rxDialogSureCancel.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rxDialogSureCancel.cancel();
    }
}
