package com.xqlh.heartsmart.ui.login.ui;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityGetMessage;
import com.xqlh.heartsmart.bean.EntityGetPhoneByAccount;
import com.xqlh.heartsmart.bean.EntityUpdatePassword;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.utils.ContenxtUtils;
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

public class RetrievePasswordActivity extends BaseActivity {

    @BindView(R.id.retrieve_titleBar)
    TitleBar retrieve_titleBar;

    @BindView(R.id.tv_account)
    TextView tv_account;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.et_verification_code_input)
    EditText et_verification_code_input;

    @BindView(R.id.et_password_fist)
    EditText et_password_fist;

    @BindView(R.id.et_password_second)
    EditText et_password_second;


    @BindView(R.id.iv_clean_account)
    ImageView iv_clean_account;

//    @BindView(R.id.iv_clean_phone)
//    ImageView iv_clean_phone;

    @BindView(R.id.iv_clean_password_first)
    ImageView iv_clean_password_first;

    @BindView(R.id.iv_clean_password_second)
    ImageView iv_clean_password_second;


    @BindView(R.id.bt_verification_code_get)
    Button bt_verification_code_get;

    @BindView(R.id.bt_submit)
    Button bt_submit;

    String account;
    SharedPreferencesHelper sp_token;
    private Disposable mdDisposable;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private int height = 0;


    @Override
    public int setContent() {
        return R.layout.activity_retrieve_password;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        sp_token = new SharedPreferencesHelper(ContenxtUtils.getContext(), Constants.CHECKINFOR);
        Intent intent = getIntent();
        account = intent.getStringExtra(Constants.ACCOUNT);
        tv_account.setText(account);
        initTtileBar();
        getPhoneByAccount(account);
        et_password_fist.addTextChangedListener(textWatcher);
        et_password_second.addTextChangedListener(textWatcher);
    }

    public void initTtileBar() {
        retrieve_titleBar.setLeftImageResource(R.drawable.return_button);
        retrieve_titleBar.setLeftText("返回");
        retrieve_titleBar.setTitle("找回密码");
        retrieve_titleBar.setTitleColor(Color.WHITE);
        retrieve_titleBar.setLeftTextColor(Color.WHITE);
        retrieve_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //根据用户名获取手机号
    public void getPhoneByAccount(String account) {
        RetrofitHelper.getApiService()
                .GetPhoneByAccount(account)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityGetPhoneByAccount>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityGetPhoneByAccount>() {
                    @Override
                    public void onSuccess(EntityGetPhoneByAccount response) {
                        if (response.getCode() == 1) {
                            if (response.getMsg().equals("OK")) {
                                if (!"".equals(response.getResult())) { //获取手机号
                                    //显示手机号
                                    tv_phone.setText(response.getResult());
                                    Log.i(TAG, "获取到的手机号" + response.getResult());
                                    //存储token
                                    sp_token.put(Constants.GET_PHONE_BY_ACCOUNT_TOKEN, response.getResultMsg());
                                    bt_verification_code_get.setEnabled(true);
                                    bt_verification_code_get.setBackgroundResource(R.drawable.retrieve_countdown_default);
                                } else {
                                    tv_phone.setText("用户未绑定手机号");
                                    showDoalog();
                                }
                            }
                        }
                    }
                });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password_one = et_password_fist.getText().toString().trim();
            String password_two = et_password_fist.getText().toString().trim();
            if (password_one.length() >= 6 & password_two.length() >= 6) {
                bt_submit.setEnabled(true);
                bt_submit.setBackgroundResource(R.drawable.retrieve_countdown_default);

            } else {
                bt_submit.setEnabled(false);
                bt_submit.setBackgroundResource(R.drawable.retrieve_countdown_click);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String password_one = et_password_fist.getText().toString().trim();
            String password_two = et_password_fist.getText().toString().trim();
            if (!TextUtils.isEmpty(password_one) && iv_clean_password_first.getVisibility() == View.GONE) {
                //显示清除按钮
                iv_clean_password_first.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(password_one)) {
                iv_clean_password_first.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(password_two) && iv_clean_password_second.getVisibility() == View.GONE) {
                //显示清除按钮
                iv_clean_password_second.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(password_two)) {
                iv_clean_password_second.setVisibility(View.GONE);
            }
        }
    };

    @OnClick({R.id.bt_verification_code_get, R.id.bt_submit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_verification_code_get:
                //验证手机号
                getMessage(sp_token.getSharedPreference(Constants.GET_PHONE_BY_ACCOUNT_TOKEN, "").toString());

                Log.i(TAG, "显示的手机号: " + tv_phone.getText().toString().trim());
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
                break;
            case R.id.bt_bind:
                if (et_password_fist.getText().equals(et_password_second.getText())) {
                    //用户名获取手机号返回的 toke,  验证码,  密码,  获取短信返回的token
                    updatePassword(sp_token.getSharedPreference(Constants.GET_PHONE_BY_ACCOUNT_TOKEN, "").toString(),
                            et_verification_code_input.getText().toString().trim(),
                            et_password_fist.getText().toString().trim(),
                            sp_token.getSharedPreference(Constants.MESSAGE_TOKEN, "").toString()
                    );
                } else {
                    Toasty.warning(ContenxtUtils.getContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT, true).show();
                }
                break;
        }
    }

    public void updatePassword(String TelephoneToken, String VerCode, String password, String token) {
        RetrofitHelper.getApiService()
                .ForgetUpdatePassword(TelephoneToken, VerCode, password, token)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityUpdatePassword>bindToLifecycle())
                .compose(ProgressUtils.<EntityUpdatePassword>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUpdatePassword>() {
                    @Override
                    public void onSuccess(EntityUpdatePassword response) {
                        if (response.getCode() == 1) {
                            startActivity(new Intent(RetrievePasswordActivity.this, LoginActivity.class));
                        }
                    }
                });
    }


    //获取验证码
    public void getMessage(String tocken) {
        Log.i(TAG, "获取验证码");
        RetrofitHelper.getApiService()
                .GetMessage2(tocken)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityGetMessage>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityGetMessage>() {
                    @Override
                    public void onSuccess(EntityGetMessage response) {
                        if (response.getCode() == 1) {
                            if (response.getMsg().equals("OK")) {
                                Log.i(TAG, "onSuccess: " + response.getResult());
                                //存储短信的token
                                sp_token.put(Constants.MESSAGE_TOKEN, response.getResult());
                            }
                        }
                    }
                });
    }


    public void showDoalog() {
        //弹出框 跳转到绑定手机号界面
        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(mContext);//提示弹窗
        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RetrievePasswordActivity.this, BindPhoneActivity.class));
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
}
