package com.xqlh.heartsmart.ui.appraisal.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityAppraisalAnswer;
import com.xqlh.heartsmart.bean.EntityAppraisalTopic;
import com.xqlh.heartsmart.ui.appraisal.adapter.AdapterAnswerApprisal;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.ProgressUtils;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppraisalActivity extends BaseActivity {

    @BindView(R.id.tv_topic)
    TextView tv_topic;

    @BindView(R.id.rv_appraisal_answer)
    RecyclerView rv_appraisal_answer;
    private String id;

    private List<EntityAppraisalTopic.ResultBean> lisTopic;
    private List<EntityAppraisalAnswer.ResultBean> listAnswer;

    private AdapterAnswerApprisal adapterAnswerApprisal;

    private int topicIndex = 0;


    @Override
    public int setContent() {
        return R.layout.activity_appraisal;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        rv_appraisal_answer.setLayoutManager(new LinearLayoutManager(this));
        initTopic(id);

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
                            if (topicIndex < lisTopic.size()) {

                                Log.i(TAG, "题目id: " + lisTopic.get(topicIndex).getID());

                                //设置题目
                                tv_topic.setText(lisTopic.get(topicIndex).getTopicNumber() + ".  " + lisTopic.get(topicIndex).getContent());

                                initAnswer(lisTopic.get(topicIndex).getID());//获取题目的id

                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(AppraisalActivity.this, AppraisalReportActivity.class));

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

    public void initAnswer(String topicid) {
        RetrofitHelper.getApiService()
                .getAppraisalAnswer(topicid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityAppraisalAnswer>() {
                    @Override
                    public void onSuccess(final EntityAppraisalAnswer response) {
                        if (response.getCode() == 1) {
                            adapterAnswerApprisal
                                    = new AdapterAnswerApprisal(
                                    R.layout.item_appraisal_answer,
                                    ContextUtils.getContext(),
                                    response.getResult());

                            rv_appraisal_answer.setAdapter(adapterAnswerApprisal);

                            adapterAnswerApprisal.openLoadAnimation();

                            adapterAnswerApprisal.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    topicIndex++;
                                    initTopic(id);
                                }
                            });
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }
}
