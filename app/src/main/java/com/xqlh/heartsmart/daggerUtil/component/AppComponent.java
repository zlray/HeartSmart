package com.xqlh.heartsmart.daggerUtil.component;


import com.xqlh.heartsmart.app.App;
import com.xqlh.heartsmart.daggerUtil.module.AppModule;
import com.xqlh.heartsmart.daggerUtil.module.HttpModule;
import com.xqlh.heartsmart.model.DataManager;
import com.xqlh.heartsmart.model.db.RealmHelper;
import com.xqlh.heartsmart.model.http.RetrofitHelper;
import com.xqlh.heartsmart.model.prefs.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by codeest on 16/8/7.
 */

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    DataManager getDataManager(); //数据中心

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    RealmHelper realmHelper();    //提供数据库帮助类

    ImplPreferencesHelper preferencesHelper(); //提供sp帮助类
}
