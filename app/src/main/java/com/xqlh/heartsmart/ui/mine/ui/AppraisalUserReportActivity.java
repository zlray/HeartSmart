package com.xqlh.heartsmart.ui.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityReportBasics;
import com.xqlh.heartsmart.bean.EntityReportDimension;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;
import com.xqlh.heartsmart.widget.netView;

import java.util.Arrays;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppraisalUserReportActivity extends BaseActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_total_score)
    TextView tv_total_score;

    @BindView(R.id.tv_level)
    TextView tv_level;

    @BindView(R.id.tv_analys)
    TextView tv_analys;


    @BindView(R.id.tv_advice)
    TextView tv_advice;

    @BindView(R.id.tv_comment)
    TextView tv_comment;

    @BindView(R.id.tv_text_analys)
    TextView tv_text_analys;

    @BindView(R.id.tv_text_advice)
    TextView tv_text_advice;

    @BindView(R.id.tv_text_comment)
    TextView tv_text_comment;


    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @BindView(R.id.ll_score)
    LinearLayout ll_score;

    @BindView(R.id.ll_level)
    LinearLayout ll_level;

    @BindView(R.id.ll_analys)
    LinearLayout ll_analys;

    @BindView(R.id.ll_advice)
    LinearLayout ll_advice;

    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;

    @BindView(R.id.nv_dimension)
    netView nv_dimension;

    private String testRecordId;
    private SharedPreferencesHelper sp;
    private String token;
    private String[] titles = new String[0];
    private double[] percent = new double[0];
    private String reportId;

    @Override
    public int setContent() {
        return R.layout.activity_appraisal_user_report;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {

        initTtileBar();
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();
        Log.i(TAG, "报告界面的 token    " + token);

        Intent intent = getIntent();
        reportId = intent.getStringExtra("reportId");

        initReportBase(reportId);
        initReportDimension(reportId);
    }
    public void initTtileBar() {
        titlebar.setTitle("测评报告");
        titlebar.setTitleColor(Color.WHITE);
        titlebar.setLeftImageResource(R.drawable.return_button);
        titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //获得报告的信息
    public void initReportBase(String reportId) {
        RetrofitHelper.getApiService()
                .getReportBasic(token, reportId)
                .compose(ProgressUtils.<EntityReportBasics>applyProgressBar(this))
                .compose(this.<EntityReportBasics>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityReportBasics>() {
                    @Override
                    public void onSuccess(final EntityReportBasics response) {
                        if (response.getCode() == 1) {
                            String analys = "";
                            String advice = "";
                            String comment = "";
                            if (response.getResult() != null) {
                                tv_name.setText(response.getResult().getUserName());
                                tv_time.setText(response.getResult().getPsyReportDate());
                                if (response.getResult().getTotalScore() > 0.0) {
                                    tv_total_score.setText(response.getResult().getTotalScore() + "");
                                } else {
                                    ll_score.setVisibility(View.GONE);
                                }
                                if (response.getResult().getTotalLevelName() != null) {
                                    tv_level.setText(response.getResult().getTotalLevelName());
                                } else {
                                    ll_level.setVisibility(View.GONE);
                                }
                                if (response.getResult().getAnalys() != null) {
                                    for (int i = 0; i < response.getResult().getAnalys().size(); i++) {
                                        analys = response.getResult().getAnalys() + "";
                                    }
                                    tv_analys.setText(analys);
                                } else {
                                    ll_analys.setVisibility(View.GONE);
                                }
                                if (response.getResult().getAdvice() != null) {
                                    for (int j = 0; j < response.getResult().getAdvice().size(); j++) {
                                        advice = response.getResult().getAdvice() + "";
                                    }
                                    tv_advice.setText(advice);
                                } else {
                                    ll_advice.setVisibility(View.GONE);
                                }
                                if (response.getResult().getComment() != null) {
                                    for (int j = 0; j < response.getResult().getComment().size(); j++) {
                                        comment = response.getResult().getComment() + "";
                                    }
                                    tv_comment.setText(comment);
                                } else {
                                    ll_comment.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    //报告维度信息
    public void initReportDimension(final String reportId) {
        RetrofitHelper.getApiService()
                .getReportDimension(token, reportId)
                .compose(ProgressUtils.<EntityReportDimension>applyProgressBar(this))
                .compose(this.<EntityReportDimension>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityReportDimension>() {
                    @Override
                    public void onSuccess(final EntityReportDimension response) {
                        if (response.getCode() == 1) {
                            if (response.getResult().size() > 0) {
                                titles = new String[response.getResult().size()];
                                percent = new double[response.getResult().size()];
                                for (int i = 0; i < response.getResult().size(); i++) {
                                    titles[i] = response.getResult().get(i).getDimensionName();
                                    percent[i] = response.getResult().get(i).getScore();
                                }
                                Log.i(TAG, "onSuccess: " + Arrays.toString(titles));
                                Log.i(TAG, "onSuccess: " + Arrays.toString(percent));

                                nv_dimension.setTitles(titles);
                                nv_dimension.setPercent(percent);
                            } else {
                                nv_dimension.setVisibility(View.GONE);
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

}
