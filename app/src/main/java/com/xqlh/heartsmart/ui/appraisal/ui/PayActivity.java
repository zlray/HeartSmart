package com.xqlh.heartsmart.ui.appraisal.ui;

import android.graphics.Color;
import android.view.View;

import com.xqlh.heartsmart.R;
import com.xqlh.heartsmart.base.BaseActivity;
import com.xqlh.heartsmart.widget.TitleBar;

import butterknife.BindView;

public class PayActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titlebar;

    @Override
    public int setContent() {
        return R.layout.activity_pay;
    }

    @Override
    public boolean setFullScreen() {
        return false;
    }

    @Override
    public void init() {

    }

    public void initTtileBar() {
        titlebar = (TitleBar) findViewById(R.id.titlebar);
        titlebar.setLeftImageResource(R.drawable.return_button);
        titlebar.setTitle("支付方式");
        titlebar.setTitleColor(Color.WHITE);
        titlebar.setLeftClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
    }
}
