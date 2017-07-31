package com.example.ruolan.letgo.Utils;

import android.content.Context;

/**
 * Created by liuwen on 2017/7/31.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defaultExceptionHandler;
    //声明单列
    private static CrashHandler customException;

    private Context mContext;

    public static CrashHandler getInstance() {
        if (customException == null) {
            customException = new CrashHandler();
        }
        return customException;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

    }
}
