package com.example.ruolan.letgo.Base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.example.ruolan.letgo.Dao.DaoManager;
import com.example.ruolan.letgo.Utils.CrashHandler;

/**
 * Created by liuwen on 2017/6/20.
 * extends MultiDexApplication solve 65536 ways
 */
public class App extends MultiDexApplication {
    private static Context context;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (getSharedPreferences(Config.USER_SETTINGS, MODE_PRIVATE).getInt(Config.USER_THEME, 0) == 0) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        DaoManager.init(this);
        //崩溃日志记录
        CrashHandler.getInstance().init(this);
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }


}
