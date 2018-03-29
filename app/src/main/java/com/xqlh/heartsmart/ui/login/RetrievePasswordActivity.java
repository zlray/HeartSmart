package com.xqlh.heartsmart.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;

public class RetrievePasswordActivity extends BaseActivity {
    @BindView(R.id.retrieve_titleBar)
    TitleBar retrieve_titleBar;


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
    }

    @Override
    public void bindView(Bundle savedInstanceState) {

    }

    public void initTtileBar(){
//        retrieve_titleBar.setLeftImageResource(R.mipmap.back_green);
        retrieve_titleBar.setLeftText("返回");
        retrieve_titleBar.setLeftTextColor(Color.WHITE);
        retrieve_titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
