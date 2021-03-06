package com.example.ruolan.letgo.Dao;

import com.example.ruolan.letgo.bean.Dish;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Created by liuwen on 2017/7/28.
 */
public class DaoBookHistory {

    /**
     * 插入对象数据
     * 插入对象为BudgetModel
     *
     * @param model
     */
    public static void insert(Dish model) {
        DaoManager.getInstance().getDaoSession().getDishDao().insert(model);
    }

    /**
     * 删除指定的model的数据
     *
     * @param model
     */
    public static void deleteByModel(Dish model) {
        DaoManager.getInstance().getDaoSession().getDishDao().delete(model);
    }


    /**
     * 删除所有
     */
    public static void deleteAllData() {
        DaoManager.getInstance().getDaoSession().getDishDao().deleteAll();
    }
    /**
     * 更新数据
     *
     * @param model
     */
    public static void update(Dish model) {
        DaoManager.getInstance().getDaoSession().getDishDao().update(model);
    }

    /**
     * 查询 BudgetModel的集合对象
     *
     * @return
     */
    public static List<Dish> query() {
        List<Dish> list = new ArrayList<>();
        list = DaoManager.getInstance().getDaoSession().getDishDao().queryBuilder().list();
        //先排序
        Collections.sort(list, new Comparator<Dish>() {
            @Override
            public int compare(Dish model1, Dish model2) {
                return model2.getUrl().compareTo(model1.getUrl());
            }
        });
        //数据去重复
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getTitle().equals(list.get(i).getTitle())) {
                    list.remove(j);
                }
            }
        }
        return list;
    }


    /**
     * 获取总数
     * UserInfoModel的总数
     *
     * @return
     */
    public static long getCount() {
        return DaoManager.getInstance().getDaoSession().getDishDao().count();
    }


}
