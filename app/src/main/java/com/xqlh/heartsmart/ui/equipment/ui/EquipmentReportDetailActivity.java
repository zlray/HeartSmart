package com.xqlh.heartsmart.ui.equipment.ui;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityReportBasics;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EquipmentReportDetailActivity extends BaseActivity {
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


    private String id;
    private String name;
    @BindView(R.id.equipment_report_titlebar)
    TitleBar equipment_report_titlebar;
    private SharedPreferencesHelper sp_login_token;
    private String token;

    @Override
    public int setContent() {
        return R.layout.activity_equipment_report_detail;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        Log.i(TAG, "报告" + id);
        sp_login_token = new SharedPreferencesHelper(
                this, Constants.CHECKINFOR);

        token = sp_login_token.getSharedPreference(Constants.LOGIN_TOKEN, "").toString().trim();
        initData(token, id);
        initTtileBar(name);
    }

    public void initTtileBar(String name) {
        equipment_report_titlebar.setTitle(name);
        equipment_report_titlebar.setTitleColor(Color.WHITE);
        equipment_report_titlebar.setLeftImageResource(R.drawable.return_button);
        equipment_report_titlebar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void initData(String token, String id) {
        RetrofitHelper.getApiService()
                .getReportBasic(token, id)
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
                                tv_total_score.setText(response.getResult().getTotalScore() + "");
                                tv_level.setText(response.getResult().getTotalLevelName());

                                for (int i = 0; i < response.getResult().getAnalys().size(); i++) {
                                    analys = response.getResult().getAnalys() + "";
                                }
                                tv_analys.setText(analys);
                                for (int j = 0; j < response.getResult().getAdvice().size(); j++) {
                                    advice = response.getResult().getAdvice() + "";
                                }
                                tv_advice.setText(advice);
                                for (int j = 0; j < response.getResult().getComment().size(); j++) {
                                    comment = response.getResult().getComment() + "";
                                }
                                tv_comment.setText(comment);
                            }
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }
}
