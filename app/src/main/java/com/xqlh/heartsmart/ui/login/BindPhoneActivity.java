package com.xqlh.heartsmart.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.ui.bean.EntityBindPhone;
import com.xqlh.heartsmart.ui.bean.EntityCheckMessage;
import com.xqlh.heartsmart.ui.bean.EntityCheckPhone;
import com.xqlh.heartsmart.ui.bean.EntityGetMessage;
import com.xqlh.heartsmart.utils.CommonUtil;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.utils.Utils;
import com.xqlh.heartsmart.widget.TitleBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BindPhoneActivity extends BaseActivity {

    @BindView(R.id.bind_titleBar)
    TitleBar bind_titleBar;

    @BindView(R.id.et_bind_phone)
    EditText et_bind_phone;

    @BindView(R.id.et_verification_code_input)
    EditText et_verification_code_input;

    @BindView(R.id.iv_clean_bind_phone)
    ImageView iv_clean_bind_phone;

    @BindView(R.id.bt_verification_code_get)
    Button bt_verification_code_get;


    @BindView(R.id.bt_bind)
    Button bt_bind;


    private SharedPreferencesHelper sp_token;


    private Disposable mdDisposable;


    @Override
    public int setContent() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        initTtileBar();

        sp_token = new SharedPreferencesHelper(
                BindPhoneActivity.this, Constants.CHECKINFOR);


        et_bind_phone.addTextChangedListener(textWatcherPhone);
        et_verification_code_input.addTextChangedListener(textWatcherCode);
    }


    public void initTtileBar() {
        bind_titleBar.setLeftImageResource(R.drawable.return_button);
        bind_titleBar.setLeftText("返回");
        bind_titleBar.setTitle("绑定手机号");
        bind_titleBar.setTitleColor(Color.WHITE);
        bind_titleBar.setLeftTextColor(Color.WHITE);
        bind_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.bt_verification_code_get, R.id.iv_clean_bind_phone, R.id.bt_bind})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_verification_code_get:
                checkPhone(et_bind_phone.getText().toString().trim());
                //获取验证码
                break;
            case R.id.iv_clean_bind_phone:
                et_bind_phone.setText("");
                break;
            case R.id.bt_bind:
                //检验验证码，并绑定手机号
                checkMessage(et_verification_code_input.getText().toString().trim(),
                        sp_token.getSharedPreference(Constants.MESSAGE_TOKEN, "").toString().trim());
                break;
        }
    }

    /**
     * 检测手机号是否注册
     *
     * @param phone
     */
    public void checkPhone(final String phone) {
        RetrofitHelper.getApiService()
                .CheckPhone(phone)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityCheckPhone>bindToLifecycle())
                .compose(ProgressUtils.<EntityCheckPhone>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityCheckPhone>() {
                    @Override
                    public void onSuccess(EntityCheckPhone response) {
                        if (response.getCode() == 1) {
                            if (response.getMsg().equals("OK")) {
                                if (response.isResult()) {
                                    //
                                    getMessage(phone);
                                    mdDisposable = Flowable.intervalRange(0, 61, 0, 1, TimeUnit.SECONDS)
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .doOnNext(new Consumer<Long>() {
                                                @Override
                                                public void accept(Long aLong) throws Exception {
                                                    bt_verification_code_get.setText((60 - aLong) + "s");
                                                    bt_verification_code_get.setEnabled(false);
                                                    bt_verification_code_get.setBackgroundResource(R.drawable.retrieve_countdown_click);
                                                }
                                            })
                                            .doOnComplete(new Action() {
                                                @Override
                                                public void run() throws Exception {
                                                    //倒计时完毕置为可点击状态
                                                    bt_verification_code_get.setEnabled(true);
                                                    bt_verification_code_get.setText("重新获取");
                                                    bt_verification_code_get.setBackgroundResource(R.drawable.retrieve_countdown_default);
                                                }
                                            })
                                            .subscribe();
                                } else {
                                    Toasty.warning(Utils.getContext(), "该手机号已经被注册", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }
                    }
                });

    }


    //获取验证码
    public void getMessage(String phone) {
        Log.i(TAG, "获取验证码");
        RetrofitHelper.getApiService()
                .GetMessage(phone)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityGetMessage>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityGetMessage>() {
                    @Override
                    public void onSuccess(EntityGetMessage response) {
                        if (response.getCode() == 1) {
                            if (response.getMsg().equals("OK")) {
                                Log.i(TAG, "onSuccess: " + response.getResult());
                                //存储
                                sp_token.put(Constants.MESSAGE_TOKEN, response.getResult());
                            }
                        }
                    }
                });
    }

    //校验验证码接口
    public void checkMessage(String message, String token) {
        RetrofitHelper.getApiService()
                .CheckMessage(token, message)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityCheckMessage>bindToLifecycle())
                .compose(ProgressUtils.<EntityCheckMessage>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityCheckMessage>() {
                    @Override
                    public void onSuccess(EntityCheckMessage response) {
                        if (response.getCode() == 1) {
                            bindPhone(et_bind_phone.getText().toString().trim(),
                                    sp_token.getSharedPreference(Constants.MESSAGE_TOKEN, "").toString().trim(),
                                    et_verification_code_input.getText().toString().trim()
                            );
                        } else {
                            Toasty.success(Utils.getContext(), "验证码错误", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    public void bindPhone(String Telphone, String token, String telcode) {
//        HashMap<String,String> map = new HashMap<>();
//        map.put("Telphone",Telphone);
//        map.put("token",token);
//        map.put("telcode",telcode);

        Log.i(TAG, "获取登录的Token: " + sp_token.getSharedPreference(Constants.LOGIN_TOKEN, "").toString().trim());
        RetrofitHelper.getApiService()
                .bindPhone(sp_token.getSharedPreference(Constants.LOGIN_TOKEN, "").toString().trim(),
                        Telphone, token, telcode)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityBindPhone>bindToLifecycle())
                .compose(ProgressUtils.<EntityBindPhone>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityBindPhone>() {
                    @Override
                    public void onSuccess(EntityBindPhone response) {
                        if (response.getCode() == 1) {
                            if (response.isResult()) {
                                Toasty.success(Utils.getContext(), response.getResultMsg(), Toast.LENGTH_SHORT, true).show();
                                Intent intent = new Intent(BindPhoneActivity.this, RetrievePasswordActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    }
                });
    }

    private TextWatcher textWatcherPhone = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = et_bind_phone.getText().toString().trim();
            String verificationCode = et_verification_code_input.getText().toString().trim();

            if (phone.length() == 11) {
                if (CommonUtil.isMobileNO(phone)) {
                    bt_verification_code_get.setEnabled(true);
                    bt_verification_code_get.setBackgroundResource(R.drawable.retrieve_countdown_default);
                    if (verificationCode.length() == 6) {
                        bt_bind.setEnabled(true);
                        bt_bind.setBackgroundResource(R.drawable.login_bt_bg);
                    }
                } else {
                    Toasty.warning(Utils.getContext(), "输入正确格式手机号", Toast.LENGTH_SHORT, true).show();
                    bt_verification_code_get.setEnabled(false);
                    bt_verification_code_get.setBackgroundResource(R.drawable.retrieve_countdown_click);
                }
            } else {
                bt_bind.setEnabled(false);
                bt_bind.setBackgroundResource(R.drawable.login_bt_bg_default);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phone = et_bind_phone.getText().toString().trim();
            String verificationCode = et_verification_code_input.getText().toString().trim();

            if (!TextUtils.isEmpty(phone) && iv_clean_bind_phone.getVisibility() == View.GONE) {
                //显示清除按钮
                iv_clean_bind_phone.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(phone)) {
                iv_clean_bind_phone.setVisibility(View.GONE);
                bt_verification_code_get.setEnabled(false);
                bt_bind.setEnabled(false);
            }
        }
    };

    private TextWatcher textWatcherCode = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String verificationCode = et_verification_code_input.getText().toString().trim();
            if (verificationCode.length() == 6) {
                bt_bind.setEnabled(true);
                bt_bind.setBackgroundResource(R.drawable.login_bt_bg);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mdDisposable != null) {
            mdDisposable.dispose();
        }
    }
}
