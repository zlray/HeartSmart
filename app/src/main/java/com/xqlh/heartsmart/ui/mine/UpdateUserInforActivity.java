package com.xqlh.heartsmart.ui.mine;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;

public class UpdateUserInforActivity extends BaseActivity {

    @BindView(R.id.update_titleBar)
    TitleBar update_titleBar;
    String nickname;
    String sex;
    String birthday;

    @Override
    public int setContent() {
        return R.layout.activity_update_user_infor;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        sex = intent.getStringExtra("sex");
        birthday = intent.getStringExtra("birthday");
        initTtileBar();

    }

    public void initTtileBar() {
        update_titleBar.setTitle("修改信息");
        update_titleBar.setTitleColor(Color.WHITE);
        update_titleBar.setLeftText("返回");
        update_titleBar.setLeftTextColor(Color.WHITE);
        update_titleBar.setLeftImageResource(R.drawable.return_button);
        update_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
