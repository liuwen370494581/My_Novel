package com.example.ruolan.letgo.Dao;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

import star.liuwen.com.novel_lw.Dao.DaoMaster;
import star.liuwen.com.novel_lw.Dao.DaoSession;

/**
 * Created by Administrator on 2017/2/17 0017.
 */

public class DaoManager {
    // 指定数据库的名字
    private static String DB_NAME = "sqtwin.db";
    private static DaoManager mDaoManager;
    private static MySQLiteOpenHelper mySqlLiteOpenHelper;
    private static DaoSession mDaoSession;
    private static Database mDatabase;


    private DaoManager() {
    }

    public static DaoManager getInstance() {
        if (mDaoManager == null) {
            synchronized (DaoManager.class) {
                if (mDaoManager == null) {
                    mDaoManager = new DaoManager();
                }
            }
        }
        return mDaoManager;
    }

    public static void init(Context context) {
        mySqlLiteOpenHelper = new MySQLiteOpenHelper(context, DB_NAME, null);
        mDatabase = mySqlLiteOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(mDatabase).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
