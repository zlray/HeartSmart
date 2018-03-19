package com.xqlh.heartsmart.daggerUtil.module;

import android.app.Activity;

import com.xqlh.heartsmart.daggerUtil.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
