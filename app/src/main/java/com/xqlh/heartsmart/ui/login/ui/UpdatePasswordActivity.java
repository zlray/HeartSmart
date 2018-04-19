package com.xqlh.heartsmart.ui.login.ui;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vondear.rxtools.RxKeyboardTool;
import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.api.RetrofitHelper;
import com.xqlh.heartsmart.api.base.BaseObserval;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.bean.EntityUpdatePassword;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.ProgressUtils;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdatePasswordActivity extends BaseActivity {
    @BindView(R.id.et_password_fist)
    EditText et_password_fist;

    @BindView(R.id.et_password_second)
    EditText et_password_second;

    @BindView(R.id.bt_update)
    Button bt_update;

    @BindView(R.id.iv_clean_password_first)
    ImageView iv_clean_password_first;

    @BindView(R.id.iv_clean_password_second)
    ImageView iv_clean_password_second;

    @BindView(R.id.update_password_titleBar)
    TitleBar update_password_titleBar;

    private SharedPreferencesHelper sp_login_token;


    @Override
    public int setContent() {
        return R.layout.activity_update_password;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        initTtileBar();
        sp_login_token = new SharedPreferencesHelper(
                UpdatePasswordActivity.this, Constants.CHECKINFOR);

        et_password_fist.addTextChangedListener(textWatcher);
        et_password_second.addTextChangedListener(textWatcher);

    }

    public void initTtileBar() {
        update_password_titleBar.setLeftImageResource(R.drawable.return_button);
        update_password_titleBar.setLeftText("返回");
        update_password_titleBar.setTitle("设置");
        update_password_titleBar.setTitleColor(Color.WHITE);
        update_password_titleBar.setLeftTextColor(Color.WHITE);
        update_password_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.iv_clean_password_first, R.id.iv_clean_password_second, R.id.bt_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clean_password_first:
                et_password_fist.setText("");
                break;
            case R.id.iv_clean_password_second:
                et_password_second.setText("");
                break;
            case R.id.bt_update:
                RxKeyboardTool.hideSoftInput(UpdatePasswordActivity.this);
                String passwordFirst = et_password_fist.getText().toString();
                String passwordSecond = et_password_second.getText().toString();
                if (passwordFirst.equals(passwordSecond)) {
                    update(sp_login_token.getSharedPreference(Constants.LOGIN_TOKEN, "").toString(), passwordFirst, passwordSecond);
                } else {
                    Toasty.warning(UpdatePasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT, true).show();
                }
                break;
        }
    }

    public void update(String token, String passwordFirst, String passwordSecond) {
// 18701662581
        RetrofitHelper.getApiService()
                .UpdatePassword(token, passwordFirst, passwordSecond)
                .subscribeOn(Schedulers.io())
                .compose(this.<EntityUpdatePassword>bindToLifecycle())
                .compose(ProgressUtils.<EntityUpdatePassword>applyProgressBar(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserval<EntityUpdatePassword>() {
                    @Override
                    public void onSuccess(EntityUpdatePassword response) {
                        if (response.getCode() == 1) {
                            Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            sp_login_token.remove(Constants.LOGIN_TOKEN);
                            Toasty.success(UpdatePasswordActivity.this, "修改成功，重新登录", Toast.LENGTH_SHORT, true).show();
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
            String passwordFirst = et_password_fist.getText().toString().trim();
            String passwordSecond = et_password_second.getText().toString().trim();

            if (passwordFirst.length() >= 6 && passwordSecond.length() >= 6) {
                bt_update.setEnabled(true);
                bt_update.setBackgroundResource(R.drawable.login_bt_bg);
            } else {
                bt_update.setEnabled(false);
                bt_update.setBackgroundResource(R.drawable.login_bt_bg_default);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            String passwordFirst = et_password_fist.getText().toString().trim();
            String passwordSecond = et_password_second.getText().toString().trim();
            //m密码框的操作
            if (!TextUtils.isEmpty(passwordFirst) && iv_clean_password_first.getVisibility() == View.GONE) {
                //显示清除按钮
                iv_clean_password_first.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(passwordFirst)) {
                iv_clean_password_first.setVisibility(View.GONE);
                bt_update.setEnabled(false);
            }
            //密码框的操作
            if (!TextUtils.isEmpty(passwordSecond) && iv_clean_password_second.getVisibility() == View.GONE) {
                iv_clean_password_second.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(passwordSecond)) {
                iv_clean_password_second.setVisibility(View.GONE);
                bt_update.setEnabled(false);
            }

//            //检测密码框输入类型
//            if (passwordFirst.isEmpty())
//                return;
//            if (!passwordFirst.matches("[A-Za-z0-9]+") || !passwordSecond.matches("[A-Za-z0-9]+")) {
//                Toasty.warning(Utils.getContext(), "请输入数字或字母", Toast.LENGTH_SHORT, true).show();
//                s.clear();
//            }
        }
    };
}
