package com.xqlh.heartsmart.daggerUtil.module;


import com.xqlh.heartsmart.app.App;
import com.xqlh.heartsmart.model.DataManager;
import com.xqlh.heartsmart.model.db.DBHelper;
import com.xqlh.heartsmart.model.db.RealmHelper;
import com.xqlh.heartsmart.model.http.HttpHelper;
import com.xqlh.heartsmart.model.http.RetrofitHelper;
import com.xqlh.heartsmart.model.prefs.ImplPreferencesHelper;
import com.xqlh.heartsmart.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by codeest on 16/8/7.
 */

@Module
public class AppModule {


    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(RealmHelper realmHelper) {
        return realmHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, DBHelper DBHelper, PreferencesHelper preferencesHelper) {
        return new DataManager(httpHelper, DBHelper, preferencesHelper);
    }
}
