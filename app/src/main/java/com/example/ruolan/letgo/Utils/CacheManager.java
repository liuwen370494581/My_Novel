package com.example.ruolan.letgo.Utils;

import com.example.ruolan.letgo.bean.BookModel;
import com.example.ruolan.letgo.bean.ClassifyModel;

import java.util.List;

/**
 * Created by liuwen on 2017/8/8.
 */
public class CacheManager {

    private static CacheManager instance;
    private List<BookModel> mBookModelList;
    private List<ClassifyModel> mClassifyModelList;
    private List<String> mCoverList;//封面轮播图url
    private List<BookModel> mCoverModelList;//封面轮播图文字
    private List<BookModel> mEditRecommendation;//首页主编推荐
    private List<BookModel> mNewUpdateList;//首页最新更新
    private List<BookModel> mNewBookFinish;//已经完结作品
    private List<BookModel> mAppsFree;//限时免费


    public static CacheManager getInstance() {
        if (null == instance) {
            synchronized (CacheManager.class) {
                if (null == instance) {
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }

    public List<BookModel> getBookModelList() {
        return mBookModelList;
    }

    public void setBookModelList(List<BookModel> bookModelList) {
        mBookModelList = bookModelList;
    }

    public List<ClassifyModel> getClassifyModelList() {
        return mClassifyModelList;
    }

    public void setClassifyModelList(List<ClassifyModel> classifyModelList) {
        mClassifyModelList = classifyModelList;
    }

    public List<String> getCoverList() {
        return mCoverList;
    }

    public void setCoverList(List<String> coverList) {
        mCoverList = coverList;
    }

    public List<BookModel> getCoverModelList() {
        return mCoverModelList;
    }

    public void setCoverModelList(List<BookModel> coverModelList) {
        mCoverModelList = coverModelList;
    }

    public List<BookModel> getEditRecommendation() {
        return mEditRecommendation;
    }

    public void setEditRecommendation(List<BookModel> editRecommendation) {
        mEditRecommendation = editRecommendation;
    }

    public List<BookModel> getNewUpdateList() {
        return mNewUpdateList;
    }

    public void setNewUpdateList(List<BookModel> newUpdateList) {
        mNewUpdateList = newUpdateList;
    }

    public List<BookModel> getNewBookFinish() {
        return mNewBookFinish;
    }

    public void setNewBookFinish(List<BookModel> newBookFinish) {
        mNewBookFinish = newBookFinish;
    }

    public List<BookModel> getAppsFree() {
        return mAppsFree;
    }

    public void setAppsFree(List<BookModel> appsFree) {
        mAppsFree = appsFree;
    }

    public void cleanCache() {
        if (instance != null) {
            instance = null;
        }
    }
}
