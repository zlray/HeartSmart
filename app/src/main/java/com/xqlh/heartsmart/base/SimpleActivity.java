package com.xqlh.heartsmart.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xqlh.heartsmart.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by codeest on 16/8/11.
 * 无MVP的activity基类
 */

public abstract class SimpleActivity extends SupportActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;
    /**
     * 定义一个成员变量
     * Define a  member arivable that is isFullScreen.
     * it's default value is false,which means not full-screen.
     */
    private boolean isFullScreen = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 必须设置在加载布局前
         * Must be set before loading the Layout
         */
        isFullScreen = setFullScreen();
        if (isFullScreen) {
            /**
             * 设置为无标题，全屏
             *
             * setting to Untitled,Full Screen
             */
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );

        } else {
            /**
             * 设置为无标题
             * set to Untitled
             */
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        onViewCreated();
        App.getInstance().addActivity(this);
        initEventAndData();
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressedSupport();
            }
        });
    }

    protected void onViewCreated() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
        mUnBinder.unbind();
    }

    /**
     * 加载布局
     * load layout
     * @return
     */
    protected abstract int getLayout();

    /**
     * @description 加载数据
     * load data
     */
    protected abstract void initEventAndData();
    /**
     * @description 是否设置当前Activity为全屏
     * Weather to set the current Activity to full screen
     */
    public abstract boolean setFullScreen();
}
