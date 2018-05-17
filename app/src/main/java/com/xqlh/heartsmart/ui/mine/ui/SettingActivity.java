package com.xqlh.heartsmart.ui.mine.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.ui.login.ui.BindPhoneActivity;
import com.xqlh.heartsmart.ui.login.ui.LoginActivity;
import com.xqlh.heartsmart.ui.login.ui.UpdatePasswordActivity;
import com.xqlh.heartsmart.utils.Constants;
import com.xqlh.heartsmart.utils.SharedPreferencesHelper;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.setting_titleBar)
    TitleBar setting_titleBar;
    @BindView(R.id.setting_rv_update_password)
    RelativeLayout setting_rv_update_password;
    @BindView(R.id.setting_rv_update_phone)
    RelativeLayout setting_rv_update_phone;
    private SharedPreferencesHelper sp_login_token;

    @Override
    public int setContent() {
        return R.layout.activity_setting;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        sp_login_token = new SharedPreferencesHelper(
                SettingActivity.this, Constants.CHECKINFOR);
        initTtileBar();

    }

    public void initTtileBar() {
        setting_titleBar.setLeftImageResource(R.drawable.return_button);
        setting_titleBar.setTitle("设置");
        setting_titleBar.setTitleColor(Color.WHITE);
        setting_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.setting_rv_update_password, R.id.setting_rv_update_phone, R.id.bt_exit})
    public void onClikc(View view) {
        switch (view.getId()) {
            case R.id.setting_rv_update_password:
                startActivity(new Intent(SettingActivity.this, UpdatePasswordActivity.class));

                break;
            case R.id.setting_rv_update_phone:
                startActivity(new Intent(SettingActivity.this, BindPhoneActivity.class));
                break;
            case R.id.bt_exit:
                sp_login_token.remove(Constants.LOGIN_TOKEN);
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }
}
