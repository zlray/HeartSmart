package com.xqlh.heartsmart.ui.login;

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

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.utils.CommonUtil;
import com.xqlh.heartsmart.utils.Utils;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

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
        et_bind_phone.addTextChangedListener(textWatcher);
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

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

    @OnClick({R.id.bt_verification_code_get, R.id.iv_clean_bind_phone})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_verification_code_get:
                //验证手机号
                bindPhone(et_bind_phone.getText().toString().trim());
                break;
            case R.id.iv_clean_phone:
                et_bind_phone.setText("");
                break;
        }
    }

    public void bindPhone(String phone) {

    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phone = et_bind_phone.getText().toString().trim();
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
            String phone = et_bind_phone.getText().toString().trim();
            if (!TextUtils.isEmpty(phone) && iv_clean_bind_phone.getVisibility() == View.GONE) {
                //显示清除按钮
                et_bind_phone.setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(phone)) {
                et_bind_phone.setVisibility(View.GONE);
                bt_verification_code_get.setEnabled(false);
            }
        }
    };
}
