package com.xqlh.heartsmart.ui.appraisal.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityAppraisalIntroduce;
import com.xqlh.heartsmart.bean.EntityCheckUserInfor;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ContextUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppraisalIntroduceActivity extends BaseActivity {


    @BindView(R.id.iv_pic)
    ImageView iv_pic;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_topic_number)
    TextView tv_topic_number;

    @BindView(R.id.tv_show_number)
    TextView tv_show_number;

    @BindView(R.id.tv_share_number)
    TextView tv_share_number;

    @BindView(R.id.tv_introduce)
    TextView tv_introduce;

    @BindView(R.id.tv_price2)
    TextView tv_price2;

    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_topic_number2)
    TextView tv_topic_number2;

    @BindView(R.id.tv_time)
    TextView tv_time;


    @BindView(R.id.bt_appraisal)
    Button bt_appraisal;

    private String psyID;
    private int time;

    SharedPreferencesHelper sp;
    private String token;

    @Override
    public int setContent() {
        return R.layout.activity_appraisal_introduce;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        psyID = intent.getStringExtra("PsyID");//
        Log.i(TAG, "测评的id" + psyID);
        sp = new SharedPreferencesHelper(ContextUtils.getContext(), Constants.CHECKINFOR);
        token = sp.getSharedPreference(Constants.LOGIN_TOKEN, "").toString();
        initData(psyID);
    }

    public void initData(String id) {
        RetrofitHelper.getApiService()
                .getAppraisalIntroduce(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityAppraisalIntroduce>() {
                    @Override
                    public void onSuccess(final EntityAppraisalIntroduce response) {
                        if (response.getCode() == 1) {
                            Glide.with(mContext).load(response.getResult().getPsychtestPic())
                                    .into(iv_pic);
                            tv_name.setText(response.getResult().getName());
                            tv_title.setText(response.getResult().getTitle());
                            tv_price.setText("￥" + response.getResult().getPrice());
                            tv_topic_number.setText(response.getResult().getTopicNu() + "精选问题");
                            tv_show_number.setText(response.getResult().getShowTimes() + "浏览次数");
                            tv_share_number.setText(response.getResult().getShareTimes() + "分享次数");

                            tv_introduce.setText(response.getResult().getTestAbstract());

                            tv_price2.setText("本次测评为付费测评,测评体验价为" + response.getResult().getPrice() + "元");
                            tv_topic_number2.setText(response.getResult().getTopicNu() + "题");

                            time = response.getResult().getTestTime();

                            tv_time.setText(response.getResult().getTestTime() + "分钟");
                        } else {
                            Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                        }
                        return;
                    }
                });
    }

    @OnClick({R.id.bt_appraisal})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_appraisal:
                RetrofitHelper.getApiService()
                        .checkUserInfor(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseObserval<EntityCheckUserInfor>() {
                            @Override
                            public void onSuccess(final EntityCheckUserInfor response) {
                                if (response.getCode() == 1) {
                                    if (response.isResult()) {
                                        //用户的信息完善
                                        Intent intent = new Intent(AppraisalIntroduceActivity.this, AppraisalActivity.class);
                                        intent.putExtra("PsyID", psyID);
                                        intent.putExtra("time", time);
                                        intent.putExtra("name", tv_name.getText().toString());
                                        startActivity(intent);
                                    } else {
                                        //完善用户的信息
                                        startActivity(new Intent(AppraisalIntroduceActivity.this, CompleteUserInforActivity.class));
                                    }
                                } else {
                                    Toasty.warning(ContextUtils.getContext(), "服务器异常", Toast.LENGTH_SHORT, true).show();
                                }
                                return;
                            }
                        });
                break;
        }
    }
}
