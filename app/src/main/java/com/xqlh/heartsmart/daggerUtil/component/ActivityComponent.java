package com.xqlh.heartsmart.daggerUtil.component;

import android.app.Activity;

import com.xqlh.heartsmart.daggerUtil.module.ActivityModule;
import com.xqlh.heartsmart.daggerUtil.scope.ActivityScope;
import com.xqlh.heartsmart.ui.GuideActivity;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(GuideActivity guideActivity);

}
