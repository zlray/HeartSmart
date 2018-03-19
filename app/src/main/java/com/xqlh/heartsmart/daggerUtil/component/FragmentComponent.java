package com.xqlh.heartsmart.daggerUtil.component;

import android.app.Activity;

import com.xqlh.heartsmart.daggerUtil.module.FragmentModule;
import com.xqlh.heartsmart.daggerUtil.scope.FragmentScope;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

}
