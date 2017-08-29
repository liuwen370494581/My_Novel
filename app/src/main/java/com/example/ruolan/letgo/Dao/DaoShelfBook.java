package com.example.ruolan.letgo.Dao;

import com.example.ruolan.letgo.bean.BookModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liuwen on 2017/8/4.
 */
public class DaoShelfBook {

    /**
     * 插入对象数据
     * 插入对象为BudgetModel
     *
     * @param model
     */
    public static void insert(BookModel model) {
        DaoManager.getInstance().getDaoSession().getBookModelDao().insertOrReplace(model);
    }

    /**
     * 删除指定的model的数据
     *
     * @param model
     */
    public static void deleteByModel(BookModel model) {
        DaoManager.getInstance().getDaoSession().getBookModelDao().delete(model);
    }


    /**
     * 删除所有
     */
    public static void deleteAllData() {
        DaoManager.getInstance().getDaoSession().getBookModelDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void update(BookModel model) {
        DaoManager.getInstance().getDaoSession().getBookModelDao().update(model);
    }

    /**
     * 查询 BudgetModel的集合对象
     *
     * @return
     */
    public static List<BookModel> query() {
        List<BookModel> list = new ArrayList<>();
        list = DaoManager.getInstance().getDaoSession().getBookModelDao().queryBuilder().list();
        Collections.sort(list, new Comparator<BookModel>() {
            @Override
            public int compare(BookModel model1, BookModel model2) {
                return model2.getBookReadTime().compareTo(model1.getBookReadTime());
            }
        });
        return list;
    }


    /**
     * 获取总数
     * UserInfoModel的总数
     *
     * @return
     */
    public static long getCount() {
        return DaoManager.getInstance().getDaoSession().getBookModelDao().count();
    }

}
