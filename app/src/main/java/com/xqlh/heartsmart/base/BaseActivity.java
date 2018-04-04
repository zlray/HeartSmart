package com.xqlh.heartsmart.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/22.
 */

public abstract class BaseActivity extends SupportActivity {

    /**
     * 定义一个成员变量
     * Define a  member arivable that is isFullScreen.
     * it's default value is false,which means not full-screen.
     */
    private boolean isFullScreen = false;
    protected static final String TAG = "lz";
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(setContent());
        ButterKnife.bind(this);
        mContext = BaseActivity.this;
        bindView(savedInstanceState);
        init();
    }


    /**
     * @return
     * @description 加载该Acitivit的布局
     * loading the layout of current Activity
     */
    public abstract int setContent();

    /**
     * @description 是否设置当前Activity为全屏
     * Weather to set the current Activity to full screen
     */
    public abstract boolean setFullScreen();

    /**
     * @description 加载当前Activity的控件
     * Loading Controls of current Activity
     */
    public abstract void init();

    /**
     *
     */
    public abstract void bindView(Bundle savedInstanceState);
}
