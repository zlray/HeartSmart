package com.xqlh.heartsmart.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vondear.rxtools.view.dialog.RxDialogSureCancel;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.api.bean.EntityCheckPhone;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.utils.CommonUtil;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.Utils;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RetrievePasswordActivity extends BaseActivity {
    @BindView(R.id.retrieve_titleBar)
    TitleBar retrieve_titleBar;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.et_verification_code_input)
    EditText et_verification_code_input;

    @BindView(R.id.et_password_fist)
    EditText et_password_fist;

    @BindView(R.id.et_password_second)
    EditText et_password_second;


    @BindView(R.id.iv_clean_phone)
    ImageView iv_clean_phone;

    @BindView(R.id.iv_clean_password_first)
    ImageView iv_clean_password_first;

    @BindView(R.id.iv_clean_password_second)
    ImageView iv_clean_password_second;


    @BindView(R.id.bt_verification_code_get)
    Button bt_verification_code_get;

    @BindView(R.id.bt_submit)
    Button bt_submit;


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

        initTtileBar();
        et_phone.addTextChangedListener(textWatcher);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = et_phone.getText().toString().trim();
            if (phone.length() == 11) {
                if (CommonUtil.isMobileNO(phone)) {
                    bt_verification_code_get.setEnabled(true);
                    bt_verification_code_get.setBackgroundResource(R.drawable.retrieve_countdown_default);
                } else {
                    Toasty.warning(Utils.getContext(), "输入正确格式手机号", Toast.LENGTH_SHORT, true).show();
                }
            } else {
                bt_verification_code_get.setEnabled(false);
                bt_verification_code_get.setBackgroundResource(R.drawable.retrieve_countdown_click);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String phone = et_phone.getText().toString().trim();
            if (!TextUtils.isEmpty(phone) && iv_clean_phone.getVisibility() == View.GONE) {
                //显示清除按钮
                iv_clean_phone.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(phone)) {
                iv_clean_phone.setVisibility(View.GONE);
                bt_verification_code_get.setEnabled(false);
            }
        }
    };

    @OnClick({R.id.bt_verification_code_get, R.id.iv_clean_phone})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_verification_code_get:
                //验证手机号
                checkPhone(et_phone.getText().toString().trim());
                break;
            case R.id.iv_clean_phone:
                et_phone.setText("");
                break;
        }
    }

    public void checkPhone(String phone) {
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
                                    //获取验证码
                                } else {
                                    showDoalog();
                                }
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
